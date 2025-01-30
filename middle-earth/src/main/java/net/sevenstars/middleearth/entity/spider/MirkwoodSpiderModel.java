package net.sevenstars.middleearth.entity.spider;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class MirkwoodSpiderModel extends EntityModel<LivingEntityRenderState> {
    private static final float LEGS_MARGIN_ANGLE = 0.18f;
    private static final float LEGS_MARGIN_ANGLE_MULTIPLIER = 2f;
    private static final float UPPER_LEGS_ROLL = 0.6f;
    private static final float BOTTOM_LEGS_ROLL = -1.3f;
    private final ModelPart body;
    private final ModelPart leg_right;
    private final ModelPart leg1;
    private final ModelPart bottom_leg1;
    private final ModelPart leg2;
    private final ModelPart bottom_leg2;
    private final ModelPart leg3;
    private final ModelPart bottom_leg3;
    private final ModelPart leg4;
    private final ModelPart bottom_leg4;
    private final ModelPart leg_left;
    private final ModelPart leg5;
    private final ModelPart bottom_leg5;
    private final ModelPart leg6;
    private final ModelPart bottom_leg6;
    private final ModelPart leg7;
    private final ModelPart bottom_leg7;
    private final ModelPart leg8;
    private final ModelPart bottom_leg8;
    private final ModelPart thorax;
    private final ModelPart abdomen;
    private final ModelPart face;
    private final ModelPart fang1;
    private final ModelPart cube_r1;
    private final ModelPart fang2;
    private final ModelPart cube_r2;
    private final ModelPart eyes;

    private List<ModelPart> evenLegs = new ArrayList<>();
    private List<ModelPart> oddLegs = new ArrayList<>();

    private List<ModelPart> evenBottomLegs = new ArrayList<>();
    private List<ModelPart> oddBottomLegs = new ArrayList<>();
    private List<ModelPart> legs = new ArrayList<>();

    public MirkwoodSpiderModel(ModelPart root) {
        super(root);

        this.body = root.getChild("body");
        this.leg_right = this.body.getChild("leg_right");
        this.leg1 = leg_right.getChild("leg1");
        this.leg2 = leg_right.getChild("leg2");
        this.leg3 = leg_right.getChild("leg3");
        this.leg4 = leg_right.getChild("leg4");

        this.bottom_leg1 = leg1.getChild("bottom_leg1");
        this.bottom_leg2 = leg2.getChild("bottom_leg2");
        this.bottom_leg3 = leg3.getChild("bottom_leg3");
        this.bottom_leg4 = leg4.getChild("bottom_leg4");

        this.leg_left = this.body.getChild("leg_left");
        this.leg5 = leg_left.getChild("leg5");
        this.leg6 = leg_left.getChild("leg6");
        this.leg7 = leg_left.getChild("leg7");
        this.leg8 = leg_left.getChild("leg8");

        this.bottom_leg5 = leg5.getChild("bottom_leg5");
        this.bottom_leg6 = leg6.getChild("bottom_leg6");
        this.bottom_leg7 = leg7.getChild("bottom_leg7");
        this.bottom_leg8 = leg8.getChild("bottom_leg8");

        this.thorax = this.body.getChild("thorax");
        this.abdomen = thorax.getChild("abdomen");

        this.face = this.body.getChild("face");
        this.fang1 = face.getChild("fang1");
        this.fang2 = face.getChild("fang2");
        this.cube_r1 = fang1.getChild("cube_r1");
        this.cube_r2 = fang2.getChild("cube_r2");
        this.eyes = face.getChild("eyes");

        evenLegs.add(leg2);
        evenLegs.add(leg4);
        evenLegs.add(leg6);
        evenLegs.add(leg8);
        oddLegs.add(leg1);
        oddLegs.add(leg3);
        oddLegs.add(leg5);
        oddLegs.add(leg7);

        evenBottomLegs.add(bottom_leg2);
        evenBottomLegs.add(bottom_leg4);
        evenBottomLegs.add(bottom_leg6);
        evenBottomLegs.add(bottom_leg8);
        oddBottomLegs.add(bottom_leg1);
        oddBottomLegs.add(bottom_leg3);
        oddBottomLegs.add(bottom_leg5);
        oddBottomLegs.add(bottom_leg7);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, -5.5F));

        ModelPartData leg_right = body.addChild("leg_right", ModelPartBuilder.create().uv(13, 45).cuboid(-3.0F, -5.0F, -10.0F, 1.0F, 3.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 2.0F, 5.5F));

        ModelPartData leg1 = leg_right.addChild("leg1", ModelPartBuilder.create().uv(33, 8).cuboid(-11.8F, -3.0F, -1.0F, 13.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-3.2F, -1.5F, -0.2F));

        ModelPartData bottom_leg1 = leg1.addChild("bottom_leg1", ModelPartBuilder.create().uv(22, 21).cuboid(-17.5F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-11.4F, -2.0F, 0.0F));

        ModelPartData leg2 = leg_right.addChild("leg2", ModelPartBuilder.create().uv(46, 16).cuboid(-9.8F, -3.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-3.2F, -1.5F, -3.0F));

        ModelPartData bottom_leg2 = leg2.addChild("bottom_leg2", ModelPartBuilder.create().uv(22, 25).cuboid(-17.5F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-9.4F, -2.0F, 0.0F));

        ModelPartData leg3 = leg_right.addChild("leg3", ModelPartBuilder.create().uv(46, 12).cuboid(-9.8F, -3.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-3.2F, -1.5F, -5.8F));

        ModelPartData bottom_leg3 = leg3.addChild("bottom_leg3", ModelPartBuilder.create().uv(22, 21).cuboid(-17.5F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-9.4F, -2.0F, 0.0F));

        ModelPartData leg4 = leg_right.addChild("leg4", ModelPartBuilder.create().uv(33, 4).cuboid(-11.8F, -3.0F, -1.0F, 13.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-3.2F, -1.5F, -8.5F));

        ModelPartData bottom_leg4 = leg4.addChild("bottom_leg4", ModelPartBuilder.create().uv(22, 25).cuboid(-17.5F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-11.4F, -2.0F, 0.0F));

        ModelPartData leg_left = body.addChild("leg_left", ModelPartBuilder.create().uv(0, 37).cuboid(3.1F, -5.1F, -10.0F, 1.0F, 3.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(7.1F, 2.1F, -3.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData leg5 = leg_left.addChild("leg5", ModelPartBuilder.create().uv(33, 4).cuboid(-11.9F, -3.0F, -1.0F, 13.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(3.0F, -1.6F, -0.2F));

        ModelPartData bottom_leg5 = leg5.addChild("bottom_leg5", ModelPartBuilder.create().uv(22, 25).cuboid(-17.4F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-11.6F, -2.0F, 0.0F));

        ModelPartData leg6 = leg_left.addChild("leg6", ModelPartBuilder.create().uv(26, 45).cuboid(-9.9F, -3.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(3.0F, -1.6F, -3.0F));

        ModelPartData bottom_leg6 = leg6.addChild("bottom_leg6", ModelPartBuilder.create().uv(22, 21).cuboid(-17.4F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-9.6F, -2.0F, 0.0F));

        ModelPartData leg7 = leg_left.addChild("leg7", ModelPartBuilder.create().uv(46, 16).cuboid(-9.9F, -3.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(3.0F, -1.6F, -5.8F));

        ModelPartData bottom_leg7 = leg7.addChild("bottom_leg7", ModelPartBuilder.create().uv(22, 25).cuboid(-17.4F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-9.6F, -2.0F, 0.0F));

        ModelPartData leg8 = leg_left.addChild("leg8", ModelPartBuilder.create().uv(33, 0).cuboid(-11.9F, -3.0F, -1.0F, 13.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(3.0F, -1.6F, -8.5F));

        ModelPartData bottom_leg8 = leg8.addChild("bottom_leg8", ModelPartBuilder.create().uv(22, 21).cuboid(-17.4F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new Dilation(-0.05F)), ModelTransform.pivot(-11.6F, -2.0F, 0.0F));

        ModelPartData thorax = body.addChild("thorax", ModelPartBuilder.create().uv(0, 21).cuboid(-2.0F, -6.0F, -9.0F, 6.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 0.0F, 5.5F));

        ModelPartData abdomen = thorax.addChild("abdomen", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -6.0F, -0.5F, 10.0F, 8.0F, 13.0F, new Dilation(0.5F)), ModelTransform.pivot(1.0F, -2.0F, 1.5F));

        ModelPartData face = body.addChild("face", ModelPartBuilder.create().uv(24, 29).cuboid(-3.0F, -10.0F, -14.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.01F)), ModelTransform.pivot(-1.0F, 2.0F, 3.5F));

        ModelPartData fang1 = face.addChild("fang1", ModelPartBuilder.create().uv(0, 5).cuboid(-0.75F, -0.5F, -6.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.1F)), ModelTransform.of(-1.5F, -3.75F, -10.0F, 0.0F, 0.0F, -0.2182F));

        ModelPartData cube_r1 = fang1.addChild("cube_r1", ModelPartBuilder.create().uv(8, 0).cuboid(1.0F, -3.2961F, -5.5433F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-1.25F, 3.5F, 0.5F, 0.3927F, 0.0F, 0.0F));

        ModelPartData fang2 = face.addChild("fang2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.25F, -0.5F, -5.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.1F)), ModelTransform.of(3.5F, -3.75F, -11.0F, 0.0F, 0.0F, 0.2182F));

        ModelPartData cube_r2 = fang2.addChild("cube_r2", ModelPartBuilder.create().uv(7, 4).cuboid(1.0F, -3.2961F, -5.5433F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-1.75F, 3.5F, 1.5F, 0.3927F, 0.0F, 0.0F));

        ModelPartData eyes = face.addChild("eyes", ModelPartBuilder.create().uv(6, 21).cuboid(1.25F, -5.75F, -12.5F, 1.0F, 1.0F, 1.0F, new Dilation(-0.1F))
                .uv(7, 11).cuboid(-0.5F, -7.3F, -12.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.3F))
                .uv(4, 10).cuboid(-2.25F, -7.25F, -10.9F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-0.25F, -5.75F, -12.5F, 1.0F, 1.0F, 1.0F, new Dilation(-0.1F))
                .uv(6, 25).cuboid(-2.25F, -6.0F, -12.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(7, 9).cuboid(3.25F, -7.25F, -10.9F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 29).cuboid(3.25F, -6.0F, -12.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 7).cuboid(1.5F, -7.3F, -12.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(LivingEntityRenderState state) {
        super.setAngles(state);
    }

    /*
    @Override
    public void setAngles(MirkwoodSpiderEntity entity, float limbAngle, float limbDistance, float ageInTicks, float headYaw, float headPitch) {
        float cosVal = (MathHelper.cos(limbAngle * 1.4F) * 0.3F) * limbDistance;
        float cosTime = MathHelper.cos(ageInTicks * 0.15F) * 0.6F;

        float percentage = (float) entity.getClimbingTicks() / MirkwoodSpiderEntity.CLIMBING_TIME_TRANSITION;

        if(percentage > 0) {
            cosVal = MathHelper.cos(ageInTicks * 0.3F) * 0.6F;

        }

        for(ModelPart leg : oddLegs) {
            leg.roll = UPPER_LEGS_ROLL + (cosVal / 3);
            leg.pitch = cosVal;
            leg.yaw = cosVal / 4;
        }
        for(ModelPart leg : evenLegs) {
            leg.roll = UPPER_LEGS_ROLL - (cosVal / 3);
            leg.pitch = -cosVal;
            leg.yaw = -cosVal / 4;
        }

        for(ModelPart bottomLeg : oddBottomLegs) {
            bottomLeg.roll = BOTTOM_LEGS_ROLL;
            bottomLeg.pitch = -cosVal;
            bottomLeg.yaw = 0;
        }
        for(ModelPart bottomLeg : evenBottomLegs) {
            bottomLeg.roll = BOTTOM_LEGS_ROLL;
            bottomLeg.pitch = cosVal;
            bottomLeg.yaw = 0;
        }

        this.leg1.yaw += LEGS_MARGIN_ANGLE * LEGS_MARGIN_ANGLE_MULTIPLIER;
        this.leg2.yaw += LEGS_MARGIN_ANGLE;
        this.leg5.yaw += 0.15f + LEGS_MARGIN_ANGLE * LEGS_MARGIN_ANGLE_MULTIPLIER;
        this.leg6.yaw += LEGS_MARGIN_ANGLE;
        this.leg3.yaw -= LEGS_MARGIN_ANGLE;
        this.leg4.yaw -= 0.15f + LEGS_MARGIN_ANGLE * LEGS_MARGIN_ANGLE_MULTIPLIER;
        this.leg7.yaw -= LEGS_MARGIN_ANGLE;
        this.leg8.yaw -= LEGS_MARGIN_ANGLE * LEGS_MARGIN_ANGLE_MULTIPLIER;

        this.bottom_leg1.yaw += 0.2f;
        this.bottom_leg4.yaw -= 0.2f;
        this.bottom_leg5.yaw += 0.2f;
        this.bottom_leg8.yaw -= 0.2f;

        this.fang1.roll = cosTime / 8;
        this.fang2.roll = -cosTime / 8;
        this.fang1.pitch = -cosVal / 5;
        this.fang2.pitch = cosVal / 5;

        this.abdomen.pitch = -0.15f + (cosVal / 7);

        this.body.pitch = -1.5f * percentage;
        this.abdomen.pitch += (0.7f * percentage) + (0.04 * cosTime);
    }
     */

}
