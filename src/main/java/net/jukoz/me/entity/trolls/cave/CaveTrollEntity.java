package net.jukoz.me.entity.trolls.cave;

import net.jukoz.me.entity.trolls.TrollEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.world.World;

public class CaveTrollEntity extends TrollEntity {
    public CaveTrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    public CaveTrollVariant getVariant() {
        return CaveTrollVariant.byId(this.getId());
    }
}
