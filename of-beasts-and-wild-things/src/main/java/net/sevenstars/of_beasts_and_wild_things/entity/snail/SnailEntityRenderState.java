package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class SnailEntityRenderState extends LivingEntityRenderState {
    public SnailEntityVariant variant;

    public SnailEntityRenderState() {
        variant = SnailEntityVariant.GREEN;
    }
}
