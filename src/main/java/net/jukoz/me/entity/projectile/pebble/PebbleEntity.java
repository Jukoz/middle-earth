package net.jukoz.me.entity.projectile.pebble;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.projectile.AbstractProjectileEntity;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PebbleEntity extends AbstractProjectileEntity {
    private float damage;

    public PebbleEntity(EntityType<? extends PebbleEntity> entityType, World world) {
        super(entityType, world);
    }

    public PebbleEntity(World world, LivingEntity owner, float dmg) {
        super(ModEntities.PEBBLE, owner, world);
        this.damage = dmg;
    }

    protected Item getDefaultItem() {
        return ModResourceItems.PEBBLE;
    }
}
