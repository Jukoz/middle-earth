package net.sevenstars.middleearth.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.cooking.block.crockpot.CrockpotScreenHandler;
import net.sevenstars.middleearth.features.artisan.gui.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.gui.cavetroll.CaveTrollScreenHandler;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreenHandler;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.features.storage.gui.wood_pile.WoodPileScreenHandler;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.features.inscription.gui.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;

public class ScreenHandlerRegistryME {
    


    public static final ScreenHandlerType<StructureManagerScreenHandler> STRUCTURE_MANAGER_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(StructureManagerScreenHandler::new, StructureManagerScreenData.PACKET_CODEC.cast());

    public static final ScreenHandlerType<StructureNestScreenHandler> STRUCTURE_NEST_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(StructureNestScreenHandler::new, StructureNestScreenData.PACKET_CODEC.cast());


    public static final ScreenHandlerType<CaveTrollScreenHandler> CAVE_TROLL_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(CaveTrollScreenHandler::new, PacketCodecs.VAR_INT.cast());

    public static void registerAllScreenHandlers() {
        register("structure_manager", STRUCTURE_MANAGER_SCREEN_HANDLER);
        register("structure_nest", STRUCTURE_NEST_SCREEN_HANDLER);
        register("crockpot",CROCKPOT_SCREEN_HANDLER );
        register("cave_troll", CAVE_TROLL_SCREEN_HANDLER);
    }

    private static void register(String name, ScreenHandlerType handlerType) {
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MiddleEarth.MOD_ID, name), handlerType);
    }
}
