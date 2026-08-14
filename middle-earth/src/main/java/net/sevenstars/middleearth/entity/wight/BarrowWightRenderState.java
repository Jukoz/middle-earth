package net.sevenstars.middleearth.entity.wight;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

@Environment(EnvType.CLIENT)
public class BarrowWightRenderState extends LivingEntityRenderState {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState screamAnimationState = new AnimationState();
    public final AnimationState incantationAnimationState = new AnimationState();

    public BarrowWightRenderState() {

    }
}
