package net.jukoz.me.entity.humanoid_npc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.jukoz.me.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.Optional;

public class HumanoidNpcTaskListProvider {
    private static final float JOB_WALKING_SPEED = 0.4F;

    public static ImmutableList<Pair<Integer, ? extends Task<? super HumanoidNpcEntity>>> createCoreTasks(float speed) {
        return ImmutableList.of(
                Pair.of(0, new StayAboveWaterTask(0.8F)),
                Pair.of(0, OpenDoorsTask.create()),
                Pair.of(0, new LookAroundTask(45, 90)),
                Pair.of(0, WakeUpTask.create()),
                Pair.of(1, new WanderAroundTask()),
                Pair.of(10, FindPointOfInterestTask.create(
                        poiType -> poiType.matchesKey(PointOfInterestTypes.HOME),
                        MemoryModuleType.HOME,
                        false,
                        Optional.of((byte) 14))),
                Pair.of(10, FindPointOfInterestTask.create(
                        poiType -> poiType.matchesKey(PointOfInterestTypes.MEETING),
                        MemoryModuleType.MEETING_POINT,
                        true,
                        Optional.of((byte) 14)))
        );
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super HumanoidNpcEntity>>> createIdleTask(float speed, EntityType entityType) {

        return ImmutableList.of(
                Pair.of(0, new WanderAroundTask(80, 120)),
                Pair.of(5, new RandomTask(
                        ImmutableList.of(
                                Pair.of(FindEntityTask.create(entityType, 8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 2),
                                Pair.of(FindEntityTask.create(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 1),
                                Pair.of(FindWalkTargetTask.create(speed), 1),
                                Pair.of(GoTowardsLookTargetTask.create(speed, 2), 1),
                                Pair.of(new JumpInBedTask(speed), 2),
                                Pair.of(new WaitTask(20, 40), 2)
                        )
                )),
                Pair.of(99, ScheduleActivityTask.create())
        );
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super HumanoidNpcEntity>>> createRestTasks(float speed) {
        return ImmutableList.of(
            Pair.of(2, create(MemoryModuleType.HOME, speed, 1, 150, 1200)),
            Pair.of(3, ForgetCompletedPointOfInterestTask.create(
                (poiType) ->
                {
                    return poiType.matchesKey(PointOfInterestTypes.HOME);
                },
                MemoryModuleType.HOME)
            ),
            Pair.of(3, new SleepTask()),
            Pair.of(5, new RandomTask(
                ImmutableMap.of(
                    MemoryModuleType.HOME, MemoryModuleState.VALUE_ABSENT),
                    ImmutableList.of(
                            Pair.of(WalkHomeTask.create(speed), 1),
                            Pair.of(WanderIndoorsTask.create(speed), 4),
                            Pair.of(GoToPointOfInterestTask.create(speed, 4), 2),
                            Pair.of(new WaitTask(20, 40), 2)
                    )
                )
            ),
            createBusyFollowTask(),
            Pair.of(99, ScheduleActivityTask.create()));
    }

    private static Pair<Integer, Task<LivingEntity>> createBusyFollowTask() {
        return Pair.of(5, new RandomTask(
                        ImmutableList.of(
                                Pair.of(LookAtMobTask.create(ModEntities.HOBBIT, 8.0F), 2),
                                Pair.of(LookAtMobTask.create(EntityType.PLAYER, 8.0F), 2),
                                Pair.of(new WaitTask(30, 60), 8))));
    }



    public static SingleTickTask<HumanoidNpcEntity> create(MemoryModuleType<GlobalPos> destination, float speed, int completionRange, int maxDistance, int maxRunTime) {
        return TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryOptional(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE), context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET), context.queryMemoryValue(destination)).apply(context, (cantReachWalkTargetSince, walkTarget, destinationResult) -> {
                return (world, entity, time) -> {
                    GlobalPos globalPos = (GlobalPos)context.getValue(destinationResult);
                    Optional<Long> optional = context.getOptionalValue(cantReachWalkTargetSince);
                    if (globalPos.getDimension() == world.getRegistryKey() && (!optional.isPresent() || world.getTime() - (Long)optional.get() <= (long)maxRunTime)) {
                        if (globalPos.getPos().getManhattanDistance(entity.getBlockPos()) > maxDistance) {
                            Vec3d vec3d = null;
                            int l = 0;

                            while(vec3d == null || BlockPos.ofFloored(vec3d).getManhattanDistance(entity.getBlockPos()) > maxDistance) {
                                vec3d = NoPenaltyTargeting.findTo(entity, 15, 7, Vec3d.ofBottomCenter(globalPos.getPos()), 1.5707963705062866);
                                ++l;
                                if (l == 1000) {
                                    entity.releaseTicketFor(destination);
                                    destinationResult.forget();
                                    cantReachWalkTargetSince.remember(time);
                                    return true;
                                }
                            }

                            walkTarget.remember(new WalkTarget(vec3d, speed, completionRange));
                        } else if (globalPos.getPos().getManhattanDistance(entity.getBlockPos()) > completionRange) {
                            walkTarget.remember(new WalkTarget(globalPos.getPos(), speed, completionRange));
                        }
                    } else {
                        entity.releaseTicketFor(destination);
                        destinationResult.forget();
                        cantReachWalkTargetSince.remember(time);
                    }

                    return true;
                };
            });
        });
    }
}
