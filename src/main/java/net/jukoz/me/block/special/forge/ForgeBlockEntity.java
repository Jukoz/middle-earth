package net.jukoz.me.block.special.forge;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.gui.forge.ForgeScreenHandler;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.jukoz.me.network.packets.C2S.ForgeOutputPacket;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.trim.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.*;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ForgeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, SidedInventory {
    private static final String ID = "forge";
    public static final int MAX_PROGRESS = 1200;
    public static final int MAX_STORAGE = 2304;
    public static final int MAX_BOOST_TIME = 10;
    public static final int FUEL_SLOT = 0;
    public static final int OUTPUT_SLOT = 5;
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(6, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    protected final Map<Item, Integer> fuelTimeMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
    private int progress = 0;
    private int boostTime = 0;
    private int fuelTime = 0;
    private int maxFuelTime = 0;
    private int mode = 0;
    private int storage = 0;

    private MetalTypes currentMetal = MetalTypes.EMPTY;

    //TODO drop ingots and

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FORGE, pos, state);
        System.out.println("x block entity: " + ForgeBlockEntity.this.getPos().getX());
        System.out.println("y block entity: " + ForgeBlockEntity.this.getPos().getY());
        System.out.println("z block entity: " + ForgeBlockEntity.this.getPos().getZ());
        System.out.println("------------------------");
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ForgeBlockEntity.this.progress;
                    case 1 -> ForgeBlockEntity.this.fuelTime;
                    case 2 -> ForgeBlockEntity.this.maxFuelTime;
                    case 3 -> ForgeBlockEntity.this.mode;
                    case 4 -> ForgeBlockEntity.this.storage;
                    case 5 -> ForgeBlockEntity.this.getPos().getX();
                    case 6 -> ForgeBlockEntity.this.getPos().getY();
                    case 7 -> ForgeBlockEntity.this.getPos().getZ();
                    case 8 -> ForgeBlockEntity.this.currentMetal.id;
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
                return 9;
            }
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        System.out.println("x create menu: " + this.propertyDelegate.get(5));
        System.out.println("y create menu: " + this.propertyDelegate.get(6));
        System.out.println("z create menu: " + this.propertyDelegate.get(7));
        System.out.println("------------------------");
        return new ForgeScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public int hasBellows(World world, BlockPos pos, BlockState state){
        Direction direction = state.get(Properties.HORIZONTAL_FACING);
        BlockPos pos1 = pos.offset(direction.rotateYClockwise());
        BlockPos pos2 = pos.offset(direction.rotateYClockwise().getOpposite());

        if(world.getBlockState(pos1).isOf(ModDecorativeBlocks.BELLOWS) && world.getBlockState(pos2).isOf(ModDecorativeBlocks.BELLOWS)){
            return 1;
        } else {
            return 0;
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt(ID + ".progress", progress);
        nbt.putInt(ID + ".boost-time", boostTime);
        nbt.putInt(ID + ".fuel-time", fuelTime);
        nbt.putInt(ID + ".max-fuel-time", maxFuelTime);
        nbt.putInt(ID + ".mode", mode);
        nbt.putInt(ID + ".storage", storage);
        nbt.putString(ID + ".current-metal", currentMetal.name);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt(ID + ".progress");
        boostTime = nbt.getInt(ID + ".boost-time");
        fuelTime = nbt.getInt(ID + ".fuel-time");
        maxFuelTime = nbt.getInt(ID + ".max-fuel-time");
        mode = nbt.getInt(ID + ".mode");
        storage = nbt.getInt(ID + ".storage");
        currentMetal = MetalTypes.valueOf(nbt.getString(ID + ".current-metal").toUpperCase());
    }

    public ItemStack getRenderStack() {
        return this.getStack(OUTPUT_SLOT);
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }


    @Override
    public void markDirty() {
        /* if(world != null && !world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i)); // TODO writeItemStack() no longer exists...
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModNetworks.ITEM_SYNC, data);
            }
        } */
        super.markDirty();
    }

    protected boolean isFuel(Item item) {
        for (Item item1: fuelTimeMap.keySet()) {
            if(item1.equals(item)) return true;
        }
        return false;
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
        if (slot == FUEL_SLOT) {
            return isFuel(stack.getItem());
        }
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot != OUTPUT_SLOT;
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
        inventory.set(slot, stack);
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
        inventory.clear();
    }

    public void bellowsBoost() {
        boostTime = MAX_BOOST_TIME;
    }

    public static void outputItemStack(int amount, Vec3i coords, ServerPlayerEntity player){
        Optional<ForgeBlockEntity> forgeBlockEntity = player.getWorld().getBlockEntity(new BlockPos((int) coords.getX(), (int) coords.getY(), (int) coords.getZ()), ModBlockEntities.FORGE);
        System.out.println("x received: " + coords.getX());
        System.out.println("y received: " + coords.getY());
        System.out.println("z received: " + coords.getZ());
        System.out.println("------------------------");
        //TODO FIX THIS
        ItemStack itemstack = ItemStack.EMPTY;
        if(forgeBlockEntity.isPresent()){
            ForgeBlockEntity entity = forgeBlockEntity.get();

            RegistryWrapper.Impl<ArmorTrimMaterial>  armorTrimMaterialRegistry = entity.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.TRIM_MATERIAL);
            RegistryWrapper.Impl<ArmorTrimPattern>  armorTrimPatternRegistry = entity.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.TRIM_PATTERN);


            switch (amount){
                case 16 -> {
                    itemstack = new ItemStack(entity.currentMetal.nugget);
                    itemstack.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(1000));
                }
                case 144 -> {
                    itemstack = new ItemStack(entity.currentMetal.ingot);
                    itemstack.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(1000));
                }
                case 288 -> {
                    itemstack = new ItemStack(ModResourceItems.ROD);
                    itemstack.set(DataComponentTypes.TRIM, new ArmorTrim(
                            armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, entity.currentMetal.name))),
                            armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "smithing_part")))));
                    itemstack.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(1000));
                }
                case 432 -> {
                    itemstack = new ItemStack(ModResourceItems.LARGE_ROD);
                    itemstack.set(DataComponentTypes.TRIM, new ArmorTrim(
                            armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, entity.currentMetal.name))),
                            armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "smithing_part")))));
                    itemstack.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(1000));
                }
            }

            if (entity.getStack(OUTPUT_SLOT).isOf(itemstack.getItem())){
                if (entity.getStack(OUTPUT_SLOT).get(DataComponentTypes.TRIM) == itemstack.get(DataComponentTypes.TRIM)) {
                    if (amount <= entity.storage) {
                        itemstack.setCount(entity.getStack(OUTPUT_SLOT).getCount() + 1);
                        entity.storage = entity.storage - amount;
                        if (entity.storage == 0) {
                            entity.currentMetal = MetalTypes.EMPTY;
                        }
                        entity.setStack(OUTPUT_SLOT, itemstack);
                    }
                }
            } else if(entity.getStack(OUTPUT_SLOT).isEmpty()){
                if (amount <= entity.storage) {
                    itemstack.setCount(entity.getStack(OUTPUT_SLOT).getCount() + 1);
                    entity.storage = entity.storage - amount;
                    if (entity.storage == 0) {
                        entity.currentMetal = MetalTypes.EMPTY;
                    }
                    entity.setStack(OUTPUT_SLOT, itemstack);
                }
            }
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, ForgeBlockEntity entity) {
        if(world.isClient()) return;

        entity.fuelTime = Math.max(0, entity.fuelTime - 1);
        entity.boostTime = Math.max(0, entity.boostTime - 1);
        boolean progress = false;
        entity.mode = entity.hasBellows(world, blockPos, blockState);
        if(hasRecipe(entity)) {
            if(entity.hasFuel(entity)) {
                int progressValue = 1;
                if(entity.boostTime > 0) {
                    progressValue = 8;
                }
                entity.progress += progressValue;
                progress = true;
                markDirty(world, blockPos, blockState); // Reloads the origin in this chunk, for sync & saving.
                if(entity.progress >= MAX_PROGRESS) {
                    craftItem(entity);
                    entity.progress = 0;
                }
            }
        }
        if (!progress){
            entity.progress = Math.max(entity.progress - 2, 0);
            markDirty(world, blockPos, blockState);
        }
        boolean isCooking = entity.fuelTime > 0;
        blockState = blockState.with(AbstractFurnaceBlock.LIT, isCooking);
        world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
    }

    private static void craftItem(ForgeBlockEntity entity) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
            if(i != FUEL_SLOT && i != OUTPUT_SLOT) inputs.add(entity.getStack(i));
        }

        Optional<RecipeEntry<AlloyingRecipe>> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(AlloyingRecipe.Type.INSTANCE, new MultipleStackRecipeInput(inputs), entity.getWorld());
        if(match.isEmpty()) throw new RuntimeException("Somehow... you crafted an item without recipe?!");

        if(hasRecipe(entity)) {
            for (int i = 1; i <= 4; i++) {
                entity.removeStack(i, 1);
            }
            entity.storage = entity.storage + match.get().value().output.getCount() * 144;
            entity.currentMetal = MetalTypes.valueOf(Registries.ITEM.getId(match.get().value().output.getItem()).getPath().replaceAll("_ingot", "").toUpperCase());
            entity.markDirty();
        }
    }

    private static boolean hasRecipe(ForgeBlockEntity entity) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
            if(i != FUEL_SLOT && i != OUTPUT_SLOT) inputs.add(entity.getStack(i));
        }
        if(inputs.isEmpty()) return false;

        MultipleStackRecipeInput input = new MultipleStackRecipeInput(inputs);
        Optional<RecipeEntry<AlloyingRecipe>> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(AlloyingRecipe.Type.INSTANCE, input, entity.getWorld());

        if(match.isEmpty()) return false;

        return canInsertAmountIntoOutput(inventory1, match.get().value().output.getCount())
                && canInsertLiquid(entity.storage, entity.currentMetal, match);
    }

    private boolean hasFuel(ForgeBlockEntity entity) {

        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
        }

        Item fuelItem = inventory1.getStack(FUEL_SLOT).getItem();
        if(this.fuelTime > 0) return true;
        else {
            if(isFuel(fuelItem)) {
                getFuel(entity, fuelItem);
                return true;
            } else return false;
        }
    }

    private void getFuel(ForgeBlockEntity entity, Item fuelItem) {
        fuelTime = Math.round((float) fuelTimeMap.get(fuelItem) / 16);
        maxFuelTime = fuelTime;
        if(fuelItem == Items.LAVA_BUCKET) {
            entity.removeStack(FUEL_SLOT);
            entity.setStack(FUEL_SLOT, Items.BUCKET.getDefaultStack());
        }
        else entity.getStack(FUEL_SLOT).decrement(1);
    }

    private static boolean canInsertAmountIntoOutput(SimpleInventory inventory1, int count) {
        int maxCount = inventory1.getStack(OUTPUT_SLOT).getMaxCount();
        if(inventory1.getStack(OUTPUT_SLOT) == ItemStack.EMPTY) maxCount = 64;
        int newCount = inventory1.getStack(OUTPUT_SLOT).getCount() + (count- 1);
        return maxCount > newCount;
    }

    private static boolean canInsertLiquid(int storage, MetalTypes currentMetal, Optional<RecipeEntry<AlloyingRecipe>> match) {
        MetalTypes metal = MetalTypes.valueOf(Registries.ITEM.getId(match.get().value().output.getItem()).getPath().replaceAll("_ingot", "").toUpperCase());
        if((storage + match.get().value().output.getCount() * 144) <= MAX_STORAGE){
            if(metal == currentMetal){
                return true;
            } else return currentMetal == MetalTypes.EMPTY;
        } else {
            return false;
        }
    }

    public enum MetalTypes implements StringIdentifiable {
        EMPTY(-1, "empty", null, null, false),

        COPPER(0, "copper", Items.COPPER_INGOT, null, true),
        TIN(1, "tin", ModResourceItems.TIN_INGOT, ModResourceItems.TIN_NUGGET, true),

        BRONZE(2, "bronze", ModResourceItems.BRONZE_INGOT, ModResourceItems.BRONZE_NUGGET, false),
        CRUDE(3, "crude", ModResourceItems.CRUDE_INGOT, ModResourceItems.CRUDE_NUGGET, false),

        LEAD(4, "lead", ModResourceItems.LEAD_INGOT, ModResourceItems.LEAD_NUGGET, false),
        SILVER(5, "silver", ModResourceItems.SILVER_INGOT, ModResourceItems.SILVER_NUGGET, false),
        IRON(6, "iron", Items.IRON_INGOT, Items.IRON_NUGGET, true),

        STEEL(7, "steel", ModResourceItems.STEEL_INGOT, ModResourceItems.STEEL_NUGGET, false),
        BURZUM_STEEL(8, "burzum_steel", ModResourceItems.BURZUM_STEEL_INGOT, ModResourceItems.BURZUM_STEEL_NUGGET, false),
        EDHEL_STEEL(9, "edhel_steel", ModResourceItems.EDHEL_STEEL_INGOT, ModResourceItems.EDHEL_STEEL_NUGGET, false),
        KHAZAD_STEEL(10, "khazad_steel", ModResourceItems.KHAZAD_STEEL_INGOT, ModResourceItems.KHAZAD_STEEL_NUGGET, false),

        MORGUL_STEEL(11, "morgul_steel", ModResourceItems.MORGUL_STEEL_INGOT, ModResourceItems.MORGUL_STEEL_NUGGET, false),
        MITHRIL(12, "mithril", ModResourceItems.MITHRIL_INGOT, ModResourceItems.MITHRIL_NUGGET, false),

        NETHERITE(13, "netherite", Items.NETHERITE_INGOT, null, true),
        ;

        private final int id;
        private final String name;
        private final Item ingot;
        private final Item nugget;
        private final boolean vanilla;

        MetalTypes(int id,String name, Item ingot, Item nugget, boolean vanilla){
            this.id = id;
            this.name = name;
            this.ingot = ingot;
            this.nugget = nugget;
            this.vanilla = vanilla;
        }

        @Override
        public String asString() {
            return name;
        }

        public int getId() {
            return id;
        }

        public static ForgeBlockEntity.MetalTypes getValue(int value) {
            for(ForgeBlockEntity.MetalTypes e: ForgeBlockEntity.MetalTypes.values()) {
                if(e.id == value) {
                    return e;
                }
            }
            return null;
        }
    }
}
