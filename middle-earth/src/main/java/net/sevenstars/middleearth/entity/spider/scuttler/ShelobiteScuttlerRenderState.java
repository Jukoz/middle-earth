package net.sevenstars.middleearth.entity.spider.scuttler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

@Environment(EnvType.CLIENT)
public class ShelobiteScuttlerRenderState extends LivingEntityRenderState {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    public final AnimationState pounceAnimationState = new AnimationState();
    public int timelineTicks;
    public int climbingTicks;
    public int leapingTicks;
    public SpiderVariant spiderVariant;

    public ShelobiteScuttlerRenderState() {

    }
}
