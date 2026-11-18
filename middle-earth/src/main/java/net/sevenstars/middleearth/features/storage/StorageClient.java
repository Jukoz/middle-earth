package net.sevenstars.middleearth.features.storage;

import net.sevenstars.middleearth.common.feature.ClientFeature;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.sevenstars.middleearth.features.storage.block.coffers.BeechCofferEntityRenderer;
import net.sevenstars.middleearth.features.storage.block.coffers.ChestnutCofferEntityRenderer;
import net.sevenstars.middleearth.features.storage.block.coffers.FirCofferEntityRenderer;
import net.sevenstars.middleearth.features.storage.block.coffers.LarchCofferEntityRenderer;
import net.sevenstars.middleearth.features.storage.block.coffers.OakCofferEntityRenderer;
import net.sevenstars.middleearth.features.storage.block.coffers.PineCofferEntityRenderer;
import net.sevenstars.middleearth.features.storage.block.coffers.SpruceCofferEntityRenderer;
import net.sevenstars.middleearth.features.storage.block.coffers.WillowCofferEntityRenderer;
import net.sevenstars.middleearth.features.storage.block.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.features.storage.gui.wood_pile.WoodPileScreen;

public class StorageClient implements ClientFeature {
    @Override
    public void registerClient() {
        HandledScreens.register(StorageScreenHandlers.WOOD_PILE_SCREEN_HANDLER, WoodPileScreen::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.LARCH_COFFER, LarchCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.PINE_COFFER, PineCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.SPRUCE_COFFER, SpruceCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.FIR_COFFER, FirCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.BEECH_COFFER, BeechCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.CHESTNUT_COFFER, ChestnutCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.OAK_COFFER, OakCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.WILLOW_COFFER, WillowCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.REINFORCED_CHEST, ReinforcedChestEntityRenderer::new);
    }
}
