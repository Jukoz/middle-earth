package net.sevenstars.middleearth.entity.npcs.ai.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.NpcBrain;
import org.jetbrains.annotations.Nullable;

public class NpcFleeTask extends NpcTask {
    private static final int SAFE_RANGE = 24;
    private static final int FLEE_DISTANCE = 12;
    private static final int FLEE_HEIGHT = 7;
    private static final double HOME_ANGLE = Math.PI / 2;
    // In goal ticks, and the game ticks them every other tick
    private static final int MAXIMUM_TIME = 20 * 15;
    private static final int PATH_INTERVAL = 20;

    private @Nullable LivingEntity threat = null;
    private int ticks = 0;

    public NpcFleeTask() {
        super(Activity.PANIC);
    }

    @Override
    protected boolean canStart(NpcEntity npc) {
        threat = npc.getBrain().getOptionalRegisteredMemory(MemoryModulesME.FLEEING_FROM).orElse(null);
        return threat != null;
    }

    @Override
    protected boolean shouldContinue(NpcEntity npc) {
        return threat != null && threat.isAlive() && ticks < MAXIMUM_TIME && !npc.isAtHome()
                && npc.squaredDistanceTo(threat) < SAFE_RANGE * SAFE_RANGE;
    }

    @Override
    protected void start(NpcEntity npc) {
        ticks = 0;
        npc.setTarget(null);
        npc.clearRoutineProp();
        npc.callGuards(threat);
        run(npc);
    }

    @Override
    protected void stop(NpcEntity npc) {
        npc.getNavigation().stop();
        threat = null;
        npc.getBrain().forget(MemoryModulesME.FLEEING_FROM);
    }

    @Override
    protected void tick(NpcEntity npc) {
        ticks++;
        if(ticks % PATH_INTERVAL == 0 || npc.getNavigation().isIdle())
            run(npc);
    }

    private void run(NpcEntity npc) {
        BlockPos home = npc.getRoutineHome();
        Vec3d destination = null;
        if(home != null && npc.getStructureManagerHostPos() != null)
            destination = NoPenaltyTargeting.findTo(npc, FLEE_DISTANCE, FLEE_HEIGHT, Vec3d.ofBottomCenter(home), HOME_ANGLE);
        if(destination == null && threat != null)
            destination = NoPenaltyTargeting.findFrom(npc, FLEE_DISTANCE, FLEE_HEIGHT, threat.getEntityPos());
        if(destination != null)
            npc.getNavigation().startMovingTo(destination.x, destination.y, destination.z, NpcBrain.FLEE_SPEED);
    }
}
