package net.sevenstars.middleearth.features.inscription;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.sevenstars.middleearth.features.inscription.gui.InscriptionTableScreen;

public class InscriptionClient {
    public static void register() {
        HandledScreens.register(InscriptionScreenHandlers.INSCRIPTION_SCREEN_HANDLER, InscriptionTableScreen::new);
        BlockRenderLayerMap.putBlock(InscriptionBlocks.INSCRIPTION_TABLE, BlockRenderLayer.CUTOUT);
    }
}
