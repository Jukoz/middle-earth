package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// Looks around for anyone the npc counts as an enemy. Who that is, NpcRelations decides through canTarget
public class NpcTargetEnemyGoal extends ActiveTargetGoal<LivingEntity> {
    private final NpcEntity npc;

    public NpcTargetEnemyGoal(NpcEntity npc) {
        // Game being hunted is left to the hunt
        super(npc, LivingEntity.class, 10, true, false, (target, world) -> target != npc.getHuntTarget());
        this.npc = npc;
    }

    @Override
    public boolean canStart() {
        return npc.getWorld().getDifficulty() != Difficulty.PEACEFUL && super.canStart();
    }
}
