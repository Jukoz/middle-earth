package net.jukoz.me.entity.goose;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.math.MathHelper;

public class GooseModel<T extends GooseEntity> extends EntityModel<GooseEntity> {
    public final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    public GooseModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_wing = root.getChild("right_wing");
        this.left_wing = root.getChild("left_wing");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-1.025F, -6.0F, -1.0F, 2.05F, 8.0F, 2.0F, new Dilation(0.0F))
                .uv(25, 0).cuboid(-1.5F, -8.75F, -3.25F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 19).cuboid(-0.75F, -7.0F, -6.25F, 1.5F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(23, 8).cuboid(-6.0F, -6.5F, -6.0F, 7.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, -3.5F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 17.0F, 1.5F));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -9.0F, -6.0F, 6.0F, 6.0F, 13.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, -2.5F, -0.2618F, 0.0F, 0.0F));

        ModelPartData right_wing = modelPartData.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, 13.0F, 0.5F));

        ModelPartData cube_r2 = right_wing.addChild("cube_r2", ModelPartBuilder.create().uv(0, 19).cuboid(3.0F, -10.0F, -2.0F, 1.0F, 5.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 9.0F, -1.5F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_wing = modelPartData.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, 13.0F, 0.5F));

        ModelPartData cube_r3 = left_wing.addChild("cube_r3", ModelPartBuilder.create().uv(13, 24).cuboid(-4.0F, -10.0F, -2.0F, 1.0F, 5.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 9.0F, -1.5F, -0.2618F, 0.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(13, 19).cuboid(-0.5F, -0.75F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F))
                .uv(26, 19).cuboid(-1.0F, 4.25F, -3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 19.75F, 2.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(16, 19).cuboid(-0.5F, -0.75F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F))
                .uv(19, 19).cuboid(-1.0F, 4.25F, -3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 19.75F, 2.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(GooseEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.right_leg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_leg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.right_wing.roll = ageInTicks;
        this.left_wing.roll = -ageInTicks;
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = netHeadYaw * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_wing.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_wing.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

    }

}