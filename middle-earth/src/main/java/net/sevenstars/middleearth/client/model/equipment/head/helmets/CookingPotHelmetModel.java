package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class CookingPotHelmetModel extends HelmetAddonModel {

    public CookingPotHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        return TexturedModelData.of(modelData, 64, 64);
    }

    protected static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("Hat2", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-3.5F, -33.75F, -3.5F, 7.0F, 2.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 23.0F, 0.0F));

        head.addChild("handle2_r1", ModelPartBuilder.create()
                .uv(1, -1).cuboid(0.0F, 0.75F, -1.0F, 0.0F, 3.0F, 3.0F, new Dilation(0.0F)),
                ModelTransform.of(5.0F, -6.3F, -0.4F, 0.0F, 0.0F, -0.3491F));

        head.addChild("handle1_r1", ModelPartBuilder.create()
                .uv(1, -1).cuboid(0.0F, 0.75F, -1.0F, 0.0F, 3.0F, 3.0F, new Dilation(0.0F)),
                ModelTransform.of(-4.9F, -6.3F, -0.4F, 0.0F, 0.0F, 0.3491F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}