package net.sevenstars.middleearth.client.renderer;

import net.minecraft.item.ItemStack;

public interface ArmedEntityRenderStateAccess {
    ItemStack getMainHandStack();
    ItemStack getOffHandStack();
    /** The off-hand stack itself, not a copy: only to know it by (it is the holder's own). */
    ItemStack getOffHandStackItself();
    /** The head slot's stack itself, not a copy: only to know it by (a worn light's flames). */
    ItemStack getHeadStackItself();
    void setHeadStackItself(ItemStack headStack);
    boolean isRestrained();
    void setMainHandStack(ItemStack mainHandStack);
    void setOffHandStack(ItemStack mainHandStack);
    void setRestrained(boolean restrained);
}
