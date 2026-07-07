package net.sevenstars.middleearth.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class DGStalkerChestplateModel extends ChestplateAddonModel {

    public DGStalkerChestplateModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        body.addChild("spike_right", ModelPartBuilder.create()
                .uv(36, 0).cuboid(-10.25F, -14.5F, 0.252F, 11.0F, 19.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(-1.8F, 1.0F, 0.75F, 0.0F, 0.6545F, -0.3491F));

        body.addChild("spike_left", ModelPartBuilder.create()
                .uv(36, 0).mirrored().cuboid(-0.5F, -14.5F, 0.284F, 11.0F, 19.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(1.764F, 1.0F, 0.5F, 0.0F, -0.6545F, 0.3491F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}