package net.sevenstars.middleearth.features.artisan;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.artisan.gui.ArtisanTableScreenHandler;

public class ArtisanScreenHandlers {
    public static ScreenHandlerType<ArtisanTableScreenHandler> ARTISAN_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(ArtisanTableScreenHandler::new, PacketCodecs.STRING.cast());

    public static void register() {
        Registry.register(Registries.SCREEN_HANDLER, MiddleEarth.id("artisan_table"), ARTISAN_SCREEN_HANDLER);
    }
}
