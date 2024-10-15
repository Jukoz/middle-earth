package net.jukoz.me.client.model.equipment.chest;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ChestplateAddonModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public ChestplateAddonModel(ModelPart root) {
        super(root);
    }
}