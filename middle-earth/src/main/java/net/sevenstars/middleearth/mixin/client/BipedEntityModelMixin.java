package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.light.HeldLight;
import net.sevenstars.middleearth.light.HeldLights;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.client.renderer.light.HangingLanterns;
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
        middleearth$raiseLamp(state, true);
        middleearth$lowerLantern(state, armPose, true);
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
        middleearth$raiseLamp(state, false);
        middleearth$lowerLantern(state, armPose, false);
    }

    /** How far the arm is raised for a light held up before the holder: a little short of level (level is half pi). */
    private static final float RAISED_ARM_PITCH = -1.3F;

    /**
     * A light in the off hand is carried up: a focused lamp or lantern out before the face, the arm along the gaze;
     * a torch, candle or lamp held up before the holder, a little below level, turning half as far as the head.
     */
    private void middleearth$raiseLamp(BipedEntityRenderState state, boolean rightArm) {
        ArmedEntityRenderStateAccess access = (ArmedEntityRenderStateAccess) state;
        ItemStack offHand = access.getOffHandStack();
        boolean offHandRight = state.mainArm == Arm.LEFT;
        if (access.isRestrained() || offHand == null || offHandRight != rightArm) {
            return;
        }
        HeldLight light = HeldLights.active(true, offHand);
        if (light == null) {
            return;
        }
        boolean raised = light.hold() == HeldLight.Hold.RAISED;
        ModelPart arm = rightArm ? this.rightArm : this.leftArm;
        arm.pitch = raised ? RAISED_ARM_PITCH : -MathHelper.HALF_PI + state.pitch * MathHelper.RADIANS_PER_DEGREE;
        arm.yaw = state.relativeHeadYaw * MathHelper.RADIANS_PER_DEGREE * (raised ? 0.5F : 1.0F);
        arm.roll = 0.0F;
    }

    /**
     * A shaded lantern in the off hand is carried at the side: the arm hangs down (without the bend forward of an
     * item held up), swinging as it walks, and the lantern hangs from the fist ({@link HangingLanterns}).
     */
    private void middleearth$lowerLantern(BipedEntityRenderState state, BipedEntityModel.ArmPose armPose, boolean rightArm) {
        ArmedEntityRenderStateAccess access = (ArmedEntityRenderStateAccess) state;
        boolean offHandRight = state.mainArm == Arm.LEFT;
        if (access.isRestrained() || offHandRight != rightArm || armPose != BipedEntityModel.ArmPose.ITEM
            || !HangingLanterns.hangs(access.getOffHandStack())) {
            return;
        }
        (rightArm ? this.rightArm : this.leftArm).pitch += MathHelper.PI / 10;
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
