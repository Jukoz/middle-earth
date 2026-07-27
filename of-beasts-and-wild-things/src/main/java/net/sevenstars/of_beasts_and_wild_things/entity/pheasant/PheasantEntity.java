package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import org.jetbrains.annotations.Nullable;

public class PheasantEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PheasantEntity.class, EntityDataSerializers.INT);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState diggingAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();

    public PheasantEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.getNavigation().setCanFloat(true);
    }

    public static AttributeSupplier.Builder createPheasantAttributes() {
        return net.minecraft.world.entity.Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    protected void customServerAiStep() {
        ServerLevel world = (ServerLevel) this.level();
        ProfilerFiller profiler = this.level().getProfiler();
        profiler.push("pheasantBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("pheasantActivityUpdate");
        PheasantBrain.updateActivities(this);
        profiler.pop();
        super.customServerAiStep();
    }

    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return PheasantBrain.create(this, dynamic);
    }

    public Brain<PheasantEntity> getBrain() {
        return (Brain<PheasantEntity>)super.getBrain();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return EntitiesWT.PHEASANT.create(world);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                 @Nullable SpawnGroupData entityData) {
        PheasantEntityVariant variant = Util.getRandom(PheasantEntityVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    //TODO to test
    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        this.entityData.set(VARIANT, view.getInt("Variant"));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

        wingFlap();
    }

    private void wingFlap() {
        Vec3 velocity = this.getDeltaMovement();
        if (!this.onGround() && velocity.y < 0.0) {
            this.setDeltaMovement(velocity.multiply(1.0, 0.6, 1.0));
        }
    }

    private void setupAnimationStates() {
        if(this.hasPose(Pose.DIGGING)) {
          diggingAnimationState.startIfStopped(this.tickCount);
        }
        else {
            diggingAnimationState.stop();
        }

        if(this.hasPose(Pose.STANDING)) {
            idleAnimationState.startIfStopped(this.tickCount);
        }
        else {
            idleAnimationState.stop();
        }

        if(!this.onGround()) {
            this.flapAnimationState.startIfStopped(this.tickCount);
        }
        else {
            this.flapAnimationState.stop();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PARROT_HURT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.9f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.9f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CHICKEN_STEP, 1.0f, 0.9f);
    }

    public PheasantEntityVariant getVariant() {
        return PheasantEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(PheasantEntityVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }
}
