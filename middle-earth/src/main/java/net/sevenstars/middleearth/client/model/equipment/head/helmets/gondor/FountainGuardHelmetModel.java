package net.sevenstars.middleearth.client.model.equipment.head.helmets.gondor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.EggHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class FountainGuardHelmetModel extends EggHelmetModel {

    public FountainGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        //+0.75
        modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()
                        .uv(29, 0).cuboid(-3.0F, -15.35F, -4.35F, 6.0F, 12.0F, 0.0F, new Dilation(0.5F))
                        .uv(0, 15).mirrored().cuboid(-4.0F, -7.75F, -4.314F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                        .uv(50, 0).mirrored().cuboid(2.0F, -19.55F, 0.0F, 7.0F, 16.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                        .uv(50, 16).cuboid(-9.0F, -19.55F, 0.0F, 7.0F, 16.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));


        return TexturedModelData.of(modelData, 64, 64);
    }
}