package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import java.util.EnumSet;

public class BeastTrackOwnerAttackerGoal extends TargetGoal {
    private final AbstractBeastEntity mob;
    private LivingEntity attacker;
    private int lastAttackedTime;

    public BeastTrackOwnerAttackerGoal(AbstractBeastEntity mob) {
        super(mob, false);
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (!this.mob.isTamed() || this.mob.isSitting()) {
            return false;
        }
        if(!this.mob.shouldAttackWhenMounted() && this.mob.hasControllingPassenger()) {
            return false;
        }
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity == null) {
            return false;
        }
        this.attacker = livingEntity.getLastHurtByMob();
        int i = livingEntity.getLastHurtByMobTimestamp();
        return i != this.lastAttackedTime && this.canAttack(this.attacker, TargetingConditions.DEFAULT);
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity != null) {
            this.lastAttackedTime = livingEntity.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}
