package net.jukoz.me.block.special.alloyfurnace;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.gui.alloyfurnace.AlloyFurnaceScreenHandler;
import net.jukoz.me.recipe.AlloyRecipe;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
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
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class AlloyFurnaceEntity extends BlockEntity implements NamedScreenHandlerFactory, SidedInventory {
    private static final String ID = "alloy_furnace";
    public static final int MAX_PROGRESS = 1200;
    public static final int FUEL_SLOT = 0;
    public static final int OUTPUT_SLOT = 5;
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(6, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    protected final Map<Item, Integer> fuelTimeMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
    private int progress = 0;
    private int fuelTime = 0;
    private int maxFuelTime = 0;


    public AlloyFurnaceEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOY_FURNACE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AlloyFurnaceEntity.this.progress;
                    case 1 -> AlloyFurnaceEntity.this.fuelTime;
                    case 2 -> AlloyFurnaceEntity.this.maxFuelTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AlloyFurnaceEntity.this.progress = value;
                    case 1 -> AlloyFurnaceEntity.this.fuelTime = value;
                    case 2 -> AlloyFurnaceEntity.this.maxFuelTime = value;
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
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlloyFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt(ID + ".progress", progress);
        nbt.putInt(ID + ".fuel-time", fuelTime);
        nbt.putInt(ID + ".max-fuel-time", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt(ID + ".progress");
        fuelTime = nbt.getInt(ID + ".fuel-time");
        maxFuelTime = nbt.getInt(ID + ".max-fuel-time");
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

    public static void tick(World world, BlockPos blockPos, BlockState blockState, AlloyFurnaceEntity entity) {
        if(world.isClient()) return;
        entity.fuelTime = Math.max(0, entity.fuelTime - 1);
        boolean progress = false;
        if(hasRecipe(entity)) {
            if(entity.hasFuel(entity)) {
                entity.progress++;
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

    private static void craftItem(AlloyFurnaceEntity entity) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
            if(i != FUEL_SLOT && i != OUTPUT_SLOT) inputs.add(entity.getStack(i));
        }

        Optional<RecipeEntry<AlloyRecipe>> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(AlloyRecipe.Type.INSTANCE, new MultipleStackRecipeInput(inputs), entity.getWorld());
        if(match.isEmpty()) throw new RuntimeException("Somehow... you crafted an item without recipe?!");

        if(hasRecipe(entity)) {
            for (int i = 1; i <= 4; i++) {
                entity.removeStack(i, 1);
            }
            entity.setStack(OUTPUT_SLOT, new ItemStack(match.get().value().output.getRegistryEntry(),
                    entity.getStack(OUTPUT_SLOT).getCount() + match.get().value().output.getCount()));
            entity.markDirty();
        }
    }

    private static boolean hasRecipe(AlloyFurnaceEntity entity) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getStack(i));
            if(i != FUEL_SLOT && i != OUTPUT_SLOT) inputs.add(entity.getStack(i));
        }

        Optional<RecipeEntry<AlloyRecipe>> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(AlloyRecipe.Type.INSTANCE, new MultipleStackRecipeInput(inputs), entity.getWorld());
        if(match.isEmpty()) return false;

        return canInsertAmountIntoOutput(inventory1, match.get().value().output.getCount())
                && canInsertRecipeIntoOutput(inventory1, match.get().value().output.getItem());
    }

    private boolean hasFuel(AlloyFurnaceEntity entity) {

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

    private void getFuel(AlloyFurnaceEntity entity, Item fuelItem) {
        fuelTime = Math.round((float) fuelTimeMap.get(fuelItem) / 16);
        maxFuelTime = fuelTime;
        if(fuelItem == Items.LAVA_BUCKET) {
            entity.removeStack(FUEL_SLOT);
            entity.setStack(FUEL_SLOT, Items.BUCKET.getDefaultStack());
        }
        else entity.getStack(FUEL_SLOT).decrement(1);
    }

    private static boolean canInsertAmountIntoOutput(SimpleInventory inventory1, int count) {
        return inventory1.getStack(OUTPUT_SLOT).getMaxCount() > inventory1.getStack(OUTPUT_SLOT).getCount() + (count- 1);
    }
    private static boolean canInsertRecipeIntoOutput(SimpleInventory inventory1, Item item) {
        return inventory1.getStack(OUTPUT_SLOT).getItem() == item || inventory1.getStack(OUTPUT_SLOT).isEmpty();
    }
}
