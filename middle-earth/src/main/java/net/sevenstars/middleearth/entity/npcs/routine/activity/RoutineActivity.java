package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.block.BlockState;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.List;

public abstract class RoutineActivity {
    protected static final int CRACK_PARTICLES = 4;
    protected static final double CRACK_SPREAD = 0.25;
    protected static final double CRACK_SPEED = 0.05;

    protected final NpcRoutine engine;
    protected final NpcEntity npc;

    protected RoutineActivity(NpcRoutine engine) {
        this.engine = engine;
        this.npc = engine.npc;
    }

    // False when the activity could not be started, having been put on hold
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        return true;
    }

    // True when the arrival was spent on something else and the work does not start yet
    public boolean beforeWork(ServerWorld world, Random random, long time) {
        return false;
    }

    public void arrive(ServerWorld world, Random random, long time) {
    }

    public void work(ServerWorld world, Random random, boolean done, long time) {
    }

    protected static int pickNear(List<BlockPos> spots, BlockPos home, int range, Random random) {
        int start = random.nextInt(Math.max(1, spots.size()));
        for(int i = 0; i < spots.size(); i++) {
            int index = (start + i) % spots.size();
            if(spots.get(index).isWithinDistance(home, range))
                return index;
        }
        return -1;
    }

    protected void cracks(ServerWorld world, BlockPos block) {
        BlockState state = world.getBlockState(block);
        if(!state.isAir())
            world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5, CRACK_PARTICLES, CRACK_SPREAD, CRACK_SPREAD, CRACK_SPREAD, CRACK_SPEED);
    }
}
