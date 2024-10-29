package net.jukoz.me.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;

/**
 * Represents a custom particle effect that simulates a ring of smoke.
 * This particle has gravity, velocity, and a limited lifespan.
 */
public class RingOfSmokeParticle extends SpriteBillboardParticle {
    SpriteProvider spriteProvider;
    /**
     * Constructs a new RingOfSmokeParticle.
     *
     * @param clientWorld The client world where the particle exists.
     * @param x The initial x-coordinate of the particle.
     * @param y The initial y-coordinate of the particle.
     * @param z The initial z-coordinate of the particle.
     */
    RingOfSmokeParticle(ClientWorld clientWorld, double x, double y, double z, double vel_x, double vel_y, double vel_z, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, vel_x, vel_y, vel_z);
        this.spriteProvider = spriteProvider;
        //making the max aged based on the velocity. a faster particle shoulbd be lasting longer
        this.maxAge = (int)((100) * ( vel_x + vel_y + vel_z));
        this.gravityStrength = 0;
        this.velocityMultiplier = 0.995F;
        this.collidesWithWorld = true;
    }

    /**
     * Returns the particle texture sheet type.
     *
     * @return The particle texture sheet type.
     */
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    /**
     * Returns the brightness of the particle.
     *
     * @param tint The tint value.
     * @return The brightness value.
     */
    public int getBrightness(float tint) {
        int i = super.getBrightness(tint);
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }

    /**
     * Returns the size of the particle.
     *
     * @param tickDelta The tick delta.
     * @return The size of the particle.
     */
    public float getSize(float tickDelta) {
        this.scale = (float) this.age /this.maxAge + 1;
        return this.scale;
    }

    /**
     * Updates the particle each tick.
     */
    public void tick() {
        super.tick();
        if (!this.dead) {
            float f = (float) this.age / (float) this.maxAge;
            this.setSpriteForAge(spriteProvider);
        }
    }

    /**
     * Factory class for creating RingOfSmokeParticle instances.
     */
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        /**
         * Constructs a new Factory.
         *
         * @param spriteProvider The sprite provider for the particle.
         */
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        /**
         * Creates a new RingOfSmokeParticle.
         *
         * @param simpleParticleType The particle type.
         * @param clientWorld The client world where the particle exists.
         * @param x The initial x-coordinate of the particle.
         * @param y The initial y-coordinate of the particle.
         * @param z The initial z-coordinate of the particle.
         * @param vel_x The initial x-velocity of the particle.
         * @param vel_y The initial y-velocity of the particle.
         * @param vel_z The initial z-velocity of the particle.
         * @return A new RingOfSmokeParticle instance.
         */
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double x, double y, double z, double vel_x, double vel_y, double vel_z) {
            RingOfSmokeParticle particle = new RingOfSmokeParticle(clientWorld, x, y, z, vel_x, vel_y, vel_z, spriteProvider);
            particle.setSprite(this.spriteProvider);
            return particle;
        }


    }
}