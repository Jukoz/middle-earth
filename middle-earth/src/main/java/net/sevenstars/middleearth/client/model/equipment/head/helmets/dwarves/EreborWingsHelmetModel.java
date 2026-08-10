package net.sevenstars.middleearth.client.model.equipment.head.helmets.dwarves;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class EreborWingsHelmetModel extends EreborHelmetModel {

    public EreborWingsHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("left_wing", ModelPartBuilder.create()
                .uv(32, 0).mirrored().cuboid(-5.9415F, -3.25F, -6.3165F, 15.0F, 8.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0.0F, -6.7624F, 4.6083F, 1.1849F, -0.9521F, -1.1082F));
        head.addChild("right_wing", ModelPartBuilder.create()
                .uv(32, 0).cuboid(-9.0585F, -3.25F, -6.3165F, 15.0F, 8.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -6.7624F, 4.6083F, 1.1849F, 0.9521F, 1.1082F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}