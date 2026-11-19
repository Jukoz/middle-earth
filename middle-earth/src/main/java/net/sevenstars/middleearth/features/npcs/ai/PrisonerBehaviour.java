package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.routine.PrisonCell;

final class PrisonerBehaviour extends Behaviour {
    // Multiple of walking speed
    private static final double PRISONER_SPEED = 0.6;
    private static final double RETURN_SPEED = 0.8;
    private static final double FOLLOW_SPEED = 1.0;
    // Blocks
    private static final double FOLLOW_GAP = 2.5;
    // In think ticks
    private static final int PAUSE = 40;
    private static final int PAUSE_VARIATION = 100;

    private int pause = 0;

    PrisonerBehaviour() {
        super(MOVE);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return npc.isConfined() && !npc.isSleeping();
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return canStart(npc);
    }

    @Override
    void tick(NpcEntity npc) {
        Path back = npc.getPrison().takeReturnPath();
        if(back != null)
            npc.getNavigation().startMovingAlong(back, RETURN_SPEED);
        PlayerEntity rescuer = npc.getPrison().getRescuer();
        if(rescuer != null) {
            follow(npc, rescuer);
            return;
        }
        if(--pause > 0 || !npc.getNavigation().isIdle())
            return;
        pause = PAUSE + npc.getRandom().nextInt(PAUSE_VARIATION);
        if(npc.tryEscape())
            return;
        PrisonCell cell = npc.getPrisonCell();
        int x = npc.getRandom().nextBetween(cell.minX(), cell.maxX());
        int z = npc.getRandom().nextBetween(cell.minZ(), cell.maxZ());
        npc.getNavigation().startMovingTo(x + 0.5, npc.getY(), z + 0.5, PRISONER_SPEED);
    }

    private static void follow(NpcEntity npc, PlayerEntity rescuer) {
        npc.getLookControl().lookAt(rescuer);
        if(npc.squaredDistanceTo(rescuer) <= FOLLOW_GAP * FOLLOW_GAP) {
            npc.getNavigation().stop();
            return;
        }
        BlockPos heading = npc.getNavigation().getTargetPos();
        if(!npc.getNavigation().isIdle() && heading != null && heading.isWithinDistance(rescuer.getBlockPos(), FOLLOW_GAP))
            return;
        npc.getNavigation().startMovingTo(rescuer, FOLLOW_SPEED);
    }
}
