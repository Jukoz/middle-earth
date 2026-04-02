package net.sevenstars.middleearth.entity.beasts.broadhoof;

import java.util.Set;

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

    public BroadhoofGoatPattern randomWeightedSelect(Set<WeightedBroadhoofGoatPattern> patternSet) {
        return BroadhoofGoatPattern.NONE;
    }
}
