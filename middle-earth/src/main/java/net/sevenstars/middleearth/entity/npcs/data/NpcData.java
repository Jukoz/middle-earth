package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.Optional;

public class NpcData {
    public static final Codec<NpcData> CODEC;
    public static final PacketCodec<RegistryByteBuf, NpcData> PACKET_CODEC;

    private RegistryEntry<NpcType> type;
    private EntityCategories category;
    private final SpawnReason spawnReason;
    private final Optional<BlockPos> structureManagerPos;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                DynamicRegistriesME.NPC_TYPE_CODEC.optionalFieldOf("Type").forGetter(NpcData::getOptionalType),
                Codec.STRING.optionalFieldOf("Category").forGetter(NpcData::getOptionalCategory),
                Codec.STRING.optionalFieldOf("SpawnReason").forGetter(NpcData::getOptionalSpawnReason),
                BlockPos.CODEC.optionalFieldOf("StructureManagerPos").forGetter((data) -> data.structureManagerPos)
        ).apply(instance, NpcData::new));

        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(PacketCodecs.registryEntry(DynamicRegistriesME.NPC_TYPE)), NpcData::getOptionalType,
                PacketCodecs.optional(PacketCodecs.STRING), NpcData::getOptionalCategory,
                PacketCodecs.optional(PacketCodecs.STRING), NpcData::getOptionalSpawnReason,
                PacketCodecs.optional(BlockPos.PACKET_CODEC), NpcData::getOptionalStructureManagerPos,
                NpcData::new);
    }

    public NpcData(Optional<RegistryEntry<NpcType>> type, Optional<String> category, Optional<String> spawnReason, Optional<BlockPos> structureManagerPos) {
        this.type = type.orElse(null);
        this.category = category.map(value -> parseEnum(EntityCategories.class, value)).orElse(null);
        this.spawnReason = spawnReason.map(value -> parseEnum(SpawnReason.class, value)).orElse(null);
        this.structureManagerPos = structureManagerPos == null ||  structureManagerPos.isEmpty() ? Optional.empty() : structureManagerPos;
    }

    public NpcData(RegistryEntry<NpcType> type, EntityCategories category, SpawnReason spawnReason, Optional<BlockPos> structureManagerPos) {
        this.type = type;
        this.category = category;
        this.spawnReason = spawnReason;
        this.structureManagerPos = structureManagerPos == null || structureManagerPos.isEmpty() ? Optional.empty() : structureManagerPos;
    }

    public NpcData() {
        this.type = null;
        this.category = null;
        this.spawnReason = null;
        this.structureManagerPos = Optional.empty();
    }

    // [BUILDERS]
    public NpcData withType(RegistryEntry<NpcType> type) {
        return new NpcData(type, category, spawnReason, structureManagerPos);
    }

    public NpcData withCategory(EntityCategories category) {
        return new NpcData(this.type, category, this.spawnReason, this.structureManagerPos);
    }

    public NpcData withSpawnReason(SpawnReason spawnReason) {
        return new NpcData(this.type, this.category, spawnReason, this.structureManagerPos);
    }

    public NpcData withStructureManagerPos(BlockPos structureManagerPos) {
        return new NpcData(this.type, this.category, this.spawnReason, Optional.of(structureManagerPos));
    }
    public NpcData withType(World world, Identifier npcDataIdentifier) {
        if(npcDataIdentifier == null || world == null)
            return this;

        Registry<NpcType> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_TYPE);
        NpcType npcType = registry.get(npcDataIdentifier);
        if(npcType == null)
            return this;
        return this.withType(registry.getEntry(npcType));
    }

    public Identifier getNpcTypeId() {
        if(type == null){
            return null;
        }
        return type.getKey()
                .map(RegistryKey::getValue)
                .orElse(null);
    }

    public Identifier getFaction() {
        if(type == null)
            return null;
        return type.value().getFactionIdentifier();
    }


    public EntityCategories getCategory() {
        return category;
    }

    public SpawnReason getSpawnReason() {
        return spawnReason;
    }

    public NpcType getNpcType() {
        if(type == null)
            return null;
        return type.value();
    }

    private Optional<RegistryEntry<NpcType>> getOptionalType() {
        return type != null ? Optional.of(type) : Optional.empty();
    }


    private Optional<String> getOptionalCategory() {
        return category != null ? Optional.of(category.name()) : Optional.empty();
    }
    private Optional<String> getOptionalSpawnReason() {
        return spawnReason != null ? Optional.of(spawnReason.name()) : Optional.empty();
    }

    private Optional<BlockPos> getOptionalStructureManagerPos() {
        return structureManagerPos;
    }

    public BlockPos getStructureManagerPos() {
        return structureManagerPos.orElse(null);
    }


    private static <E extends Enum<E>> E parseEnum(Class<E> type, String value) {
        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
