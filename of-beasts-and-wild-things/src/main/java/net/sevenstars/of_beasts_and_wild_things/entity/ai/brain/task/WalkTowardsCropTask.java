package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class WalkTowardsCropTask {
    private static final int DEFAULT_HORIZONTAL_RADIUS = 10;
    private static final int DEFAULT_VERTICAL_RADIUS = 7;
    private static final int[][] RADII = new int[][]{{1, 1}, {3, 3}, {5, 5}, {6, 5}, {7, 7}, {10, 7}};

    public WalkTowardsCropTask() {
    }

    public static SingleTickTask<PathAwareEntity> create(float speed) {
        return create(speed, WalkTowardsCropTask::convertTargetPos, Entity::isOnGround);
    }

    private static SingleTickTask<PathAwareEntity> create(float speed, Function<PathAwareEntity, Vec3d> targetGetter, Predicate<PathAwareEntity> shouldRun) {
        return TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET)).apply(context, (walkTarget) -> {
                return (world, entity, time) -> {
                    if (!shouldRun.test(entity)) {
                        return false;
                    } else {
                        Optional<Vec3d> optional = Optional.ofNullable((Vec3d)targetGetter.apply(entity));
                        walkTarget.remember(optional.map((pos) -> {
                            return new WalkTarget(pos, speed, 0);
                        }));
                        return true;
                    }
                };
            });
        });
    }

    private static Vec3d convertTargetPos(PathAwareEntity entity) {
        BlockPos targetPos = findTargetPos(entity);

        if(targetPos != null) {
            return new Vec3d(targetPos.getX() + 0.5d, targetPos.getY() + 0.8d, targetPos.getZ() + 0.5d);
        }

        Vec3d vec3d = entity.getRotationVec(0.0F);
        return NoPenaltySolidTargeting.find(entity, DEFAULT_HORIZONTAL_RADIUS, DEFAULT_VERTICAL_RADIUS, -2, vec3d.x, vec3d.z, 1.5707963705062866);
    }

    @Nullable
    private static BlockPos findTargetPos(PathAwareEntity entity) {
        BlockPos targetPos = entity.getBlockPos();

        // Start with the smallest radius, then check every block for Crops
        for (int[] radius : RADII) {
            for (int y = -radius[1]; y <= radius[1]; y++) {
                for (int x = -radius[0]; x <= radius[0]; x++) {
                    for (int z = -radius[0]; z <= radius[0]; z++) {
                        if (entity.getWorld().getBlockState(targetPos.add(x, y, z)).isIn(BlockTags.CROPS)) {
                            return targetPos.add(x, y, z).down();
                        }
                    }
                }
            }
        }
        return null;
    }
}
