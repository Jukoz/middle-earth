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
    // Orcs keep out of the sun
    public final static RoutineData NIGHT_WATCH = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 1);

    // Riders and bowmen who keep the watch and bring in game besides
    public final static RoutineData HUNTING_WATCH = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 3).add(Activity.HUNT, 1);
    // Orcs go raiding after dark
    public final static RoutineData NIGHT_HUNTING_WATCH = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 2).add(Activity.HUNT, 1);
    // Scouts range out and live on what they catch, and bring the rest back. The Uruk-hai do not mind the sun
    public final static RoutineData SCOUT = RoutineData.create(Period.SHIFTS).add(Activity.HUNT, 3).add(Activity.PATROL, 1);
    public final static RoutineData NIGHT_SCOUT = RoutineData.create(Period.NIGHT).add(Activity.HUNT, 3).add(Activity.PATROL, 1);
    // Rangers live off the land they guard
    public final static RoutineData RANGER = RoutineData.create(Period.DAY).add(Activity.HUNT, 3).add(Activity.FORAGE, 1);

    public final static RoutineData GATHERER = RoutineData.create(Period.DAY).timid().add(Activity.VISIT, 1).add(Activity.TABLE, 1).add(Activity.FORAGE, 1);
    // Village folk work the fields where the place has any
    public final static RoutineData GATHERER_AND_FISHER = RoutineData.create(Period.DAY).timid().add(Activity.VISIT, 1).add(Activity.TABLE, 1).add(Activity.FORAGE, 3).add(Activity.FISH, 2).add(Activity.FARM, 2);
    public final static RoutineData GATHERER_AND_HUNTER = RoutineData.create(Period.DAY).timid().add(Activity.VISIT, 1).add(Activity.TABLE, 1).add(Activity.FORAGE, 3).add(Activity.HUNT, 1).add(Activity.FARM, 2);
    public final static RoutineData HOBBIT = RoutineData.create(Period.DAY).timid().add(Activity.TABLE, 2).add(Activity.VISIT, 2).add(Activity.FORAGE, 4).add(Activity.FISH, 1).add(Activity.FARM, 2);
    // Bounders beat the bounds of the Shire, and are not found sitting in its villages
    public final static RoutineData BOUNDS_WATCH = RoutineData.create(Period.SHIFTS).keepingBounds();
    public final static RoutineData ELVEN_HUNTER = RoutineData.create(Period.ALWAYS).add(Activity.HUNT, 4).add(Activity.FORAGE, 1);
    public final static RoutineData OUTLAW = RoutineData.create(Period.ALWAYS).add(Activity.HUNT, 2).add(Activity.FORAGE, 1);

    // [GENERAL] For the classes that have nothing of their own. The night ones are for the orcs and goblins, who keep out of the sun
    // The rank and file walk the rounds, and go out with those who work beyond the walls
    public final static RoutineData SOLDIER = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 3).add(Activity.ESCORT, 1);
    public final static RoutineData NIGHT_SOLDIER = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 3).add(Activity.ESCORT, 1);
    // Picked men: stand at a post as often as they walk
    public final static RoutineData ELITE = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 2).add(Activity.POST, 1);
    public final static RoutineData NIGHT_ELITE = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 2).add(Activity.POST, 1);
    // Leaders sit at court, look over their guards and call on their neighbours
    public final static RoutineData LEADER = RoutineData.create(Period.DAY).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.VISIT, 1);
    public final static RoutineData NIGHT_LEADER = RoutineData.create(Period.NIGHT).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.VISIT, 1);
    public final static RoutineData ARCHER = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 2).add(Activity.HUNT, 1);
    // Riders go round on their mounts, and only hunt where they are on foot
    public final static RoutineData NIGHT_RIDER = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 3).add(Activity.HUNT, 1);
    public final static RoutineData BRUTE = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 1);
    // Orc and goblin hands, at the forge or the fire where there is one and out foraging where not
    public final static RoutineData NIGHT_WORKER = RoutineData.create(Period.NIGHT).working(StationKind.FORGE, StationKind.HEARTH, StationKind.WOOD_PILE)
            .add(Activity.WORK, 2).add(Activity.FORAGE, 1);

    public final static RoutineData NIGHT_RAIDER = RoutineData.create(Period.NIGHT).add(Activity.HUNT, 2).add(Activity.FORAGE, 1)
            .foraging(Items.BONE, Items.ROTTEN_FLESH, Items.BROWN_MUSHROOM);
    // Orcs that belong to no household cut timber at night
    public final static RoutineData NIGHT_FELLER = RoutineData.create(Period.NIGHT).add(Activity.CHOP, 2).add(Activity.FORAGE, 1);
}
