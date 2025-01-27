package net.sevenstars.middleearth;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
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
import net.sevenstars.middleearth.client.model.equipment.chest.capes.armored.CapeMediumModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.RohirricHelmetArmorAddonModel;
import net.sevenstars.middleearth.client.model.equipment.head.hoods.armored.HoodModel;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.HeaterShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.KiteShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.RoundShieldEntityModel;
import net.sevenstars.middleearth.client.renderer.*;
import net.sevenstars.middleearth.datageneration.VariantsModelProvider;
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
import net.sevenstars.middleearth.entity.dwarves.longbeards.LongbeardDwarfRenderer;
import net.sevenstars.middleearth.entity.elves.galadhrim.GaladhrimElfRenderer;
import net.sevenstars.middleearth.entity.hobbits.shire.ShireHobbitRenderer;
import net.sevenstars.middleearth.entity.humans.bandit.BanditHumanRenderer;
import net.sevenstars.middleearth.entity.humans.dale.DaleHumanRenderer;
import net.sevenstars.middleearth.entity.humans.gondor.GondorHumanRenderer;
import net.sevenstars.middleearth.entity.humans.rohan.RohanHumanRenderer;
import net.sevenstars.middleearth.entity.model.ModEntityModels;
import net.sevenstars.middleearth.entity.orcs.isengard.IsengardOrcRenderer;
import net.sevenstars.middleearth.entity.orcs.misties.MistyGoblinRenderer;
import net.sevenstars.middleearth.entity.orcs.mordor.MordorOrcRenderer;
import net.sevenstars.middleearth.entity.orcs.wild.WildGoblinRenderer;
import net.sevenstars.middleearth.entity.pheasant.PheasantRenderer;
import net.sevenstars.middleearth.entity.projectile.boulder.BoulderEntityRenderer;
import net.sevenstars.middleearth.entity.projectile.spear.SpearEntityRenderer;
import net.sevenstars.middleearth.entity.seat.SeatRenderer;
import net.sevenstars.middleearth.entity.snail.SnailRenderer;
import net.sevenstars.middleearth.entity.spider.MirkwoodSpiderRenderer;
import net.sevenstars.middleearth.entity.swan.SwanRenderer;
import net.sevenstars.middleearth.entity.uruks.isengard.IsengardUrukHaiRenderer;
import net.sevenstars.middleearth.entity.uruks.misties.MistyHobgoblinRenderer;
import net.sevenstars.middleearth.entity.uruks.mordor.MordorBlackUrukRenderer;
import net.sevenstars.middleearth.event.KeyInputHandler;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.gui.forge.ForgeHeatingScreen;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreen;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreen;
import net.sevenstars.middleearth.item.ModEquipmentItems;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.sevenstars.middleearth.item.ModWeaponItems;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.utils.ModModelPredicateProvider;
import net.sevenstars.middleearth.item.utils.armor.ModArmorModels;
import net.sevenstars.middleearth.network.ModClientNetworkHandler;
import net.sevenstars.middleearth.network.connections.ConnectionToServer;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.particles.custom.AnvilBonkParticle;
import net.sevenstars.middleearth.particles.custom.LeavesParticle;
import net.sevenstars.middleearth.particles.custom.RingOfSmokeParticle;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;

public class MiddleEarthClient implements ClientModInitializer {
    
