package net.sevenstars.middleearth.entity.npcs.util;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class ClientNpcEntityInitializer {
    public static void initializeNpcEntity(ClientWorld clientWorld, NpcEntity npcEntity){
        initializeForClient(clientWorld, npcEntity);
    }

    private static void initializeForClient(World clientWorld, NpcEntity npcEntity) {
        Identifier currentNpcDataId = npcEntity.getNpcDataId();
        if(NpcInitializerUtil.characterIdentifierExist(clientWorld, currentNpcDataId)){
            NpcInitializerUtil.generateCharacterTextures(clientWorld, currentNpcDataId, npcEntity);
        }
    }
}
