package net.sevenstars.middleearth.entity.beasts;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class BeastEntityRenderState extends LivingEntityRenderState {
    public AnimationState startSittingAnimationState = new AnimationState();
    public AnimationState stopSittingAnimationState = new AnimationState();
    public AnimationState chargeAnimationState = new AnimationState();

    public int tameness = 0;

    public boolean isSprinting = false;
    public boolean isCharging = false;
    public boolean isTame = false;
    public LivingEntity conrollingPassenger = null;

    public ItemStack armor = ItemStack.EMPTY;
    public ItemStack saddle = ItemStack.EMPTY;
}
