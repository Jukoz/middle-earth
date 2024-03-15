package net.jukoz.me.client.model.equipment;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class InnerArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public InnerArmorModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16)
                        .cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.8f)),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16)
                        .cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.5F)),
                ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored()
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.5F)),
                ModelTransform.pivot(5.0f, 2.0f, 0.0f));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16)
                .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.75F)),
                ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().
                cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.75F)),
                ModelTransform.pivot(1.9f, 12.0f, 0.0f));

        return TexturedModelData.of(modelData, 64, 32);
    }
}