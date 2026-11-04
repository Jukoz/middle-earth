package net.sevenstars.middleearth.entity.wight.brain;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.wight.BarrowWightEnchanterEntity;
import net.sevenstars.middleearth.entity.wight.BarrowWightEntity;
import net.sevenstars.middleearth.item.WeaponItemsME;

public class BarrowWightRepositionTask extends MultiTickTask<BarrowWightEnchanterEntity> {
    private final float approachSpeed;
    private final float retreatSpeed;
    private final int minDistance;
    private final int maxDistance;

    public BarrowWightRepositionTask(float approachSpeed, float retreatSpeed, int minDistance, int maxDistance) {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT
                )
        );
        this.approachSpeed = approachSpeed;
        this.retreatSpeed = retreatSpeed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, BarrowWightEnchanterEntity entity) {
        return entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).isPresent();
    }

    @Override
    protected void run(ServerWorld serverWorld, BarrowWightEnchanterEntity entity, long time) {
        updateMovement(entity);
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld, BarrowWightEnchanterEntity entity, long time) {
        updateMovement(entity);
    }

    private void updateMovement(BarrowWightEnchanterEntity entity) {
        Brain<?> brain = entity.getBrain();
        LivingEntity target = brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        brain.remember(MemoryModulesME.RETREATING, false);
        if (target == null) return;

        double distance = entity.distanceTo(target);

        if (distance < minDistance) {
            Vec3d retreatPos = NoPenaltyTargeting.findFrom(entity, 10, 6, target.getPos());
            if (retreatPos != null) {
                brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(retreatPos, retreatSpeed, 2));
            }
            brain.remember(MemoryModulesME.RETREATING, true);
        }
        else if (distance > maxDistance) {
            brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(target, approachSpeed, minDistance + 1));
        } else {
            brain.forget(MemoryModuleType.WALK_TARGET);
            entity.getNavigation().stop();
            entity.getMoveControl().strafeTo(-0.5f, 0.4f);
        }
    }
}
