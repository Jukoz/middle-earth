package net.sevenstars.middleearth.entity.goals;

import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;

import java.util.EnumSet;

public class BeastTrackOwnerAttackerGoal extends TrackTargetGoal {
    private final AbstractBeastEntity mob;
    private LivingEntity attacker;
    private int lastAttackedTime;

    public BeastTrackOwnerAttackerGoal(AbstractBeastEntity mob) {
        super(mob, false);
        this.mob = mob;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (!this.mob.isTame() || this.mob.isSitting()) {
            return false;
        }
        if(!this.mob.shouldAttackWhenMounted() && this.mob.hasControllingPassenger()) {
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
