package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
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
    public static final StreamCodec<RegistryFriendlyByteBuf, NpcInitializationData> PACKET_CODEC;

    private ResourceLocation type;
    private Boolean isRandom;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("Type").forGetter(NpcInitializationData::getOptionalType),
                Codec.BOOL.optionalFieldOf("IsRandom").forGetter(NpcInitializationData::getOptionalRandom)
        ).apply(instance, NpcInitializationData::new));

        PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), NpcInitializationData::getOptionalType,
                ByteBufCodecs.optional(ByteBufCodecs.BOOL), NpcInitializationData::getOptionalRandom,
                NpcInitializationData::new);
    }


    public NpcInitializationData(Optional<String> type, Optional<Boolean> isRandom) {
        this.type = type.map(MiddleEarth::fetchId).orElse(null);
        this.isRandom = isRandom == null || isRandom.isEmpty() || isRandom.get() == false ? null : isRandom.get();
    }

    public NpcInitializationData(ResourceLocation type, Boolean isRandom) {
        this.type = type;
        this.isRandom = isRandom;
    }

    public NpcInitializationData() {
        this.type = null;
        this.isRandom = null;
    }

    public NpcInitializationData withType(ResourceLocation newType) {
        return new NpcInitializationData(newType, this.isRandom);
    }


    private Optional<String> getOptionalType() {
        return type != null ? Optional.of(type.toString()) : Optional.empty();
    }


    private Optional<Boolean> getOptionalRandom() {
        return isRandom != null ? Optional.of(isRandom) : Optional.empty();
    }


    public boolean tryToInitialize(NpcEntity entity) {
        Level world = entity.level();
        if(world == null)
            return false;

        if(isRandom != null && isRandom){
            Registry<NpcType> registry = world.registryAccess().registryOrThrow(DynamicRegistriesME.NPC_TYPE);
            List<ResourceLocation> entryList = registry.keySet().stream().toList();
            if (entryList.isEmpty()) {
                return false;
            }
            Random random = new Random();
            this.type = entryList.get(random.nextInt(entryList.size()));
            this.isRandom = false;
        }

        if(type != null){
            Registry<NpcType> registry = world.registryAccess().registryOrThrow(DynamicRegistriesME.NPC_TYPE);
            NpcType npcType = registry.get(type);
            if(npcType == null)
                return false;
            Holder<NpcType> entry = registry.wrapAsHolder(npcType);
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
