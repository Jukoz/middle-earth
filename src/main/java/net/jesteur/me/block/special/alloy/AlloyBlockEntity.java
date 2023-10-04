package net.jesteur.me.block.special.alloy;

import net.jesteur.me.block.ModBlockEntities;
import net.jesteur.me.gui.alloy.AlloyScreenHandler;
import net.jesteur.me.item.ModRessourceItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


public class AlloyBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, SidedInventory {
    private static final String ID = "alloy";
    public static final int MAX_PROGRESS = 14; //72;
    public static final int INPUT_SIZE = 4;
    private static final int METAL_SLOT = 1;
    private static final int CARBIDE_SLOT = 2;
    private static final int FUEL_SLOT = 0;
    private static final int OUTPUT_SLOT = 5;
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(6, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    protected final Map<Item, Integer> fuelTimeMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
    private int progress = 0;
    private int fuelTime = 0;
    private int maxFuelTime = 0;


    public AlloyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AlloyBlockEntity.this.progress;
                    case 1 -> AlloyBlockEntity.this.fuelTime;
                    case 2 -> AlloyBlockEntity.this.maxFuelTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AlloyBlockEntity.this.progress = value;
                    case 1 -> AlloyBlockEntity.this.fuelTime = value;
                    case 2 -> AlloyBlockEntity.this.maxFuelTime = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Alloy"); // to fix to translation
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlloyScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt(ID + ".progress", progress);
        nbt.putInt(ID + ".fuel-time", fuelTime);
        nbt.putInt(ID + ".max-fuel-time", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt(ID + ".progress");
        fuelTime = nbt.getInt(ID + ".fuel-time");
        maxFuelTime = nbt.getInt(ID + ".max-fuel-time");
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
        if (slot == OUTPUT_SLOT) return false;
        if (slot == FUEL_SLOT) {
            System.out.println("IS " + stack.getItem().getName() + " , FUEL: " + isFuel(stack.getItem()));
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

    public static void tick(World world, BlockPos blockPos, BlockState blockState, AlloyBlockEntity entity) {
        if(world.isClient()) return;

        if(hasRecipe(entity)) {
            entity.progress++;
            markDirty(world, blockPos, blockState); // Reloads the block in this chunk, for sync & saving.
            if(entity.progress >= MAX_PROGRESS) {
                craftItem(entity);
                entity.progress = 0;
            }
        } else {
            entity.progress = Math.max(entity.progress - 2, 0);
            markDirty(world, blockPos, blockState);
        }
    }

    private static void craftItem(AlloyBlockEntity entity) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
        }

        if(hasRecipe(entity)) {
            entity.removeStack(METAL_SLOT, 1);
            entity.removeStack(CARBIDE_SLOT, 1);

            entity.setStack(OUTPUT_SLOT, new ItemStack(ModRessourceItems.DWARVEN_STEEL,
                    entity.getStack(OUTPUT_SLOT).getCount() + 1));
        }
    }

    private static boolean hasRecipe(AlloyBlockEntity entity) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
        }

        boolean hasMetalInFirstSlot = entity.getStack(METAL_SLOT).getItem() == Items.RAW_IRON || entity.getStack(METAL_SLOT).getItem() == Items.IRON_INGOT;
        boolean hasCarbideInSecondSlot = entity.getStack(CARBIDE_SLOT).getItem() == Items.COAL;
        boolean canInsertOutput = canInsertAmountIntoOutput(inventory1)
                && canInsertRecipeIntoOutput(inventory1, ModRessourceItems.DWARVEN_STEEL);

        return hasMetalInFirstSlot && hasCarbideInSecondSlot && canInsertOutput;
    }

    private static boolean canInsertAmountIntoOutput(SimpleInventory inventory1) {
        return inventory1.getStack(OUTPUT_SLOT).getMaxCount() > inventory1.getStack(OUTPUT_SLOT).getCount();
    }
    private static boolean canInsertRecipeIntoOutput(SimpleInventory inventory1, Item item) {
        return inventory1.getStack(OUTPUT_SLOT).getItem() == item || inventory1.getStack(OUTPUT_SLOT).isEmpty();
    }
}
