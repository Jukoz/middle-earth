package net.sevenstars.middleearth.client.model.equipment.head.helmets.elves;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class EgladilSentinelHelmetModel extends LorienHelmetModel {

    public EgladilSentinelHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("lower_extension", ModelPartBuilder.create()
                .uv(0, 11).cuboid(-4.0F, 0.85F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.55F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}