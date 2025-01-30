package net.sevenstars.middleearth.entity.model;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollModel;
import net.sevenstars.middleearth.entity.snail.SnailEntityModel;
import net.sevenstars.middleearth.entity.spider.MirkwoodSpiderModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;

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
        builder.put(ModEntityModelLayers.BELLOWS, BellowsBlockEntityRenderer.getTexturedModelData());

        // Animals
        builder.put(ModEntityModelLayers.SNAIL, SnailEntityModel.getTexturedModelData());

        ImmutableMap<EntityModelLayer, TexturedModelData> immutableMap = builder.build();
        return immutableMap;
    }
}
