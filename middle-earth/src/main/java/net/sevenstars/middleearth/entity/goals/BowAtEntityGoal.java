package net.sevenstars.middleearth.entity.goals;

import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class BowAtEntityGoal extends Goal {
	public static final float DEFAULT_CHANCE = 0.02F;
	protected final PathfinderMob mob;
	@Nullable
	protected Entity target;
	protected final float range;
	private int lookTime;
	private final boolean lookForward;
	private final PathNavigation navigation;
	protected final Class<? extends LivingEntity> targetType;
	protected final TargetingConditions targetPredicate;
	protected final Predicate<LivingEntity> targetSelector;

	public BowAtEntityGoal(PathfinderMob mob, Class<? extends LivingEntity> targetType, float range, Predicate<LivingEntity> targetSelector) {
		this(mob, targetType, range, targetSelector, false);
	}

	public BowAtEntityGoal(PathfinderMob mob, Class<? extends LivingEntity> targetType, float range, Predicate<LivingEntity> targetSelector, boolean lookForward) {
		this.mob = mob;
		this.targetType = targetType;
		this.range = range;
		this.lookForward = lookForward;
		this.navigation = mob.getNavigation();
		this.targetSelector = targetSelector;
		this.setFlags(EnumSet.of(Flag.LOOK));
		if (targetType == Player.class) {
			this.targetPredicate = TargetingConditions.forNonCombat().range((double)range).selector(targetSelector);
		} else {
			this.targetPredicate = TargetingConditions.forNonCombat().range((double)range);
		}
	}

	public boolean canUse() {
		if (this.mob.getTarget() != null) {
			this.target = this.mob.getTarget();
		}

		if (!(this.mob.level() instanceof ServerLevel serverWorld)) {
			return false;
		}
		if (this.targetType == Player.class) {
			this.target = serverWorld.getNearestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
		} else {
			this.target = serverWorld.getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.mob.getBoundingBox().inflate((double)this.range, 3.0, (double)this.range), (livingEntity) -> {
				return true;
			}), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
		}

		return this.target != null;

	}

	public boolean canContinueToUse() {
		if (this.target == null || !this.target.isAlive()) {
			return false;
		} else if (this.mob.distanceToSqr(this.target) > (double)(this.range * this.range)) {
			return false;
		} else {
			return this.lookTime > 0;
		}
	}

	public void start() {
		this.lookTime = 85;
		this.navigation.stop();
	}

	public void stop() {
		this.target = null;
	}

	public void tick() {
		if (this.target.isAlive()) {
			this.navigation.stop();
			double d = this.lookForward ? this.mob.getEyeY() : this.target.getEyeY();
			this.mob.getLookControl().setLookAt(this.target.getX(), d, this.target.getZ());
			--this.lookTime;
		}
	}
}
