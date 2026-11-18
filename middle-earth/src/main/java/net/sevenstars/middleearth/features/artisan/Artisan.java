package net.sevenstars.middleearth.features.artisan;

import net.sevenstars.middleearth.common.feature.Feature;

public class Artisan implements Feature {
    @Override
    public void register() {
        Feature.load(ArtisanBlocks.class);
        ArtisanScreenHandlers.register();
        Feature.load(ArtisanItems.class);
    }
}
