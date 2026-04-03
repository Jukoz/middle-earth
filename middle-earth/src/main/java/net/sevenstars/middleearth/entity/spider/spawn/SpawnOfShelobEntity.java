package net.sevenstars.middleearth.entity.spider.spawn;

import net.minecraft.block.BlockState;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.entity.goals.PounceRetreatGoal;
import net.sevenstars.middleearth.entity.goals.ShieldAgainstProjectileGoal;
import net.sevenstars.middleearth.entity.goals.SmartProjectileAttackGoal;
import net.sevenstars.middleearth.entity.goals.SpiderPonceAtTargetGoal;
import net.sevenstars.middleearth.entity.goals.interfaces.CooldownRangedAttackMob;
import net.sevenstars.middleearth.entity.goals.interfaces.Shielder;
import net.sevenstars.middleearth.entity.projectile.WebbedEntity;
import net.sevenstars.middleearth.entity.spider.Pouncer;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SpawnOfShelobEntity extends HostileEntity implements Pouncer, Shielder, CooldownRangedAttackMob {
    public static final int CLIMBING_MAX_TICKS = 40;
    public static final int PASSIVE_HEALING_COOLDOWN = 80;
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int LEAPING_TIME_TRANSITION = 9;
    public static final float MOVEMENT_SPEED = 1.15f;
    public static final float WEB_PROJECTILE_DAMAGE = 2f;

    private static final TrackedData<Byte> SPIDER_FLAGS;
    private static final TrackedData<Integer> BITE_FLAG;
    private static final TrackedData<Integer> POUNCE_FLAG;
    private static final TrackedData<Integer> BLOCK_FLAG;
    private static final TrackedData<RegistryEntry<SpiderVariant>> VARIANT;

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
        this.goalSelector.add(3, new ShieldAgainstProjectileGoal(this, this, 13, 32));
        this.goalSelector.add(4, new SmartProjectileAttackGoal(this, 0.75f, 40, 90, 17, 40));
        this.goalSelector.add(5, new SpiderPonceAtTargetGoal(this, this,
                0.5F, 0.25f, 4, 17, 4));
        this.goalSelector.add(5, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (entityData instanceof ShelobiteScuttlerEntity.SpiderData spiderData) {
            this.setVariant(spiderData.variant);
        } else {
            Optional<? extends RegistryEntry<SpiderVariant>> optional = Variants.select(SpawnContext.of(world, this.getBlockPos()), DynamicRegistriesME.SPIDER_VARIANTS);
            if (optional.isPresent()) {
                this.setVariant(optional.get());
                entityData = new ShelobiteScuttlerEntity.SpiderData(optional.get());
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
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
        builder.add(BITE_FLAG, 0);
        builder.add(POUNCE_FLAG, 0);
        builder.add(BLOCK_FLAG, 0);
        RegistryEntry<SpiderVariant> spiderVariantRegistryEntry = Variants.getOrDefaultOrThrow(this.getRegistryManager(), SpiderVariantRegistry.DEFAULT);
        builder.add(VARIANT, spiderVariantRegistryEntry);
    }

    protected void setupAnimationStates() {
        if(!this.idleAnimation.isRunning()) {
            this.idleAnimation.start(this.age);
        }
        setTrackerState(POUNCE_FLAG, pounceAnimation);
        setTrackerState(BLOCK_FLAG, blockAnimation);
        setTrackerState(BITE_FLAG, biteAnimation);
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
        this.dataTracker.set(BITE_FLAG, 1);
        if(biteAnimationCooldown == 0) biteAnimationCooldown = 40;
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

    @Override
    public void onLanding() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (this.isOnGround() && this.fallDistance > 1.5) {
                Vec3d vec3d = getPos().add(0.0, 0.5, 0.0);
                BlockState blockState = this.getSteppingBlockState();
                int count = (int) MathHelper.clamp(25.0 * this.fallDistance - 1, 0.0, 150.0);
                serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), vec3d.x, vec3d.y, vec3d.z,
                        count, this.random.nextDouble() - 0.5, 0.15, this.random.nextDouble() - 0.5, 0.2f);
            }
        }
        super.onLanding();
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);

            if(biteAnimationCooldown <= 1) {
                this.dataTracker.set(BITE_FLAG, -1);
            }
            biteAnimationCooldown = Math.max(biteAnimationCooldown - 1, 0);

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
            timelineTicks++;
            this.climbingTicks = Math.min( this.climbingTicks + 1, CLIMBING_MAX_TICKS);
        } else {
            int amount = 1;
            if(this.climbingTicks > CLIMBING_MAX_TICKS / 3) amount = 4;
            this.climbingTicks = Math.max(0, this.climbingTicks - amount);
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
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        Variants.writeVariantToNbt(view, this.getRegistryVariant());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        Variants.readVariantFromNbt(view, DynamicRegistriesME.SPIDER_VARIANTS).ifPresent(this::setVariant);
    }

    static {
        SPIDER_FLAGS = DataTracker.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistry.BYTE);
        BITE_FLAG = DataTracker.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistry.INTEGER);
        POUNCE_FLAG = DataTracker.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BLOCK_FLAG = DataTracker.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistry.INTEGER);
        VARIANT = DataTracker.registerData(SpawnOfShelobEntity.class, ModTrackedDataHandlerRegistry.SPIDER_VARIANT);
    }
}
