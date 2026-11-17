package net.sevenstars.middleearth.features.storage;

public class Storage {
    public static void register() {
        StorageBlocks.register();
        StorageBlockEntities.register();
        StorageScreenHandlers.register();
        StorageItems.register();
    }
}
