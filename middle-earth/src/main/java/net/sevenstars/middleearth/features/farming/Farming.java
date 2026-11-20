package net.sevenstars.middleearth.features.farming;

import net.sevenstars.middleearth.common.feature.Feature;

public class Farming implements Feature {
    @Override
    public void register() {
        Feature.load(FarmingBlocks.class, FarmingItems.class);
    }
}
