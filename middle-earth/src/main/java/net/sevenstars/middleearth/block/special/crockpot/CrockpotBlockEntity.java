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
    public static final int COOK_TIME = 60;
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
        boolean markDirty = false;
        if (blockEntity.isBoiling()) {
            MultipleStackRecipeInput recipeInput = new MultipleStackRecipeInput(blockEntity.inventory);
            RecipeEntry recipeEntry;
            if (blockEntity.inventory.size() >= 2) {
                recipeEntry = blockEntity.matchGetter.getFirstMatch(recipeInput, world).orElse(null);
            } else {
                recipeEntry = null;
            }

            int i = blockEntity.getMaxCountPerStack();
            if (canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, recipeInput, blockEntity.inventory, i)) {
                markDirty = true;
                System.out.println("canAcceptRecipeOutput");
                ++blockEntity.progress;
                if (blockEntity.progress >= COOK_TIME) {
                    blockEntity.progress = 0;
                    craftRecipe(world.getRegistryManager(), recipeEntry, recipeInput, blockEntity.inventory, i);
                }
            } else {
                blockEntity.progress = Math.max(blockEntity.progress - 1, 0);
            }
        }

        if (markDirty) {
            markDirty(world, pos, state);
        }

    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager dynamicRegistryManager, @Nullable RecipeEntry<CrockpotRecipe> recipe,
                                                 MultipleStackRecipeInput input, DefaultedList<ItemStack> inventory, int maxCount) {
        if (!inventory.isEmpty() && recipe != null) {
            ItemStack itemStack = (recipe.value()).craft(input, dynamicRegistryManager);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack outputStack = inventory.get(OUTPUT_SLOT);
                if (outputStack.isEmpty()) {
                    return true;
                } else if (!ItemStack.areItemsAndComponentsEqual(outputStack, itemStack)) {
                    return false;
                } else if (outputStack.getCount() < maxCount && outputStack.getCount() < outputStack.getMaxCount()) {
                    return true;
                } else {
                    return outputStack.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean craftRecipe(DynamicRegistryManager dynamicRegistryManager, @Nullable RecipeEntry<CrockpotRecipe> recipe,
                                       MultipleStackRecipeInput input, DefaultedList<ItemStack> inventory, int maxCount) {
        if (recipe != null && canAcceptRecipeOutput(dynamicRegistryManager, recipe, input, inventory, maxCount)) {
            ItemStack craftedStack = recipe.value().craft(input, dynamicRegistryManager);
            ItemStack outputStack = inventory.get(OUTPUT_SLOT);
            if (outputStack.isEmpty()) {
                inventory.set(2, craftedStack.copy());
            } else if (ItemStack.areItemsAndComponentsEqual(outputStack, craftedStack)) {
                outputStack.increment(outputStack.getCount());
            }

            for(int i = 0; i < OUTPUT_SLOT; i++) {
                inventory.remove(i);
            }

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
