package net.jukoz.me.client.model.equipment.chest;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class CustomChestplateModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public  Identifier CHESTPLATE_ADDON_TEXTURE;

    public CustomChestplateModel(ModelPart root) {
        super(root);
    }
}