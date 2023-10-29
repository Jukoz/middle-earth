package net.jukoz.me.entity.snail;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.crab.CrabVariant;
import net.jukoz.me.entity.goals.EatCropsGoal;
import net.minecraft.client.sound.Sound;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SnailEntity extends AnimalEntity {

    public static final int CLIMBING_TIME_TRANSITION = 12;
    private static final TrackedData<Byte> SPIDER_FLAGS;
    private int climbingTicks = 0;
    private int eatCropsTimer;
    private EatCropsGoal eatCropsGoal;

    public SnailEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.eatCropsGoal = new EatCropsGoal(this);
        this.goalSelector.add(0, eatCropsGoal);
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 0.8));
    }

    public static DefaultAttributeContainer.Builder createSnailAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f);
    }

    public SnailVariant getVariant() {
        return SnailVariant.byId(this.getId());
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SNAIL.create(world);
    }

    @Override
    protected void mobTick() {
        this.eatCropsTimer = this.eatCropsGoal.getTimer();
        super.mobTick();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    protected void jump() {
        // Snail is not able to jump
    }

    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SPIDER_FLAGS, (byte)0);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.SET_SHEEP_EAT_GRASS_TIMER_OR_PRIME_TNT_MINECART) {
            this.eatCropsTimer = 40;
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public void onEatingGrass() {
        super.onEatingGrass();
        if (this.isBaby()) {
            this.growUp(60);
        }
        if(random.nextDouble() <= 0.15D) {
            World world = this.getWorld();
            SnailEntity snailSpawn = ((EntityType<SnailEntity>) EntityType.get(MiddleEarth.MOD_ID + ":snail").get()).create(world);
            snailSpawn.updatePosition(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(snailSpawn);
        }
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
        if(isClimbing()) {
            this.setVelocity(0, 0.01, 0);
        }
    }

    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            this.eatCropsTimer = Math.max(0, this.eatCropsTimer - 1);
        }
        super.tickMovement();
        if(isClimbingWall()) {

            this.climbingTicks = Math.min(CLIMBING_TIME_TRANSITION, this.climbingTicks + 1);
        } else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }
    }

    public boolean isClimbing() {
        return this.isClimbingWall();
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

    static {
        SPIDER_FLAGS = DataTracker.registerData(net.minecraft.entity.mob.SpiderEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}
