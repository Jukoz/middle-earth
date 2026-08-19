package net.sevenstars.middleearth.particles.custom;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class AnvilBonkParticle extends TextureSheetParticle {

    AnvilBonkParticle(ClientLevel clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.gravity = 0.75F;
        this.friction = 0.999F;
        this.xd *= 0.800000011920929;
        this.zd *= 0.800000011920929;
        this.yd = (double) (this.random.nextFloat() * 0.2F + 0.05F);
        this.quadSize *= this.random.nextFloat() * 2.0F + 0.2F;
        this.lifetime = (int) (16.0 / (this.random.nextDouble() * 0.8 + 0.2));
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public int getLightColor(float tint) {
        int i = super.getLightColor(tint);
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }

    public float getQuadSize(float tickDelta) {
        float f = ((float) this.age + tickDelta) / (float) this.lifetime;
        return this.quadSize * (1.0F - f * f);
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            float f = (float) this.age / (float) this.lifetime;
            if (this.random.nextFloat() > f) {
                this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            AnvilBonkParticle anvilBonkParticle = new AnvilBonkParticle(clientWorld, d, e, f);
            anvilBonkParticle.pickSprite(this.spriteProvider);
            return anvilBonkParticle;
        }
    }
}
