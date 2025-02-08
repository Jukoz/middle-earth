package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class SnailEntityModel extends EntityModel<SnailEntityRenderState> {

    protected SnailEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData snail = modelPartData.addChild("snail", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.4F)), ModelTransform.pivot(0.0F, 20.0F, 0.0F));

        ModelPartData body = snail.addChild("body", ModelPartBuilder.create().uv(12, 22).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(0, -1).cuboid(-1.0F, -4.0F, -4.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, -1).mirrored().cuboid(1.0F, -4.0F, -4.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
}
