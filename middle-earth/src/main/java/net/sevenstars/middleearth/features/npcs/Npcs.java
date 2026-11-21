package net.sevenstars.middleearth.features.npcs;

import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.middleearth.entity.goals.FollowHerderGoal;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.item.items.BuildersToolItem;
import net.sevenstars.middleearth.resources.datas.biome_events.SpawnGate;

public class Npcs {
    public static void register() {
        RoutineSites.register();
        StructureManagerService.register();
        StructureEditSession.register();
        FollowHerderGoal.register();
        BuildersToolItem.register();
        SpawnGate.register();
    }
}
