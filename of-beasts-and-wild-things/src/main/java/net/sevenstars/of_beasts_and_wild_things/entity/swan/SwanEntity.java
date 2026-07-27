package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.api.entity.ai.brain.SchedulesAPI;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.block.BlocksWT;
import net.sevenstars.of_beasts_and_wild_things.block.custom.BirdNest;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.MemoryModulesWT;
import net.sevenstars.of_beasts_and_wild_things.sound.SoundsWT;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// TODO Add sounds

public class SwanEntity extends Animal {
    private static final int EGG_COOLDOWN = 12000; // = 10 minutes
    public int idleAnimationTimeout = this.random.nextInt(400) + 800; // 40 - 60 Seconds
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SwanEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(SwanEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> INTIMIDATING = SynchedEntityData.defineId(SwanEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FIGHTING = SynchedEntityData.defineId(SwanEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState intimidateAnimationState = new AnimationState();
    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState swimIdleAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();
    public SwanEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(PathType.WATER, 0F);
    }

    public static AttributeSupplier.Builder createSwanAttributes() {
        return net.minecraft.world.entity.Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 1.5)
                .add(Attributes.ATTACK_SPEED, 1.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(SLEEPING, false);
        builder.define(INTIMIDATING, false);
        builder.define(FIGHTING, false);
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel world = (ServerLevel) this.level();
        ProfilerFiller profiler = this.level().getProfiler();
        profiler.push("swanBrain");
        this.getBrain().tick(world, this);
        profiler.popPush("swanActivityUpdate");
        SwanBrain.updateActivities(this);
        profiler.pop();

        this.updateHome();
        this.updateFloating();
        super.customServerAiStep();
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.0F;
    }

    private void updateHome() {
        Optional<GlobalPos> optional = this.getBrain().getMemory(MemoryModuleType.HOME);

        if(optional.isPresent()) {
            if(!this.level().getBlockState(optional.get().pos()).is(BlocksWT.BIRD_NEST)) {
                this.getBrain().eraseMemory(MemoryModuleType.HOME);
            }
        }
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new AmphibiousPathNavigation(this, world);
    }

    public void startSleeping() {
        if (this.isPassenger()) {
            this.stopRiding();
        }

        Optional<Integer> cooldown = this.getBrain().getMemory(MemoryModulesWT.EGG_COOLDOWN);
        if(cooldown.isEmpty()) {
            Optional<GlobalPos> optional = this.getBrain().getMemory(MemoryModuleType.HOME);

            if(optional.isPresent()) {
                double rand = this.random.nextDouble();

                if(rand < 0.15) {
                    BlockPos pos = optional.get().pos();
                    BlockState homeBlock = this.level().getBlockState(optional.get().pos());
                    if(homeBlock.is(BlocksWT.BIRD_NEST) && homeBlock.getValue(BirdNest.NEST_LEVEL) < 2) {
                        this.level().setBlockAndUpdate(pos, homeBlock.setValue(BirdNest.NEST_LEVEL, homeBlock.getValue(BirdNest.NEST_LEVEL) + 1));
                    }
                }
                this.getBrain().setMemory(MemoryModulesWT.EGG_COOLDOWN, EGG_COOLDOWN);
            }
        }

        this.setSleeping(true);
        this.setDeltaMovement(Vec3.ZERO);
        this.hasImpulse = true;

        this.brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void stopSleeping() {
        this.setSleeping(false);
    }

    @Override
    public void aiStep() {
        if(!this.level().isClientSide) {
            this.wingFlap();

            this.setAggressive(this.getTarget() != null);

            if(this.isBaby()) {
                this.getBrain().setSchedule(SchedulesAPI.DEFAULT_BABY);
            }
            else if(this.isAggressive() && !this.isFighting()) {
                this.getBrain().setSchedule(Schedule.EMPTY);
                this.setIntimidating(getTarget() instanceof Player);
                this.setFighting(true);
            }
            else if (!this.isAggressive() && this.isFighting()) {
                this.getBrain().setSchedule(SchedulesAPI.DEFAULT_SLEEP);
                this.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
                this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                this.getBrain().eraseMemory(MemoryModulesAPI.DEFENDING_HOME);
                this.setIntimidating(false);
                this.setFighting(false);
            }

        }
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

        super.aiStep();
    }

    private void setupAnimationStates() {
        if(isSleeping()) {
            this.sleepingAnimationState.startIfStopped(this.tickCount);
        }
        else {
            this.sleepingAnimationState.stop();
        }
        if(this.isInWater()) {
            this.swimmingAnimationState.startIfStopped(this.tickCount);

            if(idleAnimationTimeout > 0) {
                idleAnimationTimeout--;
            }

            if(this.idleAnimationTimeout <= 0) {
                this.swimIdleAnimationState.start(this.tickCount);
                this.idleAnimationTimeout = this.random.nextInt(400) + 800;
            }
        }
        else {
            this.swimmingAnimationState.stop();
            swimIdleAnimationState.stop();
        }
        if(isIntimidating()) {
            this.intimidateAnimationState.startIfStopped(this.tickCount);
        }
        else {
            this.intimidateAnimationState.stop();
        }
        if(!this.onGround()) {
            this.flapAnimationState.startIfStopped(this.tickCount);
        }
        else {
            this.flapAnimationState.stop();
        }
    }

    private void wingFlap() {
        Vec3 velocity = this.getDeltaMovement();
        if (!this.onGround() && velocity.y < 0.0) {
            this.setDeltaMovement(velocity.multiply(1.0, 0.6, 1.0));
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == EntityEvent.START_ATTACKING && !this.isIntimidating()) {
            this.eatAnimationState.start(this.tickCount);
        }
        else {
            super.handleEntityEvent(status);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
        return super.doHurtTarget(target);
    }

    public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getEntity)
                .filter(attacker -> attacker instanceof LivingEntity)
                .map(livingAttacker -> (LivingEntity)livingAttacker);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if(this.isInWater() && target.blockPosition().getY() < this.blockPosition().getY()) {
            return false;
        }
        return !this.isBaby() && super.canAttack(target);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return getTargetFromBrain();
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
    }

    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return SwanBrain.create(this, dynamic);
    }

    public Brain<SwanEntity> getBrain() {
        return (Brain<SwanEntity>)super.getBrain();
    }

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

    public boolean isFighting() {
        return entityData.get(FIGHTING);
    }

    public void setFighting(boolean isFighting) {
        entityData.set(FIGHTING, isFighting);
    }

    public boolean isSleeping() {
        return entityData.get(SLEEPING);
    }

    public void setSleeping(boolean isSleeping) {
        entityData.set(SLEEPING, isSleeping);
    }

    public boolean isIntimidating() {
        return entityData.get(INTIMIDATING);
    }

    public void setIntimidating(boolean isIntimidating) {
        entityData.set(INTIMIDATING, isIntimidating);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(OfBeastsAndWildThings.MOD_ID, "swan_food")));
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.getItem() instanceof BucketItem) {
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.WATER_BUCKET)));
        } else {
            super.usePlayerItem(player, hand, stack);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        SwanEntity child = EntitiesWT.SWAN.create(world);

        if(child != null) {
            int i = this.random.nextInt(2);

            SwanEntityVariant variant = i == 0 ? this.getVariant() : ((SwanEntity)entity).getVariant();

            Optional<GlobalPos> optional = this.getBrain().getMemoryInternal(MemoryModuleType.HOME);

            if(optional != null && optional.isPresent()) {
                child.getBrain().setMemory(MemoryModuleType.HOME, optional.get());
            }

            child.setVariant(variant);
            return child;
        }

        return null;
    }

    @Override
    public boolean canStandOnFluid(FluidState state) {
        return state.is(FluidTags.WATER);
    }

    private void updateFloating() {
        if (this.isInWater()) {
            CollisionContext shapeContext = CollisionContext.of(this);
            if (!shapeContext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) || this.level().getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
                this.setDeltaMovement(this.getDeltaMovement().x(),0.1F,this.getDeltaMovement().z());
            } else {
                this.setOnGround(true);
            }
        }
    }

    public static boolean isValidSwanFood(LivingEntity entity) {
        return entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(OfBeastsAndWildThings.MOD_ID, "swan_food")));
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                 @Nullable SpawnGroupData entityData) {
        SwanEntityVariant variant = Util.getRandom(SwanEntityVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    public SwanEntityVariant getVariant() {
        return SwanEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(SwanEntityVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    protected SoundEvent getAmbientSound() {
        return SoundsWT.SWAN_IDLE;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundsWT.SWAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundsWT.SWAN_DEATH;
    }
    protected SoundEvent getSwimSound() {
        return SoundEvents.TURTLE_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.makeSound(SoundsWT.SWAN_STEP);
    }

    @Override
    public boolean isUnderWater() {
        return super.isUnderWater();
    }
}
