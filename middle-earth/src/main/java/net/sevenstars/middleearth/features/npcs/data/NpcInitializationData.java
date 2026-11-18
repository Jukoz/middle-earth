package net.sevenstars.middleearth.features.npcs.data;

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
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class NpcInitializationData {
    public static final Codec<NpcInitializationData> CODEC;
    public static final PacketCodec<RegistryByteBuf, NpcInitializationData> PACKET_CODEC;

    private Identifier npcClassId;
    private Boolean isRandom;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("Type").forGetter(NpcInitializationData::getOptionalNpcClass),
                Codec.BOOL.optionalFieldOf("IsRandom").forGetter(NpcInitializationData::getOptionalRandom)
        ).apply(instance, NpcInitializationData::new));

        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(PacketCodecs.STRING), NpcInitializationData::getOptionalNpcClass,
                PacketCodecs.optional(PacketCodecs.BOOLEAN), NpcInitializationData::getOptionalRandom,
                NpcInitializationData::new);
    }


    public NpcInitializationData(Optional<String> npcClassId, Optional<Boolean> isRandom) {
        this.npcClassId = npcClassId.map(MiddleEarth::ofId).orElse(null);
        this.isRandom = isRandom == null || isRandom.isEmpty() || isRandom.get() == false ? null : isRandom.get();
    }

    public NpcInitializationData(Identifier npcClassId, Boolean isRandom) {
        this.npcClassId = npcClassId;
        this.isRandom = isRandom;
    }

    public NpcInitializationData() {
        this.npcClassId = null;
        this.isRandom = null;
    }

    public NpcInitializationData withNpcClass(Identifier newNpcClassId) {
        return new NpcInitializationData(newNpcClassId, this.isRandom);
    }


    private Optional<String> getOptionalNpcClass() {
        return npcClassId != null ? Optional.of(npcClassId.toString()) : Optional.empty();
    }


    private Optional<Boolean> getOptionalRandom() {
        return isRandom != null ? Optional.of(isRandom) : Optional.empty();
    }


    public boolean tryToInitialize(NpcEntity entity) {
        World world = entity.getEntityWorld();
        if(world == null)
            return false;

        if(isRandom != null && isRandom){
            Registry<NpcClass> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
            List<RegistryKey<NpcClass>> entryList =  registry.streamKeys().toList();
            Random random = new Random();
            RegistryKey<NpcClass> randomKey = entryList.get(random.nextInt(entryList.size()));
            this.npcClassId = randomKey.getValue();
            this.isRandom = false;
        }

        if(npcClassId != null){
            Registry<NpcClass> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
            NpcClass npcClass = registry.get(npcClassId);
            if(npcClass == null)
                return false;
            RegistryEntry<NpcClass> entry = registry.getEntry(npcClass);
            this.npcClassId = null;
            EntityCategories category = null;
            if(!npcClass.hasCategory(world, category))
                category = npcClass.getNpcTextureData(world).getRandomCategory();

            entity.saveCategory(category);
            entity.saveNpcClass(entry);

            return true;
        }

        return false;
    }
}
