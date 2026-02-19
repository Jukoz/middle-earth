package net.sevenstars.middleearth.entity.barrel;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BoatEntityRenderState;

public class BarrelEntityModel extends EntityModel<BoatEntityRenderState> {
    protected BarrelEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 15)
                .cuboid(-7.0F, -16.0F, -7.0F, 2.0F, 15.0F, 14.0F, new Dilation(0.0F))
                .uv(32, 15).cuboid(5.0F, -16.0F, -7.0F, 2.0F, 15.0F, 14.0F, new Dilation(0.0F))
                .uv(0, 44).cuboid(-5.0F, -16.0F, -7.0F, 10.0F, 15.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 44).cuboid(-5.0F, -16.0F, 5.0F, 10.0F, 15.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F,
                        new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getBaseTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("water_patch", ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 6.0F, 12.0F),
                ModelTransform.of(0.0F, 10.0F, 0.0F, 0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 0, 0);
    }
}
