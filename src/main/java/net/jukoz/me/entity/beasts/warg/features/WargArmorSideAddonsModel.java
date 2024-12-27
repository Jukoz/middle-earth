package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargAnimations;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class WargArmorSideAddonsModel extends SinglePartEntityModel<WargEntity> {

    private final ModelPart warg;
    public WargArmorSideAddonsModel(ModelPart root) {
        this.warg = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelDataSideSkulls() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData upper_body = body.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

        ModelPartData body_armor = upper_body.addChild("body_armor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 2.0F));

        ModelPartData front_armor = body_armor.addChild("front_armor", ModelPartBuilder.create(), ModelTransform.pivot(7.8F, 1.5F, -1.0F));

        ModelPartData addons_front_armor = front_armor.addChild("addons_front_armor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData skulls = addons_front_armor.addChild("skulls", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData skull_01 = skulls.addChild("skull_01", ModelPartBuilder.create(), ModelTransform.of(0.092F, 2.1315F, 6.8137F, -2.7489F, -0.3054F, 3.1416F));

        ModelPartData cube_r1 = skull_01.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -2.0F, 0.0F, -0.7969F, -0.0393F, 0.3624F));

        ModelPartData skull_hat_r1 = skull_01.addChild("skull_hat_r1", ModelPartBuilder.create().uv(24, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 8.0F, 6.0F, new Dilation(-0.75F)), ModelTransform.of(-2.0F, 0.0F, 0.0F, -0.2182F, 0.2182F, 0.0F));

        ModelPartData skull_r1 = skull_01.addChild("skull_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(-1.0F)), ModelTransform.of(-2.0F, 0.0F, 0.0F, -0.2182F, 0.2182F, 0.0F));

        ModelPartData skull_02 = skulls.addChild("skull_02", ModelPartBuilder.create(), ModelTransform.of(-0.6F, 3.5F, 5.0F, 0.0F, 3.098F, 0.0F));

        ModelPartData cube_r2 = skull_02.addChild("cube_r2", ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.692F, -2.8685F, -1.8863F, -0.4037F, -0.2145F, -0.1479F));

        ModelPartData skull_hat_r2 = skull_02.addChild("skull_hat_r2", ModelPartBuilder.create().uv(24, 32).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 9.0F, 6.0F, new Dilation(-0.75F))
                .uv(0, 32).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(-1.0F)), ModelTransform.of(1.5129F, -0.8639F, -1.0609F, 0.1309F, -0.1745F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public ModelPart getPart() {
        return warg;
    }

    @Override
    public void setAngles(WargEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

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

    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        warg.render(matrices, vertexConsumer, light, overlay, color);
    }
}
