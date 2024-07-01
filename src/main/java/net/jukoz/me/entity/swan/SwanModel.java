package net.jukoz.me.entity.swan;

import net.jukoz.me.entity.deer.DeerAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class SwanModel extends SinglePartEntityModel<SwanEntity> {
    private final ModelPart swan;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public SwanModel(ModelPart root) {
        this.swan = root.getChild("root");
        this.head = swan.getChild("headAndNeck").getChild("head");
        this.rightWing = swan.getChild("rightWing");
        this.leftWing = swan.getChild("leftWing");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -6.5F, 0.0F));

        ModelPartData leftWing = root.addChild("leftWing", ModelPartBuilder.create().uv(47, 5).cuboid(0.0F, -0.75F, 7.5F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F))
                .uv(42, 48).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, -9.25F, -4.5F, 0.0F, 0.0F, -0.1745F));

        ModelPartData rightWing = root.addChild("rightWing", ModelPartBuilder.create().uv(47, 5).mirrored().cuboid(0.0F, -0.75F, 7.5F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(42, 48).mirrored().cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.5F, -9.25F, -4.5F, 0.0F, 0.0F, 0.1309F));

        ModelPartData rightLeg = root.addChild("rightLeg", ModelPartBuilder.create().uv(44, 3).mirrored().cuboid(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(19, 21).mirrored().cuboid(-2.0F, 3.0F, -2.5F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.0F, -3.0F, 0.5F));

        ModelPartData leftLeg = root.addChild("leftLeg", ModelPartBuilder.create().uv(44, 3).cuboid(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(19, 21).cuboid(-2.0F, 3.0F, -2.5F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -3.0F, 0.5F));

        ModelPartData backtail = root.addChild("backtail", ModelPartBuilder.create().uv(4, 22).cuboid(-3.5F, -3.0F, -4.0F, 7.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -7.0F, 9.0F));

        ModelPartData tailCube1 = backtail.addChild("tailCube1", ModelPartBuilder.create().uv(39, 35).cuboid(-3.0F, -3.9F, -2.6F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 2.0F, 0.3054F, 0.0F, 0.0F));

        ModelPartData headAndNeck = root.addChild("headAndNeck", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -11.0F, -3.0F));

        ModelPartData neck = headAndNeck.addChild("neck", ModelPartBuilder.create().uv(28, 30).cuboid(-1.5F, -1.75F, -4.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(30, 18).cuboid(-1.5F, -5.75F, -1.0F, 3.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 0.75F, 0.0F));

        ModelPartData head = headAndNeck.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -7.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 0).cuboid(-1.5F, -3.0F, -5.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -5.0F, 1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        swan.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return swan;
    }

    @Override
    public void setAngles(SwanEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(headYaw, headPitch);

        this.rightWing.roll = 0;
        this.leftWing.roll = 0;

        if(!entity.isOnGround()) {
            float angle = MathHelper.cos(MathHelper.cos(animationProgress * 0.5f));
            this.rightWing.roll = 4 + (4 * angle);
            this.leftWing.roll = -4 - (4 * angle);
        }

        this.animateMovement(SwanAnimations.WALK, limbAngle, limbDistance, 1f, 1f);
        this.updateAnimation(entity.swimAnimationState, SwanAnimations.SWIM, animationProgress, 1f);
        this.updateAnimation(entity.idleAnimationState, SwanAnimations.WINGCLEAN, animationProgress, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}