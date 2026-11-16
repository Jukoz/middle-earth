package net.sevenstars.middleearth.registries.content.npcclasses;

import net.minecraft.item.Items;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData.Activity;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData.Period;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;

public class NpcRoutines {
    public final static RoutineData WATCH = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 3).add(Activity.ESCORT, 1);
    public final static RoutineData DAY_WATCH = RoutineData.create(Period.DAY).add(Activity.PATROL, 3).add(Activity.ESCORT, 1);
    public final static RoutineData GUARD = RoutineData.create(Period.SHIFTS).add(Activity.POST, 1);
    // Orcs stay out of the sun
    public final static RoutineData NIGHT_WATCH = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 1);

    // Riders and bowmen who keep watch and also bring in game
    public final static RoutineData HUNTING_WATCH = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 3).add(Activity.HUNT, 1);
    // Orcs raid after dark
    public final static RoutineData NIGHT_HUNTING_WATCH = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 2).add(Activity.HUNT, 1);
    // Scouts range out, live on what they catch and bring the rest back. Uruk-hai don't mind the sun
    public final static RoutineData SCOUT = RoutineData.create(Period.SHIFTS).add(Activity.HUNT, 3).add(Activity.PATROL, 1);
    public final static RoutineData NIGHT_SCOUT = RoutineData.create(Period.NIGHT).add(Activity.HUNT, 3).add(Activity.PATROL, 1);
    // Rangers live off the land they guard
    public final static RoutineData RANGER = RoutineData.create(Period.DAY).add(Activity.HUNT, 3).add(Activity.FORAGE, 1);

    public final static RoutineData GATHERER = RoutineData.create(Period.DAY).timid().add(Activity.VISIT, 1).add(Activity.TABLE, 1).add(Activity.FORAGE, 1);
    // Village folk farm the fields where the place has any
    public final static RoutineData GATHERER_AND_FISHER = RoutineData.create(Period.DAY).timid().add(Activity.VISIT, 1).add(Activity.TABLE, 1).add(Activity.FORAGE, 3).add(Activity.FISH, 2).add(Activity.FARM, 2);
    public final static RoutineData GATHERER_AND_HUNTER = RoutineData.create(Period.DAY).timid().add(Activity.VISIT, 1).add(Activity.TABLE, 1).add(Activity.FORAGE, 3).add(Activity.HUNT, 1).add(Activity.FARM, 2);
    public final static RoutineData HOBBIT = RoutineData.create(Period.DAY).timid().add(Activity.TABLE, 2).add(Activity.VISIT, 2).add(Activity.FORAGE, 4).add(Activity.FISH, 1).add(Activity.FARM, 2);
    // Bounders keep travelling inside the Shire biome they spawned in and never head for a settlement
    public final static RoutineData BOUNDS_WATCH = RoutineData.create(Period.SHIFTS).keepingBounds();
    public final static RoutineData ELVEN_HUNTER = RoutineData.create(Period.ALWAYS).add(Activity.HUNT, 4).add(Activity.FORAGE, 1);
    public final static RoutineData OUTLAW = RoutineData.create(Period.ALWAYS).add(Activity.HUNT, 2).add(Activity.FORAGE, 1);

    // [GENERAL] For classes with no routine of their own. The night ones are for orcs and goblins, who stay out of the sun
    // The rank and file patrol and go out with those who work beyond the walls
    public final static RoutineData SOLDIER = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 3).add(Activity.ESCORT, 1);
    public final static RoutineData NIGHT_SOLDIER = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 3).add(Activity.ESCORT, 1);
    // Picked men patrol and also stand at a post. Patrol is weighted twice as high
    public final static RoutineData ELITE = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 2).add(Activity.POST, 1);
    public final static RoutineData NIGHT_ELITE = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 2).add(Activity.POST, 1);
    // Leaders hold court, inspect their guards and visit their neighbours
    public final static RoutineData LEADER = RoutineData.create(Period.DAY).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.VISIT, 1);
    public final static RoutineData NIGHT_LEADER = RoutineData.create(Period.NIGHT).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.VISIT, 1);
    public final static RoutineData ARCHER = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 2).add(Activity.HUNT, 1);
    // Riders patrol on their mounts and only hunt when on foot
    public final static RoutineData NIGHT_RIDER = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 3).add(Activity.HUNT, 1);
    public final static RoutineData BRUTE = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 1);
    // Orc and goblin workers use the forge or hearth where there is one, and forage where there isn't
    public final static RoutineData NIGHT_WORKER = RoutineData.create(Period.NIGHT).working(StationKind.FORGE, StationKind.HEARTH, StationKind.WOOD_PILE)
            .add(Activity.WORK, 2).add(Activity.FORAGE, 1);

    public final static RoutineData NIGHT_RAIDER = RoutineData.create(Period.NIGHT).add(Activity.HUNT, 2).add(Activity.FORAGE, 1)
            .foraging(Items.BONE, Items.ROTTEN_FLESH, Items.BROWN_MUSHROOM);
    // Orcs without a household cut timber at night
    public final static RoutineData NIGHT_FELLER = RoutineData.create(Period.NIGHT).add(Activity.CHOP, 2).add(Activity.FORAGE, 1);
}
