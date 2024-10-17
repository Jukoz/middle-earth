package net.jukoz.me.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.jukoz.me.MiddleEarth;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticleTypes {

    public static final SimpleParticleType MALLORN_LEAVES_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType MIRKWOOD_LEAVES_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType ANVIL_SPARK_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticleTypes(){
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MiddleEarth.MOD_ID, "mallorn_leaves_particles"), MALLORN_LEAVES_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MiddleEarth.MOD_ID, "mirkwood_leaves_particles"), MIRKWOOD_LEAVES_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MiddleEarth.MOD_ID, "anvil_spark_particles"), ANVIL_SPARK_PARTICLE);
    }

}
