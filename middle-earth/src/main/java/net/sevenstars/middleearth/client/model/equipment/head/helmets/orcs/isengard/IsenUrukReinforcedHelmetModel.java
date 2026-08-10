package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class IsenUrukReinforcedHelmetModel extends IsenUrukCrestHelmetModel {

    public IsenUrukReinforcedHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        ModelPartData head = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("flaps", ModelPartBuilder.create()
                        .uv(35, 3).cuboid(-4.0F, 0.75F, -4.0F, 8.0F, 5.0F, 5.0F, new Dilation(0.55F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("snout", ModelPartBuilder.create()
                .uv(0, 13).cuboid(-4.0F, -0.25F, -1.5F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -2.8F, -4.5F, 0.5236F, 0.0F, 0.0F));

        ModelPartData helmet_sides = head.addChild("helmet_sides", ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, -1.0F, 0.0F));

        helmet_sides.addChild("side_right", ModelPartBuilder.create()
                .uv(15, 53).mirrored().cuboid(-2.3F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-6.75F, -1.65F, 1.0F, 0.0F, 0.0F, -0.5672F));

        helmet_sides.addChild("side_left", ModelPartBuilder.create()
                .uv(15, 53).cuboid(-2.7F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new Dilation(0.0F)),
                ModelTransform.of(6.75F, -1.65F, 1.0F, 0.0F, 0.0F, 0.5672F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}