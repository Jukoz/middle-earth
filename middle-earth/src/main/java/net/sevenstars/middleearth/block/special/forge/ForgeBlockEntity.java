package net.sevenstars.middleearth.block.special.forge;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlock;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreenHandler;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ForgeBlockEntity extends BlockEntity implements ExtendedMenuProviderME, WorldlyContainer {
    private static final String ID = "forge";
    public static final int MAX_PROGRESS = 1200;
    public static final int MAX_STORAGE = 2304;
    public static final int MAX_BOOST_TIME = 10;
    public static final int FUEL_SLOT = 0;
    public static final int OUTPUT_SLOT = 5;
    private final NonNullList<ItemStack> inventory =
            NonNullList.withSize(6, ItemStack.EMPTY);
    protected final ContainerData propertyDelegate;
    private int progress = 0;
    private int boostTime = 0;
    private int fuelTime = 0;
    private int maxFuelTime = 0;
    private int mode = 0;
    private int storage = 0;

    private final RecipeManager.CachedCheck<MultipleStackRecipeInput, ? extends AlloyingRecipe> matchGetter;

    private MetalTypes currentMetal = MetalTypes.EMPTY;
    @Nullable
    private RecipeHolder<? extends AlloyingRecipe> cachedAlloyingRecipe;
    private boolean cachedHasHeatingRecipe;
    private boolean processingInputsDirty = true;
    private boolean clientSyncPending;

    //TODO melting smithing parts
    //TODO convert metals to registry, enum constant datagen if no registry
    //TODO custom metal trim data component with palette

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FORGE, pos, state);
        this.propertyDelegate = new ContainerData() {
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
            public int getCount() {
                return 6;
            }
        };

        this.matchGetter = RecipeManager.createCheck(RecipesME.FORGE);
    }

    public ItemStack getRenderStack(ForgeBlockEntity entity) {
        if (this.currentMetal != MetalTypes.EMPTY){
            return entity.currentMetal.getIngot().getDefaultInstance();
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    public int getStorage() {
        return storage;
    }

    public MetalTypes getCurrentMetal() {
        return currentMetal;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new ForgeAlloyingScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public int hasBellows(Level world, BlockPos pos, BlockState state){
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockPos pos1 = pos.relative(direction.getClockWise());
        BlockPos pos2 = pos.relative(direction.getClockWise().getOpposite());

        Direction directionForge = state.getValue(ForgeBlock.FACING);

        if(world.getBlockState(pos1).is(ModDecorativeBlocks.BELLOWS) && world.getBlockState(pos2).is(ModDecorativeBlocks.BELLOWS)){
            Direction direction1 = world.getBlockState(pos1).getValue(BellowsBlock.FACING);
            Direction direction2 = world.getBlockState(pos2).getValue(BellowsBlock.FACING);
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
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.inventory, true, registries);
        tag.putInt(ID + ".progress", this.progress);
        tag.putInt(ID + ".boost-time", this.boostTime);
        tag.putInt(ID + ".fuel-time", this.fuelTime);
        tag.putInt(ID + ".max-fuel-time", this.maxFuelTime);
        tag.putInt(ID + ".mode", this.mode);
        tag.putInt(ID + ".storage", this.storage);
        tag.putString(ID + ".current-metal", this.currentMetal.getName());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.clear();
        ContainerHelper.loadAllItems(tag, this.inventory, registries);
        this.progress = tag.getInt(ID + ".progress");
        this.boostTime = tag.getInt(ID + ".boost-time");
        this.fuelTime = tag.getInt(ID + ".fuel-time");
        this.maxFuelTime = tag.getInt(ID + ".max-fuel-time");
        this.mode = tag.getInt(ID + ".mode");
        this.storage = tag.getInt(ID + ".storage");
        String metalName = tag.contains(ID + ".current-metal") ? tag.getString(ID + ".current-metal") : "bronze";
        this.currentMetal = MetalTypes.fromValue(metalName.toLowerCase());
        this.processingInputsDirty = true;
    }

    private void markPersistedChanged() {
        super.setChanged();
    }

    private void markClientVisibleChanged() {
        this.markPersistedChanged();
        this.clientSyncPending = true;
    }

    private void markInventoryChanged(int slot) {
        if (slot >= 1 && slot <= 4) {
            this.processingInputsDirty = true;
        }
        this.markClientVisibleChanged();
    }

    @Override
    public void setChanged() {
        this.processingInputsDirty = true;
        this.markClientVisibleChanged();
    }

    private void flushClientSync(ServerLevel world) {
        if (this.clientSyncPending) {
            BlockState state = this.getBlockState();
            world.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_CLIENTS);
            this.clientSyncPending = false;
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }

    public void setInventory(NonNullList<ItemStack> inventory) {
        boolean changed = false;
        for (int i = 0; i < Math.min(inventory.size(), this.inventory.size()); i++) {
            ItemStack stack = inventory.get(i);
            if (!ItemStack.matches(this.inventory.get(i), stack)) {
                this.inventory.set(i, stack);
                changed = true;
            }
        }
        if (changed) {
            this.processingInputsDirty = true;
            this.markClientVisibleChanged();
        }
    }

    protected boolean isFuel(ItemStack stack) {
        return stack.getBurnTime(RecipeType.SMELTING) > 0;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        if (this.level.getBlockState(this.worldPosition).getValue(ForgeBlock.PART) == ForgePart.TOP) return false;

        if (mode == 0 && dir != null) return false;

        if (slot == FUEL_SLOT) {
            return isFuel(stack);
        }
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        if (this.level.getBlockState(this.worldPosition).getValue(ForgeBlock.PART) == ForgePart.TOP) return false;

        if (dir == Direction.DOWN && slot < 5) return false;

        return true;
    }

    @Override
    public int getContainerSize() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack removed = ContainerHelper.removeItem(this.inventory, slot, amount);
        if (!removed.isEmpty()) {
            this.markInventoryChanged(slot);
        }
        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(inventory, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        if (!ItemStack.matches(this.inventory.get(slot), stack)) {
            this.inventory.set(slot, stack);
            this.markInventoryChanged(slot);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        if (!this.isEmpty()) {
            this.inventory.clear();
            this.processingInputsDirty = true;
            this.markClientVisibleChanged();
        }
    }

    public void bellowsBoost() {
        if (this.boostTime != MAX_BOOST_TIME) {
            this.boostTime = MAX_BOOST_TIME;
            this.markPersistedChanged();
        }
    }

    public static void switchMode(Vec3 coords, ServerPlayer player){
        BlockPos pos = new BlockPos((int) coords.x(), (int) coords.y(), (int) coords.z());
        Optional<ForgeBlockEntity> forgeBlockEntity = player.level().getBlockEntity(pos, ModBlockEntities.FORGE);

        if(forgeBlockEntity.isPresent()){
            ForgeBlockEntity entity = forgeBlockEntity.get();
            if (entity.mode == 1){
                entity.mode = 0;
            } else if (entity.mode == 0) {
                entity.mode = 1;
            }
            entity.processingInputsDirty = true;
            entity.markClientVisibleChanged();
        }
    }

    public static void outputItemStack(int amount, Vec3 coords, ServerPlayer player, int mode){
        BlockPos pos = new BlockPos((int) coords.x(), (int) coords.y(), (int) coords.z());

        Optional<ForgeBlockEntity> forgeBlockEntity = player.level().getBlockEntity(pos, ModBlockEntities.FORGE);

        ItemStack itemstack = ItemStack.EMPTY;
        if(forgeBlockEntity.isPresent()){
            ForgeBlockEntity entity = forgeBlockEntity.get();

            if (entity.getItem(OUTPUT_SLOT).getMaxStackSize() <= entity.getItem(OUTPUT_SLOT).getCount()) return;

            HolderLookup.RegistryLookup<TrimMaterial>  armorTrimMaterialRegistry = entity.getLevel().registryAccess().lookupOrThrow(Registries.TRIM_MATERIAL);
            HolderLookup.RegistryLookup<TrimPattern>  armorTrimPatternRegistry = entity.getLevel().registryAccess().lookupOrThrow(Registries.TRIM_PATTERN);

            switch (amount){
                case 16 -> {
                    if(entity.currentMetal.getIngot().equals(ResourceItemsME.THERAPOD_NUGGET)) {
                        itemstack = new ItemStack(ResourceItemsME.PTEROSAUR_NUGGET);
                        FoodProperties foodComponent = new FoodProperties.Builder()
                                .nutrition(1).saturationModifier(0.5F).build();
                        itemstack.set(DataComponents.FOOD, foodComponent);
                        itemstack.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                    } else if (entity.currentMetal.getNugget() != null){
                        itemstack = new ItemStack(entity.currentMetal.getNugget());
                        itemstack.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                    }
                }
                case 144 -> {
                    if(entity.currentMetal.getIngot().equals(ResourceItemsME.THERAPOD_NUGGET)) {
                        itemstack = new ItemStack(ResourceItemsME.THERAPOD_NUGGET);
                        FoodProperties foodComponent = new FoodProperties.Builder()
                                .nutrition(7).saturationModifier(0.8F).build();
                        itemstack.set(DataComponents.FOOD, foodComponent);
                    } else {
                        itemstack = new ItemStack(entity.currentMetal.getIngot());
                    }
                    itemstack.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                }
                case 288 -> {
                    itemstack = new ItemStack(ResourceItemsME.ROD);
                    if(mode == 4) itemstack = new ItemStack(ResourceItemsME.ARMOR_PLATE);

                    if(entity.currentMetal.getIngot().equals(ResourceItemsME.THERAPOD_NUGGET)) {
                        if(mode == 4) itemstack = new ItemStack(ResourceItemsME.THYREOPHORAN_NUGGET);
                        else itemstack = new ItemStack(ResourceItemsME.CERATOPSIAN_NUGGET);
                        FoodProperties foodComponent = new FoodProperties.Builder()
                                .nutrition(10).saturationModifier(0.8F).build();
                        itemstack.set(DataComponents.FOOD, foodComponent);
                    }
                    else if(entity.currentMetal.isVanilla()) {
                        itemstack.set(DataComponents.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.parse(entity.currentMetal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "smithing_part")))));
                    } else {
                        itemstack.set(DataComponents.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, entity.currentMetal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "smithing_part")))));

                    }itemstack.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                }
                case 432 -> {
                    itemstack = new ItemStack(ResourceItemsME.LARGE_ROD);
                    if(entity.currentMetal.getIngot().equals(ResourceItemsME.THERAPOD_NUGGET)) {
                        itemstack = new ItemStack(ResourceItemsME.SAUROPOD_NUGGET);
                        FoodProperties foodComponent = new FoodProperties.Builder()
                                .nutrition(14).saturationModifier(0.85F).build();
                        itemstack.set(DataComponents.FOOD, foodComponent);
                    } else if (entity.currentMetal.isVanilla()){
                        itemstack.set(DataComponents.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.parse(entity.currentMetal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "smithing_part")))));

                    } else {
                        itemstack.set(DataComponents.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, entity.currentMetal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "smithing_part")))));

                    }
                    itemstack.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                }
            }

            if (entity.getItem(OUTPUT_SLOT).is(itemstack.getItem()) && !itemstack.isEmpty()){
                if (Objects.equals(entity.getItem(OUTPUT_SLOT).get(DataComponents.TRIM), itemstack.get(DataComponents.TRIM))) {
                    if (amount <= entity.storage && amount > 0) {
                        itemstack.setCount(entity.getItem(OUTPUT_SLOT).getCount() + 1);
                        entity.storage = entity.storage - amount;
                        if (entity.storage == 0) {
                            entity.currentMetal = MetalTypes.EMPTY;
                        }
                        entity.setItem(OUTPUT_SLOT, itemstack);
                        playExtractSound(entity.getLevel(), pos);
                    } else {
                        playFailedExtractSound(entity.getLevel(), pos);
                    }
                }else {
                    playFailedExtractSound(entity.getLevel(), pos);
                }
            } else if(entity.getItem(OUTPUT_SLOT).isEmpty() && !itemstack.isEmpty()){
                if (amount <= entity.storage && amount > 0) {
                    itemstack.setCount(entity.getItem(OUTPUT_SLOT).getCount() + 1);
                    entity.storage = entity.storage - amount;
                    if (entity.storage == 0) {
                        entity.currentMetal = MetalTypes.EMPTY;
                    }
                    entity.setItem(OUTPUT_SLOT, itemstack);
                    playExtractSound(entity.getLevel(), pos);
                } else {
                    playFailedExtractSound(entity.getLevel(), pos);
                }
            } else {
                playFailedExtractSound(entity.getLevel(), pos);
            }
        }
    }

    private static void playExtractSound(Level world, BlockPos pos){
        world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    private static void playFailedExtractSound(Level world, BlockPos pos){
        world.playSound(null, pos, SoundEvents.DECORATED_POT_INSERT_FAIL, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void tick(ServerLevel world, BlockPos blockPos, BlockState blockState, ForgeBlockEntity entity) {
        if (blockState.getValue(ForgeBlock.PART) == ForgePart.TOP) {
            entity.flushClientSync(world);
            return;
        }

        boolean persistedStateChanged = false;
        if (entity.fuelTime > 0) {
            entity.fuelTime--;
            persistedStateChanged = true;
        }
        if (entity.boostTime > 0) {
            entity.boostTime--;
            persistedStateChanged = true;
        }

        entity.refreshProcessingCache(world);
        boolean madeProgress = false;

        if (entity.mode == 1) {
            RecipeHolder<? extends AlloyingRecipe> recipe = entity.cachedAlloyingRecipe;
            if (recipe != null
                    && canInsertLiquid(entity.storage, entity.currentMetal, recipe)
                    && entity.hasFuel()) {
                entity.progress += entity.boostTime > 0 ? 8 : 1;
                madeProgress = true;
                persistedStateChanged = true;
                if (entity.progress >= MAX_PROGRESS) {
                    craftItem(entity, world, recipe);
                    entity.progress = 0;
                }
            }
        } else if (entity.cachedHasHeatingRecipe && entity.hasFuel()) {
            entity.progress += entity.boostTime > 0 ? 16 : 2;
            madeProgress = true;
            persistedStateChanged = true;
            if (entity.progress >= MAX_PROGRESS) {
                for (int i = 1; i <= 4; i++) {
                    ItemStack stack = entity.getItem(i);
                    if (!stack.isEmpty()) {
                        stack.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(100));
                    }
                }
                entity.progress = 0;
                entity.processingInputsDirty = true;
            }
        }

        if (!madeProgress && entity.progress > 0) {
            entity.progress = Math.max(entity.progress - 2, 0);
            persistedStateChanged = true;
        }

        if (persistedStateChanged) {
            entity.markPersistedChanged();
        }

        boolean isLit = entity.fuelTime > 0;
        if (blockState.getValue(AbstractFurnaceBlock.LIT) != isLit) {
            BlockState lowerState = blockState.setValue(AbstractFurnaceBlock.LIT, isLit);
            BlockState upperState = lowerState.setValue(ForgeBlock.PART, ForgePart.TOP);
            world.setBlock(blockPos, lowerState, Block.UPDATE_ALL);
            world.setBlock(blockPos.above(), upperState, Block.UPDATE_ALL);
        }

        entity.flushClientSync(world);
    }

    private static void craftItem(
            ForgeBlockEntity entity,
            ServerLevel world,
            RecipeHolder<? extends AlloyingRecipe> recipe
    ) {
        ExperienceOrb.award(world, entity.getBlockPos().getCenter().add(0, 1, 0), recipe.value().getXp());

        for (int i = 1; i <= 4; i++) {
            entity.getItem(i).shrink(1);
        }
        entity.storage += recipe.value().amount;
        entity.currentMetal = MetalTypes.fromValue(recipe.value().output.toLowerCase());
        entity.processingInputsDirty = true;
        entity.markClientVisibleChanged();
    }

    private void refreshProcessingCache(ServerLevel world) {
        if (!this.processingInputsDirty) {
            return;
        }

        if (this.mode == 1) {
            this.cachedHasHeatingRecipe = false;
            if (this.hasAnyProcessingInput()) {
                List<ItemStack> inputs = List.of(
                        this.getItem(1),
                        this.getItem(2),
                        this.getItem(3),
                        this.getItem(4)
                );
                this.cachedAlloyingRecipe = this.matchGetter
                        .getRecipeFor(new MultipleStackRecipeInput(inputs), world)
                        .orElse(null);
            } else {
                this.cachedAlloyingRecipe = null;
            }
        } else {
            this.cachedAlloyingRecipe = null;
            if (dropExtraItems(this)) {
                this.markClientVisibleChanged();
            }
            this.cachedHasHeatingRecipe = this.computeHasHeatingRecipe();
        }

        this.processingInputsDirty = false;
    }

    private boolean hasAnyProcessingInput() {
        for (int i = 1; i <= 4; i++) {
            if (!this.getItem(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean dropExtraItems(ForgeBlockEntity entity) {
        if (entity.getLevel() == null) {
            return false;
        }

        boolean changed = false;
        for (int i = 0; i < entity.getContainerSize(); i++)
            if (i != FUEL_SLOT) {
                ItemStack itemStack = entity.getItem(i);
                if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
                    int difference = itemStack.getCount() - 1;
                    if(i == OUTPUT_SLOT){
                        difference = itemStack.getCount();
                    }

                    ItemStack extraStack = itemStack.copy();
                    extraStack.setCount(difference);

                    ItemEntity itemEntity = new ItemEntity(entity.getLevel(),
                            entity.getBlockPos().getX() + 0.5f,
                            entity.getBlockPos().getY() + 1.5f,
                            entity.getBlockPos().getZ() + 0.5f, extraStack);
                    itemEntity.setDefaultPickUpDelay();
                    float f = (float) (entity.getLevel().getRandom().nextDouble() * 0.15f);
                    float g = (float) (entity.getLevel().getRandom().nextDouble() * 0.15f);
                    itemEntity.setDeltaMovement(f, 0.25f, g);
                    entity.getLevel().addFreshEntity(itemEntity);

                    itemStack.setCount(1);
                    if(i == OUTPUT_SLOT){
                        itemStack.setCount(0);
                    }
                    changed = true;
                }
            }
        return changed;
    }

    private boolean computeHasHeatingRecipe() {
        boolean hasColdItem = false;
        for (int i = 0; i < this.getContainerSize(); i++) {
            if(i != FUEL_SLOT && i != OUTPUT_SLOT) {
                ItemStack stack = this.getItem(i);
                if (!stack.isEmpty()) {
                    Item item = stack.getItem();
                    if (!HotMetalsModel.nuggets.contains(item)
                            && !HotMetalsModel.ingots.contains(item)
                            && !HotMetalsModel.items.contains(item)) {
                        return false;
                    }
                    TemperatureDataComponent temperatureComponent = stack.get(DataComponentTypesME.TEMPERATURE_DATA);
                    if(temperatureComponent == null || temperatureComponent.temperature() < 100) {
                        hasColdItem = true;
                    }
                }
            }
        }
        return hasColdItem;
    }

    private boolean hasFuel() {
        if (this.fuelTime > 0) {
            return true;
        }

        ItemStack fuelStack = this.getItem(FUEL_SLOT);
        if (!this.isFuel(fuelStack)) {
            return false;
        }

        this.fuelTime = Math.round((float) fuelStack.getBurnTime(RecipeType.SMELTING) / 16);
        this.maxFuelTime = this.fuelTime;
        if (fuelStack.is(Items.LAVA_BUCKET)) {
            this.inventory.set(FUEL_SLOT, Items.BUCKET.getDefaultInstance());
        } else {
            fuelStack.shrink(1);
        }
        this.markInventoryChanged(FUEL_SLOT);
        return true;
    }

    private static boolean canInsertLiquid(int storage, MetalTypes currentMetal, RecipeHolder<? extends AlloyingRecipe> match) {
        var value = match.value().output.toLowerCase();
        MetalTypes metal = MetalTypes.fromValue(value);
        if((storage + match.value().amount) <= MAX_STORAGE){
            if(metal == currentMetal){
                return true;
            } else return currentMetal == MetalTypes.EMPTY;
        } else {
            return false;
        }
    }

    @Override
    public void writeOpeningData(RegistryFriendlyByteBuf buffer) {
        BlockPos.STREAM_CODEC.encode(buffer, worldPosition);
    }
}
