package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.rule.GameRules;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.relations.NpcRelations;

final class RevengeBehaviour extends TargetBehaviour {
    // In ticks. Someone who hit the npc is followed for longer than a stranger
    private static final int LOST_SIGHT_TICKS = 300;

    private static final TargetPredicate AVOIDABLE = TargetPredicate.createAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

    private int lastAttackedTime = 0;

    RevengeBehaviour() {
        super(REVENGE_PRIORITY, LOST_SIGHT_TICKS);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        LivingEntity attacker = npc.getAttacker();
        // Common folk strike back unless they have run for the guards. At home there's nowhere left to run, so the npc calls
        // the guards from where it stands and fights
        if(npc.isTimid() && attacker != null && !NpcRelations.of(npc, attacker).isFriendly()) {
            if(npc.isAtHome())
                npc.callGuardsOnce(attacker);
            else if(npc.fleesFrom(attacker))
                return false;
        }
        int attackedTime = npc.getLastAttackedTime();
        if(attackedTime == lastAttackedTime || attacker == null)
            return false;
        ServerWorld world = (ServerWorld) npc.getEntityWorld();
        if(attacker.getType() == EntityType.PLAYER && world.getGameRules().getValue(GameRules.UNIVERSAL_ANGER))
            return false;
        return AVOIDABLE.test(world, npc, attacker) && npc.isInPositionTargetRange(attacker.getBlockPos());
    }

    @Override
    void start(NpcEntity npc) {
        npc.setTarget(npc.getAttacker());
        remembered = npc.getTarget();
        lastAttackedTime = npc.getLastAttackedTime();
        super.start(npc);
    }
}
