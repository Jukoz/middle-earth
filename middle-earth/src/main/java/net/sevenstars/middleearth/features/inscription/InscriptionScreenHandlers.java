package net.sevenstars.middleearth.features.inscription;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.inscription.gui.InscriptionTableScreenHandler;

public class InscriptionScreenHandlers {
    public static ScreenHandlerType<InscriptionTableScreenHandler> INSCRIPTION_SCREEN_HANDLER
            = new ScreenHandlerType<>(InscriptionTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void register() {
        Registry.register(Registries.SCREEN_HANDLER, MiddleEarth.id("inscription_table"), INSCRIPTION_SCREEN_HANDLER);
    }
}
