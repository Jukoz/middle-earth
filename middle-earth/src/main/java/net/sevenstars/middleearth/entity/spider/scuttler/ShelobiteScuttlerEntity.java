package net.sevenstars.middleearth.entity.spider.scuttler;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.VariantHolderUtils;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.entity.goals.SpiderPonceAtTargetGoal;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.spider.Pouncer;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.entity.spider.SpiderVariantSelector;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

public class ShelobiteScuttlerEntity extends Monster implements Pouncer {
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int LEAPING_TIME_TRANSITION = 8;
    public static final float MOVEMENT_SPEED = 1.15f;
    private static final EntityDataAccessor<Byte> SPIDER_FLAGS;
    private static final EntityDataAccessor<Integer> BITE_FLAG;
    private static final EntityDataAccessor<Integer> POUNCE_FLAG;
    private static final EntityDataAccessor<Holder<SpiderVariant>> VARIANT;

    // region Brain
    protected static final ImmutableList<SensorType<? extends Sensor<? super ShelobiteScuttlerEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
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
            MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS,
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

    public ShelobiteScuttlerEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.34)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 36.0);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        if (entityData instanceof SpiderData spiderData) {
            this.setVariant(spiderData.variant);
        } else {
            Holder<SpiderVariant> variant = SpiderVariantSelector.select(world, this.blockPosition());
            this.setVariant(variant);
            entityData = new SpiderData(variant);
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public boolean isPersistenceRequired() {
        return false;
    }

    public static boolean canSpawn(EntityType<NpcEntity> type, LevelAccessor worldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        BlockPos below = pos.below();
        boolean isOnSolidGround = worldAccess.getBlockState(below).isRedstoneConductor(worldAccess, below);
        boolean isNotOnTopOfLogs = !worldAccess.getBlockState(below).is(BlockTags.LOGS);

        return isOnSolidGround && isNotOnTopOfLogs;
    }

    @Override
    protected Brain.Provider<ShelobiteScuttlerEntity> brainProvider() {
        return Brain.provider(MEMORY_MODULE_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return ShelobiteScuttlerBrain.create(this, this.brainProvider().makeBrain(dynamic));
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new SpiderPonceAtTargetGoal(this, this,
                0.45F, 0.25f, 4, 14, 17));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, NpcEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, CaveTrollEntity.class, true));
    }

    public double getMountedHeightOffset() {
        return (double)(this.getBbHeight() * 0.5F);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        Holder<SpiderVariant> spiderVariantRegistryEntry =
                VariantHolderUtils.getDefaultOrAny(this.registryAccess(), SpiderVariantRegistry.DEFAULT);
        builder.define(VARIANT, spiderVariantRegistryEntry);
        builder.define(SPIDER_FLAGS, (byte)0);
        builder.define(BITE_FLAG, 0);
        builder.define(POUNCE_FLAG, 0);
    }

    //@Override
    //public Brain<MirkwoodSpiderEntity> getBrain() {
    //    return (Brain<MirkwoodSpiderEntity>) super.getBrain();
    //}

    protected void setupAnimationStates() {
        if (!this.idleAnimation.isStarted()) {
            this.idleAnimation.start(this.tickCount);
        }
        if (!this.walkingAnimation.isStarted()) {
            this.walkingAnimation.start(this.tickCount);
        }

        setTrackerState(BITE_FLAG, biteAnimation);
        setTrackerState(POUNCE_FLAG, pounceAnimation);
    }

    protected void setTrackerState(EntityDataAccessor<Integer> trackedData, AnimationState animationState) {
        int state = this.entityData.get(trackedData);
        if(state == 1) {
            animationState.start(this.tickCount);
        } else if (state == -1) {
            animationState.stop();
        }
        this.entityData.set(trackedData, 0);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        this.entityData.set(BITE_FLAG, 1);
        if(biteAnimationCooldown == 0) biteAnimationCooldown = 40;
        return super.doHurtTarget(target);
    }

    @Override
    public void resetFallDistance() {
        if (this.level() instanceof ServerLevel serverWorld) {
            if (this.onGround() && this.fallDistance > 1.5) {
                Vec3 vec3d = position().add(0.0, 0.5, 0.0);
                BlockState blockState = this.getBlockStateOn();
                int count = (int) Mth.clamp(20.0 * this.fallDistance - 1, 0.0, 120.0);
                serverWorld.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3d.x, vec3d.y, vec3d.z,
                        count, this.random.nextDouble() - 0.5, 0.15, this.random.nextDouble() - 0.5, 0.16f);
            }
        }
        super.resetFallDistance();
    }

    public void startPounceAnimation() {
        this.entityData.set(POUNCE_FLAG, 1);
    }
    public void stopPounceAnimation() {
        this.entityData.set(POUNCE_FLAG, -1);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if(biteAnimationCooldown <= 1) {
                this.entityData.set(BITE_FLAG, -1);
            }
            biteAnimationCooldown = Math.max(biteAnimationCooldown - 1, 0);
            this.setClimbingWall(this.horizontalCollision);
        } else {
            setupAnimationStates();
        }

    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        //Profiler profiler = Profilers.get();
        //profiler.push("shelobiteScuttlerBrain");
        //this.getBrain().tick(world, this);
        //profiler.pop();
        //ShelobiteScuttlerBrain.updateActivities(this);
        //ShelobiteScuttlerBrain.playSoundRandomly(this);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if(isClimbingWall()) {
            this.climbingTicks = this.climbingTicks + 1;
        } else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }

        if(onGround()) {
            leapingTicks = 0;
        } else {
            leapingTicks++;
        }
    }

    @Override
    protected int getBaseExperienceReward() {
        return 6 + this.random.nextInt(3);
    }

    public SpiderVariant getVariant() {
        return getRegistryVariant().value();
    }

    private Holder<SpiderVariant> getRegistryVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(Holder<SpiderVariant> variant) {
        this.entityData.set(VARIANT, variant);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SPIDER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    public boolean onClimbable() {
        return this.isClimbingWall();
    }

    public void makeStuckInBlock(BlockState state, Vec3 multiplier) {
        if (!state.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "cobwebs")))) {
            super.makeStuckInBlock(state, multiplier);
        }
    }

    // Immune to Poison
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.POISON && super.canBeAffected(effect);
    }

    public boolean isClimbingWall() {
        return (this.entityData.get(SPIDER_FLAGS) & 1) != 0;
    }

    public boolean isCollidingWall() {
        return this.horizontalCollision;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = (Byte)this.entityData.get(SPIDER_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.entityData.set(SPIDER_FLAGS, b);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    public int getLeapingTicks() {
        return this.leapingTicks;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        VariantHolderUtils.writeVariant(view, this.getRegistryVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        VariantHolderUtils.readVariant(view, this.registryAccess(), DynamicRegistriesME.SPIDER_VARIANTS)
                .ifPresent(this::setVariant);
    }

    static {
        SPIDER_FLAGS = SynchedEntityData.defineId(ShelobiteScuttlerEntity.class, EntityDataSerializers.BYTE);
        BITE_FLAG = SynchedEntityData.defineId(ShelobiteScuttlerEntity.class, EntityDataSerializers.INT);
        POUNCE_FLAG = SynchedEntityData.defineId(ShelobiteScuttlerEntity.class, EntityDataSerializers.INT);
        VARIANT = SynchedEntityData.defineId(ShelobiteScuttlerEntity.class, TrackedDataHandlerRegistryME.SPIDER_VARIANT);
    }

    public static boolean canSpawn(EntityType<ShelobiteScuttlerEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        if(spawnReason != MobSpawnType.NATURAL)
            return ShelobiteScuttlerEntity.checkMonsterSpawnRules(type, serverWorldAccess, spawnReason, blockPos, random);
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    public static class SpiderData implements SpawnGroupData {
        public final Holder<SpiderVariant> variant;

        public SpiderData(Holder<SpiderVariant> variant) {
            this.variant = variant;
        }
    }
}
