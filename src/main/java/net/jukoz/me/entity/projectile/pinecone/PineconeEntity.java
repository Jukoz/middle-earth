package net.jukoz.me.entity.projectile.pinecone;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.projectile.AbstractProjectleEntity;
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

public class PineconeEntity extends AbstractProjectleEntity {
    private Item item = ModResourceItems.PINECONE;
    private float damage;

    public PineconeEntity(EntityType<? extends PineconeEntity> entityType, World world) {
        super(entityType, world);
    }

    public PineconeEntity(World world, LivingEntity owner, float dmg) {
        super(ModEntities.PINECONE, owner, world);
        this.damage = dmg;
    }

    public PineconeEntity(World world, double x, double y, double z) {
        super(ModEntities.PINECONE, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return item;
    }
}
