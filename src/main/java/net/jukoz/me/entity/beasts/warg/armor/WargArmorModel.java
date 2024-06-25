package net.jukoz.me.entity.beasts.warg.armor;

import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class WargArmorModel extends SinglePartEntityModel<WargEntity> {
private final ModelPart root;
public WargArmorModel(ModelPart root) {
        this.root = root.getChild("root");
        }
public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.of(11.3858F, -0.8862F, 1.5F, 0.0F, 0.0F, 0.2618F));

        ModelPartData snout = head.addChild("snout", ModelPartBuilder.create().uv(83, 22).cuboid(-6.2076F, 0.1044F, 2.1F, 13.0F, 5.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(7.3218F, -2.3967F, 3.1F, -3.1416F, 0.0F, 3.1416F));

        ModelPartData helmet = head.addChild("helmet", ModelPartBuilder.create().uv(3, 23).cuboid(-5.2F, -6.2623F, -4.8552F, 13.0F, 13.0F, 10.0F, new Dilation(-0.1F)), ModelTransform.of(3.859F, 1.9485F, -0.4F, -3.1416F, 0.0F, 3.1416F));

        ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, 1.0F, 0.0F));

        ModelPartData bodyArmor = body.addChild("bodyArmor", ModelPartBuilder.create().uv(0, 0).cuboid(-26.8F, -12.0F, -1.9F, 28.0F, 13.0F, 10.0F, new Dilation(0.1F)), ModelTransform.of(-11.9F, 6.0F, 4.1F, -3.1416F, 0.0F, 3.1416F));
        return TexturedModelData.of(modelData, 128, 128);
        }

        @Override
        public ModelPart getPart() {
                return null;
        }

        @Override
        public void setAngles(WargEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
                this.getPart().traverse().forEach(ModelPart::resetTransform);
        }

        public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
                root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        }
}