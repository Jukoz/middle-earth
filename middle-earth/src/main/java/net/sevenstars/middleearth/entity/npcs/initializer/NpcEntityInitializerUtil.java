package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

public class NpcEntityInitializerUtil {
    public static boolean characterIdentifierExist(World world, Identifier typeId){
        if(typeId == null)
            return false;
        DynamicRegistryManager registryManager = world.getRegistryManager();
        NpcType type =  registryManager.getOrThrow(DynamicRegistriesME.NPC_TYPE).get(typeId);
        return type != null;
    }
}
