package net.sevenstars.middleearth;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.coffers.*;
import net.sevenstars.middleearth.block.special.crockpot.CrockpotScreen;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancEntityRenderer;
import net.sevenstars.middleearth.block.special.forge.ForgeEntityRenderer;
import net.sevenstars.middleearth.block.special.plate.PlateEntityRenderer;
import net.sevenstars.middleearth.block.special.pots.LootablePotBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilEntityRenderer;
import net.sevenstars.middleearth.client.BlockColorsME;
import net.sevenstars.middleearth.client.MiddleEarthDimensionEffects;
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
import net.sevenstars.middleearth.client.renderer.ModBuiltInModelItemRenderer;
import net.sevenstars.middleearth.client.renderer.armor.*;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.Crops;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
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
import net.sevenstars.middleearth.entity.spider.EnwebbedFeatureRenderer;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaRenderer;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderer;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobRenderer;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreen;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreen;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreen;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreen;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreen;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.weapons.HotComponentProperty;
import net.sevenstars.middleearth.item.items.weapons.SneakAttackProperty;
import net.sevenstars.middleearth.item.items.ColoredBundleItem;
import net.sevenstars.middleearth.item.items.weapons.artefacts.ArtefactCustomGlowingDaggerWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.artefacts.ArtefactCustomGlowingLongswordWeaponItem;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.network.ModClientNetworkHandler;
import net.sevenstars.middleearth.network.connections.ConnectionToServer;

@EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID, value = Dist.CLIENT)
public final class MiddleEarthClient {
    private static final ResourceLocation PULL = ResourceLocation.withDefaultNamespace("pull");
    private static final ResourceLocation PULLING = ResourceLocation.withDefaultNamespace("pulling");
    private static final ResourceLocation CHARGED = ResourceLocation.withDefaultNamespace("charged");
    private static final ResourceLocation FIREWORK = ResourceLocation.withDefaultNamespace("firework");
    private static final ResourceLocation BLOCKING = ResourceLocation.withDefaultNamespace("blocking");
    private static final ResourceLocation HOLDING = ResourceLocation.withDefaultNamespace("holding");
    private static final ResourceLocation BROKEN = ResourceLocation.withDefaultNamespace("broken");
    private static final ResourceLocation GLOWING = ResourceLocation.withDefaultNamespace("glowing");
    private static final ResourceLocation FILLED = ResourceLocation.withDefaultNamespace("filled");

