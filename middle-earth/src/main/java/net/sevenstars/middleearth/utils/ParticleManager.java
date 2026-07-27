package net.sevenstars.middleearth.utils;

import net.minecraft.core.Position;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class ParticleManager {
    private static final float RANDOM_PARTICLE_RANGE = 0.25f;
    private static final float PARTICLE_VELOCITY = 0.25f;
    private static final int PARTICLE_QUANTITY = 15;

    public static void createParticles(Level world, Position pos, SimpleParticleType particleType) {
        createParticles(world, pos, particleType, PARTICLE_QUANTITY, PARTICLE_VELOCITY, RANDOM_PARTICLE_RANGE);
    }

    public static void createParticles(Level world, Position pos, SimpleParticleType particleType, int quantity) {
        createParticles(world, pos, particleType, quantity, PARTICLE_VELOCITY, RANDOM_PARTICLE_RANGE);
    }

    public static void createParticles(Level world, Position pos, SimpleParticleType particleType, int quantity, float velocity, float range) {
        RandomSource random = world.random;
        for (int i = 0; i < quantity; i++) {
            world.addParticle(particleType,
                    pos.x() + range * (-0.5f + random.nextDouble()),
                    pos.y() + random.nextDouble(),
                    pos.z() + range * (-0.5f + random.nextDouble()),
                    velocity * (-0.5f + random.nextDouble()),
                    velocity * (-0.5f + random.nextDouble()),
                    velocity * (-0.5f + random.nextDouble()));
        }
    }
}
