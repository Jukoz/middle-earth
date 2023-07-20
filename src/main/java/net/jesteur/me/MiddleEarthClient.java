package net.jesteur.me;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.jesteur.me.block.ModNatureBlocks;
import net.jesteur.me.entity.ModEntities;
import net.jesteur.me.entity.crab.CrabRenderer;
import net.jesteur.me.entity.dwarves.durin.DurinDwarfRenderer;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfRenderer;
import net.jesteur.me.entity.hobbits.HobbitRenderer;
import net.jesteur.me.entity.model.ModEntityModels;
import net.jesteur.me.entity.orcs.mordor.MordorOrcRenderer;
import net.jesteur.me.entity.trolls.cave.CaveTrollRenderer;
import net.jesteur.me.entity.trolls.snow.SnowTrollRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class MiddleEarthClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModEntityModels.getModels();

        EntityRendererRegistry.register(ModEntities.CAVE_TROLL, CaveTrollRenderer::new);
        EntityRendererRegistry.register(ModEntities.DURIN_FOLK, DurinDwarfRenderer::new);
        EntityRendererRegistry.register(ModEntities.HOBBIT, HobbitRenderer::new);
        EntityRendererRegistry.register(ModEntities.GALADHRIM_ELF, GaladhrimElfRenderer::new);
        EntityRendererRegistry.register(ModEntities.MORDOR_ORC, MordorOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.SNOW_TROLL, SnowTrollRenderer::new);

        // Weapons
        EntityRendererRegistry.register(ModEntities.PEBBLE, FlyingItemEntityRenderer::new);

        // Animals
        EntityRendererRegistry.register(ModEntities.CRAB, CrabRenderer::new);

        initializeRenderLayerMap();
    }

    private void initializeRenderLayerMap() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORDOR_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORDOR_LICHEN_FAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.STRAWBERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TOUGH_BERRY_BUSH, RenderLayer.getCutout());
    }
}
