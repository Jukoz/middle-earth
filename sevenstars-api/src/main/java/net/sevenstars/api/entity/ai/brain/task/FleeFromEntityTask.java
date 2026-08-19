package net.sevenstars.api.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class FleeFromEntityTask<E extends PathfinderMob> extends Behavior<E> {
    private ImmutableList<Class<? extends Entity>> entities;
    private int distance;
    private float speed;

    public FleeFromEntityTask(ImmutableList<Class<? extends Entity>> entities, int distance, float speed) {
        super(Map.of(), 100, 120);

        this.entities = entities;
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, E pathAwareEntity) {
        for(Class<? extends Entity> mob : entities) {
            if(!world.getEntitiesOfClass(mob, pathAwareEntity.getBoundingBox().inflate(distance), EntitySelector.NO_CREATIVE_OR_SPECTATOR).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void start(ServerLevel serverWorld, E pathAwareEntity, long l) {
        pathAwareEntity.getBrain().setMemory(MemoryModuleType.IS_PANICKING, true);
        pathAwareEntity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        pathAwareEntity.setPose(Pose.SHOOTING);
    }

    @Override
    protected void stop(ServerLevel serverWorld, E pathAwareEntity, long l) {
        Brain<?> brain = pathAwareEntity.getBrain();
        brain.eraseMemory(MemoryModuleType.IS_PANICKING);
        pathAwareEntity.setPose(Pose.STANDING);
    }

    @Override
    protected boolean canStillUse(ServerLevel world, E entity, long time) {
        return true;
    }

    @Override
    protected void tick(ServerLevel serverWorld, E pathAwareEntity, long l) {
        if (pathAwareEntity.getNavigation().isDone()) {
            Vec3 vec3d = this.findTarget(pathAwareEntity, serverWorld);
            if (vec3d != null) {
                pathAwareEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, this.speed, 0));
            }
        }
    }

    @Nullable
    private Vec3 findTarget(E entity, ServerLevel world) {
        List<? extends Entity> fleeEntities = new ArrayList<>();
        Vec3 direction = entity.position();

        for(Class<? extends Entity> mob : entities) {
            if(!(fleeEntities = world.getEntitiesOfClass(mob, entity.getBoundingBox().inflate(distance), EntitySelector.NO_CREATIVE_OR_SPECTATOR)).isEmpty()) {
                break;
            }
        }
        if(!fleeEntities.isEmpty()) {
            return LandRandomPos.getPosAway(entity, distance, 4, fleeEntities.getFirst().position());
        }

        return LandRandomPos.getPosTowards(entity, 7, 4, direction);
    }
}
