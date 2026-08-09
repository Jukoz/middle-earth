package net.sevenstars.middleearth.entity.stone_troll;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;

public class StoneTrollModel extends EntityModel<TrollEntityRenderState> {
    private final ModelPart head;

    //private final Animation walkingAnimation;

    public StoneTrollModel(ModelPart root) {
        super(root);

        this.head = root.getChild("root").getChild("body").getChild("body_no_arms").getChild("head");

        //this.walkingAnimation = StoneTrollAnimations.WALK.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 93).cuboid(-7.0F, 0.0F, -6.0F, 12.0F, 17.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(-7.5F, -17.0F, 4.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(53, 93).cuboid(-5.0F, 0.0F, -6.0F, 12.0F, 17.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(7.5F, -17.0F, 4.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, -21.0F, 3.0F));

        ModelPartData body_no_arms = body.addChild("body_no_arms", ModelPartBuilder.create(), ModelTransform.origin(0.5F, 3.1667F, -0.5F));

        ModelPartData head = body_no_arms.addChild("head", ModelPartBuilder.create().uv(0, 6).cuboid(-2.5F, -2.0F, -9.0F, 5.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-7.5F, -8.0F, -7.0F, 15.0F, 16.0F, 15.0F, new Dilation(0.0F))
                .uv(135, 85).cuboid(-7.5F, -8.0F, -7.0F, 15.0F, 18.0F, 15.0F, new Dilation(0.3F))
                .uv(1, 1).cuboid(7.5F, -4.0F, -4.0F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 1).cuboid(-9.5F, -4.0F, -4.0F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -32.1667F, -3.5F));

        ModelPartData torso = body_no_arms.addChild("torso", ModelPartBuilder.create().uv(0, 37).cuboid(-15.5F, -10.0F, -7.5F, 31.0F, 20.0F, 15.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -24.1667F, 2.0F));

        ModelPartData belly = body_no_arms.addChild("belly", ModelPartBuilder.create().uv(89, 2).cuboid(-15.5F, -13.5F, -8.0F, 31.0F, 15.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 139).cuboid(-15.5F, 1.5F, -8.0F, 31.0F, 9.0F, 16.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -0.6667F, 1.5F));

        ModelPartData right_arm = body.addChild("right_arm", ModelPartBuilder.create().uv(137, 36).cuboid(-9.0F, -5.0F, -4.0F, 9.0F, 36.0F, 9.0F, new Dilation(0.0F)), ModelTransform.origin(-15.0F, -24.0F, 0.0F));

        ModelPartData left_arm = body.addChild("left_arm", ModelPartBuilder.create().uv(97, 36).cuboid(0.0F, -5.0F, -4.0F, 9.0F, 36.0F, 9.0F, new Dilation(0.0F)), ModelTransform.origin(16.0F, -24.0F, 0.0F));
        return TexturedModelData.of(modelData, 196, 196);
    }
    @Override
    public void setAngles(TrollEntityRenderState state) {
        super.setAngles(state);
        this.setHeadAngles(state.relativeHeadYaw, state.pitch);

        //this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 1.0F, 2.5F);
        //this.attackingAnimation.apply(state.attackAnimationState, state.age);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}