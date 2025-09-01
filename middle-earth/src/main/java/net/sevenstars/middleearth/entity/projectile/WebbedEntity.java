package net.sevenstars.middleearth.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.ModEntityTypeTags;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.middleearth.world.features.vegetation.ModVegetationConfiguredFeatures;

import java.util.Optional;

public class WebbedEntity extends AbstractProjectileEntity {
    private static ConfiguredFeature feature;
    private float damage;

    public WebbedEntity(EntityType<? extends WebbedEntity> entityType, World world) {
        super(entityType, world);
    }

    public WebbedEntity(World world, LivingEntity owner, float dmg) {
        super(ModEntities.WEB, owner, world, new ItemStack(Items.COBWEB));
        this.damage = dmg;
        if(feature == null) {
            if(!this.getWorld().isClient) {
                if(this.getWorld() instanceof ServerWorld serverWorld) {
                    Optional<? extends RegistryEntry<ConfiguredFeature<?, ?>>> optional = serverWorld.getRegistryManager()
                            .getOrThrow(RegistryKeys.CONFIGURED_FEATURE)
                            .getOptional(ModVegetationConfiguredFeatures.PATCH_WEBBING);
                    optional.ifPresent(configuredFeatureRegistryEntry -> feature = configuredFeatureRegistryEntry.value());
                }
            }
        }
    }

    protected Item getDefaultItem() {
        return ModNatureBlocks.WEBBING.asItem();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            this.spawnParticles(2);
        }
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
                livingEntity.addStatusEffect(new StatusEffectInstance(ModStatusEffects.RESTRAINED, 200));
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            Entity entity = entityHitResult.getEntity();
            if(entity.getType().isIn(ModEntityTypeTags.UNGOLIENI)) {
                return;
            }
        }
        super.onCollision(hitResult);
        spawnWebbing();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        spawnWebbing();
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        spawnWebbing();
    }

    private void spawnParticles(int amount) {
        if (amount > 0) {
            for (int j = 0; j < amount; j++) {
                this.getWorld()
                        .addParticleClient(
                                new BlockStateParticleEffect(ParticleTypes.BLOCK, ModNatureBlocks.WEBBING.getDefaultState()),
                                this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0
                        );
            }
        }
    }

    private void spawnWebbing() {
        if(this.getWorld() instanceof ServerWorld serverWorld) {
            feature.generate(serverWorld, serverWorld.getChunkManager().getChunkGenerator(), random, this.getBlockPos());
        }
    }
}
