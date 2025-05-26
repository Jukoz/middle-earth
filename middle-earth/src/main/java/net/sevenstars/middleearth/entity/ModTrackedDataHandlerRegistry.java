package net.sevenstars.middleearth.entity;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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
        TrackedDataHandlerRegistry.register(ModTrackedDataHandlerRegistry.NPC_ENTITY_DATA);
        TrackedDataHandlerRegistry.register(ModTrackedDataHandlerRegistry.NPC_ENTITY_TEXTURE_DATA);
    }
}
