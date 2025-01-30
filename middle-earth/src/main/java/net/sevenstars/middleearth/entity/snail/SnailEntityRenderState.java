package net.sevenstars.middleearth.entity.snail;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class SnailEntityRenderState extends LivingEntityRenderState {
    public SnailVariant variant;

    public SnailEntityRenderState() {
        variant = SnailVariant.GREEN;
    }
}
