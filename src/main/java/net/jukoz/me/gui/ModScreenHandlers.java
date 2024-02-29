package net.jukoz.me.gui;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.alloyfurnace.AlloyFurnaceScreenHandler;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.gui.wood_pile.WoodPileScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<AlloyFurnaceScreenHandler> ALLOY_SCREEN_HANDLER;
    public static ScreenHandlerType<ArtisanTableScreenHandler> ARTISAN_SCREEN_HANDLER;
    public static ScreenHandlerType<WoodPileScreenHandler> WOOD_PILE_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        ALLOY_SCREEN_HANDLER = new ScreenHandlerType<>(AlloyFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        ARTISAN_SCREEN_HANDLER = new ScreenHandlerType<>(ArtisanTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        WOOD_PILE_SCREEN_HANDLER = new ScreenHandlerType<>(WoodPileScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "alloy_furnace"),
                ALLOY_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "artisan_table"),
                ARTISAN_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "wood_pile"),
                WOOD_PILE_SCREEN_HANDLER);
    }
}
