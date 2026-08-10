package net.sevenstars.middleearth.client.model.equipment.head.helmets.dwarves;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class DwarvenMinerHelmetModel extends HelmetAddonModel {

    public DwarvenMinerHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("flaps", ModelPartBuilder.create()
                .uv(0, 20).cuboid(-4.0F, 1.55F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.9F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData right_candle = head.addChild("right_candle", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-1.0F, -1.25F, 1.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(20, 12).cuboid(-1.0F, -1.25F, 5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.1F))
                .uv(0, 14).cuboid(-1.0F, -1.25F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(8, 0).cuboid(-2.0F, -1.25F, -4.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 22).cuboid(-1.0F, -5.25F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.origin(-5.5F, -4.5F, -4.5F));
        right_candle.addChild("flame_right_0", ModelPartBuilder.create()
                .uv(0, 21).cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, 0.7854F, 0.0F));
        right_candle.addChild("flame_right_1", ModelPartBuilder.create()
                .uv(0, 21).cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        ModelPartData left_candle = head.addChild("left_candle", ModelPartBuilder.create()
                        .uv(0, 0).mirrored().cuboid(-1.0F, -1.25F, 1.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(20, 12).mirrored().cuboid(-1.0F, -1.25F, 5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
                .uv(0, 14).mirrored().cuboid(-1.0F, -1.25F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(8, 0).mirrored().cuboid(-2.0F, -1.25F, -4.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 22).mirrored().cuboid(-1.0F, -5.25F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.origin(5.5F, -4.5F, -4.5F));
        left_candle.addChild("flame_left_0", ModelPartBuilder.create()
                .uv(0, 21).mirrored().cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
        left_candle.addChild("flame_left_1", ModelPartBuilder.create()
                .uv(0, 21).mirrored().cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}