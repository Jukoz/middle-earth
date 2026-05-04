package net.sevenstars.middleearth.entity;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.coffers.FirCofferEntityRenderer;
import net.sevenstars.middleearth.block.special.coffers.LarchCofferEntityRenderer;
import net.sevenstars.middleearth.block.special.coffers.PineCofferEntityRenderer;
import net.sevenstars.middleearth.block.special.coffers.SpruceCofferEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.entity.barrel.BarrelEntityModel;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollModel;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.renderer.features.ear.EarModel;
import net.sevenstars.middleearth.entity.npcs.renderer.features.feet.FeetModel;
import net.sevenstars.middleearth.entity.npcs.renderer.features.hair.HairModel;
import net.sevenstars.middleearth.entity.npcs.renderer.features.nose.NoseModel;
import net.sevenstars.middleearth.entity.spider.EnwebbedModel;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaModel;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobModel;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class EntityModelsME {
    public static Map<EntityModelLayer, TexturedModelData> getModels() {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();

        builder.put(EntityModelLayersME.NPC, NpcEntityModel.getTexturedModelData(Dilation.NONE));
        builder.put(EntityModelLayersME.NPC_ENTITY_HAIR, HairModel.getTexturedModelData());
        builder.put(EntityModelLayersME.NPC_ENTITY_EAR, EarModel.getTexturedModelData());
        builder.put(EntityModelLayersME.NPC_ENTITY_NOSE, NoseModel.getTexturedModelData());
        builder.put(EntityModelLayersME.NPC_ENTITY_FEET, FeetModel.getTexturedModelData());

        builder.put(EntityModelLayersME.SNOW_TROLL, SnowTrollModel.getTexturedModelData());
        builder.put(EntityModelLayersME.SHELOBITE_LARVA, ShelobiteLarvaModel.getTexturedModelData());
        builder.put(EntityModelLayersME.SHELOBITE_SCUTTLER, ShelobiteScuttlerModel.getTexturedModelData());
        builder.put(EntityModelLayersME.SPAWN_OF_SHELOB, SpawnOfShelobModel.getTexturedModelData());

        builder.put(EntityModelLayersME.ENWEBBED, EnwebbedModel.getTexturedModelData());

        builder.put(EntityModelLayersME.REINFORCED_CHEST, ReinforcedChestEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.BELLOWS, BellowsBlockEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.LARCH_COFFER, LarchCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.PINE_COFFER, PineCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.SPRUCE_COFFER, SpruceCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.FIR_COFFER, FirCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.REINFORCED_BARREL, BarrelEntityModel.getTexturedModelData());
        builder.put(EntityModelLayersME.REINFORCED_BARREL_WATER_MASK, BarrelEntityModel.getBaseTexturedModelData());

        ImmutableMap<EntityModelLayer, TexturedModelData> immutableMap = builder.build();
        return immutableMap;
    }
}
