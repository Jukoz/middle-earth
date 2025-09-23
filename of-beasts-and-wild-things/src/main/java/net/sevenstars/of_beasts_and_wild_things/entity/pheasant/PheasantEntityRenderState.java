package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class PheasantEntityRenderState extends LivingEntityRenderState {
    public PheasantEntityVariant variant = PheasantEntityVariant.MALE;
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState diggingAnimationState = new AnimationState();
    public AnimationState flapAnimationState = new AnimationState();
}
