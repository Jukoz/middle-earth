package net.jukoz.me.entity.beasts.broadhoof.features;

import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatAnimations;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class BroadhoofGoatArmorModel extends SinglePartEntityModel<BroadhoofGoatEntity> {

    private final ModelPart broadhoofGoat;
    private final ModelPart head;

    public BroadhoofGoatArmorModel(ModelPart root) {
        this.broadhoofGoat = root.getChild("broadhoof_goat");
        this.head = broadhoofGoat.getChild(EntityModelPartNames.BODY).getChild(EntityModelPartNames.HEAD);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData broadhoof_goat = modelPartData.addChild("broadhoof_goat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = broadhoof_goat.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -11.0F, 2.0F));

        ModelPartData head = body.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, -9.0F, -0.2182F, 0.0F, 0.0F));

        ModelPartData headArmour = head.addChild("head_armour", ModelPartBuilder.create().uv(0, 1).cuboid(0.9F, -11.0F, 2.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.1F))
                .uv(0, 1).mirrored().cuboid(-7.9F, -11.0F, 2.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.pivot(2.0F, 0.0F, -8.0F));

        ModelPartData neckplate = headArmour.addChild("neckplate", ModelPartBuilder.create().uv(85, 5).cuboid(-5.0F, -7.0F, 4.0F, 6.0F, 14.0F, 6.0F, new Dilation(0.1F))
                .uv(44, 0).cuboid(-5.0F, -7.0F, -1.0F, 6.0F, 6.0F, 11.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData sideHangingPlate = headArmour.addChild("side_hanging_plate", ModelPartBuilder.create().uv(77, 53).cuboid(-5.0F, -4.0F, -1.0F, 6.0F, 5.0F, 11.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 2.0F, 2.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData bodyArmour = body.addChild("body_armour", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -3.0F, -20.0F, 12.0F, 11.0F, 20.0F, new Dilation(0.2F))
                .uv(0, 85).cuboid(-6.0F, 2.0F, -20.0F, 12.0F, 6.0F, 20.0F, new Dilation(0.1F))
                .uv(0, 31).cuboid(-7.0F, -5.0F, -21.0F, 14.0F, 16.0F, 13.0F, new Dilation(0.2F))
                .uv(0, 58).cuboid(-7.0F, -5.0F, -21.0F, 14.0F, 16.0F, 13.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, -6.0F, 9.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public ModelPart getPart() {
        return broadhoofGoat;
    }

    @Override
    public void setAngles(BroadhoofGoatEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(headYaw, headPitch);

        if((entity.hasControllingPassenger() && entity.getControllingPassenger().isSprinting()) || (entity.isAttacking() && !entity.hasControllingPassenger())) {
            this.animateMovement(BroadhoofGoatAnimations.RUN, limbAngle, limbDistance, 1.2f, 1.2f);
        }
        else {
            this.animateMovement(BroadhoofGoatAnimations.WALK, limbAngle, limbDistance, 3f, 3f);
        }

        this.updateAnimation(entity.idleAnimationState, BroadhoofGoatAnimations.EAT, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, BroadhoofGoatAnimations.RAM_ATTACK, animationProgress, 1f);
        this.updateAnimation(entity.startSittingAnimationState, BroadhoofGoatAnimations.LAY_DOWN, animationProgress, 3f);
        this.updateAnimation(entity.stopSittingAnimationState, BroadhoofGoatAnimations.STAND_UP, animationProgress, 3f);
        this.updateAnimation(entity.sittingAnimationState, BroadhoofGoatAnimations.LYING, animationProgress, 1f);
        this.updateAnimation(entity.chargeAnimationState, BroadhoofGoatAnimations.CHARGE_ATTACK, animationProgress, 1f);
        this.updateAnimation(entity.jumpAnimationState, BroadhoofGoatAnimations.JUMP, animationProgress, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}
