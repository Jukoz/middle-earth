package net.sevenstars.middleearth.entity.npcs.ai.task.target;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

// A way of picking a target and sticking with it. NpcTargetTask runs one at a time, and a behaviour with a lower priority
// number takes over from one with a higher number. Stopping one drops the target
public abstract class TargetBehaviour {
    private final int priority;
    // In ticks, which are counted in goal ticks
    private final int lostSightTicks;
    private int timeWithoutSight = 0;
    // What it fell back on when the npc's own target was cleared from elsewhere
    protected @Nullable LivingEntity remembered = null;

    protected TargetBehaviour(int priority, int lostSightTicks) {
        this.priority = priority;
        this.lostSightTicks = lostSightTicks;
    }

    public int priority() {
        return priority;
    }

    protected static double followRange(NpcEntity npc) {
        return npc.getAttributeValue(EntityAttributes.FOLLOW_RANGE);
    }

    public abstract boolean canStart(NpcEntity npc);

    public boolean shouldContinue(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        if(target == null)
            target = remembered;
        if(target == null)
            return false;
        if(!npc.canTarget(target))
            return false;
        Team team = npc.getScoreboardTeam();
        Team targetTeam = target.getScoreboardTeam();
        if(team != null && targetTeam == team)
            return false;
        double range = followRange(npc);
        if(npc.squaredDistanceTo(target) > range * range)
            return false;
        if(npc.getVisibilityCache().canSee(target))
            timeWithoutSight = 0;
        else if(++timeWithoutSight > MathHelper.ceilDiv(lostSightTicks, 2))
            return false;
        npc.setTarget(target);
        return true;
    }

    public void start(NpcEntity npc) {
        timeWithoutSight = 0;
    }

    public void stop(NpcEntity npc) {
        npc.setTarget(null);
        remembered = null;
    }
}
