package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class OrcSpikeHelmetModel extends HelmetAddonModel {

    public OrcSpikeHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData spike = head.addChild("spike", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -9.5F, 0.0F));
        spike.addChild("spike_1", ModelPartBuilder.create().uv(0, 59).cuboid(-2.5F, -3.5F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5F, 0.0F, 0.0F, -0.7854F, 0.0F));
        spike.addChild("spike_2", ModelPartBuilder.create().uv(0, 54).cuboid(0.0F, -3.5F, -2.5F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}