    private static final ItemPropertyFunction USING_ITEM = (stack, level, entity, seed) ->
            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
    private static final ItemPropertyFunction BOW_PULL = (stack, level, entity, seed) ->
            entity == null || entity.getUseItem() != stack
                    ? 0.0F
                    : (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
    private static final ItemPropertyFunction LONGBOW_PULL = (stack, level, entity, seed) ->
            entity == null || entity.getUseItem() != stack
                    ? 0.0F
                    : (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 30.0F;
    private static final ItemPropertyFunction CROSSBOW_PULL = (stack, level, entity, seed) ->
            entity == null || CrossbowItem.isCharged(stack)
                    ? 0.0F
                    : (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks())
                    / (float) CrossbowItem.getChargeDuration(stack, entity);
    private static final ItemPropertyFunction CROSSBOW_PULLING = (stack, level, entity, seed) ->
            entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack)
                    ? 1.0F : 0.0F;
    private static final ItemPropertyFunction CROSSBOW_CHARGED = (stack, level, entity, seed) ->
            CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
    private static final ItemPropertyFunction CROSSBOW_FIREWORK = (stack, level, entity, seed) -> {
        ChargedProjectiles projectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
        return projectiles != null && projectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
    };
    private static final ItemPropertyFunction ARTEFACT_BROKEN = (stack, level, entity, seed) ->
            stack.isDamageableItem() && stack.getDamageValue() >= stack.getMaxDamage() - 1 ? 1.0F : 0.0F;
    private static final ItemPropertyFunction ARTEFACT_GLOWING = (stack, level, entity, seed) -> {
        if (stack.getItem() instanceof ArtefactCustomGlowingLongswordWeaponItem) {
            return ArtefactCustomGlowingLongswordWeaponItem.shouldBeGlowing(level, entity) ? 1.0F : 0.0F;
        }
        if (stack.getItem() instanceof ArtefactCustomGlowingDaggerWeaponItem) {
            return ArtefactCustomGlowingDaggerWeaponItem.shouldBeGlowing(level, entity) ? 1.0F : 0.0F;
        }
        return 0.0F;
    };
    
    public static final ModelLayerLocation CUSTOM_ARMOR_HELMET = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "_1");
    public static final ModelLayerLocation CUSTOM_ARMOR_CHESTPLATE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "_2");
    public static final ModelLayerLocation CUSTOM_ARMOR_LEGGINGS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "_3");
    public static final ModelLayerLocation CUSTOM_ARMOR_BOOTS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "_4");
    public static final ModelLayerLocation HELMET_ADDON_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "helmet_addon");
    public static final ModelLayerLocation BACK_ATTACHMENT_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "back_attachment");
    public static final ModelLayerLocation HELMET_ATTACHMENT_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "helmet_attachment");

    public static final ModelLayerLocation HEATER_SHIELD_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "heater_shield"), "main");
    public static final ModelLayerLocation KITE_SHIELD_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "kite_shield"), "main");
    public static final ModelLayerLocation ROUND_SHIELD_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "round_shield"), "main");

    public static final ModelLayerLocation HELD_BANNER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "held_banner"), "main");

    private MiddleEarthClient() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModClientNetworkHandler.register(new ConnectionToServer());
            registerItemProperties();
            registerArmorRenderers();
            initializeRenderLayerMap();
        });
    }

    private static void registerItemProperties() {
        ItemProperties.registerGeneric(MiddleEarth.of("sneak_attack"), new SneakAttackProperty());
        ItemProperties.registerGeneric(MiddleEarth.of("hot_component"), new HotComponentProperty());

        SimpleBowItemModel.items.forEach(item -> {
            ItemProperties.register(item, PULL, BOW_PULL);
            ItemProperties.register(item, PULLING, USING_ITEM);
        });
        SimpleBigItemModel.bigBows.forEach(item -> {
            ItemProperties.register(item, PULL, LONGBOW_PULL);
            ItemProperties.register(item, PULLING, USING_ITEM);
        });
        SimpleCrossbowItemModel.items.forEach(item -> {
            ItemProperties.register(item, PULL, CROSSBOW_PULL);
            ItemProperties.register(item, PULLING, CROSSBOW_PULLING);
            ItemProperties.register(item, CHARGED, CROSSBOW_CHARGED);
            ItemProperties.register(item, FIREWORK, CROSSBOW_FIREWORK);
        });
        WeaponItemsME.shields.forEach(item -> ItemProperties.register(item, BLOCKING, USING_ITEM));
        SimpleSpearModel.items.forEach(item -> ItemProperties.register(item, HOLDING, USING_ITEM));
        SimpleArtefactModels.artefacts.forEach(artefact -> {
            ItemProperties.register(artefact.artefact(), BROKEN, ARTEFACT_BROKEN);
            if (artefact.artefact() instanceof ArtefactCustomGlowingLongswordWeaponItem
                    || artefact.artefact() instanceof ArtefactCustomGlowingDaggerWeaponItem) {
                ItemProperties.register(artefact.artefact(), GLOWING, ARTEFACT_GLOWING);
            }
        });
        ResourceItemsME.COLORED_BUNDLES.forEach(item ->
                ItemProperties.register(item, FILLED, (stack, level, entity, seed) ->
                        BundleItem.getFullnessDisplay(stack))
        );
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModScreenHandlers.CROCKPOT_SCREEN_HANDLER, CrockpotScreen::new);
        event.register(ModScreenHandlers.FORGE_ALLOYING_SCREEN_HANDLER, ForgeAlloyingScreen::new);
        event.register(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, ArtisanTableScreen::new);
        event.register(ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER, InscriptionTableScreen::new);
        event.register(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, ShapingAnvilScreen::new);
        event.register(ModScreenHandlers.WOOD_PILE_SCREEN_HANDLER, WoodPileScreen::new);
        event.register(ModScreenHandlers.STRUCTURE_MANAGER_SCREEN_HANDLER, StructureManagerScreen::new);
        event.register(ModScreenHandlers.STRUCTURE_NEST_SCREEN_HANDLER, StructureNestScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntitiesME.SNOW_TROLL, SnowTrollRenderer::new);
        event.registerEntityRenderer(EntitiesME.CAVE_TROLL, CaveTrollRenderer::new);
        event.registerEntityRenderer(EntitiesME.STONE_TROLL, StoneTrollRenderer::new);
        event.registerEntityRenderer(EntitiesME.PETRIFIED_TROLL, PetrifiedTrollRenderer::new);
        event.registerEntityRenderer(EntitiesME.BROADHOOF_GOAT, BroadhoofGoatRenderer::new);
        event.registerEntityRenderer(EntitiesME.GREAT_HORN, GreatHornRenderer::new);
        event.registerEntityRenderer(EntitiesME.WARG, WargRenderer::new);
        event.registerEntityRenderer(EntitiesME.REINFORCED_BARREL, BarrelEntityRenderer::new);
        event.registerEntityRenderer(EntitiesME.SHELOBITE_LARVA, ShelobiteLarvaRenderer::new);
        event.registerEntityRenderer(EntitiesME.SHELOBITE_SCUTTLER, ShelobiteScuttlerRenderer::new);
        event.registerEntityRenderer(EntitiesME.SPAWN_OF_SHELOB, SpawnOfShelobRenderer::new);
        event.registerEntityRenderer(EntitiesME.FIRE_OF_ORTHANC, FireOfOrthancEntityRenderer::new);
        event.registerEntityRenderer(EntitiesME.PEBBLE, ThrownItemRenderer::new);
        event.registerEntityRenderer(EntitiesME.PINECONE, ThrownItemRenderer::new);
        event.registerEntityRenderer(EntitiesME.LIT_PINECONE, ThrownItemRenderer::new);
        event.registerEntityRenderer(EntitiesME.SPEAR, SpearEntityRenderer::new);
        event.registerEntityRenderer(EntitiesME.BOULDER, BoulderEntityRenderer::new);
        event.registerEntityRenderer(EntitiesME.SMOKE_RING_PROJECTILE, SmokeRingProjectileRenderer::new);
        event.registerEntityRenderer(EntitiesME.WEB, ThrownItemRenderer::new);
        event.registerEntityRenderer(EntitiesME.NPC, NpcEntityRenderer::new);
        event.registerEntityRenderer(EntitiesME.SEAT_ENTITY, SeatRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.STONE_ANVIL, ShapingAnvilEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TREATED_ANVIL, ShapingAnvilEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FORGE, ForgeEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.LARCH_COFFER, LarchCofferEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PINE_COFFER, PineCofferEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SPRUCE_COFFER, SpruceCofferEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FIR_COFFER, FirCofferEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BEECH_COFFER, BeechCofferEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CHESTNUT_COFFER, ChestnutCofferEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.OAK_COFFER, OakCofferEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.WILLOW_COFFER, WillowCofferEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.REINFORCED_CHEST, ReinforcedChestEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BELLOWS, BellowsBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PLATE, PlateEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityType.DECORATED_POT, LootablePotBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        EntityModelLayersME.registerLayerDefinitions(event);
        event.registerLayerDefinition(CUSTOM_ARMOR_HELMET, CustomHelmetModel::getTexturedModelData);
        event.registerLayerDefinition(CUSTOM_ARMOR_CHESTPLATE, CustomChestplateModel::getTexturedModelData);
        event.registerLayerDefinition(CUSTOM_ARMOR_LEGGINGS, CustomLeggingsModel::getTexturedModelData);
        event.registerLayerDefinition(CUSTOM_ARMOR_BOOTS, CustomBootsModel::getTexturedModelData);
        event.registerLayerDefinition(HELMET_ADDON_MODEL_LAYER, RohanHelmetModel::getTexturedModelData);
        event.registerLayerDefinition(BACK_ATTACHMENT_MODEL_LAYER, CapeMediumModel::getTexturedModelData);
        event.registerLayerDefinition(HELMET_ATTACHMENT_MODEL_LAYER, HoodModel::getTexturedModelData);
        event.registerLayerDefinition(HEATER_SHIELD_LAYER, HeaterShieldEntityModel::getTexturedModelData);
        event.registerLayerDefinition(KITE_SHIELD_LAYER, KiteShieldEntityModel::getTexturedModelData);
        event.registerLayerDefinition(ROUND_SHIELD_LAYER, RoundShieldEntityModel::getTexturedModelData);
        event.registerLayerDefinition(HELD_BANNER_LAYER, HeldBannerEntityModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColorsME.registerBlockColors(event);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, tintIndex) -> tintIndex == 0 && stack.getItem() instanceof ColoredBundleItem bundle
                        ? bundle.color().getTextureDiffuseColor()
                        : 0xFFFFFFFF,
                ResourceItemsME.COLORED_BUNDLES.toArray(Item[]::new)
        );
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "middle_earth"),
                new MiddleEarthDimensionEffects()
        );
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        ModBuiltInModelItemRenderer.register(
                event,
                WeaponItemsME.HEATER_SHIELD,
                WeaponItemsME.KITE_SHIELD,
                WeaponItemsME.ROUND_SHIELD,
                WeaponItemsME.HELD_BANNER
        );
    }

    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for (var skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new EnwebbedFeatureRenderer<>(renderer, event.getEntityModels()));
            }
        }
    }

    private static void registerArmorRenderers() {
        for(ArmorModelsME.ModHelmetModels model : ArmorModelsME.ModHelmetModels.values()){
            ArmorRenderer.register(new HelmetArmorRenderer(model.getModel()), model.getItem());
        }
        ArmorRenderer.register(new HelmetVariantsRenderer(new SilvanLordHelmetModel(SilvanLordHelmetModel.getTexturedModelData().bakeRoot())), EquipmentItemsME.SILVAN_LORD_HELMET);
        ArmorRenderer.register(new HelmetVariantsRenderer(new ErynGalenWatchwardenHelmetModel(ErynGalenWatchwardenHelmetModel.getTexturedModelData().bakeRoot())), EquipmentItemsME.ERYN_GALEN_WATCHWARDEN_HELMET);
        ArmorRenderer.register(new HelmetVariantsRenderer(new ErynGalenWatchwardenHelmetModel(ErynGalenWatchwardenHelmetModel.getTexturedModelData().bakeRoot())), EquipmentItemsME.OXIDISED_ERYN_GALEN_WATCHWARDEN_HELMET);
        ArmorRenderer.register(new WoodlandCrownRenderer(new WoodlandRealmCrownModel(WoodlandRealmCrownModel.getTexturedModelData().bakeRoot())), EquipmentItemsME.WOODLAND_REALM_CROWN);

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

        EquipmentItemsME.helmetAttachments.forEach(hood -> {
            ArmorRenderer.register(new HelmetAttachmentRenderer(), hood);
        });
        EquipmentItemsME.backAttachments.forEach(cape -> {
            ArmorRenderer.register(new BackAttachmentRenderer(), cape);
        });
    }

    private static void initializeRenderLayerMap() {
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MORGUL_IVY, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.HANGING_WEBS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.CORNER_COBWEB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHELOBITE_LARVA_EGG, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.HANGING_SHELOBITE_LARVA_EGG, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.ATHELAS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BROWN_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.GREEN_SHRUB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SMALL_DRY_SHRUB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHORT_DRY_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.TALL_DRY_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WILDFLOWERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LEAF_LITTER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FIREFLY_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.RESIN_CLUMP, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FROZEN_SHRUB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.ELANOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MALLOS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.NIPHREDIL, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SIMBELMYNE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.TAN_SHRUB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.STRAWBERRY_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.TOUGH_BERRY_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.YELLOW_FLOWER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BLUE_GENTIAN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.NOBLEWHITE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MIRKWOOD_ROOTS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MIRKWOOD_HANGING_ROOTS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.PINE_BRANCHES, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LIGHT_BLUE_FLOWERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MAGENTA_FLOWERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.ORANGE_FLOWERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.PINK_FLOWERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.PURPLE_FLOWERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.RED_FLOWERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WHITE_FLOWERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.YELLOW_FLOWERS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BLUE_LAVENDER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LAVENDER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WHITE_LAVENDER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.YELLOW_TROLLIUS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.HOBBIT_SUNFLOWERS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BLUE_FESCUE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DYING_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FROZEN_GRASS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.GRIM_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MEADOWGRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SPARSE_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.NETTLES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.THISTLE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.TEMPERATE_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.GRASS_TUFT, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FROZEN_TUFT, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.HEATHER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.RED_HEATHER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DEAD_HEATHER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DRY_HEATHER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.HEATH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WHEATGRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WILD_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WILDERGRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BEACH_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.COASTAL_PANIC_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MISTWEED, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SEDUM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.ORANGE_SEDUM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.RED_SEDUM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.YELLOW_SEDUM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.GIANT_BUTTERBUR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BRACKEN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.CAMPION, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BLUE_BIGLEAF_HYDRANGEA, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.PINK_BIGLEAF_HYDRANGEA, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WHITE_BIGLEAF_HYDRANGEA, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DEAD_HEATHER_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DRY_HEATHER_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DEAD_RUSHES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FALSE_OATGRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.HEATHER_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LARGE_BLUE_FESCUE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LARGE_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LARGE_SHRIVELED_SHRUB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.RED_HEATHER_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.RUSHES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.BRAMBLES_OF_MORDOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHORT_DEAD_RUSHES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.CLOVERS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHORT_RUSHES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHORT_REEDS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHORT_CATTAILS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHORT_BULRUSH, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHRIVELED_SHRUB, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SCORCHED_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SCORCHED_TUFT, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SCORCHED_SHRUB, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SMALL_LILY_PADS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LILY_PADS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FLOWERING_LILY_PADS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LARGE_LILY_PAD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LARGE_FLOWERING_LILY_PAD, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DUCKWEED, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FLOATING_ICE, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_DIRT, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_DIRT_SLAB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_DIRT_STAIRS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHALKSOIL_GRASS_BLOCK, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LOAM_GRASS_BLOCK, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEAT_GRASS_BLOCK, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SILT_GRASS_BLOCK, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_CHALKSOIL, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_CHALKSOIL_SLAB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_CHALKSOIL_STAIRS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_LOAM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_LOAM_SLAB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_LOAM_STAIRS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_PEAT, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_PEAT_SLAB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_PEAT_STAIRS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_SILT, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_SILT_SLAB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASSY_SILT_STAIRS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEBBLED_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEBBLED_GRASS_SLAB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEBBLED_GRASS_STAIRS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.FIRE_OF_ORTHANC, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.INSCRIPTION_TABLE, RenderType.cutout());

        /*ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CERAMIC_CROCKPOT, BlockRenderLayer.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CROCKPOT, BlockRenderLayer.CUTOUT);*/
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CERAMIC_PLATE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ROTTEN_PLATE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SILVER_PLATE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.TAPPER, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POINTED_LIMESTONE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POINTED_GALONN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POINTED_IZHERABAN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POINTED_DOLOMITE, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EMBERS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.REINFORCED_SCAFFOLDING, RenderType.cutout());

        for(Block block : SimpleDoubleBlockModel.doubleBlocks){
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        }

        for(Block block : SimpleFlowerBedModel.flowerBeds){
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        }

        for(SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots){
            ItemBlockRenderTypes.setRenderLayer(flowerPot.pottedPlant(), RenderType.cutout());
        }

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MIRKWOOD_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WHITE_MUSHROOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WHITE_MUSHROOM_TILLER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MOSS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.STICKY_SNOW, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.STICKY_ICE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FOREST_MOSS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.CORRUPTED_MOSS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHORT_ICICLES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DROOPING_ICICLES, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BURZUM_SPIKES, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SPRUCE_STABLE_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.RUINED_DWARVEN_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, RenderType.cutout());

        for (Block block : Crops.crops){
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        }

        for(Block block : TintableCrossModel.notTintedBlocks()) {
            if(block != null) ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        }

        for(Block block : TintableCrossModel.tintedBlocks()) {
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        }

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.TREATED_WOOD_LADDER, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ROPE_LADDER, RenderType.cutout());

        for(SimpleLadderModel.Ladder block : SimpleLadderModel.vanillaLadders) {
            ItemBlockRenderTypes.setRenderLayer(block.ladder(), RenderType.cutout());
        }

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.TREATED_WOOD_CHAIR, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FALLEN_LEAVES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FALLEN_MALLORN_LEAVES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.QUARTZ_CLUSTER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_QUARTZ_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEDIUM_QUARTZ_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LARGE_QUARTZ_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_AGATE_CLUSTER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_RED_AGATE_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEDIUM_RED_AGATE_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LARGE_RED_AGATE_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CITRINE_CLUSTER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_CITRINE_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEDIUM_CITRINE_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LARGE_CITRINE_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GLOWSTONE_CLUSTER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_GLOWSTONE_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEDIUM_GLOWSTONE_BUD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LARGE_GLOWSTONE_BUD, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_BLACK_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_BLUE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_BROWN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_BURNT_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_DARK_BLUE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_DARK_BROWN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_DARK_GREEN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_DARK_RED_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_FANCY_BLUE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_FANCY_GREEN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_FANCY_RED_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_GRAY_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_GREEN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_PURPLE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_RED_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_ROTTEN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_WHITE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_YELLOW_CURTAIN, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BLACK_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BLUE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BROWN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BURNT_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.DARK_BLUE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.DARK_BROWN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.DARK_GREEN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.DARK_RED_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.FANCY_BLUE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.FANCY_GREEN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.FANCY_RED_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.GRAY_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.GREEN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.PURPLE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.RED_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ROTTEN_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WHITE_CURTAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.YELLOW_CURTAIN, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.DWARVEN_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_DWARVEN_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.TREATED_STEEL_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_TREATED_STEEL_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CRYSTAL_LAMP, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_CRYSTAL_LAMP, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SILVER_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_SILVER_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ELVEN_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_ELVEN_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CRUDE_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_CRUDE_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.LEAD_LANTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_LEAD_LANTERN, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BRONZE_CHAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BRONZE_BROAD_CHAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CRUDE_CHAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CRUDE_BROAD_CHAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SPIKY_CHAIN, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NET, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EXPOSED_COPPER_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WEATHERED_COPPER_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OXIDIZED_COPPER_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_COPPER_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_EXPOSED_COPPER_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_WEATHERED_COPPER_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_OXIDIZED_COPPER_BARS, RenderType.cutout());
        
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRUDE_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TREATED_STEEL_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TREATED_STEEL_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BURZUM_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.AGED_WOOD_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRUDE_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.AGED_WOOD_TRAPDOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRUDE_TRAPDOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TREATED_STEEL_TRAPDOOR, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SILVER_BARS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GILDED_BARS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WILD_CARROT, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WILD_POTATO, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WILD_BEETROOT, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.AZALEA_FLOWER_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.DRY_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.FROZEN_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.GREEN_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.IVY_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.LILAC_FLOWER_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.PINK_FLOWER_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.RED_FLOWER_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.THORNY_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WHITE_FLOWER_GROWTH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.YELLOW_FLOWER_GROWTH, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WEBBING, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.MEDGON_SPIKE, RenderType.cutout());
        
        for (SimplePaneModel.Pane pane : SimplePaneModel.panes){
            ItemBlockRenderTypes.setRenderLayer(pane.glass(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(pane.pane(), RenderType.translucent());
        }

        StoneBlockSets.stoneSetsList.forEach(setBuilder -> {
            if (setBuilder.carvedWindows != null){
                ItemBlockRenderTypes.setRenderLayer(setBuilder.carvedWindows.block(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(setBuilder.carvedWindows.verticalSlab(), RenderType.cutout());
            }
        });

        WoodBlockSets.woodSetsList.forEach(setBuilder -> {
            if (setBuilder.redstoneBlocks != null){
                ItemBlockRenderTypes.setRenderLayer(setBuilder.redstoneBlocks.trapdoor(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(setBuilder.redstoneBlocks.door(), RenderType.cutout());
            }

            if (setBuilder.furnitureBlocks != null){
                ItemBlockRenderTypes.setRenderLayer(setBuilder.furnitureBlocks.chair(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(setBuilder.furnitureBlocks.ladder(), RenderType.cutout());
            }
        });

        ItemBlockRenderTypes.setRenderLayer(ResourceItemsME.REEDS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.TALL_CATTAILS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.TALL_BULRUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.HOGWEED, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.SHORT_HOGWEED, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WATERING_CAN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WOODEN_BUCKET, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BROWN_JUG, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.LARGE_JUG, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.AMPHORA, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BROWN_AMPHORA, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BROWN_JAR, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BROWN_FAT_POT, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.FAT_POT, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CANDLESTICK, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CERAMIC_LAMP, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CANDLE_HOLDER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SKULL_CANDLE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CANDLE_HEAP, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BIG_BRAZIER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SMALL_BRAZIER, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.GILDED_BIG_BRAZIER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.GILDED_SMALL_BRAZIER, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.FIRE_BOWL, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.TORCH_OF_ORTHANC, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BONFIRE, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.SCONCE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.GILDED_SCONCE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ORCISH_SCONCE, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_SCONCE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.GILDED_WALL_SCONCE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ORCISH_WALL_SCONCE, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ORCISH_ARTISAN_TABLE, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.STONE_LECTERN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.CHISELED_DOLOMITE_BOOKSHELF, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BASALT_STATUE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.DEEPSLATE_STATUE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.PUMICE_STATUE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.GABBRO_STATUE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.GALONN_STATUE, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ARKENSTONE, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.WALL_ARKENSTONE, RenderType.translucent());


        SimpleWoodChairModel.vanillaChairs.forEach(block -> {
            ItemBlockRenderTypes.setRenderLayer(block.base(), RenderType.cutout());
        });

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.BELLOWS, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TREATED_WOOD_ROPE_FENCE, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.STRUCTURE_MANAGER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.ORC_STRUCTURE_MANAGER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModDecorativeBlocks.STRUCTURE_NEST, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.MIRKWOOD_VINES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.WILLOW_VINES, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.GLOWWORM_MAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModNatureBlocks.GLOWWORM_WEBBING, RenderType.cutout());
    }
}
