package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class BipedEntityModelMixin {

    @Shadow @Final
    public ModelPart rightArm;

    @Shadow @Final public ModelPart leftArm;
    @Unique
    private static final float VERTICAL_ANGLE = -1.4f;


    @Inject(
            at = @At("TAIL"),
            method = "poseRightArm(Lnet/minecraft/world/entity/LivingEntity;)V"
    )
    private void positionRightArm(LivingEntity entity, CallbackInfo ci) {
        ItemStack handItem = entity.getMainHandItem();
        if (isRestrained(entity)) {
            restrainedAnimation();
        } else {
            tryItemAnimation(handItem, true);
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "poseLeftArm(Lnet/minecraft/world/entity/LivingEntity;)V"
    )
    private void positionLeftArm(LivingEntity entity, CallbackInfo ci) {
        ItemStack handItem = entity.getOffhandItem();
        if (isRestrained(entity)) {
            restrainedAnimation();
        } else {
            tryItemAnimation(handItem, false);
        }
    }

    @Unique
    private static boolean isRestrained(LivingEntity entity) {
        return entity.hasEffect(ModStatusEffects.RESTRAINED)
                && entity.getEffect(ModStatusEffects.RESTRAINED).getDuration() > 0;
    }

    @Unique
    private void restrainedAnimation() {
        this.rightArm.xRot = 0.0F;
        this.rightArm.yRot = 0.0F;
        this.leftArm.xRot = 0.0F;
        this.leftArm.yRot = 0.0F;
    }

    @Unique
    private void tryItemAnimation(ItemStack itemStack, boolean rightHand) {
        if(itemStack.getItem().equals(DecorativeItemsME.FIRE_OF_ORTHANC)) {
            float pitch = this.rightArm.xRot * 0.25F - 0.5F;
            this.rightArm.xRot = pitch;
            this.rightArm.yRot = 0.0F;
            this.leftArm.xRot = pitch;
            this.leftArm.yRot = 0.0F;
        } else if(itemStack.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) itemStack.getItem()).type.twoHanded)) {
            float pitch = -1.15f;
            this.rightArm.xRot = pitch;
            this.leftArm.xRot = pitch - 0.2f;
            this.rightArm.yRot = -0.35f;
            this.leftArm.yRot = 0.8f;
        } else if (itemStack.getItem() == WeaponItemsME.HELD_BANNER) {
            if (rightHand) this.rightArm.xRot = VERTICAL_ANGLE;
            else this.leftArm.xRot = VERTICAL_ANGLE;
        }else if (itemStack.getItem() == DecorativeItemsME.TORCH_OF_ORTHANC) {
            if(rightHand) {
                this.rightArm.xRot = VERTICAL_ANGLE;
            } else {
                this.leftArm.xRot = VERTICAL_ANGLE;
            }
        }
    }
}
