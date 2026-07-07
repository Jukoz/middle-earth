package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

public class DeerEntityModel extends EntityModel<DeerEntityRenderState> {

    private final Animation walkingAnimation;
    private final Animation runningAnimation;

    protected DeerEntityModel(ModelPart root) {
        super(root);
        this.walkingAnimation = DeerEntityAnimations.WALK.createAnimation(root);
        this.runningAnimation = DeerEntityAnimations.RUN.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 11.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(40, 53).cuboid(-5.0F, -6.0F, 2.0F, 10.0F, 9.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 2.0F, 0.0F));

        ModelPartData upper_body = body.addChild("upper_body", ModelPartBuilder.create().uv(1, 48).cuboid(-4.0F, -6.0F, -8.0F, 8.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -6.0F, 8.0F));

        ModelPartData tail_r1 = tail.addChild("tail_r1", ModelPartBuilder.create().uv(0, 46).cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, 0.0F));

        ModelPartData leg_front_left = body.addChild("leg_front_left", ModelPartBuilder.create().uv(31, 24).cuboid(-2.0F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 2.0F, -5.5F));

        ModelPartData leg_front_right = body.addChild("leg_front_right", ModelPartBuilder.create().uv(44, 24).cuboid(-1.0F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 2.0F, -5.5F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -11.0F, -4.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F))
                .uv(1, 15).cuboid(-2.0F, -9.0F, -7.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(25, 7).cuboid(1.0F, -20.0F, 1.5F, 10.0F, 9.0F, 6.0F, new Dilation(0.0F))
                .uv(25, 7).mirrored().cuboid(-11.0F, -20.0F, 1.5F, 10.0F, 9.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, -2.0F, -7.0F));

        ModelPartData left_ear = head.addChild("left_ear", ModelPartBuilder.create(), ModelTransform.origin(2.0F, -9.0F, 1.0F));

        ModelPartData left_ear_r1 = left_ear.addChild("left_ear_r1", ModelPartBuilder.create().uv(21, 1).cuboid(0.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData right_ear = head.addChild("right_ear", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, -9.0F, 1.0F));

        ModelPartData right_ear_r1 = right_ear.addChild("right_ear_r1", ModelPartBuilder.create().uv(32, 1).cuboid(-4.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        ModelPartData neck = head.addChild("neck", ModelPartBuilder.create().uv(4, 27).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -3.0F, 0.0F));

        ModelPartData leg_back_left = root.addChild("leg_back_left", ModelPartBuilder.create().uv(31, 38).cuboid(-2.0F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(4.0F, 5.0F, 6.5F));

        ModelPartData leg_back_right = root.addChild("leg_back_right", ModelPartBuilder.create().uv(44, 38).cuboid(-1.0F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-4.0F, 5.0F, 6.5F));
        return TexturedModelData.of(modelData, 80, 80);
    }

    @Override
    public void setAngles(DeerEntityRenderState state) {
        super.setAngles(state);

        if(state.isRunning) {
            this.runningAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 1.5F, 1.0F);
        }
        else {
            this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2.0F, 1.0F);
        }
    }
}
