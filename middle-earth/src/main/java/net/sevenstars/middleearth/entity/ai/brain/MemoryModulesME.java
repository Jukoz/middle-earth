package net.sevenstars.middleearth.entity.ai.brain;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

import java.util.Optional;

public class MemoryModulesME<U> {
    public static final MemoryModuleType<Integer> DIG_FOR_FOOD_COOLDOWN = register("dig_for_food_cooldown", Codec.INT);
    public static final MemoryModuleType<Integer> FOOD_EATEN_COUNT = register("food_eaten_count", Codec.INT);
    private final Optional<Codec<Memory<U>>> codec;

    @VisibleForTesting
    public MemoryModulesME(Optional<Codec<U>> codec) {
        this.codec = codec.map(Memory::createCodec);
    }

    public Optional<Codec<Memory<U>>> getCodec() {
        return this.codec;
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void registerModMemoryModules() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Sensors for " + MiddleEarth.MOD_ID);
    }
}
