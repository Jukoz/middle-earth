package net.sevenstars.middleearth.features.smithing;

import net.sevenstars.middleearth.common.feature.Feature;

public class Smithing implements Feature {
    @Override
    public void register() {
        SmithingRecipes.register();
        Feature.load(SmithingBlocks.class, SmithingBlockEntities.class);
        SmithingScreenHandlers.register();
        Feature.load(SmithingItems.class);
    }
}
