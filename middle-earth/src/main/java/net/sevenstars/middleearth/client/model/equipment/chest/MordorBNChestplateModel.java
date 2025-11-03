package net.sevenstars.middleearth.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class MordorBNChestplateModel extends HaltChestplateModel {

    public MordorBNChestplateModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        ModelPartData body = modelData.getRoot().addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        body.addChild("chest_addon", ModelPartBuilder.create()
                .uv(0, 41).cuboid(-4.5F, -1.0F, -2.3F, 9.0F, 12.0F, 4.0F, new Dilation(0.51F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}