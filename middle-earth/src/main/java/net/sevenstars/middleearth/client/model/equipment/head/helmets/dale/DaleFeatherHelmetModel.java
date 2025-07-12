package net.sevenstars.middleearth.client.model.equipment.head.helmets.dale;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class DaleFeatherHelmetModel extends DaleHelmetModel {

    public DaleFeatherHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("spike", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -10.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        ModelPartData feather_duster = head.addChild("feather_duster", ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, -11.0F, 0.0F));

        ModelPartData plumes = feather_duster.addChild("plumes", ModelPartBuilder.create(),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        ModelPartData plume_0 = plumes.addChild("plumes_0", ModelPartBuilder.create()
                .uv(0, 51).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -2.1369F, -0.5338F, 1.8835F));
        plume_0.addChild("plume_0", ModelPartBuilder.create()
                .uv(0, 45).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        ModelPartData plume_1 = plumes.addChild("plumes_1", ModelPartBuilder.create()
                .uv(14, 51).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3841F, -0.761F, 0.4898F));
        plume_1.addChild("plume_1", ModelPartBuilder.create()
                .uv(14, 45).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        ModelPartData plume_2 = plumes.addChild("plumes_2", ModelPartBuilder.create()
                .uv(28, 51).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -2.8254F, -0.5049F, 2.4806F));
        plume_2.addChild("plume_2", ModelPartBuilder.create()
                .uv(28, 45).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        ModelPartData plume_3 = plumes.addChild("plumes_3", ModelPartBuilder.create()
                .uv(42, 51).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -2.7925F, 0.5672F, 3.1416F));
        plume_3.addChild("plume_3", ModelPartBuilder.create()
                .uv(42, 45).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        ModelPartData plume_4 = plumes.addChild("plumes_4", ModelPartBuilder.create()
                .uv(42, 39).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -1.7964F, -0.9478F, 1.7551F));
        plume_4.addChild("plume_4", ModelPartBuilder.create()
                .uv(42, 33).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}