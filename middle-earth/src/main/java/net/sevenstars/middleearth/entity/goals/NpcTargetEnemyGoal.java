package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// Looks around for anyone the npc counts as an enemy. Who that is, NpcRelations decides through canTarget
public class NpcTargetEnemyGoal extends ActiveTargetGoal<LivingEntity> {
    // One in this many checks looks for a target
    private static final int SEARCH_CHANCE = 10;

    private final NpcEntity npc;

    public NpcTargetEnemyGoal(NpcEntity npc) {
        // Game being hunted is left to the hunt
        super(npc, LivingEntity.class, SEARCH_CHANCE, true, false, (target, world) -> target != npc.getHuntTarget());
        this.npc = npc;
    }

    @Override
    public boolean canStart() {
        // The timid only ever go after their game, and run from the rest
        return npc.getWorld().getDifficulty() != Difficulty.PEACEFUL && !npc.isTimid() && super.canStart();
    }
}
