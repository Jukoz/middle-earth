package net.sevenstars.middleearth.entity.beasts.trolls.stone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;
import net.sevenstars.middleearth.entity.goals.BeastTargetPlayerGoal;
import net.sevenstars.middleearth.utils.SpawnUtil;

import java.util.List;

public class StoneTrollEntity extends TrollEntity {
    public static final EntityDataAccessor<Integer> PETRIFYING = SynchedEntityData.defineId(StoneTrollEntity.class, EntityDataSerializers.INT);
    public static final List<ResourceKey<Biome>> darkBiomes = List.of(

    );
    private final int PETRIFYING_DURATION = 600;

    public StoneTrollEntity(EntityType<? extends StoneTrollEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new RestrictSunGoal(this));
        this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.2d));
        this.targetSelector.addGoal(4, new BeastTargetPlayerGoal(this, this.getDisposition()));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PETRIFYING, PETRIFYING_DURATION);
    }

    @Override
    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.walkAnimation.speed());
        float g = this.walkAnimation.position(); // Todo : getPos()
        float h = this.isSitting() ? -0.75f : 0;
        return (double)this.getBbHeight() - 1.0d + (double)(0.12F * Mth.cos(g * 1.5F) * 2.0F * f) + h;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction positionUpdater) {
        int i = this.getPassengers().indexOf(passenger);
        if (i < 0) {
            return;
        }

        float f = -0.5f; // Z-Offset

        Vec3 vec3d = new Vec3(0.0, 0.0, f).yRot(-this.yBodyRot * ((float)Math.PI / 180));
        positionUpdater.accept(passenger, this.getX() + vec3d.x, this.getY() + this.getMountedHeightOffset(), this.getZ() + vec3d.z);
    }

    public void setPetrifying(int petrifying) {
        this.entityData.set(PETRIFYING, petrifying);
    }
    public int getPetrifying() {
        return this.entityData.get(PETRIFYING);
    }
    public boolean isPetrified() {
        return this.entityData.get(PETRIFYING) == -1;
    }

    @Override
    public boolean canThrow() {
        return !this.isPetrified() && !this.isSitting();
    }

    @Override
    public boolean canCharge() {
        return !this.isPetrified() && !this.isSitting();
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        if(!this.isPetrified()) {
            return super.isBondingItem(itemStack);
        }
        return false;
    }

    @Override
    protected boolean isSunBurnTick() {
        if (this.level().isDay() && !this.level().isClientSide()) {
            if(this.level().getBiome(blockPosition()).is(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "is_biome_in_darkness")))){
                return false;
            }
            float f = this.level().getBlockState(blockPosition()).getShadeBrightness(level(), blockPosition());
            BlockPos blockPos = BlockPos.containing(this.getX(), this.getEyeY(), this.getZ());
            return f > 0.5f && this.level().canSeeSky(blockPos);
        }
        return false;
    }

    @Override
    public void aiStep() {
        if (this.isAlive() && !this.level().isClientSide() && this.getPetrifying() != -1 && this.level().getBiome(this.blockPosition()).unwrapKey().isPresent()) {
            ResourceKey<Biome> biomeKey = this.level().getBiome(this.blockPosition()).unwrapKey().get();

            boolean inDaylight = this.isSunBurnTick() && this.getItemBySlot(EquipmentSlot.CHEST).isEmpty() && !darkBiomes.contains(biomeKey);

            if (inDaylight) {
                this.setPetrifying(this.getPetrifying() - 1);
                if(this.getPetrifying() <= 0) {
                    this.turnToStone();
                }
                if(this.tickCount % 10 == 0) {
                    this.playSound(SoundEvents.FIRE_AMBIENT, 1.0f, 1.0f);
                }
            }
            else {
                this.setPetrifying(PETRIFYING_DURATION);
            }
        }
        if(getPetrifying() != -1 && getPetrifying() < PETRIFYING_DURATION && this.level().isClientSide() && this.tickCount % 3 == 0) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + ((random.nextFloat() * 2f) - 1f), this.getY() + 1d + random.nextFloat(), this.getZ() + ((random.nextFloat() * 2f) - 1f), random.nextFloat() / 8.0f, 0.2f, random.nextFloat() / 8.0f);
        }

        super.aiStep();
    }

    public void turnToStone() {
        this.setNoAi(true);
        this.convertTo(EntitiesME.PETRIFIED_TROLL, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        view.putInt("Petrifying", this.getPetrifying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        this.entityData.set(PETRIFYING, view.getInt("Petrifying"));
    }

    @Override
    public void handleDamageEvent(DamageSource damageSource) {
        if(!this.isPetrified()) {
            super.handleDamageEvent(damageSource);
        }
    }

    public static boolean canSpawn(EntityType<StoneTrollEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor world, MobSpawnType spawnReason) {
        return true;
    }
}
