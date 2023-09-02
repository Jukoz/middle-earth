package net.jesteur.me.entity.barrow_wights;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

@Environment(value = EnvType.CLIENT)
public class BarrowWightModel extends SinglePartEntityModel<BarrowWightEntity> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart bottomJaw;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private static final float ROTATION_SPEED = 0.4f;

    public BarrowWightModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.bottomJaw = head.getChild("bottomJaw");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 52).cuboid(-4.0F, -4.35F, -10.1324F, 8.0F, 6.0F, 8.0F, new Dilation(0.1F)).uv(69, 7).cuboid(-3.5F, -4.6F, -9.6324F, 7.0F, 6.0F, 0.0F, new Dilation(0.0F)).uv(47, 0).cuboid(-4.0F, 1.8943F, -10.1206F, 8.0F, 1.0F, 6.0F, new Dilation(0.1F)).uv(0, 28).cuboid(-4.0F, -4.4443F, -10.5F, 8.0F, 10.0F, 10.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, -30.0557F, -1.0F));

        ModelPartData bottomJaw = head.addChild("bottomJaw", ModelPartBuilder.create().uv(62, 44).cuboid(-3.99F, -1.6614F, -5.9F, 7.98F, 2.0F, 6.0F, new Dilation(0.0F)).uv(72, 37).cuboid(-3.99F, 0.3386F, -6.0F, 7.98F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.01F, 2.0557F, -4.3F, 0.2182F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 18).cuboid(-10.0F, -6.9088F, -11.0419F, 20.0F, 3.0F, 7.0F, new Dilation(0.0F)).uv(36, 28).cuboid(-5.0F, 4.6614F, -5.0419F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -26.6614F, 6.1294F));

        ModelPartData shoulderCloak_r1 = body.addChild("shoulderCloak_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-9.91F, -20.0F, -4.5F, 20.0F, 11.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 14.6614F, -3.1294F, 0.1745F, 0.0F, 0.0F));

        ModelPartData torsoCloakRight_r1 = body.addChild("torsoCloakRight_r1", ModelPartBuilder.create().uv(56, 39).cuboid(-1.0F, -7.3386F, -1.2919F, 0.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 6.0F, -1.0F, 0.4326F, -0.1125F, 0.2461F));

        ModelPartData legCloak_r1 = body.addChild("legCloak_r1", ModelPartBuilder.create().uv(30, 44).cuboid(-5.001F, -14.3386F, 2.7081F, 10.0F, 10.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 27.0F, -4.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData waistCloakFront_r1 = body.addChild("waistCloakFront_r1", ModelPartBuilder.create().uv(0, 48).cuboid(0.0F, 3.6614F, -3.0419F, 10.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 6.0F, -1.0F, -0.3054F, 0.0F, 0.0F));

        ModelPartData waistCloakBack_r1 = body.addChild("waistCloakBack_r1", ModelPartBuilder.create().uv(64, 27).cuboid(0.0F, 3.9114F, 2.9581F, 10.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 6.0F, -1.0F, 0.3491F, 0.0F, 0.0F));

        ModelPartData waistCloakRight_r1 = body.addChild("waistCloakRight_r1", ModelPartBuilder.create().uv(26, 20).cuboid(0.75F, 2.6614F, -4.0419F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 6.0F, -1.0F, -0.0066F, 0.0065F, 0.3008F));

        ModelPartData waistCloakLeft_r1 = body.addChild("waistCloakLeft_r1", ModelPartBuilder.create().uv(54, 14).cuboid(-0.75F, 2.6614F, -4.0419F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 6.0F, -1.0F, -0.0066F, -0.0065F, -0.3008F));

        ModelPartData torsoCloakLeft_r1 = body.addChild("torsoCloakLeft_r1", ModelPartBuilder.create().uv(0, 23).cuboid(1.0F, -7.3386F, -1.2919F, 0.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 6.0F, -1.0F, 0.4326F, 0.1125F, -0.2461F));

        ModelPartData backCloakBot_r1 = body.addChild("backCloakBot_r1", ModelPartBuilder.create().uv(64, 31).cuboid(-4.0F, -1.1345F, -0.4734F, 8.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 1.0F, 0.6109F, 0.0F, 0.0F));

        ModelPartData backCloakTop_r1 = body.addChild("backCloakTop_r1", ModelPartBuilder.create().uv(69, 0).cuboid(-4.0F, 0.2274F, -0.0767F, 8.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.3386F, -2.1294F, 0.7418F, 0.0F, 0.0F));

        ModelPartData torso_r1 = body.addChild("torso_r1", ModelPartBuilder.create().uv(57, 55).cuboid(-4.0F, -6.0F, -2.5F, 8.0F, 12.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0067F, -2.5449F, 0.3491F, 0.0F, 0.0F));

        ModelPartData leftArm = modelPartData.addChild("leftArm", ModelPartBuilder.create(), ModelTransform.of(8.0F, -31.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r1 = leftArm.addChild("cube_r1", ModelPartBuilder.create().uv(32, 60).cuboid(-1.5F, -13.0F, -13.1401F, 3.0F, 26.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.494F, 12.0F, 11.1401F, 0.0F, 0.0436F, 0.0F));

        ModelPartData rightArm = modelPartData.addChild("rightArm", ModelPartBuilder.create(), ModelTransform.of(-8.0F, -31.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r2 = rightArm.addChild("cube_r2", ModelPartBuilder.create().uv(44, 60).cuboid(-1.5F, -13.0F, -13.5F, 3.0F, 26.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 12.0F, 11.5F, 0.0F, 0.0F, 0.0F));

        ModelPartData leftLeg = modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(0, 66).cuboid(-2.2296F, -1.0567F, -1.4252F, 3.0F, 14.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -13.0F, 6.0F));

        ModelPartData rightLeg = modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(12, 66).cuboid(-1.7296F, -2.0567F, -1.4252F, 3.0F, 14.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -12.0F, 6.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BarrowWightEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform); // resets all limbs before animation. eahc run of animationstate update sets next frame...
        this.head.pitch = headPitch * ((float) Math.PI / 180);
        this.head.yaw = netHeadYaw * ((float) Math.PI / 180);

        this.bottomJaw.pitch = 0.25F * Math.max(0, MathHelper.cos(ageInTicks * 0.1f));
        float k = 0.8f * limbSwingAmount;
        this.rightLeg.pitch = MathHelper.cos(limbSwing * ROTATION_SPEED) * k;
        this.leftLeg.pitch = MathHelper.cos(limbSwing * ROTATION_SPEED + (float) Math.PI) * k;

        int i = entity.getAttackTicksLeft();
        if (entity.getState().equals(BarrowWightEntity.State.ATTACK)) {
            float ageFloat = (ageInTicks - (int) ageInTicks); // Helps to smooth the animation
            this.rightArm.pitch = -1.1f + 0.9f * MathHelper.wrap((float) i - ageFloat, 10.0f);
            this.leftArm.pitch = -1.1f + 0.9f * MathHelper.wrap((float) i - ageFloat, 10.0f);
        } else {
            this.rightArm.pitch = MathHelper.cos(limbSwing * ROTATION_SPEED + (float) Math.PI) * k;
            this.leftArm.pitch = MathHelper.cos(limbSwing * ROTATION_SPEED) * k;
        }
        this.updateAnimation(((BarrowWightEntity) entity).screamAnimationState, BarrowWightAnimations.anim_scream, ageInTicks);


    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        //quick matrix translation to fix floating wight .-.
        matrices.translate(0f, 1.5f, 0f);
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;

    }


}
