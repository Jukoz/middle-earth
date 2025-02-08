package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class SnailBrain {

    public SnailBrain() {
    }

    protected static Brain<?> create(Brain<SnailEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<SnailEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new MoveToTargetTask()));
    }

    private static void addIdleActivities(Brain<SnailEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new RandomTask(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(Pair.of(StrollTask.create(1.0F), 1), Pair.of(TaskTriggerer.predicate(Entity::isOnGround), 2))))));
    }

    public static void updateActivities(SnailEntity snail) {
        snail.getBrain().resetPossibleActivities(ImmutableList.of());
    }
}
