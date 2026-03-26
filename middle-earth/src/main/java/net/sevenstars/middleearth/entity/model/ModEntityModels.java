package net.sevenstars.middleearth.entity.model;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.barrel.BarrelEntityModel;
import net.sevenstars.middleearth.entity.barrel.BarrelEntityRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollModel;
import net.sevenstars.middleearth.entity.npcs.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.features.ear.EarModel;
import net.sevenstars.middleearth.entity.npcs.features.hair.HairModel;
import net.sevenstars.middleearth.entity.npcs.features.nose.NoseModel;
import net.sevenstars.middleearth.entity.spider.EnwebbedModel;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaModel;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobModel;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class ModEntityModels {
    public static Map<EntityModelLayer, TexturedModelData> getModels() {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();

        builder.put(ModEntityModelLayers.NPC, NpcEntityModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.NPC_ENTITY_HAIR, HairModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.NPC_ENTITY_EAR, EarModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.NPC_ENTITY_NOSE, NoseModel.getTexturedModelData());

        builder.put(ModEntityModelLayers.SNOW_TROLL, SnowTrollModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.SHELOBITE_LARVA, ShelobiteLarvaModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.SHELOBITE_SCUTTLER, ShelobiteScuttlerModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.SPAWN_OF_SHELOB, SpawnOfShelobModel.getTexturedModelData());

        builder.put(ModEntityModelLayers.ENWEBBED, EnwebbedModel.getTexturedModelData());

        builder.put(ModEntityModelLayers.REINFORCED_CHEST, ReinforcedChestEntityRenderer.getTexturedModelData());
        builder.put(ModEntityModelLayers.BELLOWS, BellowsBlockEntityRenderer.getTexturedModelData());
        builder.put(ModEntityModelLayers.REINFORCED_BARREL, BarrelEntityModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.REINFORCED_BARREL_WATER_MASK, BarrelEntityModel.getBaseTexturedModelData());

        ImmutableMap<EntityModelLayer, TexturedModelData> immutableMap = builder.build();
        return immutableMap;
    }
}
