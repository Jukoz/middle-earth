package net.jukoz.me.mixin.client;

import net.jukoz.me.item.ModDecorativeItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.items.weapons.ReachWeaponItem;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.jukoz.me.utils.IEntityDataSaver;
import net.jukoz.me.utils.PlayerMovementData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {
    @Shadow public BipedEntityModel.ArmPose leftArmPose;

    @Shadow public BipedEntityModel.ArmPose rightArmPose;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart leftArm;
    private final static float VERTICAL_ANGLE = -1.4f;

    @Inject(at = @At("TAIL"), method = "positionRightArm")
    private void positionRightArm(LivingEntity entity, CallbackInfo ci) {
        List<ItemStack> handItems = new ArrayList<>();
        entity.getHandItems().forEach(handItems::add);
        if(handItems.get(0) != null) {
            tryItemAnimation(handItems.get(0), entity, true);
        }
    }

    @Inject(at = @At("TAIL"), method = "positionLeftArm")
    private void positionLeftArm(LivingEntity entity, CallbackInfo ci) {
        List<ItemStack> handItems = new ArrayList<>();
        entity.getHandItems().forEach(handItems::add);
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
    }
}
