package net.jukoz.me.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;

public class AnvilBonkParticle extends SpriteBillboardParticle {

    AnvilBonkParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.gravityStrength = 0.75F;
        this.velocityMultiplier = 0.999F;
        this.velocityX *= 0.800000011920929;
        this.velocityZ *= 0.800000011920929;
        this.velocityY = (double) (this.random.nextFloat() * 0.2F + 0.05F);
        this.scale *= this.random.nextFloat() * 2.0F + 0.2F;
        this.maxAge = (int) (16.0 / (Math.random() * 0.8 + 0.2));
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public int getBrightness(float tint) {
        int i = super.getBrightness(tint);
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }

    public float getSize(float tickDelta) {
        float f = ((float) this.age + tickDelta) / (float) this.maxAge;
        return this.scale * (1.0F - f * f);
    }

    public void tick() {
        super.tick();
        if (!this.dead) {
            float f = (float) this.age / (float) this.maxAge;
            if (this.random.nextFloat() > f) {
                this.world.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
            }
        }

    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            AnvilBonkParticle anvilBonkParticle = new AnvilBonkParticle(clientWorld, d, e, f);
            anvilBonkParticle.setSprite(this.spriteProvider);
            return anvilBonkParticle;
        }
    }
}