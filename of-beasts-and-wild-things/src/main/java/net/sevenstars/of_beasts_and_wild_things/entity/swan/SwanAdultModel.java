package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;

public class SwanAdultModel extends SwanEntityModel {
    private final Animation walkingAnimation;
    private final Animation swimmingAnimation;
    private final Animation sleepingAnimation;
    private final Animation intimidateAnimation;
    private final Animation eatAnimation;
    private final Animation swimIdleAnimation;
    private final Animation flapAnimation;

    protected SwanAdultModel(ModelPart root) {
        super(root);

        this.walkingAnimation = SwanEntityAnimations.WALK.createAnimation(root);
        this.swimmingAnimation = SwanEntityAnimations.SWIM.createAnimation(root);
        this.sleepingAnimation = SwanEntityAnimations.SLEEP.createAnimation(root);
        this.intimidateAnimation = SwanEntityAnimations.INTIMIDATE.createAnimation(root);
        this.eatAnimation = SwanEntityAnimations.EATING.createAnimation(root);
        this.swimIdleAnimation = SwanEntityAnimations.EATING_IN_WATER.createAnimation(root);
        this.flapAnimation = SwanEntityAnimations.FLAP.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 15.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, 1.5F, 0.0F));

        ModelPartData wing_left = body.addChild("wing_left", ModelPartBuilder.create(), ModelTransform.origin(4.0F, -2.75F, -4.5F));

        ModelPartData wing_left_inner = wing_left.addChild("wing_left_inner", ModelPartBuilder.create().uv(30, 11).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData wing_left_middle = wing_left_inner.addChild("wing_left_middle", ModelPartBuilder.create().uv(48, 3).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 2.25F, 5.5F));

        ModelPartData wing_left_outer = wing_left_middle.addChild("wing_left_outer", ModelPartBuilder.create().uv(37, -6).cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, -0.5F, 6.0F));

        ModelPartData wing_right = body.addChild("wing_right", ModelPartBuilder.create(), ModelTransform.origin(-4.0F, -2.75F, -4.5F));

        ModelPartData wing_right_inner = wing_right.addChild("wing_right_inner", ModelPartBuilder.create().uv(30, 11).mirrored().cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData wing_right_middle = wing_right_inner.addChild("wing_right_middle", ModelPartBuilder.create().uv(48, 3).mirrored().cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 2.25F, 5.5F));

        ModelPartData wing_right_outer = wing_right_middle.addChild("wing_right_outer", ModelPartBuilder.create().uv(37, -6).mirrored().cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-0.5F, -0.5F, 6.0F));

        ModelPartData head_and_neck = body.addChild("head_and_neck", ModelPartBuilder.create(), ModelTransform.origin(0.5F, -2.5F, -4.0F));

        ModelPartData neck = head_and_neck.addChild("neck", ModelPartBuilder.create().uv(12, 17).cuboid(-1.5F, -1.75F, -4.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-1.5F, -4.75F, -1.0F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -1.25F, 1.0F));

        ModelPartData head = head_and_neck.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -6.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 55).cuboid(-1.5F, -3.0F, -4.5F, 3.0F, 3.0F, 6.0F, new Dilation(0.01F)), ModelTransform.origin(-0.5F, -6.0F, 1.5F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(32, 23).cuboid(-3.5F, -2.25F, -0.25F, 7.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(7, 26).cuboid(-3.5F, -0.25F, -0.25F, 7.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.25F, 5.25F));

        ModelPartData leg_left = root.addChild("leg_left", ModelPartBuilder.create().uv(0, 3).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(26, 23).cuboid(-2.0F, 1.0F, -2.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(1.0F, 5.0F, 0.5F));

        ModelPartData leg_right = root.addChild("leg_right", ModelPartBuilder.create().uv(0, 3).mirrored().cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(26, 23).mirrored().cuboid(-2.0F, 1.0F, -2.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-2.0F, 5.0F, 0.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(SwanEntityRenderState state) {
        super.setAngles(state);

        this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 6.0f, 1.5f);
        this.sleepingAnimation.apply(state.sleepingAnimationState, state.age);
        this.swimmingAnimation.apply(state.swimmingAnimationState, state.age);
        this.intimidateAnimation.apply(state.intimidateAnimationState, state.age);
        this.eatAnimation.apply(state.eatAnimationState, state.age, 2.2f);
        this.swimIdleAnimation.apply(state.swimIdleAnimationState, state.age);
        this.flapAnimation.apply(state.flapAnimationState, state.age, 3f);
    }
}
