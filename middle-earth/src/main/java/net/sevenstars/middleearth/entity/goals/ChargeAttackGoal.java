package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.player.PlayerEntity;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class ChargeAttackGoal extends Goal {
    private AbstractBeastEntity mob;
    private final int MAX_COOLDOWN;
    private int checkCanNavigateCooldown;
    private Disposition beastDisposition;

    public ChargeAttackGoal(AbstractBeastEntity mob, Disposition beastDisposition, int maxCooldown) {
        this.mob = mob;
        this.beastDisposition = beastDisposition;
        this.MAX_COOLDOWN = maxCooldown;
    }

    @Override
    public boolean canStart() {
        if(this.mob.getTarget() != null && this.mob.getTarget() instanceof PlayerEntity player) {
            return PlayerDataService.getPlayerDisposition(player, player.getWorld()) == beastDisposition;
        }

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
