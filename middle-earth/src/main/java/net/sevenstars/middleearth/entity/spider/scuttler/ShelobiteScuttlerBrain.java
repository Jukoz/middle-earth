package net.sevenstars.middleearth.entity.spider.scuttler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.behavior.StrollAroundPoi;
import net.minecraft.world.entity.ai.behavior.StrollToPoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.tasks.SpiderPounceTask;

import java.util.Optional;

public class ShelobiteScuttlerBrain {
	private static final UniformInt POUNCE_COOLDOWN_RANGE = UniformInt.of(50, 80);
	public static final int POUNCE_VERTICAL_RANGE = 1;
	public static final int POUNCE_HORIZONTAL_RANGE = 3;

	protected static Brain<?> create(ShelobiteScuttlerEntity shelobiteScuttlerEntity, Brain<ShelobiteScuttlerEntity> brain) {
		addCoreActivities(shelobiteScuttlerEntity, brain);
		addIdleActivities(shelobiteScuttlerEntity, brain);
		addFightActivities(shelobiteScuttlerEntity, brain);
		addPounceActivities(shelobiteScuttlerEntity, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	protected static void setCurrentPosAsHome(ShelobiteScuttlerEntity shelobiteScuttler) {
		GlobalPos globalPos = GlobalPos.of(shelobiteScuttler.level().dimension(), shelobiteScuttler.blockPosition());
		shelobiteScuttler.getBrain().setMemory(MemoryModuleType.HOME, globalPos);
	}

	private static void addCoreActivities(ShelobiteScuttlerEntity shelobiteScuttler, Brain<ShelobiteScuttlerEntity> brain) {
		brain.addActivity(
				Activity.CORE, 0, ImmutableList.of(
						new LookAtTargetSink(45, 90),
						new MoveToTargetSink(),
						StopBeingAngryIfTargetDead.create(),
						new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS)
				)
		);
	}

	private static void addIdleActivities(ShelobiteScuttlerEntity shelobiteScuttler, Brain<ShelobiteScuttlerEntity> brain) {
		brain.addActivity(
				Activity.IDLE,
				10,
				ImmutableList.of(
						StartAttacking.<ShelobiteScuttlerEntity>create(ShelobiteScuttlerBrain::getTarget),
						getFollowTasks(),
						getIdleTasks(),
						SetLookAndInteract.create(EntityType.PLAYER, 4)
				)
		);
	}

	private static void addFightActivities(ShelobiteScuttlerEntity shelobiteScuttler, Brain<ShelobiteScuttlerEntity> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
				Activity.FIGHT,
				10,
				ImmutableList.of(
						StopAttackingIfTargetInvalid.create(target -> !isTarget(shelobiteScuttler, target)),
						SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F),
						MeleeAttack.create(20)
				),
				MemoryModuleType.ATTACK_TARGET
		);
	}

	private static void addPounceActivities(ShelobiteScuttlerEntity shelobiteScuttler, Brain<ShelobiteScuttlerEntity> brain) {
		brain.addActivityAndRemoveMemoriesWhenStopped(
				Activity.LONG_JUMP,
				ImmutableList.of(
						Pair.of(0, StopAttackingIfTargetInvalid.create(
								target -> !isTarget(shelobiteScuttler, target))
						),
						//new LeapingChargeTask(POUNCE_COOLDOWN_RANGE, SoundEvents.ENTITY_SPIDER_STEP),
						Pair.of(1, new SpiderPounceTask<>(
								POUNCE_COOLDOWN_RANGE, POUNCE_VERTICAL_RANGE, POUNCE_HORIZONTAL_RANGE,
								3.5714288F, spider -> SoundEvents.SPIDER_STEP
						))
				),
				ImmutableSet.of(
						Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
						Pair.of(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT)
				),
				ImmutableSet.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS)
				//MemoryModuleType.LONG_JUMP_COOLING_DOWN
				//Activity.LONG_JUMP,
				//10,
				//ImmutableList.of(
				//		Pair.of(0, new LeapingChargeTask(POUNCE_COOLDOWN_RANGE, SoundEvents.ENTITY_SPIDER_STEP)),
				//		Pair.of(
				//				1,
				//				new LongJumpTask<>(
				//						POUNCE_COOLDOWN_RANGE, POUNCE_VERTICAL_RANGE, POUNCE_HORIZONTAL_RANGE, 3.5714288F, spider -> SoundEvents.ENTITY_SPIDER_STEP
				//				)
				//		)
				//),
				//MemoryModuleType.ATTACK_TARGET
				//ImmutableSet.of(
						//Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT),
						//Pair.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT),
						//Pair.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT)
				//)
		);
	}

	private static RunOne<ShelobiteScuttlerEntity> getFollowTasks() {
		return new RunOne<>(
				ImmutableList.of(
						Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 1),
						Pair.of(SetEntityLookTarget.create(EntitiesME.SHELOBITE_SCUTTLER, 8.0F), 1),
						Pair.of(SetEntityLookTarget.create(8.0F), 1),
						Pair.of(new DoNothing(30, 60), 1)
				)
		);
	}

	private static RunOne<ShelobiteScuttlerEntity> getIdleTasks() {
		return new RunOne<>(
				ImmutableList.of(
						Pair.of(RandomStroll.stroll(0.6F), 2),
						Pair.of(InteractWith.of(EntitiesME.SHELOBITE_SCUTTLER, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
						Pair.of(StrollToPoi.create(MemoryModuleType.HOME, 0.6F, 2, 100), 2),
						Pair.of(StrollAroundPoi.create(MemoryModuleType.HOME, 0.6F, 5), 2),
						Pair.of(new DoNothing(30, 60), 1)
				)
		);
	}
	private static boolean isTarget(ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		return getTarget(shelobiteScuttler).filter(targetx -> targetx == target).isPresent();
	}

	private static Optional<? extends LivingEntity> getTarget(ShelobiteScuttlerEntity shelobiteScuttler) {
		Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(shelobiteScuttler, MemoryModuleType.ANGRY_AT);
		if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(shelobiteScuttler, optional.get())) {
			return optional;
		} else {
			Optional<? extends LivingEntity> optional2 = shelobiteScuttler.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
			return optional2.isPresent() ? optional2 : shelobiteScuttler.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
		}
	}

	protected static void tryRevenge(ServerLevel world, ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		if (!(target instanceof AbstractPiglin)) {
			tryRevenge(world, shelobiteScuttler, target);
		}
	}

	protected static void setTarget(ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		shelobiteScuttler.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		shelobiteScuttler.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);
	}

	protected static void playSoundRandomly(ShelobiteScuttlerEntity shelobiteScuttlerEntity) {
		if (shelobiteScuttlerEntity.level().random.nextFloat() < 0.0125) {
			playSoundIfAngry(shelobiteScuttlerEntity);
		}
	}

	private static void playSoundIfAngry(ShelobiteScuttlerEntity shelobiteScuttlerEntity) {
		shelobiteScuttlerEntity.getBrain().getActiveNonCoreActivity().ifPresent(activity -> {
			if (activity == Activity.FIGHT) {
				//mirkwoodSpiderEntity.playAngrySound();
			}
		});
	}
}
