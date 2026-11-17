package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.List;
import java.util.Objects;

public class EscortActivity extends RoutineActivity {
    private static final int ESCORT_RANGE = 24;
    private static final int ESCORT_HEIGHT_RANGE = 8;
    // Only npcs it can see, since nobody sets out with someone on the other side of a wall
    private static final TargetPredicate COMPANIONS = TargetPredicate.createNonAttackable();

    public EscortActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        return true;
    }

    // The nearest of the place's own npcs who is setting out and doesn't have anybody with them yet
    public void findWard(ServerWorld world) {
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
