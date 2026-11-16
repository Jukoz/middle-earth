package net.sevenstars.middleearth.entity.npcs.renderer;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class NpcEntityModel extends BipedEntityModel<NpcEntityRenderState> {
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_pants";
    private static final String RIGHT_PANTS = "right_pants";
    private final List<ModelPart> parts;
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;

    public NpcEntityModel(ModelPart modelPart) {
        super(modelPart, RenderLayers::entityTranslucent);
        this.leftSleeve = this.leftArm.getChild("left_sleeve");
        this.rightSleeve = this.rightArm.getChild("right_sleeve");
        this.leftPants = this.leftLeg.getChild("left_pants");
        this.rightPants = this.rightLeg.getChild("right_pants");
        this.jacket = this.body.getChild("jacket");
        this.parts = List.of(this.head, this.body, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2;
        ModelPartData modelPartData3;

        modelPartData2 = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(32, 48).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.origin(5.0F, 2.0F, 0.0F));
        modelPartData3 = modelPartData.getChild("right_arm");
        modelPartData2.addChild(LEFT_SLEEVE, ModelPartBuilder.create().uv(48, 48).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
        modelPartData3.addChild(RIGHT_SLEEVE, ModelPartBuilder.create().uv(40, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);

        modelPartData2 = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(16, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.origin(1.9F, 12.0F, 0.0F));
        modelPartData3 = modelPartData.getChild("right_leg");
        modelPartData2.addChild(LEFT_PANTS, ModelPartBuilder.create().uv(0, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
        modelPartData3.addChild(RIGHT_PANTS, ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
        ModelPartData modelPartData4 = modelPartData.getChild("body");
        modelPartData4.addChild("jacket", ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(NpcEntityRenderState renderState) {
        super.setAngles(renderState);
        gesture(renderState);
    }

    // The arms' part in what the npc is saying, in radians. An arm that's aiming, blocking or using something is left
    // to it. The head's part is in the renderer
    private void gesture(NpcEntityRenderState state) {
        float time = state.talkAge;
        float ease = Math.min(1f, time / NpcEntityRenderer.GESTURE_EASE);
        boolean right = isFree(state.rightArmPose);
        boolean left = isFree(state.leftArmPose);
        switch (state.talk) {
            // Waves a hand about in front of it
            case SAY -> {
                if(right) {
                    rightArm.pitch = MathHelper.lerp(ease, rightArm.pitch, -1.1f + 0.3f * MathHelper.sin(time * 0.5f));
                    rightArm.yaw = MathHelper.lerp(ease, rightArm.yaw, -0.35f);
                }
            }
            // Holds both hands out, palms up
            case ASK -> {
                float pitch = -0.7f + 0.08f * MathHelper.sin(time * 0.35f);
                if(right)
                    pose(rightArm, ease, pitch, 0.45f);
                if(left)
                    pose(leftArm, ease, pitch, -0.45f);
            }
            // Throws both arms up
            case EXCLAIM -> {
                float pitch = -3f + 0.12f * MathHelper.sin(time * 1.1f);
                if(right)
                    pose(rightArm, ease, pitch, 0.6f);
                if(left)
                    pose(leftArm, ease, pitch, -0.6f);
            }
            // Shakes with it
            case LAUGH -> {
                float shake = ease * (0.12f * MathHelper.sin(time * 1.3f) - 0.25f);
                if(right)
                    rightArm.pitch += shake;
                if(left)
                    leftArm.pitch += shake;
            }
            case NOD, NONE -> {}
        }
    }

    private static boolean isFree(ArmPose pose) {
        return pose == ArmPose.EMPTY || pose == ArmPose.ITEM;
    }

    private static void pose(ModelPart arm, float ease, float pitch, float roll) {
        arm.pitch = MathHelper.lerp(ease, arm.pitch, pitch);
        arm.roll = MathHelper.lerp(ease, arm.roll, roll);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.leftSleeve.visible = visible;
        this.rightSleeve.visible = visible;
        this.leftPants.visible = visible;
        this.rightPants.visible = visible;
        this.jacket.visible = visible;
    }

    public void setArmAngle(BipedEntityRenderState state, Arm arm, MatrixStack matrices) {
        super.setArmAngle(state, arm, matrices);
    }
}
