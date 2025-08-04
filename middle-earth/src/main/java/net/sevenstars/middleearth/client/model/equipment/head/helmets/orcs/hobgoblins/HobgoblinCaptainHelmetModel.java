package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.hobgoblins;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class HobgoblinCaptainHelmetModel extends HelmetAddonModel {

    public HobgoblinCaptainHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData crest = head.addChild("crest", ModelPartBuilder.create(),
                ModelTransform.origin(-1.5F, -11.5976F, -4.4512F));

        crest.addChild("crest_left", ModelPartBuilder.create()
                .uv(38, 53).mirrored().cuboid(-4.0F, -4.75F, 0.0F, 8.0F, 11.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(5.2366F, 0.5132F, 0.3928F, 0.0F, -0.3491F, 0.0F));

        crest.addChild("crest_right", ModelPartBuilder.create()
                .uv(38, 53).cuboid(-5.0F, -3.25F, 0.0F, 8.0F, 11.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(-1.3312F, -0.9868F, 0.0562F, 0.0F, 0.3491F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}