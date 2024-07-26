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

                ModelPartData warg = root.addChild("warg", ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

                ModelPartData head = warg.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.of(14.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

                ModelPartData headArmor = head.addChild("headArmor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

                ModelPartData snoutArmor = headArmor.addChild("snoutArmor", ModelPartBuilder.create().uv(83, 22).cuboid(-6.2076F, 0.1044F, 2.1F, 13.0F, 5.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(7.3218F, -2.3967F, 3.1F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData helmet = headArmor.addChild("helmet", ModelPartBuilder.create().uv(3, 23).cuboid(-5.2F, -6.2623F, -4.8552F, 13.0F, 13.0F, 10.0F, new Dilation(-0.1F)), ModelTransform.of(3.859F, 1.9485F, -0.4F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData body = warg.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

                ModelPartData bodyArmor = body.addChild("bodyArmor", ModelPartBuilder.create(), ModelTransform.pivot(-11.9F, 6.0F, 4.1F));

                ModelPartData bodyArmorCube = bodyArmor.addChild("bodyArmorCube", ModelPartBuilder.create().uv(0, 0).cuboid(-26.8F, -12.0F, -1.9F, 28.0F, 13.0F, 10.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));
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
                this.updateAnimation(entity.startSittingAnimationState, WargAnimations.SITTING_DOWN, animationProgress, 1f);
                this.updateAnimation(entity.stopSittingAnimationState, WargAnimations.STANDING_UP, animationProgress, 1f);
                this.updateAnimation(entity.sittingAnimationState, WargAnimations.SITTING, animationProgress, 1f);
        }

        public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
                warg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        }
}