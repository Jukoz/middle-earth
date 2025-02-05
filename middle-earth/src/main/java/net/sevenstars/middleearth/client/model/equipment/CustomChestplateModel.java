package net.sevenstars.middleearth.client.model.equipment;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;

public class CustomChestplateModel extends BipedEntityModel<BipedEntityRenderState> {

    public CustomChestplateModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.8F)),
                ModelTransform.pivot(0.0F, -23.0F, 0.0F));

        body.addChild("innerChest", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, -0.0F, -2.068F, 8.0F, 12.0F, 4.0F, new Dilation(0.5F)),
                ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(32, 48).cuboid(-3.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F))
                .uv(40, 16).cuboid(-3.0F, -2.3F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)), ModelTransform.pivot(-5.0F, -21.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(48, 48).mirrored().cuboid(-1.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F)).mirrored(false)
                .uv(40, 32).mirrored().cuboid(-1.0F, -2.3F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)).mirrored(false), ModelTransform.pivot(5.0F, -21.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(-1.9F, -11.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
