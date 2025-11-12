package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class IsenUrukCommanderHelmetModel extends IsenUrukHelmetModel {

    public IsenUrukCommanderHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        ModelPartData head = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("large_fan", ModelPartBuilder.create()
                        .uv(0, 17).cuboid(-13.0F, -6.75F, 0.0F, 26.0F, 12.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -10.875F, 2.3F, -0.4363F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}