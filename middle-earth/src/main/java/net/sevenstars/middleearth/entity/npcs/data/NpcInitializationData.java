package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class NpcInitializationData {
    public static final Codec<NpcInitializationData> CODEC;
    public static final PacketCodec<RegistryByteBuf, NpcInitializationData> PACKET_CODEC;

    private Identifier type;
    private Boolean isRandom;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("Type").forGetter(NpcInitializationData::getOptionalType),
                Codec.BOOL.optionalFieldOf("IsRandom").forGetter(NpcInitializationData::getOptionalRandom)
        ).apply(instance, NpcInitializationData::new));

        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(PacketCodecs.STRING), NpcInitializationData::getOptionalType,
                PacketCodecs.optional(PacketCodecs.BOOLEAN), NpcInitializationData::getOptionalRandom,
                NpcInitializationData::new);
    }


    public NpcInitializationData(Optional<String> type, Optional<Boolean> isRandom) {
        this.type = type.map(MiddleEarth::fetchId).orElse(null);
        this.isRandom = isRandom == null || isRandom.isEmpty() || isRandom.get() == false ? null : isRandom.get();
    }

    public NpcInitializationData(Identifier type, Boolean isRandom) {
        this.type = type;
        this.isRandom = isRandom;
    }

    public NpcInitializationData() {
        this.type = null;
        this.isRandom = null;
    }

    public NpcInitializationData withType(Identifier newType) {
        return new NpcInitializationData(newType, this.isRandom);
    }


    private Optional<String> getOptionalType() {
        return type != null ? Optional.of(type.toString()) : Optional.empty();
    }


    private Optional<Boolean> getOptionalRandom() {
        return isRandom != null ? Optional.of(isRandom) : Optional.empty();
    }


    public boolean tryToInitialize(NpcEntity entity) {
        World world = entity.getWorld();
        if(world == null)
            return false;

        if(isRandom != null && isRandom){
            Registry<NpcType> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_TYPE);
            List<RegistryKey<NpcType>> entryList =  registry.streamKeys().toList();
            Random random = new Random();
            RegistryKey<NpcType> randomKey = entryList.get(random.nextInt(entryList.size()));
            this.type = randomKey.getValue();
            this.isRandom = false;
        }

        if(type != null){
            Registry<NpcType> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_TYPE);
            NpcType npcType = registry.get(type);
            if(npcType == null)
                return false;
            RegistryEntry<NpcType> entry = registry.getEntry(npcType);
            this.type = null;
            EntityCategories category = null;
            if(!npcType.hasCategory(world, category))
                category = npcType.getNpcTextureData(world).getRandomCategory();

            entity.saveCategory(category);
            entity.saveNpcType(entry);

            return true;
        }

        return false;
    }
}
