package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargAnimations;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class WargSaddleModel extends SinglePartEntityModel<WargEntity> {
    private final ModelPart warg;
    public WargSaddleModel(ModelPart root) {
        this.warg = root.getChild("warg");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData warg = modelPartData.addChild("warg", ModelPartBuilder.create(), ModelTransform.of(0.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData body = warg.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData lowerBody = body.addChild("lowerBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -0.5F, -0.0F));

        ModelPartData saddle = lowerBody.addChild("saddle", ModelPartBuilder.create(), ModelTransform.pivot(0.6142F, -5.1138F, -6.5F));

        ModelPartData cube = saddle.addChild("cube", ModelPartBuilder.create().uv(0, 68).cuboid(-2.5F, -1.2F, -6.0F, 9.0F, 16.0F, 12.0F, new Dilation(0.55F))
                .uv(11, 5).cuboid(-2.5F, -1.2F, -13.0F, 9.0F, 16.0F, 19.0F, new Dilation(0.7F)), ModelTransform.of(0.0F, 0.0F, 3.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        warg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return warg;
    }

    @Override
    public void setAngles(WargEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if(entity.hasControllingPassenger() || entity.isAttacking()) {
            this.animateMovement(WargAnimations.RUN, limbAngle, limbDistance, 1f, 1f);
        }
        else {
            this.animateMovement(WargAnimations.WALK, limbAngle, limbDistance, 1.5f, 1.5f);
        }

        this.updateAnimation(entity.idleAnimationState, WargAnimations.GROOM, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, WargAnimations.BITE, animationProgress, 1f);
        this.updateAnimation(entity.startSittingAnimationState, WargAnimations.SITTING_DOWN, animationProgress, 1f);
        this.updateAnimation(entity.stopSittingAnimationState, WargAnimations.STANDING_UP, animationProgress, 1f);
        this.updateAnimation(entity.sittingAnimationState, WargAnimations.SITTING, animationProgress, 1f);
    }
}
