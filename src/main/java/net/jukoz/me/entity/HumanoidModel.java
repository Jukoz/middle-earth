package net.jukoz.me.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.Direction;
import java.util.Set;

public abstract class HumanoidModel {
    public static final String LEFT_SLEEVE = "left_sleeve";
    public static final String RIGHT_SLEEVE = "right_sleeve";
    public static final String LEFT_PANTS = "left_leg";
    public static final String RIGHT_PANTS = "right_leg";
    public static final String RIGHT_EYE = "right_eye";
    public static final String LEFT_EYE = "left_eye";
    public static final String RIGHT_PUPILL = "right_pupill";
    public static final String LEFT_PUPILL = "left_pupill";
    public static final String RIGHT_EYEBROW = "right_eyebrow";
    public static final String LEFT_EYEBROW = "left_eyebrow";
    public static final String RIGHT_EYELID_TOP = "right_eyelid_top";
    public static final String RIGHT_EYELID_BOT = "right_eyelid_bot";
    public static final String LEFT_EYELID_TOP = "left_eyelid_top";
    public static final String LEFT_EYELID_BOT = "left_eyelid_bot";

    public static ModelData getModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(8, 8)
                        .cuboid(-1.0f, 0f, -1.0f, 2.0f, 2.0f, 2.0f, dilation),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-4.0f, -8.0f, -4.1f, 8.0f, 8.0f, 8.0f, dilation),
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
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        // Face features

        head.addChild(RIGHT_EYEBROW, ModelPartBuilder.create().uv(0,0).mirrored()
                        .cuboid(1f, -5f, -4.35f, 2.0f, 1.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        head.addChild(LEFT_EYEBROW, ModelPartBuilder.create().uv(0,0).mirrored()
                        .cuboid(-3f, -5f, -4.35f, 2.0f, 1.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));


        ModelPartData rightEye = head.addChild(RIGHT_EYE, ModelPartBuilder.create().uv(0,1).mirrored()
                        .cuboid(1f, -4f, -4.25f, 2.0f, 2.0f, 0.0f),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));


        rightEye.addChild(RIGHT_PUPILL, ModelPartBuilder.create().uv(0,3).mirrored()
                                .cuboid(-0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 0.0f,
                                        Set.of(Direction.NORTH)
                                ),
                        ModelTransform.pivot(1.5f, -4f, -4.26f));


        rightEye.addChild(RIGHT_EYELID_TOP, ModelPartBuilder.create().uv(0,5).mirrored()
                        .cuboid(1f, 0, -4.28f, 2.0f, 1.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(0.0f, -4.0f, 0.0f));

        rightEye.addChild(RIGHT_EYELID_BOT, ModelPartBuilder.create().uv(0,5).mirrored()
                        .cuboid(0f, 0f, 0f, 2.0f, 1.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(1f, -2.0f, -4.28f));


        ModelPartData left_eye = head.addChild(LEFT_EYE, ModelPartBuilder.create().uv(0,1).mirrored()
                        .cuboid(-3f, -4f, -4.25f, 2.0f, 2.0f, 0.0f),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));


        left_eye.addChild(LEFT_PUPILL, ModelPartBuilder.create().uv(0,3).mirrored()
                        .cuboid(0.0f, 0f, 0.0f, 1.0f, 2.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(-2.5f, -4f, -4.26f));


        left_eye.addChild(LEFT_EYELID_TOP, ModelPartBuilder.create().uv(0,5).mirrored()
                        .cuboid(-3f, 0, -4.28f, 2.0f, 1.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(0.0f, -4f, 0.0f));

        left_eye.addChild(LEFT_EYELID_BOT, ModelPartBuilder.create().uv(0,5).mirrored()
                        .cuboid(0, 0, 0, 2.0f, 1.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(-3.0f, -2f, -4.28f));


        return modelData;
    }
}
