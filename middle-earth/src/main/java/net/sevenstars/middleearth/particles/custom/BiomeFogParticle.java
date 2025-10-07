package net.sevenstars.middleearth.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;

public class BiomeFogParticle extends SpriteBillboardParticle {

    private final BlockPos spawnPos;

    BiomeFogParticle(ClientWorld clientWorld, double d, double e, double f, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.maxAge = 320;
        this.scale(16.0F);
        this.setAlpha(0.0F);

        this.spawnPos = new BlockPos((int) d, (int) e, (int) f);

        this.velocityX = velocityX + (double)(this.random.nextFloat() / 500.0F);
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        if (this.spawnPos.getY() > world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, this.spawnPos.getX(), this.spawnPos.getZ()) + 1){
            this.markDead();
        }

        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ < this.maxAge && this.alpha >= 0.0F) {
            this.velocityX -= 0.0001F;
            this.velocityY += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.velocityZ += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if (this.age <= 160){
                this.alpha +=  0.00625F;
            }else if (this.age >= this.maxAge - 160) {
                float result = this.alpha - 0.00625F;
                if (result > 0.0f){
                    this.alpha -= 0.00625F;
                }
            }
        } else {
            this.markDead();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BiomeFogParticle biomeFogParticle = new BiomeFogParticle(clientWorld, d, e, f, g, h, i);
            biomeFogParticle.setSprite(this.spriteProvider);
            return biomeFogParticle;
        }
    }
}