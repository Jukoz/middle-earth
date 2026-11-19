package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

// The pause roll of a village stroll, which is also when a stroll counts towards being stuck
final class StuckRescueBehaviour extends Behaviour {
    // 1 in this many think ticks
    private static final int STROLL_PAUSE = 20;
    // Strolls from the same block before it counts as stuck, about 10 seconds
    private static final int STUCK_STROLLS = 5;
    private static final int MAXIMUM_RESCUE_BACK_OFF = 4;
    private static final int RESCUE_CLEAR_DISTANCE = 4;
    private static final int RESCUE_RADIUS = 3;
    private static final int RESCUE_DEPTH = 3;

    private final TravelBehaviour travel;
    private @Nullable BlockPos lastStrollPos = null;
    private int stuckStrolls = 0;
    private int rescues = 0;
    private @Nullable BlockPos rescuedAt = null;

    StuckRescueBehaviour(TravelBehaviour travel) {
        super(TRAVEL_PRIORITY, NONE, MOVE);
        this.travel = travel;
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return TravelBehaviour.isStrolling(npc) && npc.getRandom().nextInt(STROLL_PAUSE) == 0;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return false;
    }

    @Override
    void start(NpcEntity npc) {
        travel.skipChance();
        checkStuck(npc);
    }

    // Furniture is a trap: an npc on a stool or table finds no path off and stays forever
    private void checkStuck(NpcEntity npc) {
        BlockPos pos = npc.getBlockPos();
        if(!pos.equals(lastStrollPos) || npc.isSleeping()) {
            if(rescuedAt != null && !rescuedAt.isWithinDistance(pos, RESCUE_CLEAR_DISTANCE)) {
                rescuedAt = null;
                rescues = 0;
            }
            lastStrollPos = pos;
            stuckStrolls = 0;
            return;
        }
        // Shut in a small room, it waits longer each time or it'd hop around its cell
        stuckStrolls++;
        if(stuckStrolls < STUCK_STROLLS << Math.min(rescues, MAXIMUM_RESCUE_BACK_OFF) || !(npc.getEntityWorld() instanceof ServerWorld world))
            return;
        stuckStrolls = 0;
        rescues++;
        rescuedAt = pos;
        npc.getNavigation().stop();
        BlockPos best = NestSurvey.freeFloorNear(world, pos, RESCUE_RADIUS, RESCUE_DEPTH);
        if(best != null)
            npc.refreshPositionAndAngles(best.getX() + 0.5, best.getY(), best.getZ() + 0.5, npc.getYaw(), npc.getPitch());
    }
}
