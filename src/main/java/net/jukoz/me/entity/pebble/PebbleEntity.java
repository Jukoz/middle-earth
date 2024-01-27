package net.jukoz.me.entity.pebble;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PebbleEntity extends ThrownItemEntity {
    private Item item = ModResourceItems.PEBBLE;
    private float damage;

    public PebbleEntity(EntityType<? extends PebbleEntity> entityType, World world) {
        super(entityType, world);
    }

    public PebbleEntity(World world, LivingEntity owner, float dmg) {
        super(ModEntities.PEBBLE, owner, world);
        this.damage = dmg;
    }

    public PebbleEntity(World world, double x, double y, double z) {
        super(ModEntities.PEBBLE, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return item;
    }

    private ParticleEffect getParticleParameters() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(item));
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for(int i = 0; i < 8; ++i) {
                getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if(this.getOwner() instanceof ShireHobbitEntity && entity instanceof ShireHobbitEntity) return;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), this.damage);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!getWorld().isClient) {
            getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }
}
