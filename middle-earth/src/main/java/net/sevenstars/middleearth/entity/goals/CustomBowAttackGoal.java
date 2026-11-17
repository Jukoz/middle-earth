package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.attribute.EntityAttributes;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class CustomBowAttackGoal<T extends PathAwareEntity & RangedAttackMob> extends Goal {
    // In ticks
    private static final int SEEING_TICKS = 20;
    private static final int STRAFE_INTERVAL = 20;
    private static final int LOST_SIGHT_TICKS = -60;
    private static final int BOW_DRAW_TICKS = 20;
    private static final int LONGBOW_DRAW_TICKS = 30;

    private static final double STRAFE_FLIP_CHANCE = 0.3;
    private static final float BACK_AWAY_FRACTION = 0.75F;
    private static final float APPROACH_FRACTION = 0.25F;
    private static final float STRAFE_INPUT = 0.5F;
    private static final float MINIMUM_MOUNT_STRAFE = 0.15F;
    private static final float MAXIMUM_MOUNT_STRAFE = 0.3F;
    private static final float LOOK_ANGLE = 30.0F;
    private static final float BOW_POWER = 1.5f;
    private static final float LONGBOW_POWER = 2.5f;

    private final T actor;
    private final double speed;
    private int attackInterval;
    private final float squaredRange;
    private int cooldown = -1;
    private int targetSeeingTicker;
    private boolean movingToLeft;
    private boolean backward;
    private int combatTicks = -1;


    public CustomBowAttackGoal(T actor, double speed, int attackInterval, float range) {
            this.actor = actor;
            this.speed = speed;
            this.attackInterval = attackInterval;
            this.squaredRange = range * range;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public void setAttackInterval(int attackInterval) {
        this.attackInterval = attackInterval;
    }

    @Override
    public boolean canStart() {
        if (this.actor.getTarget() == null) {
            return false;
        }
        return this.isHoldingBow();
    }

    protected boolean isHoldingBow() {
        ItemStack stack = actor.getStackInHand(Hand.MAIN_HAND);
        return stack.getItem() instanceof BowItem;
    }

    @Override
    public boolean shouldContinue() {
        return (this.canStart() || !this.actor.getNavigation().isIdle()) && this.isHoldingBow();
    }

    @Override
    public void start() {
        super.start();
        this.actor.setAttacking(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.actor.setAttacking(false);
        this.targetSeeingTicker = 0;
        this.cooldown = -1;
        this.actor.clearActiveItem();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity != null) {
            double d = this.actor.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean bl = this.actor.getVisibilityCache().canSee(livingEntity);
            boolean bl2 = this.targetSeeingTicker > 0;
            if (bl != bl2) {
                this.targetSeeingTicker = 0;
            }

            if (bl) {
                ++this.targetSeeingTicker;
            } else {
                --this.targetSeeingTicker;
            }

            if (!(d > (double)this.squaredRange) && this.targetSeeingTicker >= SEEING_TICKS) {
                this.actor.getNavigation().stop();

                ++this.combatTicks;
            } else {
                this.actor.getNavigation().startMovingTo(livingEntity, this.speed);
                this.combatTicks = -1;
            }

            if (this.combatTicks >= STRAFE_INTERVAL) {
                if ((double)this.actor.getRandom().nextFloat() < STRAFE_FLIP_CHANCE) {
                    this.movingToLeft = !this.movingToLeft;
                }

                if ((double)this.actor.getRandom().nextFloat() < STRAFE_FLIP_CHANCE) {
                    this.backward = !this.backward;
                }

                this.combatTicks = 0;
            }

            if (this.combatTicks > -1) {
                if (d > (double)(this.squaredRange * BACK_AWAY_FRACTION)) {
                    this.backward = false;
                } else if (d < (double)(this.squaredRange * APPROACH_FRACTION)) {
                    this.backward = true;
                }

                float input = STRAFE_INPUT;
                if (this.actor.hasVehicle() && this.actor.getVehicle() instanceof LivingEntity mount) {
                    double speed = mount.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
                    input = (float)Math.max(MINIMUM_MOUNT_STRAFE, Math.min(MAXIMUM_MOUNT_STRAFE, speed * input));
                }

                this.actor.getMoveControl().strafeTo(
                        this.backward ? -input : input,
                        this.movingToLeft ? input : -input
                );

                Entity var7 = this.actor.getControllingVehicle();
                if (var7 instanceof MobEntity mobEntity) {
                    mobEntity.lookAtEntity(livingEntity, LOOK_ANGLE, LOOK_ANGLE);
                }

                this.actor.lookAtEntity(livingEntity, LOOK_ANGLE, LOOK_ANGLE);
            } else {
                this.actor.getLookControl().lookAt(livingEntity, LOOK_ANGLE, LOOK_ANGLE);
            }

            if (this.actor.isUsingItem()) {
                if (!bl && this.targetSeeingTicker < LOST_SIGHT_TICKS) {
                    this.actor.clearActiveItem();
                } else if (bl) {
                    int i = this.actor.getItemUseTime();
                    ItemStack itemStack = this.actor.getMainHandStack();
                    if (i >= BOW_DRAW_TICKS && !livingEntity.isInvulnerable()) {
                        if(itemStack.getItem() instanceof CustomLongbowWeaponItem) {
                            if(i >= LONGBOW_DRAW_TICKS) {
                                this.actor.shootAt(livingEntity, LONGBOW_POWER);
                                this.cooldown = this.attackInterval;
                                this.actor.clearActiveItem();
                            }
                        } else {
                            this.actor.shootAt(livingEntity, BOW_POWER);
                            this.cooldown = this.attackInterval;
                            this.actor.clearActiveItem();
                        }
                    }
                }
            } else if (--this.cooldown <= 0 && this.targetSeeingTicker >= LOST_SIGHT_TICKS) {
                this.actor.setCurrentHand(Hand.MAIN_HAND);
            }
        }
    }
}
