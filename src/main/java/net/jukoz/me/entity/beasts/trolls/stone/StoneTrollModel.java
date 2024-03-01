package net.jukoz.me.entity.beasts.trolls.stone;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class StoneTrollModel extends SinglePartEntityModel<StoneTrollEntity> {
    private final ModelPart r;
    private final ModelPart head;
    public StoneTrollModel(ModelPart root) {
        this.r = root.getChild("r");
        this.head = r.getChild("upperbody").getChild("upperbodynoarms").getChild("head");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData r = modelPartData.addChild("r", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 26.0F, 0.0F));

        ModelPartData upperbody = r.addChild("upperbody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -20.0F, 1.0F));

        ModelPartData arms = upperbody.addChild("arms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, -1.0F));

        ModelPartData right_arm = arms.addChild("right_arm", ModelPartBuilder.create().uv(28, 82).cuboid(-5.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new Dilation(0.2F))
                .uv(84, 96).cuboid(-5.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-14.0F, -46.0F, 2.0F));

        ModelPartData left_arm = arms.addChild("left_arm", ModelPartBuilder.create().uv(0, 82).cuboid(-3.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new Dilation(0.2F))
                .uv(56, 96).cuboid(-3.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(15.0F, -46.0F, 2.0F));

        ModelPartData upperbodynoarms = upperbody.addChild("upperbodynoarms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, -1.0F));

        ModelPartData head = upperbodynoarms.addChild("head", ModelPartBuilder.create().uv(74, 0).cuboid(-6.5F, -9.6306F, -6.7491F, 13.0F, 14.0F, 12.0F, new Dilation(0.0F))
                .uv(62, 70).cuboid(-6.5F, -9.6306F, -6.7491F, 13.0F, 14.0F, 12.0F, new Dilation(0.2F))
                .uv(0, 0).mirrored().cuboid(6.4F, -5.6306F, -1.7491F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 5).cuboid(-10.4F, -5.6306F, -1.7491F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -27.3694F, -1.2509F));

        ModelPartData nose = head.addChild("nose", ModelPartBuilder.create().uv(62, 0).cuboid(-2.5F, -1.5F, -3.0F, 5.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.1306F, -4.7491F, -0.3054F, 0.0F, 0.0F));

        ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(126, 29).cuboid(-4.5F, -2.0F, -4.0F, 9.0F, 4.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, 3.3694F, -2.7491F));

        ModelPartData hair = head.addChild("hair", ModelPartBuilder.create().uv(192, 158).cuboid(-33.0F, -55.2728F, -0.4462F, 13.0F, 19.0F, 12.0F, new Dilation(0.1F)), ModelTransform.pivot(26.5F, 51.3694F, -5.7491F));

        ModelPartData hair1 = hair.addChild("hair1", ModelPartBuilder.create().uv(204, 105).cuboid(-6.3F, -22.2272F, -5.9945F, 13.0F, 19.0F, 12.0F, new Dilation(0.1F)), ModelTransform.of(-26.7F, -37.5249F, 16.9945F, 0.5236F, 0.0F, 0.0F));

        ModelPartData torso = upperbodynoarms.addChild("torso", ModelPartBuilder.create().uv(0, 41).cuboid(-13.0F, -50.0F, -4.0F, 25.0F, 29.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-13.0F, -50.0F, -4.0F, 25.0F, 29.0F, 12.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, 22.0F, 0.0F));

        ModelPartData frontloin = torso.addChild("frontloin", ModelPartBuilder.create().uv(74, 48).cuboid(-12.5F, -1.5F, -0.5F, 25.0F, 21.0F, 1.0F, new Dilation(0.3F)), ModelTransform.pivot(-0.5F, -26.5F, -3.504F));

        ModelPartData backloin = torso.addChild("backloin", ModelPartBuilder.create().uv(74, 26).cuboid(-12.5F, -1.5F, -0.5F, 25.0F, 21.0F, 1.0F, new Dilation(0.3F)), ModelTransform.pivot(-0.5F, -26.5F, 7.85F));

        ModelPartData bodynoarmswithlegs = upperbodynoarms.addChild("bodynoarmswithlegs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -11.0F, 0.0F));

        ModelPartData legs = r.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_leg = legs.addChild("right_leg", ModelPartBuilder.create().uv(126, 0).cuboid(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new Dilation(0.0F))
                .uv(112, 99).cuboid(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new Dilation(0.2F)), ModelTransform.pivot(-7.0F, -21.5F, 2.0F));

        ModelPartData left_leg = legs.addChild("left_leg", ModelPartBuilder.create().uv(0, 126).cuboid(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new Dilation(0.0F))
                .uv(112, 70).cuboid(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new Dilation(0.2F)), ModelTransform.pivot(6.0F, -21.5F, 2.0F));

        ModelPartData club = modelPartData.addChild("club", ModelPartBuilder.create(), ModelTransform.of(-15.3115F, 10.3091F, -12.2077F, -0.1309F, 0.0F, 0.0873F));

        ModelPartData cube_r1 = club.addChild("cube_r1", ModelPartBuilder.create().uv(16, 158).mirrored().cuboid(-4.5F, -4.5F, -22.0F, 9.0F, 9.0F, 22.0F, new Dilation(0.0F)).mirrored(false)
                .uv(17, 194).cuboid(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 22.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1993F, 0.0145F, -0.0979F));
        return TexturedModelData.of(modelData, 256, 256);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        r.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return r;
    }

    @Override
    public void setAngles(StoneTrollEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(headYaw, headPitch);

        this.animateMovement(StoneTrollAnimations.WALK, limbAngle, limbDistance, 3f, 3f);
        this.updateAnimation(entity.attackAnimationState, StoneTrollAnimations.ATTACK, animationProgress, 1.3f);
        this.updateAnimation(entity.chargeAnimationState, StoneTrollAnimations.CHARGE, animationProgress, 1f);
        this.updateAnimation(entity.throwingAnimationState, StoneTrollAnimations.THROW, animationProgress, 1f);
        this.updateAnimation(entity.sittingAnimationState, StoneTrollAnimations.SITTING, animationProgress, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}