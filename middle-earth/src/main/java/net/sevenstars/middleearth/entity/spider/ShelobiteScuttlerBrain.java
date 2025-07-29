package net.sevenstars.middleearth.entity.spider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.tasks.SpiderPounceTask;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ShelobiteScuttlerBrain {
	private static final UniformIntProvider POUNCE_COOLDOWN_RANGE = UniformIntProvider.create(50, 80);
	public static final int POUNCE_VERTICAL_RANGE = 1;
	public static final int POUNCE_HORIZONTAL_RANGE = 3;

	protected static Brain<?> create(MirkwoodSpiderEntity shelobiteScuttlerEntity, Brain<MirkwoodSpiderEntity> brain) {
		addCoreActivities(shelobiteScuttlerEntity, brain);
		addIdleActivities(shelobiteScuttlerEntity, brain);
		addFightActivities(shelobiteScuttlerEntity, brain);
		addPounceActivities(shelobiteScuttlerEntity, brain);
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
				Activity.CORE, 0, ImmutableList.of(
						new UpdateLookControlTask(45, 90),
						new MoveToTargetTask(),
						ForgetAngryAtTargetTask.create(),
						new TickCooldownTask(MemoryModuleType.LONG_JUMP_COOLING_DOWN)
				)
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
						ForgetAttackTargetTask.create((world, target) -> !isTarget(world, shelobiteScuttler, target)),
						RangedApproachTask.create(1.0F),
						MeleeAttackTask.create(20)
				),
				MemoryModuleType.ATTACK_TARGET
		);
	}

	private static void addPounceActivities(MirkwoodSpiderEntity shelobiteScuttler, Brain<MirkwoodSpiderEntity> brain) {
		brain.setTaskList(
				Activity.LONG_JUMP,
				ImmutableList.of(
						Pair.of(0, ForgetAttackTargetTask.create(
								(world, target) -> !isTarget(world, shelobiteScuttler, target))
						),
						//new LeapingChargeTask(POUNCE_COOLDOWN_RANGE, SoundEvents.ENTITY_SPIDER_STEP),
						Pair.of(1, new SpiderPounceTask<>(
								POUNCE_COOLDOWN_RANGE, POUNCE_VERTICAL_RANGE, POUNCE_HORIZONTAL_RANGE,
								3.5714288F, spider -> SoundEvents.ENTITY_SPIDER_STEP
						))
				),
				ImmutableSet.of(
						Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT),
						Pair.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT)
				),
				ImmutableSet.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleType.LONG_JUMP_COOLING_DOWN)
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

	private static RandomTask<MirkwoodSpiderEntity> getFollowTasks() {
		return new RandomTask<>(
				ImmutableList.of(
						Pair.of(LookAtMobTask.create(EntityType.PLAYER, 8.0F), 1),
						Pair.of(LookAtMobTask.create(ModEntities.MIRKWOOD_SPIDER, 8.0F), 1),
						Pair.of(LookAtMobTask.create(8.0F), 1),
						Pair.of(new WaitTask(30, 60), 1)
				)
		);
	}

	private static RandomTask<MirkwoodSpiderEntity> getIdleTasks() {
		return new RandomTask<>(
				ImmutableList.of(
						Pair.of(StrollTask.create(0.6F), 2),
						Pair.of(FindEntityTask.create(ModEntities.MIRKWOOD_SPIDER, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
						Pair.of(GoToPosTask.create(MemoryModuleType.HOME, 0.6F, 2, 100), 2),
						Pair.of(GoAroundTask.create(MemoryModuleType.HOME, 0.6F, 5), 2),
						Pair.of(new WaitTask(30, 60), 1)
				)
		);
	}

	protected static void tick(MirkwoodSpiderEntity shelobiteScuttler) {
		Random random = shelobiteScuttler.getWorld().getRandom();
		Brain<MirkwoodSpiderEntity> brain = shelobiteScuttler.getBrain();
		List<Task<? super MirkwoodSpiderEntity>> runningTasks = brain.getRunningTasks();
		brain.resetPossibleActivities(ImmutableList.of(Activity.LONG_JUMP, Activity.FIGHT, Activity.IDLE));
		System.out.println(shelobiteScuttler.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN));
		shelobiteScuttler.setAttacking(brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
	}

	protected static void updateActivities(MirkwoodSpiderEntity shelobiteScuttler) {
		Brain<MirkwoodSpiderEntity> brain = shelobiteScuttler.getBrain();
		Optional<LivingEntity> isAttacking = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
		if(isAttacking != null && isAttacking.isPresent()) {
			Optional<Integer> canJump = brain.getOptionalMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
			if(canJump != null && canJump.isPresent()) {
				int cooldown =  canJump.get();
				if(cooldown == 0) {
					System.out.println("JUMP!!");
					brain.forget(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
				} else {
					brain.resetPossibleActivities(ImmutableList.of(Activity.FIGHT));
				}
			} else {
				brain.resetPossibleActivities(ImmutableList.of(Activity.LONG_JUMP));
				brain.remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, POUNCE_COOLDOWN_RANGE.get(shelobiteScuttler.getRandom()));
			}
		}
		else {
			shelobiteScuttler.getBrain().resetPossibleActivities(ImmutableList.of());
		}
		shelobiteScuttler.getBrain().refreshActivities(shelobiteScuttler.getWorld().getTimeOfDay(), shelobiteScuttler.getWorld().getTime());

		Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
		System.out.println(activity);
	}

	public static void updateActivities(AxolotlEntity axolotl) {
		Brain<AxolotlEntity> brain = axolotl.getBrain();
		Activity activity = (Activity)brain.getFirstPossibleNonCoreActivity().orElse(null);
		if (activity != Activity.PLAY_DEAD) {
			brain.resetPossibleActivities(ImmutableList.of(Activity.PLAY_DEAD, Activity.FIGHT, Activity.IDLE));
			if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
				brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
			}
		}
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

	protected static void setTarget(MirkwoodSpiderEntity shelobiteScuttler, LivingEntity target) {
		shelobiteScuttler.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		shelobiteScuttler.getBrain().remember(MemoryModuleType.ANGRY_AT, target.getUuid(), 600L);
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
