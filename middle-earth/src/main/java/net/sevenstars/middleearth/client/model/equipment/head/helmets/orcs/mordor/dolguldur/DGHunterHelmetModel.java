package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor.dolguldur;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class DGHunterHelmetModel extends HelmetAddonModel {

    public DGHunterHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("addon_eye", ModelPartBuilder.create()
                        .uv(6, 0).cuboid(-4.5F, -3.55F, -4.475F, 9.0F, 1.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData addons = head.addChild("fin", ModelPartBuilder.create()
                .uv(0, 0).cuboid(0.0F, -13.0F, -6.0F, 0.0F, 11.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        addons.addChild("side_right", ModelPartBuilder.create()
                        .uv(24, 15).cuboid(-5.25F, -9.75F, 0.252F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(-5.0F, -7.0F, -1.25F, 0.0F, 0.6545F, 0.0F));

        addons.addChild("side_left", ModelPartBuilder.create()
                        .uv(24, 0).mirrored().cuboid(-0.5F, -9.75F, 0.284F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(4.814F, -7.0F, -1.5F, 0.0F, -0.6545F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}