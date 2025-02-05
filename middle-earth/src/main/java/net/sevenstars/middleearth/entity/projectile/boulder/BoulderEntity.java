package net.sevenstars.middleearth.entity.projectile.boulder;

import net.sevenstars.middleearth.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BoulderEntity extends ThrownEntity {

    public BoulderEntity(EntityType<? extends ThrownEntity> type, World world) {
        super(type, world);
    }

    public BoulderEntity(EntityType<? extends ThrownEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }
    public BoulderEntity(World world, double x, double y, double z) {
        super(ModEntities.BOULDER, x, y, z, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getWorld().isClient) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if(this.getOwner() != null)  {
            Entity entity2 = this.getOwner();
            if (entity2 instanceof LivingEntity && entity2 != null) {
                entity.damage(this.getDamageSources().mobProjectile(this, (LivingEntity) entity2), 10.0f);
            }
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion((Entity)this, this.getX(), this.getY(), this.getZ(), 0.5f, false, World.ExplosionSourceType.MOB);
            this.discard();
        }
    }
}
