package net.sevenstars.middleearth.entity.pheasant;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class PheasantModel extends EntityModel<PheasantEntityRenderState> {
    private final ModelPart pheasant;
    private final ModelPart rightleg;
    private final ModelPart leftleg;
    private final ModelPart rightwing;
    private final ModelPart leftwing;

    public PheasantModel(ModelPart root) {
        super(root);

        this.pheasant = root.getChild("pheasant");
        this.rightleg = this.pheasant.getChild("rightleg");
        this.leftleg = this.pheasant.getChild("leftleg");
        this.rightwing = this.pheasant.getChild("rightwing");
        this.leftwing = this.pheasant.getChild("leftwing");

    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData pheasant = modelPartData.addChild("pheasant", ModelPartBuilder.create(), ModelTransform.pivot(2.4F, 24.0F, -3.5F));

        ModelPartData upperbody = pheasant.addChild("upperbody", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -4.0F, 3.0F));

        ModelPartData body = upperbody.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(2.5F, 4.0F, -3.0F));

        ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(27, 0).cuboid(-2.5946F, -2.513F, -3.2795F, 5.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-2.4F, -6.4F, 3.04F, -0.2618F, 0.0F, 0.0F));

        ModelPartData body_r2 = body.addChild("body_r2", ModelPartBuilder.create().uv(27, 13).cuboid(-1.9591F, -2.5521F, 0.0991F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -4.96F, 5.28F, 0.1745F, 0.0F, 0.0F));

        ModelPartData headandneck = upperbody.addChild("headandneck", ModelPartBuilder.create(), ModelTransform.of(0.0F, -3.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

        ModelPartData head = headandneck.addChild("head", ModelPartBuilder.create().uv(26, 0).cuboid(-0.52F, -1.24F, -3.28F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.56F, -2.92F, -2.4F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(44, 0).cuboid(-1.56F, 0.08F, -2.4F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, -5.0714F, -0.8695F, -0.1309F, 0.0F, 0.0F));

        ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(15, 0).cuboid(-1.0F, -1.16F, -0.64F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.1305F, 0.0F, 0.0F));

        ModelPartData neck = headandneck.addChild("neck", ModelPartBuilder.create().uv(0, 8).cuboid(-0.94F, -5.4F, -0.88F, 2.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.9914F, -0.8695F));

        ModelPartData tail = upperbody.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 1.0F));

        ModelPartData tail_r1 = tail.addChild("tail_r1", ModelPartBuilder.create().uv(1, 26).cuboid(-1.4653F, -2.2942F, -0.2503F, 3.0F, 3.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 2.0025F, 0.3054F, 0.0F, 0.0F));

        ModelPartData tail_r2 = tail.addChild("tail_r2", ModelPartBuilder.create().uv(2, 7).cuboid(-1.4743F, -0.9196F, 0.683F, 3.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 2.0025F, 0.3053F, 0.0F, 0.0F));

        ModelPartData tail_r3 = tail.addChild("tail_r3", ModelPartBuilder.create().uv(2, 7).cuboid(-1.5F, -0.52F, 1.2F, 3.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 2.0025F, 0.1773F, 0.0F, 0.0F));

        ModelPartData tail_r4 = tail.addChild("tail_r4", ModelPartBuilder.create().uv(2, 7).cuboid(-1.5001F, -0.9949F, 1.2189F, 3.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 2.0025F, 0.2181F, 0.0F, 0.0F));

        ModelPartData leftwing = pheasant.addChild("leftwing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -8.0F, 2.5F));

        ModelPartData leftwing_r1 = leftwing.addChild("leftwing_r1", ModelPartBuilder.create().uv(5, 16).mirrored().cuboid(4.3472F, -1.372F, -0.9923F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.7425F, 1.1553F, 0.2354F, -0.4346F, 0.0F, 0.0F));

        ModelPartData rightwing = pheasant.addChild("rightwing", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, -8.0F, 2.5F));

        ModelPartData rightwing_r1 = rightwing.addChild("rightwing_r1", ModelPartBuilder.create().uv(5, 16).cuboid(-0.7728F, -1.372F, -0.9923F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.2575F, 1.1553F, 0.2354F, -0.4346F, 0.0F, 0.0F));

        ModelPartData rightleg = pheasant.addChild("rightleg", ModelPartBuilder.create().uv(10, 8).cuboid(-0.54F, -1.0F, -1.46F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -4.0F, 3.5F));

        ModelPartData leftleg = pheasant.addChild("leftleg", ModelPartBuilder.create().uv(10, 8).cuboid(-0.55F, -1.0F, -1.62F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.25F, -4.0F, 3.5F));
        return TexturedModelData.of(modelData, 64, 48);
    }

    @Override
    public void setAngles(PheasantEntityRenderState state) {
        super.setAngles(state);
    }
}