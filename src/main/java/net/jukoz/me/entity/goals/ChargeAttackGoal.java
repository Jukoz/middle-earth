package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;

public class ChargeAttackGoal extends Goal {
    private AbstractBeastEntity mob;
    private final int MAX_COOLDOWN;
    private int checkCanNavigateCooldown;

    public ChargeAttackGoal(AbstractBeastEntity mob, int maxCooldown) {
        this.mob = mob;
        this.MAX_COOLDOWN = maxCooldown;
    }

    @Override
    public boolean canStart() {
        return this.mob.getChargeTimeout() == 0 &&
                (mob.getTarget() != null) &&
                this.mob.getRandom().nextInt(ChargeAttackGoal.toGoalTicks(40)) == 0 &&
                canNavigateToEntity(this.mob.getTarget()) &&
                this.mob.canCharge();
    }

    @Override
    public void start() {
        this.mob.setCharging(true);
        this.mob.setChargeTimeout(this.MAX_COOLDOWN);
        this.checkCanNavigateCooldown = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.checkCanNavigateCooldown > 0) {
            --this.checkCanNavigateCooldown;
        }

    }

    private boolean canNavigateToEntity(LivingEntity entity) {
        int j;
        this.checkCanNavigateCooldown = Goal.toGoalTicks(10 + this.mob.getRandom().nextInt(5));
        Path path = this.mob.getNavigation().findPathTo(entity, 0);
        if (path == null) {
            return false;
        }
        PathNode pathNode = path.getEnd();
        if (pathNode == null) {
            return false;
        }
        int i = pathNode.x - entity.getBlockX();
        return (double)(i * i + (j = pathNode.z - entity.getBlockZ()) * j) <= 2.25;
    }
}
