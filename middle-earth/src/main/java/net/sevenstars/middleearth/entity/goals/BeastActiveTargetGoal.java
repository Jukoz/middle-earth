package net.sevenstars.middleearth.entity.goals;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class BeastActiveTargetGoal<T extends LivingEntity> extends TargetGoal {
    private static final int DEFAULT_RECIPROCAL_CHANCE = 10;
    protected final Class<T> targetClass;
    /**
     * The reciprocal of chance to actually search for a target on every tick
     * when this goal is not started. This is also the average number of ticks
     * between each search (as in a poisson distribution).
     */
    protected final int reciprocalChance;
    @Nullable
    protected LivingEntity targetEntity;
    protected TargetingConditions targetPredicate;

    public BeastActiveTargetGoal(AbstractBeastEntity mob, Class<T> targetClass, boolean checkVisibility) {
        this(mob, targetClass, 10, checkVisibility, false, null);
    }

    public BeastActiveTargetGoal(AbstractBeastEntity mob, Class<T> targetClass, boolean checkVisibility, Predicate<LivingEntity> targetPredicate) {
        this(mob, targetClass, 10, checkVisibility, false, targetPredicate);
    }

    public BeastActiveTargetGoal(AbstractBeastEntity mob, Class<T> targetClass, boolean checkVisibility, boolean checkCanNavigate) {
        this(mob, targetClass, 10, checkVisibility, checkCanNavigate, null);
    }

    public BeastActiveTargetGoal(AbstractBeastEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, checkVisibility, checkCanNavigate);
        this.targetClass = targetClass;
        this.reciprocalChance = NearestAttackableTargetGoal.reducedTickDelay(reciprocalChance);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetPredicate = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(targetPredicate);
    }

    @Override
    public boolean canUse() {
        if (this.reciprocalChance > 0 && this.mob.getRandom().nextInt(this.reciprocalChance) != 0) {
            return false;
        }
        if(((AbstractBeastEntity)this.mob).isTamed()) {
            return false;
        }
        this.findClosestTarget();
        return this.targetEntity != null;
    }

    protected AABB getSearchBox(double distance) {
        return this.mob.getBoundingBox().inflate(distance, 4.0, distance);
    }

    protected void findClosestTarget() {
        if(this.mob.level() instanceof ServerLevel serverWorld) {
            this.targetEntity = this.targetClass == Player.class || this.targetClass == ServerPlayer.class ? serverWorld.getNearestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()) : serverWorld.getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetClass, this.getSearchBox(this.getFollowDistance()), livingEntity -> true), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.targetEntity);
        super.start();
    }

    public void setTargetEntity(@Nullable LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }
}
