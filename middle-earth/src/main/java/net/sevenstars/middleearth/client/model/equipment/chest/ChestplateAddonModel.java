package net.sevenstars.middleearth.client.model.equipment.chest;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class ChestplateAddonModel<T extends LivingEntity> extends HumanoidModel<T> {
    public ChestplateAddonModel(ModelPart root) {
        super(root);
    }

}
