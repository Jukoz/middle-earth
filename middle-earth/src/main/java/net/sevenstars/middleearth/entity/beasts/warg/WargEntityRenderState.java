package net.sevenstars.middleearth.entity.beasts.warg;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.beasts.BeastEntityRenderState;

@Environment(EnvType.CLIENT)
public class WargEntityRenderState extends BeastEntityRenderState {
    WargVariant variant = WargVariant.BROWN;
    public WargEyeVariant eyeVariant = WargEyeVariant.BLUE;
    public ItemStack armor = ItemStack.EMPTY;
    public ItemStack saddle = ItemStack.EMPTY;
    public boolean haveEmissiveEyes;
    public boolean isRunning;

    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState sittingAnimationState = new AnimationState();
}
