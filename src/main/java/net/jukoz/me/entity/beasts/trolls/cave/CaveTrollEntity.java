package net.jukoz.me.entity.beasts.trolls.cave;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.world.World;

public class CaveTrollEntity extends TrollEntity {
    public CaveTrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    public CaveTrollVariant getVariant() {
        return CaveTrollVariant.byId(this.getId());
    }
}
