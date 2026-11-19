package net.sevenstars.middleearth.features.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;

import java.util.Optional;

public class NpcData {
    public static final Codec<NpcData> CODEC;
    public static final PacketCodec<RegistryByteBuf, NpcData> PACKET_CODEC;

    private RegistryEntry<NpcClass> npcClass;
    private EntityCategories category;
    private final SpawnReason spawnReason;
    private final Optional<BlockPos> hostPos;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                DynamicRegistriesME.NPC_CLASS_CODEC.optionalFieldOf("Type").forGetter(NpcData::getOptionalNpcClass),
                Codec.STRING.optionalFieldOf("Category").forGetter(NpcData::getOptionalCategory),
                Codec.STRING.optionalFieldOf("SpawnReason").forGetter(NpcData::getOptionalSpawnReason),
                BlockPos.CODEC.optionalFieldOf("StructureManagerPos").forGetter((data) -> data.hostPos)
        ).apply(instance, NpcData::new));

        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(PacketCodecs.registryEntry(DynamicRegistriesME.NPC_CLASS)), NpcData::getOptionalNpcClass,
                PacketCodecs.optional(PacketCodecs.STRING), NpcData::getOptionalCategory,
                PacketCodecs.optional(PacketCodecs.STRING), NpcData::getOptionalSpawnReason,
                PacketCodecs.optional(BlockPos.PACKET_CODEC), NpcData::getOptionalHostPos,
                NpcData::new);
    }

    public NpcData(Optional<RegistryEntry<NpcClass>> npcClass, Optional<String> category, Optional<String> spawnReason, Optional<BlockPos> hostPos) {
        this.npcClass = npcClass.orElse(null);
        this.category = category.map(value -> parseEnum(EntityCategories.class, value)).orElse(null);
        this.spawnReason = spawnReason.map(value -> parseEnum(SpawnReason.class, value)).orElse(null);
        this.hostPos = hostPos == null ||  hostPos.isEmpty() ? Optional.empty() : hostPos;
    }

    public NpcData(RegistryEntry<NpcClass> npcClass, EntityCategories category, SpawnReason spawnReason, Optional<BlockPos> hostPos) {
        this.npcClass = npcClass;
        this.category = category;
        this.spawnReason = spawnReason;
        this.hostPos = hostPos == null || hostPos.isEmpty() ? Optional.empty() : hostPos;
    }

    public NpcData() {
        this.npcClass = null;
        this.category = null;
        this.spawnReason = null;
        this.hostPos = Optional.empty();
    }

    // [BUILDERS]
    public NpcData withNpcClass(RegistryEntry<NpcClass> npcClass) {
        return new NpcData(npcClass, category, spawnReason, hostPos);
    }

    public NpcData withCategory(EntityCategories category) {
        return new NpcData(this.npcClass, category, this.spawnReason, this.hostPos);
    }

    public NpcData withSpawnReason(SpawnReason spawnReason) {
        return new NpcData(this.npcClass, this.category, spawnReason, this.hostPos);
    }

    public NpcData withHostPos(BlockPos hostPos) {
        return new NpcData(this.npcClass, this.category, this.spawnReason, Optional.of(hostPos));
    }
    public NpcData withoutHostPos() {
        return new NpcData(this.npcClass, this.category, this.spawnReason, Optional.empty());
    }

    public Identifier getNpcClassId() {
        if(npcClass == null){
            return null;
        }
        return npcClass.getKey()
                .map(RegistryKey::getValue)
                .orElse(null);
    }

    public Identifier getFaction() {
        if(npcClass == null)
            return null;
        return npcClass.value().getFactionIdentifier();
    }


    public EntityCategories getCategory() {
        return category;
    }

    public SpawnReason getSpawnReason() {
        return spawnReason;
    }

    public NpcClass getNpcClass() {
        if(npcClass == null)
            return null;
        return npcClass.value();
    }

    private Optional<RegistryEntry<NpcClass>> getOptionalNpcClass() {
        return npcClass != null ? Optional.of(npcClass) : Optional.empty();
    }


    private Optional<String> getOptionalCategory() {
        return category != null ? Optional.of(category.name()) : Optional.empty();
    }
    private Optional<String> getOptionalSpawnReason() {
        return spawnReason != null ? Optional.of(spawnReason.name()) : Optional.empty();
    }

    private Optional<BlockPos> getOptionalHostPos() {
        return hostPos;
    }

    public BlockPos getHostPos() {
        return hostPos.orElse(null);
    }


    private static <E extends Enum<E>> E parseEnum(Class<E> type, String value) {
        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
