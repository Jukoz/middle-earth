package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

// Rate limited path search whose result is kept for the behaviour that asked
final class PathProbe {
    private final long interval;
    private long searchedAt = 0;
    private @Nullable Path path = null;

    PathProbe(long interval) {
        this.interval = interval;
    }

    boolean isDue(long time) {
        return time - searchedAt >= interval;
    }

    // Takes the slot whether or not there is anything to path to
    void search(NpcEntity npc, @Nullable LivingEntity target, long time) {
        searchedAt = time;
        path = target == null || !target.isAlive() ? null : npc.getNavigation().findPathTo(target, 0);
    }

    @Nullable Path path() {
        return path;
    }
}
