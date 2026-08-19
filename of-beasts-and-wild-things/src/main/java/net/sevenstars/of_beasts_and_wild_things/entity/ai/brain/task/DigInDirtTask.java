package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntity;

public class DigInDirtTask extends Behavior<PheasantEntity> {
    public DigInDirtTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), 90);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, PheasantEntity entity) {
        return world.getBlockState(entity.blockPosition().below()).is(Blocks.ROOTED_DIRT) || world.getBlockState(entity.blockPosition().below()).is(Blocks.COARSE_DIRT);
    }

    @Override
    protected boolean canStillUse(ServerLevel world, PheasantEntity entity, long time) {
        return this.checkExtraStartConditions(world, entity);
    }

    @Override
    protected void tick(ServerLevel world, PheasantEntity entity, long time) {
        BlockParticleOption particles = new BlockParticleOption(ParticleTypes.BLOCK, world.getBlockState(entity.blockPosition().below()));
        world.sendParticles(particles, entity.getX() + entity.getLookAngle().x() * 0.3F, entity.getY(), entity.getZ() + entity.getLookAngle().z() * 0.3F, 5, 0.1, 0.15, 0.1, 0.5);
    }

    @Override
    protected void start(ServerLevel world, PheasantEntity entity, long time) {
        entity.setPose(Pose.DIGGING);
    }

    @Override
    protected void stop(ServerLevel world, PheasantEntity entity, long time) {
        world.playSound(entity, entity.blockPosition(), SoundEvents.ROOTED_DIRT_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
        entity.setPose(Pose.STANDING);

        if(world.getBlockState(entity.blockPosition().below()).is(Blocks.ROOTED_DIRT)) {
            ItemStack itemStack;
            double d = entity.getRandom().nextDouble();
            if(d <= 0.5) {
                itemStack = new ItemStack(Items.STICK);
            } else if(d <= 0.65) {
                itemStack = new ItemStack(Items.WHEAT_SEEDS);
            } else if(d <= 0.8) {
                itemStack = new ItemStack(Items.BEETROOT_SEEDS);
            } else if(d <= 0.9) {
                itemStack = new ItemStack(Items.PUMPKIN_SEEDS);
            } else {
                itemStack = new ItemStack(Items.MELON_SEEDS);
            }

            world.addFreshEntity(new ItemEntity(world, entity.getX() + entity.getLookAngle().x() * 0.3F, entity.getY(), entity.getZ() + entity.getLookAngle().z() * 0.3F, itemStack));
        }

        // Change Dirt
        if(world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            if(world.getBlockState(entity.blockPosition().below()).is(Blocks.ROOTED_DIRT)) {
                world.setBlock(entity.blockPosition().below(), Blocks.DIRT.defaultBlockState(), Block.UPDATE_CLIENTS);
            }
            if(world.getBlockState(entity.blockPosition().below()).is(Blocks.COARSE_DIRT)) {
                world.setBlock(entity.blockPosition().below(), Blocks.ROOTED_DIRT.defaultBlockState(), Block.UPDATE_CLIENTS);
            }
        }



            // Set cooldown to 3000t = 2min30s
        entity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, 3000);
    }
}
