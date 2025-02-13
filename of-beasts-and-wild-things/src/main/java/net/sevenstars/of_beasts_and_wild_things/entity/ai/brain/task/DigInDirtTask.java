package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntity;

public class DigInDirtTask extends MultiTickTask<PheasantEntity> {
    public DigInDirtTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
                MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT), 100);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PheasantEntity entity) {
        return world.getBlockState(entity.getBlockPos().down()).isOf(Blocks.ROOTED_DIRT) && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, PheasantEntity entity, long time) {
        return this.shouldRun(world, entity);
    }

    @Override
    protected void keepRunning(ServerWorld world, PheasantEntity entity, long time) {
        world.spawnParticles(ParticleTypes.LARGE_SMOKE, entity.getX(), entity.getY(), entity.getZ(), 10, 0.2, 0.2, 0.2, 5);
    }
}
