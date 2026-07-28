package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;

final class AttributeDefaults {
    private AttributeDefaults() {
    }

    @SuppressWarnings("unchecked")
    static AttributeSupplier get(LivingEntity entity) {
        EntityType<? extends LivingEntity> entityType =
                (EntityType<? extends LivingEntity>) entity.getType();
        return DefaultAttributes.getSupplier(entityType);
    }
}
