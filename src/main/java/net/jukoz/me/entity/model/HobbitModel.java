package net.jukoz.me.entity.model;

import net.jukoz.me.entity.HumanoidModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class HobbitModel {
    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        return TexturedModelData.of(
                HumanoidModel.getModelData(dilation),
                64, 64);
    }
}
