package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.goblintown;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class GoblinKingCrownModel extends HelmetAddonModel {

    public GoblinKingCrownModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("Crown", ModelPartBuilder.create()
                .uv(57, 18).cuboid(-0.5F, -14.25F, -5.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F))
                .uv(57, 29).cuboid(4.0F, -12.25F, -1.0F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(57, 38).cuboid(-3.4F, -10.25F, -5.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(51, 29).cuboid(-5.0F, -12.25F, -1.0F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(51, 38).cuboid(2.4F, -10.25F, -5.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}