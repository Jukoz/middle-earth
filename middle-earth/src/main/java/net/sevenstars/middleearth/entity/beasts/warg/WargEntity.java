package net.sevenstars.middleearth.entity.beasts.warg;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntity;
import net.sevenstars.middleearth.entity.goals.*;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

public class WargEntity extends AbstractBeastEntity {

    private static final float MIN_MOVEMENT_SPEED_BONUS = (float) WargEntity.generateSpeed(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float)WargEntity.generateSpeed(() -> 1.0);
    private static final float MIN_ATTACK_DAMAGE_BONUS = (float)WargEntity.getChildAttackDamageBonus(() -> 0.0);
    private static final float MAX_ATTACK_DAMAGE_BONUS = (float)WargEntity.getChildAttackDamageBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = WargEntity.generateMaxHealth(max -> 0);
    private static final float MAX_HEALTH_BONUS = WargEntity.generateMaxHealth(max -> max - 1);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(WargEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EYE_VARIANT = SynchedEntityData.defineId(WargEntity.class, EntityDataSerializers.INT);
    private static final double PASSENGER_SADDLE_Y_OFFSET = 0.12;
    private static final double PASSENGER_SADDLE_BACKWARD_OFFSET = 0.18;
    public int idleAnimationTimeout = this.random.nextInt(600) + 1700;
    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntitiesME.WARG.getDimensions().scale(0.5f);

    public WargEntity(EntityType<? extends WargEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 24.0d)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2d)
                .add(Attributes.ATTACK_SPEED, 1.0d)
                .add(Attributes.FOLLOW_RANGE, 38.0d)
                .add(Attributes.ATTACK_DAMAGE, 4.0d)
                .add(Attributes.STEP_HEIGHT, 1.15d)
                .add(Attributes.SAFE_FALL_DISTANCE, 6.0d);
    }

    @Override
    protected void randomizeAttributes(RandomSource random) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateMaxHealth(random::nextInt));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateSpeed(random::nextDouble));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getChildAttackDamageBonus(random::nextDouble));
    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 2, false));
        this.goalSelector.addGoal(4, new ChargeAttackGoal(this, this.getDisposition(), maxChargeCooldown()));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.5));
        this.goalSelector.addGoal(6, new TemptGoal(this, 0.9, (stack) ->  stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "warg_food"))), false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new BeastRevengeGoal(this, new Class[0]).setGroupRevenge());
        this.targetSelector.addGoal(4, new BeastTargetPlayerGoal(this, this.getDisposition()));
        this.targetSelector.addGoal(10, new BeastActiveTargetGoal<>(this, BroadhoofGoatEntity.class, true));
        this.targetSelector.addGoal(10, new BeastActiveTargetGoal<>(this, GreatHornEntity.class, true));
        this.targetSelector.addGoal(11, new BeastActiveTargetGoal<>(this, Sheep.class, true));
        this.targetSelector.addGoal(12, new BeastActiveTargetGoal<>(this, Goat.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(EYE_VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        view.putInt("Variant", this.getTypeVariant());
        view.putInt("EyeVariant", this.getEyeTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        this.entityData.set(VARIANT, view.getInt("Variant"));
        this.entityData.set(EYE_VARIANT, view.getInt("EyeVariant"));
    }

    protected static float generateMaxHealth(IntUnaryOperator randomIntGetter) {
        return 12.0f + (float)randomIntGetter.applyAsInt(6) + (float)randomIntGetter.applyAsInt(6);
    }

    protected static double getChildAttackDamageBonus(DoubleSupplier randomDoubleGetter) {
        return (double)4f + randomDoubleGetter.getAsDouble() + randomDoubleGetter.getAsDouble() + randomDoubleGetter.getAsDouble();
    }

    protected static double generateSpeed(DoubleSupplier randomDoubleGetter) {
        return ((double)0.5 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25) * 0.3;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isCharging()) {
            if(!chargeAnimationState.isStarted()) {
                this.chargeAnimationState.start(this.tickCount);
            }

            if(this.chargeTimeout <= maxChargeCooldown() - 10 && this.onGround()) {
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(!this.level().isClientSide() && !player.isCreative()) {
            RaceType playerRace = RaceUtil.getRaceType(player);

            if(playerRace == RaceType.NONE || (this.getCompatibleRaces() != null && !this.getCompatibleRaces().contains(playerRace))) {
                return InteractionResult.FAIL;
            }
        }

        if(this.isTamed()) {
            if (this.isFood(itemStack)) {
                if(this.getHealth() < this.getMaxHealth()) {
                    itemStack.consume(1, player);
                    FoodProperties foodComponent = itemStack.get(DataComponents.FOOD);
                    float f = foodComponent != null ? (float)foodComponent.nutrition() : 1.0f;
                    this.heal(2.0f * f);
                    return InteractionResult.SUCCESS;
                }
                else if (!this.level().isClientSide && this.getAge() == 0 && this.canFallInLove()) {
                    this.usePlayerItem(player, hand, itemStack);
                    this.setInLove(player);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        WargEntity wargEntity = (WargEntity) entity;
        WargEntity wargEntity2 = EntitiesME.WARG.create(world);
        if (wargEntity2 != null) {
            int i = this.random.nextInt(9);
            WargVariant wargVariant = i < 4 ? this.getVariant() : (i < 8 ? wargEntity.getVariant() : Util.getRandom(WargVariant.values(), this.random));
            wargEntity2.setVariant(wargVariant);
            int j = this.random.nextInt(9);
            WargEyeVariant eyeVariant = j < 4 ? this.getEyeVariant() : (j < 8 ? wargEntity.getEyeVariant() : Util.getRandom(WargEyeVariant.values(), this.random));
            wargEntity2.setEyeVariant(eyeVariant);
            this.setOffspringAttributes(entity, wargEntity2);
        }
        return wargEntity2;
    }

    @Override
    protected void setOffspringAttributes(AgeableMob other, AbstractHorse child) {
        this.setOffspringAttribute(other, child, Attributes.MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setOffspringAttribute(other, child, Attributes.ATTACK_DAMAGE, MIN_ATTACK_DAMAGE_BONUS, MAX_ATTACK_DAMAGE_BONUS);
        this.setOffspringAttribute(other, child, Attributes.MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.EVIL;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return List.of(RaceType.SNAGA, RaceType.GOBLIN, RaceType.ORC, RaceType.URUK);
    }

    @Override
    public boolean usesTameness() {
        return false;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    @Override
    public boolean canMate(Animal other) {
        return other instanceof WargEntity;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "warg_food")));
    }

    @Override
    protected float getRiddenSpeed(Player controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.25f);
        }

        return super.getRiddenSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.25f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float animationSpeed = this.walkAnimation.speed();
        float animationProgress = this.walkAnimation.position() * (Mth.PI / 180) * 18;
        float f = this.walkAnimation.position() / 20;

        boolean sprinting = passenger.isSprinting();

        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (0.75f/1.4f) : 4;

        double y = sprinting ?
                0.025 - Mth.cos((f/frequency) * (Mth.PI*2)) * 0.06f :
                Mth.cos(animationProgress * frequency) * 0.06 * animationSpeed - 0.05;

        if(this.isSitting()) {
            y = -0.5;
        }

        double side = 0;
        double front = -PASSENGER_SADDLE_BACKWARD_OFFSET;
        double x = Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side - Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;
        double z = Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side + Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;

        return super.getPassengerAttachmentPoint(passenger, dimensions, scaleFactor).add(x, y + PASSENGER_SADDLE_Y_OFFSET, z);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(this.isAggressive() || this.getLastHurtByMob() != null) {
            if(!this.isTamed() || this.isTamed() && this.getLastHurtByMob() != null) {
                this.setSitting(false);
            }

            if(!this.hasControllingPassenger()) {
                this.setRunning(true);
            }

            //this.idleAnimationTimeout = this.random.nextInt(600) + 1700;
        }

        if(!this.isAggressive() && this.getLastHurtByMob() == null) {
            this.setRunning(false);
        }

        if(this.isSitting()) {
            this.getNavigation().stop();
        }
    }

    @Override
    protected void setupAnimationStates() {
        this.idleAnimationState.startIfStopped(this.tickCount);
        if(this.isSitting()) {
            if(!this.startSittingAnimationState.isStarted() && !this.sittingAnimationState.isStarted()) {
                this.startSittingAnimationState.startIfStopped(this.tickCount);
            }
            if(this.startSittingAnimationState.getAccumulatedTime() > 2000) {
                this.sittingAnimationState.startIfStopped(this.tickCount);
                this.startSittingAnimationState.stop();
            }
        }
        else if(this.startSittingAnimationState.isStarted() || this.sittingAnimationState.isStarted()) {
            this.startSittingAnimationState.stop();
            this.sittingAnimationState.stop();
            this.stopSittingAnimationState.startIfStopped(this.tickCount);
        }
        if(this.stopSittingAnimationState.getAccumulatedTime() > 1500) {
            this.stopSittingAnimationState.stop();
        }
    }
    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean result = super.doHurtTarget(target);
        if(result) {
            this.setRunning(true);
            this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
        }
        return result;
    }

    @Override
    public void chargeAttack() {
        if(!this.hasCharged()) {
            this.setHasCharged(true);
            if(!this.isTamed() && !this.level().isClientSide) {
                if(targetDir == Vec3.ZERO && this.getTarget() != null) {
                    targetDir = new Vec3( this.getTarget().blockPosition().getX() - this.blockPosition().getX(),
                            this.getTarget().blockPosition().getY() - this.blockPosition().getY(),
                            this.getTarget().blockPosition().getZ() - this.blockPosition().getZ());
                }
                this.setDeltaMovement(targetDir.multiply(1,0,1).normalize().add(0,0.6,0).scale(0.7f));
            }
            else if (this.level().isClientSide) {
                this.setHasCharged(true);
                this.setDeltaMovement(this.getLookAngle().multiply(1,0,1).normalize().add(0,0.35,0).scale(1.3f));
            }
        }
        if(!this.isTamed() && !this.level().isClientSide) {
            this.setYRot((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            Entity owner = this.getOwner();
            List<Entity> entities = serverLevel.getEntities(
                    this,
                    this.getBoundingBox().inflate(0.2f, 0.0, 0.2f),
                    entity -> (owner == null || entity != owner)
                            && !this.getPassengers().contains(entity)
                            && !(entity instanceof WargEntity && !this.isTamed())
            );
            for(Entity entity : entities) {
                entity.hurt(entity.damageSources().mobAttack(this), this.getAttackDamage());
                if(entity instanceof ServerPlayer) {
                    entity.stopRiding();
                } else {
                    entity.removeVehicle();
                    entity.ejectPassengers();
                }
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }
    }

    @Override
    public boolean shouldAttackWhenMounted() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damagePerDistance, DamageSource damageSource) {
        int i;
        if (fallDistance > 1.0f) {
            this.playSound(SoundEvents.WOLF_STEP, 2.5f, 0.7f);
        }
        if ((i = this.calculateFallDamage(fallDistance, damagePerDistance)) <= 0) {
            return false;
        }
        this.hurt(damageSource, i);
        if (this.isVehicle()) {
            if(!level().isClientSide()) {
                for (Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(damageSource, i);
                }
            }
        }
        this.playBlockFallSound();
        return true;    }


    //@Override
    public boolean isHorseArmor(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "warg_armor")));
    }

    public boolean hasCharged() {
        return hasCharged;
    }

    public void setHasCharged(boolean hasCharged) {
        this.hasCharged = hasCharged;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "warg_food")));
    }

    @Override
    public int chargeDuration() {
        return 50;
    }

    @Override
    public int maxChargeCooldown() {
        return 200;
    }

    @Override
    public boolean canCarryChest() {
        return false;
    }

    /* VARIANTS */
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                 @Nullable SpawnGroupData entityData) {
        WargVariant variant = Util.getRandom(WargVariant.values(), this.random);
        setVariant(variant);
        WargEyeVariant eyeVariant = Util.getRandom(WargEyeVariant.values(), this.random);
        setEyeVariant(eyeVariant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    public WargVariant getVariant() {
        return WargVariant.byId(this.getTypeVariant() & 255);
    }

    public WargEyeVariant getEyeVariant() {
        return WargEyeVariant.byId(this.getEyeTypeVariant() & 255);
    }
    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private int getEyeTypeVariant() {
        return this.entityData.get(EYE_VARIANT);
    }
    private void setVariant(WargVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    private void setEyeVariant(WargEyeVariant variant) {
        this.entityData.set(EYE_VARIANT, variant.getId() & 255);
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WOLF_HURT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WOLF_AMBIENT;

    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientStandSound() {
        return SoundEvents.WOLF_PANT;
    }

    @Nullable
    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.WOLF_GROWL;
    }

    @Override
    public void makeMad() {
        this.playSound(this.getAngrySound(), 1.0f, 0.7f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15f, 0.7f);
    }

    @Override
    protected void playGallopSound(SoundType group) {
        this.playSound(SoundEvents.WOLF_STEP, 1.0f, 0.7f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.WOLF_STEP, 1.1f, 0.7f);
    }
}
