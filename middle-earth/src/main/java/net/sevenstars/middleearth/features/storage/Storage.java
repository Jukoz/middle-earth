package net.sevenstars.middleearth.features.storage;

import net.sevenstars.middleearth.common.feature.Feature;

public class Storage implements Feature {
    @Override
    public void register() {
        Feature.load(StorageBlocks.class, StorageBlockEntities.class);
        StorageScreenHandlers.register();
        Feature.load(StorageItems.class);
    }
}
