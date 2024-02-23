package net.jukoz.me.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.balrog.BalrogModel;
import net.jukoz.me.entity.barrow_wights.BarrowWightModel;
import net.jukoz.me.entity.beasts.trolls.petrified.PetrifiedTrollModel;
import net.jukoz.me.entity.beasts.trolls.stone.StoneTrollModel;
import net.jukoz.me.entity.crab.CrabModel;
import net.jukoz.me.entity.duck.DuckModel;
import net.jukoz.me.entity.goose.GooseModel;
import net.jukoz.me.entity.nazguls.NazgulModel;
import net.jukoz.me.entity.snail.SnailModel;
import net.jukoz.me.entity.spider.MirkwoodSpiderModel;
import net.jukoz.me.entity.swan.SwanModel;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollModel;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public final class ModEntityModelLayers {
    private static final String MAIN = "main";
    public static final EntityModelLayer DWARF = ModEntityModelLayers.registerEntityModelLayer("dwarf", DwarfModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer ELF = ModEntityModelLayers.registerEntityModelLayer("elf", ElfModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer HOBBIT = ModEntityModelLayers.registerEntityModelLayer("hobbit", HobbitModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer NAZGUL = ModEntityModelLayers.registerEntityModelLayer("nazgul", NazgulModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer ORC = ModEntityModelLayers.registerEntityModelLayer("orc", OrcModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer CAVE_TROLL = ModEntityModelLayers.registerEntityModelLayer("cave_troll", CaveTrollModel.getTexturedModelData());
    public static final EntityModelLayer SNOW_TROLL = ModEntityModelLayers.registerEntityModelLayer("snow_troll", SnowTrollModel.getTexturedModelData());
    public static final EntityModelLayer STONE_TROLL = ModEntityModelLayers.registerEntityModelLayer("stone_troll", StoneTrollModel.getTexturedModelData());
    public static final EntityModelLayer PETRIFIED_TROLL = ModEntityModelLayers.registerEntityModelLayer("petrified_troll", PetrifiedTrollModel.getTexturedModelData());
    public static final EntityModelLayer BARROW_WIGHT = ModEntityModelLayers.registerEntityModelLayer("barrow_wight", BarrowWightModel.getTexturedModelData());
    public static final EntityModelLayer SPIDER = ModEntityModelLayers.registerEntityModelLayer("spider", MirkwoodSpiderModel.getTexturedModelData());
    public static final EntityModelLayer BALROG = ModEntityModelLayers.registerEntityModelLayer("balrog", BalrogModel.getTexturedModelData());


    // Animals
    public static final EntityModelLayer CRAB = ModEntityModelLayers.registerEntityModelLayer("crab", CrabModel.getTexturedModelData());
    public static final EntityModelLayer GOOSE = ModEntityModelLayers.registerEntityModelLayer("goose", GooseModel.getTexturedModelData());
    public static final EntityModelLayer DUCK = ModEntityModelLayers.registerEntityModelLayer("duck", DuckModel.getTexturedModelData());
    public static final EntityModelLayer SWAN = ModEntityModelLayers.registerEntityModelLayer("swan", SwanModel.getTexturedModelData());
    public static final EntityModelLayer SNAIL = ModEntityModelLayers.registerEntityModelLayer("snail", SnailModel.getTexturedModelData());


    /**
     * The modelData is used to know the UV map to use for the 3D model
     * **/
    private static EntityModelLayer registerEntityModelLayer(String registryName, TexturedModelData modelData) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(new Identifier(MiddleEarth.MOD_ID, registryName), MAIN);
        EntityModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }
}
