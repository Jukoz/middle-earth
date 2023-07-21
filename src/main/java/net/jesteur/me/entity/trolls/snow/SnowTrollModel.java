package net.jesteur.me.entity.trolls.snow;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class SnowTrollModel
extends SinglePartEntityModel<SnowTrollEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart boneJaw;
    private final ModelPart legRight;
    private final ModelPart legLeft;
    private final ModelPart armLeft;
    private final ModelPart armRight;

    private static final float ROTATION_SPEED = 0.8f;

    public SnowTrollModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.boneJaw = head.getChild("boneJaw");
        this.legRight = root.getChild("legRight");
        this.legLeft = root.getChild("legLeft");
        this.armLeft = root.getChild("armLeft");
        this.armRight = root.getChild("armRight");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(66, 33).cuboid(-9.0F, -35.0F, -7.0F, 18.0F, 3.0F, 14.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-14.0F, -32.0F, -7.0F, 28.0F, 16.0F, 17.0F, new Dilation(0.0F))
                .uv(164, 29).cuboid(0.0F, -42.0F, -7.0F, 0.0F, 35.0F, 28.0F, new Dilation(0.0F))
                .uv(164, 29).cuboid(7.0F, -41.0F, -8.0F, 0.0F, 35.0F, 28.0F, new Dilation(0.0F))
                .uv(164, 29).cuboid(-7.0F, -41.0F, -8.0F, 0.0F, 35.0F, 28.0F, new Dilation(0.0F))
                .uv(0, 33).cuboid(-12.0F, -16.0F, -9.0F, 24.0F, 16.0F, 18.0F, new Dilation(0.0F))
                .uv(84, 122).cuboid(-10.0F, -29.0F, -10.0F, 20.0F, 13.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 1.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 67).cuboid(-7.5F, -7.8F, -16.0F, 15.0F, 8.0F, 15.0F, new Dilation(0.0F))
                .uv(118, 0).cuboid(-6.5F, -7.8F, -18.0F, 13.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.5F, -2.8F, -17.0F, 5.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(116, 107).cuboid(-6.0F, 0.2F, -16.0F, 12.0F, 3.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -26.0F, -5.0F));

        ModelPartData boneJaw = head.addChild("boneJaw", ModelPartBuilder.create().uv(84, 50).cuboid(-6.0F, 0.75F, -13.0F, 12.0F, 3.0F, 14.0F, new Dilation(0.0F))
                .uv(152, 99).cuboid(-5.5F, -6.25F, -12.5F, 11.0F, 7.0F, 13.0F, new Dilation(0.0F))
                .uv(153, 80).cuboid(0.0F, 3.65F, -11.5F, 0.0F, 8.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.45F, -5.0F, 0.2356F, 0.0F, 0.0F));

        ModelPartData legLeft = modelPartData.addChild("legLeft", ModelPartBuilder.create().uv(73, 0).cuboid(-5.0F, 19.0F, -7.0F, 10.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 118).cuboid(-5.0F, 11.0F, -5.0F, 10.0F, 13.0F, 10.0F, new Dilation(0.0F))
                .uv(106, 67).cuboid(-5.5F, -2.0F, -5.5F, 11.0F, 13.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 0.0F, 1.0F));

        ModelPartData legRight = modelPartData.addChild("legRight", ModelPartBuilder.create().uv(45, 67).cuboid(-5.0F, 19.0F, -7.0F, 10.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(118, 23).cuboid(-5.0F, 11.0F, -5.0F, 10.0F, 13.0F, 10.0F, new Dilation(0.0F))
                .uv(84, 95).cuboid(-5.5F, -2.0F, -5.5F, 11.0F, 13.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, 0.0F, 1.0F));

        ModelPartData armLeft = modelPartData.addChild("armLeft", ModelPartBuilder.create().uv(0, 90).cuboid(0.0F, -4.0F, -6.0F, 11.0F, 16.0F, 12.0F, new Dilation(0.0F))
                .uv(46, 95).cuboid(1.0F, 12.0F, -5.0F, 9.0F, 23.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(14.0F, -26.0F, 2.0F));

        ModelPartData armRight = modelPartData.addChild("armRight", ModelPartBuilder.create().uv(60, 67).cuboid(-11.0F, -4.0F, -6.0F, 11.0F, 16.0F, 12.0F, new Dilation(0.0F))
                .uv(90, 0).cuboid(-10.0F, 12.0F, -5.0F, 9.0F, 23.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-14.0F, -26.0F, 2.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(SnowTrollEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.pitch = headPitch * ((float)Math.PI / 180);
        this.head.yaw = netHeadYaw * ((float)Math.PI / 180);
        this.boneJaw.pitch = 0.25F * Math.max(0, MathHelper.cos(ageInTicks * 0.1f));
        float k = 0.8f * limbSwingAmount;
        this.legRight.pitch = MathHelper.cos(limbSwing * ROTATION_SPEED) * k;
        this.legLeft.pitch = MathHelper.cos(limbSwing * ROTATION_SPEED + (float)Math.PI) * k;

        int i = entity.getAttackTicksLeft();
        if (entity.getState().equals(SnowTrollEntity.State.ATTACK)) {
            float ageFloat = (ageInTicks - (int)ageInTicks); // Helps to smooth the animation
            this.armRight.pitch = -1.1f + 0.9f * MathHelper.wrap((float) i - ageFloat, 10.0f);
            this.armLeft.pitch = -1.1f + 0.9f * MathHelper.wrap((float) i - ageFloat, 10.0f);
        } else {
            this.armRight.pitch = MathHelper.cos(limbSwing * ROTATION_SPEED + (float)Math.PI) * k;
            this.armLeft.pitch = MathHelper.cos(limbSwing * ROTATION_SPEED) * k;
        }
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