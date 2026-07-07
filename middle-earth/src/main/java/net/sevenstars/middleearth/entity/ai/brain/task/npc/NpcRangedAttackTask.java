package net.sevenstars.middleearth.entity.ai.brain.task.npc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

import java.util.function.Predicate;

public class NpcRangedAttackTask {
    public static <T extends NpcEntity> SingleTickTask<T> create(int cooldown) {
        return create((target) -> true, cooldown);
    }

    public static <T extends NpcEntity> SingleTickTask<T> create(Predicate<T> targetPredicate, int cooldown) {
        return TaskTriggerer.task((context) -> context.group(
                context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                context.queryMemoryValue(MemoryModuleType.ATTACK_TARGET),
                context.queryMemoryAbsent(MemoryModuleType.ATTACK_COOLING_DOWN),
                context.queryMemoryValue(MemoryModuleType.VISIBLE_MOBS)).apply(context,
                (lookTarget, attackTarget, attackCoolingDown, visibleMobs) -> (world, entity, time) -> {
            LivingEntity targetEntity = context.getValue(attackTarget);
            entity.lookAtEntity(targetEntity, 30.0F, 30.0F);

            boolean targetPredicateResult = targetPredicate.test(entity);
            boolean isHoldingRangedWeapon = isHoldingUsableRangedWeapon(entity);
            boolean isInAttackRange = entity.isInAttackRange(targetEntity);
            boolean canSeeIt = (context.getValue(visibleMobs)).contains(targetEntity);

            if (targetPredicateResult && isHoldingRangedWeapon && isInAttackRange && canSeeIt) {
                lookTarget.remember(new EntityLookTarget(targetEntity, true));
                attackCoolingDown.remember(true, cooldown);

                entity.aim();
                if(entity.isReadyToShoot()){
                    entity.shootAt(targetEntity);
                    entity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
                    entity.stopAiming();
                }
                return true;
            } else {
                boolean canForget = false;

                if(targetEntity.isDead())
                    canForget = true;
                else {
                    float distanceToEntity = entity.distanceTo(targetEntity);
                    if(distanceToEntity > 50 )
                        canForget = true;
                }


                if(canForget)
                    entity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);

                return false;
            }
        }));
    }

    private static boolean isHoldingUsableRangedWeapon(MobEntity mob) {
        return mob.isHolding((stack) -> {
            Item item = stack.getItem();
            return item instanceof RangedWeaponItem && mob.canUseRangedWeapon((RangedWeaponItem)item);
        });
    }
}
