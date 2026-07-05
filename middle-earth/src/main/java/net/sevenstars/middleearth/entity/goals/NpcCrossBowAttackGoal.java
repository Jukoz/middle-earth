package net.sevenstars.middleearth.entity.goals;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.EnumSet;

public class NpcCrossBowAttackGoal<T extends NpcEntity & RangedAttackMob & CrossbowUser> extends Goal {
    public static final UniformIntProvider COOLDOWN_RANGE = TimeHelper.betweenSeconds(1, 2);
    private static int USING_ITEM_FLAG = 1;
    private final T actor;
    private Stage stage = Stage.UNCHARGED;
    private final double speed;
    private final float squaredRange;
    private int seeingTargetTicker;
    private int chargedTicksLeft;
    private int cooldown;
    private int useTicks;

    public NpcCrossBowAttackGoal(T actor, double speed, float range) {
        this.actor = actor;
        this.speed = speed;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return this.hasAliveTarget() && this.isEntityHoldingCrossbow();
    }

    private boolean isEntityHoldingCrossbow() {
        ItemStack itemStack = actor.getMainHandStack();
        return (itemStack.isOf(Items.CROSSBOW) || itemStack.isIn(ItemTagsME.CROSSBOW));
    }

    @Override
    public boolean shouldContinue() {
        return this.hasAliveTarget() && (this.canStart() || !this.actor.getNavigation().isIdle()) && this.isEntityHoldingCrossbow();
    }

    private boolean hasAliveTarget() {
        return this.actor.getTarget() != null && this.actor.getTarget().isAlive();
    }

    @Override
    public void start() {
        super.start();
        useTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.actor.setAttacking(false);
        this.actor.setTarget(null);
        this.seeingTargetTicker = 0;
        if (this.actor.isUsingItem()) {
            this.actor.clearActiveItem();
            this.actor.setCharging(false);
            this.actor.getActiveItem().set(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT);
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity != null) {
            boolean canSee = this.actor.getVisibilityCache().canSee(livingEntity);

            boolean canSeeTargetTicker = this.seeingTargetTicker > 0;
            if (canSee != canSeeTargetTicker) {
                this.seeingTargetTicker = 0;
            }

            if (canSee) {
                this.seeingTargetTicker++;
            } else {
                this.seeingTargetTicker--;
            }

            double distance = this.actor.squaredDistanceTo(livingEntity);
            boolean inRange = (distance > this.squaredRange || this.seeingTargetTicker < 5) && this.chargedTicksLeft == 0;
            if (inRange) {
                this.cooldown--;
                if (this.cooldown <= 0) {
                    this.actor.getNavigation().startMovingTo(livingEntity, this.isUncharged() ? this.speed : this.speed * 0.5);
                    this.cooldown = COOLDOWN_RANGE.get(this.actor.getRandom());
                }
            } else {
                this.cooldown = 0;
                this.actor.getNavigation().stop();
            }

            this.actor.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
            if (this.stage == Stage.UNCHARGED) {
                if (!inRange) {
                    this.actor.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this.actor, Items.CROSSBOW));
                    this.stage = Stage.CHARGING;
                    this.actor.setCharging(true);
                }
            } else if (this.stage == Stage.CHARGING) {
                useTicks++;
                this.actor.setCharging(true);
                ItemStack itemStack = this.actor.getActiveItem();
                this.actor.setNpcFlag(USING_ITEM_FLAG, true);
                if (useTicks >= CrossbowItem.getPullTime(itemStack, this.actor)) {
                    this.actor.stopUsingItem();
                    this.stage = Stage.CHARGED;
                    this.chargedTicksLeft = 30 + this.actor.getRandom().nextInt(15);
                    this.actor.setCharging(false);
                }
            } else if (this.stage == Stage.CHARGED) {
                this.chargedTicksLeft--;
                this.actor.setCharging(false);
                this.actor.setNpcFlag(USING_ITEM_FLAG, true);
                if(this.chargedTicksLeft <= 1) {
                    this.actor.getMainHandStack().set(
                            DataComponentTypes.CHARGED_PROJECTILES,
                            ChargedProjectilesComponent.of(new ItemStack(Items.ARROW))
                    );
                }
                if (this.chargedTicksLeft == 0) {
                    this.stage = Stage.READY_TO_ATTACK;
                }
            } else if (this.stage == Stage.READY_TO_ATTACK && canSee) {
                this.actor.shootCrossbowAt(livingEntity);
                ItemStack crossbow =  this.actor.getMainHandStack();
                crossbow.remove(DataComponentTypes.CHARGED_PROJECTILES);
                this.actor.equipStack(EquipmentSlot.MAINHAND, crossbow);
                this.stage = Stage.UNCHARGED;
                this.actor.setNpcFlag(USING_ITEM_FLAG, false);
                this.actor.stopUsingItem();
            }
        }
    }

    private boolean isUncharged() {
        return this.stage == Stage.UNCHARGED;
    }

    enum Stage {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK;
    }
}
