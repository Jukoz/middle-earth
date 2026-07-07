package net.sevenstars.middleearth.entity.beasts.warg;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.beasts.BeastEntityRenderState;

@Environment(EnvType.CLIENT)
public class WargEntityRenderState extends BeastEntityRenderState {
    WargVariant variant = WargVariant.BROWN;
    public ItemStack armor = ItemStack.EMPTY;
    public ItemStack saddle = ItemStack.EMPTY;

    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState startSittingAnimationState = new AnimationState();
    public AnimationState stopSittingAnimationState = new AnimationState();
    public AnimationState sittingAnimationState = new AnimationState();
    public AnimationState chargeAnimationState = new AnimationState();
}
