package net.sevenstars.middleearth.entity.ai.brain.task.npc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

import java.util.function.Predicate;

public class NpcRangedAttackTask {
    public static <T extends NpcEntity> OneShot<T> create(int cooldown) {
        return create((target) -> true, cooldown);
    }

    public static <T extends NpcEntity> OneShot<T> create(Predicate<T> targetPredicate, int cooldown) {
        return BehaviorBuilder.create((context) -> context.group(
                context.registered(MemoryModuleType.LOOK_TARGET),
                context.present(MemoryModuleType.ATTACK_TARGET),
                context.absent(MemoryModuleType.ATTACK_COOLING_DOWN),
                context.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply(context,
                (lookTarget, attackTarget, attackCoolingDown, visibleMobs) -> (world, entity, time) -> {
            LivingEntity targetEntity = context.get(attackTarget);
            entity.lookAt(targetEntity, 30.0F, 30.0F);

            boolean targetPredicateResult = targetPredicate.test(entity);
            boolean isHoldingRangedWeapon = isHoldingUsableRangedWeapon(entity);
            boolean isInAttackRange = entity.isWithinMeleeAttackRange(targetEntity);
            boolean canSeeIt = (context.get(visibleMobs)).contains(targetEntity);

            if (targetPredicateResult && isHoldingRangedWeapon && isInAttackRange && canSeeIt) {
                lookTarget.set(new EntityTracker(targetEntity, true));
                attackCoolingDown.setWithExpiry(true, cooldown);

                entity.aim();
                if(entity.isReadyToShoot()){
                    entity.shootAt(targetEntity);
                    entity.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                    entity.stopAiming();
                }
                return true;
            } else {
                boolean canForget = false;

                if(targetEntity.isDeadOrDying())
                    canForget = true;
                else {
                    float distanceToEntity = entity.distanceTo(targetEntity);
                    if(distanceToEntity > 50 )
                        canForget = true;
                }


                if(canForget)
                    entity.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);

                return false;
            }
        }));
    }

    private static boolean isHoldingUsableRangedWeapon(Mob mob) {
        return mob.isHolding((stack) -> {
            Item item = stack.getItem();
            return item instanceof ProjectileWeaponItem && mob.canFireProjectileWeapon((ProjectileWeaponItem)item);
        });
    }
}
