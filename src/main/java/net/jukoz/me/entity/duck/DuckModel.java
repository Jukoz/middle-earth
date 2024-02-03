package net.jukoz.me.entity.duck;

import net.jukoz.me.entity.goose.GooseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class DuckModel<T extends DuckEntity> extends EntityModel<DuckEntity> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    public DuckModel(ModelPart root) {
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
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(20, 0).cuboid(-2.0F, -5.5F, -2.25F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.25F, -3.25F));

        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(10, 13).cuboid(-1.0F, -12.75F, -6.05F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.75F, 2.75F, 0.1309F, 0.0F, 0.0F));

        ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -11.0F, -4.5F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.75F, 2.75F, -0.0873F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 17.0389F, -0.1265F));

        ModelPartData cube_r3 = body.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -9.0F, -6.0F, 6.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.9611F, 0.1265F, -0.1745F, 0.0F, 0.0F));

        ModelPartData right_wing = modelPartData.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, 15.75F, -1.0F));

        ModelPartData cube_r4 = right_wing.addChild("cube_r4", ModelPartBuilder.create().uv(0, 13).cuboid(3.0F, -9.0F, -5.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 9.25F, 1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData left_wing = modelPartData.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, 15.75F, -1.0F));

        ModelPartData cube_r5 = left_wing.addChild("cube_r5", ModelPartBuilder.create().uv(10, 17).cuboid(-4.0F, -9.0F, -5.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 9.25F, 1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 13).cuboid(-0.5F, -0.75F, 0.0F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 5).cuboid(-1.0F, 3.25F, -3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 20.75F, -1.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(2, 16).cuboid(-0.5F, -0.75F, 0.0F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 13).cuboid(-1.0F, 3.25F, -3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 20.75F, -1.0F));
        return TexturedModelData.of(modelData, 64, 64);
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

    @Override
    public void setAngles(DuckEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.right_leg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_leg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.right_wing.roll = 0;
        this.left_wing.roll = -0;
        this.head.pitch = 0;
        if(!entity.isOnGround()) {
            float angle = MathHelper.cos(MathHelper.cos(ageInTicks * 0.5f));
            this.right_wing.roll = -4 + (4 * angle);
            this.left_wing.roll = 4 - (4 * angle);
        }
    }
}