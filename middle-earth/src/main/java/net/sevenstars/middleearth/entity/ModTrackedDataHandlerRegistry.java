package net.sevenstars.middleearth.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityTextureData;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

public class ModTrackedDataHandlerRegistry {

    public static final TrackedDataHandler<Long> INITIALIZATION_TICK;
    public static final TrackedDataHandler<String> FACTION_ID;
    public static final TrackedDataHandler<String> NPC_DATA_ID;
    public static final TrackedDataHandler<String> CATEGORY;
    public static final TrackedDataHandler<NpcEntityTextureData> NPC_ENTITY_TEXTURE_DATA;
    public static final TrackedDataHandler<BlockPos> STRUCTURE_MANAGER_HOST_POS;
    public static final TrackedDataHandler<RegistryEntry<SpiderVariant>> SPIDER_VARIANT;
    public static final TrackedDataHandler<RegistryEntry<GreatHornVariant>> GREAT_HORN_VARIANT;

    public static void register() {
        of("initialization_tick", INITIALIZATION_TICK);
        of("faction_id", FACTION_ID);
        of("npc_data_id", NPC_DATA_ID);
        of("category", CATEGORY);
        of("npc_entity_texture_data", NPC_ENTITY_TEXTURE_DATA);
        of("structure_manager_host_pos", STRUCTURE_MANAGER_HOST_POS);
        of("spider_variant", SPIDER_VARIANT);
        of("great_horn_variant", GREAT_HORN_VARIANT);
    }

    private static void of(String name, TrackedDataHandler<?> dataHandler) {
        FabricTrackedDataRegistry.register(Identifier.of(MiddleEarth.MOD_ID, name), dataHandler);
    }

    static {
        INITIALIZATION_TICK = TrackedDataHandler.create(PacketCodecs.LONG);
        FACTION_ID = TrackedDataHandler.create(PacketCodecs.STRING);
        NPC_DATA_ID = TrackedDataHandler.create(PacketCodecs.STRING);
        CATEGORY = TrackedDataHandler.create(PacketCodecs.STRING);
        NPC_ENTITY_TEXTURE_DATA = TrackedDataHandler.create(NpcEntityTextureData.PACKET_CODEC);
        STRUCTURE_MANAGER_HOST_POS = TrackedDataHandler.create(BlockPos.PACKET_CODEC);
        SPIDER_VARIANT = TrackedDataHandler.create(SpiderVariant.PACKET_CODEC);
        GREAT_HORN_VARIANT = TrackedDataHandler.create(GreatHornVariant.PACKET_CODEC);
    }
}
