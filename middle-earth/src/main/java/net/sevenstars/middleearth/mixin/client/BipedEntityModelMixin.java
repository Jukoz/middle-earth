package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {

    @Shadow @Final
    public ModelPart rightArm;

    @Shadow @Final public ModelPart leftArm;
    private final static float VERTICAL_ANGLE = -1.4f;


    @Inject(at = @At("TAIL"), method = "positionRightArm")
    private <T extends BipedEntityRenderState> void positionRightArm(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        ArmedEntityRenderStateAccess renderStateAccess = ((ArmedEntityRenderStateAccess)state);
        ItemStack handItem = renderStateAccess.getMainHandStack();

        if(renderStateAccess.isRestrained()) {
            restrainedAnimation();
        } else if(handItem != null) {
            tryItemAnimation(handItem, true);
        }

    }

    @Inject(at = @At("TAIL"), method = "positionLeftArm")
    private <T extends BipedEntityRenderState> void positionLeftArm(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        ArmedEntityRenderStateAccess renderStateAccess = ((ArmedEntityRenderStateAccess)state);
        ItemStack handItem = renderStateAccess.getOffHandStack();
        if(renderStateAccess.isRestrained()) {
            restrainedAnimation();
        } else if(handItem != null) {
            tryItemAnimation(handItem, false);
        }
    }

    private void restrainedAnimation() {
        this.rightArm.pitch = 0.0F;
        this.rightArm.yaw = 0.0F;
        this.leftArm.pitch = 0.0F;
        this.leftArm.yaw = 0.0F;
    }

    private void tryItemAnimation(ItemStack itemStack, boolean rightHand) {
        if(itemStack.getItem().equals(DecorativeItemsME.FIRE_OF_ORTHANC)) {
            float pitch = this.rightArm.pitch * 0.25F - 0.5F;
            this.rightArm.pitch = pitch;
            this.rightArm.yaw = 0.0F;
            this.leftArm.pitch = pitch;
            this.leftArm.yaw = 0.0F;
        } else if(itemStack.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) itemStack.getItem()).type.twoHanded)) {
            float pitch = -1.15f;
            this.rightArm.pitch = pitch;
            this.leftArm.pitch = pitch - 0.2f;
            this.rightArm.yaw = -0.35f;
            this.leftArm.yaw = 0.8f;
            // TODO: Fix me later
        //} else if((itemStack.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) itemStack.getItem()).type == ModWeaponTypes.SPEAR))) {
        //    if(!entity.isUsingItem() && entity instanceof PlayerEntity playerEntity) {
        //        int afkTime = PlayerMovementData.readAFK((IEntityDataSaver) playerEntity);
        //        if(afkTime > 60) {
        //            if (rightHand) this.rightArm.pitch = VERTICAL_ANGLE;
        //            else this.leftArm.pitch = VERTICAL_ANGLE;
        //        }
        //    } else if(entity instanceof MobEntity mob) {
        //        if (mob.isAiDisabled()) {
        //            if (rightHand) this.rightArm.pitch = VERTICAL_ANGLE;
        //            else this.leftArm.pitch = VERTICAL_ANGLE;
        //        }
        //    }
        } else if (itemStack.getItem() == WeaponItemsME.HELD_BANNER) {
            if (rightHand) this.rightArm.pitch = VERTICAL_ANGLE;
            else this.leftArm.pitch = VERTICAL_ANGLE;
        }else if (itemStack.getItem() == DecorativeItemsME.TORCH_OF_ORTHANC) {
            if(rightHand) {
                this.rightArm.pitch = VERTICAL_ANGLE;
            } else {
                this.leftArm.pitch = VERTICAL_ANGLE;
            }
        }
    }
}
