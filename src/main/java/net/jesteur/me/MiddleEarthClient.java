package net.jesteur.me;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.jesteur.me.block.ModDecorativeBlocks;
import net.jesteur.me.block.ModNatureBlocks;
import net.jesteur.me.datageneration.VariantsModelProvider;
import net.jesteur.me.entity.ModEntities;
import net.jesteur.me.entity.crab.CrabRenderer;
import net.jesteur.me.entity.dwarves.durin.DurinDwarfRenderer;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfRenderer;
import net.jesteur.me.entity.hobbits.shire.ShireHobbitRenderer;
import net.jesteur.me.entity.model.ModEntityModels;
import net.jesteur.me.entity.nazguls.NazgulRenderer;
import net.jesteur.me.entity.orcs.mordor.MordorOrcRenderer;
import net.jesteur.me.entity.spear.JavelinEntityRenderer;
import net.jesteur.me.entity.spider.MirkwoodSpiderRenderer;
import net.jesteur.me.entity.trolls.cave.CaveTrollRenderer;
import net.jesteur.me.entity.trolls.snow.SnowTrollRenderer;
import net.jesteur.me.item.ModEquipmentItems;
import net.jesteur.me.item.utils.ModModelPredicateProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MiddleEarthClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModEntityModels.getModels();

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

        ModModelPredicateProvider.registerBowModel();

        initializeRenderLayerMap();
    }

    private void initializeRenderLayerMap() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORDOR_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORDOR_LICHEN_FAN, RenderLayer.getCutout());
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WHITE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WHITE_MUSHROOM_TILLER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MOSS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.CORRUPTED_MOSS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.DWARVEN_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WALL_DWARVEN_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.SILVER_LANTERN, RenderLayer.getCutout());

    }

    private void registerDyeableItem(Item item) {
        ColorProviderRegistry.ITEM.register((stack, layer) -> layer == 0 ? ((DyeableItem)stack.getItem()).getColor(stack) : 0xFFFFFF, item);
    }
}
