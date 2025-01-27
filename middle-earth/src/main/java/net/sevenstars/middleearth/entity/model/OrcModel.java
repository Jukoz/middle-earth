package net.sevenstars.middleearth.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class OrcModel {
    private static final String LEFT_EAR = "left_ear";
    private static final String RIGHT_EAR = "right_ear";
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_leg";
    private static final String RIGHT_PANTS = "right_leg";

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0f);
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData rightEar = modelPartData.getChild(EntityModelPartNames.HEAD).addChild(RIGHT_EAR, ModelPartBuilder.create().uv(1, 1).cuboid(0, -0, -1f, 1.0f, 3.0f, 2.0f, dilation),
                ModelTransform.of(-5f, -5f, 0.25f,-0.7f, 0, -0.35f));
        ModelPartData leftEar = modelPartData.getChild(EntityModelPartNames.HEAD).addChild(LEFT_EAR, ModelPartBuilder.create().uv(1, 1).cuboid(0, -0, -1f, 1.0f, 3.0f, 2.0f, dilation),
                ModelTransform.of(4f, -5f, 0.25f,-0.7f, 0, 0.35f));

        ModelPartData leftArm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(32, 48)
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(17.0f, 2.5f, 0.0f));
        ModelPartData rightArm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16)
                        .cuboid(-2.5f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(-7.0f, 2.5f, 0.0f));

        rightArm.addChild(RIGHT_SLEEVE, ModelPartBuilder.create().uv(40, 32).cuboid(0f, 0f, 0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(-2.5f, -2f, -2f));
        leftArm.addChild(LEFT_SLEEVE, ModelPartBuilder.create().uv(48, 48).cuboid(0f, 0f, 0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(-1f, -2f, -2f));

        modelPartData.getChild(EntityModelPartNames.BODY).addChild(EntityModelPartNames.JACKET, ModelPartBuilder.create().uv(16, 32).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation.add(0.25f)), ModelTransform.NONE);
        modelPartData.getChild(EntityModelPartNames.LEFT_LEG).addChild(LEFT_PANTS, ModelPartBuilder.create().uv(0, 48).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(0, 0.0f, 0.0f));
        modelPartData.getChild(EntityModelPartNames.RIGHT_LEG).addChild(RIGHT_PANTS, ModelPartBuilder.create().uv(0, 32).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(0, 0.0f, 0.0f));

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.25f)),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));                // 54

        return TexturedModelData.of(modelData, 64, 64);
    }
}
