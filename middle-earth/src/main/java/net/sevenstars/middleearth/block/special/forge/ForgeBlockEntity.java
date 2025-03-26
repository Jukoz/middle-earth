package net.sevenstars.middleearth.block.special.forge;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlock;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreenHandler;
import net.sevenstars.middleearth.gui.forge.ForgeHeatingScreenHandler;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.recipe.ModRecipes;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ForgeBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {
    private static final String ID = "forge";
    public static final int MAX_PROGRESS = 1200;
    public static final int MAX_STORAGE = 2304;
    public static final int MAX_BOOST_TIME = 10;
    public static final int FUEL_SLOT = 0;
    public static final int OUTPUT_SLOT = 5;
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(6, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int boostTime = 0;
    private int fuelTime = 0;
    private int maxFuelTime = 0;
    private int mode = 0;
    private int storage = 0;

    private final ServerRecipeManager.MatchGetter<MultipleStackRecipeInput, ? extends AlloyingRecipe> matchGetter;

    private MetalTypes currentMetal = MetalTypes.EMPTY;

    //TODO melting smithing parts
    //TODO convert metals to registry, enum constant datagen if no registry
    //TODO custom metal trim data component with palette

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FORGE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ForgeBlockEntity.this.progress;
                    case 1 -> ForgeBlockEntity.this.fuelTime;
                    case 2 -> ForgeBlockEntity.this.maxFuelTime;
                    case 3 -> ForgeBlockEntity.this.mode;
                    case 4 -> ForgeBlockEntity.this.storage;
                    case 5 -> ForgeBlockEntity.this.currentMetal.getId();
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ForgeBlockEntity.this.progress = value;
                    case 1 -> ForgeBlockEntity.this.fuelTime = value;
                    case 2 -> ForgeBlockEntity.this.maxFuelTime = value;
                    case 3 -> ForgeBlockEntity.this.mode = value;
                    case 4 -> ForgeBlockEntity.this.storage = value;
                }
            }

            @Override
            public int size() {
                return 6;
            }
        };

        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipes.FORGE);
    }

    public ItemStack getRenderStack(ForgeBlockEntity entity) {
        if (this.currentMetal != MetalTypes.EMPTY){
            return entity.currentMetal.getIngot().getDefaultStack();
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    public int getStorage() {
        return storage;
    }

    public MetalTypes getCurrentMetal() {
        return currentMetal;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        if(hasBellows(player.getWorld(), this.pos, player.getWorld().getBlockState(this.pos)) == 1) {
            return new ForgeAlloyingScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
        } else {
            return new ForgeHeatingScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
        }
    }

    public int hasBellows(World world, BlockPos pos, BlockState state){
        Direction direction = state.get(Properties.HORIZONTAL_FACING);
        BlockPos pos1 = pos.offset(direction.rotateYClockwise());
        BlockPos pos2 = pos.offset(direction.rotateYClockwise().getOpposite());

        Direction directionForge = state.get(ForgeBlock.FACING);

        if(world.getBlockState(pos1).isOf(ModDecorativeBlocks.BELLOWS) && world.getBlockState(pos2).isOf(ModDecorativeBlocks.BELLOWS)){
            Direction direction1 = world.getBlockState(pos1).get(BellowsBlock.FACING);
            Direction direction2 = world.getBlockState(pos2).get(BellowsBlock.FACING);
            switch (directionForge){
                case NORTH -> {
                    if (direction1 == Direction.WEST && direction2 == Direction.EAST){
                        return 1;
                    }
                }
                case SOUTH ->{
                    if (direction1 == Direction.EAST && direction2 == Direction.WEST){
                        return 1;
                    }
                }
                case EAST ->{
                    if (direction1 == Direction.NORTH && direction2 == Direction.SOUTH){
                        return 1;
                    }
                }
                case WEST ->{
                    if (direction1 == Direction.SOUTH && direction2 == Direction.NORTH){
                        return 1;
                    }
                }
            }
        }

        return 0;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory, true, registryLookup);
        nbt.putInt(ID + ".progress", this.progress);
        nbt.putInt(ID + ".boost-time", this.boostTime);
        nbt.putInt(ID + ".fuel-time", this.fuelTime);
        nbt.putInt(ID + ".max-fuel-time", this.maxFuelTime);
        nbt.putInt(ID + ".mode", this.mode);
        nbt.putInt(ID + ".storage", this.storage);
        nbt.putString(ID + ".current-metal", this.currentMetal.getName());
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory.clear();
        Inventories.readNbt(nbt, this.inventory, registryLookup);
        this.progress = nbt.getInt(ID + ".progress");
        this.boostTime = nbt.getInt(ID + ".boost-time");
        this.fuelTime = nbt.getInt(ID + ".fuel-time");
        this.maxFuelTime = nbt.getInt(ID + ".max-fuel-time");
        this.mode = nbt.getInt(ID + ".mode");
        this.storage = nbt.getInt(ID + ".storage");
        this.currentMetal = MetalTypes.valueOf(nbt.getString(ID + ".current-metal").toUpperCase());
    }

    public void update() {
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    protected boolean isFuel(ItemStack stack) {
        return this.world.getFuelRegistry().isFuel(stack);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (this.world.getBlockState(this.pos).get(ForgeBlock.PART) == ForgePart.TOP) return false;

        if (mode == 0 && dir != null) return false;

        if (slot == FUEL_SLOT) {
            return isFuel(stack);
        }
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (this.world.getBlockState(this.pos).get(ForgeBlock.PART) == ForgePart.TOP) return false;

        if (dir == Direction.DOWN && slot < 5) return false;

        return true;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack itemStack = getStack(i);
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    public void bellowsBoost() {
        this.boostTime = MAX_BOOST_TIME;
        update();
    }

    public static void outputItemStack(int amount, Vec3d coords, ServerPlayerEntity player){
        BlockPos pos = new BlockPos((int) coords.getX(), (int) coords.getY(), (int) coords.getZ());

        Optional<ForgeBlockEntity> forgeBlockEntity = player.getWorld().getBlockEntity(pos, ModBlockEntities.FORGE);

        ItemStack itemstack = ItemStack.EMPTY;
        if(forgeBlockEntity.isPresent()){
            ForgeBlockEntity entity = forgeBlockEntity.get();

            RegistryWrapper.Impl<ArmorTrimMaterial>  armorTrimMaterialRegistry = entity.getWorld().getRegistryManager().getOrThrow(RegistryKeys.TRIM_MATERIAL);
            RegistryWrapper.Impl<ArmorTrimPattern>  armorTrimPatternRegistry = entity.getWorld().getRegistryManager().getOrThrow(RegistryKeys.TRIM_PATTERN);

            switch (amount){
                case 16 -> {
                    if (entity.currentMetal.getNugget() != null){
                        itemstack = new ItemStack(entity.currentMetal.getNugget());
                        itemstack.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                    }
                }
                case 144 -> {
                    itemstack = new ItemStack(entity.currentMetal.getIngot());
                    itemstack.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                }
                case 288 -> {
                    itemstack = new ItemStack(ModResourceItems.ROD);
                    if (entity.currentMetal.isVanilla()){
                        itemstack.set(DataComponentTypes.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(entity.currentMetal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "smithing_part")))));

                    } else {
                        itemstack.set(DataComponentTypes.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, entity.currentMetal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "smithing_part")))));

                    }itemstack.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                }
                case 432 -> {
                    itemstack = new ItemStack(ModResourceItems.LARGE_ROD);
                    if (entity.currentMetal.isVanilla()){
                        itemstack.set(DataComponentTypes.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(entity.currentMetal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "smithing_part")))));

                    } else {
                        itemstack.set(DataComponentTypes.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, entity.currentMetal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "smithing_part")))));

                    }
                    itemstack.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                }
            }

            if (entity.getStack(OUTPUT_SLOT).isOf(itemstack.getItem()) && !itemstack.isEmpty()){
                if (Objects.equals(entity.getStack(OUTPUT_SLOT).get(DataComponentTypes.TRIM), itemstack.get(DataComponentTypes.TRIM))) {
                    if (amount <= entity.storage && amount > 0) {
                        itemstack.setCount(entity.getStack(OUTPUT_SLOT).getCount() + 1);
                        entity.storage = entity.storage - amount;
                        if (entity.storage == 0) {
                            entity.currentMetal = MetalTypes.EMPTY;
                        }
                        entity.setStack(OUTPUT_SLOT, itemstack);
                        playExtractSound(entity.getWorld(), pos);
                        entity.update();
                    } else {
                        playFailedExtractSound(entity.getWorld(), pos);
                    }
                }else {
                    playFailedExtractSound(entity.getWorld(), pos);
                }
            } else if(entity.getStack(OUTPUT_SLOT).isEmpty() && !itemstack.isEmpty()){
                if (amount <= entity.storage && amount > 0) {
                    itemstack.setCount(entity.getStack(OUTPUT_SLOT).getCount() + 1);
                    entity.storage = entity.storage - amount;
                    if (entity.storage == 0) {
                        entity.currentMetal = MetalTypes.EMPTY;
                    }
                    entity.setStack(OUTPUT_SLOT, itemstack);
                    playExtractSound(entity.getWorld(), pos);
                    entity.update();
                } else {
                    playFailedExtractSound(entity.getWorld(), pos);
                }
            } else {
                playFailedExtractSound(entity.getWorld(), pos);
            }
        }
    }

    private static void playExtractSound(World world, BlockPos pos){
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    private static void playFailedExtractSound(World world, BlockPos pos){
        world.playSound(null, pos, SoundEvents.BLOCK_DECORATED_POT_INSERT_FAIL, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public static void tick(ServerWorld world, BlockPos blockPos, BlockState blockState, ForgeBlockEntity entity) {
        if (blockState.get(ForgeBlock.PART) == ForgePart.TOP) return;

        entity.fuelTime = Math.max(0, entity.fuelTime - 1);
        entity.boostTime = Math.max(0, entity.boostTime - 1);

        boolean progress = false;

        entity.mode = entity.hasBellows(world, blockPos, blockState);
        entity.update();

        if(entity.mode == 1) { // Alloying mode
            if(hasAlloyingRecipe(entity, world)) {
                if(entity.hasFuel(entity)) {
                    int progressValue = 1;
                    if(entity.boostTime > 0) {
                        progressValue = 8;
                    }
                    entity.progress += progressValue;
                    progress = true;
                    entity.update();
                    if(entity.progress >= MAX_PROGRESS) {
                        craftItem(entity, world);
                        entity.progress = 0;
                        entity.update();
                    }
                }
            }
        } else { // Heating mode
            dropExtraItems(entity);
            if(hasHeatingRecipe(entity)) {
                if(entity.hasFuel(entity)) {
                    int progressValue = 2;
                    if(entity.boostTime > 0) {
                        progressValue = 16;
                    }
                    entity.progress += progressValue;
                    progress = true;
                    entity.update();
                    if(entity.progress >= MAX_PROGRESS) {
                        for (int i = 1; i <= 4; i++) {
                            entity.getStack(i).set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                        }
                        entity.progress = 0;
                        entity.update();
                    }
                }
            }
        }

        if (!progress){
            entity.progress = Math.max(entity.progress - 2, 0);
            entity.update();
        }
        boolean isCooking = entity.fuelTime > 0;

        blockState = blockState.with(AbstractFurnaceBlock.LIT, isCooking);
        BlockState blockStateUp = blockState.with(AbstractFurnaceBlock.LIT, isCooking).with(ForgeBlock.PART, ForgePart.TOP);
        world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
        world.setBlockState(blockPos.up(), blockStateUp, Block.NOTIFY_ALL);
    }

    private static void craftItem(ForgeBlockEntity entity, ServerWorld world) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
            if(i != FUEL_SLOT && i != OUTPUT_SLOT) inputs.add(entity.getStack(i));
        }

        RecipeEntry<? extends AlloyingRecipe> match = entity.matchGetter.getFirstMatch(
                new MultipleStackRecipeInput(inputs), world).orElse(null);

        if(match == null) throw new RuntimeException("Somehow... you crafted an item without recipe?!");

        if(hasAlloyingRecipe(entity, world)) {
            for (int i = 1; i <= 4; i++) {
                entity.removeStack(i, 1);
            }
            entity.storage = entity.storage + match.value().amount;
            entity.currentMetal = MetalTypes.valueOf(match.value().output.toUpperCase());
            entity.update();
        }
    }

    private static boolean hasAlloyingRecipe(ForgeBlockEntity entity, ServerWorld world) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
            if(i != FUEL_SLOT && i != OUTPUT_SLOT) inputs.add(entity.getStack(i));
        }
        if(inputs.isEmpty()) return false;

        RecipeEntry<? extends AlloyingRecipe> match = entity.matchGetter.getFirstMatch(
                new MultipleStackRecipeInput(inputs), world).orElse(null);

        if(match == null) return false;

        return canInsertLiquid(entity.storage, entity.currentMetal, match);
    }

    private static void dropExtraItems(ForgeBlockEntity entity) {
    {
        if(entity.getWorld() == null) return;
        for (int i = 0; i < entity.size(); i++)
            if (i != FUEL_SLOT) {
                ItemStack itemStack = entity.getStack(i);
                if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
                    int difference = itemStack.getCount() - 1;
                    if(i == OUTPUT_SLOT){
                        difference = itemStack.getCount();
                    }

                    ItemStack extraStack = itemStack.copy();
                    extraStack.setCount(difference);

                    ItemEntity itemEntity = new ItemEntity(entity.getWorld(),
                            entity.getPos().getX() + 0.5f,
                            entity.getPos().getY() + 1.5f,
                            entity.getPos().getZ() + 0.5f, extraStack);
                    itemEntity.setToDefaultPickupDelay();
                    float f = (float) (Math.random() * 0.15f);
                    float g = (float) (Math.random() * 0.15f);
                    itemEntity.setVelocity(f, 0.25f, g);
                    entity.getWorld().spawnEntity(itemEntity);

                    itemStack.setCount(1);
                    if(i == OUTPUT_SLOT){
                        itemStack.setCount(0);
                    }
                }
            }
        }
    }

    private static boolean hasHeatingRecipe(ForgeBlockEntity entity) {
        List<ItemStack> inputs = new ArrayList<>();
        boolean hasColdItem = false;
        for (int i = 0; i < entity.size(); i++) {
            if(i != FUEL_SLOT && i != OUTPUT_SLOT) {
                Item item = entity.getStack(i).getItem();
                if (entity.getStack(i).isEmpty()){
                    hasColdItem = true;
                } else if(!HotMetalsModel.nuggets.contains(item) && !HotMetalsModel.ingots.contains(item) && !HotMetalsModel.items.contains(item)) {
                    return false; // One of the items cannot be heated
                } else {
                    TemperatureDataComponent temperatureComponent = entity.getStack(i).get(ModDataComponentTypes.TEMPERATURE_DATA);
                    if(temperatureComponent == null || temperatureComponent.temperature() < 100) {
                        hasColdItem = true;
                        inputs.add(entity.getStack(i));
                    }
                }
            }
        }
        if(inputs.isEmpty()) return false;
        else return hasColdItem;
    }

    private boolean hasFuel(ForgeBlockEntity entity) {

        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
        }

        ItemStack fuelStack = inventory1.getStack(FUEL_SLOT);
        if(this.fuelTime > 0) return true;
        else {
            if(isFuel(fuelStack)) {
                getFuel(entity, fuelStack);
                return true;
            } else return false;
        }
    }

    private void getFuel(ForgeBlockEntity entity, ItemStack fuelStack) {
        fuelTime = Math.round((float) this.world.getFuelRegistry().getFuelTicks(fuelStack) / 16);
        maxFuelTime = fuelTime;
        if(fuelStack.isOf(Items.LAVA_BUCKET)) {
            entity.removeStack(FUEL_SLOT);
            entity.setStack(FUEL_SLOT, Items.BUCKET.getDefaultStack());
        }
        else entity.getStack(FUEL_SLOT).decrement(1);
    }

    private static boolean canInsertLiquid(int storage, MetalTypes currentMetal, RecipeEntry<? extends AlloyingRecipe> match) {
        MetalTypes metal = MetalTypes.valueOf(match.value().output.toUpperCase());
        if((storage + match.value().amount) <= MAX_STORAGE){
            if(metal == currentMetal){
                return true;
            } else return currentMetal == MetalTypes.EMPTY;
        } else {
            return false;
        }
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
