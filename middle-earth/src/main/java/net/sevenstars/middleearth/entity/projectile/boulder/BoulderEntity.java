package net.sevenstars.middleearth.entity.projectile.boulder;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sevenstars.middleearth.entity.EntitiesME;

public class BoulderEntity extends ThrowableProjectile {

    public BoulderEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
    }

    public BoulderEntity(Level world, double x, double y, double z) {
        super(EntitiesME.BOULDER, x, y, z, world);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if(this.getOwner() != null)  {
            Entity entity2 = this.getOwner();
            if (entity2 instanceof LivingEntity && entity2 != null && !entity.level().isClientSide()) {
                entity.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity2), 10.0f);
            }
        }
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().explode((Entity)this, this.getX(), this.getY(), this.getZ(), 0.5f, false, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }
}
