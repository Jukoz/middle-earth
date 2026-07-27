package net.sevenstars.api.entity.ai.brain;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.sevenstars.api.SevenStarsApi;
import net.sevenstars.api.registries.RegistrationBridge;

import java.util.Optional;

public class MemoryModulesAPI<U> {
    public static final MemoryModuleType<Boolean> DEFENDING_HOME = register("defending_home", Codec.BOOL);

    private final Optional<Codec<ExpirableValue<U>>> codec;

    @VisibleForTesting
    public MemoryModulesAPI(Optional<Codec<U>> codec) {
        this.codec = codec.map(ExpirableValue::codec);
    }

    public Optional<Codec<ExpirableValue<U>>> getCodec() {
        return this.codec;
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return RegistrationBridge.register(
                BuiltInRegistries.MEMORY_MODULE_TYPE,
                ResourceLocation.fromNamespaceAndPath(SevenStarsApi.MOD_ID, id),
                new MemoryModuleType<>(Optional.of(codec))
        );
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return RegistrationBridge.register(
                BuiltInRegistries.MEMORY_MODULE_TYPE,
                ResourceLocation.fromNamespaceAndPath(SevenStarsApi.MOD_ID, id),
                new MemoryModuleType<>(Optional.empty())
        );
    }

    public static void registerModMemoryModules() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Sensors for " + SevenStarsApi.MOD_ID);
    }
}
