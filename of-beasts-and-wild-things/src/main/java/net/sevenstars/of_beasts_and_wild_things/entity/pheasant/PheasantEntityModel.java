package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class PheasantEntityModel extends EntityModel<PheasantEntityRenderState> {

    public PheasantEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData pheasant = modelPartData.addChild("pheasant", ModelPartBuilder.create(), ModelTransform.pivot(2.4F, 24.0F, -3.5F));

        ModelPartData upper_body = pheasant.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -4.0F, 3.0F));

        ModelPartData body = upper_body.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(2.5F, 4.0F, -3.0F));

        ModelPartData body_front_r1 = body.addChild("body_front_r1", ModelPartBuilder.create().uv(27, 0).cuboid(-2.5946F, -2.513F, -3.2795F, 5.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-2.4F, -6.4F, 3.04F, -0.2618F, 0.0F, 0.0F));

        ModelPartData body_back_r1 = body.addChild("body_back_r1", ModelPartBuilder.create().uv(27, 13).cuboid(-1.9591F, -2.5521F, 0.0991F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -4.96F, 5.28F, 0.1745F, 0.0F, 0.0F));

        ModelPartData head_and_neck = upper_body.addChild("head_and_neck", ModelPartBuilder.create(), ModelTransform.of(0.0F, -3.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

        ModelPartData head = head_and_neck.addChild("head", ModelPartBuilder.create().uv(26, 0).cuboid(-0.52F, -1.24F, -3.28F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.56F, -2.92F, -2.4F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(44, 0).cuboid(-1.56F, 0.08F, -2.4F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, -5.0714F, -0.8695F, -0.1309F, 0.0F, 0.0F));

        ModelPartData top_feather_r1 = head.addChild("top_feather_r1", ModelPartBuilder.create().uv(15, 0).cuboid(-1.0F, -1.16F, -0.64F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.1305F, 0.0F, 0.0F));

        ModelPartData neck = head_and_neck.addChild("neck", ModelPartBuilder.create().uv(0, 8).cuboid(-0.94F, -5.4F, -0.88F, 2.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.9914F, -0.8695F));

        ModelPartData tail = upper_body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 1.0F));

        ModelPartData tail_feather4_r1 = tail.addChild("tail_feather4_r1", ModelPartBuilder.create().uv(2, 7).cuboid(-1.5F, -0.52F, 1.2F, 3.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 2.0025F, 0.1773F, 0.0F, 0.0F));

        ModelPartData tail_feather3_r1 = tail.addChild("tail_feather3_r1", ModelPartBuilder.create().uv(2, 7).cuboid(-1.5001F, -0.9949F, 1.2189F, 3.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 2.0025F, 0.2181F, 0.0F, 0.0F));

        ModelPartData tail_feather2_r1 = tail.addChild("tail_feather2_r1", ModelPartBuilder.create().uv(2, 7).cuboid(-1.4743F, -0.9196F, 0.683F, 3.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 2.0025F, 0.3053F, 0.0F, 0.0F));

        ModelPartData tail_feather1_r1 = tail.addChild("tail_feather1_r1", ModelPartBuilder.create().uv(1, 26).cuboid(-1.4653F, -2.2942F, -0.2503F, 3.0F, 3.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 2.0025F, 0.3054F, 0.0F, 0.0F));

        ModelPartData left_wing = pheasant.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -8.0F, 2.5F));

        ModelPartData left_wing_r1 = left_wing.addChild("left_wing_r1", ModelPartBuilder.create().uv(5, 16).mirrored().cuboid(4.3472F, -1.372F, -0.9923F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.7425F, 1.1553F, 0.2354F, -0.4346F, 0.0F, 0.0F));

        ModelPartData right_wing = pheasant.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, -8.0F, 2.5F));

        ModelPartData right_wing_r1 = right_wing.addChild("right_wing_r1", ModelPartBuilder.create().uv(5, 16).cuboid(-0.7728F, -1.372F, -0.9923F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.2575F, 1.1553F, 0.2354F, -0.4346F, 0.0F, 0.0F));

        ModelPartData right_leg = pheasant.addChild("right_leg", ModelPartBuilder.create().uv(10, 8).cuboid(-0.54F, -1.0F, -1.46F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -4.0F, 3.5F));

        ModelPartData left_leg = pheasant.addChild("left_leg", ModelPartBuilder.create().uv(10, 8).cuboid(-0.55F, -1.0F, -1.62F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.25F, -4.0F, 3.5F));
        return TexturedModelData.of(modelData, 64, 48);
    }

    @Override
    public void setAngles(PheasantEntityRenderState state) {
        super.setAngles(state);
    }
}