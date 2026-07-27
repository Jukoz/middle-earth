package net.sevenstars.middleearth.block.special.crockpot;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import net.sevenstars.middleearth.recipe.CrockpotRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrockpotBlockEntity extends BlockEntity implements ExtendedMenuProviderME, WorldlyContainer {
    private static final String ID = "crockpot";
    public static final int OUTPUT_SLOT = 4;
    public static final int COOK_TIME = 60;
    private final NonNullList<ItemStack> inventory =
            NonNullList.withSize(5, ItemStack.EMPTY);
    protected final ContainerData propertyDelegate;
    private final RecipeManager.CachedCheck<MultipleStackRecipeInput, ? extends CrockpotRecipe> matchGetter;
    private int progress = 0;
    private float liquidTopLevel;
    private MultipleStackRecipeInput cachedRecipeInput = new MultipleStackRecipeInput(List.of());
    @Nullable
    private RecipeHolder<? extends CrockpotRecipe> cachedRecipe;
    private int cachedIngredientCount;
    private boolean recipeCacheDirty = true;
    private boolean clientSyncPending;

    public CrockpotBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 0.5f);
    }

    public CrockpotBlockEntity(BlockPos pos, BlockState state, float liquidTopLevel) {
        super(null, pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrockpotBlockEntity.this.progress;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrockpotBlockEntity.this.progress = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
        this.matchGetter = RecipeManager.createCheck(RecipesME.CROCKPOT);
        this.liquidTopLevel = liquidTopLevel;
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, CrockpotBlockEntity blockEntity) {
        if (world.isClientSide || !(world instanceof ServerLevel serverWorld)) {
            return;
        }

        if (!blockEntity.isBoiling()) {
            if (blockEntity.progress != 0) {
                blockEntity.progress = 0;
                blockEntity.markPersistedChanged();
                blockEntity.clientSyncPending = true;
            }
            blockEntity.flushClientSync(serverWorld);
            return;
        }

        blockEntity.refreshRecipeCache(serverWorld);

        int previousProgress = blockEntity.progress;
        boolean inventoryChanged = false;
        if (blockEntity.cachedIngredientCount < 2) {
            blockEntity.progress = Math.max(blockEntity.progress - 1, 0);
        }
        ++blockEntity.progress;
        if (blockEntity.progress >= COOK_TIME) {
            blockEntity.progress = 0;
            inventoryChanged = craftRecipe(
                    world.registryAccess(),
                    blockEntity.cachedRecipe,
                    blockEntity.cachedRecipeInput,
                    blockEntity.inventory
            );
            if (inventoryChanged) {
                blockEntity.recipeCacheDirty = true;
                blockEntity.clientSyncPending = true;
            }
            blockEntity.recipeCraftedSound();
        }

        if (blockEntity.progress != previousProgress || inventoryChanged) {
            blockEntity.markPersistedChanged();
        }
        if ((previousProgress > 0) != (blockEntity.progress > 0)) {
            blockEntity.clientSyncPending = true;
        }
        blockEntity.flushClientSync(serverWorld);
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, CrockpotBlockEntity blockEntity) {
        if(blockEntity.isCooking())
        {
            double x = (double)pos.getX() + 0.5;
            double y = (double)pos.getY() + 0.5;
            double z = (double)pos.getZ() + 0.5;
            if (world.random.nextDouble() < 0.12) {
                world.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            double i = world.random.nextDouble() * 0.4 - 0.2;
            double j = world.random.nextDouble() * 0.4 - 0.2;
            world.addParticle(ParticleTypes.BUBBLE, x + i, y, z + j, 0.0, 0.1, 0.0);
        }
    }

    private static boolean craftRecipe(RegistryAccess dynamicRegistryManager,
                                       @Nullable RecipeHolder<? extends CrockpotRecipe> recipe,
                                       MultipleStackRecipeInput input, NonNullList<ItemStack> inventory) {
        if (recipe != null) {
            ItemStack craftedStack = recipe.value().assemble(input, dynamicRegistryManager);
            inventory.set(OUTPUT_SLOT, craftedStack.copy());
            for(int i = 0; i < OUTPUT_SLOT; i++) {
                inventory.set(i, ItemStack.EMPTY);
            }
            return true;
        } else {
            return false;
        }
    }

    private void refreshRecipeCache(ServerLevel world) {
        if (!this.recipeCacheDirty) {
            return;
        }

        ArrayList<ItemStack> ingredients = new ArrayList<>();
        for (ItemStack ingredient : this.inventory) {
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }
        this.cachedIngredientCount = ingredients.size();
        this.cachedRecipeInput = new MultipleStackRecipeInput(ingredients);
        this.cachedRecipe = this.cachedIngredientCount >= 2
                ? this.matchGetter.getRecipeFor(this.cachedRecipeInput, world).orElse(null)
                : null;
        this.recipeCacheDirty = false;
    }

    private void markPersistedChanged() {
        super.setChanged();
    }

    private void markInventoryChanged() {
        this.recipeCacheDirty = true;
        this.markPersistedChanged();
        this.clientSyncPending = true;
    }

    @Override
    public void setChanged() {
        this.recipeCacheDirty = true;
        this.markPersistedChanged();
        this.clientSyncPending = true;
    }

    private void flushClientSync(ServerLevel world) {
        if (this.clientSyncPending) {
            BlockState state = this.getBlockState();
            world.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_CLIENTS);
            this.clientSyncPending = false;
        }
    }

    public void recipeCraftedSound() {
        double x = (double)worldPosition.getX() + 0.5;
        double y = (double)worldPosition.getY() + 0.5;
        double z = (double)worldPosition.getZ() + 0.5;
        level.playSound(null, worldPosition, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.1F, 0.8F);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new CrockpotScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public boolean isCooking() {
        return progress > 0;
    }

    public boolean isHanging() {
        if(level != null) {
            BlockState blockState = level.getBlockState(getBlockPos());
            if(blockState == null || blockState.isAir()) return false;
            return blockState.getValue(CrockpotBlock.HANGING);
        }
        return false;
    }

    public boolean isBoiling() {
        return isHanging() && hasOutput();
    }

    public boolean hasOutput() {
        return !getItem(OUTPUT_SLOT).isEmpty();
    }

    public float getLiquidTopLevel() {
        return this.liquidTopLevel;
    }

    public boolean fill(ItemStack itemStack) {
        if(getItem(OUTPUT_SLOT).isEmpty()) {
            if(itemStack.getItem() == Items.WATER_BUCKET) {
                setItem(OUTPUT_SLOT, itemStack);
                return true;
            }
        }
        return false;
    }

    public ItemStack fillBowl(Item remainder) {
        if(hasOutput()) {
            ItemStack outputStack = getItem(OUTPUT_SLOT);
            var foodProperties = outputStack.get(DataComponents.FOOD);
            if (foodProperties != null) {
                ItemStack recipeRemainder = foodProperties.usingConvertsTo().orElse(ItemStack.EMPTY);
                if (recipeRemainder.getItem() == remainder) {
                    ItemStack result = outputStack.copy();
                    result.setCount(1);
                    outputStack.shrink(1);
                    if(outputStack.getCount() == 0) {
                        this.inventory.set(OUTPUT_SLOT, ItemStack.EMPTY);
                    }
                    this.markInventoryChanged();
                    return result;
                }
            }
        }
        return ItemStack.EMPTY;
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
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return false; // Do not extract liquid output into hopper.
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    public List<ItemStack> getList() {
        return new ArrayList<>(this.inventory.stream().toList());
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack removed = ContainerHelper.removeItem(this.inventory, slot, amount);
        if (!removed.isEmpty()) {
            this.markInventoryChanged();
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
            this.markInventoryChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        boolean hasItems = false;
        for (ItemStack stack : this.inventory) {
            if (!stack.isEmpty()) {
                hasItems = true;
                break;
            }
        }
        if (hasItems) {
            this.inventory.clear();
            this.markInventoryChanged();
        }
    }

    @Override
    public void writeOpeningData(RegistryFriendlyByteBuf buffer) {
        BlockPos.STREAM_CODEC.encode(buffer, worldPosition);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.inventory, registries);
        tag.putInt(ID + ".progress", this.progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.clear();
        ContainerHelper.loadAllItems(tag, this.inventory, registries);
        this.progress = tag.getInt(ID + ".progress");
        this.recipeCacheDirty = true;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return this.saveWithoutMetadata(registryLookup);
    }
}
