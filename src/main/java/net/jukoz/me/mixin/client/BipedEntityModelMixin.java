package net.jukoz.me.mixin.client;

import net.jukoz.me.item.ModDecorativeItems;
import net.jukoz.me.item.items.ReachWeaponItem;
import net.jukoz.me.item.utils.WeaponTypes;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {
    @Shadow public BipedEntityModel.ArmPose leftArmPose;

    @Shadow public BipedEntityModel.ArmPose rightArmPose;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart leftArm;

    @Inject(at = @At("HEAD"), method = "positionRightArm")
    private void positionRightArm(LivingEntity entity, CallbackInfo ci) {
        if(this.rightArmPose == BipedEntityModel.ArmPose.ITEM) {
            tryItemAnimation(entity.getMainHandStack());
        }
    }

    @Inject(at = @At("HEAD"), method = "positionLeftArm")
    private void positionLeftArm(LivingEntity entity, CallbackInfo ci) {
        if(this.leftArmPose == BipedEntityModel.ArmPose.ITEM) {
            tryItemAnimation(entity.getOffHandStack());
        }
    }

    private void tryItemAnimation(ItemStack itemStack) {
        if(itemStack.getItem().equals(ModDecorativeItems.FIRE_OF_ORTHANC)) {
            float pitch = this.rightArm.pitch * 0.25F - 0.5F;
            this.rightArm.pitch = pitch;
            this.rightArm.yaw = 0.0F;
            this.leftArm.pitch = pitch;
            this.leftArm.yaw = 0.0F;
        }
        /*if(itemStack.getItem() instanceof ReachWeaponItem && ((ReachWeaponItem) itemStack.getItem()).getType() == WeaponTypes.SPEAR) {
            float pitch = -2.60f;
            this.rightArm.pitch = pitch;
            this.rightArm.yaw = 0.0F;
            this.rightArm.roll = 0.0F;
        }*/
    }
}
