package net.sevenstars.middleearth.entity.npcs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.sevenstars.middleearth.entity.EntitiesME;

public class NpcTaskListProvider {
    private static final float WALKING_SPEED = 0.4F;


    public static ImmutableList<Pair<Integer, ? extends Task<? super NpcEntity>>> createIdleTasks(float speed) {
        return ImmutableList.of(
                Pair.of(2, new RandomTask(
                        ImmutableList.of(
                                Pair.of(FindEntityTask.create(EntitiesME.NPC, 8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 2),
                                Pair.of(GoToPointOfInterestTask.create(speed), 1),
                                Pair.of(GoToLookTargetTask.create(speed, 2), 1),
                                Pair.of(new JumpInBedTask(speed), 1),
                                Pair.of(new WaitTask(30, 60), 1)))),
                Pair.of(3, FindInteractionTargetTask.create(EntityType.PLAYER, 4)),
                Pair.of(3, new CompositeTask(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), CompositeTask.Order.ORDERED, CompositeTask.RunMode.RUN_ONE,
                        ImmutableList.of(Pair.of(new GatherItemsVillagerTask(), 1)))),
                Pair.of(99, ScheduleActivityTask.create()));
    }

    private static Pair<Integer, Task<LivingEntity>> createFreeFollowTask() {
        return Pair.of(5, new RandomTask(ImmutableList.of(
                Pair.of(LookAtMobTask.create(EntityType.CAT, 8.0F), 8),
                Pair.of(LookAtMobTask.create(EntityType.VILLAGER, 8.0F), 2),
                Pair.of(LookAtMobTask.create(EntityType.PLAYER, 8.0F), 2),
                Pair.of(LookAtMobTask.create(SpawnGroup.CREATURE, 8.0F), 1),
                Pair.of(LookAtMobTask.create(SpawnGroup.WATER_CREATURE, 8.0F), 1),
                Pair.of(LookAtMobTask.create(SpawnGroup.AXOLOTLS, 8.0F), 1),
                Pair.of(LookAtMobTask.create(SpawnGroup.UNDERGROUND_WATER_CREATURE, 8.0F), 1),
                Pair.of(LookAtMobTask.create(SpawnGroup.WATER_AMBIENT, 8.0F), 1),
                Pair.of(LookAtMobTask.create(SpawnGroup.MONSTER, 8.0F), 1),
                Pair.of(new WaitTask(30, 60), 2))));
    }
}
