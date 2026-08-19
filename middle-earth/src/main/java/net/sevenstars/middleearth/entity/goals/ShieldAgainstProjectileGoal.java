package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.pathfinder.Path;
import net.sevenstars.middleearth.entity.goals.interfaces.Shielder;

import java.util.EnumSet;

public class ShieldAgainstProjectileGoal extends Goal {
    private final PathfinderMob blocker;
    private final Shielder shielder;
    private LivingEntity target;
    private Path path;
    private int minDistance;
    private int maxDistance;

    public ShieldAgainstProjectileGoal(PathfinderMob mob, Shielder shielder, int minDistance, int maxDistance) {
        this.blocker = mob;
        this.shielder = shielder;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.blocker.isVehicle()) {
            return false;
        }
        this.target = this.blocker.getTarget();
        if (this.target == null) {
            return false;
        }

        return canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();
        if(this.path != null) {
            this.blocker.getNavigation().moveTo(target, 0.7f);
        }
        this.blocker.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        this.blocker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 21, 1));
    }

    @Override
    public boolean canContinueToUse() {
        double d = this.blocker.distanceTo(this.target);
        if (d < this.minDistance || d > this.maxDistance) {
            return false;
        }

        this.path = this.blocker.getNavigation().createPath(target, 0);
        if(path == null) return false;

        ItemStack mainStack = target.getMainHandItem();
        if (mainStack.getItem() instanceof ProjectileWeaponItem) {
            if(target instanceof Player player) {
                return player.getTicksUsingItem() > 1;
            } else if(mainStack.getItem() instanceof CrossbowItem) {
                if(CrossbowItem.isCharged(mainStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
        this.blocker.getNavigation().moveTo(this.path, 0.7f);
        shielder.blockShield();
    }

    @Override
    public void stop() {
        super.stop();
        shielder.unblockShield();
    }
}
