package net.sevenstars.middleearth.entity.beasts.warg;

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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
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

    private static final float MIN_MOVEMENT_SPEED_BONUS = (float) WargEntity.getChildMovementSpeedBonus(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float)WargEntity.getChildMovementSpeedBonus(() -> 1.0);
    private static final float MIN_ATTACK_DAMAGE_BONUS = (float)WargEntity.getChildAttackDamageBonus(() -> 0.0);
    private static final float MAX_ATTACK_DAMAGE_BONUS = (float)WargEntity.getChildAttackDamageBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = WargEntity.getChildHealthBonus(max -> 0);
    private static final float MAX_HEALTH_BONUS = WargEntity.getChildHealthBonus(max -> max - 1);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(WargEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public int idleAnimationTimeout = this.random.nextInt(600) + 1700;
    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntitiesME.WARG.getDimensions().scaled(0.5f);

    public WargEntity(EntityType<? extends WargEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.MAX_HEALTH, 24.0d)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.2d)
                .add(EntityAttributes.ATTACK_SPEED, 1.0d)
                .add(EntityAttributes.FOLLOW_RANGE, 38.0d)
                .add(EntityAttributes.ATTACK_DAMAGE, 4.0d)
                .add(EntityAttributes.STEP_HEIGHT, 1.15d)
                .add(EntityAttributes.SAFE_FALL_DISTANCE, 6.0d);
    }

    @Override
    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(this.getChildHealthBonus(random::nextInt));
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus(random::nextDouble));
        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(this.getChildAttackDamageBonus(random::nextDouble));
    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 2, false));
        this.goalSelector.add(4, new ChargeAttackGoal(this, this.getDisposition(), maxChargeCooldown()));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.5));
        this.goalSelector.add(6, new TemptGoal(this, 0.9, (stack) ->  stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_food"))), false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 0.5));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(3, new BeastRevengeGoal(this, new Class[0]).setGroupRevenge());
        this.targetSelector.add(4, new BeastTargetPlayerGoal(this, this.getDisposition()));
        this.targetSelector.add(11, new BeastActiveTargetGoal<>(this, SheepEntity.class, true));
        this.targetSelector.add(12, new BeastActiveTargetGoal<>(this, GoatEntity.class, true));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
    }

    @Override
    public void writeData(WriteView view) {
        super.writeData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(VARIANT, view.getInt("Variant", 0));

    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 12.0f + (float)randomIntGetter.applyAsInt(6) + (float)randomIntGetter.applyAsInt(6);
    }

    protected static double getChildAttackDamageBonus(DoubleSupplier randomDoubleGetter) {
        return (double)4f + randomDoubleGetter.getAsDouble() + randomDoubleGetter.getAsDouble() + randomDoubleGetter.getAsDouble();
    }

    protected static double getChildMovementSpeedBonus(DoubleSupplier randomDoubleGetter) {
        return ((double)0.5 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25) * 0.3;
    }

    private WolfSoundVariant getSoundVariant() {
        return SoundEvents.WOLF_SOUNDS.get(WolfSoundVariants.Type.ANGRY);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isCharging()) {
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }

            if(this.chargeTimeout <= maxChargeCooldown() - 10 && this.isOnGround()) {
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(!this.getWorld().isClient() && !player.isCreative()) {
            RaceType playerRace = RaceUtil.getRaceType(player);

            if(playerRace == RaceType.NONE || (this.getCompatibleRaces() != null && !this.getCompatibleRaces().contains(playerRace))) {
                return ActionResult.FAIL;
            }
        }

        if(this.isTame()) {
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
    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        WargEntity wargEntity = (WargEntity) entity;
        WargEntity wargEntity2 = EntitiesME.WARG.create(world, SpawnReason.BREEDING);
        if (wargEntity2 != null) {
            int i = this.random.nextInt(9);
            WargVariant wargVariant = i < 4 ? this.getVariant() : (i < 8 ? wargEntity.getVariant() : Util.getRandom(WargVariant.values(), this.random));
            wargEntity2.setVariant(wargVariant);
            this.setChildAttributes(entity, wargEntity2);
        }
        return wargEntity2;
    }

    @Override
    protected void setChildAttributes(PassiveEntity other, AbstractHorseEntity child) {
        this.setChildAttribute(other, child, EntityAttributes.MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setChildAttribute(other, child, EntityAttributes.ATTACK_DAMAGE, MIN_ATTACK_DAMAGE_BONUS, MAX_ATTACK_DAMAGE_BONUS);
        this.setChildAttribute(other, child, EntityAttributes.MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.EVIL;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return List.of(RaceType.ORC, RaceType.URUK);
    }

    @Override
    public boolean usesTameness() {
        return false;
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return other instanceof WargEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_food")));
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED)) : ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 0.25f);
        }

        return super.getSaddledSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED)) : ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 0.25f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float animationSpeed = this.limbAnimator.getSpeed();
        float animationProgress = this.limbAnimator.getAnimationProgress() * (MathHelper.PI / 180) * 18;

        boolean sprinting = passenger.isSprinting();

        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (1.2f/0.5f) : (2f/1.25f);

        double y = sprinting ?
                MathHelper.cos(animationProgress * frequency) * 0.095 * animationSpeed :
                MathHelper.sin(animationProgress * frequency * 2f) * 0.06 * animationSpeed;

        if(this.isSitting()) {
            y = -0.5;
        }

        return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(0, y,0);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(this.isAttacking() || this.getAttacker() != null) {
            if(!this.isTame() || this.isTame() && this.getAttacker() != null) {
                this.setSitting(false);
            }

            if(!this.hasControllingPassenger()) {
                this.setRunning(true);
            }

            this.idleAnimationTimeout = this.random.nextInt(600) + 1700;
        }

        if(!this.isAttacking() && this.getAttacker() == null) {
            this.setRunning(false);
        }

        if(this.isSitting()) {
            this.getNavigation().stop();
        }
    }

    @Override
    protected void setupAnimationStates() {
        if(this.isSitting()) {
            this.startSittingAnimationState.startIfNotRunning(this.age);
        }
        if(!this.isSitting() && this.startSittingAnimationState.isRunning()) {
            this.startSittingAnimationState.stop();
            this.stopSittingAnimationState.start(this.age);
        }
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public void chargeAttack() {
        if(!this.hasCharged()) {
            this.setHasCharged(true);
            if(!this.isTame() && !this.getWorld().isClient) {
                if(targetDir == Vec3d.ZERO && this.getTarget() != null) {
                    targetDir = new Vec3d( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                            this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                            this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
                }
                this.setVelocity(targetDir.multiply(1,0,1).normalize().add(0,0.6,0).multiply(0.7f));
            }
            else if (this.getWorld().isClient) {
                this.setHasCharged(true);
                this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().add(0,0.35,0).multiply(1.3f));
            }
        }
        if(!this.isTame() && !this.getWorld().isClient) {
            this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
        }

        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));

        for(Entity entity : entities) {
            if(this.getOwner() != null && entity.getUuid() != this.getOwner().getUuid() && entity != this && !this.getPassengerList().contains(entity) && !((entity instanceof WargEntity) && !this.isTame())) {
                if(getWorld() instanceof ServerWorld serverWorld)
                    entity.damage(serverWorld, entity.getDamageSources().mobAttack(this), this.getAttackDamage());

                if(entity instanceof ServerPlayerEntity) {
                    entity.stopRiding();
                }
                else if(!this.getWorld().isClient) {
                    entity.dismountVehicle();
                    entity.removeAllPassengers();
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
    public boolean handleFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource) {
        int i;
        if (fallDistance > 1.0f) {
            this.playSound(SoundEvents.ENTITY_WOLF_STEP, 2.5f, 0.7f);
        }
        if ((i = this.computeFallDamage(fallDistance, damagePerDistance)) <= 0) {
            return false;
        }
        this.damage(damageSource, i);
        if (this.hasPassengers()) {
            if(getWorld() instanceof ServerWorld serverWorld){
                for (Entity entity : this.getPassengersDeep()) {
                    entity.damage(serverWorld, damageSource, i);
                }
            }
        }
        this.playBlockFallSound();
        return true;    }


    //@Override
    public boolean isHorseArmor(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_armor")));
    }

    public boolean hasCharged() {
        return hasCharged;
    }

    public void setHasCharged(boolean hasCharged) {
        this.hasCharged = hasCharged;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_food")));
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        WargVariant variant = Util.getRandom(WargVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public WargVariant getVariant() {
        return WargVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(WargVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return this.getSoundVariant().deathSound().value();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return this.getSoundVariant().hurtSound().value();
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.getSoundVariant().ambientSound().value();

    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientStandSound() {
        return this.getSoundVariant().pantSound().value();
    }

    @Nullable
    @Override
    protected SoundEvent getAngrySound() {
        return this.getSoundVariant().growlSound().value();
    }

    @Override
    public void playAngrySound() {
        this.playSound(this.getAngrySound(), 1.0f, 0.7f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15f, 0.7f);
    }

    @Override
    protected void playWalkSound(BlockSoundGroup group) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 1.0f, 0.7f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 1.1f, 0.7f);
    }
}
