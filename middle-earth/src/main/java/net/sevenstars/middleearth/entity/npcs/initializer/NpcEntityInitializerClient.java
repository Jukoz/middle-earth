package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NpcEntityInitializerClient {
    public static void initializeNpcEntity(ClientLevel clientWorld, NpcEntity npcEntity){
        initializeForClient(clientWorld, npcEntity);
    }

    private static void initializeForClient(Level clientWorld, NpcEntity npcEntity) {
        if(NpcEntityInitializerUtil.characterIdentifierExist(clientWorld, npcEntity.getNpcTypeIdentifier())){
            NpcGenerator.generateCharacterTextures(clientWorld, npcEntity);
        }
    }
}
