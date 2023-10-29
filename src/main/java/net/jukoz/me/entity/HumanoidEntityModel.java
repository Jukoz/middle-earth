package net.jukoz.me.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.utils.ToRad;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class HumanoidEntityModel<T extends MobEntity>
            extends BipedEntityModel<T> {
    private ModelPart rightEye;
    private ModelPart rightEyebrow;
    private ModelPart rightPupil;
    private ModelPart rightEyelidTop;
    private ModelPart rightEyelidBot;

    private ModelPart leftEye;
    private ModelPart leftEyebrow;
    private ModelPart leftPupil;
    private ModelPart leftEyelidTop;
    private ModelPart leftEyelidBot;



    public HumanoidEntityModel(ModelPart root) {
        super(root);
        ModelPart head = root.getChild(EntityModelPartNames.HEAD);
        if(!head.hasChild(HumanoidModel.RIGHT_EYE) || !head.hasChild(HumanoidModel.LEFT_EYE)) return;

        this.rightEye = head.getChild(HumanoidModel.RIGHT_EYE);
        this.rightEyebrow = head.getChild(HumanoidModel.RIGHT_EYEBROW);
        this.rightPupil = head.getChild(HumanoidModel.RIGHT_PUPIL);
        this.rightEyelidTop = this.rightEye.getChild(HumanoidModel.RIGHT_EYELID_TOP);
        this.rightEyelidBot = this.rightEye.getChild(HumanoidModel.RIGHT_EYELID_BOT);

        this.leftEye = head.getChild(HumanoidModel.LEFT_EYE);
        this.leftEyebrow = head.getChild(HumanoidModel.LEFT_EYEBROW);
        this.leftPupil = head.getChild(HumanoidModel.LEFT_PUPIL);
        this.leftEyelidTop = this.leftEye.getChild(HumanoidModel.LEFT_EYELID_TOP);
        this.leftEyelidBot = this.leftEye.getChild(HumanoidModel.LEFT_EYELID_BOT);
    }

    @Override
    public void animateModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = ArmPose.EMPTY;
        this.leftArmPose = ArmPose.EMPTY;
        ItemStack itemStack = (mobEntity).getStackInHand(Hand.MAIN_HAND);
        if (itemStack.isOf(Items.BOW) && (mobEntity).isAttacking()) {
            if ((mobEntity).getMainArm() == Arm.RIGHT) {
                this.rightArmPose = ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = ArmPose.BOW_AND_ARROW;
            }
        }
        super.animateModel(mobEntity, f, g, h);
    }

    @Override
    public void setAngles(T mobEntity, float f, float g, float h, float i, float j) {
        super.setAngles(mobEntity, f, g, h, i, j);
        ItemStack itemStack = (mobEntity).getMainHandStack();
        if ((mobEntity).isAttacking() && (itemStack.isEmpty() || !itemStack.isOf(Items.BOW))) {
            float k = MathHelper.sin(this.handSwingProgress * (float)Math.PI);
            float l = MathHelper.sin((1.0f - (1.0f - this.handSwingProgress) * (1.0f - this.handSwingProgress)) * (float)Math.PI);
            this.rightArm.roll = 0.0f;
            this.leftArm.roll = 0.0f;
            this.rightArm.yaw = -(0.1f - k * 0.6f);
            this.leftArm.yaw = 0.1f - k * 0.6f;
            this.rightArm.pitch = -1.5707964f;
            this.leftArm.pitch = -1.5707964f;
            this.rightArm.pitch -= k * 1.2f - l * 0.4f;
            this.leftArm.pitch -= k * 1.2f - l * 0.4f;


            CrossbowPosing.swingArms(this.rightArm, this.leftArm, h);
        }

        this.rightEyebrow.roll = ToRad.ex(5);
        this.rightEyebrow.pivotY = -4f - 0.7f;
        this.rightPupil.yScale = 0.75f;
        this.rightPupil.xScale = 1.3f;
        this.rightEyelidTop.yScale = 0.45f;
        this.rightEyelidBot.yScale = -0.25f;

        this.leftEyebrow.roll = ToRad.ex(-5);
        this.leftEyebrow.pivotY = -4f - 0.7f;
        this.leftPupil.yScale = 0.75f;
        this.leftPupil.xScale = 1.3f;
        //this.leftPupill.pivotX = 0.3f;
        this.leftEyelidTop.yScale = 0.45f;
        this.leftEyelidBot.yScale = -0.25f;

        this.rightEyelidTop.hidden = true;
        this.rightEyelidBot.hidden = true;
        this.leftEyelidTop.hidden = true;
        this.leftEyelidBot.hidden = true;
        /*

        this.rightEyebrow.pivotY = -3.8f;
        this.rightEye.yScale = -0.6f;
        this.rightPupill.yScale = -0.6f;
        this.rightEyelidTop.yScale = 0.25f;
        this.rightEyelidTop.hidden = true;
        this.rightEyelidBot.yScale = 0.8f;


        this.leftEyebrow.pivotY = -3.8f;
        this.leftEye.yScale = -0.6f;
        this.leftPupill.yScale = -0.6f;
        this.leftEyelidTop.yScale = 0.25f;
        this.leftEyelidTop.hidden = true;
        this.leftEyelidBot.yScale = 0.8f;*/
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        float f = arm == Arm.RIGHT ? 1.0f : -1.0f;
        ModelPart modelPart = this.getArm(arm);
        modelPart.pivotX += f;
        modelPart.rotate(matrices);
        modelPart.pivotX -= f;
    }

}
