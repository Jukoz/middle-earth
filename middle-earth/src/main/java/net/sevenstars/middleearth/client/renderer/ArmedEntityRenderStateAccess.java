package net.sevenstars.middleearth.client.renderer;

import net.minecraft.item.ItemStack;

public interface ArmedEntityRenderStateAccess {
    ItemStack getMainHandStack();
    ItemStack getOffHandStack();
    /** The off-hand stack itself, not a copy: only to know it by (it is the holder's own). */
    ItemStack getOffHandStackItself();
    boolean isRestrained();
    void setMainHandStack(ItemStack mainHandStack);
    void setOffHandStack(ItemStack mainHandStack);
    void setRestrained(boolean restrained);
}
