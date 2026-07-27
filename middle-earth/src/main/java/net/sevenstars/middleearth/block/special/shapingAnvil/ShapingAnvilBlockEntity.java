package net.sevenstars.middleearth.block.special.shapingAnvil;

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
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShapingAnvilBlockEntity extends BlockEntity implements ExtendedMenuProviderME, WorldlyContainer {
    private static final String ID = "shaping_anvil";

    public int outputIndex = 0;
    public int maxOutputIndex = 0;

    public final NonNullList<ItemStack> inventory =
            NonNullList.withSize(1, ItemStack.EMPTY);

    protected final ContainerData propertyDelegate;
    private List<RecipeHolder<AnvilShapingRecipe>> cachedRecipes = List.of();
    private boolean recipeCacheDirty = true;
    private boolean clientSyncPending;

    //TODO make work in creative somehow

    public ShapingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TREATED_ANVIL, pos, state);

        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ShapingAnvilBlockEntity.this.outputIndex;
                    case 1 -> ShapingAnvilBlockEntity.this.maxOutputIndex;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ShapingAnvilBlockEntity.this.outputIndex = value;
                    case 1 -> ShapingAnvilBlockEntity.this.maxOutputIndex = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public ShapingAnvilBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ShapingAnvilBlockEntity.this.outputIndex;
                    case 1 -> ShapingAnvilBlockEntity.this.maxOutputIndex;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ShapingAnvilBlockEntity.this.outputIndex = value;
                    case 1 -> ShapingAnvilBlockEntity.this.maxOutputIndex = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public static void updateIndex(int index, Vec3 coords, ServerPlayer player){
        BlockPos pos = new BlockPos((int) coords.x(), (int) coords.y(), (int) coords.z());
        BlockEntity shapingAnvilBlockEntity = player.level().getBlockEntity(pos);

        if(shapingAnvilBlockEntity instanceof ShapingAnvilBlockEntity entity){
            if(index >= 0 && entity.outputIndex != index) {
                entity.outputIndex = index;
                entity.markPersistedChanged();
            }
        }
    }

    public ItemStack getRenderStack(ShapingAnvilBlockEntity entity) {
        return entity.getItem(0);
    }

    public void bonk(ShapingAnvilBlockEntity entity, ServerLevel world){
        ItemStack input = entity.getItem(0);

        List<RecipeHolder<AnvilShapingRecipe>> match = entity.getMatchingRecipes(world);

        if (!match.isEmpty() && input.get(DataComponentTypesME.TEMPERATURE_DATA) != null){
            int temperature = input.get(DataComponentTypesME.TEMPERATURE_DATA).temperature();

            entity.getLevel().playSound(null, worldPosition, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1.5f, 1.0f - (float) temperature / 1000);

            int minRandProgress = 7;
            int maxRandProgress = 14;

            if (input.getMaxDamage() == 0 && input.getDamageValue() == 0){
                input.set(DataComponents.MAX_DAMAGE, match.get(entity.outputIndex).value().getAmount());
                input.setDamageValue(match.get(entity.outputIndex).value().getAmount()
                        - (int) (world.getRandom().nextDouble() * (maxRandProgress - minRandProgress) + minRandProgress));
            } else{
                input.setDamageValue(input.getDamageValue()
                        - (int) (world.getRandom().nextDouble() * (maxRandProgress - minRandProgress) + minRandProgress));
            }

            Level serverWorld = this.getLevel();
            if (serverWorld instanceof ServerLevel) {
                ((ServerLevel)serverWorld).sendParticles(ModParticleTypes.ANVIL_SPARK_PARTICLE, worldPosition.getX()+ 0.5f, worldPosition.getY() + 1.0f, worldPosition.getZ() + 0.5f, Math.max(temperature / 10, 3), 0.0, 0.0, 0.0, 0.0);
            }

            int minRandTemperature = 10;
            int maxRandTemperature = 18;
            int value = (int) (world.getRandom().nextDouble()
                    * (maxRandTemperature - minRandTemperature) + minRandTemperature);

            if ((input.get(DataComponentTypesME.TEMPERATURE_DATA).temperature() - value) <= 0){
                input.remove(DataComponentTypesME.TEMPERATURE_DATA);
            } else {
                input.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(DataComponentTypesME.TEMPERATURE_DATA).temperature() - value));
            }
            entity.markInventoryChanged();
            HolderLookup.RegistryLookup<TrimMaterial>  armorTrimMaterialRegistry = entity.getLevel().registryAccess().lookupOrThrow(Registries.TRIM_MATERIAL);
            HolderLookup.RegistryLookup<TrimPattern>  armorTrimPatternRegistry = entity.getLevel().registryAccess().lookupOrThrow(Registries.TRIM_PATTERN);


            if (input.getDamageValue() == 0){
                int xp = 2;
                if(input.getItem() == ResourceItemsME.ROD || input.getItem() == ResourceItemsME.ARMOR_PLATE) xp = 4;
                if(input.getItem() == ResourceItemsME.LARGE_ROD) xp = 7;
                ExperienceOrb.award(world, entity.getBlockPos().getCenter().add(0, 1, 0), xp);

                ItemStack output = match.get(entity.outputIndex).value().assemble(new SingleRecipeInput(input), entity.level.registryAccess());

                if(input.get(DataComponents.TRIM) != null){
                    output.set(DataComponents.TRIM, input.get(DataComponents.TRIM));
                } else{
                    MetalTypes metal = MetalTypes.EMPTY;
                    if(input.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "ingot_shaping")))) {
                        metal = MetalTypes.getMetalByIngot(input.getItem());
                    }else if(input.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "nugget_shaping")))) {
                        metal = MetalTypes.getMetalByNugget(input.getItem());
                    }
                    if (metal.isVanilla()){
                        output.set(DataComponents.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.parse(metal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,"smithing_part")))));
                    } else {
                        output.set(DataComponents.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, metal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "smithing_part")))));
                    }
                }
                if (input.get(DataComponentTypesME.TEMPERATURE_DATA) != null){
                    output.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(DataComponentTypesME.TEMPERATURE_DATA).temperature()));
                }
                entity.getLevel().playSound(null, worldPosition, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                entity.setItem(0, output);
            }
        } else {
            Level serverWorld = this.getLevel();
            if (serverWorld instanceof ServerLevel) {
                ((ServerLevel)serverWorld).sendParticles(ModParticleTypes.ANVIL_SPARK_PARTICLE, worldPosition.getX()+ 0.5f, worldPosition.getY() + 1.0f, worldPosition.getZ() + 0.5f, 2, 0.0, 0.0, 0.0, 0.0);
            }
            entity.getLevel().playSound(null, worldPosition, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1.5f, 1.0f);
        }
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, ShapingAnvilBlockEntity entity) {
        if (entity.recipeCacheDirty) {
            entity.refreshRecipeCache(world);
        }
        entity.flushClientSync(world);
    }

    private List<RecipeHolder<AnvilShapingRecipe>> getMatchingRecipes(Level world) {
        if (this.recipeCacheDirty) {
            this.refreshRecipeCache(world);
        }
        return this.cachedRecipes;
    }

    private void refreshRecipeCache(Level world) {
        ItemStack input = this.getItem(0);
        if (input.isEmpty()) {
            this.cachedRecipes = List.of();
        } else {
            RecipeManager recipeManager = world.getRecipeManager();
            this.cachedRecipes = recipeManager.getRecipesFor(
                    AnvilShapingRecipe.Type.INSTANCE,
                    new SingleRecipeInput(input),
                    world
            );
        }
        this.recipeCacheDirty = false;

        int newMaxOutputIndex = this.cachedRecipes.isEmpty() ? 0 : this.cachedRecipes.size() - 1;
        int newOutputIndex = this.cachedRecipes.isEmpty()
                ? this.outputIndex
                : Math.min(this.outputIndex, newMaxOutputIndex);
        if (this.maxOutputIndex != newMaxOutputIndex || this.outputIndex != newOutputIndex) {
            this.maxOutputIndex = newMaxOutputIndex;
            this.outputIndex = newOutputIndex;
            this.markPersistedChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.inventory, true, registries);
        tag.putInt("current-index", this.outputIndex);
        tag.putInt("current-max-index", this.maxOutputIndex);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.clear();
        ContainerHelper.loadAllItems(tag, this.inventory, registries);
        this.outputIndex = tag.getInt("current-index");
        this.maxOutputIndex = tag.getInt("current-max-index");
        this.recipeCacheDirty = true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new ShapingAnvilScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    private void markPersistedChanged() {
        super.setChanged();
        this.clientSyncPending = true;
    }

    private void markInventoryChanged() {
        this.recipeCacheDirty = true;
        this.markPersistedChanged();
    }

    @Override
    public void setChanged() {
        this.recipeCacheDirty = true;
        this.markPersistedChanged();
    }

    private void flushClientSync(Level world) {
        if (this.clientSyncPending && world instanceof ServerLevel serverWorld) {
            BlockState state = this.getBlockState();
            serverWorld.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_CLIENTS);
            this.clientSyncPending = false;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return getItem(0).isEmpty();
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
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
        ItemStack result = ContainerHelper.removeItem(inventory, slot, amount);
        if (!result.isEmpty()) {
            this.markInventoryChanged();
        }
        return result;
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
            this.markInventoryChanged();
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
            this.markInventoryChanged();
        }
    }

    @Override
    public void writeOpeningData(RegistryFriendlyByteBuf buffer) {
        BlockPos.STREAM_CODEC.encode(buffer, worldPosition);
    }
}
