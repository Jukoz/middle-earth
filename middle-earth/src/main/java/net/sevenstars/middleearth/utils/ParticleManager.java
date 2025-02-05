package net.sevenstars.middleearth.utils;

import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class ParticleManager {
    private static final float RANDOM_PARTICLE_RANGE = 0.25f;
    private static final float PARTICLE_VELOCITY = 0.25f;
    private static final int PARTICLE_QUANTITY = 15;

    public static void createParticles(World world, Position pos, SimpleParticleType particleType) {
        createParticles(world, pos, particleType, PARTICLE_QUANTITY, PARTICLE_VELOCITY, RANDOM_PARTICLE_RANGE);
    }

    public static void createParticles(World world, Position pos, SimpleParticleType particleType, int quantity) {
        createParticles(world, pos, particleType, quantity, PARTICLE_VELOCITY, RANDOM_PARTICLE_RANGE);
    }

    public static void createParticles(World world, Position pos, SimpleParticleType particleType, int quantity, float velocity, float range) {
        for (int i = 0; i < quantity; i++) {
            world.addParticle(particleType,
                    pos.getX() + range * (-0.5f + Math.random()),
                    pos.getY() + Math.random(),
                    pos.getZ() + range * (-0.5f + Math.random()),
                    velocity * (-0.5f + Math.random()),
                    velocity * (-0.5f + Math.random()),
                    velocity * (-0.5f + Math.random()));
        }
    }
}
