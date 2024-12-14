package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargAnimations;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WargArmorModel extends SinglePartEntityModel<WargEntity> {
        private final ModelPart warg;
        private final ModelPart head;
        public WargArmorModel(ModelPart root) {
                this.warg = root.getChild("root");
                this.head = warg.getChild(EntityModelPartNames.BODY).getChild("upper_body").getChild(EntityModelPartNames.HEAD);
                }
        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = new ModelData();
                ModelPartData modelPartData = modelData.getRoot();
                ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

                ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

                ModelPartData upper_body = body.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

                ModelPartData body_armor = upper_body.addChild("body_armor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 2.0F));

                ModelPartData front_armor = body_armor.addChild("front_armor", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.4F, -5.0F, 14.0F, 11.0F, 10.0F, new Dilation(0.0F))
                        .uv(49, 0).cuboid(-4.0F, -6.5F, -5.0F, 11.0F, 8.0F, 10.0F, new Dilation(0.4F)), ModelTransform.pivot(7.8F, 1.5F, -1.0F));

                ModelPartData left_chains = front_armor.addChild("left_chains", ModelPartBuilder.create(), ModelTransform.pivot(0.5F, -3.5F, 5.3F));

                ModelPartData chains_r1 = left_chains.addChild("chains_r1", ModelPartBuilder.create().uv(102, 24).cuboid(-6.5F, -2.0F, 0.0F, 13.0F, 10.0F, 0.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0007F, -0.0076F, -0.0002F));

                ModelPartData right_chains = front_armor.addChild("right_chains", ModelPartBuilder.create(), ModelTransform.of(0.5F, -2.5F, -5.3F, 0.0F, 0.0F, -0.1745F));

                ModelPartData chains_r2 = right_chains.addChild("chains_r2", ModelPartBuilder.create().uv(102, 24).cuboid(-6.5F, -2.0F, 0.0F, 13.0F, 10.0F, 0.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -1.0F, 0.0F, 0.0007F, 0.0076F, 0.1744F));

                ModelPartData back_armor = body_armor.addChild("back_armor", ModelPartBuilder.create(), ModelTransform.pivot(-7.2F, 1.5F, -1.0F));

                ModelPartData backlegplate_r1 = back_armor.addChild("backlegplate_r1", ModelPartBuilder.create().uv(42, 62).cuboid(-8.2F, -6.5F, -5.0F, 15.0F, 9.0F, 10.0F, new Dilation(0.5F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData backarmor_r1 = back_armor.addChild("backarmor_r1", ModelPartBuilder.create().uv(37, 36).cuboid(-7.8F, -6.5F, -5.0F, 14.0F, 11.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.1F, 0.0F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData head = upper_body.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.of(14.3858F, -1.8862F, 1.5F, 0.0F, 0.0F, 0.2618F));

                ModelPartData head_armor = head.addChild("head_armor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

                ModelPartData Lbone_snout_r1 = head_armor.addChild("Lbone_snout_r1", ModelPartBuilder.create().uv(101, 0).cuboid(-7.2076F, -0.8956F, -2.3986F, 11.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(7.4218F, 1.1634F, -4.1014F, -2.7477F, -0.1032F, -3.0907F));

                ModelPartData Lbone_snout_r2 = head_armor.addChild("Lbone_snout_r2", ModelPartBuilder.create().uv(101, 0).cuboid(-7.2076F, -0.8956F, 2.3986F, 11.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(7.4218F, 1.1634F, 3.1014F, 2.7477F, 0.1032F, -3.0907F));

                ModelPartData underhelmet_r1 = head_armor.addChild("underhelmet_r1", ModelPartBuilder.create().uv(5, 57).cuboid(-3.2F, -6.6623F, -3.8552F, 11.0F, 9.0F, 8.0F, new Dilation(-0.3F)), ModelTransform.of(5.859F, 2.9485F, -0.4F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData helmet_r1 = head_armor.addChild("helmet_r1", ModelPartBuilder.create().uv(3, 23).cuboid(-3.2F, -6.2623F, -4.8552F, 11.0F, 13.0F, 10.0F, new Dilation(-0.1F)), ModelTransform.of(5.859F, 1.9485F, -0.4F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData snout2 = head_armor.addChild("snout2", ModelPartBuilder.create(), ModelTransform.pivot(7.3218F, -2.3967F, 3.1F));

                ModelPartData snout_r1 = snout2.addChild("snout_r1", ModelPartBuilder.create().uv(36, 23).cuboid(-6.2076F, 0.1044F, 2.1F, 13.0F, 5.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData snout_r2 = snout2.addChild("snout_r2", ModelPartBuilder.create().uv(98, 35).cuboid(-5.2076F, 3.2829F, 2.1F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.2F, 0.0F, -3.1416F, 0.0F, 3.1416F));
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