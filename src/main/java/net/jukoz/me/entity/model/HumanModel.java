package net.jukoz.me.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class HumanModel {
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_leg";
    private static final String RIGHT_PANTS = "right_leg";

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(8, 8)
                .cuboid(-1.0f, 0f, -1.0f, 2.0f, 2.0f, 2.0f, dilation),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));


        root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0)
                .cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        ModelPartData leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(32, 48)
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(17.0f, 2.5f, 0.0f));
        ModelPartData rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16)
                        .cuboid(-2.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(-7.0f, 2.5f, 0.0f));

        rightArm.addChild(RIGHT_SLEEVE, ModelPartBuilder.create().uv(40, 32).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(-2f, -2f, -2f));
        leftArm.addChild(LEFT_SLEEVE, ModelPartBuilder.create().uv(48, 48).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(-1f, -2f, -2f));

        root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16)
                .cuboid(-4.0f, 0.0f, -2f, 8.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(0.0f, 0f, 0.0f));

        root.getChild(EntityModelPartNames.BODY).addChild(EntityModelPartNames.JACKET, ModelPartBuilder.create().uv(16, 32)
                .cuboid(-4.0f, 0.0f, -2f, 8.0f, 12.0f, 4.0f, dilation.add(0.25f)), ModelTransform.NONE);

        root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16)
                .cuboid(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(-1.9f, 0.0f, 0.0f));
        root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored()
                .cuboid(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(1.9f, 0.0f, 0.0f));


        root.getChild(EntityModelPartNames.LEFT_LEG).addChild(LEFT_PANTS, ModelPartBuilder.create().uv(0, 48)
                        .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(0, 0.0f, 0.0f));
        root.getChild(EntityModelPartNames.RIGHT_LEG).addChild(RIGHT_PANTS, ModelPartBuilder.create().uv(0, 32)
                        .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(0, 0.0f, 0.0f));

        root.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.5f)),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));                // 54

        return TexturedModelData.of(modelData, 64, 64);
    }
}
