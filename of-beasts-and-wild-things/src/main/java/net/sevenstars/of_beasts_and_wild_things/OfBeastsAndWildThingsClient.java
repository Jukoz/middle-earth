package net.sevenstars.of_beasts_and_wild_things;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.world.biome.DryFoliageColors;
import net.sevenstars.of_beasts_and_wild_things.block.ModBlocks;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.model.ModEntityModels;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntityRenderer;

public class OfBeastsAndWildThingsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModEntityModels.getModels();

        EntityRendererRegistry.register(ModEntities.SNAIL, SnailEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PHEASANT, PheasantEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SWAN, SwanEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEER, DeerEntityRenderer::new);

        BlockRenderLayerMap.putBlock(ModBlocks.BIRD_NEST, BlockRenderLayer.CUTOUT);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
                    if (view == null || pos == null) {
                        return DryFoliageColors.DEFAULT;
                    }
                    return BiomeColors.getDryFoliageColor(view, pos);
                }, ModBlocks.BIRD_NEST);
    }
}
