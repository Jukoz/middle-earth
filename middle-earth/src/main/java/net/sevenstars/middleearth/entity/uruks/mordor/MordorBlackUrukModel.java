package net.sevenstars.middleearth.entity.uruks.mordor;

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
public class MordorBlackUrukModel<T extends MobEntity>
        extends BipedEntityModel<T> {

    public MordorBlackUrukModel(ModelPart root) {
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
        MordorUrukEntity.State state = ((MordorUrukEntity)mobEntity).getState();

        if(state == MordorUrukEntity.State.ATTACKING) {
            // Walk added
            this.leftArm.pitch = MathHelper.cos(f * 0.6662f) * 2.0f * g * 0.5f;
            this.leftArm.yaw = 0.0f;
            this.leftArm.roll = 0.0f;

            this.rightArm.pitch = -0.9f + MathHelper.cos(f * 0.6662f + (float)Math.PI) * 2.0f * g * 0.5f;
            this.rightArm.yaw = 0.0f;
            this.rightArm.roll = 0.0f;
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
