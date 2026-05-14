package net.sevenstars.middleearth.entity.projectile.smoke;

import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import org.joml.Quaternionf;

public class SmokeRingProjectileRenderState extends ProjectileEntityRenderState {
    public Quaternionf orientationQuat;
    public int maxLifespan;
    public boolean failed;
}
