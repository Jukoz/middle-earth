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
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;

import java.util.Optional;

public class NpcData {
    public static final Codec<NpcData> CODEC;
    public static final PacketCodec<RegistryByteBuf, NpcData> PACKET_CODEC;

    private RegistryEntry<NpcClass> npcClass;
    private EntityCategories category;
    private final SpawnReason spawnReason;
    private final Optional<BlockPos> structureManagerPos;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                DynamicRegistriesME.NPC_CLASS_CODEC.optionalFieldOf("Type").forGetter(NpcData::getOptionalNpcClass),
                Codec.STRING.optionalFieldOf("Category").forGetter(NpcData::getOptionalCategory),
                Codec.STRING.optionalFieldOf("SpawnReason").forGetter(NpcData::getOptionalSpawnReason),
                BlockPos.CODEC.optionalFieldOf("StructureManagerPos").forGetter((data) -> data.structureManagerPos)
        ).apply(instance, NpcData::new));

        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(PacketCodecs.registryEntry(DynamicRegistriesME.NPC_CLASS)), NpcData::getOptionalNpcClass,
                PacketCodecs.optional(PacketCodecs.STRING), NpcData::getOptionalCategory,
                PacketCodecs.optional(PacketCodecs.STRING), NpcData::getOptionalSpawnReason,
                PacketCodecs.optional(BlockPos.PACKET_CODEC), NpcData::getOptionalStructureManagerPos,
                NpcData::new);
    }

    public NpcData(Optional<RegistryEntry<NpcClass>> npcClass, Optional<String> category, Optional<String> spawnReason, Optional<BlockPos> structureManagerPos) {
        this.npcClass = npcClass.orElse(null);
        this.category = category.map(value -> parseEnum(EntityCategories.class, value)).orElse(null);
        this.spawnReason = spawnReason.map(value -> parseEnum(SpawnReason.class, value)).orElse(null);
        this.structureManagerPos = structureManagerPos == null ||  structureManagerPos.isEmpty() ? Optional.empty() : structureManagerPos;
    }

    public NpcData(RegistryEntry<NpcClass> npcClass, EntityCategories category, SpawnReason spawnReason, Optional<BlockPos> structureManagerPos) {
        this.npcClass = npcClass;
        this.category = category;
        this.spawnReason = spawnReason;
        this.structureManagerPos = structureManagerPos == null || structureManagerPos.isEmpty() ? Optional.empty() : structureManagerPos;
    }

    public NpcData() {
        this.npcClass = null;
        this.category = null;
        this.spawnReason = null;
        this.structureManagerPos = Optional.empty();
    }

    // [BUILDERS]
    public NpcData withNpcClass(RegistryEntry<NpcClass> npcClass) {
        return new NpcData(npcClass, category, spawnReason, structureManagerPos);
    }

    public NpcData withCategory(EntityCategories category) {
        return new NpcData(this.npcClass, category, this.spawnReason, this.structureManagerPos);
    }

    public NpcData withSpawnReason(SpawnReason spawnReason) {
        return new NpcData(this.npcClass, this.category, spawnReason, this.structureManagerPos);
    }

    public NpcData withStructureManagerPos(BlockPos structureManagerPos) {
        return new NpcData(this.npcClass, this.category, this.spawnReason, Optional.of(structureManagerPos));
    }
    public NpcData withoutStructureManagerPos() {
        return new NpcData(this.npcClass, this.category, this.spawnReason, Optional.empty());
    }

    public NpcData withNpcClass(World world, Identifier npcDataIdentifier) {
        if(npcDataIdentifier == null || world == null)
            return this;

        Registry<NpcClass> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
        NpcClass npcClass = registry.get(npcDataIdentifier);
        if(npcClass == null)
            return this;
        return this.withNpcClass(registry.getEntry(npcClass));
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
