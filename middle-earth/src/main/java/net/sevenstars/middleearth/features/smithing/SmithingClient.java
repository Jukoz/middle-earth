package net.sevenstars.middleearth.features.smithing;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.smithing.block.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.features.smithing.block.forge.ForgeEntityRenderer;
import net.sevenstars.middleearth.features.smithing.block.shapingAnvil.ShapingAnvilEntityRenderer;
import net.sevenstars.middleearth.features.smithing.client.AnvilBonkParticle;
import net.sevenstars.middleearth.features.smithing.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.features.smithing.gui.shapinganvil.ShapingAnvilScreen;
import net.sevenstars.middleearth.features.smithing.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.sevenstars.middleearth.features.smithing.item.HotComponentProperty;
import net.sevenstars.middleearth.particles.ParticleTypeRegistryME;

public class SmithingClient {
    public static void register() {
        BooleanProperties.ID_MAPPER.put(MiddleEarth.id("hot_component"), HotComponentProperty.CODEC);
        HandledScreens.register(SmithingScreenHandlers.FORGE_ALLOYING_SCREEN_HANDLER, ForgeAlloyingScreen::new);
        HandledScreens.register(SmithingScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, ShapingAnvilScreen::new);
        ShapingAnvilScreenHandler.clientInputChanged = ShapingAnvilScreen::clearOpenOutputs;
        BlockEntityRendererFactories.register(SmithingBlockEntities.STONE_ANVIL, ShapingAnvilEntityRenderer::new);
        BlockEntityRendererFactories.register(SmithingBlockEntities.TREATED_ANVIL, ShapingAnvilEntityRenderer::new);
        BlockEntityRendererFactories.register(SmithingBlockEntities.FORGE, ForgeEntityRenderer::new);
        BlockEntityRendererFactories.register(SmithingBlockEntities.BELLOWS, BellowsBlockEntityRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ParticleTypeRegistryME.ANVIL_SPARK_PARTICLE, AnvilBonkParticle.Factory::new);
        BlockRenderLayerMap.putBlock(SmithingBlocks.BELLOWS, BlockRenderLayer.CUTOUT);
    }
}
