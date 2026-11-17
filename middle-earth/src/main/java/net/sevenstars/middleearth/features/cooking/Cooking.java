package net.sevenstars.middleearth.features.cooking;

public class Cooking {
    public static void register() {
        CookingRecipes.register();
        CookingBlocks.register();
        CookingBlockEntities.register();
        CookingScreenHandlers.register();
        CookingItems.register();
    }
}
