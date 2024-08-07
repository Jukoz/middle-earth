package net.jukoz.me.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.Entity;

public class DwarfModel {
    public static final String NOSE = "nose";
    public static final String BEARD = "beard";
    public static final String BEARD2 = "beard2";
    public static final String BEARD_TIP = "beard_tip";
    public static final String NORI_BEARD_CENTER = "nori_beard_center";
    public static final String NORI_BEARD_LEFT = "nori_beard_left";
    public static final String NORI_BEARD_RIGHT = "nori_beard_right";
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    public static final float BEARD_PITCH_ANGLE = -0.174f;

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
            ModelPartData modelPartData = modelData.getRoot();
            ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder
                    .create().uv(0, 0)
                    .cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

            head.addChild(NOSE, ModelPartBuilder.create().uv(0, 5).cuboid(-1f, 0, 0.25f, 2.0f, 2.0f, 1.0f, dilation),
                    ModelTransform.of(0f, -4.0f, -5.0f, 0, 0, 0));

            head.addChild(BEARD, ModelPartBuilder.create().uv(56, 25).cuboid(0, 0, -4f, 7.0f, 5.0f, 2.0f, dilation),
                    ModelTransform.of(-3.5f, 0.67f, 0.0f, BEARD_PITCH_ANGLE, 0, 0));
            head.addChild(BEARD2, ModelPartBuilder.create().uv(57, 26).cuboid(0, 0, -6f, 5.0f, 3.0f, 1.0f, dilation),
                    ModelTransform.of(-2.5f, 1f, 1.05f, BEARD_PITCH_ANGLE, 0, 0));
            head.addChild(BEARD_TIP, ModelPartBuilder.create().uv(57, 26).cuboid(0, 0, -5f, 5.0f, 2.0f, 1.0f, dilation),
                    ModelTransform.of(-2.5f, 5.99f, 1f, BEARD_PITCH_ANGLE, 0, 0));

            head.addChild(NORI_BEARD_CENTER, ModelPartBuilder.create().uv(64, 0).cuboid(-1, 1.5f, -4.5f, 2.0f, 5.0f, 2.0f, dilation.add(0.15f)),
                    ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE - 0.2f, 0, 0));
            head.addChild(NORI_BEARD_LEFT, ModelPartBuilder.create().uv(64, 0).cuboid(1f, 0.7f, -4f, 2.0f, 5.0f, 2.0f, dilation),
                    ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE, 0, -0.55f));
            head.addChild(NORI_BEARD_RIGHT, ModelPartBuilder.create().uv(64, 0).cuboid(-3f, 0.7f, -4f, 2.0f, 5.0f, 2.0f, dilation),
                    ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE, 0, 0.55f));

            modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.5f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
            modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

            ModelPartData rightArm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create()
                    .uv(40, 16).cuboid(-3.0f, -2.5f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
            rightArm.addChild(RIGHT_SLEEVE, ModelPartBuilder.create().uv(40, 32).cuboid(0f, 0f, 0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)), ModelTransform.pivot(-3f, -2f, -2f));

            ModelPartData leftArm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create()
                    .uv(40, 16).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
            leftArm.addChild(LEFT_SLEEVE, ModelPartBuilder.create().uv(48, 48).cuboid(0f, 0f, 0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)), ModelTransform.pivot(-1f, -2f, -2f));

            modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
            modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(1.9f, 12.0f, 0.0f));

            return TexturedModelData.of(modelData, 82, 64);
    }
}
