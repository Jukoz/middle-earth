package net.sevenstars.middleearth;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;
import net.sevenstars.middleearth.block.*;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancEntityRenderer;
import net.sevenstars.middleearth.block.special.forge.ForgeEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilEntityRenderer;
import net.sevenstars.middleearth.client.model.equipment.CustomBootsModel;
import net.sevenstars.middleearth.client.model.equipment.CustomChestplateModel;
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.CustomLeggingsModel;
import net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.armored.CapeMediumModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.rohan.RohanHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.armored.HoodModel;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.HeaterShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.KiteShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.RoundShieldEntityModel;
import net.sevenstars.middleearth.client.renderer.armor.*;
import net.sevenstars.middleearth.client.renderer.handheld.HeaterShieldModelRenderer;
import net.sevenstars.middleearth.client.renderer.handheld.HeldBannerModelRenderer;
import net.sevenstars.middleearth.client.renderer.handheld.KiteShieldModelRenderer;
import net.sevenstars.middleearth.client.renderer.handheld.RoundShieldModelRenderer;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.Crops;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.barrow_wights.BarrowWightEntityRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.petrified.PetrifiedTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.warg.WargRenderer;
import net.sevenstars.middleearth.entity.deer.DeerRenderer;
import net.sevenstars.middleearth.entity.model.ModEntityModels;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderer;
import net.sevenstars.middleearth.entity.projectile.boulder.BoulderEntityRenderer;
import net.sevenstars.middleearth.entity.projectile.smoke.SmokeRingProjectileRenderer;
import net.sevenstars.middleearth.entity.projectile.spear.SpearEntityRenderer;
import net.sevenstars.middleearth.entity.seat.SeatRenderer;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaRenderer;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderer;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobRenderer;
import net.sevenstars.middleearth.entity.swan.SwanRenderer;
import net.sevenstars.middleearth.event.KeyInputHandler;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.gui.forge.ForgeHeatingScreen;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreen;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreen;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreen;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreen;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.network.ModClientNetworkHandler;
import net.sevenstars.middleearth.network.connections.ConnectionToServer;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.particles.custom.AnvilBonkParticle;
import net.sevenstars.middleearth.particles.custom.LeavesParticle;
import net.sevenstars.middleearth.particles.custom.RingOfSmokeParticle;

public class MiddleEarthClient implements ClientModInitializer {
    
