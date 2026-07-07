package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class IsenUrukSoldierHelmetModel extends IsenUrukCrestHelmetModel {

    public IsenUrukSoldierHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        ModelPartData head = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData side_spikes = head.addChild("side_spikes", ModelPartBuilder.create(),
                ModelTransform.origin(6.75F, -1.65F, -1.0F));

        side_spikes.addChild("spikes_right", ModelPartBuilder.create()
                        .uv(43, 53).cuboid(-2.7F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5672F));
        side_spikes.addChild("spikes_left", ModelPartBuilder.create()
                        .uv(43, 53).mirrored().cuboid(-2.3F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-13.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}