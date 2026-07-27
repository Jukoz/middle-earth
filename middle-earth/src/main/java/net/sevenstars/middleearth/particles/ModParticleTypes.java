package net.sevenstars.middleearth.particles;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;

public class ModParticleTypes {

    public static final SimpleParticleType ANVIL_SPARK_PARTICLE = new SimpleParticleType(false);
    public static final SimpleParticleType BIOME_FOG_PARTICLE = new SimpleParticleType(true);
    public static final SimpleParticleType FIREFLY_PARTICLE = new SimpleParticleType(false);
    public static final ParticleType<ColorParticleOption> TINTED_LEAVES_PARTICLE = new ParticleType<>(false) {
        @Override
        public MapCodec<ColorParticleOption> codec() {
            return ColorParticleOption.codec(this);
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ColorParticleOption> streamCodec() {
            return ColorParticleOption.streamCodec(this);
        }
    };

    public static void registerParticleTypes(){
        RegistrationBridge.register(BuiltInRegistries.PARTICLE_TYPE, MiddleEarth.of("anvil_spark_particles"), ANVIL_SPARK_PARTICLE);
        RegistrationBridge.register(BuiltInRegistries.PARTICLE_TYPE, MiddleEarth.of("biome_fog_particles"), BIOME_FOG_PARTICLE);
        RegistrationBridge.register(BuiltInRegistries.PARTICLE_TYPE, MiddleEarth.of("firefly"), FIREFLY_PARTICLE);
        RegistrationBridge.register(BuiltInRegistries.PARTICLE_TYPE, MiddleEarth.of("tinted_leaves"), TINTED_LEAVES_PARTICLE);
    }

}
