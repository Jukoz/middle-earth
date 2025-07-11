package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class StrollAroundHomeTask {
    public static Task<PathAwareEntity> create(float speed, int radius, boolean strollInWater) {
        return create(speed, entity -> findTargetPos(entity, radius), strollInWater ? entity -> true : entity -> !entity.isTouchingWater());
    }

    private static SingleTickTask<PathAwareEntity> create(float speed, Function<PathAwareEntity, Vec3d> targetGetter, Predicate<PathAwareEntity> shouldRun) {
        return TaskTriggerer.task(
                context -> context.group(context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET)).apply(context, walkTarget -> (world, entity, time) -> {
                    if (!shouldRun.test(entity)) {
                        return false;
                    } else {
                        Optional<Vec3d> optional = Optional.ofNullable((Vec3d)targetGetter.apply(entity));
                        walkTarget.remember(optional.map(pos -> new WalkTarget(pos, speed, 0)));
                        return true;
                    }
                })
        );
    }

    @Nullable
    private static Vec3d findTargetPos(PathAwareEntity entity, int radius) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        Vec3d homePos;
        Vec3d entityPos = entity.getPos();

        if(optional != null && optional.isPresent()) {
            homePos = optional.get().pos().toBottomCenterPos();
        }
        else {
            return null;
        }

        Vec3d direction = entityPos.relativize(homePos).normalize();
        return NoPenaltySolidTargeting.find(entity, radius, radius, -2, direction.x, direction.z, (float) (Math.PI / 2));
    }}
