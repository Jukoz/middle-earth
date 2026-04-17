package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.util.Util;
import net.sevenstars.api.dtos.WeightedItem;

import java.util.*;

public class WeightedBroadhoofGoatPattern extends WeightedItem<BroadhoofGoatPattern> {

    WeightedBroadhoofGoatPattern(BroadhoofGoatPattern item) {
        super(item);
    }

    WeightedBroadhoofGoatPattern(BroadhoofGoatPattern item, int weight) {
        super(item, weight);
    }

    @Override
    public WeightedItem<BroadhoofGoatPattern> withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }
}
