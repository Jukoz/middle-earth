package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.registries.content.forage.ForageTables;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.List;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.SPOT_RANGE;

public class ForagingActivity extends RoutineActivity {
    private static final int FORAGING_TIME = 20 * 15;
    private static final int FORAGING_TIME_VARIATION = 20 * 10;

    private static final int FORAGE_ROAM = 3;
    public static final double ROAM_SPEED = 0.75;

    private static final List<Item> DEFAULT_FORAGE = List.of(Items.BROWN_MUSHROOM, Items.SWEET_BERRIES, Items.APPLE);

    private static final int FORAGE_SWING_ONE_IN = 3;
    private static final int FORAGE_ROAM_ONE_IN = 4;

    public ForagingActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        Random random = npc.getRandom();
        int index = pickNear(engine.site.getGrounds(), home, SPOT_RANGE + engine.site.getExtent(), random);
        if(index < 0)
            return engine.cannotStart(picked, "no open ground within reach of home");
        engine.spot = engine.site.getGrounds().get(index);
        return true;
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + FORAGING_TIME + random.nextInt(FORAGING_TIME_VARIATION);
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            List<Item> forage = forage(world);
            engine.carried = forage.get(random.nextInt(forage.size()));
            engine.startReturn(world);
        } else if(random.nextInt(FORAGE_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
        } else if(random.nextInt(FORAGE_ROAM_ONE_IN) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
            npc.getNavigation().startMovingTo(engine.spot.getX() + random.nextBetween(-FORAGE_ROAM, FORAGE_ROAM), engine.spot.getY(), engine.spot.getZ() + random.nextBetween(-FORAGE_ROAM, FORAGE_ROAM), engine.speed * ROAM_SPEED);
        }
    }

    // What its routine sends the npc out for, where that's something particular. Otherwise whatever grows in the land
    // it's in
    private List<Item> forage(ServerWorld world) {
        RoutineData routine = npc.getRoutine();
        if(routine != null && !routine.getForage().isEmpty())
            return routine.getForage();
        List<Item> found = ForageTables.itemsAt(world, engine.spot);
        return found.isEmpty() ? DEFAULT_FORAGE : found;
    }
}
