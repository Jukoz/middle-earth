package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.api.entity.ai.brain.task.MoveTowardsBlockTask;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.EatCropTask;

public class SnailBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super SnailEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public SnailBrain() {
    }

    protected static Brain<?> create(SnailEntity snailEntity, Dynamic<?> dynamic) {
        Brain.Provider<SnailEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
        Brain<SnailEntity> brain = profile.makeBrain(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivities(Brain<SnailEntity> brain) {
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new MoveToTargetSink(), new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS)));
    }

    private static void addIdleActivities(Brain<SnailEntity> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RunOne(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(MoveTowardsBlockTask.create(1.0F, BlockTags.CROPS), 5),
                        Pair.of(new EatCropTask(), 5),
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(60, 100), 1)
                ))),
                Pair.of(1, new RunOne(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_PRESENT), ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(60, 100), 1)
                )))
        ));
    }

    public static void updateActivities(SnailEntity snail) {
        snail.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE, Activity.LONG_JUMP));
    }

    static {
        SENSORS = ImmutableList.of();
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS);
    }
}
