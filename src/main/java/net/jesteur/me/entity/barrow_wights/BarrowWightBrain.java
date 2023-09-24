

package net.jesteur.me.entity.barrow_wights;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.List;

public class BarrowWightBrain {

    private static final float RANGED_APPROACH_SPEED = 1.2f;
    private static final int MELEE_ATTACK_INTERVAL = 18;
    private static final List<SensorType<? extends Sensor<? super BarrowWightEntity>>> SENSORS = List.of(SensorType.NEAREST_PLAYERS);
    private static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_ATTACKABLE);

    public static void updateActivities(BarrowWightEntity barrowWight) {
        barrowWight.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    public static Brain<?> create(BarrowWightEntity barrowWight, Dynamic<?> dynamic) {
        Brain.Profile profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<BarrowWightEntity> brain = profile.deserialize(dynamic);
        BarrowWightBrain.addCoreActivities(brain);
        BarrowWightBrain.addIdleActivities(brain);
        BarrowWightBrain.addFightActivities(barrowWight, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<BarrowWightEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), LookAtDisturbanceTask.create(), new LookAroundTask(45, 90), new WanderAroundTask()));
    }


    private static void addIdleActivities(Brain<BarrowWightEntity> brain) {
        brain.setTaskList(Activity.IDLE, 10, ImmutableList.of(StrollTask.create(0.5f), new WaitTask(30, 60)));
    }

    private static void addFightActivities(BarrowWightEntity barrowWight, Brain<BarrowWightEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 10, ImmutableList.of(new HallucinatingWhispersTask(),LookAtMobTask.create((LivingEntity entity) -> BarrowWightBrain.isTargeting(barrowWight, entity), (float)barrowWight.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)), RangedApproachTask.create(1.2f), MeleeAttackTask.create(18)), MemoryModuleType.ATTACK_TARGET);
    }

    private static boolean isTargeting(BarrowWightEntity warden, LivingEntity entity) {
        return warden.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).filter(entityx -> entityx == entity).isPresent();
    }
}