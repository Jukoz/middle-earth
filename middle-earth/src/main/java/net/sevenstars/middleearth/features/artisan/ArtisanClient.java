package net.sevenstars.middleearth.features.artisan;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.sevenstars.middleearth.features.artisan.gui.ArtisanTableScreen;

public class ArtisanClient {
    public static void register() {
        HandledScreens.register(ArtisanScreenHandlers.ARTISAN_SCREEN_HANDLER, ArtisanTableScreen::new);
        BlockRenderLayerMap.putBlock(ArtisanBlocks.ORCISH_ARTISAN_TABLE, BlockRenderLayer.CUTOUT);
    }
}
