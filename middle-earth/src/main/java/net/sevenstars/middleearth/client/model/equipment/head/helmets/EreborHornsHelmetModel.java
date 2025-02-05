package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class EreborHornsHelmetModel extends HelmetAddonModel {

    public EreborHornsHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addon_swirl_horns = head.addChild("addon_swirl_horns", ModelPartBuilder.create(), ModelTransform.pivot(-11.0F, -7.2F, -3.4645F));

        ModelPartData horns = addon_swirl_horns.addChild("horns", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 3.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData right_horns = horns.addChild("right_horns", ModelPartBuilder.create(), ModelTransform.pivot(13.0F, -3.8284F, -0.4142F));

        right_horns.addChild("rightswirlhorntip_0", ModelPartBuilder.create().uv(21, 59).cuboid(-8.0F, 4.0F, -5.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        right_horns.addChild("rightswirlhorntip_1", ModelPartBuilder.create().uv(20, 50).cuboid(-8.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));
        right_horns.addChild("rightswirlhorn", ModelPartBuilder.create().uv(1, 52).cuboid(-8.0F, 0.0F, -1.0F, 4.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.8284F, 1.4142F, 0.7854F, 0.0F, 0.0F));

        ModelPartData left_horns = horns.addChild("left_horns", ModelPartBuilder.create(), ModelTransform.pivot(11.0F, -3.8284F, -0.4142F));

        left_horns.addChild("leftswirlhorntip_0", ModelPartBuilder.create().uv(21, 59).mirrored().cuboid(5.0F, 4.0F, -5.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        left_horns.addChild("leftswirlhorntip_1", ModelPartBuilder.create().uv(20, 50).mirrored().cuboid(4.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));
        left_horns.addChild("leftswirlhorn", ModelPartBuilder.create().uv(1, 52).mirrored().cuboid(4.0F, 0.0F, -1.0F, 4.0F, 7.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.8284F, 1.4142F, 0.7854F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}