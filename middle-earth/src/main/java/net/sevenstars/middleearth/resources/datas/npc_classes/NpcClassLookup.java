package net.sevenstars.middleearth.resources.datas.npc_classes;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.ArrayList;
import java.util.List;

public class NpcClassLookup {
    public static List<NpcClass> getAllNpcClasses(World world, List<Identifier> ids) {
        Registry<NpcClass> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
        List<NpcClass> list = new ArrayList<>();
        for(Identifier id : ids){
            list.add(registry.get(id));
        }
        return list;
    }

    public static List<NpcClass> getAllNpcClassesFromRace(World world, List<Identifier> ids, Identifier race){
        List<NpcClass> unsortedList = getAllNpcClasses(world, ids);
        List<NpcClass> list = new ArrayList<>();
        for(NpcClass npcClass : unsortedList){
            if(npcClass.getRace().equals(race))
                list.add(npcClass);
        }
        return list;
    }
    public static NpcClass getNpcClass(World world, Identifier id) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS).get(id);
    }
}
