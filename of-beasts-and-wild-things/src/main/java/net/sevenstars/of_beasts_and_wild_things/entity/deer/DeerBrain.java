package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.api.entity.ai.brain.task.EatBerriesTask;
import net.sevenstars.api.entity.ai.brain.task.FleeFromEntityTask;
import net.sevenstars.api.entity.ai.brain.task.MoveTowardsBlockTask;

public class DeerBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super DeerEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public DeerBrain() {
    }

    protected static Brain<?> create(DeerEntity deerEntity, Dynamic<?> dynamic) {
        Brain.Provider<DeerEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
        Brain<DeerEntity> brain = profile.makeBrain(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivities(Brain<DeerEntity> brain) {
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new MoveToTargetSink()));
    }

    private static void addIdleActivities(Brain<DeerEntity> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new FleeFromEntityTask<DeerEntity>(ImmutableList.of(Player.class), 5, 1.5F)),
                Pair.of(1, new RunOne(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(MoveTowardsBlockTask.create(1.0F, Blocks.SWEET_BERRY_BUSH), 5),
                        Pair.of(new EatBerriesTask(), 5),
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(60, 100), 1)
                ))),
                Pair.of(2, new RunOne(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_PRESENT), ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(60, 100), 1)
                )))
        ));
    }

    public static void updateActivities(DeerEntity deer) {
        deer.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE, Activity.LONG_JUMP));
    }

    static {
        SENSORS = ImmutableList.of();
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS);
    }
}
