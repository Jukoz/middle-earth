package net.sevenstars.middleearth.entity.ai.brain.task.npc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.MobEntity;

import java.util.Optional;
import java.util.function.Function;

public class NpcRangedApproachTask {
    public static Task<MobEntity> create(float speed, int distance) {
        return create((entity) -> speed, (entity) -> distance);
    }

    public static Task<MobEntity> create(Function<LivingEntity, Float> speed, Function<LivingEntity, Integer> distance) {
        return TaskTriggerer.task((context) -> context.group(
                    context.queryMemoryOptional(MemoryModuleType.WALK_TARGET),
                    context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                    context.queryMemoryValue(MemoryModuleType.ATTACK_TARGET),
                    context.queryMemoryOptional(MemoryModuleType.VISIBLE_MOBS)
                )
                .apply(context, (walkTarget, lookTarget, attackTarget, visibleMobs) -> (world, entity, time) -> {
                    LivingEntity livingEntity = context.getValue(attackTarget);
                    Optional<LivingTargetCache> optionalVisibleAttackTargets = context.getOptionalValue(visibleMobs);
                    if (optionalVisibleAttackTargets.isPresent() && (optionalVisibleAttackTargets.get()).contains(livingEntity) && TargetUtil.isTargetWithinAttackRange(entity, livingEntity, 1)) {
                        walkTarget.forget();
                    } else {
                        lookTarget.remember(new EntityLookTarget(livingEntity, true));
                        walkTarget.remember(new WalkTarget(new EntityLookTarget(livingEntity, false), speed.apply(entity), distance.apply(entity)));
                    }

                    return true;
                }
            )
        );
    }
}
