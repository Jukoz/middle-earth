package net.jukoz.me.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.forge.ForgeAlloyingScreenHandler;
import net.jukoz.me.gui.forge.ForgeHeatingScreenHandler;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.jukoz.me.gui.wood_pile.WoodPileScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static ScreenHandlerType<ArtisanTableScreenHandler> ARTISAN_SCREEN_HANDLER;
    public static ScreenHandlerType<ShapingAnvilScreenHandler> TREATED_ANVIL_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ShapingAnvilScreenHandler::new, BlockPos.PACKET_CODEC.cast());;
    public static ScreenHandlerType<WoodPileScreenHandler> WOOD_PILE_SCREEN_HANDLER;
    public static final ScreenHandlerType<ForgeAlloyingScreenHandler> FORGE_ALLOYING_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ForgeAlloyingScreenHandler::new, BlockPos.PACKET_CODEC.cast());
    public static final ScreenHandlerType<ForgeHeatingScreenHandler> FORGE_HEATING_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ForgeHeatingScreenHandler::new, BlockPos.PACKET_CODEC.cast());

    public static void registerAllScreenHandlers() {
        ARTISAN_SCREEN_HANDLER = new ScreenHandlerType<>(ArtisanTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        WOOD_PILE_SCREEN_HANDLER = new ScreenHandlerType<>(WoodPileScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MiddleEarth.MOD_ID, "forge_alloying"),
                FORGE_ALLOYING_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MiddleEarth.MOD_ID, "forge_heating"),
                FORGE_HEATING_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MiddleEarth.MOD_ID, "artisan_table"),
                ARTISAN_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MiddleEarth.MOD_ID, "treated_anvil"),
                TREATED_ANVIL_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MiddleEarth.MOD_ID, "wood_pile"),
                WOOD_PILE_SCREEN_HANDLER);
    }
}
