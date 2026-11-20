package net.sevenstars.middleearth.features.storage;

import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.features.storage.block.coffers.CofferEntityRenderer;
import net.sevenstars.middleearth.common.feature.ClientFeature;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.sevenstars.middleearth.features.storage.block.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.features.storage.gui.wood_pile.WoodPileScreen;

public class StorageClient implements ClientFeature {
    @Override
    public void registerClient() {
        HandledScreens.register(StorageScreenHandlers.WOOD_PILE_SCREEN_HANDLER, WoodPileScreen::new);
        BlockEntityRendererFactories.register(StorageBlockEntities.LARCH_COFFER, CofferEntityRenderer.of(EntityModelLayersME.LARCH_COFFER, "larch_coffer", false));
        BlockEntityRendererFactories.register(StorageBlockEntities.PINE_COFFER, CofferEntityRenderer.of(EntityModelLayersME.PINE_COFFER, "pine_coffer", false));
        BlockEntityRendererFactories.register(StorageBlockEntities.SPRUCE_COFFER, CofferEntityRenderer.of(EntityModelLayersME.SPRUCE_COFFER, "spruce_coffer", true));
        BlockEntityRendererFactories.register(StorageBlockEntities.FIR_COFFER, CofferEntityRenderer.of(EntityModelLayersME.FIR_COFFER, "fir_coffer", false));
        BlockEntityRendererFactories.register(StorageBlockEntities.BEECH_COFFER, CofferEntityRenderer.of(EntityModelLayersME.BEECH_COFFER, "beech_coffer", false));
        BlockEntityRendererFactories.register(StorageBlockEntities.CHESTNUT_COFFER, CofferEntityRenderer.of(EntityModelLayersME.CHESTNUT_COFFER, "chestnut_coffer", false));
        BlockEntityRendererFactories.register(StorageBlockEntities.OAK_COFFER, CofferEntityRenderer.of(EntityModelLayersME.OAK_COFFER, "oak_coffer", false));
        BlockEntityRendererFactories.register(StorageBlockEntities.WILLOW_COFFER, CofferEntityRenderer.of(EntityModelLayersME.WILLOW_COFFER, "willow_coffer", false));
        BlockEntityRendererFactories.register(StorageBlockEntities.REINFORCED_CHEST, ReinforcedChestEntityRenderer::new);
    }
}
