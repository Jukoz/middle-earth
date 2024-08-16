package net.jukoz.me.entity.projectile.pinecone;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.projectile.AbstractProjectileEntity;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class PineconeEntity extends AbstractProjectileEntity {
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
        return ModResourceItems.PINECONE;
    }
}
