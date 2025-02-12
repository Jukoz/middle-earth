package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class PheasantEntityRenderState extends LivingEntityRenderState {
    public PheasantEntityVariant variant;

    public PheasantEntityRenderState() {
        variant = PheasantEntityVariant.MALE;
    }
}
