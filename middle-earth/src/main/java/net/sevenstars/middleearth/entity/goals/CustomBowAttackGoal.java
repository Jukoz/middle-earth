package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import java.util.EnumSet;

public class CustomBowAttackGoal<T extends PathfinderMob & RangedAttackMob> extends Goal {
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
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public void setAttackInterval(int attackInterval) {
        this.attackInterval = attackInterval;
    }

    @Override
    public boolean canUse() {
        if (this.actor.getTarget() == null) {
            return false;
        }
        return this.isHoldingBow();
    }

    protected boolean isHoldingBow() {
        ItemStack stack = actor.getItemInHand(InteractionHand.MAIN_HAND);
        return stack.getItem() instanceof BowItem;
    }

    @Override
    public boolean canContinueToUse() {
        return (this.canUse() || !this.actor.getNavigation().isDone()) && this.isHoldingBow();
    }

    @Override
    public void start() {
        super.start();
        this.actor.setAggressive(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.actor.setAggressive(false);
        this.targetSeeingTicker = 0;
        this.cooldown = -1;
        this.actor.stopUsingItem();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity != null) {
            double d = this.actor.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean bl = this.actor.getSensing().hasLineOfSight(livingEntity);
            boolean bl2 = this.targetSeeingTicker > 0;
            if (bl != bl2) {
                this.targetSeeingTicker = 0;
            }

            if (bl) {
                ++this.targetSeeingTicker;
            } else {
                --this.targetSeeingTicker;
            }

            if (!(d > (double)this.squaredRange) && this.targetSeeingTicker >= 20) {
                this.actor.getNavigation().stop();

                ++this.combatTicks;
            } else {
                this.actor.getNavigation().moveTo(livingEntity, this.speed);
                this.combatTicks = -1;
            }

            if (this.combatTicks >= 20) {
                if ((double)this.actor.getRandom().nextFloat() < 0.3) {
                    this.movingToLeft = !this.movingToLeft;
                }

                if ((double)this.actor.getRandom().nextFloat() < 0.3) {
                    this.backward = !this.backward;
                }

                this.combatTicks = 0;
            }

            if (this.combatTicks > -1) {
                if (d > (double)(this.squaredRange * 0.75F)) {
                    this.backward = false;
                } else if (d < (double)(this.squaredRange * 0.25F)) {
                    this.backward = true;
                }

                float input = 0.5F;
                if (this.actor.isPassenger() && this.actor.getVehicle() instanceof LivingEntity mount) {
                    double speed = mount.getAttributeValue(Attributes.MOVEMENT_SPEED);
                    input = (float)Math.max(0.15F, Math.min(0.3F, speed * input));
                }

                this.actor.getMoveControl().strafe(
                        this.backward ? -input : input,
                        this.movingToLeft ? input : -input
                );

                Entity var7 = this.actor.getControlledVehicle();
                if (var7 instanceof Mob mobEntity) {
                    mobEntity.lookAt(livingEntity, 30.0F, 30.0F);
                }

                this.actor.lookAt(livingEntity, 30.0F, 30.0F);
            } else {
                this.actor.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
            }

            if (this.actor.isUsingItem()) {
                if (!bl && this.targetSeeingTicker < -60) {
                    this.actor.stopUsingItem();
                } else if (bl) {
                    int i = this.actor.getTicksUsingItem();
                    ItemStack itemStack = this.actor.getMainHandItem();
                    if (i >= 20 && !livingEntity.isInvulnerable()) {
                        if(itemStack.getItem() instanceof CustomLongbowWeaponItem) {
                            if(i >= 30) {
                                this.actor.performRangedAttack(livingEntity, 2.5f);
                                this.cooldown = this.attackInterval;
                                this.actor.stopUsingItem();
                            }
                        } else {
                            this.actor.performRangedAttack(livingEntity, 1.5f);
                            this.cooldown = this.attackInterval;
                            this.actor.stopUsingItem();
                        }
                    }
                }
            } else if (--this.cooldown <= 0 && this.targetSeeingTicker >= -60) {
                this.actor.startUsingItem(InteractionHand.MAIN_HAND);
            }
        }
    }
}
