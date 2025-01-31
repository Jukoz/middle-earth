package net.sevenstars.middleearth.client.model.equipment.head.hoods;

import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;

public class CloakHoodModel extends HelmetAddonModel {
    private final ModelPart hood;

    public CloakHoodModel(ModelPart root) {
        super(root);
        hood = root.getChild("hat").getChild("hood");
    }
}
