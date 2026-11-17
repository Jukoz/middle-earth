package net.sevenstars.middleearth.features.cooking;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.cooking.block.crockpot.CrockpotScreenHandler;

public class CookingScreenHandlers {
    public static final ScreenHandlerType<CrockpotScreenHandler> CROCKPOT_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(CrockpotScreenHandler::new, BlockPos.PACKET_CODEC.cast());

    public static void register() {
        Registry.register(Registries.SCREEN_HANDLER, MiddleEarth.id("crockpot"), CROCKPOT_SCREEN_HANDLER);
    }
}
