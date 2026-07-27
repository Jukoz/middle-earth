package net.sevenstars.of_beasts_and_wild_things.entity.model;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityModel;

import java.util.Map;

public class EntityModelsWT {
    public static Map<ModelLayerLocation, LayerDefinition> getModels() {
        ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder = ImmutableMap.builder();

        // Animals
        builder.put(EntityModelLayersWT.SNAIL, SnailEntityModel.getTexturedModelData());
        builder.put(EntityModelLayersWT.PHEASANT, PheasantEntityModel.getTexturedModelData());
        builder.put(EntityModelLayersWT.DEER, DeerEntityModel.getTexturedModelData());

        ImmutableMap<ModelLayerLocation, LayerDefinition> immutableMap = builder.build();
        return immutableMap;
    }
}
