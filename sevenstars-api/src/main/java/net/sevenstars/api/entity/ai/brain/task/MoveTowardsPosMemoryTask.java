package net.sevenstars.api.entity.ai.brain.task;

import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class MoveTowardsPosMemoryTask {
    public static SingleTickTask<PathAwareEntity> create(MemoryModuleType<GlobalPos> destination, float speed, int completionRange, int maxDistance, int maxRunTime) {
        return TaskTriggerer.task(
                context -> context.group(
                                context.queryMemoryOptional(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE),
                                context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET),
                                context.queryMemoryValue(destination)
                        )
                        .apply(context, (cantReachWalkTargetSince, walkTarget, destinationResult) -> (world, entity, time) -> {
                            GlobalPos globalPos = context.getValue(destinationResult);
                            Optional<Long> optional = context.getOptionalValue(cantReachWalkTargetSince);
                            if (globalPos.dimension() == world.getRegistryKey() && (!optional.isPresent() || world.getTime() - (Long)optional.get() <= maxRunTime)) {
                                if (globalPos.pos().getManhattanDistance(entity.getBlockPos()) > maxDistance) {
                                    Vec3d vec3d = null;
                                    int l = 0;

                                    while (vec3d == null || BlockPos.ofFloored(vec3d).getManhattanDistance(entity.getBlockPos()) > maxDistance) {
                                        vec3d = NoPenaltyTargeting.findTo(entity, 15, 7, Vec3d.ofBottomCenter(globalPos.pos()), (float) (Math.PI / 2));
                                        if (++l == 1000) {
                                            destinationResult.forget();
                                            cantReachWalkTargetSince.remember(time);
                                            return true;
                                        }
                                    }

                                    walkTarget.remember(new WalkTarget(vec3d, speed, completionRange));
                                } else if (globalPos.pos().getManhattanDistance(entity.getBlockPos()) > completionRange) {
                                    walkTarget.remember(new WalkTarget(globalPos.pos(), speed, completionRange));
                                }
                            } else {
                                destinationResult.forget();
                                cantReachWalkTargetSince.remember(time);
                            }

                            return true;
                        })
        );
    }
}
