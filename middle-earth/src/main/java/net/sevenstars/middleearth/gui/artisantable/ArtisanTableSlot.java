package net.sevenstars.middleearth.gui.artisantable;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.sevenstars.middleearth.item.ResourceItemsME;

public class ArtisanTableSlot extends Slot {
    private boolean enabled = true;
    private InputType inputType = InputType.ANY;

    public ArtisanTableSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isActive() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if(!isActive()) return false;
        if(inputType == InputType.ANY) return super.mayPlace(stack);

        else if(inputType == InputType.HANDLE && !(stack.is(Items.STICK) || stack.is(ResourceItemsME.ROD)
                || stack.is(Items.BONE) || stack.is(ResourceItemsME.DIRTY_BONE))) {
            return false;
        }
        else if(inputType == InputType.HILT && !(stack.is(ResourceItemsME.SWORD_HILT))) {
            return false;
        }
        else if(inputType == InputType.BLADE && !(stack.is(ResourceItemsME.SHORT_BLADE) ||
                stack.is(ResourceItemsME.BLADE) || stack.is(ResourceItemsME.LONG_BLADE))) {
            return false;
        }
        else if(inputType == InputType.AXE && !(stack.is(ResourceItemsME.AXE_HEAD))) {
            return false;
        }
        else if(inputType == InputType.PICKAXE && !(stack.is(ResourceItemsME.PICKAXE_HEAD))) {
            return false;
        }
        else if(inputType == InputType.SHOVEL && !(stack.is(ResourceItemsME.SHOVEL_HEAD))) {
            return false;
        }
        else if(inputType == InputType.HOE && !(stack.is(ResourceItemsME.HOE_HEAD))) {
            return false;
        }

        return super.mayPlace(stack);
    }
}
