package net.sevenstars.middleearth.client.renderer;

import net.minecraft.util.math.Vec3d;

public interface BipedEntityRenderStateAccess {
    float getTickProgress();
    Vec3d getPreviousVelocity();
    Vec3d getVelocity();
    void setTickProgress(float tickProgress);
    void setPreviousVelocity(Vec3d velocity);
    void setVelocity(Vec3d velocity);
}
