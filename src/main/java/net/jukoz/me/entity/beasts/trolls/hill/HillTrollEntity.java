package net.jukoz.me.entity.beasts.trolls.hill;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.world.World;

public class HillTrollEntity extends TrollEntity {
    public HillTrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }
}
