package net.jukoz.me.client.model.equipment;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;

public class RohanTier4ArmourModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public final ModelPart headornament;
    public final ModelPart body_undersuit;
    public final ModelPart right_arm_undersuit;
    public final ModelPart left_arm_undersuit;


    public RohanTier4ArmourModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
        this.headornament = head.getChild("headornament");
        this.body_undersuit = body.getChild("undersuit");
        this.right_arm_undersuit = rightArm.getChild("right_arm_undersuit");
        this.left_arm_undersuit = leftArm.getChild("left_arm_undersuit");
    }

    public static TexturedModelData getTexturedModelData() {
        /*ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();*/
        ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
        ModelPartData head = data.getRoot().getChild(EntityModelPartNames.HEAD);
        ModelPartData body = data.getRoot().getChild(EntityModelPartNames.BODY);
        ModelPartData rightLeg = data.getRoot().getChild(EntityModelPartNames.RIGHT_LEG);
        ModelPartData leftLeg = data.getRoot().getChild(EntityModelPartNames.LEFT_LEG);
        ModelPartData rightArm = data.getRoot().getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPartData leftArm = data.getRoot().getChild(EntityModelPartNames.LEFT_ARM);

        head.addChild("headornament", ModelPartBuilder.create()
                        .uv(16, 16).cuboid(-3.9F, 0.0F, -2.1F, 8.0F, 12.0F, 4.0F, new Dilation(0.75F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        body.addChild("undersuit", ModelPartBuilder.create()
                        .uv(16, 16).cuboid(-3.9F, 0.0F, -2.1F, 8.0F, 12.0F, 4.0F, new Dilation(0.75F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        rightArm.addChild("right_arm_undersuit", ModelPartBuilder.create()
                        .uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        leftArm.addChild("left_arm_undersuit", ModelPartBuilder.create()
                        .uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));


        /*modelPartData.addChild("body", ModelPartBuilder.create()
                .uv(16, 16).cuboid(-3.9F, 0.0F, -2.1F, 8.0F, 12.0F, 4.0F, new Dilation(0.75F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild("right_arm", ModelPartBuilder.create()
                .uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        modelPartData.addChild("left_arm", ModelPartBuilder.create()
                .uv(40, 16).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)).mirrored(false),
                ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        modelPartData.addChild("right_leg", ModelPartBuilder.create()
                .uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)),
                ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        modelPartData.addChild("left_leg", ModelPartBuilder.create()
                .uv(0, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)).mirrored(false),
                ModelTransform.pivot(1.9F, 12.0F, 0.0F));*/
        return TexturedModelData.of(data, 64, 32);
    }

    @Override
    public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
        /*if (!(livingEntity instanceof ArmorStandEntity entityIn)) {
            if (!(livingEntity instanceof LivingEntity entityIn)) {
            super.setAngles(livingEntity, f, g, h, i, j);
            return;
        }

        this.head.yaw = ((float) Math.PI / 180F) * entityIn.getHeadYaw();

        this.head.setPivot(0.0F, 1.0F, 0.0F);
        this.body.yaw = entityIn.bodyYaw;

        this.leftArm.yaw = ((float) Math.PI / 180F) * entityIn.getLeftArmRotation().getYaw();
        this.leftArm.pitch = ((float) Math.PI / 180F) * entityIn.getLeftArmRotation().getPitch();
        this.leftArm.roll = ((float) Math.PI / 180F) * entityIn.getLeftArmRotation().getRoll();
        this.rightArm.yaw = ((float) Math.PI / 180F) * entityIn.getRightArmRotation().getYaw();
        this.rightArm.pitch = ((float) Math.PI / 180F) * entityIn.getRightArmRotation().getPitch();
        this.rightArm.roll = ((float) Math.PI / 180F) * entityIn.getRightArmRotation().getRoll();

        this.leftLeg.yaw = ((float) Math.PI / 180F) * entityIn.getLeftLegRotation().getYaw();
        this.leftLeg.pitch = ((float) Math.PI / 180F) * entityIn.getLeftLegRotation().getPitch();
        this.leftLeg.roll = ((float) Math.PI / 180F) * entityIn.getLeftLegRotation().getRoll();
        this.rightLeg.yaw = ((float) Math.PI / 180F) * entityIn.getRightLegRotation().getYaw();
        this.rightLeg.pitch = ((float) Math.PI / 180F) * entityIn.getRightLegRotation().getPitch();
        this.rightLeg.roll = ((float) Math.PI / 180F) * entityIn.getRightLegRotation().getRoll();

        this.leftLeg.setPivot(1.9F, 11.0F, 0.0F);
        this.rightLeg.setPivot(-1.9F, 11.0F, 0.0F);
        this.hat.copyTransform(this.head);*/
    }
}