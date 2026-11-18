package net.sevenstars.middleearth.features.smithing;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.smithing.gui.forge.ForgeAlloyingScreenHandler;
import net.sevenstars.middleearth.features.smithing.gui.shapinganvil.ShapingAnvilScreenHandler;

public class SmithingScreenHandlers {
    public static ScreenHandlerType<ShapingAnvilScreenHandler> TREATED_ANVIL_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(ShapingAnvilScreenHandler::new, BlockPos.PACKET_CODEC.cast());

    public static final ScreenHandlerType<ForgeAlloyingScreenHandler> FORGE_ALLOYING_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(ForgeAlloyingScreenHandler::new, BlockPos.PACKET_CODEC.cast());

    public static void register() {
        Registry.register(Registries.SCREEN_HANDLER, MiddleEarth.id("forge_alloying"), FORGE_ALLOYING_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, MiddleEarth.id("treated_anvil"), TREATED_ANVIL_SCREEN_HANDLER);
    }
}
