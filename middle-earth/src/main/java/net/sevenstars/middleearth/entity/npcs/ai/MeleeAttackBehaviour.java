package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

final class MeleeAttackBehaviour extends AttackBehaviour {
    // Multiple of walking speed
    private static final double MELEE_ATTACK_SPEED = 1.2;
    private static final int ATTACK_INTERVAL = 20;
    private static final long PATH_INTERVAL = 20;
    private static final int REPATH_DELAY = 4;
    private static final int REPATH_DELAY_VARIATION = 7;
    private static final int FAR_DELAY = 10;
    private static final int NEAR_DELAY = 5;
    private static final int NO_PATH_DELAY = 15;

    // Blocks, squared. The farther the target, the longer between paths
    private static final double FAR_DISTANCE = 32 * 32;
    private static final double NEAR_DISTANCE = 16 * 16;
    // Squared. A target that barely moved is only chased now and then
    private static final double MOVED_DISTANCE = 1.0;
    private static final float REPATH_CHANCE = 0.05F;
    private static final float LOOK_ANGLE = 30.0F;

    private @Nullable Path path = null;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int updateCountdown;
    private int cooldown;
    private long lastUpdateTime = 0;

    MeleeAttackBehaviour() {
        super(NpcEntity.AttackType.MELEE);
    }

    @Override
    boolean canAttack(NpcEntity npc) {
        long time = npc.getEntityWorld().getTime();
        if(time - lastUpdateTime < PATH_INTERVAL)
            return false;
        lastUpdateTime = time;
        LivingEntity target = npc.getTarget();
        if(target == null)
            return false;
        if(!target.isAlive())
            return false;
        path = npc.getNavigation().findPathTo(target, 0);
        if(path != null)
            return true;
        return npc.isInAttackRange(target);
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        if(target == null)
            return false;
        if(!target.isAlive())
            return false;
        return !npc.getNavigation().isIdle();
    }

    @Override
    void start(NpcEntity npc) {
        npc.getNavigation().startMovingAlong(path, MELEE_ATTACK_SPEED);
        npc.setAttacking(true);
        updateCountdown = 0;
        cooldown = 0;
    }

    @Override
    void stop(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        if(!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target))
            npc.setTarget(null);
        npc.setAttacking(false);
        npc.getNavigation().stop();
    }

    @Override
    void tick(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        if(target == null)
            return;
        npc.getLookControl().lookAt(target, LOOK_ANGLE, LOOK_ANGLE);
        updateCountdown = Math.max(updateCountdown - 1, 0);
        if(npc.getVisibilityCache().canSee(target) && updateCountdown <= 0
                && (targetX == 0 && targetY == 0 && targetZ == 0 || target.squaredDistanceTo(targetX, targetY, targetZ) >= MOVED_DISTANCE
                || npc.getRandom().nextFloat() < REPATH_CHANCE)) {
            targetX = target.getX();
            targetY = target.getY();
            targetZ = target.getZ();
            updateCountdown = REPATH_DELAY + npc.getRandom().nextInt(REPATH_DELAY_VARIATION);
            double distance = npc.squaredDistanceTo(target);
            if(distance > FAR_DISTANCE)
                updateCountdown += FAR_DELAY;
            else if(distance > NEAR_DISTANCE)
                updateCountdown += NEAR_DELAY;
            if(!npc.getNavigation().startMovingTo(target, MELEE_ATTACK_SPEED))
                updateCountdown += NO_PATH_DELAY;
        }
        cooldown = Math.max(cooldown - 1, 0);
        if(cooldown <= 0 && npc.isInAttackRange(target) && npc.getVisibilityCache().canSee(target)) {
            cooldown = ATTACK_INTERVAL;
            npc.swingHand(Hand.MAIN_HAND);
            npc.tryAttack((ServerWorld) npc.getEntityWorld(), target);
        }
    }
}
