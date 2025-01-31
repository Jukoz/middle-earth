package net.sevenstars.middleearth.entity.pheasant;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.sevenstars.middleearth.entity.snail.SnailVariant;
@Environment(EnvType.CLIENT)
public class PheasantEntityRenderState extends LivingEntityRenderState {
    public PheasantVariant variant;

    public PheasantEntityRenderState() {
        variant = PheasantVariant.MALE;
    }
}
