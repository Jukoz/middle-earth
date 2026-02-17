package net.sevenstars.middleearth.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticleTypes {

    public static final SimpleParticleType ANVIL_SPARK_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType BIOME_FOG_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType RING_OF_SMOKE = FabricParticleTypes.simple();

    public static void registerParticleTypes(){
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MiddleEarth.MOD_ID, "anvil_spark_particles"), ANVIL_SPARK_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MiddleEarth.MOD_ID, "biome_fog_particles"), BIOME_FOG_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MiddleEarth.MOD_ID, "ring_of_smoke_particles"), RING_OF_SMOKE);
    }

}
