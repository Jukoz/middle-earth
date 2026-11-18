package net.sevenstars.middleearth.features.inscription;

import net.sevenstars.middleearth.common.feature.ClientFeature;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.sevenstars.middleearth.features.inscription.gui.InscriptionTableScreen;

public class InscriptionClient implements ClientFeature {
    @Override
    public void registerClient() {
        HandledScreens.register(InscriptionScreenHandlers.INSCRIPTION_SCREEN_HANDLER, InscriptionTableScreen::new);
        BlockRenderLayerMap.putBlock(InscriptionBlocks.INSCRIPTION_TABLE, BlockRenderLayer.CUTOUT);
    }
}
