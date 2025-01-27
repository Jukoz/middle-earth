package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class HelmetAddonModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public HelmetAddonModel(ModelPart root) {
        super(root);
    }
}