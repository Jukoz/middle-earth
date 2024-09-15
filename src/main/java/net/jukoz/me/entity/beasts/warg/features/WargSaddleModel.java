package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargAnimations;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class WargSaddleModel extends SinglePartEntityModel<WargEntity> {
    private final ModelPart warg;
    public WargSaddleModel(ModelPart root) {
        this.warg = root.getChild("root");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData upperBody = body.addChild("upperBody", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

        ModelPartData saddle = upperBody.addChild("saddle", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, -4.0F, -4.0F));

        ModelPartData saddleFurRight_r1 = saddle.addChild("saddleFurRight_r1", ModelPartBuilder.create().uv(77, 62).cuboid(-0.5F, -8.0F, -3.0F, 1.0F, 10.0F, 9.0F, new Dilation(0.3F)), ModelTransform.of(-7.0F, 5.8F, -0.7F, 1.5708F, -1.7017F, -1.5708F));

        ModelPartData saddleFurLeft_r1 = saddle.addChild("saddleFurLeft_r1", ModelPartBuilder.create().uv(57, 62).cuboid(-0.5F, -8.0F, -3.0F, 1.0F, 10.0F, 9.0F, new Dilation(0.3F)), ModelTransform.of(-7.0F, 5.8F, 10.7F, 1.5708F, -1.4399F, -1.5708F));

        ModelPartData saddleFurTop_r1 = saddle.addChild("saddleFurTop_r1", ModelPartBuilder.create().uv(59, 51).mirrored().cuboid(-2.5F, -1.2F, -3.0F, 9.0F, 2.0F, 9.0F, new Dilation(0.3F)).mirrored(false), ModelTransform.of(-7.0F, -1.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData saddleMat_r1 = saddle.addChild("saddleMat_r1", ModelPartBuilder.create().uv(0, 68).cuboid(-2.5F, -1.2F, -6.0F, 9.0F, 16.0F, 12.0F, new Dilation(0.3F))
                .uv(11, 5).cuboid(-2.5F, -1.2F, -13.0F, 9.0F, 16.0F, 19.0F, new Dilation(0.4F)), ModelTransform.of(0.0F, 0.0F, 3.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        warg.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return warg;
    }

    @Override
    public void setAngles(WargEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if((entity.hasControllingPassenger() && entity.getControllingPassenger().isSprinting()) || entity.isRunning()) {
            this.animateMovement(WargAnimations.RUN, limbAngle, limbDistance, 1.2f, 1.2f);
        }
        else {
            this.animateMovement(WargAnimations.WALK, limbAngle, limbDistance, 1.5f, 1.5f);
        }

        this.updateAnimation(entity.idleAnimationState, WargAnimations.GROOM, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, WargAnimations.BITE, animationProgress, 1f);
        this.updateAnimation(entity.startSittingAnimationState, WargAnimations.SIT_DOWN, animationProgress, 3f);
        this.updateAnimation(entity.stopSittingAnimationState, WargAnimations.STAND_UP, animationProgress, 3f);
        this.updateAnimation(entity.sittingAnimationState, WargAnimations.SIT, animationProgress, 1f);
    }
}
