package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;

public class SwanBabyModel extends SwanEntityModel{
    private final Animation walkingAnimation;
    private final Animation swimmingAnimation;
    private final Animation sleepingAnimation;
    private final Animation flapAnimation;
    protected SwanBabyModel(ModelPart root) {
        super(root);

        this.walkingAnimation = SwanEntityAnimations.BABY_WALK.createAnimation(root);
        this.swimmingAnimation = SwanEntityAnimations.BABY_SWIM.createAnimation(root);
        this.sleepingAnimation = SwanEntityAnimations.BABY_SLEEP.createAnimation(root);
        this.flapAnimation = SwanEntityAnimations.BABY_FLAP.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, 21.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(1, 1).cuboid(-3.0F, -2.0F, -1.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(1.5F, 2.0F, 0.0F));

        ModelPartData backfeathers_r1 = body.addChild("backfeathers_r1", ModelPartBuilder.create().uv(0, 14).cuboid(-1.5F, -1.2F, -0.8F, 3.0F, 2.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.of(-1.5F, -1.0F, 3.5F, 0.2618F, 0.0F, 0.0F));

        ModelPartData left_wing = body.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -2.0F, 0.0F));

        ModelPartData cube_r1 = left_wing.addChild("cube_r1", ModelPartBuilder.create().uv(0, 16).cuboid(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        ModelPartData right_wing = body.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.origin(-3.0F, -2.0F, 0.0F));

        ModelPartData cube_r2 = right_wing.addChild("cube_r2", ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        ModelPartData left_leg = body.addChild("left_leg", ModelPartBuilder.create().uv(10, 8).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(1, 1).cuboid(-1.0F, 1.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 1.5F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 7).cuboid(-3.0F, -5.0F, -2.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 2).cuboid(-2.0F, -3.0F, -3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData right_leg = body.addChild("right_leg", ModelPartBuilder.create().uv(10, 8).mirrored().cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                .uv(1, 1).mirrored().cuboid(0.0F, 1.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-3.0F, 0.0F, 1.5F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(SwanEntityRenderState state) {
        super.setAngles(state);

        this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2.0f, 1.0f);
        this.swimmingAnimation.apply(state.swimmingAnimationState, state.age);
        this.sleepingAnimation.apply(state.sleepingAnimationState, state.age);
        this.flapAnimation.apply(state.flapAnimationState, state.age, 3f);
    }
}
