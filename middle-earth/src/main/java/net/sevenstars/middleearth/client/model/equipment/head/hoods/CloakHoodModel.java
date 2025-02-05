package net.sevenstars.middleearth.client.model.equipment.head.hoods;

import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;

public class CloakHoodModel<T extends LivingEntity> extends HelmetAddonModel<T> {
    private final ModelPart hood;

    public CloakHoodModel(ModelPart root) {
        super(root);
        hood = root.getChild("hat").getChild("hood");
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
