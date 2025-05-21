package net.sevenstars.middleearth.gui.artisantable;

import net.sevenstars.middleearth.item.ResourceItemsME;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class ArtisanTableSlot extends Slot {
    private boolean enabled = true;
    private InputType inputType = InputType.ANY;

    public ArtisanTableSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if(!isEnabled()) return false;
        if(inputType == InputType.ANY) return super.canInsert(stack);

        else if(inputType == InputType.HANDLE && !(stack.isOf(Items.STICK) || stack.isOf(ResourceItemsME.ROD))) {
            return false;
        }
        else if(inputType == InputType.HILT && !(stack.isOf(ResourceItemsME.SWORD_HILT))) {
            return false;
        }
        else if(inputType == InputType.BLADE && !(stack.isOf(ResourceItemsME.SHORT_BLADE) ||
                stack.isOf(ResourceItemsME.BLADE) || stack.isOf(ResourceItemsME.LONG_BLADE))) {
            return false;
        }
        else if(inputType == InputType.AXE && !(stack.isOf(ResourceItemsME.AXE_HEAD))) {
            return false;
        }
        else if(inputType == InputType.PICKAXE && !(stack.isOf(ResourceItemsME.PICKAXE_HEAD))) {
            return false;
        }
        else if(inputType == InputType.SHOVEL && !(stack.isOf(ResourceItemsME.SHOVEL_HEAD))) {
            return false;
        }
        else if(inputType == InputType.HOE && !(stack.isOf(ResourceItemsME.HOE_HEAD))) {
            return false;
        }

        return super.canInsert(stack);
    }
}
