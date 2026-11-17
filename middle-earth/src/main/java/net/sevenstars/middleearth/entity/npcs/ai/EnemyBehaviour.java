package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Looks for anyone the npc counts as an enemy. NpcRelations decides who that is through canTarget
final class EnemyBehaviour extends TargetBehaviour {
    // Only 1 in this many think ticks looks for a target
    private static final int SEARCH_CHECKS = 5;
    private static final int LOST_SIGHT_TICKS = 60;

    private @Nullable TargetPredicate predicate = null;
    private @Nullable LivingEntity found = null;

    EnemyBehaviour() {
        super(ENEMY_PRIORITY, LOST_SIGHT_TICKS);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        // Timid npcs only go after their game and run from everything else
        if(npc.getEntityWorld().getDifficulty() == Difficulty.PEACEFUL || npc.isTimid())
            return false;
        if(npc.getRandom().nextInt(SEARCH_CHECKS) != 0)
            return false;
        find(npc);
        return found != null;
    }

    @Override
    void start(NpcEntity npc) {
        npc.setTarget(found);
        super.start(npc);
    }

    private void find(NpcEntity npc) {
        ServerWorld world = (ServerWorld) npc.getEntityWorld();
        if(predicate == null) {
            // The animal being hunted is left to the hunt, which sets it as the target itself
            predicate = TargetPredicate.createAttackable().setPredicate((target, targetWorld) -> target != npc.getHuntTarget());
        }
        double range = followRange(npc);
        Box box = npc.getBoundingBox().expand(range, range, range);
        List<LivingEntity> candidates = world.getEntitiesByClass(LivingEntity.class, box, candidate -> true);
        found = world.getClosestEntity(candidates, predicate.setBaseMaxDistance(range), npc, npc.getX(), npc.getEyeY(), npc.getZ());
    }
}
