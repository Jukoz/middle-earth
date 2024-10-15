package net.jukoz.me.particles.custom;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class RingOfSmoke extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    protected RingOfSmoke(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, int maxAge) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider); // Set initial sprite
        this.maxAge = maxAge;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new RingOfSmoke(world, x, y, z, this.sprites, 100); // Set the desired max age here
        }
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Quaternionf quaternionf = new Quaternionf();
        this.getRotator().setRotation(quaternionf, camera, tickDelta);
        if (this.angle != 0.0f) {
            quaternionf.rotateZ(MathHelper.lerp(tickDelta, this.prevAngle, this.angle));
        }

        Vec3d vec3d = camera.getPos();
        float x = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float y = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float z = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - vec3d.getZ());

        float scale = this.getSize(tickDelta) * (1.0f + (this.age / (float)this.maxAge) * 2.0f); // Increase scale over time

        this.method_60373(vertexConsumer, camera, quaternionf, tickDelta);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider); // Update sprite based on age
    }
}