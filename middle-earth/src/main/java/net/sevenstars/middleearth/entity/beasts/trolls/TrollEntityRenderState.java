package net.sevenstars.middleearth.entity.beasts.trolls;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class TrollEntityRenderState extends LivingEntityRenderState {
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState chargeAnimationState = new AnimationState();
    public AnimationState throwingAnimationState = new AnimationState();


}
