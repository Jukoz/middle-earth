package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;

import java.util.EnumSet;

public class BeastAttackWithOwnerGoal extends TrackTargetGoal {
    private final TrollEntity mob;
    private LivingEntity attacking;
    private int lastAttackTime;

    public BeastAttackWithOwnerGoal(TrollEntity mob) {
        super(mob, false);
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (!this.mob.isTame() || this.mob.isSitting()) {
            return false;
        }
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity == null) {
            return false;
        }
        this.attacking = livingEntity.getAttacking();
        int i = livingEntity.getLastAttackTime();
        return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT);
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacking);
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity != null) {
            this.lastAttackTime = livingEntity.getLastAttackTime();
        }
        super.start();
    }
}
