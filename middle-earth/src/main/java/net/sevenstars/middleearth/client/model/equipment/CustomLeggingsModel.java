package net.sevenstars.middleearth.client.model.equipment;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;

public class CustomLeggingsModel extends BipedEntityModel<BipedEntityRenderState> {

    public CustomLeggingsModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();


        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("waist", ModelPartBuilder.create().uv(16, 16).cuboid(-3.9F, -0.0F, -2.1F, 8.0F, 12.0F, 4.0F, new Dilation(0.3F)),
                ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.1F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(-1.9F, -11.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(-1.968F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)).mirrored(false), ModelTransform.pivot(1.9F, -11.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
