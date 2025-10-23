package net.sevenstars.api.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;

public class EatBerriesTask extends MultiTickTask<LivingEntity> {
    public EatBerriesTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
                MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT), 90);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        Vec3d rotationVector = entity.getRotationVector().normalize();
        BlockState state = world.getBlockState(entity.getBlockPos().add((int)Math.round(rotationVector.getX()), 0, (int)Math.round(rotationVector.getZ())));

        return state.isOf(Blocks.SWEET_BERRY_BUSH) && state.get(SweetBerryBushBlock.AGE) >= 2;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
        return this.shouldRun(world, entity);
    }

    @Override
    protected void keepRunning(ServerWorld world, LivingEntity entity, long time) {
        Vec3d rotationVector = entity.getRotationVector().normalize();
        BlockPos pos = entity.getBlockPos().add((int)Math.round(rotationVector.getX()), 0, (int)Math.round(rotationVector.getZ()));

        BlockStateParticleEffect particles = new BlockStateParticleEffect(ParticleTypes.BLOCK, world.getBlockState(pos));
        world.spawnParticles(particles, (entity.getX() + pos.getX()) / 2, entity.getY() + 0.5, (entity.getZ() + pos.getZ()) / 2, 10, 0.1, 0.4, 0.1, 1);
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        entity.setPose(EntityPose.DIGGING);
    }

    @Override
    protected void finishRunning(ServerWorld world, LivingEntity entity, long time) {
        world.playSound(entity, entity.getBlockPos(), SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 1.0f);
        entity.setPose(EntityPose.STANDING);

        Vec3d rotationVector = entity.getRotationVector().normalize();
        BlockPos pos = entity.getBlockPos().add((int)Math.round(rotationVector.getX()), 0, (int)Math.round(rotationVector.getZ()));

        if(world.getBlockState(pos).isOf(Blocks.SWEET_BERRY_BUSH) && world.getBlockState(pos).get(SweetBerryBushBlock.AGE) >= 2) {
            if(world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                world.setBlockState(pos, world.getBlockState(pos).with(SweetBerryBushBlock.AGE, 1));
            }

            ItemStack itemStack = new ItemStack(world.getBlockState(pos).getBlock().asItem());
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
        }



        // Set cooldown to 3000t = 2min30s
        entity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, 3000);
    }
}
