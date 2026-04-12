package net.sevenstars.middleearth.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.entity.barrel.BarrelEntityModel;
import net.sevenstars.middleearth.entity.barrow_wights.BarrowWightModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatArmorModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatSaddleModel;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollSaddleModel;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornArmorModel;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornSaddleModel;
import net.sevenstars.middleearth.entity.beasts.trolls.petrified.PetrifiedTrollModel;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollModel;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollModel;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.beasts.warg.features.*;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.renderer.features.ear.EarModel;
import net.sevenstars.middleearth.entity.npcs.renderer.features.feet.FeetModel;
import net.sevenstars.middleearth.entity.npcs.renderer.features.hair.HairModel;
import net.sevenstars.middleearth.entity.npcs.renderer.features.nose.NoseModel;
import net.sevenstars.middleearth.entity.spider.EnwebbedModel;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaModel;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerModel;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobModel;

@Environment(value= EnvType.CLIENT)
public final class ModEntityModelLayers {
    private static final String MAIN = "main";
    // region NPC
    public static final EntityModelLayer NPC = registerEntityModelLayer("npc", NpcEntityModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer NPC_ENTITY_HAIR = registerEntityModelLayer("npc_entity_hair",  HairModel.getTexturedModelData());
    public static final EntityModelLayer NPC_ENTITY_EAR = registerEntityModelLayer("npc_entity_ear",  EarModel.getTexturedModelData());
    public static final EntityModelLayer NPC_ENTITY_NOSE = registerEntityModelLayer("npc_entity_nose",  NoseModel.getTexturedModelData());
    public static final EntityModelLayer NPC_ENTITY_FEET = registerEntityModelLayer("npc_entity_feet",  FeetModel.getTexturedModelData());

    // endregion
    public static final EntityModelLayer BROADHOOF_GOAT = ModEntityModelLayers.registerEntityModelLayer("broadhoof_goat", BroadhoofGoatModel.getTexturedModelData());
    public static final EntityModelLayer BROADHOOF_GOAT_ARMOR = ModEntityModelLayers.registerEntityModelLayer("broadhoof_goat_armor", BroadhoofGoatArmorModel.getTexturedModelData());
    public static final EntityModelLayer BROADHOOF_GOAT_SADDLE = ModEntityModelLayers.registerEntityModelLayer("broadhoof_goat_saddle", BroadhoofGoatSaddleModel.getTexturedModelData());
    public static final EntityModelLayer GREAT_HORN = ModEntityModelLayers.registerEntityModelLayer("great_horn", GreatHornModel.getTexturedModelData());
    public static final EntityModelLayer GREAT_HORN_BABY = ModEntityModelLayers.registerEntityModelLayer("great_horn_baby", GreatHornModel.getTexturedModelData().transform(GreatHornModel.BABY_TRANSFORMER));
    public static final EntityModelLayer GREAT_HORN_SADDLE = ModEntityModelLayers.registerEntityModelLayer("great_horn_saddle", GreatHornSaddleModel.getTexturedModelData());
    public static final EntityModelLayer GREAT_HORN_ARMOR = ModEntityModelLayers.registerEntityModelLayer("great_horn_armor", GreatHornArmorModel.getTexturedModelData());
    public static final EntityModelLayer WARG = ModEntityModelLayers.registerEntityModelLayer("warg", WargModel.getTexturedModelData());
    public static final EntityModelLayer WARG_ARMOR = ModEntityModelLayers.registerEntityModelLayer("warg_armor", WargArmorModel.getTexturedModelData());
    public static final EntityModelLayer WARG_ARMOR_ADDONS_FRONT = ModEntityModelLayers.registerEntityModelLayer("warg_armor_addons_front", WargArmorTopAddonsModel.getTexturedModelDataFront());
    public static final EntityModelLayer WARG_ARMOR_ADDONS_BACK = ModEntityModelLayers.registerEntityModelLayer("warg_armor_addons_back", WargArmorTopAddonsModel.getTexturedModelDataBack());
    public static final EntityModelLayer WARG_ARMOR_ADDONS_SPINE = ModEntityModelLayers.registerEntityModelLayer("warg_armor_addons_spine", WargArmorBaseAddonsModel.getTexturedModelDataSpine());
    public static final EntityModelLayer WARG_ARMOR_ADDONS_SIDE_SKULL = ModEntityModelLayers.registerEntityModelLayer("warg_armor_addons_side_skull", WargArmorSideAddonsModel.getTexturedModelDataSideSkulls());
    public static final EntityModelLayer WARG_SADDLE = ModEntityModelLayers.registerEntityModelLayer("warg_saddle", WargSaddleModel.getTexturedModelData());
    public static final EntityModelLayer SNOW_TROLL = ModEntityModelLayers.registerEntityModelLayer("snow_troll", SnowTrollModel.getTexturedModelData());
    public static final EntityModelLayer CAVE_TROLL = ModEntityModelLayers.registerEntityModelLayer("cave_troll", CaveTrollEntityModel.getTexturedModelData());
    public static final EntityModelLayer CAVE_TROLL_SADDLE = ModEntityModelLayers.registerEntityModelLayer("cave_troll_sddle", CaveTrollSaddleModel.getTexturedModelData());
    public static final EntityModelLayer STONE_TROLL = ModEntityModelLayers.registerEntityModelLayer("stone_troll", StoneTrollModel.getTexturedModelData());
    public static final EntityModelLayer PETRIFIED_TROLL = ModEntityModelLayers.registerEntityModelLayer("petrified_troll", PetrifiedTrollModel.getTexturedModelData());
    public static final EntityModelLayer BARROW_WIGHT = ModEntityModelLayers.registerEntityModelLayer("barrow_wight", BarrowWightModel.getTexturedModelData());
    public static final EntityModelLayer SHELOBITE_LARVA = ModEntityModelLayers.registerEntityModelLayer("shelobite_larva", ShelobiteLarvaModel.getTexturedModelData());
    public static final EntityModelLayer SHELOBITE_SCUTTLER = ModEntityModelLayers.registerEntityModelLayer("shelobite_scuttler", ShelobiteScuttlerModel.getTexturedModelData());
    public static final EntityModelLayer SPAWN_OF_SHELOB = ModEntityModelLayers.registerEntityModelLayer("spawn_of_shelob", SpawnOfShelobModel.getTexturedModelData());

    public static final EntityModelLayer ENWEBBED = ModEntityModelLayers.registerEntityModelLayer("enwebbed", EnwebbedModel.getTexturedModelData());

    // Blocks
    public static final EntityModelLayer BELLOWS = ModEntityModelLayers.registerEntityModelLayer("bellows", BellowsBlockEntityRenderer.getTexturedModelData());
    public static final EntityModelLayer REINFORCED_CHEST = ModEntityModelLayers.registerEntityModelLayer("reinforced_chest", ReinforcedChestEntityRenderer.getTexturedModelData());

    public static final EntityModelLayer REINFORCED_BARREL = ModEntityModelLayers.registerEntityModelLayer("reinforced_barrel", BarrelEntityModel.getTexturedModelData());
    public static final EntityModelLayer REINFORCED_BARREL_WATER_MASK = ModEntityModelLayers.registerEntityModelLayer("reinforced_barrel_water_mask", BarrelEntityModel.getBaseTexturedModelData());


    /**
     * The modelData is used to know the UV map to use for the 3D model
     * **/
    private static EntityModelLayer registerEntityModelLayer(String registryName, TexturedModelData modelData) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, registryName), MAIN);
        EntityModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }

    /**
     * The modelData is used to know the UV map to use for the 3D model
     * **/
    private static EntityModelLayer registerEntityModelLayer(String id, String layer, TexturedModelData modelData) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, id), layer);
        EntityModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }
}
