package net.sevenstars.middleearth.entity.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.entity.ai.behavior.LongJumpUtil;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class SpiderPounceTask<E extends Mob> extends Behavior<E> {
	protected static final int MAX_TARGET_SEARCH_TIME = 20;
	private static final int JUMP_WINDUP_TIME = 40;
	protected static final int PATHING_DISTANCE = 8;
	private static final int RUN_TIME = 200;
	private static final List<Integer> POUNCE_ANGLES = Lists.<Integer>newArrayList(30, 37, 45, 50, 55);
	private final UniformInt cooldownRange;
	protected final int verticalRange;
	protected final int horizontalRange;
	protected final float maxRange;
	protected List<Target> potentialTargets = Lists.<Target>newArrayList();
	protected Optional<Vec3> startPos = Optional.empty();
	@Nullable
	protected Vec3 currentTarget;
	protected int targetSearchTime;
	protected long targetPickedTime;
	private final Function<E, SoundEvent> entityToSound;
	private final BiPredicate<E, BlockPos> jumpToPredicate;

	public SpiderPounceTask(UniformInt cooldownRange, int verticalRange, int horizontalRange, float maxRange,
			Function<E, SoundEvent> entityToSound) {
		super(
				ImmutableMap.of(
						MemoryModuleType.LOOK_TARGET,
						MemoryStatus.REGISTERED
						//MemoryModuleType.LONG_JUMP_COOLING_DOWN,
						//MemoryModuleState.VALUE_ABSENT,
						//MemoryModuleType.LONG_JUMP_MID_JUMP,
						//MemoryModuleState.VALUE_ABSENT
				),
				200
		);
		this.cooldownRange = cooldownRange;
		this.verticalRange = verticalRange;
		this.horizontalRange = horizontalRange;
		this.maxRange = maxRange;
		this.entityToSound = entityToSound;
		this.jumpToPredicate = LongJumpToRandomPos::defaultAcceptableLandingSpot;
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel serverWorld, Mob mobEntity) {
		boolean validTerrain = mobEntity.onGround();
				//&& !mobEntity.isTouchingWater()
				//&& !mobEntity.isInLava()
				//&& !serverWorld.getBlockState(mobEntity.getBlockPos()).isOf(Blocks.HONEY_BLOCK);
		if (!validTerrain) {
			mobEntity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, 40);
		}

		return validTerrain;
	}

	@Override
	protected boolean canStillUse(ServerLevel serverWorld, Mob mobEntity, long l) {
		boolean canJump = this.startPos.isPresent();
		canJump = canJump && this.targetSearchTime > 0;
		canJump = canJump && !mobEntity.isInWater();
		canJump = canJump && (this.currentTarget != null || !this.potentialTargets.isEmpty());

		//Optional<Integer> optionalMemory = mobEntity.getBrain().getOptionalMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
		//if(optionalMemory != null && optionalMemory.isPresent()) {
		//	canJump = false;
		//}

		if (!canJump) { //&& mobEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LONG_JUMP_MID_JUMP).isEmpty()) {
			mobEntity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, cooldownRange.sample(mobEntity.getRandom()));
		}

		return canJump;
	}

	@Override
	protected void start(ServerLevel serverWorld, E mobEntity, long l) {
		this.currentTarget = null;
		Optional<LivingEntity> targetEntity = mobEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
		if(targetEntity.isPresent()) {
			LivingEntity value = targetEntity.get();
			this.potentialTargets.add(new Target(value.position(), 1));
		}
		this.targetSearchTime = 20;
		this.startPos = Optional.of(mobEntity.position());
	}

	@Override
	protected void tick(ServerLevel serverWorld, E mobEntity, long l) {
		if (this.currentTarget != null) {
			if (l - this.targetPickedTime >= 40L) {
				mobEntity.setYRot(mobEntity.yBodyRot);
				mobEntity.setDiscardFriction(true);
				double d = this.currentTarget.length();
				double e = d + mobEntity.getJumpBoostPower();
				mobEntity.setDeltaMovement(this.currentTarget.scale(e / d));
				mobEntity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, cooldownRange.sample(mobEntity.getRandom()));
				serverWorld.playSound(null, mobEntity, (SoundEvent)this.entityToSound.apply(mobEntity), SoundSource.NEUTRAL, 1.0F, 1.0F);
				potentialTargets.clear();
				targetSearchTime = 0;
			}
		} else {
			this.targetSearchTime--;
			this.pickTarget(serverWorld, mobEntity, l);
		}
	}

	@Override
	protected void stop(ServerLevel world, E entity, long time) {
		super.stop(world, entity, time);
		entity.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		entity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, this.cooldownRange.sample(world.getRandom()));
	}

	protected void pickTarget(ServerLevel world, E entity, long time) {
		while (!this.potentialTargets.isEmpty()) {
			Optional<Target> optional = this.removeRandomTarget(world);
			if (!optional.isEmpty()) {
				Target target = optional.get();
				BlockPos pos = new BlockPos((int)target.pos.x, (int)target.pos.y, (int)target.pos.z);
				if (this.canJumpTo(world, entity, pos)) {
					Vec3 vec3d = Vec3.atCenterOf(pos);
					Vec3 vec3d2 = this.getJumpingVelocity(entity, vec3d);
					if (vec3d2 != null) {
						entity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(pos));
						PathNavigation entityNavigation = entity.getNavigation();
						Path path = entityNavigation.createPath(pos, 0, 8);
						if (path == null || !path.canReach()) {
							this.currentTarget = vec3d2;
							this.targetPickedTime = time;
							return;
						}
					}
				}
			}
		}
	}

	protected Optional<Target> removeRandomTarget(ServerLevel world) {
		Optional<Target> optional = WeightedRandom.getRandomItem(world.random, this.potentialTargets);
		optional.ifPresent(this.potentialTargets::remove);
		return optional;
	}

	private boolean canJumpTo(ServerLevel world, E entity, BlockPos pos) {
		BlockPos blockPos = entity.blockPosition();
		int i = blockPos.getX();
		int j = blockPos.getZ();
		return i == pos.getX() && j == pos.getZ() ? false : this.jumpToPredicate.test(entity, pos);
	}

	@Nullable
	protected Vec3 getJumpingVelocity(Mob entity, Vec3 targetPos) {
		List<Integer> list = Lists.<Integer>newArrayList(POUNCE_ANGLES);
		Collections.shuffle(list);
		float f = (float)(entity.getAttributeValue(Attributes.JUMP_STRENGTH) * this.maxRange);

		for (int i : list) {
			Optional<Vec3> optional = LongJumpUtil.calculateJumpVectorForAngle(entity, targetPos, f, i, true);
			if (optional.isPresent()) {
				return (Vec3)optional.get();
			}
		}

		return null;
	}

	public record Target(Vec3 pos, Weight weight) implements WeightedEntry {
		public Target(Vec3 pos, int weight) {
			this(pos, Weight.of(weight));
		}

		@Override
		public Weight getWeight() {
			return this.weight;
		}
	}
}
