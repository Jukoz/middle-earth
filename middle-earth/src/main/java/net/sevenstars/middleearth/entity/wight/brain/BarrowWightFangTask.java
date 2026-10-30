package net.sevenstars.middleearth.entity.wight.brain;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.wight.BarrowWightEnchanterEntity;
import net.sevenstars.middleearth.entity.wight.BarrowWightEntity;

public class BarrowWightFangTask extends MultiTickTask<BarrowWightEnchanterEntity> {
    private static final int SHOOT_CHARGING_EXPIRY = Math.round(15.0F);
    private static final int RECOVER_EXPIRY = Math.round(4.0F);
    private static final int SHOOT_COOLDOWN_EXPIRY = (int)(BarrowWightEnchanterEntity.HALLUCINATION_CAST_COOLDOWN * 1.5f);

    public BarrowWightFangTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.HALLUCINATION_CAST_COOLDOWN,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.SUMMON_CAST_COOLDOWN,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.CAST_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
                ),
                SHOOT_CHARGING_EXPIRY + 1 + RECOVER_EXPIRY
        );
    }

    protected boolean shouldRun(ServerWorld serverWorld, BarrowWightEnchanterEntity BarrowWightEnchanterEntity) {
        Brain<BarrowWightEntity> brain = BarrowWightEnchanterEntity.getBrain();

        if(!brain.hasMemoryModule(MemoryModulesME.SUMMON_CAST_COOLDOWN)) return false;
        int cooldown = brain.getOptionalMemory(MemoryModulesME.SUMMON_CAST_COOLDOWN).orElse(0);
        if(cooldown >= BarrowWightSummonTask.SHOOT_COOLDOWN_EXPIRY * 0.85f) return false;

        if(!brain.hasMemoryModule(MemoryModulesME.HALLUCINATION_CAST_COOLDOWN)) return false;
        cooldown = brain.getOptionalMemory(MemoryModulesME.HALLUCINATION_CAST_COOLDOWN).orElse(0);
        if(cooldown >= BarrowWightHallucinationTask.SHOOT_COOLDOWN_EXPIRY * 0.85f) return false;

        if(brain.hasMemoryModule(MemoryModulesME.CAST_COOLDOWN)) return false;
        return (Boolean)BarrowWightEnchanterEntity.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                .map(target -> isTargetWithinRange(BarrowWightEnchanterEntity, target))
                .orElse(false);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, BarrowWightEnchanterEntity BarrowWightEnchanterEntity, long l) {
        return BarrowWightEnchanterEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    protected void run(ServerWorld serverWorld, BarrowWightEnchanterEntity barrowWightEnchanterEntity, long l) {
        barrowWightEnchanterEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                .ifPresent(target -> barrowWightEnchanterEntity.setPose(EntityPose.SHOOTING));
        Brain<BarrowWightEntity> brain = barrowWightEnchanterEntity.getBrain();
        LivingEntity livingEntity = (LivingEntity)brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (livingEntity != null) {
            castSpell(livingEntity, barrowWightEnchanterEntity, serverWorld);
        }

        if(!brain.hasMemoryModule(MemoryModulesME.CAST_COOLDOWN)) {
            barrowWightEnchanterEntity.tryCastingAnimation();
            brain.remember(MemoryModulesME.CAST_COOLDOWN, SHOOT_COOLDOWN_EXPIRY);
        }
    }

    protected void castSpell(LivingEntity target, BarrowWightEnchanterEntity owner, ServerWorld serverWorld) {
        double minY = Math.min(target.getY(), owner.getY());
        double maxY = Math.max(target.getY(), owner.getY()) + 1.0;
        float atan = (float) MathHelper.atan2(target.getZ() - owner.getZ(), target.getX() - owner.getX());
        int i;
        if (owner.squaredDistanceTo(target) < 9.0) {
            float g;
            for(i = 0; i < 5; ++i) {
                g = atan + (float)i * 3.1415927F * 0.4F;
                this.conjureFangs(serverWorld, owner, owner.getX() + (double)MathHelper.cos(g) * 1.5, owner.getZ() + (double)MathHelper.sin(g) * 1.5, minY, maxY, g, 0);
            }

            for(i = 0; i < 8; ++i) {
                g = atan + (float)i * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
                this.conjureFangs(serverWorld, owner, owner.getX() + (double)MathHelper.cos(g) * 2.5, owner.getZ() + (double)MathHelper.sin(g) * 2.5, minY, maxY, g, 3);
            }
        } else {
            for(i = 3; i < 16; ++i) {
                double h = 1.25 * (double)(i + 1);
                this.conjureFangs(serverWorld, owner, owner.getX() + (double)MathHelper.cos(atan) * h, owner.getZ() + (double)MathHelper.sin(atan) * h, minY, maxY, atan, i);
            }
        }

    }

    private void conjureFangs(ServerWorld serverWorld, BarrowWightEnchanterEntity owner, double x, double z, double maxY, double y, float yaw, int warmup) {
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        boolean bl = false;
        double d = 0.0;

        do {
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState = serverWorld.getBlockState(blockPos2);
            if (blockState.isSideSolidFullSquare(serverWorld, blockPos2, Direction.UP)) {
                if (!serverWorld.isAir(blockPos)) {
                    BlockState blockState2 = serverWorld.getBlockState(blockPos);
                    VoxelShape voxelShape = blockState2.getCollisionShape(serverWorld, blockPos);
                    if (!voxelShape.isEmpty()) {
                        d = voxelShape.getMax(Direction.Axis.Y);
                    }
                }

                bl = true;
                break;
            }

            blockPos = blockPos.down();
        } while(blockPos.getY() >= MathHelper.floor(maxY) - 1);

        if (bl) {
            serverWorld.spawnEntity(new EvokerFangsEntity(serverWorld, x, (double)blockPos.getY() + d, z, yaw, warmup, owner));
            serverWorld.emitGameEvent(GameEvent.ENTITY_PLACE, new Vec3d(x, (double)blockPos.getY() + d, z), GameEvent.Emitter.of(owner));
        }

    }

    @Override
    protected void finishRunning(ServerWorld world, BarrowWightEnchanterEntity entity, long time) {
        super.finishRunning(world, entity, time);
    }

    private static boolean isTargetWithinRange(BarrowWightEnchanterEntity breeze, LivingEntity target) {
        double d = breeze.getPos().squaredDistanceTo(target.getPos());
        return d < 256.0;
    }
}
