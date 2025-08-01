package net.sevenstars.middleearth.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.item.ResourceItemsME;

public class WebbedEntity extends AbstractProjectileEntity {
    private float damage;

    public WebbedEntity(EntityType<? extends WebbedEntity> entityType, World world) {
        super(entityType, world);
    }

    public WebbedEntity(World world, LivingEntity owner, float dmg) {
        super(ModEntities.WEB, owner, world, new ItemStack(Items.COBWEB));
        this.damage = dmg;
    }

    protected Item getDefaultItem() {
        return ResourceItemsME.PEBBLE;
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if(this.getWorld() instanceof ServerWorld serverWorld) {
            entity.damage(serverWorld, this.getDamageSources().thrown( this, this.getOwner()), this.damage);
            if(entity instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAVING, 200));
            }
        }
    }
}
