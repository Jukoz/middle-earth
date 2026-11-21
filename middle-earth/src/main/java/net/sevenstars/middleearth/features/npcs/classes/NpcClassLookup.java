package net.sevenstars.middleearth.features.npcs.classes;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NpcClassLookup {
    private static final Set<Identifier> missing = new HashSet<>();

    public static List<NpcClass> getAllNpcClasses(World world, List<Identifier> ids) {
        Registry<NpcClass> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
        List<NpcClass> list = new ArrayList<>();
        for(Identifier id : ids){
            NpcClass npcClass = registry.get(id);
            if(npcClass == null) {
                if(missing.add(id))
                    MiddleEarth.LOGGER.logError("Unknown npc class " + id);
                continue;
            }
            list.add(npcClass);
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
}
