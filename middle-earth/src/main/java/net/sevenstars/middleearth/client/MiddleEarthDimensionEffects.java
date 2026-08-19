package net.sevenstars.middleearth.client;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

public final class MiddleEarthDimensionEffects extends DimensionSpecialEffects {
    public MiddleEarthDimensionEffects() {
        super(300.0F, true, SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
        return fogColor.multiply(
                brightness * 0.94F + 0.06F,
                brightness * 0.94F + 0.06F,
                brightness * 0.91F + 0.09F
        );
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }
}
