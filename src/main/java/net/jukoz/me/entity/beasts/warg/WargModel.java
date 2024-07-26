package net.jukoz.me.entity.beasts.warg;

import net.jukoz.me.entity.deer.DeerAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WargModel extends SinglePartEntityModel<WargEntity> {
    private final ModelPart warg;
    public WargModel(ModelPart root) {
        this.warg = root.getChild("root");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData warg = root.addChild("warg", ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData head = warg.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(39, 35).cuboid(-1.3858F, -4.1138F, -5.0F, 10.0F, 6.0F, 9.0F, new Dilation(0.0F))
                .uv(39, 35).cuboid(-1.3858F, -4.1138F, -5.0F, 10.0F, 6.0F, 9.0F, new Dilation(0.025F))
                .uv(60, 51).cuboid(-1.3858F, -2.1138F, -2.0F, 15.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(60, 59).cuboid(7.6142F, 2.8862F, -2.0F, 6.0F, 1.0F, 3.0F, new Dilation(0.05F))
                .uv(37, 18).cuboid(-2.0F, 1.25F, -4.0F, 10.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(14.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        ModelPartData earRight = head.addChild("earRight", ModelPartBuilder.create(), ModelTransform.pivot(4.1142F, -2.1138F, -3.978F));

        ModelPartData earRight_r1 = earRight.addChild("earRight_r1", ModelPartBuilder.create().uv(0, 19).cuboid(0.2802F, -4.8619F, -1.032F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, -0.022F, 0.0F, 0.0F, -1.0036F));

        ModelPartData earLeft = head.addChild("earLeft", ModelPartBuilder.create(), ModelTransform.pivot(4.1142F, -2.1138F, 3.022F));

        ModelPartData earLeft_r1 = earLeft.addChild("earLeft_r1", ModelPartBuilder.create().uv(0, 1).cuboid(0.2802F, -4.8619F, 0.01F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.0036F));

        ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(0, 44).cuboid(-5.5F, -0.25F, -1.5F, 15.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 66).cuboid(-5.5F, -1.25F, -1.5F, 15.0F, 1.0F, 3.0F, new Dilation(-0.05F)), ModelTransform.pivot(4.1142F, 3.1362F, -0.5F));

        ModelPartData tail = warg.addChild("tail", ModelPartBuilder.create().uv(48, 28).cuboid(-12.0F, -4.0F, -1.5F, 13.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-13.3858F, -5.1138F, -0.5F, 0.0F, 0.0F, -1.5708F));

        ModelPartData body = warg.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));
        ModelPartData bodyFur = body.addChild("bodyFur", ModelPartBuilder.create().uv(1, 19).cuboid(-6.5F, -5.5F, -5.0F, 13.0F, 13.0F, 10.0F, new Dilation(-0.3F)), ModelTransform.pivot(7.5F, -0.25F, 1.0F));

        ModelPartData mainBody = body.addChild("mainBody", ModelPartBuilder.create().uv(0, 0).cuboid(-13.5F, -5.0F, -4.0F, 27.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.2F, 0.0F, 1.0F));

        ModelPartData mane = body.addChild("mane", ModelPartBuilder.create().uv(6, 73).cuboid(-8.5F, -3.0F, 0.0F, 17.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, -8.0F, 1.0F));

        ModelPartData legLeft = warg.addChild("legLeft", ModelPartBuilder.create().uv(0, 50).cuboid(-2.7F, 1.0F, -1.5F, 5.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.8858F, 2.8862F, 2.0F));

        ModelPartData legRight = warg.addChild("legRight", ModelPartBuilder.create().uv(16, 50).cuboid(-2.7F, 1.0F, -1.5F, 5.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.8858F, 2.8862F, -3.0F));

        ModelPartData armRight = warg.addChild("armRight", ModelPartBuilder.create().uv(46, 50).cuboid(-2.0F, 1.0F, -1.5F, 4.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(9.6142F, 2.8862F, -3.0F));

        ModelPartData armLeft = warg.addChild("armLeft", ModelPartBuilder.create().uv(32, 50).cuboid(-2.0F, 1.0F, -1.0F, 4.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(9.6142F, 2.8862F, 1.5F));
        return TexturedModelData.of(modelData, 128, 128);
    }


    @Override
    public void setAngles(WargEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if(entity.hasControllingPassenger() || entity.isAttacking()) {
            this.animateMovement(WargAnimations.RUN, limbAngle, limbDistance, 1f, 1f);
        }
        else {
            this.animateMovement(WargAnimations.WALK, limbAngle, limbDistance, 1f, 1f);
        }

        this.updateAnimation(entity.idleAnimationState, WargAnimations.GROOM, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, WargAnimations.BITE, animationProgress, 1f);
        this.updateAnimation(entity.startSittingAnimationState, WargAnimations.SITTING_DOWN, animationProgress, 1f);
        this.updateAnimation(entity.stopSittingAnimationState, WargAnimations.STANDING_UP, animationProgress, 1f);
        this.updateAnimation(entity.sittingAnimationState, WargAnimations.SITTING, animationProgress, 1f);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        warg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.warg;
    }
}