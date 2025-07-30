package net.sevenstars.middleearth.entity.spider;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.mob.PiglinBruteBrain;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.sevenstars.middleearth.entity.goals.FastPonceAtTargetGoal;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MirkwoodSpiderEntity extends HostileEntity {
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int ADULT_AGE = 20 * 60 * 2; // 2 min of baby time
    public static final float MOVEMENT_SPEED = 1.2f;
    private static final TrackedData<Byte> SPIDER_FLAGS;

    protected static final ImmutableList<SensorType<? extends Sensor<? super MirkwoodSpiderEntity>>> SENSOR_TYPES = ImmutableList.of(
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

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState biteAnimation = new AnimationState();
    public final AnimationState pounceAnimation = new AnimationState();

    private int idleAnimationCooldown = 0;
    private int climbingTicks = 0;

    public MirkwoodSpiderEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 16.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.35)
                .add(EntityAttributes.FOLLOW_RANGE, 36.0);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        ShelobiteScuttlerBrain.setCurrentPosAsHome(this);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected Brain.Profile<MirkwoodSpiderEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULE_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return ShelobiteScuttlerBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new FastPonceAtTargetGoal(this, 0.3F, 0.4f));
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

    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SPIDER_FLAGS, (byte)0);
    }

    //@Override
    //public Brain<MirkwoodSpiderEntity> getBrain() {
    //    return (Brain<MirkwoodSpiderEntity>) super.getBrain();
    //}

    protected void setupAnimationStates() {
        if (!this.idleAnimation.isRunning()) {
            //this.idleAnimationCooldown = this.random.nextInt(40) + 80;
            this.idleAnimation.start(this.age);
        } else {
            this.idleAnimationCooldown--;
        }
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        biteAnimation.start(this.age);
        return super.tryAttack(world, target);
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
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
            this.climbingTicks = Math.min(CLIMBING_TIME_TRANSITION, this.climbingTicks + 1);
        } else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }
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
        if (!state.isOf(Blocks.COBWEB) && !state.isOf(ModNatureBlocks.CORNER_COBWEB) && !state.isOf(ModNatureBlocks.HANGING_COBWEB)) {
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

    public MirkwoodSpiderVariants getVariant() {
        return MirkwoodSpiderVariants.byId(this.getId());
    }

    static {
        SPIDER_FLAGS = DataTracker.registerData(MirkwoodSpiderEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}
