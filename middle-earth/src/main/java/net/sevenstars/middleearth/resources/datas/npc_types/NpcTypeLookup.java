package net.sevenstars.middleearth.resources.datas.npc_types;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.ArrayList;
import java.util.List;

public class NpcTypeLookup {
    public static List<NpcType> getAllNpcTypes(World world, List<Identifier> ids) {
        Registry<NpcType> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_TYPE);
        List<NpcType> list = new ArrayList<>();
        for(Identifier id : ids){
            list.add(registry.get(id));
        }
        return list;
    }

    public static List<NpcType> getAllNpcTypesFromRace(World world, List<Identifier> ids, Identifier race){
        List<NpcType> unsortedList = getAllNpcTypes(world, ids);
        List<NpcType> list = new ArrayList<>();
        for(NpcType npcType : unsortedList){
            if(npcType.getRace().equals(race))
                list.add(npcType);
        }
        return list;
    }
    public static NpcType getNpcType(World world, Identifier id) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_TYPE).get(id);
    }
}
