package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class HammerhandHelmModel extends HelmetAddonModel {


    public HammerhandHelmModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addons = head.addChild("addons", ModelPartBuilder.create().uv(30, 17).cuboid(0.0F, -13.5F, -9.5F, 0.0F, 11.0F, 17.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

        ModelPartData hornRight = addons.addChild("horns_right", ModelPartBuilder.create().uv(0, 0).cuboid(0.1F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.5F, -4.0F, 0.0F));
        hornRight.addChild("horn_right", ModelPartBuilder.create().uv(50, 0).cuboid(-3.5F, -12.0F, 1.0F, 7.0F, 13.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1396F));

        ModelPartData hornLeft = addons.addChild("horns_left", ModelPartBuilder.create().uv(0, 0).cuboid(11.1F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.5F, -4.0F, 0.0F));
        hornLeft.addChild("horn_left", ModelPartBuilder.create().uv(50, 13).mirrored().cuboid(-3.5F, -12.0F, 1.0F, 7.0F, 13.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(12.2F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1396F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}