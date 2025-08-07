package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FleeFromEntityTask<E extends PathAwareEntity> extends MultiTickTask<E> {
    private ImmutableList<Class<? extends Entity>> entities;
    private float distance;
    private float speed;

    public FleeFromEntityTask(ImmutableList<Class<? extends Entity>> entities, float distance, float speed) {
        super(Map.of(), 100, 120);

        this.entities = entities;
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, E pathAwareEntity) {
        for(Class<? extends Entity> mob : entities) {
            if(!world.getEntitiesByClass(mob, pathAwareEntity.getBoundingBox().expand(distance), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    protected void run(ServerWorld serverWorld, E pathAwareEntity, long l) {
        pathAwareEntity.getBrain().remember(MemoryModuleType.IS_PANICKING, true);
        pathAwareEntity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        pathAwareEntity.setPose(EntityPose.SHOOTING);
    }

    protected void finishRunning(ServerWorld serverWorld, E pathAwareEntity, long l) {
        Brain<?> brain = pathAwareEntity.getBrain();
        brain.forget(MemoryModuleType.IS_PANICKING);
        pathAwareEntity.setPose(EntityPose.STANDING);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, E entity, long time) {
        return true;
    }

    protected void keepRunning(ServerWorld serverWorld, E pathAwareEntity, long l) {
        if (pathAwareEntity.getNavigation().isIdle()) {
            Vec3d vec3d = this.findTarget(pathAwareEntity, serverWorld);
            if (vec3d != null) {
                pathAwareEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, this.speed, 0));
            }
        }
    }

    @Nullable
    private Vec3d findTarget(E entity, ServerWorld world) {
        List<? extends Entity> fleeEntities = new ArrayList<>();
        Vec3d direction = entity.getPos();

        for(Class<? extends Entity> mob : entities) {
            if(!(fleeEntities = world.getEntitiesByClass(mob, entity.getBoundingBox().expand(distance), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)).isEmpty()) {
                break;
            }
        }
        if(!fleeEntities.isEmpty()) {
            direction = entity.getPos().subtract(fleeEntities.getFirst().getPos()).normalize().multiply(5, 0, 5);
            for(int i = 0; world.getBlockState(new BlockPos((int)direction.x, (int)direction.y - 1, (int)direction.z)).isOf(Blocks.WATER) && i < 8; i++) {
                direction.rotateY(MathHelper.PI / 8);
            }
        }

        return FuzzyTargeting.findTo(entity, 7, 4, direction);
    }
}
