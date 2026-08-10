package net.sevenstars.middleearth.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class BlackCastellanChestplateModel extends ChestplateAddonModel {

    public BlackCastellanChestplateModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        body.addChild("chest_extension", ModelPartBuilder.create()
                .uv(42, 39).cuboid(-4.5F, -1.125F, -2.3F, 9.0F, 12.0F, 0.0F, new Dilation(0.51F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        right_arm.addChild("right_halt", ModelPartBuilder.create()
                .uv(0, 32).cuboid(-1.0F, -6.25F, -3.5F, 0.0F, 7.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        left_arm.addChild("left_halt", ModelPartBuilder.create()
                .uv(0, 39).cuboid(11.0F, -6.5F, -3.5F, 0.0F, 7.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.origin(-10.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}