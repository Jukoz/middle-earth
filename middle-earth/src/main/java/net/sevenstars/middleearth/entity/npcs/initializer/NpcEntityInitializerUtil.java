package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;

public class NpcEntityInitializerUtil {
    public static boolean characterIdentifierExist(World world, Identifier npcClassId){
        if(npcClassId == null)
            return false;
        DynamicRegistryManager registryManager = world.getRegistryManager();
        NpcClass npcClass =  registryManager.getOrThrow(DynamicRegistriesME.NPC_CLASS).get(npcClassId);
        return npcClass != null;
    }
}
