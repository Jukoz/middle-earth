package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class DwarvenMinerHelmetModel extends HelmetAddonModel {

    public DwarvenMinerHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head_addon_gr = head.addChild("head_addon_gr", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.8F, 0.0F));

        head_addon_gr.addChild("addon_flaps", ModelPartBuilder.create().uv(0, 20).cuboid(-4.0F, 0.8F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.9F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData addon_candles = head_addon_gr.addChild("addon_candles", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addon_right_candle = addon_candles.addChild("addon_right_candle", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(20, 12).cuboid(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.1F))
                .uv(0, 14).cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(8, 0).cuboid(-2.0F, -2.0F, -4.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 22).cuboid(-1.0F, -6.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, -4.5F, -4.5F));

        addon_right_candle.addChild("flame_r1", ModelPartBuilder.create().uv(0, 21).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, 0.7854F, 0.0F));
        addon_right_candle.addChild("flame_r2", ModelPartBuilder.create().uv(0, 21).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        ModelPartData addon_left_candle = addon_candles.addChild("addon_left_candle", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(20, 12).mirrored().cuboid(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
                .uv(0, 14).mirrored().cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(8, 0).mirrored().cuboid(-2.0F, -2.0F, -4.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 22).mirrored().cuboid(-1.0F, -6.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.5F, -4.5F, -4.5F));

        addon_left_candle.addChild("flame_r3", ModelPartBuilder.create().uv(0, 21).mirrored().cuboid(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
        addon_left_candle.addChild("flame_r4", ModelPartBuilder.create().uv(0, 21).mirrored().cuboid(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}