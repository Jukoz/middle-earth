package net.jukoz.me.entity.beasts.broadhoof;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BroadhoofGoatModel extends SinglePartEntityModel<BroadhoofGoatEntity> {
    private final ModelPart broadhoofGoat;
    private final ModelPart horns;
    private final ModelPart[] leftHorns = new ModelPart[BroadhoofGoatHorns.values().length];
    private final ModelPart[] rightHorns = new ModelPart[BroadhoofGoatHorns.values().length];
    public BroadhoofGoatModel(ModelPart root) {
        this.broadhoofGoat = root.getChild("broadhoof_goat");

        this.horns = broadhoofGoat.getChild(EntityModelPartNames.BODY).getChild(EntityModelPartNames.HEAD).getChild("horns");

        this.leftHorns[0] = horns.getChild("tiny_left_horn_r1");
        this.rightHorns[0] = horns.getChild("tiny_right_horn_r1");

        this.leftHorns[1] = horns.getChild("normal_left_horn_r1");
        this.rightHorns[1] = horns.getChild("normal_right_horn_r1");

        this.leftHorns[2] = horns.getChild("long_left_horn_r1");
        this.rightHorns[2] = horns.getChild("long_right_horn_r1");

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

        ModelPartData beard_r1 = head.addChild("beard_r1", ModelPartBuilder.create().uv(3, 47).mirrored().cuboid(0.0F, -5.0F, -2.0F, 0.0F, 11.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.0F, -4.0F, 2.9232F, 0.0F, 3.1321F));

        ModelPartData neck_r1 = head.addChild("neck_r1", ModelPartBuilder.create().uv(36, 2).cuboid(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 2.1213F, -1.636F, 0.7854F, 0.0F, 0.0F));

        ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData horns = head.addChild("horns", ModelPartBuilder.create(), ModelTransform.pivot(-11.0F, -11.0208F, -7.4645F));

        ModelPartData long_left_horn_r1 = horns.addChild("long_left_horn_r1", ModelPartBuilder.create().uv(0, 113).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 2.0F, 1.4443F, 0.0338F, 0.2597F));

        ModelPartData normal_left_horn_r1 = horns.addChild("normal_left_horn_r1", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData tiny_left_horn_r1 = horns.addChild("tiny_left_horn_r1", ModelPartBuilder.create().uv(28, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData curly_left_horn = horns.addChild("curly_left_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData curly_left_horn5_r1 = curly_left_horn.addChild("curly_left_horn5_r1", ModelPartBuilder.create().uv(58, 107).cuboid(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(57, 102).cuboid(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(59, 95).cuboid(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(57, 90).cuboid(1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(56, 82).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(14.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData swirly_left_horn = horns.addChild("swirly_left_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData swirly_left_horn_tip_r1 = swirly_left_horn.addChild("swirly_left_horn_tip_r1", ModelPartBuilder.create().uv(21, 80).cuboid(0.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(1, 81).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData wide_left_horn = horns.addChild("wide_left_horn", ModelPartBuilder.create(), ModelTransform.of(13.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.3491F));

        ModelPartData wide_left_horn_tip_r1 = wide_left_horn.addChild("wide_left_horn_tip_r1", ModelPartBuilder.create().uv(59, 117).mirrored().cuboid(-11.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(35, 119).mirrored().cuboid(-21.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(17, 115).mirrored().cuboid(-21.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData huge_left_horn = horns.addChild("huge_left_horn", ModelPartBuilder.create(), ModelTransform.of(13.0F, 0.0F, 1.0F, 0.0792F, 0.1004F, 0.4055F));

        ModelPartData huge_left_horn4_r1 = huge_left_horn.addChild("huge_left_horn4_r1", ModelPartBuilder.create().uv(85, 88).cuboid(-1.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(86, 95).cuboid(-1.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new Dilation(0.0F))
                .uv(67, 94).cuboid(-1.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new Dilation(0.0F))
                .uv(73, 109).cuboid(-1.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData long_right_horn_r1 = horns.addChild("long_right_horn_r1", ModelPartBuilder.create().uv(0, 113).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(9.0F, -1.0F, 2.0F, 1.4443F, -0.0338F, -0.2597F));

        ModelPartData normal_right_horn_r1 = horns.addChild("normal_right_horn_r1", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData tiny_right_horn_r1 = horns.addChild("tiny_right_horn_r1", ModelPartBuilder.create().uv(28, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData curly_right_horn = horns.addChild("curly_right_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData curly_right_horn5_r1 = curly_right_horn.addChild("curly_right_horn5_r1", ModelPartBuilder.create().uv(37, 108).cuboid(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 102).cuboid(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(37, 95).cuboid(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(35, 90).cuboid(3.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(37, 82).cuboid(7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData swirly_right_horn = horns.addChild("swirly_right_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData swirly_right_horn_tip_r1 = swirly_right_horn.addChild("swirly_right_horn_tip_r1", ModelPartBuilder.create().uv(21, 94).cuboid(-8.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(1, 95).cuboid(-8.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData wide_right_horn = horns.addChild("wide_right_horn", ModelPartBuilder.create(), ModelTransform.of(9.0F, 0.0F, 0.0F, 0.0F, 0.4363F, -0.3491F));

        ModelPartData wide_right_horn_tip_r1 = wide_right_horn.addChild("wide_right_horn_tip_r1", ModelPartBuilder.create().uv(59, 117).cuboid(9.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(35, 119).cuboid(11.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(17, 115).cuboid(18.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData huge_right_horn = horns.addChild("huge_right_horn", ModelPartBuilder.create(), ModelTransform.of(9.0F, 0.0F, 1.0F, 0.0792F, -0.1004F, -0.4055F));

        ModelPartData huge_right_horn4_r1 = huge_right_horn.addChild("huge_right_horn4_r1", ModelPartBuilder.create().uv(85, 88).mirrored().cuboid(-2.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
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
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        broadhoofGoat.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return broadhoofGoat;
    }

    @Override
    public void setAngles(BroadhoofGoatEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        for(int i = 0; i < BroadhoofGoatHorns.values().length; i++) {
            this.leftHorns[i].visible = (entity.getHorns().getId() == i) && entity.hasLeftHorn();
            this.rightHorns[i].visible = (entity.getHorns().getId() == i) && entity.hasRightHorn();
        }
    }
}