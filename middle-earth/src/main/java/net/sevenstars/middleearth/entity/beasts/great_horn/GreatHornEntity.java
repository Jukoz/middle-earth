package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatHorns;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatVariant;
import net.sevenstars.middleearth.entity.goals.BowAtEntityGoal;
import net.sevenstars.middleearth.entity.goals.ChargeAttackGoal;
import net.sevenstars.middleearth.entity.goals.SmartFleeEntityGoal;
import net.sevenstars.middleearth.entity.goals.interfaces.Evader;
import net.sevenstars.middleearth.recipe.ModTags;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

public class GreatHornEntity extends AbstractBeastEntity implements Evader {
    private static final float MIN_MOVEMENT_SPEED_BONUS = (float) GreatHornEntity.getChildMovementSpeedBonus(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float) GreatHornEntity.getChildMovementSpeedBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = GreatHornEntity.getChildHealthBonus(max -> 0);
    private static final float MAX_HEALTH_BONUS = GreatHornEntity.getChildHealthBonus(max -> max - 1);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> BOW = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> MOUNTABLE = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> EVADING = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState earWigglingAnimationState = new AnimationState();
    public final AnimationState gallopAnimationState = new AnimationState();
    public final AnimationState bowAnimationState = new AnimationState();
    private static final EntityDimensions BABY_BASE_DIMENSIONS = ModEntities.GREAT_HORN.getDimensions().scaled(0.5f);
    protected int bowAnimationTimeout = 0;

