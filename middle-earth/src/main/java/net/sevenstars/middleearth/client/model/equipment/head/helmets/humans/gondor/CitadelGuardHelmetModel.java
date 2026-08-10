package net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.gondor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.EggHelmetModel;

public class CitadelGuardHelmetModel extends EggHelmetModel {

    public CitadelGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        //+0.75
        ModelPartData head = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("side_left", ModelPartBuilder.create()
                .uv(40, 32).cuboid(-0.5F, -9.75F, 0.284F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(5.0F, -6.0F, -2.5F, 0.0F, -1.3963F, 0.0F));
        head.addChild("side_right", ModelPartBuilder.create()
                .uv(52, 32).cuboid(-5.25F, -9.75F, 0.252F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(-5.0F, -6.0F, -2.5F, 0.0F, 1.3963F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}