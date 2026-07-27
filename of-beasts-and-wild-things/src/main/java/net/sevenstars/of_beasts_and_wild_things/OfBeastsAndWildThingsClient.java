package net.sevenstars.of_beasts_and_wild_things;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.sevenstars.of_beasts_and_wild_things.block.BlocksWT;
import net.sevenstars.of_beasts_and_wild_things.client.DryFoliageColorResolver;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.client.FarmAnimalVariantModels;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.client.VariantChickenRenderer;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.client.VariantCowRenderer;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.client.VariantPigRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelsWT;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntityRenderer;

public final class OfBeastsAndWildThingsClient {
    private OfBeastsAndWildThingsClient() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(OfBeastsAndWildThingsClient::clientSetup);
        modEventBus.addListener(OfBeastsAndWildThingsClient::registerRenderers);
        modEventBus.addListener(OfBeastsAndWildThingsClient::registerLayerDefinitions);
        modEventBus.addListener(OfBeastsAndWildThingsClient::registerColorResolvers);
        modEventBus.addListener(OfBeastsAndWildThingsClient::registerBlockColors);
        modEventBus.addListener(OfBeastsAndWildThingsClient::registerReloadListeners);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(BlocksWT.BIRD_NEST, RenderType.cutout()));
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntitiesWT.SNAIL, SnailEntityRenderer::new);
        event.registerEntityRenderer(EntitiesWT.PHEASANT, PheasantEntityRenderer::new);
        event.registerEntityRenderer(EntitiesWT.SWAN, SwanEntityRenderer::new);
        event.registerEntityRenderer(EntitiesWT.DEER, DeerEntityRenderer::new);
        event.registerEntityRenderer(EntitiesWT.SWAN_EGG, ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityType.CHICKEN, VariantChickenRenderer::new);
        event.registerEntityRenderer(EntityType.COW, VariantCowRenderer::new);
        event.registerEntityRenderer(EntityType.PIG, VariantPigRenderer::new);
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EntityModelLayersWT.SNAIL, net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityModel::getTexturedModelData);
        event.registerLayerDefinition(EntityModelLayersWT.PHEASANT, net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityModel::getTexturedModelData);
        event.registerLayerDefinition(EntityModelLayersWT.SWAN, net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanAdultModel::getTexturedModelData);
        event.registerLayerDefinition(EntityModelLayersWT.SWAN_BABY, net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanBabyModel::getTexturedModelData);
        event.registerLayerDefinition(EntityModelLayersWT.DEER, net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntityModel::getTexturedModelData);
        event.registerLayerDefinition(FarmAnimalVariantModels.COLD_CHICKEN, FarmAnimalVariantModels::coldChickenLayer);
        event.registerLayerDefinition(FarmAnimalVariantModels.COLD_COW, FarmAnimalVariantModels::coldCowLayer);
    }

    private static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((BlockColor) (state, view, pos, tintIndex) -> {
            if (view == null || pos == null) {
                return DryFoliageColorResolver.FALLBACK_COLOR;
            }
            return view.getBlockTint(pos, DryFoliageColorResolver.COLOR_RESOLVER);
        }, BlocksWT.BIRD_NEST);
    }

    private static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(DryFoliageColorResolver.COLOR_RESOLVER);
    }

    private static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(DryFoliageColorResolver.RELOAD_LISTENER);
    }
}
