package net.sevenstars.middleearth.entity.spider.spawn;

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
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
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
import net.sevenstars.middleearth.entity.goals.PounceRetreatGoal;
import net.sevenstars.middleearth.entity.goals.ShieldAgainstProjectileGoal;
import net.sevenstars.middleearth.entity.goals.SmartProjectileAttackGoal;
import net.sevenstars.middleearth.entity.goals.SpiderPonceAtTargetGoal;
import net.sevenstars.middleearth.entity.goals.interfaces.CooldownRangedAttackMob;
import net.sevenstars.middleearth.entity.goals.interfaces.Shielder;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.projectile.WebbedEntity;
import net.sevenstars.middleearth.entity.spider.Pouncer;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.entity.spider.SpiderVariantSelector;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

public class SpawnOfShelobEntity extends Monster implements Pouncer, Shielder, CooldownRangedAttackMob {
    public static final int CLIMBING_MAX_TICKS = 40;
    public static final int PASSIVE_HEALING_COOLDOWN = 80;
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int LEAPING_TIME_TRANSITION = 9;
    public static final float MOVEMENT_SPEED = 1.15f;
    public static final float WEB_PROJECTILE_DAMAGE = 2f;

    private static final EntityDataAccessor<Byte> SPIDER_FLAGS;
    private static final EntityDataAccessor<Integer> BITE_FLAG;
    private static final EntityDataAccessor<Integer> POUNCE_FLAG;
    private static final EntityDataAccessor<Integer> BLOCK_FLAG;
    private static final EntityDataAccessor<Holder<SpiderVariant>> VARIANT;

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState biteAnimation = new AnimationState();
    public final AnimationState pounceAnimation = new AnimationState();
    public final AnimationState blockAnimation = new AnimationState();

    private int passiveHealingCooldown = 0;
    private int climbingTicks = 0;
    private int timelineTicks = 0;
    private int leapingTicks = 0;
    private int shootCooldown = 0;
    private int biteAnimationCooldown = 0;

