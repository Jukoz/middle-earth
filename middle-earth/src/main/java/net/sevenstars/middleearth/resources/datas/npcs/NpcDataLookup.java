package net.sevenstars.middleearth.resources.datas.npcs;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.ArrayList;
import java.util.List;

public class NpcDataLookup {
    public static List<NpcData> getAllNpcDatas(World world, List<Identifier> ids) {
        Registry<NpcData> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC);
        List<NpcData> list = new ArrayList<>();
        for(Identifier id : ids){
            list.add(registry.get(id));
        }
        return list;
    }

    public static List<NpcData> getAllNpcDatasFromRace(World world, List<Identifier> ids, Identifier race){
        List<NpcData> unsortedList = getAllNpcDatas(world, ids);
        List<NpcData> list = new ArrayList<>();
        for(NpcData npcData : unsortedList){
            if(npcData.getRace().equals(race))
                list.add(npcData);
        }
        return list;
    }
    public static NpcData getNpcData(World world, Identifier id) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC).get(id);
    }
}
