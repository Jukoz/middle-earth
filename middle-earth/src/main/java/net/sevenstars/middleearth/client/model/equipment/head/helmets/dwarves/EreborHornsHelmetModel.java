package net.sevenstars.middleearth.client.model.equipment.head.helmets.dwarves;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class EreborHornsHelmetModel extends HelmetAddonModel {

    public EreborHornsHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData addon_horns = head.addChild("addon_horns", ModelPartBuilder.create(),
                ModelTransform.origin(-11.0F, -8.0208F, -3.4645F));

        ModelPartData right_addon_horn = addon_horns.addChild("right_addon_horn", ModelPartBuilder.create(),
                ModelTransform.of(-1.0F, 3.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData right_horn = right_addon_horn.addChild("right_horn", ModelPartBuilder.create(),
                ModelTransform.origin(13.0F, -3.8284F, -0.4142F));

        right_horn.addChild("right_horn_0", ModelPartBuilder.create()
                .uv(21, 59).cuboid(-8.0F, 4.75F, -5.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        right_horn.addChild("right_horn_1", ModelPartBuilder.create()
                .uv(20, 50).cuboid(-8.0F, 4.75F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));
        right_horn.addChild("right_horn_2", ModelPartBuilder.create()
                .uv(1, 52).cuboid(-8.0F, 0.75F, -1.0F, 4.0F, 7.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 2.8284F, 1.4142F, 0.7854F, 0.0F, 0.0F));

        ModelPartData left_horn = right_addon_horn.addChild("left_horn", ModelPartBuilder.create(),
                ModelTransform.origin(11.0F, -3.8284F, -0.4142F));

        left_horn.addChild("left_horn_0", ModelPartBuilder.create()
                        .uv(21, 59).mirrored().cuboid(5.0F, 4.75F, -5.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        left_horn.addChild("left_horn_1", ModelPartBuilder.create()
                        .uv(20, 50).mirrored().cuboid(4.0F, 4.75F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0.0F, 2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));
        left_horn.addChild("left_horn_2", ModelPartBuilder.create()
                        .uv(1, 52).mirrored().cuboid(4.0F, 0.75F, -1.0F, 4.0F, 7.0F, 5.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0.0F, 2.8284F, 1.4142F, 0.7854F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}