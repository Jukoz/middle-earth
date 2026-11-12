package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

// Common folk run home from a fight and call the guards, instead of trading blows
public class NpcFleeHomeGoal extends Goal {
    private static final int THREAT_RANGE = 16;
    private static final int SAFE_RANGE = 24;
    // Close to home there is nowhere left to run, and the npc stands its ground
    private static final int HOME_RANGE = 5;
    private static final int FLEE_DISTANCE = 12;
    // In ticks
    private static final int MAXIMUM_TIME = 20 * 15;
    private static final int PATH_INTERVAL = 20;

    private final NpcEntity npc;
    private final double speed;
    private @Nullable LivingEntity threat = null;
    private int ticks = 0;

    public NpcFleeHomeGoal(NpcEntity npc, double speed) {
        this.npc = npc;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        LivingEntity danger = npc.getTarget() != null ? npc.getTarget() : npc.getAttacker();
        // Game is not a danger
        if(danger == null || !danger.isAlive() || danger == npc.getHuntTarget() || !isTimid())
            return false;
        if(npc.squaredDistanceTo(danger) > THREAT_RANGE * THREAT_RANGE || isHome())
            return false;
        threat = danger;
        return true;
    }

    private boolean isTimid() {
        NpcType type = npc.getNpcType();
        return type != null && type.getRoutine() != null && type.getRoutine().isTimid();
    }

    private boolean isHome() {
        BlockPos home = npc.getRoutineHome();
        return home != null && npc.getStructureManagerHostPos() != null && home.isWithinDistance(npc.getPos(), HOME_RANGE);
    }

    @Override
    public boolean shouldContinue() {
        return threat != null && threat.isAlive() && ticks < MAXIMUM_TIME && !isHome()
                && npc.squaredDistanceTo(threat) < SAFE_RANGE * SAFE_RANGE;
    }

    @Override
    public void start() {
        ticks = 0;
        npc.setTarget(null);
        npc.clearRoutineProp();
        npc.callGuards(threat);
        run();
    }

    @Override
    public void stop() {
        npc.getNavigation().stop();
        threat = null;
    }

    @Override
    public void tick() {
        ticks++;
        if(ticks % PATH_INTERVAL == 0 || npc.getNavigation().isIdle())
            run();
    }

    private void run() {
        BlockPos home = npc.getRoutineHome();
        Vec3d destination = null;
        if(home != null && npc.getStructureManagerHostPos() != null)
            destination = NoPenaltyTargeting.findTo(npc, FLEE_DISTANCE, 7, Vec3d.ofBottomCenter(home), Math.PI / 2);
        if(destination == null && threat != null)
            destination = NoPenaltyTargeting.findFrom(npc, FLEE_DISTANCE, 7, threat.getPos());
        if(destination != null)
            npc.getNavigation().startMovingTo(destination.x, destination.y, destination.z, speed);
    }
}
