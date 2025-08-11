package net.sevenstars.middleearth.entity.spider.spawn;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.entity.goals.ShieldAgainstProjctileGoal;
import net.sevenstars.middleearth.entity.goals.interfaces.CooldownRangedAttackMob;
import net.sevenstars.middleearth.entity.goals.PounceRetreatGoal;
import net.sevenstars.middleearth.entity.goals.SmartProjectileAttackGoal;
import net.sevenstars.middleearth.entity.goals.SpiderPonceAtTargetGoal;
import net.sevenstars.middleearth.entity.goals.interfaces.Shielder;
import net.sevenstars.middleearth.entity.projectile.WebbedEntity;
import net.sevenstars.middleearth.entity.spider.MirkwoodSpiderVariants;
import net.sevenstars.middleearth.entity.spider.Pouncer;

public class SpawnOfShelobEntity extends HostileEntity implements Pouncer, Shielder, CooldownRangedAttackMob {
    public static final int PASSIVE_HEALING_COOLDOWN = 80;
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int LEAPING_TIME_TRANSITION = 9;
    public static final float MOVEMENT_SPEED = 1.15f;
    public static final float WEB_PROJECTILE_DAMAGE = 2f;
    private static final TrackedData<Byte> SPIDER_FLAGS;
    private static final TrackedData<Boolean> ATTACK_FLAG;
    private static final TrackedData<Integer> POUNCE_FLAG;
    private static final TrackedData<Integer> BLOCK_FLAG;

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState biteAnimation = new AnimationState();
    public final AnimationState pounceAnimation = new AnimationState();
    public final AnimationState blockAnimation = new AnimationState();

    private int passiveHealingCooldown = 0;
    private int climbingTicks = 0;
    private int leapingTicks = 0;
    private int shootCooldown = 0;

    public SpawnOfShelobEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 36.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.ATTACK_DAMAGE, 7)
                .add(EntityAttributes.ARMOR, 3)
                .add(EntityAttributes.FOLLOW_RANGE, 48.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new PounceRetreatGoal(this, 0.8f, 1.15f, 0.3f));
        this.goalSelector.add(3, new ShieldAgainstProjctileGoal(this, this, 13, 32));
        this.goalSelector.add(4, new SmartProjectileAttackGoal(this, 0.7f, 40, 90, 17, 40));
        this.goalSelector.add(5, new SpiderPonceAtTargetGoal(this, this,
                0.5F, 0.25f, 4, 17, 4));
        this.goalSelector.add(5, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
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
        builder.add(SPIDER_FLAGS, (byte)0);
        builder.add(ATTACK_FLAG, false);
        builder.add(POUNCE_FLAG, 0);
        builder.add(BLOCK_FLAG, 0);
    }

    protected void setupAnimationStates() {
        if(!this.idleAnimation.isRunning()) {
            this.idleAnimation.start(this.age);
        }
        setTrackerState(POUNCE_FLAG, pounceAnimation);
        setTrackerState(BLOCK_FLAG, blockAnimation);

        boolean attackState = this.dataTracker.get(ATTACK_FLAG);
        if(attackState) {
            this.biteAnimation.stop();
            this.biteAnimation.start(this.age);
            this.dataTracker.set(ATTACK_FLAG, false);
        }
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

    public void startPounceAnimation() {
        this.dataTracker.set(POUNCE_FLAG, 1);
    }
    public void stopPounceAnimation() {
        this.dataTracker.set(POUNCE_FLAG, -1);
    }


    @Override
    public void blockShield() {
        this.dataTracker.set(BLOCK_FLAG, 1);
    }
    @Override
    public void unblockShield() {
        this.dataTracker.set(BLOCK_FLAG, -1);
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        boolean result = super.tryAttack(world, target);
        this.dataTracker.set(ATTACK_FLAG, result);
        if (target instanceof LivingEntity) {
            int i = 0;
            if (this.getWorld().getDifficulty() == Difficulty.NORMAL) {
                i = 7;
            } else if (this.getWorld().getDifficulty() == Difficulty.HARD) {
                i = 15;
            }

            if (i > 0) {
                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 20, 0), this);
            }
        }
        return result;
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
            this.shootCooldown = Math.max(0, this.shootCooldown - 1);
            if(!this.hasStatusEffect(StatusEffects.REGENERATION)) {
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

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        double dX = target.getX() - this.getX();
        double e = target.getEyeY() - 1.1F;
        double dZ = target.getZ() - this.getZ();
        double g = Math.sqrt(dX * dX + dZ * dZ) * 0.2F;
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            ItemStack itemStack = new ItemStack(Items.COBWEB);
            ProjectileEntity.spawn(
                    new WebbedEntity(serverWorld, this, WEB_PROJECTILE_DAMAGE * pullProgress), serverWorld, itemStack,
                    entity -> entity.setVelocity(dX, e + g - entity.getY(), dZ, 1.6F, 8 - this.getWorld().getDifficulty().getId() * 4)
            );
        }

        this.playSound(SoundEvents.ENTITY_BREEZE_SHOOT, 1.0F, 0.7F + (this.getRandom().nextFloat() * 0.6F));
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

    public MirkwoodSpiderVariants getVariant() {
        return MirkwoodSpiderVariants.byId(this.getId());
    }

    static {
        SPIDER_FLAGS = DataTracker.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistry.BYTE);
        ATTACK_FLAG = DataTracker.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        POUNCE_FLAG = DataTracker.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BLOCK_FLAG = DataTracker.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}
