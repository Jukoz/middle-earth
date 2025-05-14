package net.sevenstars.middleearth.block.special.crockpot;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import net.sevenstars.middleearth.recipe.CrockpotRecipe;
import net.sevenstars.middleearth.recipe.ModRecipes;
import org.jetbrains.annotations.Nullable;

public class CrockpotBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {
    private static final String ID = "crockpot";
    public static final int OUTPUT_SLOT = 4;
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(5, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private final ServerRecipeManager.MatchGetter<MultipleStackRecipeInput, ? extends CrockpotRecipe> matchGetter;
    private int progress = 0;

    public CrockpotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CROCKPOT, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
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
            public int size() {
                return 1;
            }
        };
        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipes.CROCKPOT);
    }

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, CrockpotBlockEntity blockEntity) {
        ItemStack outputStack = blockEntity.inventory.get(OUTPUT_SLOT);
        if (blockEntity.isBoiling()) {
            MultipleStackRecipeInput recipeInput = new MultipleStackRecipeInput(blockEntity.inventory);
            RecipeEntry recipeEntry;
            if (blockEntity.inventory.size() >= 2) {
                recipeEntry = (RecipeEntry)blockEntity.matchGetter.getFirstMatch(recipeInput, world).orElse((Object)null);
            } else {
                recipeEntry = null;
            }

            int i = blockEntity.getMaxCountPerStack();
            if (!blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, recipeInput, blockEntity.inventory, i)) {
                blockEntity.litTimeRemaining = blockEntity.getFuelTime(world.getFuelRegistry(), outputStack);
                blockEntity.litTotalTime = blockEntity.litTimeRemaining;
                if (blockEntity.isBurning()) {
                    bl2 = true;
                    if (bl4) {
                        Item item = outputStack.getItem();
                        outputStack.decrement(1);
                        if (outputStack.isEmpty()) {
                            blockEntity.inventory.set(1, item.getRecipeRemainder());
                        }
                    }
                }
            }

            if (blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, recipeInput, blockEntity.inventory, i)) {
                ++blockEntity.cookingTimeSpent;
                if (blockEntity.cookingTimeSpent == blockEntity.cookingTotalTime) {
                    blockEntity.cookingTimeSpent = 0;
                    blockEntity.cookingTotalTime = getCookTime(world, blockEntity);
                    if (craftRecipe(world.getRegistryManager(), recipeEntry, recipeInput, blockEntity.inventory, i)) {
                        blockEntity.setLastRecipe(recipeEntry);
                    }

                    bl2 = true;
                }
            } else {
                blockEntity.cookingTimeSpent = 0;
            }
        } else if (!blockEntity.isBurning() && blockEntity.cookingTimeSpent > 0) {
            blockEntity.cookingTimeSpent = MathHelper.clamp(blockEntity.cookingTimeSpent - 2, 0, blockEntity.cookingTotalTime);
        }

        if (bl != blockEntity.isBurning()) {
            bl2 = true;
            state = (BlockState)state.with(AbstractFurnaceBlock.LIT, blockEntity.isBurning());
            world.setBlockState(pos, state, 3);
        }

        if (bl2) {
            markDirty(world, pos, state);
        }

    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager dynamicRegistryManager, @Nullable RecipeEntry<? extends AbstractCookingRecipe> recipe, SingleStackRecipeInput input, DefaultedList<ItemStack> inventory, int maxCount) {
        if (!((ItemStack)inventory.get(0)).isEmpty() && recipe != null) {
            ItemStack itemStack = ((AbstractCookingRecipe)recipe.value()).craft(input, dynamicRegistryManager);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = (ItemStack)inventory.get(2);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
                    return false;
                } else if (itemStack2.getCount() < maxCount && itemStack2.getCount() < itemStack2.getMaxCount()) {
                    return true;
                } else {
                    return itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean craftRecipe(DynamicRegistryManager dynamicRegistryManager, @Nullable RecipeEntry<? extends AbstractCookingRecipe> recipe, SingleStackRecipeInput input, DefaultedList<ItemStack> inventory, int maxCount) {
        if (recipe != null && canAcceptRecipeOutput(dynamicRegistryManager, recipe, input, inventory, maxCount)) {
            ItemStack itemStack = (ItemStack)inventory.get(0);
            ItemStack itemStack2 = ((AbstractCookingRecipe)recipe.value()).craft(input, dynamicRegistryManager);
            ItemStack itemStack3 = (ItemStack)inventory.get(2);
            if (itemStack3.isEmpty()) {
                inventory.set(2, itemStack2.copy());
            } else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
                itemStack3.increment(1);
            }

            if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !((ItemStack)inventory.get(1)).isEmpty() && ((ItemStack)inventory.get(1)).isOf(Items.BUCKET)) {
                inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemStack.decrement(1);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CrockpotScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public boolean isHanging() {
        if(world != null) {
            BlockState blockState = world.getBlockState(getPos());
            if(blockState == null || blockState.isAir()) return false;
            return blockState.get(CrockpotBlock.HANGING);
        }
        return false;
    }

    public boolean isBoiling() {
        return isHanging() && hasOutput();
    }

    public boolean hasOutput() {
        return !getStack(OUTPUT_SLOT).isEmpty();
    }

    public boolean fill(ItemStack itemStack) {
        if(getStack(OUTPUT_SLOT).isEmpty()) {
            if(itemStack.getItem() == Items.WATER_BUCKET) {
                setStack(OUTPUT_SLOT, itemStack);
                return true;
            }
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
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false; // Do not extract liquid output into hopper.
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
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

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.inventory, true, registries);
        nbt.putInt(ID + ".progress", this.progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventory.clear();
        Inventories.readNbt(nbt, this.inventory, registries);
        this.progress = nbt.getInt(ID + ".progress", 0);
    }
}
