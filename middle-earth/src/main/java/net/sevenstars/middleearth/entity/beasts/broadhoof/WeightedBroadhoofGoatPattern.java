package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.util.Util;

import java.util.*;

public class WeightedBroadhoofGoatPattern {
    public BroadhoofGoatPattern pattern;
    public int weight;

    public WeightedBroadhoofGoatPattern(BroadhoofGoatPattern value) {
        this(value, 1);
    }

    public WeightedBroadhoofGoatPattern(BroadhoofGoatPattern value, int weight) {
        this.pattern = value;
        this.weight = weight;
    }

    public static BroadhoofGoatPattern randomWeightedSelect(Set<WeightedBroadhoofGoatPattern> patternSet) {
        Random random = new Random();
        List<BroadhoofGoatPattern> patterns = new ArrayList<>();

        for(WeightedBroadhoofGoatPattern weightedPattern : patternSet) {
            for(int i = 0; i < weightedPattern.weight; i++) {
                patterns.add(weightedPattern.pattern);
            }
        }

        return patterns.get(random.nextInt(patterns.size()));
    }
}
