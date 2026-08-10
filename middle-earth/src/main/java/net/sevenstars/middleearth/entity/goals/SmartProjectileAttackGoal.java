package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.goals.interfaces.CooldownRangedAttackMob;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SmartProjectileAttackGoal extends Goal {
	private final MobEntity mob;
	private final CooldownRangedAttackMob owner;
	@Nullable
	private LivingEntity target;
	private int updateCountdownTicks = -1;
	private final double mobSpeed;
	private int seenTargetTicks;
	private final int minIntervalTicks;
	private final int maxIntervalTicks;
	private final float minShootRange;
	private final float maxShootRange;
	private final float squaredMaxShootRange;

	public SmartProjectileAttackGoal(CooldownRangedAttackMob mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float minShootRange, float maxShootRange) {
		if (!(mob instanceof LivingEntity)) {
			throw new IllegalArgumentException("ProjectileAttackGoal requires Mob implements RangedAttackMob");
		} else {
			this.owner = mob;
			this.mob = (MobEntity)mob;
			this.mobSpeed = mobSpeed;
			this.minIntervalTicks = minIntervalTicks;
			this.maxIntervalTicks = maxIntervalTicks;
			this.minShootRange = minShootRange;
			this.maxShootRange = maxShootRange;
			this.squaredMaxShootRange = maxShootRange * maxShootRange;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}
	}

	@Override
	public boolean canStart() {
		LivingEntity livingEntity = this.mob.getTarget();
		if (livingEntity != null && livingEntity.isAlive()) {
			this.target = livingEntity;
			return isInbound() && (owner.getRangeAttackCooldown() == 0);
		} else {
			return false;
		}
	}

	public boolean isInbound() {
		float distance = target.distanceTo(this.mob);
		return (distance >= this.minShootRange && distance <= this.maxShootRange);
	}

	@Override
	public boolean shouldContinue() {
		boolean shouldContinue = this.target.isAlive();
		shouldContinue &= isInbound();
		if(this.target instanceof PlayerEntity playerEntity) {
			shouldContinue &= !(playerEntity.isSpectator() || playerEntity.isCreative());
		}
		return shouldContinue;
	}

	@Override
	public void start() {
		super.start();
		int cooldown = mob.getRandom().nextBetween(minIntervalTicks, maxIntervalTicks);
		owner.setRangeAttackCooldown(cooldown);
	}

	@Override
	public void stop() {
		this.target = null;
		this.seenTargetTicks = 0;
		this.updateCountdownTicks = -1;
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		double d = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
		boolean bl = this.mob.getVisibilityCache().canSee(this.target);
		if (bl) {
			this.seenTargetTicks++;
		} else {
			this.seenTargetTicks = 0;
		}

		if (!(d > this.squaredMaxShootRange) && this.seenTargetTicks >= 5) {
			this.mob.getNavigation().stop();
		} else {
			this.mob.getNavigation().startMovingTo(this.target, this.mobSpeed);
		}

		this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
		if (--this.updateCountdownTicks == 0) {
			if (!bl) {
				return;
			}

			float f = (float)Math.sqrt(d) / this.maxShootRange;
			float g = MathHelper.clamp(f, 0.1F, 1.0F);
			this.owner.shootAt(this.target, g);
			this.updateCountdownTicks = MathHelper.floor(f * (this.maxIntervalTicks - this.minIntervalTicks) + this.minIntervalTicks);
		} else if (this.updateCountdownTicks < 0) {
			this.updateCountdownTicks = MathHelper.floor(
				MathHelper.lerp(Math.sqrt(d) / this.maxShootRange, (double)this.minIntervalTicks, (double)this.maxIntervalTicks)
			);
		}
	}
}
