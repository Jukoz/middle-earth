package net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.armored;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class HelmetSkullModel extends HelmetAddonModel {

    public HelmetSkullModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("skull", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -40.0F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 13).cuboid(-3.0F, -39.25F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.2F))
                .uv(0, 26).cuboid(-3.0F, -39.25F, -3.0F, 6.0F, 9.0F, 6.0F, new Dilation(0.4F))
                .uv(36, 53).cuboid(-0.5F, -41.25F, -0.5F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F))
                .uv(40, 60).cuboid(-5.0F, -37.55F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 23.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}