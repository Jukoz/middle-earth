package net.sevenstars.middleearth.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;

public class ModTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<String> FACTION_ID;
    public static final TrackedDataHandler<String> NPC_DATA_ID;
    public static final TrackedDataHandler<String> CATEGORY;
    public static final TrackedDataHandler<NpcEntityTextureData> NPC_ENTITY_TEXTURE_DATA;
    public static final TrackedDataHandler<BlockPos> STRUCTURE_MANAGER_HOST_POS;

    public static void register() {
        of("faction_id", FACTION_ID);
        of("npc_data_id", NPC_DATA_ID);
        of("category", CATEGORY);
        of("npc_entity_texture_data", NPC_ENTITY_TEXTURE_DATA);
        of("structure_manager_host_pos", STRUCTURE_MANAGER_HOST_POS);
    }

    private static void of(String name, TrackedDataHandler<?> dataHandler) {
        FabricTrackedDataRegistry.register(Identifier.of(MiddleEarth.MOD_ID, name), dataHandler);
    }

    static {
        FACTION_ID = TrackedDataHandler.create(PacketCodecs.STRING);
        NPC_DATA_ID = TrackedDataHandler.create(PacketCodecs.STRING);
        CATEGORY = TrackedDataHandler.create(PacketCodecs.STRING);
        NPC_ENTITY_TEXTURE_DATA = TrackedDataHandler.create(NpcEntityTextureData.PACKET_CODEC);
        STRUCTURE_MANAGER_HOST_POS = TrackedDataHandler.create(BlockPos.PACKET_CODEC);
    }
}
