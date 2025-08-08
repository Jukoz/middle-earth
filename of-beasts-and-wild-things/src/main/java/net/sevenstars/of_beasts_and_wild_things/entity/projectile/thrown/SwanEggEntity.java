package net.sevenstars.of_beasts_and_wild_things.entity.projectile.thrown;

import net.minecraft.entity.*;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;
import net.sevenstars.of_beasts_and_wild_things.item.ModItems;

public class SwanEggEntity extends ThrownItemEntity {
    private static final EntityDimensions EMPTY_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);

    public SwanEggEntity(EntityType<SwanEggEntity> entityType, World world) {
        super(entityType, world);
    }

    public SwanEggEntity(World world, LivingEntity owner, ItemStack stack) {
        super(EntitiesWT.SWAN_EGG, owner, world, stack);
    }

    public SwanEggEntity(World world, double x, double y, double z, ItemStack stack) {
        super(EntitiesWT.SWAN_EGG, x, y, z, world, stack);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            double d = 0.08;

            for (int i = 0; i < 8; i++) {
                this.getWorld()
                        .addParticleClient(
                                new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()),
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
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().serverDamage(this.getDamageSources().thrown(this, this.getOwner()), 0.0F);

    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; j++) {
                    SwanEntity swanEntity = EntitiesWT.SWAN.create(this.getWorld(), SpawnReason.TRIGGERED);
                    if (swanEntity != null) {
                        swanEntity.setBreedingAge(-24000);
                        swanEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                        if (!swanEntity.recalculateDimensions(EMPTY_DIMENSIONS)) {
                            break;
                        }

                        this.getWorld().spawnEntity(swanEntity);
                    }
                }
            }

            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SWAN_EGG;
    }
}
