package net.sevenstars.middleearth.entity.ai.brain;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;

import java.util.Optional;

public class MemoryModulesME<U> {
    public static final MemoryModuleType<Integer> DIG_FOR_FOOD_COOLDOWN = register("dig_for_food_cooldown", Codec.INT);
    public static final MemoryModuleType<Integer> ROAR_COOLDOWN = register("roar_cooldown", Codec.INT);
    public static final MemoryModuleType<Integer> SMASH_COOLDOWN = register("smash_cooldown", Codec.INT);
    public static final MemoryModuleType<Integer> ACTION_TIMEOUT = register("action_timeout", Codec.INT);
    public static final MemoryModuleType<Integer> FOOD_EATEN_COUNT = register("food_eaten_count", Codec.INT);
    public static final MemoryModuleType<Boolean> TAME = register("tame", Codec.BOOL);
    public static final MemoryModuleType<Boolean> SITTING = register("sitting", Codec.BOOL);
    public static final MemoryModuleType<BlockPos> ASSIGNED_BED_POS = register("assigned_bed_pos", BlockPos.CODEC);
    public static final MemoryModuleType<BlockPos> STRUCTURE_MANAGER_HOST_POS = register("structure_manager_host_pos", BlockPos.CODEC);
    private final Optional<Codec<ExpirableValue<U>>> codec;

    @VisibleForTesting
    public MemoryModulesME(Optional<Codec<U>> codec) {
        this.codec = codec.map(ExpirableValue::codec);
    }

    public Optional<Codec<ExpirableValue<U>>> getCodec() {
        return this.codec;
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return RegistrationBridge.register(BuiltInRegistries.MEMORY_MODULE_TYPE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return RegistrationBridge.register(BuiltInRegistries.MEMORY_MODULE_TYPE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void registerModMemoryModules() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Sensors for " + MiddleEarth.MOD_ID);
    }
}
