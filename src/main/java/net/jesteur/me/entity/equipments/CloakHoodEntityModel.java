package net.jesteur.me.entity.equipments;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;

public class CloakHoodEntityModel <T extends LivingEntity> extends AnimalModel<T> {
    private final ModelPart hood;
    public CloakHoodEntityModel(ModelPart hood) {
        this.hood = hood;
    }
    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return null;
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return null;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
