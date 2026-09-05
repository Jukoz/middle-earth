package net.sevenstars.ofhallsandheralds.persistentdatas.origin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.sevenstars.ofhallsandheralds.dtos.spawn.Spawn;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.Optional;

public class OriginPersistentData {
    public RegistryKey<Spawn> originSpawn;
    public BlockPos initialBlockPos;
    public RegistryKey<DimensionType> initialDimension;

    public static final Codec<OriginPersistentData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryKey.createCodec(DynamicRegistriesHH.SPAWN).fieldOf("origin_spawn").forGetter(OriginPersistentData::getSpawn),
            BlockPos.CODEC.optionalFieldOf("initial_block_pos").forGetter(OriginPersistentData::getInitialBlockPos),
            RegistryKey.createCodec(RegistryKeys.DIMENSION_TYPE).optionalFieldOf("initial_dimension_type").forGetter(OriginPersistentData::getInitialDimension)

    ).apply(instance, OriginPersistentData::new));

    public static final PacketCodec<RegistryByteBuf, OriginPersistentData> PACKET_CODEC = PacketCodec.tuple(
            RegistryKey.createPacketCodec(DynamicRegistriesHH.SPAWN), OriginPersistentData::getSpawn,
            PacketCodecs.optional(BlockPos.PACKET_CODEC), OriginPersistentData::getInitialBlockPos,
            PacketCodecs.optional(RegistryKey.createPacketCodec(RegistryKeys.DIMENSION_TYPE)), OriginPersistentData::getInitialDimension,
            OriginPersistentData::new);

    private RegistryKey<Spawn> getSpawn() {
        return originSpawn;
    }

    private Optional<BlockPos> getInitialBlockPos() {
        return Optional.ofNullable(initialBlockPos);
    }

    private Optional<RegistryKey<DimensionType>> getInitialDimension() {
        return Optional.ofNullable(initialDimension);
    }


    public OriginPersistentData() {
        this.originSpawn = null;
    }

    public OriginPersistentData(RegistryKey<Spawn> originSpawn, Optional<BlockPos> initialBlockPos, Optional<RegistryKey<DimensionType>> initialDimension) {
        this.originSpawn = originSpawn;
        this.initialBlockPos = initialBlockPos.orElse(null);
        this.initialDimension = initialDimension.orElse(null);
    }

    public void SetOriginSpawn(RegistryKey<Spawn> originSpawn) {
        this.originSpawn = originSpawn;
    }

    public void SetInitialLocation(BlockPos blockPos, RegistryKey<DimensionType> dimensionType) {
        this.initialBlockPos = blockPos;
        this.initialDimension = dimensionType;
    }
}
