package net.sevenstars.middleearth.entity.projectile.pinecone;

import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.projectile.AbstractProjectileEntity;
import net.sevenstars.middleearth.item.ResourceItemsME;
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
        super(EntitiesME.PINECONE, owner, world, new ItemStack(ResourceItemsME.PINECONE));
        this.damage = dmg;
    }

    protected Item getDefaultItem() {
        return ResourceItemsME.PINECONE;
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }
}
