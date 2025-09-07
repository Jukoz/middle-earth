package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.BabyModelTransformer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelTransformer;

import java.util.Set;

public class GreatHornModel extends EntityModel<GreatHornEntityRenderState> {
    public static final ModelTransformer BABY_TRANSFORMER = new BabyModelTransformer(true, 10.0F, 4.0F, Set.of("head"));
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart frontHalf;
    private final ModelPart saddle;
    private final ModelPart frontBody;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;
    private final ModelPart headNeck;
    private final ModelPart neck;
    private final ModelPart topHead;
    private final ModelPart rightAntler;
    private final ModelPart leftAntler;
    private final ModelPart beard;
    private final ModelPart headStall;
    private final ModelPart reins;
    private final ModelPart backBody;
    private final ModelPart tail;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;

    private final Animation idleAnimation;
    private final Animation earWiggleAnimation;
    private final Animation walkingAnimation;
    private final Animation gallopAnimation;
    private final Animation bowAnimation;

    public GreatHornModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.frontHalf = this.body.getChild("front_half");
        this.saddle = this.frontHalf.getChild("saddle");
        this.frontBody = this.frontHalf.getChild("front_body");
        this.frontLeftLeg = this.frontHalf.getChild("frontLeftLeg");
        this.frontRightLeg = this.frontHalf.getChild("frontRightLeg");
        this.headNeck = this.frontHalf.getChild("head_neck");
        this.neck = this.headNeck.getChild("neck");
        this.topHead = this.headNeck.getChild("top_head");
        this.rightAntler = this.topHead.getChild("right_antler");
        this.leftAntler = this.topHead.getChild("left_antler");
        this.beard = this.topHead.getChild("beard");
        this.headStall = this.topHead.getChild("head_stall");
        this.reins = this.topHead.getChild("reins");
        this.backBody = this.frontHalf.getChild("back_body");
        this.tail = this.backBody.getChild("tail");
        this.backLeftLeg = this.root.getChild("backLeftLeg");
        this.backRightLeg = this.root.getChild("backRightLeg");

