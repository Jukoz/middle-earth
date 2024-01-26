package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;

import java.util.EnumSet;

public class BeastTrackOwnerAttackerGoal extends TrackTargetGoal {
    private final TrollEntity mob;
    private LivingEntity attacker;
    private int lastAttackedTime;

    public BeastTrackOwnerAttackerGoal(TrollEntity mob) {
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
        this.attacker = livingEntity.getAttacker();
        int i = livingEntity.getLastAttackedTime();
        return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT);
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity != null) {
            this.lastAttackedTime = livingEntity.getLastAttackedTime();
        }
        super.start();
    }
}
