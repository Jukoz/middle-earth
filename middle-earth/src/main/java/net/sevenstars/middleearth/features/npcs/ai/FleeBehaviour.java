package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

final class FleeBehaviour extends Behaviour {
    private static final int SAFE_RANGE = 24;
    private static final int FLEE_DISTANCE = 12;
    private static final int FLEE_HEIGHT = 7;
    private static final double HOME_ANGLE = Math.PI / 2;
    // As a multiple of the npc's walking speed
    private static final double FLEE_SPEED = 1.15;
    // In think ticks
    private static final int MAXIMUM_TIME = 20 * 15;
    private static final int PATH_INTERVAL = 20;

    private @Nullable LivingEntity threat = null;
    private int ticks = 0;

    FleeBehaviour() {
        super(MOVE);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        threat = npc.getBrain().getOptionalRegisteredMemory(MemoryModulesME.FLEEING_FROM).orElse(null);
        return threat != null;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return threat != null && threat.isAlive() && ticks < MAXIMUM_TIME && !npc.isAtHome()
                && npc.squaredDistanceTo(threat) < SAFE_RANGE * SAFE_RANGE;
    }

    @Override
    void start(NpcEntity npc) {
        ticks = 0;
        npc.setTarget(null);
        npc.clearRoutineProp();
        npc.callGuards(threat);
        flee(npc);
    }

    @Override
    void stop(NpcEntity npc) {
        npc.getNavigation().stop();
        threat = null;
        npc.getBrain().forget(MemoryModulesME.FLEEING_FROM);
    }

    @Override
    void tick(NpcEntity npc) {
        ticks++;
        if(ticks % PATH_INTERVAL == 0 || npc.getNavigation().isIdle())
            flee(npc);
    }

    private void flee(NpcEntity npc) {
        BlockPos home = npc.getRoutineHome();
        Vec3d destination = null;
        if(home != null && npc.getHostPos() != null)
            destination = NoPenaltyTargeting.findTo(npc, FLEE_DISTANCE, FLEE_HEIGHT, Vec3d.ofBottomCenter(home), HOME_ANGLE);
        if(destination == null && threat != null)
            destination = NoPenaltyTargeting.findFrom(npc, FLEE_DISTANCE, FLEE_HEIGHT, threat.getEntityPos());
        if(destination != null)
            npc.getNavigation().startMovingTo(destination.x, destination.y, destination.z, FLEE_SPEED);
    }
}
