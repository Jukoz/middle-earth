package net.sevenstars.middleearth.entity.swan;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.sevenstars.middleearth.entity.snail.SnailVariant;

@Environment(EnvType.CLIENT)
public class SwanEntityRenderState extends LivingEntityRenderState {
    public SwanVariant variant;
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public SwanEntityRenderState() {
        variant = SwanVariant.WHITE;
    }
}