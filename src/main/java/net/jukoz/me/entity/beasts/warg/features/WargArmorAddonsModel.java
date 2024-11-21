package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargAnimations;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WargArmorAddonsModel extends SinglePartEntityModel<WargEntity> {

    private final ModelPart warg;
    private final ModelPart head;
    public WargArmorAddonsModel(ModelPart root) {
        this.warg = root.getChild("root");
        this.head = warg.getChild(EntityModelPartNames.BODY).getChild("upper_body").getChild(EntityModelPartNames.HEAD);
    }

    public static TexturedModelData getTexturedModelDataFront() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData upper_body = body.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

        ModelPartData body_armor = upper_body.addChild("body_armor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 2.0F));

        ModelPartData armor_addons = body_armor.addChild("armor_addons", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, -5.0F, -4.0F));

        ModelPartData front_addons = armor_addons.addChild("front_addons", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData skull2 = front_addons.addChild("skull2", ModelPartBuilder.create().uv(6, 43).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.1F))
                .uv(79, 48).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.2F))
                .uv(6, 30).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(6, 60).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.of(13.0F, -4.0F, 3.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    public static TexturedModelData getTexturedModelDataBack() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData upper_body = body.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

        ModelPartData body_armor = upper_body.addChild("body_armor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 2.0F));

        ModelPartData armor_addons = body_armor.addChild("armor_addons", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, -5.0F, -4.0F));

        ModelPartData back_addons = armor_addons.addChild("back_addons", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-1.0F, -9.0F, -1.0F, 2.0F, 7.0F, 8.0F, new Dilation(0.0F))
                .uv(6, 60).cuboid(-1.0F, -23.0F, 2.0F, 2.0F, 23.0F, 2.0F, new Dilation(-0.1F))
                .uv(20, 63).cuboid(-1.0F, -27.0F, 3.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = back_addons.addChild("cube_r1", ModelPartBuilder.create().uv(4, 99).cuboid(-16.0F, -8.5F, 0.0F, 17.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -21.5F, 3.0F, 0.0F, 0.0F, -1.4399F));

        ModelPartData cube_r2 = back_addons.addChild("cube_r2", ModelPartBuilder.create().uv(19, 81).cuboid(-1.0F, -8.5F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(-0.3F)), ModelTransform.of(-2.0F, -21.5F, 3.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData skull = back_addons.addChild("skull", ModelPartBuilder.create().uv(6, 43).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.1F))
                .uv(6, 30).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, 3.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public ModelPart getPart() {
        return warg;
    }

    @Override
    public void setAngles(WargEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        if(!entity.hasControllingPassenger()) {
            this.setHeadAngles(headYaw, headPitch);
        }

        if((entity.hasControllingPassenger() && entity.getControllingPassenger().isSprinting()) || entity.isRunning()) {
            this.animateMovement(WargAnimations.RUN, limbAngle, limbDistance, 1.2f, 1.2f);
        }
        else {
            this.animateMovement(WargAnimations.WALK, limbAngle, limbDistance, 1.5f, 1.5f);
        }

        this.updateAnimation(entity.idleAnimationState, WargAnimations.GROOM, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, WargAnimations.BITE, animationProgress, 1f);
        this.updateAnimation(entity.startSittingAnimationState, WargAnimations.SIT_DOWN, animationProgress, 3f);
        this.updateAnimation(entity.stopSittingAnimationState, WargAnimations.STAND_UP, animationProgress, 3f);
        this.updateAnimation(entity.sittingAnimationState, WargAnimations.SIT, animationProgress, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        warg.render(matrices, vertexConsumer, light, overlay, color);
    }
}
