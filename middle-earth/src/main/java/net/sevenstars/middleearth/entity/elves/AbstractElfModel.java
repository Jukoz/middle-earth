package net.sevenstars.middleearth.entity.elves;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.passive.PassiveEntity;


public abstract class AbstractElfModel<T extends PassiveEntity> extends BipedEntityModel<T> {
    protected AbstractElfModel(ModelPart pRoot) {
        super(pRoot);
    }
}
