package net.jesteur.me.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.dwarves.durin.DurinDwarfModel;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public final class ModEntityModelLayers {
    private static final String MAIN = "main";
    public static final EntityModelLayer DWARF = ModEntityModelLayers.registerEntityModelLayer("dwarf", DwarfModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer ELF = ModEntityModelLayers.registerEntityModelLayer("elf", ElfModel.getTexturedModelData(Dilation.NONE));
    public static final EntityModelLayer ORC = ModEntityModelLayers.registerEntityModelLayer("orc", OrcModel.getTexturedModelData(Dilation.NONE));


    /**
     * The modelData is used to know the UV map to use for the 3D model
     * **/
    private static EntityModelLayer registerEntityModelLayer(String registryName, TexturedModelData modelData) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(new Identifier(MiddleEarth.MOD_ID, registryName), MAIN);
        EntityModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }
}
