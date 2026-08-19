package net.sevenstars.middleearth.particles.custom;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Heightmap;

public class BiomeFogParticle extends TextureSheetParticle {

    private final BlockPos spawnPos;

    BiomeFogParticle(ClientLevel clientWorld, double d, double e, double f, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.scale(16.0F);
        this.setAlpha(0.0F);

        this.spawnPos = new BlockPos((int) d, (int) e, (int) f);

        this.xd = velocityX + (double)(this.random.nextFloat() / 750.0F);
        this.yd = velocityY;
        this.zd = velocityZ;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getLightColor(float tint) {
        return (int)(255.0F * getFadeAmount(this.getLifetimeProgress((float)this.age + tint), 0.1F, 0.3F));
    }

    public void tick() {
        if (this.spawnPos.getY() > level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.spawnPos.getX(), this.spawnPos.getZ()) + 1){
            this.remove();
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.alpha < 0.15F){
            this.setAlpha(getFadeAmount(this.getLifetimeProgress((float)this.age), 0.3F, 0.5F));
        }

        if (this.age++ < this.lifetime) {
            this.xd -= 0.00005F;
            this.yd += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.zd += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.move(this.xd, this.yd, this.zd);
        } else {
            this.remove();
        }
    }

    private float getLifetimeProgress(float age) {
        return Mth.clamp(age / this.lifetime, 0.0F, 1.0F);
    }

    private static float getFadeAmount(float lifetimeProgress, float fadeIn, float fadeOut) {
        if (lifetimeProgress >= 1.0F - fadeIn) {
            return (1.0F - lifetimeProgress) / fadeIn;
        } else {
            return lifetimeProgress <= fadeOut ? lifetimeProgress / fadeOut : 1.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            BiomeFogParticle biomeFogParticle = new BiomeFogParticle(clientWorld, d, e, f, g, h, i);
            biomeFogParticle.setLifetime(clientWorld.random.nextIntBetweenInclusive(300, 400));
            biomeFogParticle.pickSprite(this.spriteProvider);
            return biomeFogParticle;
        }
    }
}