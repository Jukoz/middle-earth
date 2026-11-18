package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

// Evil npcs out at night with no home of their own, with nobody awake to fight, go for the people asleep in their beds.
// Prisoners are left alone
final class RaidBehaviour extends TargetBehaviour {
    // Only 1 in this many think ticks looks for a sleeper
    private static final int SEARCH_CHECKS = 5;
    private static final int LOST_SIGHT_TICKS = 60;
    // Paths are only tried to the nearest few, nearest first
    private static final int PATH_TRIES = 3;

    private @Nullable NpcEntity found = null;

    RaidBehaviour() {
        super(RAID_PRIORITY, LOST_SIGHT_TICKS);
    }

    static boolean isRaiding(NpcEntity npc) {
        return npc.isEvil() && npc.getEntityWorld().isNight() && npc.getStructureManagerHostPos() == null && !npc.isConfined();
    }

    @Override
    boolean canStart(NpcEntity npc) {
        if(npc.getEntityWorld().getDifficulty() == Difficulty.PEACEFUL || !isRaiding(npc))
            return false;
        if(npc.getRandom().nextInt(SEARCH_CHECKS) != 0)
            return false;
        found = find(npc);
        return found != null;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return isRaiding(npc) && super.shouldContinue(npc);
    }

    // A sleeper behind a wall is still there to be found. Once it wakes it's fought like anyone else
    @Override
    boolean needsSight(LivingEntity target) {
        return !target.isSleeping();
    }

    @Override
    void start(NpcEntity npc) {
        npc.setTarget(found);
        super.start(npc);
    }

    private @Nullable NpcEntity find(NpcEntity npc) {
        ServerWorld world = (ServerWorld) npc.getEntityWorld();
        double range = followRange(npc);
        Box box = npc.getBoundingBox().expand(range, range, range);
        List<NpcEntity> raiders = world.getEntitiesByClass(NpcEntity.class, box, other -> other != npc && isRaiding(other));
        List<NpcEntity> sleepers = world.getEntitiesByClass(NpcEntity.class, box, other -> other.isSleeping() && !isPrisoner(other) && npc.canTarget(other));
        // A sleeper another raider is already after comes last, so the raiders spread over the beds
        sleepers.sort(Comparator.<NpcEntity>comparingInt(sleeper -> raiders.stream().anyMatch(raider -> raider.getTarget() == sleeper) ? 1 : 0)
                .thenComparingDouble(npc::squaredDistanceTo));
        for(int i = 0; i < Math.min(PATH_TRIES, sleepers.size()); i++) {
            NpcEntity sleeper = sleepers.get(i);
            Path path = npc.getNavigation().findPathTo(sleeper, 0);
            if(path != null && path.reachesTarget())
                return sleeper;
        }
        return null;
    }

    private static boolean isPrisoner(NpcEntity npc) {
        return npc.isConfined() || NpcJobs.PRISONER.getValue().equals(npc.getJobId());
    }
}
