package net.jesteur.me.gui;

import net.jesteur.me.gui.alloy.AlloyScreenHandler;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<AlloyScreenHandler> ALLOY_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        ALLOY_SCREEN_HANDLER = new ScreenHandlerType<>(AlloyScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    }
}
