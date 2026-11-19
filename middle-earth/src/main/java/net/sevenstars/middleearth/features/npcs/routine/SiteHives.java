package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

// Honey in settlement hives, which have no bees to fill them
class SiteHives {
    private final Map<BlockPos, Long> hivesEmptied = new HashMap<>();
    private final Map<BlockPos, Integer> hiveLevels = new HashMap<>();

    // For an unseen hive, guessed from the honey in it
    long emptiedAt(BlockPos hive, long time, int levelFound, long filledFor) {
        Integer left = hiveLevels.get(hive);
        if(left == null || left != levelFound)
            hivesEmptied.put(hive, time - filledFor);
        return hivesEmptied.get(hive);
    }

    void noteLevel(BlockPos hive, int level) {
        hiveLevels.put(hive, level);
    }

    void empty(BlockPos hive, long time) {
        hivesEmptied.put(hive, time);
        hiveLevels.put(hive, 0);
    }
}
