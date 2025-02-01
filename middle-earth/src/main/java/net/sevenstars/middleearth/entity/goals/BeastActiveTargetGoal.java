package net.sevenstars.middleearth.entity.goals;

import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class BeastActiveTargetGoal<T extends LivingEntity> extends TrackTargetGoal {
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
    protected TargetPredicate targetPredicate;

    public BeastActiveTargetGoal(AbstractBeastEntity mob, Class<T> targetClass, boolean checkVisibility) {
        this(mob, targetClass, 10, checkVisibility, false, null);
    }

    public BeastActiveTargetGoal(AbstractBeastEntity mob, Class<T> targetClass, boolean checkVisibility, TargetPredicate.EntityPredicate targetPredicate) {
        this(mob, targetClass, 10, checkVisibility, false, targetPredicate);
    }

    public BeastActiveTargetGoal(AbstractBeastEntity mob, Class<T> targetClass, boolean checkVisibility, boolean checkCanNavigate) {
        this(mob, targetClass, 10, checkVisibility, checkCanNavigate, null);
    }

    public BeastActiveTargetGoal(AbstractBeastEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable TargetPredicate.EntityPredicate targetPredicate) {
        super(mob, checkVisibility, checkCanNavigate);
        this.targetClass = targetClass;
        this.reciprocalChance = ActiveTargetGoal.toGoalTicks(reciprocalChance);
        this.setControls(EnumSet.of(Control.TARGET));
        this.targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(targetPredicate);
    }

    @Override
    public boolean canStart() {
        if (this.reciprocalChance > 0 && this.mob.getRandom().nextInt(this.reciprocalChance) != 0) {
            return false;
        }
        if(((AbstractBeastEntity)this.mob).isTame()) {
            return false;
        }
        this.findClosestTarget();
        return this.targetEntity != null;
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 4.0, distance);
    }

    protected void findClosestTarget() {
        if(this.mob.getWorld() instanceof ServerWorld serverWorld) {
            this.targetEntity = this.targetClass == PlayerEntity.class || this.targetClass == ServerPlayerEntity.class ? serverWorld.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()) : serverWorld.getClosestEntity(this.mob.getWorld().getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()), livingEntity -> true), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
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
