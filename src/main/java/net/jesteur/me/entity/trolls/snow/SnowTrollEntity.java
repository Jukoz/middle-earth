package net.jesteur.me.entity.trolls.snow;

import net.jesteur.me.entity.trolls.TrollEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class SnowTrollEntity extends TrollEntity {
    public SnowTrollEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public SnowTrollVariant getVariant() {
        return SnowTrollVariant.byId(this.getId());
    }
}
