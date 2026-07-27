package net.sevenstars.middleearth.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;
import net.sevenstars.middleearth.entity.npcs.data.NpcData;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.entity.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

public class TrackedDataHandlerRegistryME {
    public static final EntityDataSerializer<NpcData> NPC_DATA;
    public static final EntityDataSerializer<NpcInitializationData> NPC_INITIALIZATION_DATA;
    public static final EntityDataSerializer<NpcTextureData> NPC_TEXTURE_DATA;


    public static final EntityDataSerializer<Long> INITIALIZATION_TICK;
    public static final EntityDataSerializer<String> FACTION_ID;
    public static final EntityDataSerializer<String> NPC_DATA_ID;
    public static final EntityDataSerializer<String> CATEGORY;
    public static final EntityDataSerializer<BlockPos> STRUCTURE_MANAGER_HOST_POS;
    public static final EntityDataSerializer<Holder<SpiderVariant>> SPIDER_VARIANT;
    public static final EntityDataSerializer<Holder<GreatHornVariant>> GREAT_HORN_VARIANT;

    public static void register() {
        of("npc_data", NPC_DATA);
        of("npc_initialization_data", NPC_INITIALIZATION_DATA);
        of("npc_texture_data", NPC_TEXTURE_DATA);
        of("initialization_tick", INITIALIZATION_TICK);
        of("faction_id", FACTION_ID);
        of("npc_data_id", NPC_DATA_ID);
        of("category", CATEGORY);
        of("structure_manager_host_pos", STRUCTURE_MANAGER_HOST_POS);
        of("spider_variant", SPIDER_VARIANT);
        of("great_horn_variant", GREAT_HORN_VARIANT);
    }

    private static void of(String name, EntityDataSerializer<?> dataHandler) {
        RegistrationBridge.register(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), dataHandler);
    }

    static {
        NPC_INITIALIZATION_DATA = EntityDataSerializer.forValueType(NpcInitializationData.PACKET_CODEC);
        NPC_DATA = EntityDataSerializer.forValueType(NpcData.PACKET_CODEC);
        INITIALIZATION_TICK = EntityDataSerializer.forValueType(ByteBufCodecs.VAR_LONG);
        FACTION_ID = EntityDataSerializer.forValueType(ByteBufCodecs.STRING_UTF8);
        NPC_DATA_ID = EntityDataSerializer.forValueType(ByteBufCodecs.STRING_UTF8);
        CATEGORY = EntityDataSerializer.forValueType(ByteBufCodecs.STRING_UTF8);
        NPC_TEXTURE_DATA = EntityDataSerializer.forValueType(NpcTextureData.PACKET_CODEC);
        STRUCTURE_MANAGER_HOST_POS = EntityDataSerializer.forValueType(BlockPos.STREAM_CODEC);
        SPIDER_VARIANT = EntityDataSerializer.forValueType(SpiderVariant.PACKET_CODEC);
        GREAT_HORN_VARIANT = EntityDataSerializer.forValueType(GreatHornVariant.PACKET_CODEC);
    }
}
