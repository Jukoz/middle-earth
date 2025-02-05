package net.sevenstars.middleearth.entity.projectile.pinecone;

import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.projectile.AbstractProjectileEntity;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
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

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }
}