    public static final EntityModelLayer CUSTOM_ARMOR_HELMET = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "_1");
    public static final EntityModelLayer CUSTOM_ARMOR_CHESTPLATE = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "_2");
    public static final EntityModelLayer CUSTOM_ARMOR_LEGGINGS = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "_3");
    public static final EntityModelLayer CUSTOM_ARMOR_BOOTS = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "_4");
    public static final EntityModelLayer HELMET_ADDON_MODEL_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "helmet_addon");
    public static final EntityModelLayer BACK_ATTACHMENT_MODEL_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "back_attachment");
    public static final EntityModelLayer HELMET_ATTACHMENT_MODEL_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "helmet_attachment");

    public static final EntityModelLayer HEATER_SHIELD_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "heater_shield"), "main");
    public static final EntityModelLayer KITE_SHIELD_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "kite_shield"), "main");
    public static final EntityModelLayer ROUND_SHIELD_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "round_shield"), "main");

    public static final EntityModelLayer HELD_BANNER_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "held_banner"), "main");


    @Override
    public void onInitializeClient() {
        ModClientNetworkHandler.register(new ConnectionToServer());

        KeyInputHandler.register();

        ModEntityModels.getModels();
        EntityRendererRegistry.register(ModEntities.BARROW_WIGHT, BarrowWightEntityRenderer::new);
        // Entities

        EntityRendererRegistry.register(ModEntities.SNOW_TROLL, SnowTrollRenderer::new);
        EntityRendererRegistry.register(ModEntities.STONE_TROLL, StoneTrollRenderer::new);
        EntityRendererRegistry.register(ModEntities.PETRIFIED_TROLL, PetrifiedTrollRenderer::new);

        EntityRendererRegistry.register(ModEntities.BROADHOOF_GOAT, BroadhoofGoatRenderer::new);

        EntityRendererRegistry.register(ModEntities.WARG, WargRenderer::new);
        EntityRendererRegistry.register(ModEntities.SHELOBITE_LARVA, ShelobiteLarvaRenderer::new);
        EntityRendererRegistry.register(ModEntities.SHELOBITE_SCUTTLER, ShelobiteScuttlerRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPAWN_OF_SHELOB, SpawnOfShelobRenderer::new);
        //EntityRendererRegistry.register(ModEntities.BALROG, BalrogRenderer::new);

        EntityRendererRegistry.register(ModEntities.FIRE_OF_ORTHANC, FireOfOrthancEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PEBBLE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PINECONE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.LIT_PINECONE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPEAR, SpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOULDER, BoulderEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SMOKE_RING_PROJECTILE, SmokeRingProjectileRenderer::new);
        EntityRendererRegistry.register(ModEntities.WEB, FlyingItemEntityRenderer::new);

        // Animals
        EntityRendererRegistry.register(ModEntities.SWAN, SwanRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEER, DeerRenderer::new);

        EntityRendererRegistry.register(ModEntities.NPC, NpcEntityRenderer::new);


        EntityRendererRegistry.register(ModEntities.SEAT_ENTITY, SeatRenderer::new);

        HandledScreens.register(ModScreenHandlers.FORGE_ALLOYING_SCREEN_HANDLER, ForgeAlloyingScreen::new);
        HandledScreens.register(ModScreenHandlers.FORGE_HEATING_SCREEN_HANDLER, ForgeHeatingScreen::new);
        HandledScreens.register(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, ArtisanTableScreen::new);
        HandledScreens.register(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, ShapingAnvilScreen::new);
        HandledScreens.register(ModScreenHandlers.WOOD_PILE_SCREEN_HANDLER, WoodPileScreen::new);
        HandledScreens.register(ModScreenHandlers.STRUCTURE_MANAGER_SCREEN_HANDLER, StructureManagerScreen::new);
        HandledScreens.register(ModScreenHandlers.STRUCTURE_NEST_SCREEN_HANDLER, StructureNestScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.TREATED_ANVIL, ShapingAnvilEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.FORGE, ForgeEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.REINFORCED_CHEST, ReinforcedChestEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.BELLOWS, BellowsBlockEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_HELMET, CustomHelmetModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_CHESTPLATE, CustomChestplateModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_LEGGINGS, CustomLeggingsModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_BOOTS, CustomBootsModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HELMET_ADDON_MODEL_LAYER, RohanHelmetModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BACK_ATTACHMENT_MODEL_LAYER, CapeMediumModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HELMET_ATTACHMENT_MODEL_LAYER, HoodModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(HEATER_SHIELD_LAYER, HeaterShieldEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(KITE_SHIELD_LAYER, KiteShieldEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ROUND_SHIELD_LAYER, RoundShieldEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(HELD_BANNER_LAYER, HeldBannerEntityModel::getTexturedModelData);


        SpecialModelTypes.ID_MAPPER.put(Identifier.of(MiddleEarth.MOD_ID, "held_banner"), HeldBannerModelRenderer.Unbaked.CODEC);

        SpecialModelTypes.ID_MAPPER.put(Identifier.of(MiddleEarth.MOD_ID, "heater_shield"), HeaterShieldModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(MiddleEarth.MOD_ID, "kite_shield"), KiteShieldModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(MiddleEarth.MOD_ID, "round_shield"), RoundShieldModelRenderer.Unbaked.CODEC);

        for(ArmorModelsME.ModHelmetModels model : ArmorModelsME.ModHelmetModels.values()){
            ArmorRenderer.register(new HelmetArmorRenderer(model.getModel()), model.getItem());
        }

        for(ArmorModelsME.ModChestplateModels model : ArmorModelsME.ModChestplateModels.values()){
            ArmorRenderer.register(new ChestplateArmorRenderer(model.getModel()), model.getItem());
        }

        EquipmentItemsME.armorPiecesListHelmets.forEach(armor -> {
            ArmorRenderer.register(new HelmetArmorRenderer(), armor.asItem());
        });
        EquipmentItemsME.armorPiecesListChestplates.forEach(armor -> {
            ArmorRenderer.register(new ChestplateArmorRenderer(), armor.asItem());
        });
        EquipmentItemsME.armorPiecesListLeggings.forEach(armor -> {
            ArmorRenderer.register(new LeggingsArmorRenderer(), armor.asItem());
        });
        EquipmentItemsME.armorPiecesListBoots.forEach(armor -> {
            ArmorRenderer.register(new BootsArmorRenderer(), armor.asItem());
        });

        EquipmentItemsME.helmetAtttachments.forEach(hood -> {
            ArmorRenderer.register(new HelmetAttachmentRenderer(), hood);
        });
        EquipmentItemsME.backAttachments.forEach(cape -> {
            ArmorRenderer.register(new BackAttachmentRenderer(), cape);
        });

        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.MALLORN_LEAVES_PARTICLE, LeavesParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.MIRKWOOD_LEAVES_PARTICLE, LeavesParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.ANVIL_SPARK_PARTICLE, AnvilBonkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.RING_OF_SMOKE, RingOfSmokeParticle.Factory::new);

        initializeRenderLayerMap();
    }

    private void initializeRenderLayerMap() {
        BlockRenderLayerMap.putBlock(ModNatureBlocks.MORGUL_IVY, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.HANGING_COBWEB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.CORNER_COBWEB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.MIRKWOOD_SPIDER_EGG, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.BROWN_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.GREEN_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.SMALL_DRY_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.FROZEN_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.MALLOS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.ELANOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.TAN_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.STRAWBERRY_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.TOUGH_BERRY_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.YELLOW_FLOWER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.MIRKWOOD_ROOTS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.MIRKWOOD_HANGING_ROOTS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.PINE_BRANCHES, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.LIGHT_BLUE_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.MAGENTA_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.ORANGE_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.PINK_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.PURPLE_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.RED_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WHITE_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.YELLOW_FLOWERS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.LAVENDER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.YELLOW_TROLLIUS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.DRY_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.DYING_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.FROZEN_GRASS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.GRIM_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.TEMPERATE_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.GRASS_TUFT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.FROZEN_TUFT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.HEATHER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.RED_HEATHER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.DEAD_HEATHER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.DRY_HEATHER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.HEATH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WHEATGRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WILD_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WILDERGRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.BEACH_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.COASTAL_PANIC_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.SEDUM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.YELLOW_SEDUM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.BRACKEN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.DEAD_RUSHES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.FALSE_OATGRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.SHORT_CATTAILS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.SHORT_BULRUSH, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.SHRIVELED_SHRUB, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.SCORCHED_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.SCORCHED_TUFT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.SCORCHED_SHRUB, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.HOROKAKA, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.GIANT_HOROKAKA, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.SMALL_LILY_PADS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.LILY_PADS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.DUCKWEED, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.FLOATING_ICE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.GRASSY_DIRT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GRASSY_DIRT_SLAB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GRASSY_DIRT_STAIRS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.PEBBLED_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.PEBBLED_GRASS_SLAB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.PEBBLED_GRASS_STAIRS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.FIRE_OF_ORTHANC, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.TAPPER, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModBlocks.POINTED_LIMESTONE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.POINTED_GALONN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.POINTED_IZHERABAN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.POINTED_DOLOMITE, BlockRenderLayer.CUTOUT);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
            if (view == null || pos == null) {
                return GrassColors.getDefaultColor();
            }
            return BiomeColors.getGrassColor(view, pos);
        }, ModNatureBlocks.WILD_GRASS, ModNatureBlocks.GRASS_TUFT, ModNatureBlocks.WHEATGRASS,
                ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_SLAB, ModBlocks.GRASSY_DIRT_STAIRS,
                ModBlocks.PEBBLED_GRASS, ModBlocks.PEBBLED_GRASS_SLAB, ModBlocks.PEBBLED_GRASS_STAIRS,
                ModBlocks.TURF, ModBlocks.TURF_SLAB, ModBlocks.TURF_STAIRS, ModBlocks.TURF_VERTICAL_SLAB);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
                    if (view == null || pos == null) {
                        return FoliageColors.DEFAULT;
                    }
                    return BiomeColors.getFoliageColor(view, pos);
                }, ModNatureBlocks.FALLEN_LEAVES);

        for(Block block : SimpleDoubleBlockModel.doubleBlocks){
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        for(Block block : SimpleFlowerBedModel.flowerBeds){
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        for(SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots){
            BlockRenderLayerMap.putBlock(flowerPot.pottedPlant(), BlockRenderLayer.CUTOUT);
        }

        BlockRenderLayerMap.putBlock(ModNatureBlocks.MIRKWOOD_SAPLING, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WHITE_MUSHROOM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WHITE_MUSHROOM_TILLER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.MOSS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.STICKY_SNOW, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.STICKY_ICE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.FOREST_MOSS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.CORRUPTED_MOSS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.SHORT_ICICLES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.DROOPING_ICICLES, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.SPRUCE_STABLE_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.RUINED_DWARVEN_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, BlockRenderLayer.CUTOUT);

        for (Block block : Crops.crops){
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        for(Block block : TintableCrossModel.notTintedBlocks) {
            if(block != null) BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }
        for(Block block : TintableCrossModel.tintedBlocks) {
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        BlockRenderLayerMap.putBlock(WoodBlockSets.BEECH.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.LARCH.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.BLACK_LEBETHRON.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WHITE_LEBETHRON.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.CHESTNUT.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.FIR.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.HOLLY.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MALLORN.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MAPLE.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.SILVER_MAPLE.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MIRKWOOD.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.PALM.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WHITE_PALM.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.PINE.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.BLACK_PINE.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WILLOW.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.SCORCHED.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.MUSHROOM.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.DARK_MUSHROOM.ladder(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.GRAY_MUSHROOM.ladder(), BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.TREATED_WOOD_LADDER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ROPE_LADDER, BlockRenderLayer.CUTOUT);

        for(SimpleLadderModel.Ladder block : SimpleLadderModel.vanillaLadders) {
            BlockRenderLayerMap.putBlock(block.ladder(), BlockRenderLayer.CUTOUT);
        }

        BlockRenderLayerMap.putBlock(MushroomBlockSets.DARK_MUSHROOM.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.GRAY_MUSHROOM.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.MUSHROOM.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.SCORCHED.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WILLOW.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.PALM.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WHITE_PALM.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MIRKWOOD.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MALLORN.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WHITE_LEBETHRON.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.BLACK_LEBETHRON.trapdoor(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.BEECH.trapdoor(), BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(WoodBlockSets.BEECH.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.BLACK_LEBETHRON.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WHITE_LEBETHRON.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.CHESTNUT.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.LARCH.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MALLORN.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.PALM.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WHITE_PALM.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WILLOW.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.SCORCHED.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.GRAY_MUSHROOM.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.DARK_MUSHROOM.door(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.MUSHROOM.door(), BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(WoodBlockSets.BEECH.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.LARCH.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.BLACK_LEBETHRON.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WHITE_LEBETHRON.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.CHESTNUT.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.FIR.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.HOLLY.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MALLORN.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MAPLE.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.SILVER_MAPLE.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.MIRKWOOD.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.PALM.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WHITE_PALM.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.PINE.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.BLACK_PINE.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.WILLOW.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WoodBlockSets.SCORCHED.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.GRAY_MUSHROOM.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.DARK_MUSHROOM.chair(), BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(MushroomBlockSets.MUSHROOM.chair(), BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.TREATED_WOOD_CHAIR, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.FALLEN_LEAVES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.FALLEN_MALLORN_LEAVES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.QUARTZ_CLUSTER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.SMALL_QUARTZ_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.MEDIUM_QUARTZ_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.LARGE_QUARTZ_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.RED_AGATE_CLUSTER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.SMALL_RED_AGATE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.MEDIUM_RED_AGATE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.LARGE_RED_AGATE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.CITRINE_CLUSTER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.SMALL_CITRINE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.MEDIUM_CITRINE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.LARGE_CITRINE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GLOWSTONE_CLUSTER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.SMALL_GLOWSTONE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.MEDIUM_GLOWSTONE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.LARGE_GLOWSTONE_BUD, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.DWARVEN_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WALL_DWARVEN_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.CRYSTAL_LAMP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WALL_CRYSTAL_LAMP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.SILVER_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WALL_SILVER_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ELVEN_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WALL_ELVEN_LANTERN, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BRONZE_CHAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BRONZE_BROAD_CHAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.SPIKY_CHAIN, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.NET, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.EXPOSED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WEATHERED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.OXIDIZED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WAXED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WAXED_EXPOSED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WAXED_WEATHERED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WAXED_OXIDIZED_COPPER_BARS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.TREATED_STEEL_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.TREATED_STEEL_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.TREATED_STEEL_TRAPDOOR, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.SILVER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GILDED_BARS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.WILD_CARROT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WILD_POTATO, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WILD_BEETROOT, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.AZALEA_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.DRY_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.FROZEN_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.GREEN_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.IVY_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.LILAC_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.PINK_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.RED_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.WHITE_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.YELLOW_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.MEDGON_SPIKE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WOOD_FRAMED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WOOD_FRAMED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WATTLE_FRAMED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WATTLE_FRAMED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PLASTER_ROUND_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PLASTER_ROUND_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GONLUIN_CARVED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GONLUIN_CARVED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.TUFF_CARVED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.TUFF_CARVED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BLACKSTONE_CARVED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BLACKSTONE_CARVED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.IZHERABAN_CARVED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.IZHERABAN_CARVED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.MEDGON_CARVED_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.MEDGON_CARVED_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.RED_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS, BlockRenderLayer.TRANSLUCENT);
        
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.RED_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS_PANE, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(ResourceItemsME.REEDS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.TALL_CATTAILS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.TALL_BULRUSH, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WATERING_CAN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WOODEN_BUCKET, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BROWN_JUG, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.LARGE_JUG, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.AMPHORA, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BROWN_AMPHORA, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BROWN_JAR, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BROWN_FAT_POT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.FAT_POT, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.CANDLE_HEAP, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BIG_BRAZIER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.SMALL_BRAZIER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GILDED_BIG_BRAZIER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GILDED_SMALL_BRAZIER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.FIRE_BOWL, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.TORCH_OF_ORTHANC, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BONFIRE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.SCONCE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GILDED_SCONCE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ORCISH_SCONCE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WALL_SCONCE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GILDED_WALL_SCONCE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ORCISH_WALL_SCONCE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.GALONN_STATUE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.ARKENSTONE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.WALL_ARKENSTONE, BlockRenderLayer.TRANSLUCENT);


        SimpleWoodChairModel.vanillaChairs.forEach(block -> {
            BlockRenderLayerMap.putBlock(block.base(), BlockRenderLayer.CUTOUT);
        });

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.BELLOWS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.STRUCTURE_MANAGER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.STRUCTURE_NEST, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModNatureBlocks.MIRKWOOD_VINES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.MIRKWOOD_VINES_PLANT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.GLOWWORM_MAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModNatureBlocks.GLOWWORM_WEBBING, BlockRenderLayer.CUTOUT);
    }
}
