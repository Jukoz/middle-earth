package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import java.util.EnumSet;

public class BeastAttackWithOwnerGoal extends TargetGoal {
    private final AbstractBeastEntity mob;
    private LivingEntity attacking;
    private int lastAttackTime;

    public BeastAttackWithOwnerGoal(AbstractBeastEntity mob) {
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
        this.attacking = livingEntity.getLastHurtMob();

        if(this.attacking instanceof AbstractBeastEntity && ((AbstractBeastEntity) this.attacking).getOwner() == this.mob.getOwner()) {
            return false;
        }

        int i = livingEntity.getLastHurtMobTimestamp();
        return i != this.lastAttackTime && this.canAttack(this.attacking, TargetingConditions.DEFAULT);
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacking);
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity != null) {
            this.lastAttackTime = livingEntity.getLastHurtMobTimestamp();
        }
        super.start();
    }
}
