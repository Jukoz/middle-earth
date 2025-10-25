package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor.dolguldur;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class BlackReaverHelmetModel extends HelmetAddonModel {

    public BlackReaverHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("horn_left", ModelPartBuilder.create()
                .uv(0, -8).mirrored().cuboid(0.0F, -14.25F, -9.0F, 0.0F, 10.0F, 10.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(3.0F, 1.0F, -5.0F, -0.4363F, -0.6981F, 0.0F));

        head.addChild("horn_right", ModelPartBuilder.create()
                .uv(0, -8).cuboid(0.0F, -14.25F, -9.0F, 0.0F, 10.0F, 10.0F, new Dilation(0.0F)),
                ModelTransform.of(-3.0F, 1.0F, -5.0F, -0.4363F, 0.6981F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}