package net.sevenstars.middleearth.entity.spider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Optional;

public class ShelobiteScuttlerBrain {
	private static final int ANGRY_AT_EXPIRY = 600;
	private static final int MELEE_ATTACK_COOLDOWN = 20;

	protected static Brain<?> create(MirkwoodSpiderEntity shelobiteScuttlerEntity, Brain<MirkwoodSpiderEntity> brain) {
		addCoreActivities(shelobiteScuttlerEntity, brain);
		addIdleActivities(shelobiteScuttlerEntity, brain);
		addFightActivities(shelobiteScuttlerEntity, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}

	protected static void setCurrentPosAsHome(MirkwoodSpiderEntity shelobiteScuttler) {
		GlobalPos globalPos = GlobalPos.create(shelobiteScuttler.getWorld().getRegistryKey(), shelobiteScuttler.getBlockPos());
		shelobiteScuttler.getBrain().remember(MemoryModuleType.HOME, globalPos);
	}

	private static void addCoreActivities(MirkwoodSpiderEntity shelobiteScuttler, Brain<MirkwoodSpiderEntity> brain) {
		brain.setTaskList(
				Activity.CORE, 0, ImmutableList.of(new UpdateLookControlTask(45, 90), new MoveToTargetTask(), OpenDoorsTask.create(), ForgetAngryAtTargetTask.create())
		);
	}

	private static void addIdleActivities(MirkwoodSpiderEntity shelobiteScuttler, Brain<MirkwoodSpiderEntity> brain) {
		brain.setTaskList(
				Activity.IDLE,
				10,
				ImmutableList.of(
						UpdateAttackTargetTask.<MirkwoodSpiderEntity>create(ShelobiteScuttlerBrain::getTarget),
						getFollowTasks(),
						getIdleTasks(),
						FindInteractionTargetTask.create(EntityType.PLAYER, 4)
				)
		);
	}

	private static void addFightActivities(MirkwoodSpiderEntity shelobiteScuttler, Brain<MirkwoodSpiderEntity> brain) {
		brain.setTaskList(
				Activity.FIGHT,
				10,
				ImmutableList.of(
						ForgetAttackTargetTask.create((world, target) -> !isTarget(world, shelobiteScuttler, target)), RangedApproachTask.create(1.0F), MeleeAttackTask.create(20)
				),
				MemoryModuleType.ATTACK_TARGET
		);
	}

	private static RandomTask<MirkwoodSpiderEntity> getFollowTasks() {
		return new RandomTask<>(
				ImmutableList.of(
						Pair.of(LookAtMobTask.create(EntityType.PLAYER, 8.0F), 1),
						Pair.of(LookAtMobTask.create(EntityType.PIGLIN, 8.0F), 1),
						Pair.of(LookAtMobTask.create(EntityType.PIGLIN_BRUTE, 8.0F), 1),
						Pair.of(LookAtMobTask.create(8.0F), 1),
						Pair.of(new WaitTask(30, 60), 1)
				)
		);
	}

	private static RandomTask<MirkwoodSpiderEntity> getIdleTasks() {
		return new RandomTask<>(
				ImmutableList.of(
						Pair.of(StrollTask.create(0.6F), 2),
						Pair.of(FindEntityTask.create(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
						Pair.of(FindEntityTask.create(EntityType.PIGLIN_BRUTE, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
						Pair.of(GoToPosTask.create(MemoryModuleType.HOME, 0.6F, 2, 100), 2),
						Pair.of(GoAroundTask.create(MemoryModuleType.HOME, 0.6F, 5), 2),
						Pair.of(new WaitTask(30, 60), 1)
				)
		);
	}

	protected static void tick(MirkwoodSpiderEntity shelobiteScuttlerEntity) {
		Brain<MirkwoodSpiderEntity> brain = shelobiteScuttlerEntity.getBrain();
		Activity activity = (Activity)brain.getFirstPossibleNonCoreActivity().orElse(null);
		brain.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
		Activity activity2 = (Activity)brain.getFirstPossibleNonCoreActivity().orElse(null);
		if (activity != activity2) {
			playSoundIfAngry(shelobiteScuttlerEntity);
		}

		shelobiteScuttlerEntity.setAttacking(brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
	}

	private static boolean isTarget(ServerWorld world, MirkwoodSpiderEntity shelobiteScuttler, LivingEntity target) {
		return getTarget(world, shelobiteScuttler).filter(targetx -> targetx == target).isPresent();
	}

	private static Optional<? extends LivingEntity> getTarget(ServerWorld world, MirkwoodSpiderEntity shelobiteScuttler) {
		Optional<LivingEntity> optional = TargetUtil.getEntity(shelobiteScuttler, MemoryModuleType.ANGRY_AT);
		if (optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(world, shelobiteScuttler, (LivingEntity)optional.get())) {
			return optional;
		} else {
			Optional<? extends LivingEntity> optional2 = shelobiteScuttler.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
			return optional2.isPresent() ? optional2 : shelobiteScuttler.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
		}
	}

	protected static void tryRevenge(ServerWorld world, MirkwoodSpiderEntity shelobiteScuttler, LivingEntity target) {
		if (!(target instanceof AbstractPiglinEntity)) {
			tryRevenge(world, shelobiteScuttler, target);
		}
	}

	protected static void setTarget(MirkwoodSpiderEntity piglinBrute, LivingEntity target) {
		piglinBrute.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		piglinBrute.getBrain().remember(MemoryModuleType.ANGRY_AT, target.getUuid(), 600L);
	}

	protected static void playSoundRandomly(MirkwoodSpiderEntity mirkwoodSpiderEntity) {
		if (mirkwoodSpiderEntity.getWorld().random.nextFloat() < 0.0125) {
			playSoundIfAngry(mirkwoodSpiderEntity);
		}
	}

	private static void playSoundIfAngry(MirkwoodSpiderEntity mirkwoodSpiderEntity) {
		mirkwoodSpiderEntity.getBrain().getFirstPossibleNonCoreActivity().ifPresent(activity -> {
			if (activity == Activity.FIGHT) {
				//mirkwoodSpiderEntity.playAngrySound();
			}
		});
	}
}
