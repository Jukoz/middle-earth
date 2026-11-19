package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// Homeless evil npcs at night, with nobody awake to fight, go for sleepers. Not prisoners
final class RaidBehaviour extends TargetBehaviour {
    // One search per this many think ticks
    private static final int SEARCH_CHECKS = 5;
    private static final int LOST_SIGHT_TICKS = 60;
    private static final int PATH_TRIES = 3;
    // Sleepers no path led to are left alone this long, in ticks
    private static final int UNREACHABLE_TICKS = 20 * 5;

    private final Map<UUID, Long> unreachable = new HashMap<>();
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

    // Finds sleepers through walls; once awake they're fought like anyone
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
        long time = world.getTime();
        unreachable.values().removeIf(until -> until <= time);
        double range = followRange(npc);
        Box box = npc.getBoundingBox().expand(range, range, range);
        List<NpcEntity> sleepers = world.getEntitiesByClass(NpcEntity.class, box,
                other -> other.isSleeping() && !other.isPrisoner() && npc.canTarget(other) && !unreachable.containsKey(other.getUuid()));
        if(sleepers.isEmpty())
            return null;
        Set<LivingEntity> pursued = new HashSet<>();
        for(NpcEntity raider : world.getEntitiesByClass(NpcEntity.class, box, other -> other != npc && isRaiding(other)))
            pursued.add(raider.getTarget());
        // Sleepers another raider is after go last, so raiders spread over the beds
        sleepers.sort(Comparator.<NpcEntity>comparingInt(sleeper -> pursued.contains(sleeper) ? 1 : 0)
                .thenComparingDouble(npc::squaredDistanceTo));
        List<NpcEntity> tried = sleepers.subList(0, Math.min(PATH_TRIES, sleepers.size()));
        for(NpcEntity sleeper : tried) {
            Path path = npc.getNavigation().findPathTo(sleeper, 0);
            if(path != null && path.reachesTarget())
                return sleeper;
        }
        for(NpcEntity sleeper : tried)
            unreachable.put(sleeper.getUuid(), time + UNREACHABLE_TICKS);
        return null;
    }
}
