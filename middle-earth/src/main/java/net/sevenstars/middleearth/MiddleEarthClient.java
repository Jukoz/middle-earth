package net.sevenstars.middleearth;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.coffers.*;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancEntityRenderer;
import net.sevenstars.middleearth.block.special.forge.ForgeEntityRenderer;
import net.sevenstars.middleearth.block.special.plate.PlateEntityRenderer;
import net.sevenstars.middleearth.block.special.pots.LootablePotBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilEntityRenderer;
import net.sevenstars.middleearth.block.special.skull.OldSkullBlockEntityRenderer;
import net.sevenstars.middleearth.client.BlockColorsME;
import net.sevenstars.middleearth.client.model.equipment.CustomBootsModel;
import net.sevenstars.middleearth.client.model.equipment.CustomChestplateModel;
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.CustomLeggingsModel;
import net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.armored.CapeMediumModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.armored.HoodModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.ErynGalenWatchwardenHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.SilvanLordHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.WoodlandRealmCrownModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.rohan.RohanHelmetModel;
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
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.EntityModelsME;
import net.sevenstars.middleearth.entity.barrel.BarrelEntityRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.petrified.PetrifiedTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.warg.WargRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderer;
import net.sevenstars.middleearth.entity.projectile.boulder.BoulderEntityRenderer;
import net.sevenstars.middleearth.entity.projectile.smoke.SmokeRingProjectileRenderer;
import net.sevenstars.middleearth.entity.projectile.spear.SpearEntityRenderer;
import net.sevenstars.middleearth.entity.seat.SeatRenderer;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaRenderer;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderer;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobRenderer;
import net.sevenstars.middleearth.event.KeyInputHandler;
import net.sevenstars.middleearth.gui.ScreenHandlerRegistryME;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreen;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreen;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreen;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreen;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreen;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.items.weapons.HotComponentProperty;
import net.sevenstars.middleearth.item.items.weapons.SneakAttackProperty;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.network.ClientNetworkHandlerME;
import net.sevenstars.api.network.connections.ConnectionToServer;
import net.sevenstars.middleearth.particles.ParticleTypeRegistryME;
import net.sevenstars.middleearth.particles.custom.AnvilBonkParticle;
import net.sevenstars.middleearth.particles.custom.BiomeFogParticle;

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
        ClientNetworkHandlerME.register(new ConnectionToServer());

        KeyInputHandler.register();

        EntityModelsME.getModels();
        BooleanProperties.ID_MAPPER.put(MiddleEarth.id("sneak_attack"), SneakAttackProperty.CODEC);
        BooleanProperties.ID_MAPPER.put(MiddleEarth.id("hot_component"), HotComponentProperty.CODEC);

        // Entities

        EntityRendererRegistry.register(EntitiesME.SNOW_TROLL, SnowTrollRenderer::new);
        EntityRendererRegistry.register(EntitiesME.CAVE_TROLL, CaveTrollRenderer::new);
        EntityRendererRegistry.register(EntitiesME.STONE_TROLL, StoneTrollRenderer::new);
        EntityRendererRegistry.register(EntitiesME.PETRIFIED_TROLL, PetrifiedTrollRenderer::new);

        EntityRendererRegistry.register(EntitiesME.BROADHOOF_GOAT, BroadhoofGoatRenderer::new);
        EntityRendererRegistry.register(EntitiesME.GREAT_HORN, GreatHornRenderer::new);
        EntityRendererRegistry.register(EntitiesME.WARG, WargRenderer::new);

        EntityRendererRegistry.register(EntitiesME.REINFORCED_BARREL, BarrelEntityRenderer::new);

        EntityRendererRegistry.register(EntitiesME.SHELOBITE_LARVA, ShelobiteLarvaRenderer::new);
        EntityRendererRegistry.register(EntitiesME.SHELOBITE_SCUTTLER, ShelobiteScuttlerRenderer::new);
        EntityRendererRegistry.register(EntitiesME.SPAWN_OF_SHELOB, SpawnOfShelobRenderer::new);
        //EntityRendererRegistry.register(EntitiesME.BALROG, BalrogRenderer::new);


        EntityRendererRegistry.register(EntitiesME.FIRE_OF_ORTHANC, FireOfOrthancEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.PEBBLE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.PINECONE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.LIT_PINECONE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.SPEAR, SpearEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.BOULDER, BoulderEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.SMOKE_RING_PROJECTILE, SmokeRingProjectileRenderer::new);
        EntityRendererRegistry.register(EntitiesME.WEB, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(EntitiesME.NPC, NpcEntityRenderer::new);


        EntityRendererRegistry.register(EntitiesME.SEAT_ENTITY, SeatRenderer::new);
        //HandledScreens.register(ModScreenHandlers.CROCKPOT_SCREEN_HANDLER, CrockpotScreen::new);
        HandledScreens.register(ScreenHandlerRegistryME.FORGE_ALLOYING_SCREEN_HANDLER, ForgeAlloyingScreen::new);
        HandledScreens.register(ScreenHandlerRegistryME.ARTISAN_SCREEN_HANDLER, ArtisanTableScreen::new);
        HandledScreens.register(ScreenHandlerRegistryME.INSCRIPTION_SCREEN_HANDLER, InscriptionTableScreen::new);
        HandledScreens.register(ScreenHandlerRegistryME.TREATED_ANVIL_SCREEN_HANDLER, ShapingAnvilScreen::new);
        HandledScreens.register(ScreenHandlerRegistryME.WOOD_PILE_SCREEN_HANDLER, WoodPileScreen::new);
        HandledScreens.register(ScreenHandlerRegistryME.STRUCTURE_MANAGER_SCREEN_HANDLER, StructureManagerScreen::new);
        HandledScreens.register(ScreenHandlerRegistryME.STRUCTURE_NEST_SCREEN_HANDLER, StructureNestScreen::new);

        BlockEntityRendererFactories.register(BlockEntityRegistryME.STONE_ANVIL, ShapingAnvilEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.TREATED_ANVIL, ShapingAnvilEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.FORGE, ForgeEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.LARCH_COFFER, LarchCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.PINE_COFFER, PineCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.SPRUCE_COFFER, SpruceCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.FIR_COFFER, FirCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.BEECH_COFFER, BeechCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.CHESTNUT_COFFER, ChestnutCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.OAK_COFFER, OakCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.WILLOW_COFFER, WillowCofferEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.REINFORCED_CHEST, ReinforcedChestEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.OLD_SKULL, OldSkullBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.BELLOWS, BellowsBlockEntityRenderer::new);
        //BlockEntityRendererFactories.register(ModBlockEntities.CROCKPOT, CrockpotEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistryME.PLATE, PlateEntityRenderer::new);
        BlockEntityRendererFactories.register(net.minecraft.block.entity.BlockEntityType.DECORATED_POT, LootablePotBlockEntityRenderer::new);

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

        for(ArmorModelsME.CustomHelmetModels model : ArmorModelsME.CustomHelmetModels.values()){
            ArmorRenderer.register(new HelmetArmorRenderer(model.getModel()), model.getItem());
        }
        ArmorRenderer.register(new HelmetVariantsRenderer(new SilvanLordHelmetModel(SilvanLordHelmetModel.getTexturedModelData().createModel())), EquipmentItemsME.SILVAN_LORD_HELMET);
        ArmorRenderer.register(new HelmetVariantsRenderer(new ErynGalenWatchwardenHelmetModel(ErynGalenWatchwardenHelmetModel.getTexturedModelData().createModel())), EquipmentItemsME.ERYN_GALEN_WATCHWARDEN_HELMET);
        ArmorRenderer.register(new HelmetVariantsRenderer(new ErynGalenWatchwardenHelmetModel(ErynGalenWatchwardenHelmetModel.getTexturedModelData().createModel())), EquipmentItemsME.OXIDISED_ERYN_GALEN_WATCHWARDEN_HELMET);
        ArmorRenderer.register(new WoodlandCrownRenderer(new WoodlandRealmCrownModel(WoodlandRealmCrownModel.getTexturedModelData().createModel())), EquipmentItemsME.WOODLAND_REALM_CROWN);

        for(ArmorModelsME.CustomChestplateModels model : ArmorModelsME.CustomChestplateModels.values()){
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

        EquipmentItemsME.helmetAttachments.forEach(hood -> {
            ArmorRenderer.register(new HelmetAttachmentRenderer(), hood);
        });
        EquipmentItemsME.backAttachments.forEach(cape -> {
            ArmorRenderer.register(new BackAttachmentRenderer(), cape);
        });

        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.addModel(ExtraModelKey.create(() -> "plate_apple"), SimpleUnbakedExtraModel.blockStateModel(MiddleEarth.idFilePath("item", "plate_apple")));
        });

        ParticleFactoryRegistry.getInstance().register(ParticleTypeRegistryME.ANVIL_SPARK_PARTICLE, AnvilBonkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleTypeRegistryME.BIOME_FOG_PARTICLE, BiomeFogParticle.Factory::new);

        initializeRenderLayerMap();
        BlockColorsME.initializeBlockColors();
    }

    private void initializeRenderLayerMap() {
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MORGUL_IVY, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.HANGING_WEBS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.CORNER_COBWEB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHELOBITE_LARVA_EGG, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.HANGING_SHELOBITE_LARVA_EGG, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.ATHELAS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.BROWN_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.GREEN_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SMALL_DRY_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FROZEN_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.ELANOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MALLOS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.NIPHREDIL, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SIMBELMYNE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.TAN_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.STRAWBERRY_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.TOUGH_BERRY_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.YELLOW_FLOWER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.BLUE_GENTIAN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.GREEN_JEWEL_CORNFLOWER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.NOBLEWHITE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MIRKWOOD_ROOTS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MIRKWOOD_HANGING_ROOTS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.PINE_BRANCHES, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LIGHT_BLUE_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MAGENTA_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.ORANGE_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.PINK_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.PURPLE_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.RED_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WHITE_FLOWERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.YELLOW_FLOWERS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.BLUE_LAVENDER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LAVENDER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WHITE_LAVENDER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.YELLOW_TROLLIUS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.HOBBIT_SUNFLOWERS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.BLUE_FESCUE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DYING_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FROZEN_GRASS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.GRIM_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MEADOWGRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SPARSE_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.NETTLES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.THISTLE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.TEMPERATE_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.GRASS_TUFT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FROZEN_TUFT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.HEATHER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.RED_HEATHER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DEAD_HEATHER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DRY_HEATHER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.HEATH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WHEATGRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WILD_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WILDERGRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.BEACH_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.COASTAL_PANIC_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MISTWEED, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SEDUM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.ORANGE_SEDUM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.RED_SEDUM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.YELLOW_SEDUM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.GIANT_BUTTERBUR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.BRACKEN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.CAMPION, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.BLUE_BIGLEAF_HYDRANGEA, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.PINK_BIGLEAF_HYDRANGEA, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WHITE_BIGLEAF_HYDRANGEA, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DEAD_HEATHER_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DRY_HEATHER_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DEAD_RUSHES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FALSE_OATGRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.HEATHER_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LARGE_BLUE_FESCUE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LARGE_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LARGE_SHRIVELED_SHRUB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.RED_HEATHER_BUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.RUSHES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.BRAMBLES_OF_MORDOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHORT_DEAD_RUSHES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.CLOVERS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHORT_RUSHES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHORT_REEDS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHORT_CATTAILS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHORT_BULRUSH, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHRIVELED_SHRUB, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SCORCHED_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SCORCHED_TUFT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SCORCHED_SHRUB, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SMALL_LILY_PADS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SMALL_FLOWERING_LILY_PADS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LILY_PADS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FLOWERING_LILY_PADS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LARGE_LILY_PAD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LARGE_FLOWERING_LILY_PAD, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DUCKWEED, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FLOATING_ICE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_DIRT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_DIRT_SLAB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_DIRT_STAIRS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.CHALKSOIL_GRASS_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.LOAM_GRASS_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.PEAT_GRASS_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.SILT_GRASS_BLOCK, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_CHALKSOIL, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_CHALKSOIL_SLAB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_CHALKSOIL_STAIRS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_LOAM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_LOAM_SLAB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_LOAM_STAIRS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_PEAT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_PEAT_SLAB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_PEAT_STAIRS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_SILT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_SILT_SLAB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GRASSY_SILT_STAIRS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.PEBBLED_GRASS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.PEBBLED_GRASS_SLAB, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.PEBBLED_GRASS_STAIRS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.FIRE_OF_ORTHANC, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.INSCRIPTION_TABLE, BlockRenderLayer.CUTOUT);

        /*BlockRenderLayerMap.putBlock(ModDecorativeBlocks.CERAMIC_CROCKPOT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModDecorativeBlocks.CROCKPOT, BlockRenderLayer.CUTOUT);*/
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CERAMIC_PLATE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ROTTEN_PLATE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SILVER_PLATE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.TAPPER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.POINTED_LIMESTONE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.POINTED_GALONN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.POINTED_IZHERABAN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.POINTED_DOLOMITE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.EMBERS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.REINFORCED_SCAFFOLDING, BlockRenderLayer.CUTOUT);

        for(Block block : SimpleDoubleBlockModel.doubleBlocks){
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        for(Block block : SimpleFlowerBedModel.flowerBeds){
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        for(SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots){
            BlockRenderLayerMap.putBlock(flowerPot.pottedPlant(), BlockRenderLayer.CUTOUT);
        }

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MIRKWOOD_SAPLING, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WHITE_MUSHROOM, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WHITE_MUSHROOM_TILLER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MOSS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.STICKY_SNOW, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.STICKY_ICE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FOREST_MOSS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.CORRUPTED_MOSS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHORT_ICICLES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DROOPING_ICICLES, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.BURZUM_SPIKES, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.TALL_BLACK_PINE_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.REINFORCED_BLACK_PINE_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.RICKETY_SIMPLE_LARCH_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SPRUCE_STABLE_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.LARGE_BEECH_FENCE_GATE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.RUINED_DWARVEN_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.LIGHT_BLUE_HOBBIT_DOOR, BlockRenderLayer.CUTOUT);

        for (Block block : Crops.crops){
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        for(Block block : TintableCrossModel.notTintedBlocks) {
            if(block != null) BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        for(Block block : TintableCrossModel.tintedBlocks) {
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.TREATED_WOOD_LADDER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ROPE_LADDER, BlockRenderLayer.CUTOUT);

        for(SimpleLadderModel.Ladder block : SimpleLadderModel.vanillaLadders) {
            BlockRenderLayerMap.putBlock(block.ladder(), BlockRenderLayer.CUTOUT);
        }

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.TREATED_WOOD_CHAIR, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FALLEN_LEAVES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FALLEN_MALLORN_LEAVES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FALLEN_MIRKWOOD_LEAVES, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.QUARTZ_CLUSTER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.SMALL_QUARTZ_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.MEDIUM_QUARTZ_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.LARGE_QUARTZ_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.RED_AGATE_CLUSTER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.SMALL_RED_AGATE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.MEDIUM_RED_AGATE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.LARGE_RED_AGATE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.CITRINE_CLUSTER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.SMALL_CITRINE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.MEDIUM_CITRINE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.LARGE_CITRINE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GLOWSTONE_CLUSTER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.SMALL_GLOWSTONE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.MEDIUM_GLOWSTONE_BUD, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.LARGE_GLOWSTONE_BUD, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_BLACK_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_BLUE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_BROWN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_BURNT_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_DARK_BLUE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_DARK_BROWN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_DARK_GREEN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_DARK_RED_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_FANCY_BLUE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_FANCY_GREEN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_FANCY_RED_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_GRAY_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_GREEN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_PURPLE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_RED_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_ROTTEN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_WHITE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_YELLOW_CURTAIN, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BLACK_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BLUE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BROWN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BURNT_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.DARK_BLUE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.DARK_BROWN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.DARK_GREEN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.DARK_RED_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.FANCY_BLUE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.FANCY_GREEN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.FANCY_RED_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.GRAY_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.GREEN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.PURPLE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.RED_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ROTTEN_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WHITE_CURTAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.YELLOW_CURTAIN, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.DWARVEN_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_DWARVEN_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.TREATED_STEEL_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_TREATED_STEEL_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CRYSTAL_LAMP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_CRYSTAL_LAMP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SILVER_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_SILVER_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ELVEN_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_ELVEN_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CRUDE_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_CRUDE_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.LEAD_LANTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_LEAD_LANTERN, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BRONZE_CHAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BRONZE_BROAD_CHAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CRUDE_CHAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CRUDE_BROAD_CHAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SPIKY_CHAIN, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.NET, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.EXPOSED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.WEATHERED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.OXIDIZED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.WAXED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.WAXED_EXPOSED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.WAXED_WEATHERED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.WAXED_OXIDIZED_COPPER_BARS, BlockRenderLayer.CUTOUT);
        
        BlockRenderLayerMap.putBlock(BlockRegistryME.BRONZE_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.CRUDE_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.TREATED_STEEL_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.TREATED_STEEL_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.BURZUM_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.AGED_WOOD_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.BRONZE_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.CRUDE_DOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.AGED_WOOD_TRAPDOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.BRONZE_TRAPDOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.CRUDE_TRAPDOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.TREATED_STEEL_TRAPDOOR, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.SILVER_BARS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(BlockRegistryME.GILDED_BARS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WILD_CARROT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WILD_POTATO, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WILD_BEETROOT, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.AZALEA_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.DRY_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.FROZEN_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.GREEN_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.IVY_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.LILAC_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.PINK_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.RED_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.THORNY_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WHITE_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.YELLOW_FLOWER_GROWTH, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WEBBING, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.MEDGON_SPIKE, BlockRenderLayer.CUTOUT);
        
        for (SimplePaneModel.Pane pane : SimplePaneModel.panes){
            BlockRenderLayerMap.putBlock(pane.glass(), BlockRenderLayer.TRANSLUCENT);
            BlockRenderLayerMap.putBlock(pane.pane(), BlockRenderLayer.TRANSLUCENT);
        }

        StoneBlockSetRegistryME.stoneSetsList.forEach(setBuilder -> {
            if (setBuilder.carvedWindows != null){
                BlockRenderLayerMap.putBlock(setBuilder.carvedWindows.block(), BlockRenderLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(setBuilder.carvedWindows.verticalSlab(), BlockRenderLayer.CUTOUT);
            }
        });

        WoodBlockSetRegistryME.woodSetsList.forEach(setBuilder -> {
            if (setBuilder.redstoneBlocks != null){
                BlockRenderLayerMap.putBlock(setBuilder.redstoneBlocks.trapdoor(), BlockRenderLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(setBuilder.redstoneBlocks.door(), BlockRenderLayer.CUTOUT);
            }

            if (setBuilder.furnitureBlocks != null){
                BlockRenderLayerMap.putBlock(setBuilder.furnitureBlocks.chair(), BlockRenderLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(setBuilder.furnitureBlocks.ladder(), BlockRenderLayer.CUTOUT);
            }
        });

        BlockRenderLayerMap.putBlock(ResourceItemsME.REEDS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.TALL_CATTAILS, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.TALL_BULRUSH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.HOGWEED, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.SHORT_HOGWEED, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WATERING_CAN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WOODEN_BUCKET, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BROWN_JUG, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.LARGE_JUG, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.AMPHORA, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BROWN_AMPHORA, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BROWN_JAR, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BROWN_FAT_POT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.FAT_POT, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SKELETON, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CANDLESTICK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CERAMIC_LAMP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CANDLE_HOLDER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SKULL_CANDLE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CANDLE_HEAP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_BRONZE_CHANDELIER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BRONZE_CHANDELIER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_CHANDELIER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CHANDELIER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BIG_BRAZIER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SMALL_BRAZIER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.GILDED_BIG_BRAZIER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.GILDED_SMALL_BRAZIER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.FIRE_BOWL, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.TORCH_OF_ORTHANC, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BONFIRE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.SCONCE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.GILDED_SCONCE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ORCISH_SCONCE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_SCONCE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.GILDED_WALL_SCONCE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ORCISH_WALL_SCONCE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ORCISH_ARTISAN_TABLE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.STONE_LECTERN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.CHISELED_DOLOMITE_BOOKSHELF, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BASALT_STATUE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.DEEPSLATE_STATUE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.PUMICE_STATUE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.GABBRO_STATUE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.GALONN_STATUE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ARKENSTONE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.WALL_ARKENSTONE, BlockRenderLayer.TRANSLUCENT);


        SimpleWoodChairModel.vanillaChairs.forEach(block -> {
            BlockRenderLayerMap.putBlock(block.base(), BlockRenderLayer.CUTOUT);
        });

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.BELLOWS, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(BlockRegistryME.TREATED_WOOD_ROPE_FENCE, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.STRUCTURE_MANAGER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.ORC_STRUCTURE_MANAGER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DecorativeBlockRegistryME.STRUCTURE_NEST, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.MIRKWOOD_VINES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.WILLOW_VINES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.GLOWWORM_MAIN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(NatureBlockRegistryME.GLOWWORM_WEBBING, BlockRenderLayer.CUTOUT);
    }
}
