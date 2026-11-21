package net.sevenstars.middleearth.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenData;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenHandler;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenHandler;

public class ScreenHandlerRegistryME {
    public static final ScreenHandlerType<StructureMarkerScreenHandler> STRUCTURE_MARKER_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(StructureMarkerScreenHandler::new, StructureMarkerScreenData.PACKET_CODEC);

    public static final ScreenHandlerType<BedTagScreenHandler> BED_TAG_SCREEN_HANDLER
            = new ExtendedScreenHandlerType<>(BedTagScreenHandler::new, BedTagScreenData.PACKET_CODEC);

    public static void registerAllScreenHandlers() {
        register("structure_marker", STRUCTURE_MARKER_SCREEN_HANDLER);
        register("bed_tag", BED_TAG_SCREEN_HANDLER);
    }

    private static void register(String name, ScreenHandlerType handlerType) {
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MiddleEarth.MOD_ID, name), handlerType);
    }
}
