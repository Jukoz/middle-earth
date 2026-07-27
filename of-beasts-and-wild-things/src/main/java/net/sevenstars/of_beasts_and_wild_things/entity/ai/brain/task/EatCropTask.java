package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;

public class EatCropTask extends Behavior<SnailEntity> {

    public EatCropTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), 100);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverWorld, SnailEntity snailEntity) {
        return serverWorld.getBlockState(snailEntity.blockPosition().above()).is(BlockTags.CROPS) && serverWorld.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    @Override
    protected boolean canStillUse(ServerLevel world, SnailEntity entity, long time) {
        return checkExtraStartConditions(world, entity);
    }

    @Override
    protected void tick(ServerLevel world, SnailEntity entity, long time) {
        BlockParticleOption particles = new BlockParticleOption(ParticleTypes.BLOCK, world.getBlockState(entity.blockPosition().above()));
        world.sendParticles(particles, entity.getX(), entity.getY(), entity.getZ(), 10, 0.1, 0.4, 0.1, 1);
    }

    @Override
    protected void stop(ServerLevel world, SnailEntity entity, long time) {
        world.playSound(entity, entity.blockPosition(), SoundEvents.PLAYER_BURP, SoundSource.BLOCKS, 1.0f, 5f);

        // Break crop
        world.setBlock(entity.blockPosition().above(), Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
        world.destroyBlock(entity.blockPosition().above(), true, entity);

        // Make baby grow faster on eating
        if (entity.isBaby()) {
            entity.ageUp(60);
        }
        // 5% chance of spawning a baby snail
        else if(entity.getRandom().nextDouble() <= 0.05) {
            world.sendParticles(ParticleTypes.HAPPY_VILLAGER, entity.getX(), entity.getY() + 0.2, entity.getZ(), 15, 0.3, 0.7, 0.3, 1);
            SnailEntity babySnail = (SnailEntity) entity.getBreedOffspring(world,entity);
            if(babySnail != null) {
                babySnail.moveTo(entity.getX(), entity.getY(), entity.getZ());
                world.addFreshEntity(babySnail);
            }
        }

        // Set cooldown to 3000t = 2min30s
        entity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, 3000);
    }
}
