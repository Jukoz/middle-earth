package net.sevenstars.middleearth.entity.wight;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.utils.SpawnUtil;

public class BarrowWightEntity extends HostileEntity {
    private static final TrackedData<Integer> ATTACK_FLAG;

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
    public final AnimationState screamAnimation = new AnimationState();
    public final AnimationState incantationAnimation = new AnimationState();

    private int attackAnimationCooldown = 0;

    public BarrowWightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 30.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.23)
                .add(EntityAttributes.FOLLOW_RANGE, 36.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1 , false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, NpcEntity.class, true));
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ATTACK_FLAG, 0);
    }

    protected void setupAnimationStates() {
        if (!this.idleAnimation.isRunning()) {
            this.idleAnimation.start(this.age);
        }
        if (!this.walkingAnimation.isRunning()) {
            this.walkingAnimation.start(this.age);
        }

        setTrackerState(ATTACK_FLAG, attackAnimation);
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
        boolean result = super.tryAttack(world, target);
        this.dataTracker.set(ATTACK_FLAG, 1);
        if(attackAnimationCooldown == 0) attackAnimationCooldown = 20;
        return result;
    }

    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            setupAnimationStates();
        } else {
            if (attackAnimationCooldown <= 1) {
                this.dataTracker.set(ATTACK_FLAG, -1);
            }
            attackAnimationCooldown = Math.max(attackAnimationCooldown - 1, 0);
        }
    }

    @Override
    protected int getExperienceToDrop(ServerWorld world) {
        return 10 + this.random.nextInt(5);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BREEZE_IDLE_GROUND;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BREEZE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BREEZE_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_STRAY_STEP, 0.17F, 0.8F);
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return !effect.equals(StatusEffects.WITHER) && !effect.equals(StatusEffects.POISON) && super.canHaveStatusEffect(effect);
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
    }

    static {
        ATTACK_FLAG = DataTracker.registerData(BarrowWightEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public static boolean canSpawn(EntityType<BarrowWightEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        if(spawnReason != SpawnReason.NATURAL)
            return BarrowWightEntity.canSpawnInDark(type, serverWorldAccess, spawnReason, blockPos, random);
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }
}
