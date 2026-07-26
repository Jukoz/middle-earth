package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NpcEntityInitializerClient {
    public static void initializeNpcEntity(ClientWorld clientWorld, NpcEntity npcEntity){
        initializeForClient(clientWorld, npcEntity);
    }

    private static void initializeForClient(World clientWorld, NpcEntity npcEntity) {
        if(NpcEntityInitializerUtil.characterIdentifierExist(clientWorld, npcEntity.getNpcTypeIdentifier())){
            NpcGenerator.generateCharacterTextures(clientWorld, npcEntity);
        }
    }
}
