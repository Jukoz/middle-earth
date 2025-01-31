package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class DaleHelmetModel extends HelmetAddonModel {

    public DaleHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addon = head.addChild("addons", ModelPartBuilder.create().uv(0, 37).cuboid(-5.0F, -5.3F, -6.0F, 10.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 29).cuboid(0.0F, -13.3F, -6.0F, 0.0F, 11.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData bone = addon.addChild("spike", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -11.0F, 0.0F));
        bone.addChild("spike_cube", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        ModelPartData Plumes = bone.addChild("Plumes", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        ModelPartData Plume = Plumes.addChild("plume_0", ModelPartBuilder.create().uv(0, 51).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -2.1369F, -0.5338F, 1.8835F));
        Plume.addChild("plume_cube_0", ModelPartBuilder.create().uv(0, 45).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        ModelPartData Plume2 = Plumes.addChild("plume_1", ModelPartBuilder.create().uv(14, 51).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3841F, -0.761F, 0.4898F));
        Plume2.addChild("plume_cube_1", ModelPartBuilder.create().uv(14, 45).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        ModelPartData Plume3 = Plumes.addChild("plume_2", ModelPartBuilder.create().uv(28, 51).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -2.8254F, -0.5049F, 2.4806F));
        Plume3.addChild("plume_cube_2", ModelPartBuilder.create().uv(28, 45).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        ModelPartData Plume4 = Plumes.addChild("plume_3", ModelPartBuilder.create().uv(42, 51).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -2.7925F, 0.5672F, 3.1416F));
        Plume4.addChild("plume_cube_3", ModelPartBuilder.create().uv(42, 45).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        ModelPartData Plume5 = Plumes.addChild("plume_4", ModelPartBuilder.create().uv(42, 39).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.7964F, -0.9478F, 1.7551F));
        Plume5.addChild("plume_cube_4", ModelPartBuilder.create().uv(42, 33).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}