    public SpawnOfShelobEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 36.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 7)
                .add(Attributes.ARMOR, 3)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PounceRetreatGoal(this, 0.8f, 1.15f, 0.3f));
        this.goalSelector.addGoal(3, new ShieldAgainstProjectileGoal(this, this, 13, 32));
        this.goalSelector.addGoal(4, new SmartProjectileAttackGoal(this, 0.75f, 40, 90, 17, 40));
        this.goalSelector.addGoal(5, new SpiderPonceAtTargetGoal(this, this,
                0.5F, 0.25f, 4, 17, 4));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, NpcEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, CaveTrollEntity.class, true));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        if (entityData instanceof ShelobiteScuttlerEntity.SpiderData spiderData) {
            this.setVariant(spiderData.variant);
        } else {
            Holder<SpiderVariant> variant = SpiderVariantSelector.select(world, this.blockPosition());
            this.setVariant(variant);
            entityData = new ShelobiteScuttlerEntity.SpiderData(variant);
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
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
        builder.define(SPIDER_FLAGS, (byte)0);
        builder.define(BITE_FLAG, 0);
        builder.define(POUNCE_FLAG, 0);
        builder.define(BLOCK_FLAG, 0);
        Holder<SpiderVariant> spiderVariantRegistryEntry =
                VariantHolderUtils.getDefaultOrAny(this.registryAccess(), SpiderVariantRegistry.DEFAULT);
        builder.define(VARIANT, spiderVariantRegistryEntry);
    }

    protected void setupAnimationStates() {
        if(!this.idleAnimation.isStarted()) {
            this.idleAnimation.start(this.tickCount);
        }
        setTrackerState(POUNCE_FLAG, pounceAnimation);
        setTrackerState(BLOCK_FLAG, blockAnimation);
        setTrackerState(BITE_FLAG, biteAnimation);
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

    public void startPounceAnimation() {
        this.entityData.set(POUNCE_FLAG, 1);
    }
    public void stopPounceAnimation() {
        this.entityData.set(POUNCE_FLAG, -1);
    }


    @Override
    public void blockShield() {
        this.entityData.set(BLOCK_FLAG, 1);
    }
    @Override
    public void unblockShield() {
        this.entityData.set(BLOCK_FLAG, -1);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean result = super.doHurtTarget(target);
        this.entityData.set(BITE_FLAG, 1);
        if(biteAnimationCooldown == 0) biteAnimationCooldown = 40;
        if (target instanceof LivingEntity) {
            int i = 0;
            if (this.level().getDifficulty() == Difficulty.NORMAL) {
                i = 7;
            } else if (this.level().getDifficulty() == Difficulty.HARD) {
                i = 15;
            }

            if (i > 0) {
                ((LivingEntity)target).addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
            }
        }
        return result;
    }

    @Override
    public void resetFallDistance() {
        if (this.level() instanceof ServerLevel serverWorld) {
            if (this.onGround() && this.fallDistance > 1.5) {
                Vec3 vec3d = position().add(0.0, 0.5, 0.0);
                BlockState blockState = this.getBlockStateOn();
                int count = (int) Mth.clamp(25.0 * this.fallDistance - 1, 0.0, 150.0);
                serverWorld.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3d.x, vec3d.y, vec3d.z,
                        count, this.random.nextDouble() - 0.5, 0.15, this.random.nextDouble() - 0.5, 0.2f);
            }
        }
        super.resetFallDistance();
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbingWall(this.horizontalCollision);

            if(biteAnimationCooldown <= 1) {
                this.entityData.set(BITE_FLAG, -1);
            }
            biteAnimationCooldown = Math.max(biteAnimationCooldown - 1, 0);

            this.shootCooldown = Math.max(0, this.shootCooldown - 1);
            if(!this.hasEffect(MobEffects.REGENERATION)) {
                passiveHealingCooldown = Math.max(0, passiveHealingCooldown - 1);
                if(passiveHealingCooldown == 0 && this.getHealth() < this.getMaxHealth()) {
                    this.heal(1);
                    passiveHealingCooldown = PASSIVE_HEALING_COOLDOWN;
                }
            }
        } else {
            setupAnimationStates();
        }

    }

    @Override
    public void aiStep() {
        super.aiStep();
        if(isClimbingWall()) {
            timelineTicks++;
            this.climbingTicks = Math.min( this.climbingTicks + 1, CLIMBING_MAX_TICKS);
        } else {
            int amount = 1;
            if(this.climbingTicks > CLIMBING_MAX_TICKS / 3) amount = 4;
            this.climbingTicks = Math.max(0, this.climbingTicks - amount);
        }

        if(onGround()) {
            leapingTicks = 0;
        } else {
            leapingTicks++;
        }
    }

    @Override
    protected int getBaseExperienceReward() {
        return 13 + this.random.nextInt(4);
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

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        double dX = target.getX() - this.getX();
        double e = target.getEyeY() - 1.1F;
        double dZ = target.getZ() - this.getZ();
        double g = Math.sqrt(dX * dX + dZ * dZ) * 0.2F;
        if (this.level() instanceof ServerLevel serverWorld) {
            WebbedEntity projectile = new WebbedEntity(serverWorld, this, WEB_PROJECTILE_DAMAGE * pullProgress);
            projectile.shoot(dX, e + g - projectile.getY(), dZ, 1.6F,
                    8 - this.level().getDifficulty().getId() * 4);
            serverWorld.addFreshEntity(projectile);
        }

        this.playSound(SoundEvents.BREEZE_SHOOT, 1.0F, 0.7F + (this.getRandom().nextFloat() * 0.6F));
    }

    @Override
    public int getRangeAttackCooldown() {
        return this.shootCooldown;
    }

    @Override
    public void setRangeAttackCooldown(int cooldown) {
        this.shootCooldown = cooldown;
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

    public int getTimelineTicks() {
        return this.timelineTicks;
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
        SPIDER_FLAGS = SynchedEntityData.defineId(SpawnOfShelobEntity.class, EntityDataSerializers.BYTE);
        BITE_FLAG = SynchedEntityData.defineId(SpawnOfShelobEntity.class, EntityDataSerializers.INT);
        POUNCE_FLAG = SynchedEntityData.defineId(SpawnOfShelobEntity.class, EntityDataSerializers.INT);
        BLOCK_FLAG = SynchedEntityData.defineId(SpawnOfShelobEntity.class, EntityDataSerializers.INT);
        VARIANT = SynchedEntityData.defineId(SpawnOfShelobEntity.class, TrackedDataHandlerRegistryME.SPIDER_VARIANT);
    }

    public static boolean canSpawn(EntityType<SpawnOfShelobEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        if(spawnReason != MobSpawnType.NATURAL)
            return SpawnOfShelobEntity.checkMonsterSpawnRules(type, serverWorldAccess, spawnReason, blockPos, random);
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor world, MobSpawnType spawnReason) {
        return true;
    }
}
