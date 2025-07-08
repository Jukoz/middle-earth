package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class SwanEntityRenderState extends LivingEntityRenderState {
    public SwanEntityVariant variant;

    public SwanEntityRenderState() {
        variant = SwanEntityVariant.WHITE;
    }
}
