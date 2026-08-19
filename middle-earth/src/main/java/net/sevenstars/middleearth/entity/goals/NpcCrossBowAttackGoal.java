package net.sevenstars.middleearth.entity.goals;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.EnumSet;

public class NpcCrossBowAttackGoal<T extends NpcEntity & RangedAttackMob & CrossbowAttackMob> extends Goal {
    public static final UniformInt COOLDOWN_RANGE = TimeUtil.rangeOfSeconds(1, 2);
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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.hasAliveTarget() && this.isEntityHoldingCrossbow();
    }

    private boolean isEntityHoldingCrossbow() {
        ItemStack itemStack = actor.getMainHandItem();
        return (itemStack.is(Items.CROSSBOW) || itemStack.is(ItemTagsME.CROSSBOW));
    }

    @Override
    public boolean canContinueToUse() {
        return this.hasAliveTarget() && (this.canUse() || !this.actor.getNavigation().isDone()) && this.isEntityHoldingCrossbow();
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
        this.actor.setAggressive(false);
        this.actor.setTarget(null);
        this.seeingTargetTicker = 0;
        if (this.actor.isUsingItem()) {
            this.actor.stopUsingItem();
            this.actor.setChargingCrossbow(false);
            this.actor.getUseItem().set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity != null) {
            boolean canSee = this.actor.getSensing().hasLineOfSight(livingEntity);

            boolean canSeeTargetTicker = this.seeingTargetTicker > 0;
            if (canSee != canSeeTargetTicker) {
                this.seeingTargetTicker = 0;
            }

            if (canSee) {
                this.seeingTargetTicker++;
            } else {
                this.seeingTargetTicker--;
            }

            double distance = this.actor.distanceToSqr(livingEntity);
            boolean inRange = (distance > this.squaredRange || this.seeingTargetTicker < 5) && this.chargedTicksLeft == 0;
            if (inRange) {
                this.cooldown--;
                if (this.cooldown <= 0) {
                    this.actor.getNavigation().moveTo(livingEntity, this.isUncharged() ? this.speed : this.speed * 0.5);
                    this.cooldown = COOLDOWN_RANGE.sample(this.actor.getRandom());
                }
            } else {
                this.cooldown = 0;
                this.actor.getNavigation().stop();
            }

            this.actor.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
            if (this.stage == Stage.UNCHARGED) {
                if (!inRange) {
                    this.actor.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.actor, Items.CROSSBOW));
                    this.stage = Stage.CHARGING;
                    this.actor.setChargingCrossbow(true);
                }
            } else if (this.stage == Stage.CHARGING) {
                useTicks++;
                this.actor.setChargingCrossbow(true);
                ItemStack itemStack = this.actor.getUseItem();
                this.actor.setNpcFlag(USING_ITEM_FLAG, true);
                if (useTicks >= CrossbowItem.getChargeDuration(itemStack, this.actor)) {
                    this.actor.releaseUsingItem();
                    this.stage = Stage.CHARGED;
                    this.chargedTicksLeft = 30 + this.actor.getRandom().nextInt(15);
                    this.actor.setChargingCrossbow(false);
                }
            } else if (this.stage == Stage.CHARGED) {
                this.chargedTicksLeft--;
                this.actor.setChargingCrossbow(false);
                this.actor.setNpcFlag(USING_ITEM_FLAG, true);
                if(this.chargedTicksLeft <= 1) {
                    this.actor.getMainHandItem().set(
                            DataComponents.CHARGED_PROJECTILES,
                            ChargedProjectiles.of(new ItemStack(Items.ARROW))
                    );
                }
                if (this.chargedTicksLeft == 0) {
                    this.stage = Stage.READY_TO_ATTACK;
                }
            } else if (this.stage == Stage.READY_TO_ATTACK && canSee) {
                this.actor.shootCrossbowAt(livingEntity);
                ItemStack crossbow =  this.actor.getMainHandItem();
                crossbow.remove(DataComponents.CHARGED_PROJECTILES);
                this.actor.setItemSlot(EquipmentSlot.MAINHAND, crossbow);
                this.stage = Stage.UNCHARGED;
                this.actor.setNpcFlag(USING_ITEM_FLAG, false);
                this.actor.releaseUsingItem();
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
