package net.jukoz.me.entity.nazguls;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class NazgulModel<T extends MobEntity>
        extends BipedEntityModel<T> {

    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_leg";
    private static final String RIGHT_PANTS = "right_leg";
    private static final float LEFT_ARM_PIVOT_X = 7.0f;
    private static final float RIGHT_ARM_PIVOT_X = -7.0f;
    private static final float LEG_PIVOT_X = 1.9f;

    public NazgulModel(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = ArmPose.EMPTY;
        this.leftArmPose = ArmPose.EMPTY;
        super.animateModel(mobEntity, f, g, h);
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(8, 8)
                        .cuboid(-1.0f, 0f, -1.0f, 2.0f, 2.0f, 2.0f, dilation),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));


        root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        ModelPartData leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(32, 48)
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(LEFT_ARM_PIVOT_X, 2.5f, 0.0f));
        ModelPartData rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16)
                        .cuboid(-3f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(RIGHT_ARM_PIVOT_X, 2.5f, 0.0f));

        rightArm.addChild(RIGHT_SLEEVE, ModelPartBuilder.create().uv(40, 32).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(-2f, -2f, -2f));
        leftArm.addChild(LEFT_SLEEVE, ModelPartBuilder.create().uv(48, 48).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(-1f, -2f, -2f));

        root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16)
                        .cuboid(-4.0f, 0.0f, -2f, 8.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(0.0f, 0f, 0.0f));

        root.getChild(EntityModelPartNames.BODY).addChild(EntityModelPartNames.JACKET, ModelPartBuilder.create().uv(16, 32)
                .cuboid(-4.0f, 0.0f, 0f, 8.0f, 12.0f, 4.0f, dilation.add(0.25f)), ModelTransform.NONE);

        root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(-LEG_PIVOT_X, 0.0f, 0.0f));
        root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored()
                        .cuboid(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(LEG_PIVOT_X, 0.0f, 0.0f));


        root.getChild(EntityModelPartNames.LEFT_LEG).addChild(LEFT_PANTS, ModelPartBuilder.create().uv(0, 48)
                        .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(0, 0.0f, 0.0f));
        root.getChild(EntityModelPartNames.RIGHT_LEG).addChild(RIGHT_PANTS, ModelPartBuilder.create().uv(0, 32)
                        .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(0, 0.0f, 0.0f));

        root.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.5f)),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));                // 54

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T mobEntity, float limbAngle, float limbDistance, float ageInTicks, float i, float j) {
        super.setAngles(mobEntity, limbAngle, limbDistance, ageInTicks, i, j);
        ItemStack itemStack = (mobEntity).getMainHandStack();
        NazgulEntity.State state = ((NazgulEntity)mobEntity).getState();

        if(state == NazgulEntity.State.ATTACKING) {
            this.leftArm.pitch = MathHelper.cos(limbAngle * 0.6662f) * 2.0f * limbDistance * 0.5f;
            this.leftArm.yaw = 0.0f;
            this.leftArm.roll = 0.0f;

            this.rightArm.pitch = -0.9f + MathHelper.cos(limbAngle * 0.6662f + (float)Math.PI) * 2.0f * limbDistance * 0.5f;
            this.rightArm.yaw = 0.0f;
            this.rightArm.roll = 0.0f;
        } else if(state == NazgulEntity.State.ON_FIRE || state == NazgulEntity.State.FADING) {
            float limbCosTime = (float) (1 + 0.25f * Math.cos(limbAngle));
            this.rightArm.pitch = -3.1f + MathHelper.cos(ageInTicks * 0.4f + (float)Math.PI) * 0.3f * limbCosTime;
            this.rightArm.roll = -0.6f + MathHelper.sin(ageInTicks * 0.666f + (float)Math.PI) * 0.2f;

            this.leftArm.pitch = -3.1f + MathHelper.sin(ageInTicks * 0.4f + (float)Math.PI) * 0.3f * limbCosTime;
            this.leftArm.roll = 0.6f + MathHelper.cos(ageInTicks * 0.666f + (float)Math.PI) * 0.2f;
        }
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        float f = arm == Arm.RIGHT ? 1.0f : -1.0f;
        ModelPart modelPart = this.getArm(arm);
        modelPart.pivotX += f;
        modelPart.rotate(matrices);
        modelPart.pivotX -= f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha * 0.5f);
    }
}
