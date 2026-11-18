package net.sevenstars.middleearth.features.storage;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.storage.gui.wood_pile.WoodPileScreenHandler;

public class StorageScreenHandlers {
    public static ScreenHandlerType<WoodPileScreenHandler> WOOD_PILE_SCREEN_HANDLER
            = new ScreenHandlerType<>(WoodPileScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void register() {
        Registry.register(Registries.SCREEN_HANDLER, MiddleEarth.id("wood_pile"), WOOD_PILE_SCREEN_HANDLER);
    }
}
