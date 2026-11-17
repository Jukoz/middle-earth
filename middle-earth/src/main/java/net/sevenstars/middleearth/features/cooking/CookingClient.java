package net.sevenstars.middleearth.features.cooking;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.sevenstars.middleearth.features.cooking.block.plate.PlateEntityRenderer;

public class CookingClient {
    public static void register() {
        BlockEntityRendererFactories.register(CookingBlockEntities.PLATE, PlateEntityRenderer::new);
        BlockRenderLayerMap.putBlock(CookingBlocks.CERAMIC_PLATE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(CookingBlocks.ROTTEN_PLATE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(CookingBlocks.SILVER_PLATE, BlockRenderLayer.CUTOUT);
    }
}
