package net.sevenstars.middleearth.entity.swan;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityAnimations;

@Environment(value= EnvType.CLIENT)
public class SwanModel extends EntityModel<SwanEntityRenderState> {
    private final ModelPart swan;
    private final ModelPart headAndNeck;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    private final Animation walkingAnimation;
    private final Animation swimmingAnimation;
    private final Animation cleaningAnimation;
    private final Animation attackingAnimation;

    public SwanModel(ModelPart root) {
        super(root);

        this.swan = root.getChild("root");
        this.headAndNeck = swan.getChild("body").getChild("headAndNeck");
        this.head = swan.getChild("body").getChild("headAndNeck").getChild("head");
        this.rightWing = swan.getChild("body").getChild("rightWing");
        this.leftWing = swan.getChild("body").getChild("leftWing");

        this.walkingAnimation = SwanAnimations.WALK.createAnimation(root);
        this.swimmingAnimation = SwanAnimations.SWIM.createAnimation(root);
        this.cleaningAnimation = SwanAnimations.WINGCLEAN.createAnimation(root);
        this.attackingAnimation = SwanAnimations.ATTACK.createAnimation(root);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 23.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -6.5F, 0.0F));

        ModelPartData leftWing = body.addChild("leftWing", ModelPartBuilder.create(), ModelTransform.origin(4.0F, -2.75F, -4.5F));

        ModelPartData leftInnerWing = leftWing.addChild("leftInnerWing", ModelPartBuilder.create().uv(30, 11).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData leftMiddleWing = leftInnerWing.addChild("leftMiddleWing", ModelPartBuilder.create().uv(48, 3).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 2.25F, 5.5F));

        ModelPartData leftOuterWing = leftMiddleWing.addChild("leftOuterWing", ModelPartBuilder.create().uv(37, -6).cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, -0.5F, 6.0F));

        ModelPartData rightWing = body.addChild("rightWing", ModelPartBuilder.create(), ModelTransform.origin(-4.0F, -2.75F, -4.5F));

        ModelPartData rightInnerWing = rightWing.addChild("rightInnerWing", ModelPartBuilder.create().uv(30, 11).mirrored().cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rightMiddleWing = rightInnerWing.addChild("rightMiddleWing", ModelPartBuilder.create().uv(48, 3).mirrored().cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 2.25F, 5.5F));

        ModelPartData rightOuterWing = rightMiddleWing.addChild("rightOuterWing", ModelPartBuilder.create().uv(37, -6).mirrored().cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-0.5F, -0.5F, 6.0F));

        ModelPartData headAndNeck = body.addChild("headAndNeck", ModelPartBuilder.create(), ModelTransform.origin(0.5F, -2.5F, -4.0F));

        ModelPartData neck = headAndNeck.addChild("neck", ModelPartBuilder.create().uv(12, 17).cuboid(-1.5F, -1.75F, -4.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-1.5F, -4.75F, -1.0F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -1.25F, 1.0F));

        ModelPartData head = headAndNeck.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -6.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 55).cuboid(-1.5F, -3.0F, -4.5F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -6.0F, 1.5F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(32, 23).cuboid(-3.5F, -2.25F, -0.25F, 7.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(7, 26).cuboid(-3.5F, -0.25F, -0.25F, 7.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.25F, 5.25F));

        ModelPartData leftLeg = root.addChild("leftLeg", ModelPartBuilder.create().uv(0, 3).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(26, 23).cuboid(-2.0F, 1.0F, -2.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(1.0F, -3.0F, 0.5F));

        ModelPartData rightLeg = root.addChild("rightLeg", ModelPartBuilder.create().uv(0, 3).mirrored().cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(26, 23).mirrored().cuboid(-2.0F, 1.0F, -2.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-2.0F, -3.0F, 0.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(SwanEntityRenderState state) {
        super.setAngles(state);

        this.headAndNeck.yaw = state.relativeHeadYaw * 0.017453292F;
        this.headAndNeck.pitch = state.pitch * 0.017453292F;

        this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 1.0F, 2.5F);
        this.swimmingAnimation.apply(state.swimAnimationState, state.age);
        this.cleaningAnimation.apply(state.idleAnimationState, state.age);
        this.attackingAnimation.apply(state.attackAnimationState, state.age);
    }
}