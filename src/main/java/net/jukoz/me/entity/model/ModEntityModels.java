package net.jukoz.me.entity.model;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.jukoz.me.entity.snail.SnailModel;
import net.jukoz.me.entity.spider.MirkwoodSpiderModel;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class ModEntityModels {
    public static Map<EntityModelLayer, TexturedModelData> getModels() {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();

        builder.put(ModEntityModelLayers.HUMAN, HumanModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.DWARF, DwarfModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.ELF, ElfModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.HOBBIT, HobbitModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.ORC, OrcModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.URUK, UrukModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.SNOW_TROLL, SnowTrollModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.SPIDER, MirkwoodSpiderModel.getTexturedModelData());

        builder.put(ModEntityModelLayers.REINFORCED_CHEST, ReinforcedChestEntityRenderer.getSingleTexturedModelData());

        // Animals
        builder.put(ModEntityModelLayers.SNAIL, SnailModel.getTexturedModelData());

        ImmutableMap<EntityModelLayer, TexturedModelData> immutableMap = builder.build();
        return immutableMap;
    }
}
