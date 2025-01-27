package net.sevenstars.middleearth.entity.projectile.pebble;

import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.hobbits.shire.ShireHobbitEntity;
import net.sevenstars.middleearth.entity.projectile.AbstractProjectileEntity;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
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

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if(this.getOwner() instanceof ShireHobbitEntity && entity instanceof ShireHobbitEntity) return;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), this.damage);
    }
}
