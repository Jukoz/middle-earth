package net.sevenstars.middleearth.entity.projectile.pebble;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.projectile.AbstractProjectileEntity;
import net.sevenstars.middleearth.item.ResourceItemsME;

public class PebbleEntity extends AbstractProjectileEntity {
    private float damage;

    public PebbleEntity(EntityType<? extends PebbleEntity> entityType, Level world) {
        super(entityType, world);
    }

    public PebbleEntity(Level world, LivingEntity owner, float dmg) {
        super(EntitiesME.PEBBLE, owner, world, new ItemStack(ResourceItemsME.PEBBLE));
        this.damage = dmg;
    }

    protected Item getDefaultItem() {
        return ResourceItemsME.PEBBLE;
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        //if(this.getOwner() instanceof ShireHobbitEntity && entity instanceof ShireHobbitEntity) return;
        if(this.level() instanceof ServerLevel)
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), this.damage);
    }
}
