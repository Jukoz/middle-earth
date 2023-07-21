package net.jesteur.me.entity.model;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.entity.crab.CrabModel;
import net.jesteur.me.entity.trolls.cave.CaveTrollModel;
import net.jesteur.me.entity.trolls.snow.SnowTrollModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class ModEntityModels {
    public static Map<EntityModelLayer, TexturedModelData> getModels() {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();

        builder.put(ModEntityModelLayers.DWARF, DwarfModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.ELF, ElfModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.HOBBIT, HobbitModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.ORC, OrcModel.getTexturedModelData(Dilation.NONE));
        builder.put(ModEntityModelLayers.CAVE_TROLL, CaveTrollModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.SNOW_TROLL, SnowTrollModel.getTexturedModelData());

        // Animals
        builder.put(ModEntityModelLayers.CRAB, CrabModel.getTexturedModelData());

        ImmutableMap<EntityModelLayer, TexturedModelData> immutableMap = builder.build();
        return immutableMap;
    }
}
