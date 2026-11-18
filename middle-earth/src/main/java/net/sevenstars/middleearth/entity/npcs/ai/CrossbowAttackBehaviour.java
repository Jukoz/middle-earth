package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.ItemTagsME;

final class CrossbowAttackBehaviour extends AttackBehaviour {
    private static final UniformIntProvider COOLDOWN_RANGE = TimeHelper.betweenSeconds(1, 2);
    private static final int USING_ITEM_FLAG = 1;
    private static final int SEEING_TICKS = 5;
    private static final int CHARGED_HOLD = 30;
    private static final int CHARGED_HOLD_VARIATION = 15;

    // In blocks
    private static final float RANGE = 11.0F;
    private static final float SQUARED_RANGE = RANGE * RANGE;

    private static final double CHARGED_SPEED_FACTOR = 0.5;
    private static final float LOOK_ANGLE = 30.0F;

    private Stage stage = Stage.UNCHARGED;
    private int seeingTargetTicker;
    private int chargedTicksLeft;
    private int cooldown;
    private int useTicks;

    CrossbowAttackBehaviour() {
        super(NpcEntity.AttackType.CROSSBOW);
    }

    @Override
    boolean canAttack(NpcEntity npc) {
        return hasAliveTarget(npc) && isEntityHoldingCrossbow(npc);
    }

    private boolean isEntityHoldingCrossbow(NpcEntity npc) {
        ItemStack itemStack = npc.getMainHandStack();
        return (itemStack.isOf(Items.CROSSBOW) || itemStack.isIn(ItemTagsME.CROSSBOW));
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return canStart(npc);
    }

    private boolean hasAliveTarget(NpcEntity npc) {
        return npc.getTarget() != null && npc.getTarget().isAlive();
    }

    @Override
    void start(NpcEntity npc) {
        useTicks = 0;
    }

    @Override
    void stop(NpcEntity npc) {
        npc.setAttacking(false);
        npc.setTarget(null);
        seeingTargetTicker = 0;
        if (npc.isUsingItem()) {
            npc.clearActiveItem();
            npc.setCharging(false);
            npc.getActiveItem().set(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT);
        }
    }

    @Override
    void tick(NpcEntity npc) {
        LivingEntity livingEntity = npc.getTarget();
        if (livingEntity != null) {
            boolean canSee = npc.getVisibilityCache().canSee(livingEntity);

            boolean canSeeTargetTicker = seeingTargetTicker > 0;
            if (canSee != canSeeTargetTicker) {
                seeingTargetTicker = 0;
            }

            if (canSee) {
                seeingTargetTicker++;
            } else {
                seeingTargetTicker--;
            }

            double distance = npc.squaredDistanceTo(livingEntity);
            boolean approaching = (distance > SQUARED_RANGE || seeingTargetTicker < SEEING_TICKS) && chargedTicksLeft == 0;
            if (approaching) {
                cooldown--;
                if (cooldown <= 0) {
                    npc.getNavigation().startMovingTo(livingEntity, isUncharged() ? NpcBrain.BOW_ATTACK_SPEED : NpcBrain.BOW_ATTACK_SPEED * CHARGED_SPEED_FACTOR);
                    cooldown = COOLDOWN_RANGE.get(npc.getRandom());
                }
            } else {
                cooldown = 0;
                npc.getNavigation().stop();
            }

            npc.getLookControl().lookAt(livingEntity, LOOK_ANGLE, LOOK_ANGLE);
            if (stage == Stage.UNCHARGED) {
                if (!approaching) {
                    npc.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(npc, Items.CROSSBOW));
                    stage = Stage.CHARGING;
                    npc.setCharging(true);
                }
            } else if (stage == Stage.CHARGING) {
                useTicks++;
                npc.setCharging(true);
                ItemStack itemStack = npc.getActiveItem();
                npc.setNpcFlag(USING_ITEM_FLAG, true);
                if (useTicks >= CrossbowItem.getPullTime(itemStack, npc)) {
                    npc.stopUsingItem();
                    stage = Stage.CHARGED;
                    chargedTicksLeft = CHARGED_HOLD + npc.getRandom().nextInt(CHARGED_HOLD_VARIATION);
                    npc.setCharging(false);
                }
            } else if (stage == Stage.CHARGED) {
                chargedTicksLeft--;
                npc.setCharging(false);
                npc.setNpcFlag(USING_ITEM_FLAG, true);
                if(chargedTicksLeft <= 1) {
                    npc.getMainHandStack().set(
                            DataComponentTypes.CHARGED_PROJECTILES,
                            ChargedProjectilesComponent.of(new ItemStack(Items.ARROW))
                    );
                }
                if (chargedTicksLeft == 0) {
                    stage = Stage.READY_TO_ATTACK;
                }
            } else if (stage == Stage.READY_TO_ATTACK && canSee) {
                npc.shootCrossbowAt(livingEntity);
                ItemStack crossbow =  npc.getMainHandStack();
                crossbow.remove(DataComponentTypes.CHARGED_PROJECTILES);
                npc.equipStack(EquipmentSlot.MAINHAND, crossbow);
                stage = Stage.UNCHARGED;
                npc.setNpcFlag(USING_ITEM_FLAG, false);
                npc.stopUsingItem();
            }
        }
    }

    private boolean isUncharged() {
        return stage == Stage.UNCHARGED;
    }

    private enum Stage {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK
    }
}
