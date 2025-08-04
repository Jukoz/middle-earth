package net.sevenstars.middleearth.entity.spider.scuttler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class ShelobiteScuttlerRenderState extends LivingEntityRenderState {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    public final AnimationState pounceAnimationState = new AnimationState();
    public int climbingTicks;

    public ShelobiteScuttlerRenderState() {

    }
}
