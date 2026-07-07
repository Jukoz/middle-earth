package net.sevenstars.middleearth.client.renderer;

import net.minecraft.item.ItemStack;

public interface ArmedEntityRenderStateAccess {
    ItemStack getMainHandStack();
    ItemStack getOffHandStack();
    boolean isRestrained();
    void setMainHandStack(ItemStack mainHandStack);
    void setOffHandStack(ItemStack mainHandStack);
    void setRestrained(boolean restrained);
}
