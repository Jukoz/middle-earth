package net.sevenstars.middleearth.entity.npcs.ai.task.attack;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.NpcBrain;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;

public class BowAttackBehaviour extends AttackBehaviour {
    // In ticks
    private static final int SEEING_TICKS = 20;
    private static final int STRAFE_INTERVAL = 20;
    private static final int LOST_SIGHT_TICKS = -60;
    private static final int BOW_DRAW_TICKS = 20;
    private static final int LONGBOW_DRAW_TICKS = 30;

    // In blocks
    private static final float RANGE = 16.0F;
    private static final float SQUARED_RANGE = RANGE * RANGE;

    private static final double STRAFE_FLIP_CHANCE = 0.3;
    private static final float BACK_AWAY_FRACTION = 0.75F;
    private static final float APPROACH_FRACTION = 0.25F;
    private static final float STRAFE_INPUT = 0.5F;
    private static final float MINIMUM_MOUNT_STRAFE = 0.15F;
    private static final float MAXIMUM_MOUNT_STRAFE = 0.3F;
    private static final float LOOK_ANGLE = 30.0F;
    private static final float BOW_POWER = 1.5f;
    private static final float LONGBOW_POWER = 2.5f;

    private int cooldown = -1;
    private int targetSeeingTicker;
    private boolean movingToLeft;
    private boolean backward;
    private int combatTicks = -1;

    @Override
    public boolean canStart(NpcEntity npc) {
        if(npc.getTarget() == null)
            return false;
        return isHoldingBow(npc);
    }

    private boolean isHoldingBow(NpcEntity npc) {
        ItemStack stack = npc.getStackInHand(Hand.MAIN_HAND);
        return stack.getItem() instanceof BowItem;
    }

    @Override
    public boolean shouldContinue(NpcEntity npc) {
        return (canStart(npc) || !npc.getNavigation().isIdle()) && isHoldingBow(npc);
    }

    @Override
    public void start(NpcEntity npc) {
        npc.setAttacking(true);
    }

    @Override
    public void stop(NpcEntity npc) {
        npc.setAttacking(false);
        targetSeeingTicker = 0;
        cooldown = -1;
        npc.clearActiveItem();
    }

    @Override
    public void tick(NpcEntity npc) {
        LivingEntity livingEntity = npc.getTarget();
        if (livingEntity != null) {
            double d = npc.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean bl = npc.getVisibilityCache().canSee(livingEntity);
            boolean bl2 = targetSeeingTicker > 0;
            if (bl != bl2) {
                targetSeeingTicker = 0;
            }

            if (bl) {
                ++targetSeeingTicker;
            } else {
                --targetSeeingTicker;
            }

            if (!(d > (double)SQUARED_RANGE) && targetSeeingTicker >= SEEING_TICKS) {
                npc.getNavigation().stop();

                ++combatTicks;
            } else {
                npc.getNavigation().startMovingTo(livingEntity, NpcBrain.BOW_ATTACK_SPEED);
                combatTicks = -1;
            }

            if (combatTicks >= STRAFE_INTERVAL) {
                if ((double)npc.getRandom().nextFloat() < STRAFE_FLIP_CHANCE) {
                    movingToLeft = !movingToLeft;
                }

                if ((double)npc.getRandom().nextFloat() < STRAFE_FLIP_CHANCE) {
                    backward = !backward;
                }

                combatTicks = 0;
            }

            if (combatTicks > -1) {
                if (d > (double)(SQUARED_RANGE * BACK_AWAY_FRACTION)) {
                    backward = false;
                } else if (d < (double)(SQUARED_RANGE * APPROACH_FRACTION)) {
                    backward = true;
                }

                float input = STRAFE_INPUT;
                if (npc.hasVehicle() && npc.getVehicle() instanceof LivingEntity mount) {
                    double speed = mount.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
                    input = (float)Math.max(MINIMUM_MOUNT_STRAFE, Math.min(MAXIMUM_MOUNT_STRAFE, speed * input));
                }

                npc.getMoveControl().strafeTo(
                        backward ? -input : input,
                        movingToLeft ? input : -input
                );

                Entity var7 = npc.getControllingVehicle();
                if (var7 instanceof MobEntity mobEntity) {
                    mobEntity.lookAtEntity(livingEntity, LOOK_ANGLE, LOOK_ANGLE);
                }

                npc.lookAtEntity(livingEntity, LOOK_ANGLE, LOOK_ANGLE);
            } else {
                npc.getLookControl().lookAt(livingEntity, LOOK_ANGLE, LOOK_ANGLE);
            }

            if (npc.isUsingItem()) {
                if (!bl && targetSeeingTicker < LOST_SIGHT_TICKS) {
                    npc.clearActiveItem();
                } else if (bl) {
                    int i = npc.getItemUseTime();
                    ItemStack itemStack = npc.getMainHandStack();
                    if (i >= BOW_DRAW_TICKS && !livingEntity.isInvulnerable()) {
                        if(itemStack.getItem() instanceof CustomLongbowWeaponItem) {
                            if(i >= LONGBOW_DRAW_TICKS) {
                                npc.shootAt(livingEntity, LONGBOW_POWER);
                                cooldown = npc.getBowAttackInterval();
                                npc.clearActiveItem();
                            }
                        } else {
                            npc.shootAt(livingEntity, BOW_POWER);
                            cooldown = npc.getBowAttackInterval();
                            npc.clearActiveItem();
                        }
                    }
                }
            } else if (--cooldown <= 0 && targetSeeingTicker >= LOST_SIGHT_TICKS) {
                npc.setCurrentHand(Hand.MAIN_HAND);
            }
        }
    }
}
