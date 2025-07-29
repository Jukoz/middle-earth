package net.sevenstars.middleearth.entity.projectile.smoke;

import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import org.joml.Quaternionf;

/**
 * Stores render-specific state for {@link SmokeRingProjectileEntity}.
 */
public class SmokeRingProjectileRenderState extends ProjectileEntityRenderState {
    public Quaternionf orientationQuat;
    public int maxLifespan;
}
