package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.client.renderer.BipedEntityRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BipedEntityRenderState.class)
public class BipedEntityRenderStateMixin extends ArmedEntityRenderState implements BipedEntityRenderStateAccess {
    @Unique private float tickProgress;
    @Unique private Vec3d previousVelocity;
    @Unique private Vec3d velocity;

    @Override
    public float getTickProgress() {
        return tickProgress;
    }

    @Override
    public Vec3d getPreviousVelocity() {
        return previousVelocity;
    }

    @Override
    public Vec3d getVelocity() {
        return velocity;
    }

    @Override
    public void setTickProgress(float tickProgress) {
        this.tickProgress = tickProgress;
    }

    @Override
    public void setPreviousVelocity(Vec3d previousVelocity) {
        this.previousVelocity = previousVelocity;
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity;
    }
}
