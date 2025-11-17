package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class OrcSpikeHelmetModel extends HelmetAddonModel {

    public OrcSpikeHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("spike_0", ModelPartBuilder.create()
                        .uv(0, 59).cuboid(-2.5F, -2.75F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -11.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        head.addChild("spike_1", ModelPartBuilder.create()
                .uv(0, 54).cuboid(0.0F, -2.75F, -2.5F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -11.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}