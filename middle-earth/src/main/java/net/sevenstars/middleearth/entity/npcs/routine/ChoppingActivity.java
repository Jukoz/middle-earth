package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.List;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.SPOT_RANGE;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.itemName;
import static net.sevenstars.middleearth.entity.npcs.routine.StationActivity.WORK_RANGE;

class ChoppingActivity extends RoutineActivity {
    private static final int CHOPPING_TIME = 20 * 40;
    private static final int CHOPPING_TIME_VARIATION = 20 * 20;

    private static final int RECENT_TREES = 3;
    private static final int TREE_PASSES = 2;

    private static final int CHOP_SWING_ONE_IN = 3;

    private static final float CHOP_SOUND_VOLUME = 0.8f;
    private static final float CHOP_SOUND_PITCH = 0.9f;
    private static final float CHOP_SOUND_PITCH_VARIATION = 0.2f;

    private @Nullable BlockPos tree = null;
    private final ArrayDeque<BlockPos> recentTrees = new ArrayDeque<>();

    ChoppingActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        tree = nextTree(world, home);
        if(tree == null)
            return routine.cannotStart(picked, "no tree within reach of home");
        routine.spot = routine.standAt(world, tree);
        routine.lookTarget = Vec3d.ofCenter(tree.up());
        return true;
    }

    private @Nullable BlockPos nextTree(ServerWorld world, BlockPos home) {
        List<BlockPos> trees = routine.site.getTrees();
        int range = SPOT_RANGE + routine.site.getExtent();
        int start = npc.getRandom().nextInt(Math.max(1, trees.size()));
        for(int pass = 0; pass < TREE_PASSES; pass++) {
            for(int i = 0; i < trees.size(); i++) {
                BlockPos candidate = trees.get((start + i) % trees.size());
                if(!candidate.isWithinDistance(home, range) || recentTrees.contains(candidate)
                        || !isLoaded(world, candidate) || !world.getBlockState(candidate).isIn(BlockTags.LOGS))
                    continue;
                // A bush is a log with leaves on every side: no room to stand and swing
                if(NestSurvey.standNextTo(world, candidate, home) == null)
                    continue;
                recentTrees.addLast(candidate);
                if(recentTrees.size() > RECENT_TREES)
                    recentTrees.removeFirst();
                return candidate;
            }
            // With few trees about, a recently worked one beats none
            recentTrees.clear();
        }
        return null;
    }

    @Override
    boolean beforeWork(ServerWorld world, Random random, long time) {
        return leaveIfHidden(world, random, tree);
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.phaseEnd = time + CHOPPING_TIME + random.nextInt(CHOPPING_TIME_VARIATION);
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            routine.carried = tree != null && world.getBlockState(tree).isIn(BlockTags.LOGS) ? world.getBlockState(tree).getBlock().asItem() : null;
            if(routine.carried == null)
                routine.abandoned("the tree was gone");
            else
                routine.did("carries " + itemName(routine.carried) + " from the tree at " + tree.toShortString());
            BlockPos home = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
            routine.dropOff = routine.carried == null ? null : routine.site.pickStation(List.of(StationKind.WOOD_PILE), home, WORK_RANGE);
            routine.startReturn(world);
        } else if(tree != null && random.nextInt(CHOP_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.playSound(null, tree, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, CHOP_SOUND_VOLUME, CHOP_SOUND_PITCH + random.nextFloat() * CHOP_SOUND_PITCH_VARIATION);
            cracks(world, tree.up());
        }
    }
}
