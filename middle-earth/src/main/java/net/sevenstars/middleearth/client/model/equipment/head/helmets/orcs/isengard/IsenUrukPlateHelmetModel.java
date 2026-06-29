package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class IsenUrukPlateHelmetModel extends HelmetAddonModel {

    public IsenUrukPlateHelmetModel(ModelPart root) {
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

        head.addChild("fan", ModelPartBuilder.create()
                .uv(36, 29).cuboid(-7.0F, -7.75F, 0.0F, 14.0F, 9.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -15.0F, 4.0F, 2.5744F, 0.0F, 0.0F));

        ModelPartData side_spikes = head.addChild("side_spikes", ModelPartBuilder.create(),
                ModelTransform.origin(6.75F, -1.65F, -1.0F));

        side_spikes.addChild("spikes_right", ModelPartBuilder.create()
                        .uv(43, 53).cuboid(-2.7F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5672F));
        side_spikes.addChild("spikes_left", ModelPartBuilder.create()
                        .uv(43, 53).mirrored().cuboid(-2.3F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-13.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}