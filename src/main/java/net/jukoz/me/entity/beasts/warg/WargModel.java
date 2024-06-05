package net.jukoz.me.entity.beasts.warg;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WargModel extends SinglePartEntityModel<WargEntity> {
    private final ModelPart root;
    private final ModelPart head;
    public WargModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("root").getChild("upperBody").getChild("head");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData upperBody = root.addChild("upperBody", ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData head = upperBody.addChild("head", ModelPartBuilder.create().uv(39, 35).cuboid(-1.3858F, -4.1138F, -6.0F, 10.0F, 6.0F, 9.0F, new Dilation(0.0F))
                .uv(60, 51).cuboid(-1.3858F, -2.1138F, -3.0F, 15.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(60, 59).cuboid(7.6142F, 2.8862F, -3.0F, 6.0F, 1.0F, 3.0F, new Dilation(0.05F))
                .uv(37, 18).cuboid(-2.0F, 1.25F, -5.0F, 10.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(14.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        ModelPartData earRight = head.addChild("earRight", ModelPartBuilder.create(), ModelTransform.pivot(3.1142F, -1.1138F, -3.978F));

        ModelPartData cube_r1 = earRight.addChild("cube_r1", ModelPartBuilder.create().uv(0, 19).cuboid(0.2802F, -4.8619F, -2.032F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -0.022F, 0.0F, 0.0F, -1.0036F));

        ModelPartData earLeft = head.addChild("earLeft", ModelPartBuilder.create(), ModelTransform.pivot(3.1142F, -1.1138F, 3.022F));

        ModelPartData cube_r2 = earLeft.addChild("cube_r2", ModelPartBuilder.create().uv(0, 1).cuboid(0.2802F, -4.8619F, -0.99F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0036F));

        ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(0, 44).cuboid(-5.5F, -0.25F, -2.5F, 15.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 66).cuboid(-5.5F, -1.25F, -2.5F, 15.0F, 1.0F, 3.0F, new Dilation(-0.05F)), ModelTransform.pivot(4.1142F, 3.1362F, -0.5F));

        ModelPartData tail = upperBody.addChild("tail", ModelPartBuilder.create().uv(48, 28).cuboid(-11.0F, -4.0F, -2.5F, 13.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-13.3858F, -4.1138F, -0.5F, 0.0F, 0.0F, -1.5708F));

        ModelPartData body = upperBody.addChild("body", ModelPartBuilder.create().uv(1, 19).cuboid(1.0F, -5.75F, -5.0F, 13.0F, 13.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-13.0F, -5.0F, -4.0F, 27.0F, 10.0F, 8.0F, new Dilation(0.0F))
                .uv(6, 73).cuboid(-3.0F, -11.0F, 0.0F, 17.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

        ModelPartData legLeft = upperBody.addChild("legLeft", ModelPartBuilder.create().uv(0, 50).cuboid(-2.5F, 1.0F, -2.5F, 5.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.8858F, 2.8862F, 2.0F));

        ModelPartData legRight = upperBody.addChild("legRight", ModelPartBuilder.create().uv(16, 50).cuboid(-2.5F, 1.0F, -2.5F, 5.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.8858F, 2.8862F, -3.0F));

        ModelPartData armRight = upperBody.addChild("armRight", ModelPartBuilder.create().uv(46, 50).cuboid(-2.0F, 1.0F, -2.5F, 4.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(9.6142F, 2.8862F, -3.0F));

        ModelPartData armLeft = upperBody.addChild("armLeft", ModelPartBuilder.create().uv(32, 50).cuboid(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(9.6142F, 2.8862F, 1.5F));
        return TexturedModelData.of(modelData, 128, 128);

    }

    @Override
    public void setAngles(WargEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(headYaw, headPitch);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}