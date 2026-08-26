package net.sevenstars.api.registries.brain;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.api.SevenStarsApi;

import java.util.Optional;

public class MemoryModulesAPI<U> {
    public static final MemoryModuleType<Boolean> DEFENDING_HOME = register("defending_home", Codec.BOOL);
    public static final MemoryModuleType<Integer> ACTION_TIMEOUT = register("action_timeout", Codec.INT);

    private final Optional<Codec<Memory<U>>> codec;

    @VisibleForTesting
    public MemoryModulesAPI(Optional<Codec<U>> codec) {
        this.codec = codec.map(Memory::createCodec);
    }

    public Optional<Codec<Memory<U>>> getCodec() {
        return this.codec;
    }

    private static <U> MemoryModuleType<U> register(String idPath, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, SevenStarsApi.id(idPath), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String idPath) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, SevenStarsApi.id(idPath), new MemoryModuleType<>(Optional.empty()));
    }

    public static void register() {
        SevenStarsApi.logRegistryMsg("Sensors");
    }
}
