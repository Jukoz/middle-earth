package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.moria;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class MoriaCaptainHelmetModel extends MoriaScreecherHelmetModel {

    public MoriaCaptainHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        head.addChild("side_spikes", ModelPartBuilder.create()
                .uv(48, 13).cuboid(-4.0F, -3.25F, -1.0F, 8.0F, 10.0F, 0.0F, new Dilation(0.0F))
                .uv(47, 25).cuboid(-21.0F, -3.25F, -1.0F, 8.0F, 10.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(8.5F, -7.2F, -1.0F, -0.4363F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}