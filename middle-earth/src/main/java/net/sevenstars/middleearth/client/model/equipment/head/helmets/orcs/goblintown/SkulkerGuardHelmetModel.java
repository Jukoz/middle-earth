package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.goblintown;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.EggHelmetModel;

public class SkulkerGuardHelmetModel extends EggHelmetModel {

    public SkulkerGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData head = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("large_crest", ModelPartBuilder.create()
                        .uv(28, -18).cuboid(0.0F, -15.25F, -7.0F, 0.0F, 14.0F, 18.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("mandible_left", ModelPartBuilder.create()
                        .uv(2, 15).cuboid(0.0F, -8.25F, -6.0F, 0.0F, 9.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(4.0F, 2.0F, -4.0F, 0.0F, -0.4363F, 0.0F));
        head.addChild("mandible_right", ModelPartBuilder.create()
                        .uv(2, 15).cuboid(0.0F, -8.25F, -6.0F, 0.0F, 9.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(-4.0F, 2.0F, -4.0F, 0.0F, 0.4363F, 0.0F));


        return TexturedModelData.of(modelData, 64, 64);
    }
}