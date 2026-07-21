package net.sevenstars.middleearth.entity.beasts.warg;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class WargModel extends EntityModel<WargEntityRenderState> {
    private final ModelPart warg;
    private final ModelPart head;
    private final ModelPart mane;

    private final Animation runningAnimation;
    private final Animation walkingAnimation;
    private final Animation idleAnimation;
    private final Animation bitingAnimation;
    private final Animation startSittingAnimation;
    private final Animation stopSittingAnimation;
    private final Animation sittingAnimation;

    public WargModel(ModelPart root) {
        super(root);

        this.warg = root.getChild("root");
        ModelPart body = this.warg.getChild(EntityModelPartNames.BODY);
        ModelPart upperBody = body.getChild("upper_body");
        ModelPart bodyNoLegs = upperBody.getChild("body_no_legs");
        this.head = bodyNoLegs.getChild(EntityModelPartNames.HEAD);
        this.mane = bodyNoLegs.getChild("body_fur").getChild("mane");

        this.walkingAnimation = WargAnimations.WALK.createAnimation(root);
        this.runningAnimation = WargAnimations.RUN.createAnimation(root);
        this.idleAnimation = WargAnimations.IDLE.createAnimation(root);
        this.bitingAnimation = WargAnimations.BITE.createAnimation(root);
        this.startSittingAnimation = WargAnimations.STANDING_TO_SITTING.createAnimation(root);
        this.stopSittingAnimation = WargAnimations.SITTING_TO_STANDING.createAnimation(root);
        this.sittingAnimation = WargAnimations.SITTING.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 128, 128);
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));
        ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(-2.6142F, 2.1138F, 1.5F));
        ModelPartData upperBody = body.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.origin(-1.3858F, -1.1138F, -1.5F));
        ModelPartData bodyNoLegs = upperBody.addChild("body_no_legs", ModelPartBuilder.create(), ModelTransform.origin(7.5F, -0.25F, 0.5F));
        ModelPartData bodyFur = bodyNoLegs.addChild("body_fur", ModelPartBuilder.create().uv(0, 0).cuboid(-6.5F, -7.5F, -6.0F, 14.0F, 15.0F, 13.0F, new Dilation(-0.3F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        bodyFur.addChild("mane", ModelPartBuilder.create().uv(54, 15).cuboid(-9.5F, -5.0F, 0.0F, 20.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-2.0F, -6.75F, 0.5F));
        bodyNoLegs.addChild("main_body", ModelPartBuilder.create().uv(0, 49).cuboid(-0.5F, -6.0F, -5.0F, 12.0F, 12.0F, 11.0F, new Dilation(0.0F)), ModelTransform.origin(-5.3F, 0.25F, 0.0F));

        ModelPartData head = bodyNoLegs.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()
                .uv(74, 0).cuboid(-1.5F, -3.25F, -5.0F, 10.0F, 5.0F, 10.0F, new Dilation(0.0F))
                .uv(74, 0).cuboid(-1.5F, -3.25F, -5.0F, 10.0F, 5.0F, 10.0F, new Dilation(0.025F))
                .uv(54, 31).cuboid(-2.1142F, 1.6138F, -4.0F, 10.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(7.25F, -2.0F, 0.5F));
        head.addChild("snout", ModelPartBuilder.create()
                .uv(54, 23).cuboid(-9.5F, 0.5F, -2.5F, 15.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(46, 82).cuboid(-0.644F, 4.522F, -2.5F, 6.0F, 1.0F, 4.0F, new Dilation(0.05F)), ModelTransform.origin(8.0F, -1.5F, 0.5F));
        head.addChild("ear_right", ModelPartBuilder.create(), ModelTransform.of(4.0F, -2.25F, -4.478F, 0.0F, -0.8727F, 0.0F));
        head.addChild("ear_left", ModelPartBuilder.create(), ModelTransform.of(4.0F, -2.25F, 4.522F, 0.0F, 0.8727F, 0.0F));
        ModelPartData earRight = head.getChild("ear_right");
        earRight.addChild("earR_r1", ModelPartBuilder.create().uv(82, 72).cuboid(0.2802F, -4.8619F, -1.032F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, -0.022F, 0.0F, 0.0F, -1.0036F));
        ModelPartData earLeft = head.getChild("ear_left");
        earLeft.addChild("earL_r1", ModelPartBuilder.create().uv(82, 79).cuboid(0.2802F, -4.8619F, 0.01F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.0036F));
        ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create()
                .uv(54, 42).cuboid(-5.5F, -0.25F, -2.0F, 15.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(46, 60).cuboid(-5.564F, -1.75F, -2.0F, 15.0F, 2.0F, 4.0F, new Dilation(-0.09F)), ModelTransform.of(3.75F, 3.25F, 0.0F, 0.0F, 0.0F, 0.2618F));
        jaw.addChild("tongue", ModelPartBuilder.create().uv(8, 78).cuboid(-1.8F, 0.1F, -1.5F, 9.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -0.25F, 0.0F, 0.0F, 0.0F, -0.0349F));
        head.addChild("headmane", ModelPartBuilder.create().uv(68, 66).cuboid(-7.5F, -5.0F, 0.0F, 15.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-1.0F, -4.25F, 0.0F));

        ModelPartData waitTail = bodyNoLegs.addChild("wait_tail", ModelPartBuilder.create().uv(0, 28).cuboid(-0.3F, -2.0F, -5.5F, 16.0F, 10.0F, 11.0F, new Dilation(0.0F)), ModelTransform.origin(-20.5F, -3.75F, 0.5F));
        waitTail.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));
        ModelPartData tail = waitTail.getChild("tail");
        tail.addChild("bone fluff_r1", ModelPartBuilder.create()
                .uv(0, 108).cuboid(-13.0F, -5.0F, -0.75F, 15.0F, 2.0F, 1.0F, new Dilation(0.4F))
                .uv(0, 72).cuboid(-13.0F, -5.0F, -0.75F, 15.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(46, 49).cuboid(-18.0F, -6.0F, -2.75F, 20.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-1.5979F, 4.1769F, 0.25F, 0.0F, 0.0F, 0.1745F));

        upperBody.addChild("right_front_leg", ModelPartBuilder.create().uv(30, 72).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new Dilation(0.01F)), ModelTransform.origin(9.0F, 4.0F, -2.5F));
        upperBody.addChild("left_front_leg", ModelPartBuilder.create().uv(30, 89).cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 13.0F, 4.0F, new Dilation(0.01F)), ModelTransform.origin(9.0F, 4.0F, 5.0F));

        ModelPartData backBody = body.addChild("back_body", ModelPartBuilder.create(), ModelTransform.origin(2.3142F, -2.1138F, -0.5F));
        ModelPartData leftBackLeg = backBody.addChild("left_back_leg", ModelPartBuilder.create().uv(46, 66).cuboid(-3.5F, -1.0F, -1.0F, 7.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-14.7F, -2.0F, 3.5F));
        leftBackLeg.addChild("left_back_foot", ModelPartBuilder.create().uv(0, 78).cuboid(-2.0F, -2.0F, -1.5F, 4.0F, 12.0F, 3.0F, new Dilation(0.01F)), ModelTransform.origin(-3.2F, 10.0F, 0.5F));
        ModelPartData rightBackLeg = backBody.addChild("right_back_leg", ModelPartBuilder.create().uv(46, 66).cuboid(-4.5F, -1.0F, -4.0F, 7.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-13.7F, -2.0F, -2.5F));
        rightBackLeg.addChild("right_back_foot", ModelPartBuilder.create().uv(0, 93).cuboid(-2.0F, -2.0F, -1.5F, 4.0F, 12.0F, 3.0F, new Dilation(0.01F)), ModelTransform.origin(-4.2F, 10.0F, -1.5F));

        return modelData;
    }

    public static ModelData getEmptyModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));
        ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(-2.6142F, 2.1138F, 1.5F));
        ModelPartData upperBody = body.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.origin(-1.3858F, -1.1138F, -1.5F));
        ModelPartData bodyNoLegs = upperBody.addChild("body_no_legs", ModelPartBuilder.create(), ModelTransform.origin(7.5F, -0.25F, 0.5F));
        ModelPartData bodyFur = bodyNoLegs.addChild("body_fur", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        bodyFur.addChild("mane", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, -6.75F, 0.5F));
        bodyNoLegs.addChild("main_body", ModelPartBuilder.create(), ModelTransform.origin(-5.3F, 0.25F, 0.0F));

        ModelPartData head = bodyNoLegs.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(7.25F, -2.0F, 0.5F));
        head.addChild("snout", ModelPartBuilder.create(), ModelTransform.origin(8.0F, -1.5F, 0.5F));
        head.addChild("ear_right", ModelPartBuilder.create(), ModelTransform.of(4.0F, -2.25F, -4.478F, 0.0F, -0.8727F, 0.0F));
        head.addChild("ear_left", ModelPartBuilder.create(), ModelTransform.of(4.0F, -2.25F, 4.522F, 0.0F, 0.8727F, 0.0F));
        ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create(), ModelTransform.of(3.75F, 3.25F, 0.0F, 0.0F, 0.0F, 0.2618F));
        jaw.addChild("tongue", ModelPartBuilder.create(), ModelTransform.of(2.0F, -0.25F, 0.0F, 0.0F, 0.0F, -0.0349F));
        head.addChild("headmane", ModelPartBuilder.create(), ModelTransform.origin(-1.0F, -4.25F, 0.0F));

        ModelPartData waitTail = bodyNoLegs.addChild("wait_tail", ModelPartBuilder.create(), ModelTransform.origin(-20.5F, -3.75F, 0.5F));
        waitTail.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));

        upperBody.addChild("right_front_leg", ModelPartBuilder.create(), ModelTransform.origin(9.0F, 4.0F, -2.5F));
        upperBody.addChild("left_front_leg", ModelPartBuilder.create(), ModelTransform.origin(9.0F, 4.0F, 5.0F));

        ModelPartData backBody = body.addChild("back_body", ModelPartBuilder.create(), ModelTransform.origin(2.3142F, -2.1138F, -0.5F));
        ModelPartData leftBackLeg = backBody.addChild("left_back_leg", ModelPartBuilder.create(), ModelTransform.origin(-14.7F, -2.0F, 3.5F));
        leftBackLeg.addChild("left_back_foot", ModelPartBuilder.create(), ModelTransform.origin(-3.2F, 10.0F, 0.5F));
        ModelPartData rightBackLeg = backBody.addChild("right_back_leg", ModelPartBuilder.create(), ModelTransform.origin(-13.7F, -2.0F, -2.5F));
        rightBackLeg.addChild("right_back_foot", ModelPartBuilder.create(), ModelTransform.origin(-4.2F, 10.0F, -1.5F));

        return modelData;
    }

    @Override
    public void setAngles(WargEntityRenderState state) {
        super.setAngles(state);

        boolean running = state.isRunning || state.isSprinting
                || (state.conrollingPassenger != null && state.conrollingPassenger.isSprinting())
                || state.isCharging;

        if(!running) {
            this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2f, 2f);
        }
        else {
            this.runningAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 1.2f, 1.2f);
        }

        this.idleAnimation.apply(state.idleAnimationState, state.age);
        this.bitingAnimation.apply(state.attackAnimationState, state.age);
        this.startSittingAnimation.apply(state.startSittingAnimationState, state.age);
        this.stopSittingAnimation.apply(state.stopSittingAnimationState, state.age);
        this.sittingAnimation.apply(state.sittingAnimationState, state.age);
    }
}