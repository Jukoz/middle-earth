package net.jesteur.me.entity.spear;

import net.jesteur.me.entity.ModEntities;
import net.jesteur.me.entity.hobbits.HobbitEntity;
import net.jesteur.me.entity.pebble.PebbleEntity;
import net.jesteur.me.item.ModRessourceItems;
import net.jesteur.me.item.ModWeaponItems;
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

public class SpearEntity extends ThrownItemEntity {
    private Item item;
    private float damage;

    public SpearEntity(EntityType<? extends SpearEntity> entityType, World world) {
        super(entityType, world);
        this.item = ModWeaponItems.GONDOR_SPEAR;
    }

    public SpearEntity(World world, Item item, LivingEntity owner, float dmg) {
        super(ModEntities.SPEAR, owner, world);
        this.item = item;
        this.damage = dmg;
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
        if(this.getOwner() instanceof HobbitEntity && entity instanceof HobbitEntity) return;
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
