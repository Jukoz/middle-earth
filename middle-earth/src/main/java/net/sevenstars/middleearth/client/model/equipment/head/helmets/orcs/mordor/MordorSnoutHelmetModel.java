package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class MordorSnoutHelmetModel extends HelmetAddonModel {

    public MordorSnoutHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData snout = head.addChild("snout", ModelPartBuilder.create(),
                //edit -0.75 for height, nothing else
                ModelTransform.of(0.0F, -0.75F, -2.59F, 1.1345F, 0.0F, 0.0F));
        snout.addChild("snout_cube", ModelPartBuilder.create()
                .uv(0, 16).cuboid(-3.5F, -3.5F, -7.5F, 7.0F, 7.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        head.addChild("side_extension_left", ModelPartBuilder.create()
                .uv(18, 0).cuboid(0.0F, -2.25F, 1.0F, 1.0F, 6.0F, 4.0F, new Dilation(0.5F)),
                ModelTransform.of(-4.0F, -6.0F, 0.2F, 0.0F, -0.4363F, 0.0F));

        head.addChild("side_extension_right", ModelPartBuilder.create()
                .uv(29, 0).cuboid(-1.0F, -2.25F, 1.0F, 1.0F, 6.0F, 4.0F, new Dilation(0.5F)),
                ModelTransform.of(4.0F, -6.0F, 0.2F, 0.0F, 0.4363F, 0.0F));


        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);

    }
}