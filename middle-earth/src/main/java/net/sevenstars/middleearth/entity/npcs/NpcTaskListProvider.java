package net.sevenstars.middleearth.entity.npcs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.JumpOnBed;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.TradeWithVillager;
import net.minecraft.world.entity.ai.behavior.UpdateActivityFromSchedule;
import net.minecraft.world.entity.ai.behavior.VillageBoundRandomStroll;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.sevenstars.middleearth.entity.EntitiesME;

public class NpcTaskListProvider {
    private static final float WALKING_SPEED = 0.4F;


    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super NpcEntity>>> createIdleTasks(float speed) {
        return ImmutableList.of(
                Pair.of(2, new RunOne(
                        ImmutableList.of(
                                Pair.of(InteractWith.of(EntitiesME.NPC, 8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 2),
                                Pair.of(VillageBoundRandomStroll.create(speed), 1),
                                Pair.of(SetWalkTargetFromLookTarget.create(speed, 2), 1),
                                Pair.of(new JumpOnBed(speed), 1),
                                Pair.of(new DoNothing(30, 60), 1)))),
                Pair.of(3, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                Pair.of(3, new GateBehavior(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE,
                        ImmutableList.of(Pair.of(new TradeWithVillager(), 1)))),
                Pair.of(99, UpdateActivityFromSchedule.create()));
    }

    private static Pair<Integer, BehaviorControl<LivingEntity>> createFreeFollowTask() {
        return Pair.of(5, new RunOne(ImmutableList.of(
                Pair.of(SetEntityLookTarget.create(EntityType.CAT, 8.0F), 8),
                Pair.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), 2),
                Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2),
                Pair.of(SetEntityLookTarget.create(MobCategory.CREATURE, 8.0F), 1),
                Pair.of(SetEntityLookTarget.create(MobCategory.WATER_CREATURE, 8.0F), 1),
                Pair.of(SetEntityLookTarget.create(MobCategory.AXOLOTLS, 8.0F), 1),
                Pair.of(SetEntityLookTarget.create(MobCategory.UNDERGROUND_WATER_CREATURE, 8.0F), 1),
                Pair.of(SetEntityLookTarget.create(MobCategory.WATER_AMBIENT, 8.0F), 1),
                Pair.of(SetEntityLookTarget.create(MobCategory.MONSTER, 8.0F), 1),
                Pair.of(new DoNothing(30, 60), 2))));
    }
}
