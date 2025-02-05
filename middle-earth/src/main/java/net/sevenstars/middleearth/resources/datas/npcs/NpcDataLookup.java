package net.sevenstars.middleearth.resources.datas.npcs;

import net.sevenstars.middleearth.resources.MiddleEarthNpcs;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class NpcDataLookup {
    public static List<NpcData> getAllNpcDatas(World world, List<Identifier> ids) {
        Registry<NpcData> registry = world.getRegistryManager().get(MiddleEarthNpcs.NPC_KEY);
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
            if(npcData.getRaceId().equals(race))
                list.add(npcData);
        }
        return list;
    }
}
