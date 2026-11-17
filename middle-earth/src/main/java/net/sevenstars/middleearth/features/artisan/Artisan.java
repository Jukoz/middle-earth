package net.sevenstars.middleearth.features.artisan;

public class Artisan {
    public static void register() {
        ArtisanBlocks.register();
        ArtisanScreenHandlers.register();
        ArtisanItems.register();
    }
}
