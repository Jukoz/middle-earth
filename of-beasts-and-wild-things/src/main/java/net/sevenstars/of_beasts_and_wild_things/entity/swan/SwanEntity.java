package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModSchedule;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class SwanEntity extends AnimalEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(SwanEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(SwanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState intimidateAnimationState = new AnimationState();
    public SwanEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSwanAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MAX_HEALTH, 10.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.ATTACK_DAMAGE, 1.5)
                .add(EntityAttributes.ATTACK_SPEED, 1.0);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(SLEEPING, false);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("swanBrain");
        this.getBrain().tick(world, this);
        if(!this.getBrain().getSchedule().equals(ModSchedule.SWAN_DEFAULT))
        {
            profiler.swap("swanActivityUpdate");
            SwanBrain.updateActivities(this);
            profiler.pop();
        }

        this.updateFloating();
        super.mobTick(world);
    }

    public void startSleeping() {
        if (this.hasVehicle()) {
            this.stopRiding();
        }

        this.setSleeping(true);
        this.setVelocity(Vec3d.ZERO);
        this.velocityDirty = true;

        this.brain.forget(MemoryModuleType.WALK_TARGET);
        this.brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void stopSleeping() {
        this.setSleeping(false);
    }

    @Override
    public void tickMovement() {
        if(!this.getWorld().isClient) {
            this.setAttacking(this.getTarget() != null);
            System.out.println(this.getTarget());

            if(this.isAttacking() && this.getBrain().getSchedule() != Schedule.EMPTY) {
                this.getBrain().setSchedule(Schedule.EMPTY);
            }
            else if(!this.isAttacking() && this.getBrain().getSchedule() != ModSchedule.SWAN_DEFAULT) {
                this.getBrain().setSchedule(ModSchedule.SWAN_DEFAULT);
                this.getBrain().forget(MemoryModuleType.LOOK_TARGET);
                this.getBrain().forget(MemoryModuleType.WALK_TARGET);
                this.getBrain().resetPossibleActivities(ImmutableList.of());
                this.getBrain().refreshActivities(this.getWorld().getTimeOfDay(), this.getWorld().getTime());
            }

        }
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }

        super.tickMovement();
    }

    private void setupAnimationStates() {
        if(isSleeping()) {
            this.sleepingAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.sleepingAnimationState.stop();
        }
        if(this.isTouchingWater()) {
            this.swimmingAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.swimmingAnimationState.stop();
        }
        if(isAttacking()) {
            this.intimidateAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.intimidateAnimationState.stop();
        }

    }

    public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getAttacker)
                .filter(attacker -> attacker instanceof LivingEntity)
                .map(livingAttacker -> (LivingEntity)livingAttacker);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return getTargetInBrain();
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return SwanBrain.create(this, dynamic);
    }

    public Brain<SwanEntity> getBrain() {
        return (Brain<SwanEntity>)super.getBrain();
    }

    @Override
    public void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(VARIANT, view.getInt("Variant", 0));
    }

    @Override
    public boolean isSleeping() {
        return dataTracker.get(SLEEPING);
    }

    public void setSleeping(boolean isSleeping) {
        dataTracker.set(SLEEPING, isSleeping);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        SwanEntity child = ModEntities.SWAN.create(world, SpawnReason.BREEDING);
        int i = this.random.nextInt(2);

        SwanEntityVariant variant = i == 0 ? this.getVariant() : ((SwanEntity)entity).getVariant();
        if(child != null) {
            child.setVariant(variant);
            return child;
        }

        return null;
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return state.isIn(FluidTags.WATER);
    }

    private void updateFloating() {
        if (this.isTouchingWater()) {
            ShapeContext shapeContext = ShapeContext.of(this);
            if (!shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, this.getBlockPos(), true) || this.getWorld().getFluidState(this.getBlockPos().up()).isIn(FluidTags.WATER)) {
                this.setVelocity(this.getVelocity().multiply(0.5).add(0.0, 0.05, 0.0));
            } else {
                this.setOnGround(true);
            }
        }
    }

    public static boolean isValidSwanFood(LivingEntity entity) {
        return entity.getType().equals(ModEntities.SNAIL) || entity.getType().equals(EntityType.TADPOLE);
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        SwanEntityVariant variant = Util.getRandom(SwanEntityVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public SwanEntityVariant getVariant() {
        return SwanEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(SwanEntityVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 2.0F);
    }
}
