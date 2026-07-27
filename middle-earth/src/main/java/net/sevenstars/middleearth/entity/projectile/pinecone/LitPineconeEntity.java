package net.sevenstars.middleearth.entity.projectile.pinecone;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.projectile.AbstractProjectileEntity;
import net.sevenstars.middleearth.item.ResourceItemsME;

public class LitPineconeEntity extends AbstractProjectileEntity {
    private float damage;


    public LitPineconeEntity(EntityType<? extends LitPineconeEntity> entityType, Level world) {
        super(entityType, world);
    }

    public LitPineconeEntity(Level world, LivingEntity owner, float dmg) {
        super(EntitiesME.LIT_PINECONE, owner, world, new ItemStack(ResourceItemsME.LIT_PINECONE));
        this.damage = dmg;
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.igniteForSeconds(4);
        if(this.level() instanceof ServerLevel)
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), this.damage);
    }

    protected Item getDefaultItem() {
        return ResourceItemsME.LIT_PINECONE;
    }

}
