package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

import java.util.List;
import java.util.Objects;

class EscortActivity extends RoutineActivity {
    private static final int ESCORT_RANGE = 24;
    private static final int ESCORT_HEIGHT_RANGE = 8;
    // Only npcs it can see: nobody sets out with someone behind a wall
    private static final TargetPredicate COMPANIONS = TargetPredicate.createNonAttackable();

    EscortActivity(NpcRoutine routine) {
        super(routine);
    }

    void findWard(ServerWorld world) {
        if(!RoutineSites.takeEntitySearch(world))
            return;
        List<NpcEntity> setting = world.getEntitiesByClass(NpcEntity.class, npc.getBoundingBox().expand(ESCORT_RANGE, ESCORT_HEIGHT_RANGE, ESCORT_RANGE),
                other -> other != npc && other.isAlive() && other.isOnOuting() && other.getEscort() == null
                        && Objects.equals(npc.getFactionIdentifier(), other.getFactionIdentifier()));
        NpcEntity ward = world.getClosestEntity(setting, COMPANIONS, npc, npc.getX(), npc.getY(), npc.getZ());
        if(ward != null)
            npc.escort(ward);
    }
}
