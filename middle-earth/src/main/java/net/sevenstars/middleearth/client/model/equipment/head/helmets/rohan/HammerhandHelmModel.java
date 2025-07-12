package net.sevenstars.middleearth.client.model.equipment.head.helmets.rohan;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class HammerhandHelmModel extends RohanHelmetModel {
    
    public HammerhandHelmModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData HornsR = head.addChild("horn_right_attachment", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-6.4F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        HornsR.addChild("horn_right", ModelPartBuilder.create()
                .uv(50, 0).cuboid(-3.5F, -11.25F, 0.0F, 7.0F, 13.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(-5.5F, -5.0F, 0.0F, 0.0F, 0.0F, 0.1396F));

        ModelPartData HornsR2 = head.addChild("horn_left_attachment", ModelPartBuilder.create()
                .uv(0, 0).cuboid(4.6F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        HornsR2.addChild("horn_left", ModelPartBuilder.create()
                .uv(50, 13).mirrored().cuboid(-3.5F, -11.25F, 0.0F, 7.0F, 13.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(5.7F, -5.0F, 0.0F, 0.0F, 0.0F, -0.1396F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}