package net.sevenstars.of_beasts_and_wild_things.entity.projectile.thrown;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;

public class SwanEggEntity extends ThrowableItemProjectile {
    private static final EntityDimensions EMPTY_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);

    public SwanEggEntity(EntityType<SwanEggEntity> entityType, Level world) {
        super(entityType, world);
    }

    public SwanEggEntity(Level world, LivingEntity owner, ItemStack stack) {
        super(EntitiesWT.SWAN_EGG, owner, world);
        this.setItem(stack);
    }

    public SwanEggEntity(Level world, double x, double y, double z, ItemStack stack) {
        super(EntitiesWT.SWAN_EGG, x, y, z, world);
        this.setItem(stack);
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == EntityEvent.DEATH) {
            double d = 0.08;

            for (int i = 0; i < 8; i++) {
                this.level()
                        .addParticle(
                                new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                (this.random.nextFloat() - 0.5) * 0.08,
                                (this.random.nextFloat() - 0.5) * 0.08,
                                (this.random.nextFloat() - 0.5) * 0.08
                        );
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);

    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; j++) {
                    SwanEntity swanEntity = EntitiesWT.SWAN.create(this.level());
                    if (swanEntity != null) {
                        swanEntity.setAge(-24000);
                        swanEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        if (!swanEntity.fudgePositionAfterSizeChange(EMPTY_DIMENSIONS)) {
                            break;
                        }

                        this.level().addFreshEntity(swanEntity);
                    }
                }
            }

            this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ItemsWT.SWAN_EGG;
    }
}
