package net.jukoz.me.entity.beasts.trolls.snow;

import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class SnowTrollEntity extends TrollEntity {
    public SnowTrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    public SnowTrollVariant getVariant() {
        return SnowTrollVariant.byId(this.getId());
    }


}
