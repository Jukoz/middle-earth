package net.sevenstars.middleearth.entity.npcs.ai.task;

import net.minecraft.entity.ai.brain.Activity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.task.attack.AttackBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.attack.BowAttackBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.attack.CrossbowAttackBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.attack.MeleeAttackBehaviour;
import org.jetbrains.annotations.Nullable;

// Fights with the one behaviour that fits the weapon the npc was last set up with. Each update the behaviour stops if it
// should, then starts if it can, and then ticks, and on the ticks in between it only ticks
public class NpcAttackTask extends NpcTask {
    private final MeleeAttackBehaviour melee = new MeleeAttackBehaviour();
    private final BowAttackBehaviour bow = new BowAttackBehaviour();
    private final CrossbowAttackBehaviour crossbow = new CrossbowAttackBehaviour();
    private @Nullable AttackBehaviour running = null;

    public NpcAttackTask() {
        super(Activity.FIGHT);
    }

    @Override
    protected boolean canStart(NpcEntity npc) {
        return true;
    }

    @Override
    protected boolean shouldContinue(NpcEntity npc) {
        return true;
    }

    @Override
    protected boolean runsEveryTick() {
        return true;
    }

    @Override
    protected void tick(NpcEntity npc) {
        if(isGoalTick(npc)) {
            if(running != null && !running.shouldContinue(npc))
                end(npc);
            if(running == null) {
                AttackBehaviour wanted = wanted(npc);
                if(wanted.canStart(npc)) {
                    running = wanted;
                    wanted.start(npc);
                }
            }
        }
        if(running != null)
            running.tick(npc);
    }

    @Override
    protected void stop(NpcEntity npc) {
        end(npc);
    }

    public void end(NpcEntity npc) {
        if(running == null)
            return;
        AttackBehaviour ended = running;
        running = null;
        ended.stop(npc);
    }

    private AttackBehaviour wanted(NpcEntity npc) {
        return switch(npc.getAttackType()) {
            case BOW -> bow;
            case CROSSBOW -> crossbow;
            case MELEE -> melee;
        };
    }
}
