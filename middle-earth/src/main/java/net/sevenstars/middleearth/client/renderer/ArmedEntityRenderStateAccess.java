package net.sevenstars.middleearth.client.renderer;

import net.minecraft.item.ItemStack;

public interface ArmedEntityRenderStateAccess {
    ItemStack getMainHandStack();
    ItemStack getOffHandStack();
    void setMainHandStack(ItemStack mainHandStack);
    void setOffHandStack(ItemStack mainHandStack);
}
