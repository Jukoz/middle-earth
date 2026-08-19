package net.sevenstars.middleearth.gui.forge;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class ForgeAlloyingFuelSlot extends Slot {
    private final ForgeAlloyingScreenHandler handler;

    public ForgeAlloyingFuelSlot(Container inventory, ForgeAlloyingScreenHandler handler, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return AbstractFurnaceBlockEntity.isFuel(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return super.getMaxStackSize(stack);
    }
}