    public static final EntityModelLayer CUSTOM_ARMOR_HELMET = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "_1");
    public static final EntityModelLayer CUSTOM_ARMOR_CHESTPLATE = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "_2");
    public static final EntityModelLayer CUSTOM_ARMOR_LEGGINGS = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "_3");
    public static final EntityModelLayer CUSTOM_ARMOR_BOOTS = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "_4");
    public static final EntityModelLayer HELMET_ADDON_MODEL_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "helmet_addon");
    public static final EntityModelLayer CAPE_MODEL_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "cape");
    public static final EntityModelLayer HOOD_MODEL_LAYER = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, "armor"), "hood");

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
        EntityRendererRegistry.register(ModEntities.HOBBIT_CIVILIAN, ShireHobbitRenderer::new);
        EntityRendererRegistry.register(ModEntities.HOBBIT_BOUNDER, ShireHobbitRenderer::new);
        EntityRendererRegistry.register(ModEntities.HOBBIT_SHIRRIFF, ShireHobbitRenderer::new);

        EntityRendererRegistry.register(ModEntities.GONDORIAN_MILITIA, GondorHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.GONDORIAN_SOLDIER, GondorHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.GONDORIAN_KNIGHT, GondorHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.GONDORIAN_VETERAN, GondorHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.GONDORIAN_LEADER, GondorHumanRenderer::new);

        EntityRendererRegistry.register(ModEntities.ROHIRRIM_MILITIA, RohanHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.ROHIRRIM_SOLDIER, RohanHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.ROHIRRIM_KNIGHT, RohanHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.ROHIRRIM_VETERAN, RohanHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.ROHIRRIM_LEADER, RohanHumanRenderer::new);

        EntityRendererRegistry.register(ModEntities.DALISH_MILITIA, DaleHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.DALISH_SOLDIER, DaleHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.DALISH_KNIGHT, DaleHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.DALISH_VETERAN, DaleHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.DALISH_LEADER, DaleHumanRenderer::new);

        EntityRendererRegistry.register(ModEntities.LONGBEARD_MILITIA, LongbeardDwarfRenderer::new);
        EntityRendererRegistry.register(ModEntities.LONGBEARD_SOLDIER, LongbeardDwarfRenderer::new);
        EntityRendererRegistry.register(ModEntities.LONGBEARD_ELITE, LongbeardDwarfRenderer::new);
        EntityRendererRegistry.register(ModEntities.LONGBEARD_VETERAN, LongbeardDwarfRenderer::new);
        EntityRendererRegistry.register(ModEntities.LONGBEARD_LEADER, LongbeardDwarfRenderer::new);

        EntityRendererRegistry.register(ModEntities.LORIEN_MILITIA, GaladhrimElfRenderer::new);
        EntityRendererRegistry.register(ModEntities.LORIEN_SOLDIER, GaladhrimElfRenderer::new);
        EntityRendererRegistry.register(ModEntities.LORIEN_KNIGHT, GaladhrimElfRenderer::new);
        EntityRendererRegistry.register(ModEntities.LORIEN_VETERAN, GaladhrimElfRenderer::new);
        EntityRendererRegistry.register(ModEntities.LORIEN_LEADER, GaladhrimElfRenderer::new);
        
        EntityRendererRegistry.register(ModEntities.MORDOR_ORC_SNAGA, MordorOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.MORDOR_ORC_SOLDIER, MordorOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.MORDOR_BLACK_URUK_SOLDIER, MordorBlackUrukRenderer::new);
        EntityRendererRegistry.register(ModEntities.MORDOR_BLACK_URUK_VETERAN, MordorBlackUrukRenderer::new);
        EntityRendererRegistry.register(ModEntities.MORDOR_BLACK_URUK_LEADER, MordorBlackUrukRenderer::new);
        
        EntityRendererRegistry.register(ModEntities.MISTY_GOBLIN_SNAGA, MistyGoblinRenderer::new);
        EntityRendererRegistry.register(ModEntities.MISTY_GOBLIN_WARRIOR, MistyGoblinRenderer::new);
        EntityRendererRegistry.register(ModEntities.MISTY_HOBGOBLIN_SOLDIER, MistyHobgoblinRenderer::new);
        EntityRendererRegistry.register(ModEntities.MISTY_HOBGOBLIN_VETERAN, MistyHobgoblinRenderer::new);
        EntityRendererRegistry.register(ModEntities.MISTY_HOBGOBLIN_LEADER, MistyHobgoblinRenderer::new);

        EntityRendererRegistry.register(ModEntities.ISENGARD_ORC_SNAGA, IsengardOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.ISENGARD_ORC_WARRIOR, IsengardOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.ISENGARD_URUK_HAI_SOLDIER, IsengardUrukHaiRenderer::new);
        EntityRendererRegistry.register(ModEntities.ISENGARD_URUK_HAI_VETERAN, IsengardUrukHaiRenderer::new);
        EntityRendererRegistry.register(ModEntities.ISENGARD_URUK_HAI_LEADER, IsengardUrukHaiRenderer::new);

        EntityRendererRegistry.register(ModEntities.STONE_TROLL, StoneTrollRenderer::new);
        EntityRendererRegistry.register(ModEntities.PETRIFIED_TROLL, PetrifiedTrollRenderer::new);

        EntityRendererRegistry.register(ModEntities.BROADHOOF_GOAT, BroadhoofGoatRenderer::new);

        EntityRendererRegistry.register(ModEntities.WARG, WargRenderer::new);
        EntityRendererRegistry.register(ModEntities.MIRKWOOD_SPIDER, MirkwoodSpiderRenderer::new);
        EntityRendererRegistry.register(ModEntities.SNOW_TROLL, SnowTrollRenderer::new);
        //EntityRendererRegistry.register(ModEntities.BALROG, BalrogRenderer::new);

        EntityRendererRegistry.register(ModEntities.BANDIT_MILITIA, BanditHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.BANDIT_SOLDIER, BanditHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.BANDIT_CHIEFTAIN, BanditHumanRenderer::new);
        EntityRendererRegistry.register(ModEntities.WILD_GOBLIN, WildGoblinRenderer::new);

        EntityRendererRegistry.register(ModEntities.FIRE_OF_ORTHANC, FireOfOrthancEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PEBBLE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PINECONE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.LIT_PINECONE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPEAR, SpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOULDER, BoulderEntityRenderer::new);

        SimpleDyeableItemModel.items.forEach(this::registerDyeableItem);

        registerDyeableItem(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR);
        registerDyeableItem(ModEquipmentItems.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR);

        registerDyeableItem(ModEquipmentItems.WARG_LEATHER_ARMOR);
        registerDyeableItem(ModEquipmentItems.WARG_REINFORCED_LEATHER_ARMOR);


        // Animals
        EntityRendererRegistry.register(ModEntities.SWAN, SwanRenderer::new);
        EntityRendererRegistry.register(ModEntities.PHEASANT, PheasantRenderer::new);
        EntityRendererRegistry.register(ModEntities.SNAIL, SnailRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEER, DeerRenderer::new);

        EntityRendererRegistry.register(ModEntities.SEAT_ENTITY, SeatRenderer::new);

        ModModelPredicateProvider.registerAllPredicates();

        HandledScreens.register(ModScreenHandlers.FORGE_ALLOYING_SCREEN_HANDLER, ForgeAlloyingScreen::new);
        HandledScreens.register(ModScreenHandlers.FORGE_HEATING_SCREEN_HANDLER, ForgeHeatingScreen::new);
        HandledScreens.register(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, ArtisanTableScreen::new);
        HandledScreens.register(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, ShapingAnvilScreen::new);
        HandledScreens.register(ModScreenHandlers.WOOD_PILE_SCREEN_HANDLER, WoodPileScreen::new);
        BlockEntityRendererFactories.register(ModBlockEntities.TREATED_ANVIL, ShapingAnvilEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.FORGE, ForgeEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.REINFORCED_CHEST, ReinforcedChestEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.BELLOWS, BellowsBlockEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_HELMET, CustomHelmetModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_CHESTPLATE, CustomChestplateModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_LEGGINGS, CustomLeggingsModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_BOOTS, CustomBootsModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HELMET_ADDON_MODEL_LAYER, RohirricHelmetArmorAddonModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CAPE_MODEL_LAYER, CapeMediumModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HOOD_MODEL_LAYER, HoodModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(HEATER_SHIELD_LAYER, HeaterShieldEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(KITE_SHIELD_LAYER, KiteShieldEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ROUND_SHIELD_LAYER, RoundShieldEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(HELD_BANNER_LAYER, HeldBannerEntityModel::getTexturedModelData);

        BuiltinItemRendererRegistry.INSTANCE.register(ModWeaponItems.HEATER_SHIELD, new ModBuiltInModelItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModWeaponItems.KITE_SHIELD, new ModBuiltInModelItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModWeaponItems.ROUND_SHIELD, new ModBuiltInModelItemRenderer());

        BuiltinItemRendererRegistry.INSTANCE.register(ModWeaponItems.HELD_BANNER, new ModBuiltInModelItemRenderer());

        for(ModArmorModels.ModHelmetModels model : ModArmorModels.ModHelmetModels.values()){
            ArmorRenderer.register(new HelmetArmorRenderer(model.getModel()), model.getItem());
        }

        for(ModArmorModels.ModChestplateModels model : ModArmorModels.ModChestplateModels.values()){
            ArmorRenderer.register(new ChestplateArmorRenderer(model.getModel()), model.getItem());
        }

        ModEquipmentItems.armorPiecesListHelmets.forEach(armor -> {
            ArmorRenderer.register(new HelmetArmorRenderer(), armor.asItem());
        });
        ModEquipmentItems.armorPiecesListChestplates.forEach(armor -> {
            ArmorRenderer.register(new ChestplateArmorRenderer(), armor.asItem());
        });
        ModEquipmentItems.armorPiecesListLeggings.forEach(armor -> {
            ArmorRenderer.register(new LeggingsArmorRenderer(), armor.asItem());
        });
        ModEquipmentItems.armorPiecesListBoots.forEach(armor -> {
            ArmorRenderer.register(new BootsArmorRenderer(), armor.asItem());
        });

        ModEquipmentItems.hoods.forEach(hood -> {
            ArmorRenderer.register(new HoodRenderer(), hood);
        });
        ModEquipmentItems.capes.forEach(cape -> {
            ArmorRenderer.register(new CapeRenderer(), cape);
        });

        ModelLoadingPlugin.register(pluginContext -> {
            for(Item item : SimpleBigItemModel.items) {
                Identifier identifier = VariantsModelProvider.getInventoryModelIdentifierVariant(item);
                pluginContext.addModels(identifier);
            }

            for(Item item : SimpleBigItemModel.bigBows) {
                Identifier identifier = VariantsModelProvider.getInventoryModelIdentifierVariant(item);
                pluginContext.addModels(identifier);
                identifier = VariantsModelProvider.getInventoryLongbowModelIdentifierVariant(item, 0);
                pluginContext.addModels(identifier);
                identifier = VariantsModelProvider.getInventoryLongbowModelIdentifierVariant(item, 1);
                pluginContext.addModels(identifier);
                identifier = VariantsModelProvider.getInventoryLongbowModelIdentifierVariant(item, 2);
                pluginContext.addModels(identifier);
            }

            for(Item item : SimpleBigItemModel.artefacts) {
                Identifier identifier = VariantsModelProvider.getInventoryModelIdentifierVariant(item);
                pluginContext.addModels(identifier);
            }

            for(Item item : SimpleBigItemModel.artefactsGlowing) {
                Identifier identifierGlowing = VariantsModelProvider.getInventoryModelGlowingItem(item);
                pluginContext.addModels(identifierGlowing);
            }

            for(Item item : SimpleBigItemModel.artefactsBroken) {
                Identifier identifierBroken = VariantsModelProvider.getInventoryModelBrokenItem(item);
                pluginContext.addModels(identifierBroken);
            }

            for(Item item : SimpleSpearModel.items) {
                Identifier identifier = VariantsModelProvider.getInventoryModelIdentifierVariant(item);
                pluginContext.addModels(identifier);
            }

            for(Item item : SimpleBigItemModel.genericItems){
                Identifier identifier = VariantsModelProvider.getInventoryModelIdentifierVariant(item);
                pluginContext.addModels(identifier);
            }
        });
        ModelLoadingPlugin.register(pluginContext -> {
            for(Item item : HotMetalsModel.ingots) {
                Identifier identifier = VariantsModelProvider.getHotModelIdentifierVariant(item);
                pluginContext.addModels(identifier);
            }
            for(Item item : HotMetalsModel.nuggets) {
                Identifier identifier = VariantsModelProvider.getHotModelIdentifierVariant(item);
                pluginContext.addModels(identifier);
            }
            for(Item item : HotMetalsModel.items) {
                Identifier identifier = VariantsModelProvider.getHotModelIdentifierVariant(item);
                pluginContext.addModels(identifier);
            }
        });


        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.MALLORN_LEAVES_PARTICLE, LeavesParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.MIRKWOOD_LEAVES_PARTICLE, LeavesParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.ANVIL_SPARK_PARTICLE, AnvilBonkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.RING_OF_SMOKE, RingOfSmokeParticle.Factory::new);

        initializeRenderLayerMap();
    }

    private void initializeRenderLayerMap() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MORGUL_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.HANGING_COBWEB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.CORNER_COBWEB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MIRKWOOD_SPIDER_EGG, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.BROWN_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.GREEN_SHRUB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SMALL_DRY_SHRUB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FROZEN_SHRUB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MALLOS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.ELANOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TAN_SHRUB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.STRAWBERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TOUGH_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.YELLOW_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MIRKWOOD_ROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MIRKWOOD_HANGING_ROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.PINE_BRANCHES, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.LIGHT_BLUE_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MAGENTA_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.ORANGE_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.PINK_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.PURPLE_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.RED_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WHITE_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.YELLOW_FLOWERS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.LAVENDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.YELLOW_TROLLIUS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.DRY_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.DYING_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FROZEN_GRASS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.GRIM_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TEMPERATE_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.GRASS_TUFT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FROZEN_TUFT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.HEATHER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.RED_HEATHER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.DEAD_HEATHER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.DRY_HEATHER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.HEATH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WHEATGRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WILD_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WILDERGRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.BEACH_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.COASTAL_PANIC_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SEDUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.YELLOW_SEDUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.BRACKEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.DEAD_RUSHES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FALSE_OATGRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SHORT_CATTAILS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SHORT_BULRUSH, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SHRIVELED_SHRUB, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SCORCHED_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SCORCHED_TUFT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SCORCHED_SHRUB, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.HOROKAKA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.GIANT_HOROKAKA, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SMALL_LILY_PADS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.LILY_PADS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.DUCKWEED, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FLOATING_ICE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASSY_DIRT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASSY_DIRT_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASSY_DIRT_STAIRS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEBBLED_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEBBLED_GRASS_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEBBLED_GRASS_STAIRS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.FIRE_OF_ORTHANC, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POINTED_LIMESTONE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POINTED_GALONN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POINTED_IZHERABAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POINTED_DOLOMITE, RenderLayer.getCutout());

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
                        return FoliageColors.getDefaultColor();
                    }
                    return BiomeColors.getFoliageColor(view, pos);
                }, ModNatureBlocks.FALLEN_LEAVES);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getDefaultColor(), ModNatureBlocks.WILD_GRASS, ModNatureBlocks.GRASS_TUFT, ModNatureBlocks.WHEATGRASS,
                ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_SLAB, ModBlocks.GRASSY_DIRT_STAIRS,
                ModBlocks.PEBBLED_GRASS, ModBlocks.PEBBLED_GRASS_SLAB, ModBlocks.PEBBLED_GRASS_STAIRS,
                ModBlocks.TURF, ModBlocks.TURF_SLAB, ModBlocks.TURF_STAIRS, ModBlocks.TURF_VERTICAL_SLAB);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), ModNatureBlocks.FALLEN_LEAVES);

        for(Block block : SimpleDoubleBlockModel.doubleBlocks){
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        for(Block block : SimpleFlowerBedModel.flowerBeds){
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        for(SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots){
            BlockRenderLayerMap.INSTANCE.putBlock(flowerPot.pottedPlant(), RenderLayer.getCutout());
        }

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MIRKWOOD_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WHITE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WHITE_MUSHROOM_TILLER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.MOSS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.STICKY_SNOW, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.STICKY_ICE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FOREST_MOSS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.CORRUPTED_MOSS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.SHORT_ICICLES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.DROOPING_ICICLES, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.SPRUCE_STABLE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.RUINED_DWARVEN_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, RenderLayer.getCutout());

        for (Block block : Crops.crops){
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        for(Block block : TintableCrossModel.notTintedBlocks) {
            if(block != null) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }
        for(Block block : TintableCrossModel.tintedBlocks) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BEECH.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.LARCH.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BLACK_LEBETHRON.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_LEBETHRON.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.CHESTNUT.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.FIR.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.HOLLY.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MALLORN.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MAPLE.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.SILVER_MAPLE.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MIRKWOOD.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.PALM.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_PALM.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.PINE.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BLACK_PINE.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WILLOW.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.SCORCHED.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.MUSHROOM.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.DARK_MUSHROOM.ladder(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.GRAY_MUSHROOM.ladder(), RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.TREATED_WOOD_LADDER, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.ROPE_LADDER, RenderLayer.getCutout());

        for(SimpleLadderModel.Ladder block : SimpleLadderModel.vanillaLadders) {
            BlockRenderLayerMap.INSTANCE.putBlock(block.ladder(), RenderLayer.getCutout());
        }

        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.DARK_MUSHROOM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.GRAY_MUSHROOM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.MUSHROOM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.SCORCHED.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WILLOW.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.PALM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_PALM.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MIRKWOOD.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MALLORN.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_LEBETHRON.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BLACK_LEBETHRON.trapdoor(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BEECH.trapdoor(), RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BEECH.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BLACK_LEBETHRON.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_LEBETHRON.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.CHESTNUT.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.LARCH.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MALLORN.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.PALM.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_PALM.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WILLOW.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.SCORCHED.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.GRAY_MUSHROOM.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.DARK_MUSHROOM.door(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.MUSHROOM.door(), RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BEECH.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.LARCH.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BLACK_LEBETHRON.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_LEBETHRON.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.CHESTNUT.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.FIR.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.HOLLY.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MALLORN.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MAPLE.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.SILVER_MAPLE.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.MIRKWOOD.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.PALM.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WHITE_PALM.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.PINE.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.BLACK_PINE.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.WILLOW.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WoodBlockSets.SCORCHED.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.GRAY_MUSHROOM.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.DARK_MUSHROOM.chair(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlockSets.MUSHROOM.chair(), RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.TREATED_WOOD_CHAIR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FALLEN_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FALLEN_MALLORN_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.QUARTZ_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_QUARTZ_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_QUARTZ_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_QUARTZ_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_AGATE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_RED_AGATE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_RED_AGATE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_RED_AGATE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CITRINE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_CITRINE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_CITRINE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_CITRINE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLOWSTONE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_GLOWSTONE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_GLOWSTONE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_GLOWSTONE_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.DWARVEN_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WALL_DWARVEN_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.CRYSTAL_LAMP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WALL_CRYSTAL_LAMP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.SILVER_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WALL_SILVER_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.ELVEN_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WALL_ELVEN_LANTERN, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BRONZE_CHAIN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BRONZE_BROAD_CHAIN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.SPIKY_CHAIN, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NET, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OXIDIZED_COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_OXIDIZED_COPPER_BARS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TREATED_STEEL_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TREATED_STEEL_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TREATED_STEEL_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILVER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GILDED_BARS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WILD_CARROT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WILD_POTATO, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WILD_BEETROOT, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.AZALEA_FLOWER_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.DRY_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.FROZEN_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.GREEN_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.IVY_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.LILAC_FLOWER_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.PINK_FLOWER_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.RED_FLOWER_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.WHITE_FLOWER_GROWTH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.YELLOW_FLOWER_GROWTH, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.MEDGON_SPIKE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WOOD_FRAMED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WOOD_FRAMED_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WATTLE_FRAMED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WATTLE_FRAMED_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.PLASTER_ROUND_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.PLASTER_ROUND_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GONLUIN_CARVED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GONLUIN_CARVED_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.TUFF_CARVED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.TUFF_CARVED_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BLACKSTONE_CARVED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BLACKSTONE_CARVED_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.IZHERABAN_CARVED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.IZHERABAN_CARVED_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.MEDGON_CARVED_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.MEDGON_CARVED_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.RED_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS, RenderLayer.getTranslucent());
        
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.RED_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModResourceItems.REEDS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TALL_CATTAILS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModNatureBlocks.TALL_BULRUSH, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WATERING_CAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WOODEN_BUCKET, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BROWN_JUG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.LARGE_JUG, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.AMPHORA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BROWN_AMPHORA, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BROWN_JAR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BROWN_FAT_POT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.FAT_POT, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.CANDLE_HEAP, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BIG_BRAZIER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.SMALL_BRAZIER, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GILDED_BIG_BRAZIER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GILDED_SMALL_BRAZIER, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.FIRE_BOWL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.TORCH_OF_ORTHANC, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BONFIRE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.SCONCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GILDED_SCONCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.ORCISH_SCONCE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WALL_SCONCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GILDED_WALL_SCONCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.ORCISH_WALL_SCONCE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.GALONN_STATUE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.ARKENSTONE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.WALL_ARKENSTONE, RenderLayer.getTranslucent());

        SimpleWoodChairModel.vanillaChairs.forEach(block -> {
            BlockRenderLayerMap.INSTANCE.putBlock(block.base(), RenderLayer.getCutout());
        });

        BlockRenderLayerMap.INSTANCE.putBlock(ModDecorativeBlocks.BELLOWS, RenderLayer.getCutout());
    }

    private void registerDyeableItem(Item item) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR), item);
    }
}
