package net.jukoz.me.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class HelmetAddonModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public HelmetAddonModel(ModelPart root) {
        super(root);
    }
}