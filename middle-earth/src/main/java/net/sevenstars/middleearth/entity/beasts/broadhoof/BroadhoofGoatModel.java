package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

public class BroadhoofGoatModel extends EntityModel<BroadhoofGoatEntityRenderState> {
    private final ModelPart broadhoofGoat;
    private final ModelPart head;
    private final ModelPart horns;
    private final ModelPart wildBeard;
    private final ModelPart brushedBeard;
    private final ModelPart[] leftHorns = new ModelPart[BroadhoofGoatHorns.values().length];
    private final ModelPart[] rightHorns = new ModelPart[BroadhoofGoatHorns.values().length];

    public BroadhoofGoatModel(ModelPart root) {
        super(root);

        this.broadhoofGoat = root.getChild("broadhoof_goat");
        this.head = broadhoofGoat.getChild(EntityModelPartNames.BODY).getChild(EntityModelPartNames.HEAD);

        this.wildBeard = this.head.getChild("wild_beard");
        this.brushedBeard = this.head.getChild("brushed_beard");

        this.horns = this.head.getChild("horns");

        this.leftHorns[0] = horns.getChild("tiny_left_horn");
        this.rightHorns[0] = horns.getChild("tiny_right_horn");

        this.leftHorns[1] = horns.getChild("normal_left_horn");
        this.rightHorns[1] = horns.getChild("normal_right_horn");

        this.leftHorns[2] = horns.getChild("long_left_horn");
        this.rightHorns[2] = horns.getChild("long_right_horn");

        this.leftHorns[3] = horns.getChild("curly_left_horn");
        this.rightHorns[3] = horns.getChild("curly_right_horn");

        this.leftHorns[4] = horns.getChild("swirly_left_horn");
        this.rightHorns[4] = horns.getChild("swirly_right_horn");

        this.leftHorns[5] = horns.getChild("wide_left_horn");
        this.rightHorns[5] = horns.getChild("wide_right_horn");

        this.leftHorns[6] = horns.getChild("huge_left_horn");
        this.rightHorns[6] = horns.getChild("huge_right_horn");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData broadhoof_goat = modelPartData.addChild("broadhoof_goat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = broadhoof_goat.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(2, 48).cuboid(-6.0F, -9.0F, -11.0F, 12.0F, 11.0F, 20.0F, new Dilation(0.0F))
                .uv(0, 18).cuboid(-7.0F, -11.0F, -12.0F, 14.0F, 16.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, 2.0F));

        ModelPartData tail_r1 = body.addChild("tail_r1", ModelPartBuilder.create().uv(86, 1).cuboid(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 9.0F, 0.4363F, 0.0F, 0.0F));

        ModelPartData head = body.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(2.9F, -11.0F, -6.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 0).cuboid(-5.9F, -11.0F, -6.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, -9.0F, -0.2182F, 0.0F, 0.0F));

        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(100, 91).cuboid(-4.0F, -2.0F, -1.0F, 8.0F, 7.0F, 6.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, -9.0F, -9.0F, 0.3054F, 0.0F, 0.0F));

        ModelPartData wild_beard = head.addChild("wild_beard", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, -4.0F));

        ModelPartData wild_beard_r1 = wild_beard.addChild("wild_beard_r1", ModelPartBuilder.create().uv(3, 47).mirrored().cuboid(0.0F, -5.0F, -2.0F, 0.0F, 11.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 2.9232F, 0.0F, 3.1321F));

        ModelPartData brushed_beard = head.addChild("brushed_beard", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -7.0F));

        ModelPartData brushed_beard_r1 = brushed_beard.addChild("brushed_beard_r1", ModelPartBuilder.create().uv(54, 31).cuboid(-3.0F, -2.0F, -2.0F, 6.0F, 10.0F, 5.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        ModelPartData neck_r1 = head.addChild("neck_r1", ModelPartBuilder.create().uv(36, 2).cuboid(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 2.1213F, -1.636F, 0.7854F, 0.0F, 0.0F));

        ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData horns = head.addChild("horns", ModelPartBuilder.create(), ModelTransform.pivot(-11.0F, -11.0208F, -7.4645F));

        ModelPartData long_left_horn = horns.addChild("long_left_horn", ModelPartBuilder.create().uv(0, 113).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 2.0F, 1.4443F, 0.0338F, 0.2597F));

        ModelPartData normal_left_horn = horns.addChild("normal_left_horn", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData tiny_left_horn = horns.addChild("tiny_left_horn", ModelPartBuilder.create().uv(28, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData curly_left_horn = horns.addChild("curly_left_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData curly_left_horn5_r1 = curly_left_horn.addChild("curly_left_horn5_r1", ModelPartBuilder.create().uv(58, 107).cuboid(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(57, 102).cuboid(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(59, 95).cuboid(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(57, 90).cuboid(1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(56, 82).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(14.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData swirly_left_horn = horns.addChild("swirly_left_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData swirly_left_horn_tip_r1 = swirly_left_horn.addChild("swirly_left_horn_tip_r1", ModelPartBuilder.create().uv(21, 80).cuboid(0.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(1, 81).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData wide_left_horn = horns.addChild("wide_left_horn", ModelPartBuilder.create(), ModelTransform.of(13.0F, 0.6F, -0.1F, 0.2012F, -0.2955F, 0.2578F));

        ModelPartData wide_left_horn_tip_r1 = wide_left_horn.addChild("wide_left_horn_tip_r1", ModelPartBuilder.create().uv(59, 117).mirrored().cuboid(-11.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(35, 119).mirrored().cuboid(-21.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(17, 115).mirrored().cuboid(-21.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData huge_left_horn = horns.addChild("huge_left_horn", ModelPartBuilder.create(), ModelTransform.of(12.6F, 1.0F, -0.5F, 0.0576F, -0.2575F, 0.287F));

        ModelPartData huge_left_horn4_r1 = huge_left_horn.addChild("huge_left_horn4_r1", ModelPartBuilder.create().uv(85, 88).cuboid(-1.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(86, 95).cuboid(-1.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new Dilation(0.0F))
                .uv(67, 94).cuboid(-1.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new Dilation(0.0F))
                .uv(73, 109).cuboid(-1.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData long_right_horn = horns.addChild("long_right_horn", ModelPartBuilder.create().uv(0, 113).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(9.0F, -1.0F, 2.0F, 1.4443F, -0.0338F, -0.2597F));

        ModelPartData normal_right_horn = horns.addChild("normal_right_horn", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData tiny_right_horn = horns.addChild("tiny_right_horn", ModelPartBuilder.create().uv(28, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData curly_right_horn = horns.addChild("curly_right_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData curly_right_horn5_r1 = curly_right_horn.addChild("curly_right_horn5_r1", ModelPartBuilder.create().uv(37, 108).cuboid(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 102).cuboid(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(37, 95).cuboid(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(35, 90).cuboid(3.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(37, 82).cuboid(7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData swirly_right_horn = horns.addChild("swirly_right_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData swirly_right_horn_tip_r1 = swirly_right_horn.addChild("swirly_right_horn_tip_r1", ModelPartBuilder.create().uv(21, 94).cuboid(-8.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(1, 95).cuboid(-8.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData wide_right_horn = horns.addChild("wide_right_horn", ModelPartBuilder.create(), ModelTransform.of(9.0F, 0.6F, -0.1F, 0.2012F, 0.2955F, -0.2578F));

        ModelPartData wide_right_horn_tip_r1 = wide_right_horn.addChild("wide_right_horn_tip_r1", ModelPartBuilder.create().uv(59, 117).cuboid(9.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(35, 119).cuboid(11.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(17, 115).cuboid(18.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData huge_right_horn = horns.addChild("huge_right_horn", ModelPartBuilder.create(), ModelTransform.of(9.4F, 1.0F, -0.5F, 0.0576F, 0.2575F, -0.287F));

        ModelPartData huge_right_horn5_r1 = huge_right_horn.addChild("huge_right_horn5_r1", ModelPartBuilder.create().uv(85, 88).mirrored().cuboid(-2.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
                .uv(86, 95).mirrored().cuboid(-2.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(67, 94).mirrored().cuboid(-2.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
                .uv(73, 109).mirrored().cuboid(-2.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));


        ModelPartData left_front_leg = broadhoof_goat.addChild("left_front_leg", ModelPartBuilder.create().uv(43, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -9.0F, -6.0F));

        ModelPartData left_hind_leg = broadhoof_goat.addChild("left_hind_leg", ModelPartBuilder.create().uv(61, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -9.0F, 7.0F));

        ModelPartData right_front_leg = broadhoof_goat.addChild("right_front_leg", ModelPartBuilder.create().uv(43, 17).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-4.0F, -9.0F, -6.0F));

        ModelPartData right_hind_leg = broadhoof_goat.addChild("right_hind_leg", ModelPartBuilder.create().uv(61, 17).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.0F, -9.0F, 7.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BroadhoofGoatEntityRenderState state) {
        super.setAngles(state);
        this.setHeadAngles(state.yawDegrees, state.pitch);

        /* I will drastically change how the horns are rendered
        for(int i = 0 ; i < BroadhoofGoatHorns.values().length; i++) {
            this.leftHorns[i].visible = (entity.getHorns().getId() == i) && entity.hasLeftHorn() && !entity.isBaby();
            this.rightHorns[i].visible = (entity.getHorns().getId() == i) && entity.hasRightHorn() && !entity.isBaby();
        }*/

        this.wildBeard.visible = !state.beardBrushed;
        this.brushedBeard.visible = state.beardBrushed;

        /* Will be readded on refactor
        if((entity.hasControllingPassenger() && entity.getControllingPassenger().isSprinting()) || (entity.isAttacking() && !entity.hasControllingPassenger())) {
            this.animateMovement(BroadhoofGoatAnimations.RUN, limbAngle, limbDistance, 1.2f, 1.2f);
        }
        else {
            this.animateMovement(BroadhoofGoatAnimations.WALK, limbAngle, limbDistance, 4f, 4f);
        }*/

        animateWalking(BroadhoofGoatAnimations.RUN, state.limbFrequency, state.limbAmplitudeMultiplier, 1.0f, 2.5f);
        animate(state.idleAnimationState, BroadhoofGoatAnimations.EAT, state.age);
        animate(state.attackAnimationState, BroadhoofGoatAnimations.RAM_ATTACK, state.age);
        animate(state.startSittingAnimationState, BroadhoofGoatAnimations.LAY_DOWN, state.age);
        animate(state.stopSittingAnimationState, BroadhoofGoatAnimations.STAND_UP, state.age);
        animate(state.sittingAnimationState, BroadhoofGoatAnimations.LYING, state.age);
        animate(state.chargeAnimationState, BroadhoofGoatAnimations.CHARGE_ATTACK, state.age);
        animate(state.jumpAnimationState, BroadhoofGoatAnimations.JUMP, state.age);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}