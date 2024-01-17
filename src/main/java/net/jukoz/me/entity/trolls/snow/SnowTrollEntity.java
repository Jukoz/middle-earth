package net.jukoz.me.entity.trolls.snow;

import net.jukoz.me.entity.trolls.TrollEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.world.World;

public class SnowTrollEntity extends TrollEntity {
    public SnowTrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    public SnowTrollVariant getVariant() {
        return SnowTrollVariant.byId(this.getId());
    }
}
