package net.jukoz.me.entity.swan;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.entity.deer.DeerAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

@Environment(value= EnvType.CLIENT)
public class SwanModel extends SinglePartEntityModel<SwanEntity> {
    private final ModelPart swan;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public SwanModel(ModelPart root) {
        this.swan = root.getChild("root");
        this.head = swan.getChild("body").getChild("headAndNeck");
        this.rightWing = swan.getChild("body").getChild("rightWing");
        this.leftWing = swan.getChild("body").getChild("leftWing");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 23.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -6.5F, 0.0F));

        ModelPartData leftWing = body.addChild("leftWing", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -2.75F, -4.5F));

        ModelPartData leftInnerWing = leftWing.addChild("leftInnerWing", ModelPartBuilder.create().uv(30, 11).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftMiddleWing = leftInnerWing.addChild("leftMiddleWing", ModelPartBuilder.create().uv(48, 3).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.25F, 5.5F));

        ModelPartData leftOuterWing = leftMiddleWing.addChild("leftOuterWing", ModelPartBuilder.create().uv(37, -6).cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -0.5F, 6.0F));

        ModelPartData rightWing = body.addChild("rightWing", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -2.75F, -4.5F));

        ModelPartData rightInnerWing = rightWing.addChild("rightInnerWing", ModelPartBuilder.create().uv(30, 11).mirrored().cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightMiddleWing = rightInnerWing.addChild("rightMiddleWing", ModelPartBuilder.create().uv(48, 3).mirrored().cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 2.25F, 5.5F));

        ModelPartData rightOuterWing = rightMiddleWing.addChild("rightOuterWing", ModelPartBuilder.create().uv(37, -6).mirrored().cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-0.5F, -0.5F, 6.0F));

        ModelPartData headAndNeck = body.addChild("headAndNeck", ModelPartBuilder.create(), ModelTransform.pivot(0.5F, -2.5F, -4.0F));

        ModelPartData neck = headAndNeck.addChild("neck", ModelPartBuilder.create().uv(12, 17).cuboid(-1.5F, -1.75F, -4.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-1.5F, -4.75F, -1.0F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -1.25F, 1.0F));

        ModelPartData head = headAndNeck.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -6.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 55).cuboid(-1.5F, -3.0F, -4.5F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -6.0F, 1.5F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(32, 23).cuboid(-3.5F, -2.25F, -0.25F, 7.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(7, 26).cuboid(-3.5F, -0.25F, -0.25F, 7.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.25F, 5.25F));

        ModelPartData leftLeg = root.addChild("leftLeg", ModelPartBuilder.create().uv(0, 3).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(26, 23).cuboid(-2.0F, 1.0F, -2.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -3.0F, 0.5F));

        ModelPartData rightLeg = root.addChild("rightLeg", ModelPartBuilder.create().uv(0, 3).mirrored().cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(26, 23).mirrored().cuboid(-2.0F, 1.0F, -2.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.0F, -3.0F, 0.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        swan.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return swan;
    }

    @Override
    public void setAngles(SwanEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(headYaw, headPitch);

        if(!entity.isOnGround() && !entity.isTouchingWater()) {
            float angle = MathHelper.cos(MathHelper.cos(animationProgress * 0.5f));
            this.rightWing.roll = 4 + (4 * angle);
            this.leftWing.roll = -4 - (4 * angle);
        }

        this.animateMovement(SwanAnimations.WALK, limbAngle, limbDistance, 4f, 4f);
        this.updateAnimation(entity.swimAnimationState, SwanAnimations.SWIM, animationProgress, 1f);
        this.updateAnimation(entity.idleAnimationState, SwanAnimations.WINGCLEAN, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, SwanAnimations.ATTACK, animationProgress, 1.7f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}