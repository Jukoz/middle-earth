package net.sevenstars.middleearth.entity.projectile.pinecone;

import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.projectile.AbstractProjectileEntity;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class LitPineconeEntity extends AbstractProjectileEntity {
    private float damage;


    public LitPineconeEntity(EntityType<? extends LitPineconeEntity> entityType, World world) {
        super(entityType, world);
    }

    public LitPineconeEntity(World world, LivingEntity owner, float dmg) {
        super(ModEntities.LIT_PINECONE, owner, world);
        this.damage = dmg;
    }

    public LitPineconeEntity(World world, double x, double y, double z) {
        super(ModEntities.LIT_PINECONE, x, y, z, world);
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.setOnFireFor(4);
        if(this.getWorld() instanceof ServerWorld serverWorld)
            entity.damage(serverWorld, this.getDamageSources().thrown( this, this.getOwner()), this.damage);
    }

    protected Item getDefaultItem() {
        return ModResourceItems.LIT_PINECONE;
    }

}
