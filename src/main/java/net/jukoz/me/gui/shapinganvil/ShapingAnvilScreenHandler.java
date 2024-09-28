package net.jukoz.me.gui.shapinganvil;

import com.google.common.collect.Lists;
import net.jukoz.me.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.recipe.AnvilShapingRecipe;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ShapingAnvilScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected BlockPos pos;
    private final World world;


    public ShapingAnvilScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(3));
        this.pos = blockPos;
    }

    public ShapingAnvilScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, syncId);
        checkSize(inventory, 2);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;
        this.pos = BlockPos.ORIGIN;
        this.world = playerInventory.player.getWorld();

        this.addSlot(new ShapingAnvilSlot(inventory, 0, 23, 34));
        this.addSlot(new ShapingAnvilOutputSlot(inventory, 1, 134, 34));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getStack();
            if(slot < this.inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size() - 1, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                invSlot.setStack(ItemStack.EMPTY);
            } else {
                invSlot.markDirty();
            }
        }
        return stack;
    }

    public BlockPos getPos() {
        return pos;
    }

    public ItemStack getOutput() {
        ItemStack input = inventory.getStack(0);

        List<RecipeEntry<AnvilShapingRecipe>> match = this.world.getRecipeManager()
                .getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), this.world);

        if(match.isEmpty()) return ItemStack.EMPTY;

        if(match.get(this.propertyDelegate.get(1)).value().getOutput().isEmpty()) return ItemStack.EMPTY;

        return match.get(this.propertyDelegate.get(1)).value().getOutput();
    }

    public boolean isBonking() {
        return propertyDelegate.get(0) > 0;
    }

    public float getScaledProgress() {
        int progress = this.propertyDelegate.get(0);

        return (float) progress / ShapingAnvilBlockEntity.MAX_PROGRESS;
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