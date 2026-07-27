package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

public class NpcEntityInitializerUtil {
    public static boolean characterIdentifierExist(Level world, ResourceLocation typeId){
        if(typeId == null)
            return false;
        return world.registryAccess().registryOrThrow(DynamicRegistriesME.NPC_TYPE).containsKey(typeId);
    }
}
