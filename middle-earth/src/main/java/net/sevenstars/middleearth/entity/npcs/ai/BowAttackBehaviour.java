package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.Hand;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;

final class BowAttackBehaviour extends AttackBehaviour {
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

    BowAttackBehaviour() {
        super(NpcEntity.AttackType.BOW);
    }

    @Override
    boolean canAttack(NpcEntity npc) {
        return npc.getTarget() != null && isHoldingBow(npc);
    }

    private boolean isHoldingBow(NpcEntity npc) {
        return npc.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BowItem;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return (canStart(npc) || !npc.getNavigation().isIdle()) && isHoldingBow(npc);
    }

    @Override
    void start(NpcEntity npc) {
        npc.setAttacking(true);
    }

    @Override
    void stop(NpcEntity npc) {
        npc.setAttacking(false);
        targetSeeingTicker = 0;
        cooldown = -1;
        npc.clearActiveItem();
    }

    @Override
    void tick(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        if(target == null)
            return;
        double squaredDistance = npc.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
        boolean canSee = npc.getVisibilityCache().canSee(target);
        if(canSee != (targetSeeingTicker > 0))
            targetSeeingTicker = 0;
        if(canSee)
            targetSeeingTicker++;
        else
            targetSeeingTicker--;

        if(squaredDistance <= SQUARED_RANGE && targetSeeingTicker >= SEEING_TICKS) {
            npc.getNavigation().stop();
            combatTicks++;
        } else {
            npc.getNavigation().startMovingTo(target, NpcBrain.BOW_ATTACK_SPEED);
            combatTicks = -1;
        }

        if(combatTicks >= STRAFE_INTERVAL) {
            if(npc.getRandom().nextFloat() < STRAFE_FLIP_CHANCE)
                movingToLeft = !movingToLeft;
            if(npc.getRandom().nextFloat() < STRAFE_FLIP_CHANCE)
                backward = !backward;
            combatTicks = 0;
        }

        if(combatTicks > -1) {
            if(squaredDistance > SQUARED_RANGE * BACK_AWAY_FRACTION)
                backward = false;
            else if(squaredDistance < SQUARED_RANGE * APPROACH_FRACTION)
                backward = true;

            float input = STRAFE_INPUT;
            if(npc.hasVehicle() && npc.getVehicle() instanceof LivingEntity mount) {
                double speed = mount.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
                input = (float) Math.max(MINIMUM_MOUNT_STRAFE, Math.min(MAXIMUM_MOUNT_STRAFE, speed * input));
            }
            npc.getMoveControl().strafeTo(backward ? -input : input, movingToLeft ? input : -input);

            if(npc.getControllingVehicle() instanceof MobEntity mount)
                mount.lookAtEntity(target, LOOK_ANGLE, LOOK_ANGLE);
            npc.lookAtEntity(target, LOOK_ANGLE, LOOK_ANGLE);
        } else {
            npc.getLookControl().lookAt(target, LOOK_ANGLE, LOOK_ANGLE);
        }

        if(npc.isUsingItem()) {
            if(!canSee && targetSeeingTicker < LOST_SIGHT_TICKS) {
                npc.clearActiveItem();
            } else if(canSee) {
                int drawTicks = npc.getItemUseTime();
                if(drawTicks >= BOW_DRAW_TICKS && !target.isInvulnerable()) {
                    if(npc.getMainHandStack().getItem() instanceof CustomLongbowWeaponItem) {
                        if(drawTicks >= LONGBOW_DRAW_TICKS) {
                            npc.shootAt(target, LONGBOW_POWER);
                            cooldown = npc.getBowAttackInterval();
                            npc.clearActiveItem();
                        }
                    } else {
                        npc.shootAt(target, BOW_POWER);
                        cooldown = npc.getBowAttackInterval();
                        npc.clearActiveItem();
                    }
                }
            }
        } else if(--cooldown <= 0 && targetSeeingTicker >= LOST_SIGHT_TICKS) {
            npc.setCurrentHand(Hand.MAIN_HAND);
        }
    }
}
