package net.sevenstars.middleearth.entity.elves.galadhrim;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomBowWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;

@Environment(value= EnvType.CLIENT)
public class GaladhrimElfModel<T extends MobEntity>
        extends BipedEntityModel<T> {

    public GaladhrimElfModel(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = ArmPose.EMPTY;
        this.leftArmPose = ArmPose.EMPTY;
        ItemStack itemStack = (mobEntity).getStackInHand(Hand.MAIN_HAND);
        if ((itemStack.isOf(Items.BOW) || itemStack.getItem() instanceof CustomLongbowWeaponItem || itemStack.getItem() instanceof CustomBowWeaponItem) && (mobEntity).isAttacking()) {
            if (mobEntity.getMainArm() == Arm.RIGHT) {
                this.rightArmPose = ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = ArmPose.BOW_AND_ARROW;
            }
        }
        super.animateModel(mobEntity, f, g, h);
    }

    @Override
    public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
        super.setAngles(livingEntity, f, g, h, i, j);
        if(riding){
            head.pivotY = 10f;
            hat.pivotY = 10f;
            body.pivotY = 10f;
            leftArm.pivotY = 12f;
            rightArm.pivotY = 12f;
            leftLeg.pivotY = 22f;
            rightLeg.pivotY = 22f;
        }
    }


    /*@Override
    public void setAngles(T mobEntity, float f, float g, float h, float i, float j) {
        super.setAngles(mobEntity, f, g, h, i, j);
        ItemStack itemStack = ((LivingEntity)mobEntity).getMainHandStack();
        if (((MobEntity)mobEntity).isAttacking() && (itemStack.isOf(Items.BOW))) {
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
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        float f = arm == Arm.RIGHT ? 1.0f : -1.0f;
        ModelPart modelPart = this.getArm(arm);
        modelPart.pivotX += f;
        modelPart.rotate(matrices);
        modelPart.pivotX -= f;
    }*/
}
