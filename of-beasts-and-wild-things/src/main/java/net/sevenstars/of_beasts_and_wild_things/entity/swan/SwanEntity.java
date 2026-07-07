package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
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

public class SwanEntity extends AnimalEntity {
    private static final int EGG_COOLDOWN = 12000; // = 10 minutes
    public int idleAnimationTimeout = this.random.nextInt(400) + 800; // 40 - 60 Seconds
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(SwanEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(SwanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> INTIMIDATING = DataTracker.registerData(SwanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> FIGHTING = DataTracker.registerData(SwanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState intimidateAnimationState = new AnimationState();
    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState swimIdleAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();
    public SwanEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0F);
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
        builder.add(INTIMIDATING, false);
        builder.add(FIGHTING, false);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("swanBrain");
        this.getBrain().tick(world, this);
        profiler.swap("swanActivityUpdate");
        SwanBrain.updateActivities(this);
        profiler.pop();

        this.updateHome();
        this.updateFloating();
        super.mobTick(world);
    }

    @Override
    protected float getBaseWaterMovementSpeedMultiplier() {
        return 1.0F;
    }

    private void updateHome() {
        Optional<GlobalPos> optional = this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HOME);

        if(optional.isPresent()) {
            if(!this.getWorld().getBlockState(optional.get().pos()).isOf(BlocksWT.BIRD_NEST)) {
                this.getBrain().forget(MemoryModuleType.HOME);
            }
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new AmphibiousSwimNavigation(this, world);
    }

    public void startSleeping() {
        if (this.hasVehicle()) {
            this.stopRiding();
        }

        Optional<Integer> cooldown = this.getBrain().getOptionalRegisteredMemory(MemoryModulesWT.EGG_COOLDOWN);
        if(cooldown.isEmpty()) {
            Optional<GlobalPos> optional = this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HOME);

            if(optional.isPresent()) {
                double rand = this.random.nextDouble();

                if(rand < 0.15) {
                    BlockPos pos = optional.get().pos();
                    BlockState homeBlock = this.getWorld().getBlockState(optional.get().pos());
                    if(homeBlock.isOf(BlocksWT.BIRD_NEST) && homeBlock.get(BirdNest.NEST_LEVEL) < 2) {
                        this.getWorld().setBlockState(pos, homeBlock.with(BirdNest.NEST_LEVEL, homeBlock.get(BirdNest.NEST_LEVEL) + 1));
                    }
                }
                this.getBrain().remember(MemoryModulesWT.EGG_COOLDOWN, EGG_COOLDOWN);
            }
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
            this.wingFlap();

            this.setAttacking(this.getTarget() != null);

            if(this.isBaby()) {
                this.getBrain().setSchedule(SchedulesAPI.DEFAULT_BABY);
            }
            else if(this.isAttacking() && !this.isFighting()) {
                this.getBrain().setSchedule(Schedule.EMPTY);
                this.setIntimidating(getTarget() instanceof PlayerEntity);
                this.setFighting(true);
            }
            else if (!this.isAttacking() && this.isFighting()) {
                this.getBrain().setSchedule(SchedulesAPI.DEFAULT_SLEEP);
                this.getBrain().forget(MemoryModuleType.LOOK_TARGET);
                this.getBrain().forget(MemoryModuleType.WALK_TARGET);
                this.getBrain().forget(MemoryModulesAPI.DEFENDING_HOME);
                this.setIntimidating(false);
                this.setFighting(false);
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

            if(idleAnimationTimeout > 0) {
                idleAnimationTimeout--;
            }

            if(this.idleAnimationTimeout <= 0) {
                this.swimIdleAnimationState.start(this.age);
                this.idleAnimationTimeout = this.random.nextInt(400) + 800;
            }
        }
        else {
            this.swimmingAnimationState.stop();
            swimIdleAnimationState.stop();
        }
        if(isIntimidating()) {
            this.intimidateAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.intimidateAnimationState.stop();
        }
        if(!this.isOnGround()) {
            this.flapAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.flapAnimationState.stop();
        }
    }

    private void wingFlap() {
        Vec3d velocity = this.getVelocity();
        if (!this.isOnGround() && velocity.y < 0.0) {
            this.setVelocity(velocity.multiply(1.0, 0.6, 1.0));
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_ATTACK_SOUND && !this.isIntimidating()) {
            this.eatAnimationState.start(this.age);
        }
        else {
            super.handleStatus(status);
        }
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
        return super.tryAttack(world, target);
    }

    public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getAttacker)
                .filter(attacker -> attacker instanceof LivingEntity)
                .map(livingAttacker -> (LivingEntity)livingAttacker);
    }

    @Override
    public boolean canBreatheInWater() {
        return super.canBreatheInWater();
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if(this.isTouchingWater() && target.getBlockPos().getY() < this.getBlockPos().getY()) {
            return false;
        }
        return !this.isBaby() && super.canTarget(target);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return getTargetInBrain();
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        this.getBrain().remember(MemoryModuleType.ATTACK_TARGET, target);
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

    public boolean isFighting() {
        return dataTracker.get(FIGHTING);
    }

    public void setFighting(boolean isFighting) {
        dataTracker.set(FIGHTING, isFighting);
    }

    public boolean isSleeping() {
        return dataTracker.get(SLEEPING);
    }

    public void setSleeping(boolean isSleeping) {
        dataTracker.set(SLEEPING, isSleeping);
    }

    public boolean isIntimidating() {
        return dataTracker.get(INTIMIDATING);
    }

    public void setIntimidating(boolean isIntimidating) {
        dataTracker.set(INTIMIDATING, isIntimidating);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(OfBeastsAndWildThings.MOD_ID, "swan_food")));
    }

    @Override
    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (stack.getItem() instanceof BucketItem) {
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.WATER_BUCKET)));
        } else {
            super.eat(player, hand, stack);
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        SwanEntity child = EntitiesWT.SWAN.create(world, SpawnReason.BREEDING);

        if(child != null) {
            int i = this.random.nextInt(2);

            SwanEntityVariant variant = i == 0 ? this.getVariant() : ((SwanEntity)entity).getVariant();

            Optional<GlobalPos> optional = this.getBrain().getOptionalMemory(MemoryModuleType.HOME);

            if(optional != null && optional.isPresent()) {
                child.getBrain().remember(MemoryModuleType.HOME, optional.get());
            }

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
                this.setVelocity(this.getVelocity().getX(),0.1F,this.getVelocity().getZ());
            } else {
                this.setOnGround(true);
            }
        }
    }

    public static boolean isValidSwanFood(LivingEntity entity) {
        return entity.getType().isIn(TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(OfBeastsAndWildThings.MOD_ID, "swan_food")));
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
        return SoundsWT.SWAN_IDLE;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundsWT.SWAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundsWT.SWAN_DEATH;
    }
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundsWT.SWAN_STEP);
    }

    @Override
    public boolean isSubmergedInWater() {
        return super.isSubmergedInWater();
    }
}
