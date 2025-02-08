package net.sevenstars.of_beasts_and_wild_things.entity.model;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityModel;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class ModEntityModels {
    public static Map<EntityModelLayer, TexturedModelData> getModels() {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();

        // Animals
        builder.put(ModEntityModelLayers.SNAIL, SnailEntityModel.getTexturedModelData());

        ImmutableMap<EntityModelLayer, TexturedModelData> immutableMap = builder.build();
        return immutableMap;
    }
}
