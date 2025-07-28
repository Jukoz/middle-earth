package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class SwanEntityRenderState extends LivingEntityRenderState {
    public SwanEntityVariant variant;
    public AnimationState swimmingAnimationState;
    public AnimationState sleepingAnimationState;
    public AnimationState intimidateAnimationState;
    public AnimationState eatAnimationState;

    public SwanEntityRenderState() {
        variant = SwanEntityVariant.WHITE;
    }
}
