package net.jesteur.me;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.jesteur.me.block.ModNatureBlocks;
import net.jesteur.me.datageneration.VariantsModelProvider;
import net.jesteur.me.entity.ModEntities;
import net.jesteur.me.entity.barrow_wights.BarrowWightEntityRenderer;
import net.jesteur.me.entity.crab.CrabRenderer;
import net.jesteur.me.entity.dwarves.durin.DurinDwarfRenderer;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfRenderer;
import net.jesteur.me.entity.hobbits.HobbitRenderer;
import net.jesteur.me.entity.model.ModEntityModels;
import net.jesteur.me.entity.orcs.mordor.MordorOrcRenderer;
import net.jesteur.me.entity.spear.JavelinEntityRenderer;
import net.jesteur.me.entity.trolls.cave.CaveTrollRenderer;
import net.jesteur.me.entity.trolls.snow.SnowTrollRenderer;
import net.jesteur.me.item.ModEquipmentItems;
import net.jesteur.me.item.utils.ModModelPredicateProvider;
import net.jesteur.me.mixin.InGameHUDInvoker;
import net.jesteur.me.utils.HallucinationData;
import net.jesteur.me.utils.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class MiddleEarthClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {

            InGameHud ingamehud = MinecraftClient.getInstance().inGameHud;
            InGameHUDInvoker inGameHUDInvoker = (InGameHUDInvoker) ingamehud;
            int hallucination = HallucinationData.readHallucination((IEntityDataSaver) MinecraftClient.getInstance().player);
            inGameHUDInvoker.renderOverlayInvoker(drawContext, new Identifier(MiddleEarth.MOD_ID, "textures/entities/barrow_wights/overlay.png"), hallucination/100.0f);
            ingamehud.clear(); // this seems to be needed as its drawing the same ui on top of eachother... unsure how to solve otherwise. will clear
        });

        ModEntityModels.getModels();
        EntityRendererRegistry.register(ModEntities.BARROW_WIGHT, BarrowWightEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAVE_TROLL, CaveTrollRenderer::new);
        EntityRendererRegistry.register(ModEntities.DURIN_FOLK, DurinDwarfRenderer::new);
        EntityRendererRegistry.register(ModEntities.HOBBIT, HobbitRenderer::new);
        EntityRendererRegistry.register(ModEntities.GALADHRIM_ELF, GaladhrimElfRenderer::new);
        EntityRendererRegistry.register(ModEntities.MORDOR_ORC, MordorOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.SNOW_TROLL, SnowTrollRenderer::new);

        // Items
        ModelLoadingRegistry.INSTANCE.registerModelProvider(((manager, out) -> new VariantsModelProvider().provideExtraModels(manager, out)));
        EntityRendererRegistry.register(ModEntities.PEBBLE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPEAR, JavelinEntityRenderer::new);

        registerDyeableItem(ModEquipmentItems.TUNIC_CLOAK);
        registerDyeableItem(ModEquipmentItems.CLOAK);
        registerDyeableItem(ModEquipmentItems.CLOAK_HOOD);

        // Animals
        EntityRendererRegistry.register(ModEntities.CRAB, CrabRenderer::new);

        ModModelPredicateProvider.registerBowModel();

        initializeRenderLayerMap();
    }

    private void initializeRenderLayerMap() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORDOR_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORDOR_LICHEN_FAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.BROWN_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.GREEN_SHRUB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TAN_SHRUB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.STRAWBERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TOUGH_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.YELLOW_FLOWER, RenderLayer.getCutout());
    }

    private void registerDyeableItem(Item item) {
        ColorProviderRegistry.ITEM.register(new ItemColorProvider() {
            @Override
            public int getColor(ItemStack stack, int layer)
            {
                return layer == 0 ? ((DyeableItem)stack.getItem()).getColor(stack) : 0xFFFFFF;
            }
        }, item);
    }
}
