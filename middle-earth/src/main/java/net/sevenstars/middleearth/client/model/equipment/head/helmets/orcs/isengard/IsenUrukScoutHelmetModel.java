package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class IsenUrukScoutHelmetModel extends IsenUrukCrestHelmetModel {

    public IsenUrukScoutHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        ModelPartData head = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("flaps", ModelPartBuilder.create()
                .uv(35, 3).cuboid(-4.0F, 0.75F, -4.0F, 8.0F, 5.0F, 5.0F, new Dilation(0.55F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}