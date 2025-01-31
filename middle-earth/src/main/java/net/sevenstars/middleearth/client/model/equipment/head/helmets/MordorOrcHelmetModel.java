package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class MordorOrcHelmetModel extends HelmetAddonModel {

    public MordorOrcHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData snout = head.addChild("snoot", ModelPartBuilder.create(), ModelTransform.of(0.0F, -0.0F, -2.59F, 1.1345F, 0.0F, 0.0F));

        snout.addChild("snout_cube", ModelPartBuilder.create().uv(0, 16).cuboid(-3.5F, -3.7F, -7.5F, 7.0F, 7.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
        head.addChild("pikes", ModelPartBuilder.create().uv(34, -2).cuboid(0.0F, -14.6F, -8.5F, 0.0F, 12.0F, 15.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("forward_extension", ModelPartBuilder.create().uv(40, 0).cuboid(-4.0F, -8.2F, -7.8F, 8.0F, 9.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("down_hat_extension", ModelPartBuilder.create().uv(46, 35).cuboid(-4.0F, -8.4F, -4.0F, 8.0F, 8.0F, 1.0F, new Dilation(0.9F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));
        head.addChild("side_extension_left", ModelPartBuilder.create().uv(18, 0).cuboid(0.0F, -3.2F, 1.0F, 1.0F, 6.0F, 4.0F, new Dilation(0.5F)), ModelTransform.of(-4.0F, -5.0F, 0.2F, 0.0F, -0.4363F, 0.0F));
        head.addChild("side_extension_right", ModelPartBuilder.create().uv(29, 0).cuboid(-1.0F, -3.2F, 1.0F, 1.0F, 6.0F, 4.0F, new Dilation(0.5F)), ModelTransform.of(4.0F, -5.0F, 0.2F, 0.0F, 0.4363F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}