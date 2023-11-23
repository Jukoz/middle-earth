package net.jukoz.me.entity.model;

import net.jukoz.me.entity.humanoid_npc.HumanoidNpcModel;
import net.minecraft.client.model.*;

public class HobbitModel {
    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        return TexturedModelData.of(
                HumanoidNpcModel.getModelData(dilation),
                64, 72);
    }
}
