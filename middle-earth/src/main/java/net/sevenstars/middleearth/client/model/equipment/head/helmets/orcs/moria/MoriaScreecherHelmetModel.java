package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.moria;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class MoriaScreecherHelmetModel extends HelmetAddonModel {

    public MoriaScreecherHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("large_crest", ModelPartBuilder.create()
                        .uv(28, -18).cuboid(0.0F, -15.25F, -7.0F, 0.0F, 14.0F, 18.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("upper_jaw", ModelPartBuilder.create()
                        .uv(0, 17).cuboid(-5.3268F, -7.5878F, -1.8578F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(0.8268F, -5.3903F, -7.9422F, -0.5025F, 0.7273F, -0.357F));

        head.addChild("bottom_jaw", ModelPartBuilder.create()
                .uv(0, 31).cuboid(-8.4497F, -9.75F, 2.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.3F)),
                ModelTransform.of(0.3498F, 4.5F, -8.9142F, 0.6603F, 0.6603F, 0.4439F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}