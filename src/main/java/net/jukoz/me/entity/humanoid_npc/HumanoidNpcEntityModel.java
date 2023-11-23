package net.jukoz.me.entity.humanoid_npc;

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
public class HumanoidNpcEntityModel<T extends MobEntity>
            extends BipedEntityModel<T> {
    private ModelPart root;
    private ModelPart leftEyebrow;
    private ModelPart rightEyebrow;
    private ModelPart faceIdle;
    private ModelPart faceDiscussing;
    private ModelPart faceFearful;
    private ModelPart faceSleeping;
    private ModelPart faceHurt;


    public HumanoidNpcEntityModel(ModelPart root) {
        super(root);
        this.root = root;
        ModelPart head = root.getChild(EntityModelPartNames.HEAD);
        if(
            head.hasChild(HumanoidNpcModel.LEFT_EYEBROW) &&
            head.hasChild(HumanoidNpcModel.RIGHT_EYEBROW) &&
            head.hasChild(HumanoidNpcModel.FACE_IDLE) &&
            head.hasChild(HumanoidNpcModel.FACE_DISCUSSING) &&
            head.hasChild(HumanoidNpcModel.FACE_FEARFUL) &&
            head.hasChild(HumanoidNpcModel.FACE_SLEEPING) &&
            head.hasChild(HumanoidNpcModel.FACE_HURT)
        ) {
            this.leftEyebrow = head.getChild(HumanoidNpcModel.LEFT_EYEBROW);
            this.rightEyebrow = head.getChild(HumanoidNpcModel.RIGHT_EYEBROW);
            this.faceIdle = head.getChild(HumanoidNpcModel.FACE_IDLE);
            this.faceDiscussing = head.getChild(HumanoidNpcModel.FACE_DISCUSSING);
            this.faceFearful = head.getChild(HumanoidNpcModel.FACE_FEARFUL);
            this.faceSleeping = head.getChild(HumanoidNpcModel.FACE_SLEEPING);
            this.faceHurt = head.getChild(HumanoidNpcModel.FACE_HURT);
        }
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
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ItemStack itemStack = (entity).getMainHandStack();
        if ((entity).isAttacking() && (itemStack.isEmpty() || !itemStack.isOf(Items.BOW))) {
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
            CrossbowPosing.swingArms(this.rightArm, this.leftArm, ageInTicks);
        }
        this.body.pitch = (float)Math.sin(0);
        if(this.hasCustomFeatures()) {
            this.leftEyebrow.roll = ToRad.ex(0);
            this.leftEyebrow.pivotY = -4.7f;

            this.rightEyebrow.roll = ToRad.ex(0);
            this.rightEyebrow.pivotY = -4.7f;

            int ageValue = (int) (ageInTicks % 100);

            this.clearEmotions();
            if(entity.isLeashed()){
                this.faceHurt.hidden = false;
            } else {
                switch(((HumanoidNpcEntity)entity).getEmotionState()){
                    case Idle -> {
                        if(ageValue % 25 < 4){
                            this.faceDiscussing.hidden = false;
                        } else {
                            this.faceIdle.hidden = false;
                        }
                    }
                    case Discussing -> {
                        this.faceDiscussing.hidden = false;
                    } case Sleeping -> {
                        this.faceSleeping.hidden = false;
                        this.rightEyebrow.pivotY = -4.2f;
                        this.leftEyebrow.pivotY = -4.2f;
                    } case Fearful -> {
                        this.faceFearful.hidden = false;
                    }
                    case Hurt -> {
                        this.faceHurt.hidden = false;
                    }
                }
            }
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

    private void clearEmotions(){
        this.faceIdle.hidden = true;
        this.faceDiscussing.hidden = true;
        this.faceFearful.hidden = true;
        this.faceSleeping.hidden = true;
        this.faceHurt.hidden = true;
    }

    private boolean hasCustomFeatures(){
        return (
            this.leftEyebrow != null &&
            this.rightEyebrow != null &&
            this.faceIdle != null &&
            this.faceDiscussing != null &&
            this.faceFearful != null &&
            this.faceSleeping != null &&
            this.faceHurt != null
        );
    }
}
