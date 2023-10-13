package net.jesteur.me.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.gui.alloyfurnace.AlloyFurnaceScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<AlloyFurnaceScreenHandler> ALLOY_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        ALLOY_SCREEN_HANDLER = new ScreenHandlerType<>(AlloyFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "alloy_furnace"),
                ALLOY_SCREEN_HANDLER);
    }
}
