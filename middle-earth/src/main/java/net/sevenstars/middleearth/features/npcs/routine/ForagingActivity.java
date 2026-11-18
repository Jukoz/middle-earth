package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.registries.content.forage.ForageTables;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;

import java.util.List;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.SPOT_RANGE;

class ForagingActivity extends RoutineActivity {
    private static final int FORAGING_TIME = 20 * 15;
    private static final int FORAGING_TIME_VARIATION = 20 * 10;

    private static final int FORAGE_ROAM = 3;
    static final double ROAM_SPEED = 0.75;

    private static final List<Item> DEFAULT_FORAGE = List.of(Items.BROWN_MUSHROOM, Items.SWEET_BERRIES, Items.APPLE);

    private static final int FORAGE_SWING_ONE_IN = 3;
    private static final int FORAGE_ROAM_ONE_IN = 4;

    ForagingActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        Random random = npc.getRandom();
        int index = pickNear(routine.site.getGrounds(), home, SPOT_RANGE + routine.site.getExtent(), random);
        if(index < 0)
            return routine.cannotStart(picked, "no open ground within reach of home");
        routine.spot = routine.site.getGrounds().get(index);
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.phaseEnd = time + FORAGING_TIME + random.nextInt(FORAGING_TIME_VARIATION);
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            List<Item> forage = forage(world);
            routine.carried = forage.get(random.nextInt(forage.size()));
            routine.startReturn(world);
        } else if(random.nextInt(FORAGE_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
        } else if(random.nextInt(FORAGE_ROAM_ONE_IN) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
            npc.getNavigation().startMovingTo(routine.spot.getX() + random.nextBetween(-FORAGE_ROAM, FORAGE_ROAM), routine.spot.getY(), routine.spot.getZ() + random.nextBetween(-FORAGE_ROAM, FORAGE_ROAM), routine.speed * ROAM_SPEED);
        }
    }

    // What its routine sends it for, else whatever grows where it is
    private List<Item> forage(ServerWorld world) {
        RoutineData data = npc.getRoutine();
        if(data != null && !data.getForage().isEmpty())
            return data.getForage();
        List<Item> found = ForageTables.itemsAt(world, routine.spot);
        return found.isEmpty() ? DEFAULT_FORAGE : found;
    }
}
