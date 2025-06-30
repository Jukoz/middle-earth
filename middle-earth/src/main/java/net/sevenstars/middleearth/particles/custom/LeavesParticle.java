package net.sevenstars.middleearth.particles.custom;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class LeavesParticle extends net.minecraft.client.particle.LeavesParticle {


    protected LeavesParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float f, float g, boolean bl, boolean bl2, float h, float i) {
        super(world, x, y, z, spriteProvider, f, g, bl, bl2, h, i);
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new LeavesParticle(clientWorld, d, e, f, this.sprites, 0.07F, 10.0F, true, false, 2.0F, 0.021F);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
}
