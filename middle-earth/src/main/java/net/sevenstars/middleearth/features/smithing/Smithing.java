package net.sevenstars.middleearth.features.smithing;

public class Smithing {
    public static void register() {
        SmithingRecipes.register();
        SmithingBlocks.register();
        SmithingBlockEntities.register();
        SmithingScreenHandlers.register();
        SmithingItems.register();
    }
}
