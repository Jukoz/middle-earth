package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;

public class EatCropTask extends MultiTickTask<SnailEntity> {

    public EatCropTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
                MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT), 100);
    }

    protected boolean shouldRun(ServerWorld serverWorld, SnailEntity snailEntity) {
        return serverWorld.getBlockState(snailEntity.getBlockPos().up()).isIn(BlockTags.CROPS) && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, SnailEntity entity, long time) {
        return shouldRun(world, entity);
    }

    @Override
    protected void keepRunning(ServerWorld world, SnailEntity entity, long time) {
        BlockStateParticleEffect particles = new BlockStateParticleEffect(ParticleTypes.BLOCK, world.getBlockState(entity.getBlockPos().up()));
        world.spawnParticles(particles, entity.getX(), entity.getY(), entity.getZ(), 10, 0.1, 0.4, 0.1, 1);
    }

    @Override
    protected void finishRunning(ServerWorld world, SnailEntity entity, long time) {
        System.out.println("Eating crops");

        world.playSound(entity, entity.getBlockPos(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.BLOCKS, 1.0f, 5f);

        world.setBlockState(entity.getBlockPos().up(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
        world.breakBlock(entity.getBlockPos().up(), true, entity);

        if (entity.isBaby()) {
            entity.growUp(60);
        }
        else if(entity.getRandom().nextDouble() <= 0.05) {
            world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, entity.getX(), entity.getY() + 0.2, entity.getZ(), 15, 0.3, 0.7, 0.3, 1);
            SnailEntity babySnail = ((EntityType<SnailEntity>)EntityType.get(OfBeastsAndWildThings.MOD_ID + ":snail").get()).create(world, SpawnReason.BREEDING);
            babySnail.updatePosition(entity.getX(), entity.getY(), entity.getZ());
            world.spawnEntity(babySnail);
        }

        entity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, 200);
    }
}
