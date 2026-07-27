package net.sevenstars.middleearth.particles.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public final class FireflyParticle extends TextureSheetParticle {
    private FireflyParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            double velocityX,
            double velocityY,
            double velocityZ) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        speedUpWhenYMotionIsBlocked = true;
        friction = 0.96F;
        quadSize *= 0.75F;
        xd *= 0.8F;
        yd *= 0.8F;
        zd *= 0.8F;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float partialTick) {
        return (int)(255.0F * fade(lifetimeProgress(age + partialTick), 0.1F, 0.3F));
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.getBlockState(BlockPos.containing(x, y, z)).isAir()) {
            remove();
            return;
        }

        setAlpha(fade(lifetimeProgress(age), 0.3F, 0.5F));
        if (random.nextDouble() > 0.95 || age == 1) {
            setParticleSpeed(
                    -0.05F + 0.1F * random.nextDouble(),
                    -0.05F + 0.1F * random.nextDouble(),
                    -0.05F + 0.1F * random.nextDouble());
        }
    }

    private float lifetimeProgress(float particleAge) {
        return Mth.clamp(particleAge / lifetime, 0.0F, 1.0F);
    }

    private static float fade(float progress, float fadeOut, float fadeIn) {
        if (progress >= 1.0F - fadeOut) {
            return (1.0F - progress) / fadeOut;
        }
        return progress <= fadeIn ? progress / fadeIn : 1.0F;
    }

    public static final class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType type,
                ClientLevel level,
                double x,
                double y,
                double z,
                double velocityX,
                double velocityY,
                double velocityZ) {
            FireflyParticle particle = new FireflyParticle(
                    level,
                    x,
                    y,
                    z,
                    0.5 - level.random.nextDouble(),
                    level.random.nextBoolean() ? velocityY : -velocityY,
                    0.5 - level.random.nextDouble());
            particle.setLifetime(level.random.nextIntBetweenInclusive(200, 300));
            particle.scale(1.5F);
            particle.pickSprite(sprites);
            particle.setAlpha(0.0F);
            return particle;
        }
    }
}
