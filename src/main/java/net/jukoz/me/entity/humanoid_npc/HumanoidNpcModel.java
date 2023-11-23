package net.jukoz.me.entity.humanoid_npc;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.Direction;
import java.util.Set;

public abstract class HumanoidNpcModel {
    public static final String LEFT_SLEEVE = "left_sleeve";
    public static final String RIGHT_SLEEVE = "right_sleeve";
    public static final String LEFT_PANTS = "left_leg";
    public static final String RIGHT_PANTS = "right_leg";
    public static final String LEFT_EYEBROW = "left_eyebrow";
    public static final String RIGHT_EYEBROW = "right_eyebrow";

    public static final String FACE_IDLE = "face_idle";
    public static final String FACE_DISCUSSING = "face_discussing";
    public static final String FACE_FEARFUL = "face_fearful";
    public static final String FACE_SLEEPING = "face_sleeping";
    public static final String FACE_HURT = "face_hurt";


    public static ModelData getModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(8, 8)
                        .cuboid(-1.0f, 0f, -1.0f, 2.0f, 2.0f, 2.0f, dilation),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-4.0f, -8.0f, -4.1f, 8.0f, 8.0f, 8.0f,
                                Set.of(Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN)
                        ),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        head.addChild(FACE_IDLE, ModelPartBuilder.create().uv(HumanoidNpcEmotionStates.Idle.getXOffset(), 64)
                        .cuboid(0, 0, 0, 8.0f, 8.0f, 0f,
                                Set.of(Direction.NORTH)
                        ).mirrored(),
                ModelTransform.pivot(-4.0f, -8.0f, -4.1f));

        head.addChild(FACE_DISCUSSING, ModelPartBuilder.create().uv(HumanoidNpcEmotionStates.Discussing.getXOffset(), 64)
                        .cuboid(0, 0, 0, 8.0f, 8.0f, 0f,
                                Set.of(Direction.NORTH)
                        ).mirrored(),
                ModelTransform.pivot(-4.0f, -8.0f, -4.1f));

        head.addChild(FACE_FEARFUL, ModelPartBuilder.create().uv(HumanoidNpcEmotionStates.Fearful.getXOffset(), 64)
                        .cuboid(0, 0, 0, 8.0f, 8.0f, 0f,
                                Set.of(Direction.NORTH)
                        ).mirrored(),
                ModelTransform.pivot(-4.0f, -8.0f, -4.1f));

        head.addChild(FACE_SLEEPING, ModelPartBuilder.create().uv(HumanoidNpcEmotionStates.Sleeping.getXOffset(), 64)
                        .cuboid(0, 0, 0, 8.0f, 8.0f, 0f,
                                Set.of(Direction.NORTH)
                        ).mirrored(),
                ModelTransform.pivot(-4.0f, -8.0f, -4.1f));

        head.addChild(FACE_HURT, ModelPartBuilder.create().uv(HumanoidNpcEmotionStates.Hurt.getXOffset(), 64)
                        .cuboid(0, 0, 0, 8.0f, 8.0f, 0f,
                                Set.of(Direction.NORTH)
                        ).mirrored(),
                ModelTransform.pivot(-4.0f, -8.0f, -4.1f));

        ModelPartData leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(32, 48)
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(17.0f, 2.5f, 0.0f));

        ModelPartData rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16)
                        .cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
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
        int eyebrowTextureY = 0;
        float eyebrowOffset = -4.25f;

        head.addChild(RIGHT_EYEBROW, ModelPartBuilder.create().uv(0,eyebrowTextureY).mirrored()
                        .cuboid(-1f, -0.5f, 0, 2.0f, 1.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(2f, -4f, eyebrowOffset));

        head.addChild(LEFT_EYEBROW, ModelPartBuilder.create().uv(0,eyebrowTextureY).mirrored()
                        .cuboid(-1f, -0.5f, 0f, 2.0f, 1.0f, 0.0f,
                                Set.of(Direction.NORTH)
                        ),
                ModelTransform.pivot(-2f, -4f, eyebrowOffset));

        return modelData;
    }
}
