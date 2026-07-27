package net.sevenstars.middleearth.entity.projectile.pinecone;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.projectile.AbstractProjectileEntity;
import net.sevenstars.middleearth.item.ResourceItemsME;

public class PineconeEntity extends AbstractProjectileEntity {
    private float damage;

    public PineconeEntity(EntityType<? extends PineconeEntity> entityType, Level world) {
        super(entityType, world);
    }

    public PineconeEntity(Level world, LivingEntity owner, float dmg) {
        super(EntitiesME.PINECONE, owner, world, new ItemStack(ResourceItemsME.PINECONE));
        this.damage = dmg;
    }

    protected Item getDefaultItem() {
        return ResourceItemsME.PINECONE;
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
    }
}
