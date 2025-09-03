package net.sevenstars.middleearth.entity.spider.scuttler;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.entity.goals.SpiderPonceAtTargetGoal;
import net.sevenstars.middleearth.entity.spider.Pouncer;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.entity.spider.SpiderVariants;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ShelobiteScuttlerEntity extends HostileEntity implements Pouncer {
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int LEAPING_TIME_TRANSITION = 8;
    public static final float MOVEMENT_SPEED = 1.15f;
    private static final TrackedData<Byte> SPIDER_FLAGS;
    private static final TrackedData<Integer> BITE_FLAG;
    private static final TrackedData<Integer> POUNCE_FLAG;
    private static final TrackedData<RegistryEntry<SpiderVariant>> VARIANT;

    // region Brain
    protected static final ImmutableList<SensorType<? extends Sensor<? super ShelobiteScuttlerEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            MemoryModuleType.HOME,
            MemoryModuleType.LONG_JUMP_COOLING_DOWN,
            MemoryModuleType.LONG_JUMP_MID_JUMP
    );
    // endregion

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState biteAnimation = new AnimationState();
    public final AnimationState pounceAnimation = new AnimationState();

    private int climbingTicks = 0;
    private int leapingTicks = 0;
    private int biteAnimationCooldown = 0;

    public ShelobiteScuttlerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 16.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.34)
                .add(EntityAttributes.ATTACK_DAMAGE, 4)
                .add(EntityAttributes.FOLLOW_RANGE, 36.0);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (entityData instanceof SpiderData spiderData) {
            this.setVariant(spiderData.variant);
        } else {
            Optional<? extends RegistryEntry<SpiderVariant>> optional = Variants.select(SpawnContext.of(world, this.getBlockPos()), SpiderVariants.KEY);
            if (optional.isPresent()) {
                this.setVariant(optional.get());
                entityData = new SpiderData(optional.get());
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected Brain.Profile<ShelobiteScuttlerEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULE_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return ShelobiteScuttlerBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new SpiderPonceAtTargetGoal(this, this,
                0.45F, 0.25f, 4, 14, 17));
        this.goalSelector.add(4, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public double getMountedHeightOffset() {
        return (double)(this.getHeight() * 0.5F);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        RegistryEntry<SpiderVariant> spiderVariantRegistryEntry = Variants.getOrDefaultOrThrow(this.getRegistryManager(), SpiderVariants.DEFAULT);
        builder.add(VARIANT, spiderVariantRegistryEntry);
        builder.add(SPIDER_FLAGS, (byte)0);
        builder.add(BITE_FLAG, 0);
        builder.add(POUNCE_FLAG, 0);
    }

    //@Override
    //public Brain<MirkwoodSpiderEntity> getBrain() {
    //    return (Brain<MirkwoodSpiderEntity>) super.getBrain();
    //}

    protected void setupAnimationStates() {
        if (!this.idleAnimation.isRunning()) {
            this.idleAnimation.start(this.age);
        }
        if (!this.walkingAnimation.isRunning()) {
            this.walkingAnimation.start(this.age);
        }

        setTrackerState(BITE_FLAG, biteAnimation);
        setTrackerState(POUNCE_FLAG, pounceAnimation);
    }

    protected void setTrackerState(TrackedData<Integer> trackedData, AnimationState animationState) {
        int state = this.dataTracker.get(trackedData);
        if(state == 1) {
            animationState.start(this.age);
        } else if (state == -1) {
            animationState.stop();
        }
        this.dataTracker.set(trackedData, 0);
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        this.dataTracker.set(BITE_FLAG, 1);
        if(biteAnimationCooldown == 0) biteAnimationCooldown = 40;
        return super.tryAttack(world, target);
    }

    public void startPounceAnimation() {
        this.dataTracker.set(POUNCE_FLAG, 1);
    }
    public void stopPounceAnimation() {
        this.dataTracker.set(POUNCE_FLAG, -1);
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if(biteAnimationCooldown <= 1) {
                this.dataTracker.set(BITE_FLAG, -1);
            }
            biteAnimationCooldown = Math.max(biteAnimationCooldown - 1, 0);
            this.setClimbingWall(this.horizontalCollision);
        } else {
            setupAnimationStates();
        }

    }

    @Override
    protected void mobTick(ServerWorld world) {
        super.mobTick(world);
        //Profiler profiler = Profilers.get();
        //profiler.push("shelobiteScuttlerBrain");
        //this.getBrain().tick(world, this);
        //profiler.pop();
        //ShelobiteScuttlerBrain.updateActivities(this);
        //ShelobiteScuttlerBrain.playSoundRandomly(this);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if(isClimbingWall()) {
            this.climbingTicks = this.climbingTicks + 1;
        } else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }

        if(isOnGround()) {
            leapingTicks = 0;
        } else {
            leapingTicks++;
        }
    }

    public SpiderVariant getVariant() {
        return getRegistryVariant().value();
    }

    private RegistryEntry<SpiderVariant> getRegistryVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(RegistryEntry<SpiderVariant> variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public void slowMovement(BlockState state, Vec3d multiplier) {
        if (!state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "cobwebs")))) {
            super.slowMovement(state, multiplier);
        }
    }

    // Immune to Poison
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return effect.getEffectType() != StatusEffects.POISON && super.canHaveStatusEffect(effect);
    }

    public boolean isClimbingWall() {
        return (this.dataTracker.get(SPIDER_FLAGS) & 1) != 0;
    }

    public boolean isCollidingWall() {
        return this.horizontalCollision;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = (Byte)this.dataTracker.get(SPIDER_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.dataTracker.set(SPIDER_FLAGS, b);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    public int getLeapingTicks() {
        return this.leapingTicks;
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        Variants.writeVariantToNbt(view, this.getRegistryVariant());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        Variants.readVariantFromNbt(view, SpiderVariants.KEY).ifPresent(this::setVariant);
    }

    static {
        SPIDER_FLAGS = DataTracker.registerData(ShelobiteScuttlerEntity.class, TrackedDataHandlerRegistry.BYTE);
        BITE_FLAG = DataTracker.registerData(ShelobiteScuttlerEntity.class, TrackedDataHandlerRegistry.INTEGER);
        POUNCE_FLAG = DataTracker.registerData(ShelobiteScuttlerEntity.class, TrackedDataHandlerRegistry.INTEGER);
        VARIANT = DataTracker.registerData(ShelobiteScuttlerEntity.class, ModTrackedDataHandlerRegistry.SPIDER_VARIANT);
    }

    public static class SpiderData implements EntityData {
        public final RegistryEntry<SpiderVariant> variant;

        public SpiderData(RegistryEntry<SpiderVariant> variant) {
            this.variant = variant;
        }
    }
}
