package net.sevenstars.middleearth.entity.goals;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.goals.interfaces.Shielder;
import net.sevenstars.middleearth.entity.spider.Pouncer;

import java.util.EnumSet;

public class ShieldAgainstProjctileGoal extends Goal {
    private final PathAwareEntity blocker;
    private final Shielder shielder;
    private LivingEntity target;
    private Path path;
    private int minDistance;
    private int maxDistance;

    public ShieldAgainstProjctileGoal(PathAwareEntity mob, Shielder shielder, int minDistance, int maxDistance) {
        this.blocker = mob;
        this.shielder = shielder;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.blocker.hasPassengers()) {
            return false;
        }
        this.target = this.blocker.getTarget();
        if (this.target == null) {
            return false;
        }

        return shouldContinue();
    }

    @Override
    public void tick() {
        super.tick();
        this.path = this.blocker.getNavigation().findPathTo(target, 0);
        if(this.path != null) {
            this.blocker.getNavigation().startMovingTo(target, 0.7f);
        }
        this.blocker.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        this.blocker.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 21, 1));
    }

    @Override
    public boolean shouldContinue() {
        double d = this.blocker.distanceTo(this.target);
        if (d < this.minDistance || d > this.maxDistance) {
            return false;
        }

        this.path = this.blocker.getNavigation().findPathTo(target, 0);
        if(path == null) return false;

        ItemStack mainStack = target.getMainHandStack();
        if (mainStack.getItem() instanceof RangedWeaponItem) {
            if(target instanceof PlayerEntity player) {
                return player.getItemUseTime() > 1;
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
        this.blocker.getNavigation().startMovingAlong(this.path, 0.7f);
        shielder.blockShield();
    }

    @Override
    public void stop() {
        super.stop();
        shielder.unblockShield();
    }
}
