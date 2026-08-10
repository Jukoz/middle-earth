package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.goblintown;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class GoblinTownBoneMandibleModel extends HelmetAddonModel {

    public GoblinTownBoneMandibleModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("mandible_left", ModelPartBuilder.create()
                .uv(2, 15).cuboid(0.0F, -8.25F, -6.0F, 0.0F, 9.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(4.0F, 2.0F, -4.0F, 0.0F, -0.4363F, 0.0F));
        head.addChild("mandible_right", ModelPartBuilder.create()
                .uv(2, 15).cuboid(0.0F, -8.25F, -6.0F, 0.0F, 9.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(-4.0F, 2.0F, -4.0F, 0.0F, 0.4363F, 0.0F));
        head.addChild("bottom_jaw", ModelPartBuilder.create()
                .uv(0, 36).cuboid(-8.4497F, -9.75F, 2.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.3F)),
                ModelTransform.of(0.3498F, 4.5F, -8.9142F, 0.6603F, 0.6603F, 0.4439F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}