package net.sevenstars.middleearth.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityData;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;

public class ModTrackedDataHandlerRegistry {

    public static final TrackedDataHandler<NpcEntityData> NPC_ENTITY_DATA;
    public static final TrackedDataHandler<NpcEntityTextureData> NPC_ENTITY_TEXTURE_DATA;

    static {
        NPC_ENTITY_DATA = TrackedDataHandler.create(NpcEntityData.PACKET_CODEC);
        NPC_ENTITY_TEXTURE_DATA = TrackedDataHandler.create(NpcEntityTextureData.PACKET_CODEC);
    }

    public static void register() {
        FabricTrackedDataRegistry.register(Identifier.of(MiddleEarth.MOD_ID, "npc_entity_data"), ModTrackedDataHandlerRegistry.NPC_ENTITY_DATA);
        FabricTrackedDataRegistry.register(Identifier.of(MiddleEarth.MOD_ID, "npc_entity_texture_data"), ModTrackedDataHandlerRegistry.NPC_ENTITY_TEXTURE_DATA);
    }
}
