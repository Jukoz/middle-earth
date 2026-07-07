package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

import java.util.Optional;

public class MemoryModulesWT<U> {
    public static final MemoryModuleType<Integer> EGG_COOLDOWN = register("egg_cooldown", Codec.INT);
    private final Optional<Codec<Memory<U>>> codec;

    @VisibleForTesting
    public MemoryModulesWT(Optional<Codec<U>> codec) {
        this.codec = codec.map(Memory::createCodec);
    }

    public Optional<Codec<Memory<U>>> getCodec() {
        return this.codec;
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, OfBeastsAndWildThings.of(id), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, OfBeastsAndWildThings.of(id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void registerModMemoryModules() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Sensors for " + OfBeastsAndWildThings.MOD_ID);
    }
}
