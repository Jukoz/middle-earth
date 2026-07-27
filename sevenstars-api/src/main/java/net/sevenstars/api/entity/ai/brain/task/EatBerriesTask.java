package net.sevenstars.api.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EatBerriesTask extends Behavior<LivingEntity> {
    public EatBerriesTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), 90);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, LivingEntity entity) {
        Vec3 rotationVector = entity.getLookAngle().normalize();
        BlockState state = world.getBlockState(entity.blockPosition().offset((int)Math.round(rotationVector.x()), 0, (int)Math.round(rotationVector.z())));

        return state.is(Blocks.SWEET_BERRY_BUSH) && state.getValue(SweetBerryBushBlock.AGE) >= 2;
    }

    @Override
    protected boolean canStillUse(ServerLevel world, LivingEntity entity, long time) {
        return this.checkExtraStartConditions(world, entity);
    }

    @Override
    protected void tick(ServerLevel world, LivingEntity entity, long time) {
        Vec3 rotationVector = entity.getLookAngle().normalize();
        BlockPos pos = entity.blockPosition().offset((int)Math.round(rotationVector.x()), 0, (int)Math.round(rotationVector.z()));

        BlockParticleOption particles = new BlockParticleOption(ParticleTypes.BLOCK, world.getBlockState(pos));
        world.sendParticles(particles, (entity.getX() + pos.getX()) / 2, entity.getY() + 0.5, (entity.getZ() + pos.getZ()) / 2, 10, 0.1, 0.4, 0.1, 1);
    }

    @Override
    protected void start(ServerLevel world, LivingEntity entity, long time) {
        entity.setPose(Pose.DIGGING);
    }

    @Override
    protected void stop(ServerLevel world, LivingEntity entity, long time) {
        world.playSound(entity, entity.blockPosition(), SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
        entity.setPose(Pose.STANDING);

        Vec3 rotationVector = entity.getLookAngle().normalize();
        BlockPos pos = entity.blockPosition().offset((int)Math.round(rotationVector.x()), 0, (int)Math.round(rotationVector.z()));

        if(world.getBlockState(pos).is(Blocks.SWEET_BERRY_BUSH) && world.getBlockState(pos).getValue(SweetBerryBushBlock.AGE) >= 2) {
            if(world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(SweetBerryBushBlock.AGE, 1));
            }

            ItemStack itemStack = new ItemStack(world.getBlockState(pos).getBlock().asItem());
            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
        }



        // Set cooldown to 3000t = 2min30s
        entity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, 3000);
    }
}
