package net.sevenstars.middleearth.entity.stone_troll;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class StoneTrollRenderState extends LivingEntityRenderState {
    public AnimationState sleepingAnimationState;
}
