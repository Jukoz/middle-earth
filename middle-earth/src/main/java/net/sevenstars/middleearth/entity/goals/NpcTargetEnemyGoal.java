package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// Looks for anyone the npc counts as an enemy. NpcRelations decides who that is through canTarget
public class NpcTargetEnemyGoal extends ActiveTargetGoal<LivingEntity> {
    // Only 1 in this many checks actually looks for a target
    private static final int SEARCH_CHANCE = 10;

    private final NpcEntity npc;

    public NpcTargetEnemyGoal(NpcEntity npc) {
        // The animal being hunted is left to the hunt, which sets it as the target itself
        super(npc, LivingEntity.class, SEARCH_CHANCE, true, false, (target, world) -> target != npc.getHuntTarget());
        this.npc = npc;
    }

    @Override
    public boolean canStart() {
        // Timid npcs only go after their game and run from everything else
        return npc.getEntityWorld().getDifficulty() != Difficulty.PEACEFUL && !npc.isTimid() && super.canStart();
    }
}
