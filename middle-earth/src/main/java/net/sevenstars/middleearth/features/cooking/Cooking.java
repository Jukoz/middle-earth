package net.sevenstars.middleearth.features.cooking;

import net.sevenstars.middleearth.common.feature.Feature;

public class Cooking implements Feature {
    @Override
    public void register() {
        CookingRecipes.register();
        Feature.load(CookingBlocks.class, CookingBlockEntities.class);
        CookingScreenHandlers.register();
        Feature.load(CookingItems.class);
    }
}
