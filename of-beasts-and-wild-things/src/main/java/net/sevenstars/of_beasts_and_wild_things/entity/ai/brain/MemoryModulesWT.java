package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.api.registries.RegistrationBridge;

import java.util.Optional;

public class MemoryModulesWT<U> {
    public static final MemoryModuleType<Integer> EGG_COOLDOWN = register("egg_cooldown", Codec.INT);
    private final Optional<Codec<ExpirableValue<U>>> codec;

    @VisibleForTesting
    public MemoryModulesWT(Optional<Codec<U>> codec) {
        this.codec = codec.map(ExpirableValue::codec);
    }

    public Optional<Codec<ExpirableValue<U>>> getCodec() {
        return this.codec;
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return RegistrationBridge.register(BuiltInRegistries.MEMORY_MODULE_TYPE, OfBeastsAndWildThings.of(id), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return RegistrationBridge.register(BuiltInRegistries.MEMORY_MODULE_TYPE, OfBeastsAndWildThings.of(id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void registerModMemoryModules() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Sensors for " + OfBeastsAndWildThings.MOD_ID);
    }
}
