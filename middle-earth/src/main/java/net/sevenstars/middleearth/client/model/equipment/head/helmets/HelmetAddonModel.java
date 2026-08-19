package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class HelmetAddonModel<T extends LivingEntity> extends HumanoidModel<T> {

    public HelmetAddonModel(ModelPart root) {
        super(root);
    }
}
