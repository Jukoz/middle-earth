package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

// A way of picking a target and sticking with it. Stopping one drops the target
abstract class TargetBehaviour extends Behaviour {
    // In ticks, though the sight is only checked on think ticks
    private final int lostSightTicks;
    private int timeWithoutSight = 0;
    // What it fell back on when the npc's own target was cleared from elsewhere
    @Nullable LivingEntity remembered = null;

    TargetBehaviour(int priority, int lostSightTicks) {
        super(priority, TARGET);
        this.lostSightTicks = lostSightTicks;
    }

    static double followRange(NpcEntity npc) {
        return npc.getAttributeValue(EntityAttributes.FOLLOW_RANGE);
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
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
        if(!needsSight(target) || npc.getVisibilityCache().canSee(target))
            timeWithoutSight = 0;
        else if(++timeWithoutSight > MathHelper.ceilDiv(lostSightTicks, 2))
            return false;
        npc.setTarget(target);
        return true;
    }

    boolean needsSight(LivingEntity target) {
        return true;
    }

    @Override
    void start(NpcEntity npc) {
        timeWithoutSight = 0;
    }

    @Override
    void stop(NpcEntity npc) {
        npc.setTarget(null);
        remembered = null;
    }
}