        this.idleAnimation = GreatHornAnimations.IDLE.createAnimation(root);
        this.earWiggleAnimation = GreatHornAnimations.EAR_WIGGLE.createAnimation(root);
        this.walkingAnimation = GreatHornAnimations.WALK.createAnimation(root);
        this.gallopAnimation = GreatHornAnimations.GALLOP.createAnimation(root);
        this.bowAnimation = GreatHornAnimations.BOW.createAnimation(root);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 8.0F, -10.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -8.5F, 12.0F));

        ModelPartData front_half = body.addChild("front_half", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -13.0F));

        ModelPartData saddle = front_half.addChild("saddle", ModelPartBuilder.create().uv(88, 50).cuboid(-7.0F, -1.0F, 1.0F, 14.0F, 22.0F, 6.0F, new Dilation(0.0F))
                .uv(84, 18).cuboid(-7.0F, -2.0F, -7.0F, 14.0F, 23.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -6.0F, 9.0F));

        ModelPartData front_body = front_half.addChild("front_body", ModelPartBuilder.create().uv(0, 15).cuboid(-6.5F, -8.0F, -6.5F, 13.0F, 16.0F, 13.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.5F, 2.5F));

        ModelPartData frontLeftLeg = front_half.addChild("frontLeftLeg", ModelPartBuilder.create().uv(0, 108).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(4.5F, 8.5F, -1.0F));

        ModelPartData frontRightLeg = front_half.addChild("frontRightLeg", ModelPartBuilder.create().uv(16, 108).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-4.5F, 8.5F, -1.0F));

        ModelPartData head_neck = front_half.addChild("head_neck", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -6.0F, -3.0F));

        ModelPartData neck = head_neck.addChild("neck", ModelPartBuilder.create().uv(40, 0).cuboid(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.5F, -2.0F));

        ModelPartData top_head = head_neck.addChild("top_head", ModelPartBuilder.create().uv(96, 0).cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new Dilation(0.0F))
                .uv(69, 0).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -7.5F, 0.0F));

        ModelPartData earLeft = top_head.addChild("earLeft", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -6.0F, 1.0F, -0.7854F, 0.4363F, 0.0F));

        ModelPartData earRight = top_head.addChild("earRight", ModelPartBuilder.create().uv(9, 0).cuboid(-0.5F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -6.0F, 1.0F, -0.7854F, -0.4363F, 0.0F));

        ModelPartData right_antler = top_head.addChild("right_antler", ModelPartBuilder.create().uv(34, 120).mirrored().cuboid(-8.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.0F, -7.0F, 0.0F, 0.1812F, -0.3808F, 0.4252F));

        ModelPartData cube_r1 = right_antler.addChild("cube_r1", ModelPartBuilder.create().uv(34, 124).mirrored().cuboid(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-7.0F, 1.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData cube_r2 = right_antler.addChild("cube_r2", ModelPartBuilder.create().uv(27, 87).mirrored().cuboid(-16.0F, 0.0F, -12.0F, 16.0F, 0.0F, 23.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-8.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.6109F));

        ModelPartData cube_r3 = right_antler.addChild("cube_r3", ModelPartBuilder.create().uv(27, 112).cuboid(-5.0F, 0.0F, -7.0F, 11.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.0F, -2.0F, -1.1781F, 0.0F, 0.0F));

        ModelPartData left_antler = top_head.addChild("left_antler", ModelPartBuilder.create().uv(54, 120).cuboid(0.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -7.0F, 0.0F, 0.1812F, 0.3808F, -0.4252F));

        ModelPartData cube_r4 = left_antler.addChild("cube_r4", ModelPartBuilder.create().uv(50, 112).cuboid(-6.0F, 0.0F, -7.0F, 11.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.0F, -2.0F, -1.1781F, 0.0F, 0.0F));

        ModelPartData cube_r5 = left_antler.addChild("cube_r5", ModelPartBuilder.create().uv(62, 87).cuboid(0.0F, 0.0F, -12.0F, 16.0F, 0.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 0.0F, 7.0F, 0.0F, 0.0F, -0.6109F));

        ModelPartData cube_r6 = left_antler.addChild("cube_r6", ModelPartBuilder.create().uv(54, 124).cuboid(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData beard = top_head.addChild("beard", ModelPartBuilder.create().uv(69, 3).cuboid(0.0F, -1.0F, -6.0F, 0.0F, 18.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.5F, -5.0F));

        ModelPartData head_stall = top_head.addChild("head_stall", ModelPartBuilder.create().uv(52, 26).cuboid(-2.5F, -11.5F, -11.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.2F))
                .uv(51, 35).cuboid(-3.5F, -14.5F, -6.0F, 7.0F, 7.0F, 9.0F, new Dilation(0.2F))
                .uv(53, 53).cuboid(-3.5F, -7.5F, -4.0F, 7.0F, 18.0F, 7.0F, new Dilation(0.2F)), ModelTransform.origin(0.0F, 7.5F, 0.0F));

        ModelPartData reins = top_head.addChild("reins", ModelPartBuilder.create().uv(86, 99).cuboid(3.8F, -1.0F, -1.0F, 0.0F, 7.0F, 21.0F, new Dilation(0.0F))
                .uv(86, 91).cuboid(-3.8F, -1.0F, -1.0F, 0.0F, 7.0F, 21.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.0F, -6.0F));

        ModelPartData back_body = front_half.addChild("back_body", ModelPartBuilder.create().uv(0, 44).cuboid(-5.5F, -4.5F, -4.0F, 11.0F, 13.0F, 14.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 13.0F));

        ModelPartData tail = back_body.addChild("tail", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -4.5F, 10.0F));

        ModelPartData cube_r7 = tail.addChild("cube_r7", ModelPartBuilder.create().uv(39, 47).cuboid(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        ModelPartData backLeftLeg = root.addChild("backLeftLeg", ModelPartBuilder.create().uv(0, 72).cuboid(-2.25F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new Dilation(0.0F))
                .uv(4, 89).cuboid(-2.25F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(4.75F, -4.75F, 19.75F));

        ModelPartData backRightLeg = root.addChild("backRightLeg", ModelPartBuilder.create().uv(24, 72).cuboid(-2.75F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new Dilation(0.0F))
                .uv(28, 89).cuboid(-1.75F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-4.75F, -4.75F, 19.75F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(GreatHornEntityRenderState state) {
        super.setAngles(state);

        this.idleAnimation.apply(state.idleAnimationState, state.age);
        this.earWiggleAnimation.apply(state.earWiggleAnimationState, state.age);

        if(state.bowAnimationState.isRunning()) {
            this.bowAnimation.apply(state.bowAnimationState, state.age);
        } else {
            if(state.gallopAnimationState.isRunning()) {
                this.gallopAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 1.0F, 2.5F);
            } else {
                this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2.75F, 2.5F);
            }
        }

        boolean showSaddle = !state.saddle;
        saddle.hidden = showSaddle;
        headStall.hidden = showSaddle;
        reins.hidden = showSaddle;

        if(!state.hasRider) {
            reins.pitch = -12.5f * 0.017453292F;
        } else {
            reins.pitch = 0f;
        }

        if(state.baby) {
            leftAntler.visible = false;
            rightAntler.visible = false;
            beard.hidden = true;
        }
    }
}