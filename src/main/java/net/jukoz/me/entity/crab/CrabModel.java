package net.jukoz.me.entity.crab;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class CrabModel extends EntityModel<CrabEntity> {
    private static final String ARM_RIGHT = "bone_arm_right";
    private final ModelPart armRight;
    private static final String CLAW_TOP_RIGHT = "claw_top_right";
    private final ModelPart clawTopRight;
    private static final String CLAW_BOT_RIGHT = "claw_bot_right";
    private final ModelPart clawBotRight;

    private static final String ARM_LEFT = "bone_arm_left";
    private final ModelPart armLeft;

    private static final String CLAW_TOP_LEFT = "claw_top_left";
    private final ModelPart clawTopLeft;
    private static final String CLAW_BOT_LEFT = "claw_bot_left";
    private final ModelPart clawBotLeft;

    private static final String LEG_SWIMMER_LEFT = "bone_leg_swimmer_left";
    private final ModelPart legSwimmerLeft;

    private static final String SWIMMER_LEFT = "swimmer_left";
    private final ModelPart swimmerLeft;
    private static final String LEG_SWIMMER_RIGHT = "bone_leg_swimmer_right";
    private final ModelPart legSwimmerRight;

    private static final String SWIMMER_RIGHT = "swimmer_right";
    private final ModelPart swimmerRight;

    private static final String LEG_RIGHT_FIRST = "bone_leg_right_first";
    private final ModelPart legRightFirst;
    private static final String LEG_RIGHT_SECOND = "bone_leg_right_second";
    private final ModelPart legRightSecond;
    private static final String LEG_RIGHT_THIRD = "bone_leg_right_third";
    private final ModelPart legRightThird;
    private static final String LEG_LEFT_FIRST = "bone_leg_left_first";
    private final ModelPart legLeftFirst;
    private static final String LEG_LEFT_SECOND = "bone_leg_left_second";
    private final ModelPart legLeftSecond;
    private static final String LEG_LEFT_THIRD = "bone_leg_left_third";
    private final ModelPart legLeftThird;
    private static final String EYE_RIGHT = "bone_eye_right";
    private final ModelPart eyeRight;
    private static final String EYE_LEFT = "bone_eye_left";
    private final ModelPart eyeLeft;
    private static final String BODY = "bb_main";
    private final ModelPart body;
    public CrabModel(ModelPart root) {

        this.armRight = root.getChild(ARM_RIGHT);
        this.clawTopRight = this.armRight.getChild(CLAW_TOP_RIGHT);
        this.clawBotRight = this.armRight.getChild(CLAW_BOT_RIGHT);

        this.armLeft = root.getChild(ARM_LEFT);
        this.clawTopLeft = this.armLeft.getChild(CLAW_TOP_LEFT);
        this.clawBotLeft = this.armLeft.getChild(CLAW_BOT_LEFT);

        this.legSwimmerLeft = root.getChild(LEG_SWIMMER_LEFT);
        this.swimmerLeft = this.legSwimmerLeft.getChild(SWIMMER_LEFT);

        this.legSwimmerRight = root.getChild(LEG_SWIMMER_RIGHT);
        this.swimmerRight = this.legSwimmerRight.getChild(SWIMMER_RIGHT);

        this.legRightFirst = root.getChild(LEG_RIGHT_FIRST);
        this.legRightSecond = root.getChild(LEG_RIGHT_SECOND);
        this.legRightThird = root.getChild(LEG_RIGHT_THIRD);
        this.legLeftFirst = root.getChild(LEG_LEFT_FIRST);
        this.legLeftSecond = root.getChild(LEG_LEFT_SECOND);
        this.legLeftThird = root.getChild(LEG_LEFT_THIRD);
        this.eyeRight = root.getChild(EYE_RIGHT);
        this.eyeLeft = root.getChild(EYE_LEFT);
        this.body = root.getChild(BODY);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();


        ModelPartData armRight = modelPartData
                .addChild(ARM_RIGHT, ModelPartBuilder.create().uv(20, 13).cuboid(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, 20.4F, -3.0F));

        ModelPartData clawTopRight = modelPartData.getChild(ARM_RIGHT)
                .addChild(CLAW_TOP_RIGHT, ModelPartBuilder.create().uv(16, 11).cuboid(-3F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F,  new Dilation(0.0F)),
                ModelTransform.of(-1.5F, -0.5F, 0,0, 0, 0));

        ModelPartData clawBotRight = modelPartData.getChild(ARM_RIGHT)
                .addChild(CLAW_BOT_RIGHT, ModelPartBuilder.create().uv(0, 16).cuboid(-3F, -1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)),
                        ModelTransform.of(-1.5F, 0.5F, 0,0, 0, 0));


        ModelPartData armLeft = modelPartData
                .addChild(ARM_LEFT, ModelPartBuilder.create().uv(20, 16).cuboid(.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, 20.4F, -3.0F));

        ModelPartData clawTopLeft = modelPartData.getChild(ARM_LEFT)
                .addChild(CLAW_TOP_LEFT, ModelPartBuilder.create().uv(16, 9).cuboid(.5F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F,  new Dilation(0.0F)),
                        ModelTransform.of(1.5F, -0.5F, 0,0, 0, 0));

        ModelPartData clawBotLeft = modelPartData.getChild(ARM_LEFT)
                .addChild(CLAW_BOT_LEFT, ModelPartBuilder.create().uv(8, 16).cuboid(.5F, -1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)),
                        ModelTransform.of(1.5F, 0.5F, 0,0, 0, 0));



        ModelPartData bone_leg_swimmer_left = modelPartData.addChild(LEG_SWIMMER_LEFT, ModelPartBuilder.create().uv(0, 3).cuboid(-0.5F, -0.5f, 0.5F, 1, 1, 1, new Dilation(0.0F)), ModelTransform.pivot(2F, 21.8F, 2.8F));

        ModelPartData swimmerLeft = modelPartData.getChild(LEG_SWIMMER_LEFT)
                .addChild(SWIMMER_LEFT, ModelPartBuilder.create().uv(8, 16).cuboid(0, -0.5f, 1.5F, 0, 1, 1,  new Dilation(0.0F)),
                ModelTransform.of(0, 0, 0,0, 0, 0));


        ModelPartData bone_leg_swimmer_right = modelPartData.addChild(LEG_SWIMMER_RIGHT, ModelPartBuilder.create().uv(16, 11).cuboid(-0.5F,  -0.5f, 0.5F, 1, 1, 1, new Dilation(0.0F)), ModelTransform.pivot(-2F, 21.8F, 2.8F));

        ModelPartData swimmerRight = modelPartData.getChild(LEG_SWIMMER_RIGHT)
                .addChild(SWIMMER_RIGHT, ModelPartBuilder.create().uv(8, 16).cuboid(0, -0.5f, 1.5F, 0, 1, 1,  new Dilation(0.0F)),
                ModelTransform.of(0, 0, 0,0, 0, 0));



        ModelPartData bone_leg_right_first = modelPartData.addChild(LEG_RIGHT_FIRST, ModelPartBuilder.create().uv(12, 18).cuboid(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 22.4F, -2.0F));

        ModelPartData bone_leg_right_second = modelPartData.addChild(LEG_RIGHT_SECOND, ModelPartBuilder.create().uv(8, 18).cuboid(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 22.4F, 0.0F));

        ModelPartData bone_leg_right_third = modelPartData.addChild(LEG_RIGHT_THIRD, ModelPartBuilder.create().uv(4, 18).cuboid(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 22.4F, 2.0F));


        ModelPartData bone_leg_left_first = modelPartData.addChild(LEG_LEFT_FIRST, ModelPartBuilder.create().uv(0, 9).cuboid(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 22.4F, -2.0F));

        ModelPartData bone_leg_left_second = modelPartData.addChild(LEG_LEFT_SECOND, ModelPartBuilder.create().uv(16, 16).cuboid(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 22.4F, 0.0F));

        ModelPartData bone_leg_left_third = modelPartData.addChild(LEG_LEFT_THIRD, ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 22.4F, 2.0F));


        ModelPartData bone_eye_right = modelPartData.addChild(EYE_RIGHT, ModelPartBuilder.create().uv(21, 0).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 19.3F, -2.0F));

        ModelPartData bone_eye_left = modelPartData.addChild(EYE_LEFT, ModelPartBuilder.create().uv(20, 18).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 19.3F, -2.0F));

        ModelPartData bb_main = modelPartData.addChild(BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -4.7F, -3.5F, 7.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 9).cuboid(-2.5F, -2.7F, -2.7F, 5.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(CrabEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float legAngle = 65F * MathHelper.RADIANS_PER_DEGREE;
        float armAngle = 30F * MathHelper.RADIANS_PER_DEGREE;
        float cosVal = (MathHelper.cos(limbSwing * 2.0F) * 0.6F) * limbSwingAmount;

        legRightFirst.roll = legAngle;
        legRightSecond.roll = legAngle;
        legRightThird.roll = legAngle;

        legLeftFirst.roll = -legAngle;
        legLeftSecond.roll = -legAngle;
        legLeftThird.roll = -legAngle;

        legRightFirst.pitch = -cosVal;
        legRightSecond.pitch = -cosVal * 0.25f;
        legRightThird.pitch = cosVal;

        legLeftFirst.pitch = cosVal;
        legLeftSecond.pitch = cosVal * 0.25f;
        legLeftThird.pitch = -cosVal;

        float armRollMovementModifier = 0.5F;
        armRight.roll = armAngle ;
        armLeft.roll = -armAngle;

        float clawPitchMovement = MathHelper.abs(MathHelper.cos(limbSwing * 1.3333F) * 0.5F) * limbSwingAmount;
        clawTopRight.roll = -clawPitchMovement;
        clawBotRight.roll = clawPitchMovement;

        clawTopLeft.roll = clawPitchMovement;
        clawBotLeft.roll = -clawPitchMovement;

        legSwimmerLeft.yaw = clawPitchMovement;
        legSwimmerRight.yaw = -clawPitchMovement;

        swimmerLeft.yaw = clawPitchMovement * 2;
        swimmerRight.yaw = -clawPitchMovement * 2;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        armRight.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        armLeft.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legSwimmerLeft.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legSwimmerRight.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legRightFirst.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legRightSecond.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legRightThird.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legLeftFirst.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legLeftSecond.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legLeftThird.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        eyeRight.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        eyeLeft.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
