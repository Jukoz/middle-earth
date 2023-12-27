package net.jukoz.me;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.jukoz.me.block.*;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnaceEntityRenderer;
import net.jukoz.me.datageneration.VariantsModelProvider;
import net.jukoz.me.datageneration.content.models.SimpleDoubleBlockModel;
import net.jukoz.me.datageneration.content.models.SimpleFlowerBedModel;
import net.jukoz.me.datageneration.content.models.TintableCrossModel;
import net.jukoz.me.datageneration.content.tags.Crops;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.barrow_wights.BarrowWightEntityRenderer;
import net.jukoz.me.entity.crab.CrabRenderer;
import net.jukoz.me.entity.dwarves.durin.DurinDwarfRenderer;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfRenderer;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitRenderer;
import net.jukoz.me.entity.model.ModEntityModels;
import net.jukoz.me.entity.nazguls.NazgulRenderer;
import net.jukoz.me.entity.orcs.mordor.MordorOrcRenderer;
import net.jukoz.me.entity.snail.SnailRenderer;
import net.jukoz.me.entity.spear.JavelinEntityRenderer;
import net.jukoz.me.entity.spider.MirkwoodSpiderRenderer;
import net.jukoz.me.entity.trolls.cave.CaveTrollRenderer;
import net.jukoz.me.entity.trolls.snow.SnowTrollRenderer;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.gui.alloyfurnace.AlloyFurnaceScreen;
import net.jukoz.me.gui.wood_pile.WoodPileScreen;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModRessourceItems;
import net.jukoz.me.item.utils.ModModelPredicateProvider;
import net.jukoz.me.network.ModNetworks;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;

public class MiddleEarthClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModEntityModels.getModels();
        EntityRendererRegistry.register(ModEntities.BARROW_WIGHT, BarrowWightEntityRenderer::new);
        // Entities
        EntityRendererRegistry.register(ModEntities.CAVE_TROLL, CaveTrollRenderer::new);
        EntityRendererRegistry.register(ModEntities.DURIN_FOLK, DurinDwarfRenderer::new);
        EntityRendererRegistry.register(ModEntities.HOBBIT, ShireHobbitRenderer::new);
        EntityRendererRegistry.register(ModEntities.GALADHRIM_ELF, GaladhrimElfRenderer::new);
        EntityRendererRegistry.register(ModEntities.NAZGUL, NazgulRenderer::new);
        EntityRendererRegistry.register(ModEntities.MORDOR_ORC, MordorOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.MIRKWOOD_SPIDER, MirkwoodSpiderRenderer::new);
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
        EntityRendererRegistry.register(ModEntities.SNAIL, SnailRenderer::new);

        ModModelPredicateProvider.registerBowModel();

        HandledScreens.register(ModScreenHandlers.ALLOY_SCREEN_HANDLER, AlloyFurnaceScreen::new);
        HandledScreens.register(ModScreenHandlers.WOOD_PILE_SCREEN_HANDLER, WoodPileScreen::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ALLOY_FURNACE, AlloyFurnaceEntityRenderer::new);

        ModNetworks.registerS2CPackets();

        initializeRenderLayerMap();
    }

    private void initializeRenderLayerMap() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORDOR_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORGUL_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.HANGING_COBWEB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.CORNER_COBWEB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MIRKWOOD_SPIDER_EGG, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.BROWN_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.GREEN_SHRUB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TAN_SHRUB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.STRAWBERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TOUGH_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.YELLOW_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MIRKWOOD_ROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MIRKWOOD_HANGING_ROOTS, RenderLayer.getCutout());


        for(Block block : TintableCrossModel.notTintedBlocks){
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        for(Block block : SimpleDoubleBlockModel.doubleBlocks){
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        for(Block block : SimpleFlowerBedModel.flowerBeds){
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MOSS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FOREST_MOSS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.CORRUPTED_MOSS, RenderLayer.getCutout());

        for (Block block : Crops.crops){
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.DARK_MUSHROOM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.GRAY_MUSHROOM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.MUSHROOM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WILLOW.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.PALM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MIRKWOOD.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MALLORN.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_LEBETHRON.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BLACK_LEBETHRON.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BEECH.trapdoor(), RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BEECH.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MALLORN.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.PALM.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WILLOW.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.GRAY_MUSHROOM.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.DARK_MUSHROOM.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.MUSHROOM.door(), RenderLayer.getCutout());
        
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SAPPHIRE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_SAPPHIRE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_SAPPHIRE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_SAPPHIRE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_AGATE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_RED_AGATE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_RED_AGATE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_RED_AGATE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CITRINE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_CITRINE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_CITRINE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_CITRINE_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.DWARVEN_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WALL_DWARVEN_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.SILVER_LANTERN, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILVERS_BARS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WILD_CARROT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WILD_POTATO, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WILD_BEETROOT, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROWN_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CYAN_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRAY_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GREEN_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIME_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAGENTA_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.YELLOW_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROWN_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CYAN_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRAY_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GREEN_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIME_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAGENTA_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.YELLOW_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModRessourceItems.REEDS, RenderLayer.getCutout());
    }

    private void registerDyeableItem(Item item) {
        ColorProviderRegistry.ITEM.register((stack, layer) -> layer == 0 ? ((DyeableItem)stack.getItem()).getColor(stack) : 0xFFFFFF, item);
    }
}
