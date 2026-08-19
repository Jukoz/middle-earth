package net.sevenstars.middleearth.entity.projectile;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.EntityTypeTagsME;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.middleearth.world.features.vegetation.ModVegetationConfiguredFeatures;

import java.util.Optional;

public class WebbedEntity extends AbstractProjectileEntity {
    private static ConfiguredFeature feature;
    private float damage;

    public WebbedEntity(EntityType<? extends WebbedEntity> entityType, Level world) {
        super(entityType, world);
    }

    public WebbedEntity(Level world, LivingEntity owner, float dmg) {
        super(EntitiesME.WEB, owner, world, new ItemStack(Items.COBWEB));
        this.damage = dmg;
        if(feature == null) {
            if(!this.level().isClientSide) {
                if(this.level() instanceof ServerLevel serverWorld) {
                    Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = serverWorld.registryAccess()
                            .lookupOrThrow(Registries.CONFIGURED_FEATURE)
                            .get(ModVegetationConfiguredFeatures.PATCH_WEBBING);
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
        if (this.level().isClientSide) {
            this.spawnParticles(2);
        }
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if(this.level() instanceof ServerLevel serverWorld) {
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), this.damage);
            if(entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAVING, 200));
                livingEntity.addEffect(new MobEffectInstance(ModStatusEffects.RESTRAINED, 200));
                //if(entity instanceof ServerPlayerEntity playerEntity) {
                //    ServerPlayNetworking.send(playerEntity, new PacketLivingEntityData(1));
                //}
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            Entity entity = entityHitResult.getEntity();
            if(entity.getType().is(EntityTypeTagsME.UNGOLIENI)) {
                return;
            }
        }
        super.onHit(hitResult);
        spawnWebbing();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        spawnWebbing();
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        super.onInsideBlock(state);
        spawnWebbing();
    }

    private void spawnParticles(int amount) {
        if (amount > 0) {
            for (int j = 0; j < amount; j++) {
                this.level()
                        .addParticle(
                                new BlockParticleOption(ParticleTypes.BLOCK, ModNatureBlocks.WEBBING.defaultBlockState()),
                                this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0
                        );
            }
        }
    }

    private void spawnWebbing() {
        if(this.level() instanceof ServerLevel serverWorld && feature != null) {
            feature.place(serverWorld, serverWorld.getChunkSource().getGenerator(), random, this.blockPosition());
        }
    }
}
