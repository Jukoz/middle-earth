package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargAnimations;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class WargEyesModel extends SinglePartEntityModel<WargEntity> {
    private final ModelPart warg;
    public WargEyesModel(ModelPart root) {
        this.warg = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData upperBody = body.addChild("upperBody", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

        ModelPartData head = upperBody.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(39, 35).cuboid(-1.3858F, -4.1138F, -5.0F, 10.0F, 6.0F, 9.0F, new Dilation(0.025F)), ModelTransform.of(14.3858F, -1.8862F, 1.5F, 0.0F, 0.0F, 0.2618F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public ModelPart getPart() {
        return this.warg;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        warg.render(matrices, vertexConsumer, light, overlay, color);
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
        this.updateAnimation(entity.startSittingAnimationState, WargAnimations.SIT_DOWN, animationProgress, 1f);
        this.updateAnimation(entity.stopSittingAnimationState, WargAnimations.STAND_UP, animationProgress, 1f);
        this.updateAnimation(entity.sittingAnimationState, WargAnimations.SIT, animationProgress, 1f);
    }
}
