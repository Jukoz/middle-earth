package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class DeerEntityRenderState extends LivingEntityRenderState {
    public DeerEntityVariant variant;
    public boolean isRunning;

    public DeerEntityRenderState() {
        variant = DeerEntityVariant.SPOTS;
        isRunning = false;
    }
}
