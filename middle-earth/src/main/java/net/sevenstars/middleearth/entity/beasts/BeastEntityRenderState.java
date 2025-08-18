package net.sevenstars.middleearth.entity.beasts;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class BeastEntityRenderState extends LivingEntityRenderState {
    public AnimationState sittingAnimationState = new AnimationState();
    public AnimationState startSittingAnimationState = new AnimationState();
    public AnimationState stopSittingAnimationState = new AnimationState();
}
