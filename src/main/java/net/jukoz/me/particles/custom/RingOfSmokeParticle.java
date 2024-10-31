package net.jukoz.me.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Box;

/**
 * Represents a custom particle effect that simulates a ring of smoke.
 * This particle has gravity, velocity, and a limited lifespan.
 */
public class RingOfSmokeParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    private final double strength;

    public RingOfSmokeParticle(ClientWorld clientWorld, double x, double y, double z, double vel_x, double vel_y, double vel_z, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, 0,0,0);
        this.spriteProvider = spriteProvider;
        this.strength =  (Math.abs(vel_x) + Math.abs(vel_y) + Math.abs(vel_z));


        // using constructor velocity seems to be scuffing everyhting so im setting it manually after lots of debugging
        this.velocityX = vel_x;
        this.velocityY = vel_y;
        this.velocityZ = vel_z;
        //fix for 1st frame bug
        setSprite(spriteProvider.getSprite(0, this.maxAge));

        this.maxAge = (int)((600) * this.strength);
        this.gravityStrength = 0;
        this.collidesWithWorld = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.dead) {
            setSpriteForAge(spriteProvider);
            float ageRatio = (float)this.age / this.maxAge;
            this.setAlpha(1.0F - ageRatio);
            //this.checkCollisionWithPlayer();
        }
    }


    private void checkCollisionWithPlayer() {
        Box particleBox = this.getBoundingBox();
        for (PlayerEntity player : this.world.getPlayers()) {
            if (player.getBoundingBox().intersects(particleBox)) {
                onCollisionWithPlayer(player);
            }
        }
    }

    private void onCollisionWithPlayer(PlayerEntity player) {
        // Handle collision logic here
        LoggerUtil.logDebugMsg("Collision detected with player: " + player.getName().getString());
    }


    /**
     * Returns the particle texture sheet type.
     *
     * @return The particle texture sheet type.
     */
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    /**
     * Returns the size of the particle.
     *
     * @param tickDelta The tick delta. ?? --- froosty
     * @return The size of the particle.
     */
    public float getSize(float tickDelta) {
        this.scale = (float) this.age/this.maxAge + 0.3F;
        return Math.min(this.scale, 0.8F);
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
            return new RingOfSmokeParticle(clientWorld, x, y, z, vel_x, vel_y, vel_z, spriteProvider);
        }


    }
}