    public GreatHornEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
        idleAnimationTimeout = 200;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.MAX_HEALTH, 50.0d)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.3d)
                .add(EntityAttributes.ATTACK_SPEED, 1.0d)
                .add(EntityAttributes.FOLLOW_RANGE, 38.0d)
                .add(EntityAttributes.ATTACK_DAMAGE, 4.0d)
                .add(EntityAttributes.STEP_HEIGHT, 1.15d)
                .add(EntityAttributes.SAFE_FALL_DISTANCE, 7.0d);
    }

    @Override
    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(this.getChildHealthBonus(random::nextInt));
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus(random::nextDouble));
        this.getAttributeInstance(EntityAttributes.JUMP_STRENGTH).setBaseValue(this.getChildJumpStrengthBonus(random::nextDouble));
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BowAtEntityGoal(this, PlayerEntity.class, 16, (livingEntity -> {
            return this.shouldBow((PlayerEntity) livingEntity);
        }) ));
        this.goalSelector.add(3, new SmartFleeEntityGoal<>(this, (Evader) this,
                PlayerEntity.class, 20.0F, 1.6, 1.9, (entity) -> {
            return !this.canTrust((PlayerEntity)entity);
        }));
        this.goalSelector.add(4, new ChargeAttackGoal(this, null, maxChargeCooldown()));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.5));
        this.goalSelector.add(6, new TemptGoal(this, 1.0, (stack) -> stack.isIn(ModTags.ELK_FOOD), false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(BOW, 0);
        builder.add(MOUNTABLE, true);
        builder.add(EVADING, false);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(VARIANT, view.getInt("Variant", 0));
        this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 20.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(8);
    }

    protected static double getChildJumpStrengthBonus(DoubleSupplier randomDoubleGetter) {
        return 0;
    }

    protected static double getChildMovementSpeedBonus(DoubleSupplier randomDoubleGetter) {
        return ((double)0.4f + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.2) * 0.27;
    }

    @Override
    public Disposition getDisposition() {
        return Disposition.GOOD;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return List.of(RaceType.ELF);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(!this.getWorld().isClient() && !player.isCreative()) {
            RaceType playerRace = RaceUtil.getRaceType(player);

            if(playerRace == null || playerRace == RaceType.NONE || (this.getCompatibleRaces() != null && !this.getCompatibleRaces().contains(playerRace))) {
                return ActionResult.FAIL;
            }
        }

        if(this.isTame() && this.isTamable()) {
            if (this.isBreedingItem(itemStack)) {
                if(this.getHealth() < this.getMaxHealth()) {
                    itemStack.decrementUnlessCreative(1, player);
                    FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
                    float f = foodComponent != null ? (float)foodComponent.nutrition() : 1.0f;
                    this.heal(2.0f * f);
                    return ActionResult.SUCCESS;
                }
                else if (!this.getWorld().isClient && this.getBreedingAge() == 0 && this.canEat()) {
                    this.eat(player, hand, itemStack);
                    this.lovePlayer(player);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean canCarryChest() {
        return false;
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float f = this.limbAnimator.getAnimationProgress() / 20;
        float g = this.limbAnimator.getAnimationProgress() * (MathHelper.PI / 180) * 18; // TODO : Fix,was using limbAnimator.getPos()

        double y = 0.45;
        if(gallopAnimationState.isRunning()) {
            y += -0.025 + MathHelper.cos((f/0.75f) * (MathHelper.PI*2)) * 0.15;
        } else {
            y += MathHelper.cos(g - MathHelper.PI) * 0.02 - 0.2;
        }

        return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(0, y,0);
    }

    @Override
    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        GreatHornEntity greatHornEntity2 = ModEntities.GREAT_HORN.create(world, SpawnReason.BREEDING);
        if (greatHornEntity2 != null) {
            this.setChildAttributes(entity, greatHornEntity2);
        }
        return greatHornEntity2;
    }

    @Override
    protected void setChildAttributes(PassiveEntity other, AbstractHorseEntity child) {
        this.setChildAttribute(other, child, EntityAttributes.MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setChildAttribute(other, child, EntityAttributes.MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }

    @Override
    protected boolean isTamable() {
        return this.isMountable();
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return other instanceof GreatHornEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ModTags.ELK_FOOD);
    }

    @Override
    public void chargeAttack() {
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2,0,0.2));

        if(!this.isTame() && !this.getWorld().isClient) {
            if(targetDir == Vec3d.ZERO && this.getTarget() != null) {
                targetDir = new Vec3d( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                        this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                        this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
            }
            this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
            this.setVelocity(targetDir.multiply(1,0,1).normalize().multiply(1.0d - ((double)MathHelper.abs(this.chargeTimeout - (maxChargeCooldown() - chargeDuration()) - (chargeDuration() * 0.2f)) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }
        else if (this.getWorld().isClient) {
            this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().multiply(1.0d - ((double)MathHelper.abs(this.chargeTimeout - (maxChargeCooldown() - chargeDuration()) - (chargeDuration() * 0.2f)) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }

        for(Entity entity : entities) {
            if(this.getOwner() != null && entity.getUuid() != this.getOwner().getUuid() && entity != this
                    && !this.getPassengerList().contains(entity) && !this.getWorld().isClient()) {
                entity.damage((ServerWorld) this.getWorld(), entity.getDamageSources().mobAttack(this), getAttackDamage());

                Vec3d velocity = this.getVelocity();
                velocity = velocity.multiply(1.0, 0.0, 1.0);
                velocity = velocity.normalize();
                Vec3d vec3d = velocity.multiply(2);
                if (vec3d.lengthSquared() > 0.0) {
                    entity.addVelocity(vec3d.x, 0.15, vec3d.z);
                }

                this.setCharging(false);
            }
        }
        this.getWorld().addParticleClient(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfNotRunning(this.age);
    }

    @Override
    protected void jump(float strength, Vec3d movementInput) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            this.setChargeTimeout(30);
            double d = this.getJumpVelocity(strength);
            Vec3d vec3d = this.getVelocity().multiply(4);
            this.setVelocity(vec3d.x, d, vec3d.z);
            this.setOnGround(false);
            this.velocityDirty = true;
            if (movementInput.z > 0.0) {
                float f = MathHelper.sin(this.getYaw() * ((float)Math.PI / 180));
                float g = MathHelper.cos(this.getYaw() * ((float)Math.PI / 180));
                this.setVelocity(this.getVelocity().add(-0.4f * f * strength, 0.0, 0.4f * g * strength));
            }
        }
        else {
            super.jump(strength, movementInput);
        }
    }

    @Override
    public void startJumping(int height) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            this.jumping = true;
            this.playJumpSound();
        }
        else {
            super.startJumping(height);
        }
    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    @Override
    public int maxChargeCooldown() {
        return 120;
    }

    @Override
    public int chargeDuration() {
        return 16;
    }

    @Override
    public void tick() {
        super.tick();
        if(bowAnimationTimeout > 0) {
            bowAnimationTimeout = Math.max(bowAnimationTimeout - 1, 0);
            if(bowAnimationTimeout == 0) {
                dataTracker.set(BOW, -1);
            }
        }
    }

    public void slowMovement(BlockState state, Vec3d multiplier) {
        float pow = 0.1f;
        Vec3d lessPenalty = new Vec3d(Math.pow(multiplier.x, pow), Math.pow(multiplier.y, pow), Math.pow(multiplier.z, pow));
        super.slowMovement(state, lessPenalty);
    }

    protected void setupAnimationStates() {
        this.idleAnimationState.startIfNotRunning(this.age);

        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.earWigglingAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        int bowState = dataTracker.get(BOW);
        if(bowState == 1) {
            this.bowAnimationState.startIfNotRunning(this.age);
            dataTracker.set(BOW, 0);
        } else if(bowState == -1) {
            this.bowAnimationState.stop();
            dataTracker.set(BOW, 0);
        }

        if(hasControllingPassenger()) {
            if((getControllingPassenger().isSprinting())) {
                this.gallopAnimationState.startIfNotRunning(this.age);
            } else {
                this.gallopAnimationState.stop();
            }
        } else if(this.dataTracker.get(EVADING)) {
            this.gallopAnimationState.startIfNotRunning(this.age);
        } else {
            this.gallopAnimationState.stop();
        }
    }

    @Override
    public void startFlee() {
        this.dataTracker.set(EVADING, true);
    }

    @Override
    public void stopFlee() {
        this.dataTracker.set(EVADING, false);
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.isOf(Items.STICK);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        float speed = ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED));
        if(this.getWorld().getBiome(this.getBlockPos()).isIn(BiomeTags.IS_FOREST)) {
            speed *= 1.1f;
        }
        if (controllingPlayer.isSprinting()) {
            return speed;
        } else {
            return speed * 0.5f;
        }
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    public boolean canTrust(PlayerEntity playerEntity) {
        RaceType playerRace = RaceUtil.getRaceType(playerEntity);
        return (playerRace != null && playerRace != RaceType.NONE) && (this.getCompatibleRaces() != null && this.getCompatibleRaces().contains(playerRace));
    }

    public boolean shouldBow(PlayerEntity playerEntity) {
        return isOwner(playerEntity) && bowAnimationTimeout > 0;
    }

    public boolean isOwner(PlayerEntity playerEntity) {
        PlayerEntity owner = this.getOwner();
        return (owner != null && owner.getUuid().equals(playerEntity.getUuid()));
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.isIn(ModTags.ELK_FOOD);
    }

    @Override
    public void setOwner(@Nullable LivingEntity entity) {
        super.setOwner(entity);
        this.dataTracker.set(BOW, 1);
        bowAnimationTimeout = 80;
    }

    /* VARIANTS */
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        BroadhoofGoatVariant variant = Util.getRandom(BroadhoofGoatVariant.values(), this.random);
        this.setVariant(variant);

        if(!this.getWorld().isClient()) {
            this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
        }

        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    private void setBroadhoofVariant(BroadhoofGoatVariant variant, BroadhoofGoatHorns horns) {
        this.setVariant(variant);
    }

    public BroadhoofGoatVariant getVariant() {
        return BroadhoofGoatVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(BroadhoofGoatVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GOAT_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GOAT_HURT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GOAT_AMBIENT;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientStandSound() {
        return SoundEvents.ENTITY_GOAT_SCREAMING_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_GOAT_PREPARE_RAM;
    }

    @Override
    public void playAngrySound() {
        this.playSound(this.getAngrySound(), 1.0f, 0.7f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 0.15f, 0.7f);
    }

    @Override
    protected void playWalkSound(BlockSoundGroup group) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 1.0f, 0.7f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_GOAT_LONG_JUMP, 1.0f, 0.7f);
    }
}
