package net.sevenstars.middleearth.gui.shapinganvil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;

import java.util.List;

public class ShapingAnvilScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected BlockPos pos;
    private final World world;

    private ItemStack outputStack;

    public ShapingAnvilScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, new SimpleInventory(1), new ArrayPropertyDelegate(2));
        this.pos = blockPos;
    }

    public ShapingAnvilScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;
        this.pos = BlockPos.ORIGIN;
        this.world = playerInventory.player.getWorld();
        this.outputStack = ItemStack.EMPTY;

        this.addSlot(new ShapingAnvilSlot(inventory, 0, 80, 55){
            @Override
            public void markDirty() {
                super.markDirty();
                ShapingAnvilScreenHandler.this.updateStack(ShapingAnvilScreenHandler.this.inventory);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void updateStack(Inventory inventory) {
        /*System.out.println("content changed");
        ItemStack input = inventory.getStack(0);

        List<RecipeEntry<AnvilShapingRecipe>> match = this.world.getRecipeManager().getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), this.world).toList();

        if (match.isEmpty()) this.outputStack = ItemStack.EMPTY;

        if (this.propertyDelegate.get(0) <= this.propertyDelegate.get(1)){
            this.outputStack =  match.get(this.propertyDelegate.get(0)).value().getOutput();
        } else {
            this.outputStack =  ItemStack.EMPTY;
        }
        System.out.println("stack is now: " + this.outputStack);*/
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
