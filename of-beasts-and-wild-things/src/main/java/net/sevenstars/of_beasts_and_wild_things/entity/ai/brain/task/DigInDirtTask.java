package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntity;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;

public class DigInDirtTask extends MultiTickTask<PheasantEntity> {
    public DigInDirtTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
                MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT), 100);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PheasantEntity entity) {
        return world.getBlockState(entity.getBlockPos().down()).isOf(Blocks.ROOTED_DIRT) || world.getBlockState(entity.getBlockPos().down()).isOf(Blocks.COARSE_DIRT);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, PheasantEntity entity, long time) {
        return this.shouldRun(world, entity);
    }

    @Override
    protected void keepRunning(ServerWorld world, PheasantEntity entity, long time) {
        BlockStateParticleEffect particles = new BlockStateParticleEffect(ParticleTypes.BLOCK, world.getBlockState(entity.getBlockPos().down()));
        world.spawnParticles(particles, entity.getX() + entity.getRotationVector().getX() * 0.5, entity.getY(), entity.getZ() + entity.getRotationVector().getZ() * 0.5, 5, 0.1, 0.15, 0.1, 0.5);
    }

    @Override
    protected void finishRunning(ServerWorld world, PheasantEntity entity, long time) {
        world.playSound(entity, entity.getBlockPos(), SoundEvents.BLOCK_ROOTED_DIRT_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);

        if(world.getBlockState(entity.getBlockPos().down()).isOf(Blocks.ROOTED_DIRT)) {
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

            world.spawnEntity(new ItemEntity(world, entity.getX() + entity.getRotationVector().getX() * 0.5, entity.getY(), entity.getZ() + entity.getRotationVector().getZ() * 0.5, itemStack));
        }

        // Change Dirt
        if(world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            if(world.getBlockState(entity.getBlockPos().down()).isOf(Blocks.ROOTED_DIRT)) {
                world.setBlockState(entity.getBlockPos().down(), Blocks.DIRT.getDefaultState(), Block.NOTIFY_LISTENERS);
            }
            if(world.getBlockState(entity.getBlockPos().down()).isOf(Blocks.COARSE_DIRT)) {
                world.setBlockState(entity.getBlockPos().down(), Blocks.ROOTED_DIRT.getDefaultState(), Block.NOTIFY_LISTENERS);
            }
        }



            // Set cooldown to 3000t = 2min30s
        entity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, 3000);
    }
}
