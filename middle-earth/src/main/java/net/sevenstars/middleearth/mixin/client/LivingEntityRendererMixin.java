package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    private final static float VERTICAL_ANGLE = -1.4f;

    @Inject(at = @At("TAIL"), method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V")
    private <T extends LivingEntity, S extends LivingEntityRenderState>
    void updateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        System.out.println("Living entity render state");
        ItemStack mainHandStack = livingEntity.getMainHandStack();
        ItemStack offHandStack = livingEntity.getOffHandStack();
        ((ArmedEntityRenderStateAccess)livingEntityRenderState).setMainHandStack(mainHandStack);
        ((ArmedEntityRenderStateAccess)livingEntityRenderState).setOffHandStack(offHandStack);
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private <S extends LivingEntityRenderState>
    void render(S livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        System.out.println("Living entity render");
        System.out.println(((ArmedEntityRenderStateAccess)livingEntityRenderState).getMainHandStack());
        System.out.println(((ArmedEntityRenderStateAccess)livingEntityRenderState).getOffHandStack());
    }
/*
    @Inject(at = @At("TAIL"), method = "positionRightArm")
    private <T extends BipedEntityRenderState> void positionRightArm(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        List<ItemStack> handItems = new ArrayList<>();
        armPose.isTwoHanded()
        handItems.add(entity.getStackInHand(Hand.MAIN_HAND));
        handItems.add(entity.getStackInHand(Hand.OFF_HAND));
        if(handItems.get(0) != null) {
            tryItemAnimation(handItems.get(0), entity, true);
        }
    }

    @Inject(at = @At("TAIL"), method = "positionLeftArm")
    private <T extends BipedEntityRenderState> void positionLeftArm(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        List<ItemStack> handItems = new ArrayList<>();
        handItems.add(entity.getStackInHand(Hand.MAIN_HAND));
        handItems.add(entity.getStackInHand(Hand.OFF_HAND));
        if(handItems.get(1) != null) {
            tryItemAnimation(handItems.get(1), entity, false);
        }
    }

    private void tryItemAnimation(ItemStack itemStack, LivingEntity entity, boolean rightHand) {
        if(itemStack.getItem().equals(ModDecorativeItems.FIRE_OF_ORTHANC)) {
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
        } else if((itemStack.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) itemStack.getItem()).type == ModWeaponTypes.SPEAR))) {
            if(!entity.isUsingItem() && entity instanceof PlayerEntity playerEntity) {
                int afkTime = PlayerMovementData.readAFK((IEntityDataSaver) playerEntity);
                if(afkTime > 60) {
                    if (rightHand) this.rightArm.pitch = VERTICAL_ANGLE;
                    else this.leftArm.pitch = VERTICAL_ANGLE;
                }
            } else if(entity instanceof MobEntity mob) {
                if (mob.isAiDisabled()) {
                    if (rightHand) this.rightArm.pitch = VERTICAL_ANGLE;
                    else this.leftArm.pitch = VERTICAL_ANGLE;
                }
            }
        } else if (itemStack.getItem() == ModWeaponItems.HELD_BANNER) {
            if (rightHand) this.rightArm.pitch = VERTICAL_ANGLE;
            else this.leftArm.pitch = VERTICAL_ANGLE;
        }else if (itemStack.getItem() == ModDecorativeItems.TORCH_OF_ORTHANC) {
            if(rightHand) {
                this.rightArm.pitch = VERTICAL_ANGLE;
            } else {
                this.leftArm.pitch = VERTICAL_ANGLE;
            }
        }
    }*/
}
