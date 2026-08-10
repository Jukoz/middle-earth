package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.goblintown;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.FlapHelmetModel;

public class GoblinTownTunnelerHelmetModel extends FlapHelmetModel {

    public GoblinTownTunnelerHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData Candle = head.addChild("candle", ModelPartBuilder.create()
                .uv(0, 22).mirrored().cuboid(-2.6F, -13.05F, -1.6F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 22).mirrored().cuboid(0.4F, -12.05F, 0.5F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.origin(0.0F, 0.3F, 0.0F));

        Candle.addChild("flame_0", ModelPartBuilder.create()
                        .uv(0, 21).mirrored().cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(1.4F, -13.8F, 1.5F, 0.0F, 0.7854F, 0.0F));
        Candle.addChild("flame_1", ModelPartBuilder.create()
                        .uv(0, 21).mirrored().cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(1.4F, -13.8F, 1.5F, 0.0F, -0.7854F, 0.0F));
        Candle.addChild("flame_2", ModelPartBuilder.create()
                        .uv(0, 21).mirrored().cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-1.6F, -14.8F, -0.6F, 0.0F, -0.7854F, 0.0F));
        Candle.addChild("flame_3", ModelPartBuilder.create()
                        .uv(0, 21).mirrored().cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-1.6F, -14.8F, -0.6F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}