package net.jesteur.me.entity.spider;

import net.jesteur.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jesteur.me.entity.goals.FastPonceAtTargetGoal;
import net.jesteur.me.entity.hobbits.HobbitEntity;
import net.jesteur.me.entity.orcs.mordor.MordorOrcEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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

public class MirkwoodSpiderEntity extends HostileEntity {
    public static final int CLIMBING_TIME_TRANSITION = 12;
    private static final TrackedData<Byte> SPIDER_FLAGS;
    private int climbingTicks = 0;

    public MirkwoodSpiderEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 36.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new FastPonceAtTargetGoal(this, 0.3F, 0.4f));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.2f, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, HobbitEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
    }

    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            if (target instanceof LivingEntity) {
                int i = 0;
                if (this.getWorld().getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.getWorld().getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 1) {
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 20, 0), this);
                    double random = Math.random();
                    if(random < 0.2f) {
                        ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, i * 20, 0), this);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public double getMountedHeightOffset() {
        return (double)(this.getHeight() * 0.5F);
    }

    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SPIDER_FLAGS, (byte)0);
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
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
        if (!state.isOf(Blocks.COBWEB)) {
            super.slowMovement(state, multiplier);
        }

    }

    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
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
        SPIDER_FLAGS = DataTracker.registerData(net.minecraft.entity.mob.SpiderEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}
