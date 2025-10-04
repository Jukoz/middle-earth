package net.sevenstars.middleearth.client.renderer;

import net.minecraft.util.math.Vec3d;

public interface BipedEntityRenderStateAccess {
    Vec3d getVelocity();
    void setVelocity(Vec3d velocity);
}
