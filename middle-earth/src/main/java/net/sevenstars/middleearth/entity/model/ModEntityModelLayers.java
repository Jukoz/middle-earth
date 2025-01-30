package net.sevenstars.middleearth.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.entity.barrow_wights.BarrowWightModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatArmorModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatSaddleModel;
import net.sevenstars.middleearth.entity.beasts.trolls.petrified.PetrifiedTrollModel;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollModel;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollModel;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.beasts.warg.features.*;
import net.sevenstars.middleearth.entity.deer.DeerModel;
import net.sevenstars.middleearth.entity.pheasant.PheasantModel;
import net.sevenstars.middleearth.entity.snail.SnailEntityModel;
import net.sevenstars.middleearth.entity.spider.MirkwoodSpiderModel;
import net.sevenstars.middleearth.entity.swan.SwanModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public final class ModEntityModelLayers {
    private static final String MAIN = "main";
    public static final EntityModelLayer DWARF = ModEntityModelLayers.registerEntityModelLayer("dwarf", DwarfModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer HUMAN = ModEntityModelLayers.registerEntityModelLayer("human", HumanModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer ELF = ModEntityModelLayers.registerEntityModelLayer("elf", ElfModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer HOBBIT = ModEntityModelLayers.registerEntityModelLayer("hobbit", HobbitModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer ORC = ModEntityModelLayers.registerEntityModelLayer("orc", OrcModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer URUK = ModEntityModelLayers.registerEntityModelLayer("uruk", UrukModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer BROADHOOF_GOAT = ModEntityModelLayers.registerEntityModelLayer("broadhoof_goat", BroadhoofGoatModel.getTexturedModelData());
    public static final EntityModelLayer BROADHOOF_GOAT_ARMOR = ModEntityModelLayers.registerEntityModelLayer("broadhoof_goat_armor", BroadhoofGoatArmorModel.getTexturedModelData());
    public static final EntityModelLayer BROADHOOF_GOAT_SADDLE = ModEntityModelLayers.registerEntityModelLayer("broadhoof_goat_saddle", BroadhoofGoatSaddleModel.getTexturedModelData());
    public static final EntityModelLayer WARG = ModEntityModelLayers.registerEntityModelLayer("warg", WargModel.getTexturedModelData());
    public static final EntityModelLayer WARG_ARMOR = ModEntityModelLayers.registerEntityModelLayer("warg_armor", WargArmorModel.getTexturedModelData());
    public static final EntityModelLayer WARG_ARMOR_ADDONS_FRONT = ModEntityModelLayers.registerEntityModelLayer("warg_armor_addons_front", WargArmorTopAddonsModel.getTexturedModelDataFront());
    public static final EntityModelLayer WARG_ARMOR_ADDONS_BACK = ModEntityModelLayers.registerEntityModelLayer("warg_armor_addons_back", WargArmorTopAddonsModel.getTexturedModelDataBack());
    public static final EntityModelLayer WARG_ARMOR_ADDONS_SPINE = ModEntityModelLayers.registerEntityModelLayer("warg_armor_addons_spine", WargArmorBaseAddonsModel.getTexturedModelDataSpine());
    public static final EntityModelLayer WARG_ARMOR_ADDONS_SIDE_SKULL = ModEntityModelLayers.registerEntityModelLayer("warg_armor_addons_side_skull", WargArmorSideAddonsModel.getTexturedModelDataSideSkulls());
    public static final EntityModelLayer WARG_SADDLE = ModEntityModelLayers.registerEntityModelLayer("warg_saddle", WargSaddleModel.getTexturedModelData());
    public static final EntityModelLayer SNOW_TROLL = ModEntityModelLayers.registerEntityModelLayer("snow_troll", SnowTrollModel.getTexturedModelData());
    public static final EntityModelLayer STONE_TROLL = ModEntityModelLayers.registerEntityModelLayer("stone_troll", StoneTrollModel.getTexturedModelData());
    public static final EntityModelLayer PETRIFIED_TROLL = ModEntityModelLayers.registerEntityModelLayer("petrified_troll", PetrifiedTrollModel.getTexturedModelData());
    public static final EntityModelLayer BARROW_WIGHT = ModEntityModelLayers.registerEntityModelLayer("barrow_wight", BarrowWightModel.getTexturedModelData());
    public static final EntityModelLayer SPIDER = ModEntityModelLayers.registerEntityModelLayer("spider", MirkwoodSpiderModel.getTexturedModelData());

    // Animals
    public static final EntityModelLayer SWAN = ModEntityModelLayers.registerEntityModelLayer("swan", SwanModel.getTexturedModelData());
    public static final EntityModelLayer PHEASANT = ModEntityModelLayers.registerEntityModelLayer("pheasant", PheasantModel.getTexturedModelData());
    public static final EntityModelLayer SNAIL = ModEntityModelLayers.registerEntityModelLayer("snail", SnailEntityModel.getTexturedModelData());
    public static final EntityModelLayer DEER = ModEntityModelLayers.registerEntityModelLayer("deer", DeerModel.getTexturedModelData());

    public static final EntityModelLayer BELLOWS = ModEntityModelLayers.registerEntityModelLayer("bellows", BellowsBlockEntityRenderer.getTexturedModelData());
    public static final EntityModelLayer REINFORCED_CHEST = ModEntityModelLayers.registerEntityModelLayer("reinforced_chest", ReinforcedChestEntityRenderer.getTexturedModelData());


    /**
     * The modelData is used to know the UV map to use for the 3D model
     * **/
    private static EntityModelLayer registerEntityModelLayer(String registryName, TexturedModelData modelData) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(Identifier.of(MiddleEarth.MOD_ID, registryName), MAIN);
        EntityModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }
}
