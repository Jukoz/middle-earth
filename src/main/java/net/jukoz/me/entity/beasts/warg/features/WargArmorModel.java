package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargAnimations;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class WargArmorModel extends SinglePartEntityModel<WargEntity> {
private final ModelPart warg;
public WargArmorModel(ModelPart root) {
        this.warg = root.getChild("root");
        }
        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = new ModelData();
                ModelPartData modelPartData = modelData.getRoot();
                ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

                ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

                ModelPartData upperBody = body.addChild("upperBody", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

                ModelPartData bodyArmor = upperBody.addChild("bodyArmor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 2.0F));

                ModelPartData frontBodyArmor = bodyArmor.addChild("frontBodyArmor", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.5F, -5.0F, 14.0F, 13.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(7.8F, 1.5F, -1.0F));

                ModelPartData backBodyArmor = bodyArmor.addChild("backBodyArmor", ModelPartBuilder.create(), ModelTransform.pivot(-7.2F, 1.5F, -1.0F));

                ModelPartData backBodyArmor_r1 = backBodyArmor.addChild("backBodyArmor_r1", ModelPartBuilder.create().uv(36, 36).cuboid(-5.8F, -6.5F, -5.0F, 12.0F, 13.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData head = upperBody.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.of(14.3858F, -1.8862F, 1.5F, 0.0F, 0.0F, 0.2618F));

                ModelPartData headArmour = head.addChild("headArmour", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

                ModelPartData snout_r1 = headArmour.addChild("snout_r1", ModelPartBuilder.create().uv(36, 23).cuboid(-6.2076F, 0.1044F, 2.1F, 13.0F, 5.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(7.3218F, -2.3967F, 3.1F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData helmet_r1 = headArmour.addChild("helmet_r1", ModelPartBuilder.create().uv(0, 23).cuboid(-5.2F, -6.2623F, -4.8552F, 13.0F, 13.0F, 10.0F, new Dilation(-0.1F)), ModelTransform.of(3.859F, 1.9485F, -0.4F, -3.1416F, 0.0F, 3.1416F));
                return TexturedModelData.of(modelData, 128, 128);
        }



        @Override
        public ModelPart getPart() {
                return warg;
        }

        @Override
        public void setAngles(WargEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
                this.getPart().traverse().forEach(ModelPart::resetTransform);

                if(entity.hasControllingPassenger() || entity.isAttacking()) {
                        this.animateMovement(WargAnimations.RUN, limbAngle, limbDistance, 1f, 1f);
                }
                else {
                        this.animateMovement(WargAnimations.WALK, limbAngle, limbDistance, 1.5f, 1.5f);
                }

                this.updateAnimation(entity.idleAnimationState, WargAnimations.GROOM, animationProgress, 1f);
                this.updateAnimation(entity.attackAnimationState, WargAnimations.BITE, animationProgress, 1f);
                this.updateAnimation(entity.startSittingAnimationState, WargAnimations.SIT_DOWN, animationProgress, 1f);
                this.updateAnimation(entity.stopSittingAnimationState, WargAnimations.STAND_UP, animationProgress, 1f);
                this.updateAnimation(entity.sittingAnimationState, WargAnimations.SIT, animationProgress, 1f);
        }

        public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
                warg.render(matrices, vertexConsumer, light, overlay, color);
        }
}