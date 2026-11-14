package net.sevenstars.middleearth.registries.content.npctypes;

import net.minecraft.item.Items;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Activity;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Period;

public class NpcRoutines {
    public final static RoutineData WATCH = RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 1);
    public final static RoutineData DAY_WATCH = RoutineData.create(Period.DAY).add(Activity.PATROL, 1);
    // Orcs keep out of the sun
    public final static RoutineData NIGHT_WATCH = RoutineData.create(Period.NIGHT).add(Activity.PATROL, 1);

    public final static RoutineData FISHER = RoutineData.create(Period.DAY).timid().add(Activity.MEET, 2).add(Activity.FISH, 1);
    public final static RoutineData GATHERER = RoutineData.create(Period.DAY).timid().add(Activity.MEET, 2).add(Activity.FORAGE, 1);
    public final static RoutineData GATHERER_AND_FISHER = RoutineData.create(Period.DAY).timid().add(Activity.MEET, 2).add(Activity.FORAGE, 3).add(Activity.FISH, 2);
    public final static RoutineData GATHERER_AND_HUNTER = RoutineData.create(Period.DAY).timid().add(Activity.MEET, 2).add(Activity.FORAGE, 3).add(Activity.HUNT, 1);
    public final static RoutineData HOBBIT = RoutineData.create(Period.DAY).timid().add(Activity.TEA, 2).add(Activity.MEET, 1).add(Activity.FORAGE, 4).add(Activity.FISH, 1)
            .foraging(Items.BROWN_MUSHROOM, Items.BROWN_MUSHROOM, Items.RED_MUSHROOM, Items.APPLE);
    // Bounders beat the bounds of the Shire, and are not found sitting in its villages
    public final static RoutineData BOUNDS_WATCH = RoutineData.create(Period.SHIFTS).keepingBounds();
    public final static RoutineData ELVEN_HUNTER = RoutineData.create(Period.ALWAYS).add(Activity.HUNT, 4).add(Activity.FORAGE, 1);
    public final static RoutineData OUTLAW = RoutineData.create(Period.ALWAYS).add(Activity.HUNT, 2).add(Activity.FORAGE, 1);

    public final static RoutineData NIGHT_RAIDER = RoutineData.create(Period.NIGHT).add(Activity.HUNT, 2).add(Activity.FORAGE, 1)
            .foraging(Items.BONE, Items.ROTTEN_FLESH, Items.BROWN_MUSHROOM);
    public final static RoutineData WOODCUTTER = RoutineData.create(Period.ALWAYS).add(Activity.FORAGE, 1)
            .foraging(Items.OAK_LOG, Items.OAK_LOG, Items.STICK);
}
