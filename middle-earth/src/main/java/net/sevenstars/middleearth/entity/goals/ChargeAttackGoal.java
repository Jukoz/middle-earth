package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class ChargeAttackGoal extends Goal {
    private AbstractBeastEntity mob;
    private final int MAX_COOLDOWN;
    private int checkCanNavigateCooldown;
    private DispositionType beastDispositionType;

    public ChargeAttackGoal(AbstractBeastEntity mob, DispositionType beastDispositionType, int maxCooldown) {
        this.mob = mob;
        this.beastDispositionType = beastDispositionType;
        this.MAX_COOLDOWN = maxCooldown;
    }

    @Override
    public boolean canUse() {
        if(this.mob.getTarget() != null && this.mob.getTarget() instanceof Player player) {
            return PlayerDataService.getPlayerDisposition(player, player.level()) == beastDispositionType;
        }

        return this.mob.getChargeTimeout() == 0 &&
                (mob.getTarget() != null) &&
                this.mob.getRandom().nextInt(ChargeAttackGoal.reducedTickDelay(40)) == 0 &&
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
        this.checkCanNavigateCooldown = Goal.reducedTickDelay(10 + this.mob.getRandom().nextInt(5));
        Path path = this.mob.getNavigation().createPath(entity, 0);
        if (path == null) {
            return false;
        }
        Node pathNode = path.getEndNode();
        if (pathNode == null) {
            return false;
        }
        int i = pathNode.x - entity.getBlockX();
        return (double)(i * i + (j = pathNode.z - entity.getBlockZ()) * j) <= 2.25;
    }
}
