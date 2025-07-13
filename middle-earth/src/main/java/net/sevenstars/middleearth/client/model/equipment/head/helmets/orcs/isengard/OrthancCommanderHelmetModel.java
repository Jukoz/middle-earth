package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class OrthancCommanderHelmetModel extends OrthancGuardHelmetModel {

    public OrthancCommanderHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        ModelPartData head = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("addons_commander", ModelPartBuilder.create()
                .uv(1, 16).cuboid(-11.0F, -18.625F, -0.7F, 22.0F, 12.0F, 1.0F, new Dilation(0.0F))
                .uv(-9, 30).cuboid(-7.5F, -1.85F, -1.0F, 15.0F, 0.0F, 9.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}