package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import org.jetbrains.annotations.Nullable;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.ArrayDeque;
import java.util.List;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.SPOT_RANGE;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.itemName;
import static net.sevenstars.middleearth.entity.npcs.routine.activity.StationActivity.WORK_RANGE;

public class ChoppingActivity extends RoutineActivity {
    private static final int CHOPPING_TIME = 20 * 40;
    private static final int CHOPPING_TIME_VARIATION = 20 * 20;

    // The most recently worked trees are skipped for a while
    private static final int RECENT_TREES = 3;
    private static final int TREE_PASSES = 2;

    private static final int CHOP_SWING_ONE_IN = 3;

    private static final float CHOP_SOUND_VOLUME = 0.8f;
    private static final float CHOP_SOUND_PITCH = 0.9f;
    private static final float CHOP_SOUND_PITCH_VARIATION = 0.2f;

    // The base of the trunk being worked at, and the rock face being worked
    private @Nullable BlockPos tree = null;
    private final ArrayDeque<BlockPos> recentTrees = new ArrayDeque<>();

    public ChoppingActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        tree = nextTree(world, home);
        if(tree == null)
            return engine.cannotStart(picked, "no tree within reach of home");
        engine.spot = engine.standAt(world, tree);
        engine.lookTarget = Vec3d.ofCenter(tree.up());
        return true;
    }

    // The trees are around the place, as far out as foraging goes. A tree felled since the scan is skipped
    private @Nullable BlockPos nextTree(ServerWorld world, BlockPos home) {
        List<BlockPos> trees = engine.site.getTrees();
        int range = SPOT_RANGE + engine.site.getExtent();
        int start = npc.getRandom().nextInt(Math.max(1, trees.size()));
        for(int pass = 0; pass < TREE_PASSES; pass++) {
            for(int i = 0; i < trees.size(); i++) {
                BlockPos candidate = trees.get((start + i) % trees.size());
                if(!candidate.isWithinDistance(home, range) || recentTrees.contains(candidate)
                        || !isLoaded(world, candidate) || !world.getBlockState(candidate).isIn(BlockTags.LOGS))
                    continue;
                // A bush is a log with leaves on every side, leaving no room to stand and swing at it
                if(NestSurvey.standNextTo(world, candidate, home) == null)
                    continue;
                recentTrees.addLast(candidate);
                if(recentTrees.size() > RECENT_TREES)
                    recentTrees.removeFirst();
                return candidate;
            }
            // With few trees about, one worked recently is better than none
            recentTrees.clear();
        }
        return null;
    }

    @Override
    public boolean beforeWork(ServerWorld world, Random random, long time) {
        BlockPos worked = tree;
        if(worked != null && !engine.canSee(world, worked)) {
            engine.abandoned("what it was to work at, " + worked.toShortString() + ", is behind a wall");
            engine.finish(world, REST + random.nextInt(REST_VARIATION));
            return true;
        }
        return false;
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + CHOPPING_TIME + random.nextInt(CHOPPING_TIME_VARIATION);
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            engine.carried = tree != null && world.getBlockState(tree).isIn(BlockTags.LOGS) ? world.getBlockState(tree).getBlock().asItem() : null;
            if(engine.carried == null)
                engine.abandoned("the tree was gone");
            else
                engine.did("carries " + itemName(engine.carried) + " from the tree at " + tree.toShortString());
            // Stacked on the wood pile of the place, where there is one
            BlockPos home = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
            engine.dropOff = engine.carried == null ? null : engine.site.pickStation(List.of(StationKind.WOOD_PILE), home, WORK_RANGE);
            engine.startReturn(world);
        } else if(tree != null && random.nextInt(CHOP_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.playSound(null, tree, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, CHOP_SOUND_VOLUME, CHOP_SOUND_PITCH + random.nextFloat() * CHOP_SOUND_PITCH_VARIATION);
            cracks(world, tree.up());
        }
    }
}
