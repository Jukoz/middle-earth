package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class IsenUrukSapperHelmetModel extends IsenUrukHelmetModel {

    public IsenUrukSapperHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        ModelPartData head = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("sapper_addons", ModelPartBuilder.create()
                .uv(-6, 42).cuboid(-6.5F, -3.025F, -6.5F, 13.0F, 0.0F, 6.0F, new Dilation(0.0F))
                .uv(3, -9).cuboid(0.0F, -12.25F, -6.0F, 0.0F, 10.0F, 9.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}