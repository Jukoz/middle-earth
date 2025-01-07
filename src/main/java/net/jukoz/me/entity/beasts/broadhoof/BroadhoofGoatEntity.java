package net.jukoz.me.entity.beasts.broadhoof;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.Disposition;
import net.jukoz.me.resources.datas.RaceType;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.InstrumentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

public class BroadhoofGoatEntity extends AbstractBeastEntity {
    private static final float MIN_MOVEMENT_SPEED_BONUS = (float)BroadhoofGoatEntity.getChildMovementSpeedBonus(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float)BroadhoofGoatEntity.getChildMovementSpeedBonus(() -> 1.0);
    private static final float MIN_JUMP_STRENGTH_BONUS = (float)BroadhoofGoatEntity.getChildJumpStrengthBonus(() -> 0.0);
    private static final float MAX_JUMP_STRENGTH_BONUS = (float)BroadhoofGoatEntity.getChildJumpStrengthBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = BroadhoofGoatEntity.getChildHealthBonus(max -> 0);
    private static final float MAX_HEALTH_BONUS = BroadhoofGoatEntity.getChildHealthBonus(max -> max - 1);
    private static final Ingredient TEMPTING_INGREDIENT = Ingredient.fromTag(ItemTags.GOAT_FOOD);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> HORNS = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> LEFT_HORN = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RIGHT_HORN = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> BRUSHED_BEARD = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MOUNTABLE = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState jumpAnimationState = new AnimationState();
    private static final EntityDimensions BABY_BASE_DIMENSIONS = ModEntities.BROADHOOF_GOAT.getDimensions().scaled(0.5f);


    public BroadhoofGoatEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0d)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.4d)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0d)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0d)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0d)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.15d)
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 9.0d)
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 1);
    }

    @Override
    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(this.getChildHealthBonus(random::nextInt));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus(random::nextDouble));
        this.getAttributeInstance(EntityAttributes.GENERIC_JUMP_STRENGTH).setBaseValue(this.getChildJumpStrengthBonus(random::nextDouble));
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 2.5, false));
        this.goalSelector.add(4, new ChargeAttackGoal(this, null, maxChargeCooldown()));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.5));
        this.goalSelector.add(6, new TemptGoal(this, 1.0, TEMPTING_INGREDIENT, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastRevengeGoal(this, new Class[0]).setGroupRevenge());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(HORNS, 0);
        builder.add(LEFT_HORN, true);
        builder.add(RIGHT_HORN, true);
        builder.add(BRUSHED_BEARD, false);
        builder.add(MOUNTABLE, true);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putInt("Horns", this.getTypeHorns());
        nbt.putBoolean("HasLeftHorn", this.hasLeftHorn());
        nbt.putBoolean("HasRightHorn", this.hasRightHorn());
        nbt.putBoolean("HasBrushedBeard", this.hasBrushedBeard());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        this.dataTracker.set(HORNS, nbt.getInt("Horns"));
        this.dataTracker.set(LEFT_HORN, nbt.getBoolean("HasLeftHorn"));
        this.dataTracker.set(RIGHT_HORN, nbt.getBoolean("HasRightHorn"));
        this.dataTracker.set(BRUSHED_BEARD, nbt.getBoolean("HasBrushedBeard"));
        this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 20.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(9);
    }

    protected static double getChildJumpStrengthBonus(DoubleSupplier randomDoubleGetter) {
        return (double)0.8f + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2;
    }

    protected static double getChildMovementSpeedBonus(DoubleSupplier randomDoubleGetter) {
        return ((double)0.4f + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2) * 0.25;
    }

    @Override
    protected Disposition getDisposition() {
        return Disposition.GOOD;
    }

    @Override
    protected List<RaceType> getRaceType() {
        return List.of(RaceType.DWARF);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(!this.getWorld().isClient() && !player.isCreative()) {
            RaceType playerRace = RaceUtil.getRaceType(player);

            if(playerRace == RaceType.NONE || (this.getRaceType() != null && !this.getRaceType().contains(playerRace))) {
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
            else if(itemStack.isOf(Items.BRUSH)) {
                this.setBrushedBeard(true);
                return ActionResult.SUCCESS;
            }
            else if(itemStack.isOf(Items.SHEARS)) {
                this.setBrushedBeard(false);
                return ActionResult.SUCCESS;
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
        float f = this.limbAnimator.getSpeed();
        float g = this.limbAnimator.getPos() * (MathHelper.PI / 180) * 18;
        // h is the frequency, which is calculated by dividing the speed of the animation by the duration of the animation.
        float h = passenger.isSprinting() ? (1.2f/0.74f) : 4;
        float j = passenger.isSprinting() ? 1 : 0;

        double y = MathHelper.cos(g * h + (MathHelper.PI * (j - 1))) * (0.06 + (0.05 * j)) - 0.05;

        if(this.isSitting()) {
            y = -0.5;
        }

        return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(0, y,0);
    }

    @Override
    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        BroadhoofGoatEntity broadhoofEntity = (BroadhoofGoatEntity)entity;
        BroadhoofGoatEntity broadhoofEntity2 = ModEntities.BROADHOOF_GOAT.create(world);
        if (broadhoofEntity2 != null) {
            int i = this.random.nextInt(9);
            BroadhoofGoatVariant broadhoofVariant = i < 4 ? this.getVariant() : (i < 8 ? broadhoofEntity.getVariant() : Util.getRandom(BroadhoofGoatVariant.values(), this.random));
            int j = this.random.nextInt(5);
            BroadhoofGoatHorns broadhoofHorns = j < 2 ? this.getHorns() : (j < 4 ? broadhoofEntity.getHorns() : Util.getRandom(BroadhoofGoatHorns.values(), this.random));
            broadhoofEntity2.setBroadhoofVariant(broadhoofVariant, broadhoofHorns);
            this.setChildAttributes(entity, broadhoofEntity2);
        }
        return broadhoofEntity2;
    }

    @Override
    protected void setChildAttributes(PassiveEntity other, AbstractHorseEntity child) {
        this.setChildAttribute(other, child, EntityAttributes.GENERIC_MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setChildAttribute(other, child, EntityAttributes.GENERIC_JUMP_STRENGTH, MIN_JUMP_STRENGTH_BONUS, MAX_JUMP_STRENGTH_BONUS);
        this.setChildAttribute(other, child, EntityAttributes.GENERIC_MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }

    @Override
    protected boolean isMountable() {
        return this.dataTracker.get(MOUNTABLE);
    }

    @Override
    protected boolean isTamable() {
        return this.isMountable();
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return other instanceof BroadhoofGoatEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.GOAT_FOOD);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if(super.tryAttack(target)) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
            return true;
        }
        return false;
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
            if(entity.getUuid() != this.getOwnerUuid() && entity != this && !this.getPassengerList().contains(entity)) {
                entity.damage(entity.getDamageSources().mobAttack(this), getAttackDamage());

                Vec3d velocity = this.getVelocity();
                velocity = velocity.multiply(1.0, 0.0, 1.0);
                velocity = velocity.normalize();
                Vec3d vec3d = velocity.multiply(2);
                if (vec3d.lengthSquared() > 0.0) {
                    entity.addVelocity(vec3d.x, 0.15, vec3d.z);
                }

                if(this.random.nextInt(10) == 0 && !this.isTame() && !this.isBaby()) {
                    this.dropHorn();
                }

                this.setCharging(false);
            }
        }
        this.getWorld().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfNotRunning(this.age);
    }

    @Override
    protected void jump(float strength, Vec3d movementInput) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            this.setChargeTimeout(30);
            double d = this.getJumpVelocity(strength);
            Vec3d vec3d = this.getVelocity().multiply(4);
            this.setVelocity(vec3d.x, d, vec3d.z);
            this.setInAir(true);
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
            if(!this.isSitting()) {
                this.jumping = true;
                this.playJumpSound();
            }
            else {
                this.setSitting(false);
            }
        }
        else {
            super.startJumping(height);
        }
    }

    @Override
    public boolean isHorseArmor(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "broadhoof_goat_armor")));
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
    public void tickMovement() {
        super.tickMovement();

        if(this.isSitting()) {
            this.getNavigation().stop();
        }
    }

    public boolean dropHorn() {
        boolean bl = this.hasLeftHorn();
        boolean bl2 = this.hasRightHorn();
        if (!bl && !bl2) {
            return false;
        }
        TrackedData<Boolean> trackedData = !bl ? RIGHT_HORN : (!bl2 ? LEFT_HORN : (this.random.nextBoolean() ? LEFT_HORN : RIGHT_HORN));
        this.dataTracker.set(trackedData, false);
        Vec3d vec3d = this.getPos();
        ItemStack itemStack = this.getGoatHornStack();
        double d = MathHelper.nextBetween(this.random, -0.2f, 0.2f);
        double e = MathHelper.nextBetween(this.random, 0.3f, 0.7f);
        double f = MathHelper.nextBetween(this.random, -0.2f, 0.2f);
        ItemEntity itemEntity = new ItemEntity(this.getWorld(), vec3d.getX(), vec3d.getY(), vec3d.getZ(), itemStack, d, e, f);
        this.getWorld().spawnEntity(itemEntity);
        return true;
    }

    public ItemStack getGoatHornStack() {
        Random random = Random.create(this.getUuid().hashCode());
        TagKey<Instrument> tagKey = this.random.nextBoolean() ? InstrumentTags.SCREAMING_GOAT_HORNS : InstrumentTags.REGULAR_GOAT_HORNS;
        RegistryEntryList.Named<Instrument> registryEntryList = Registries.INSTRUMENT.getOrCreateEntryList(tagKey);
        return GoatHornItem.getStackForInstrument(Items.GOAT_HORN, registryEntryList.getRandom(random).get());
    }

    protected void setupAnimationStates() {
        if(this.isSitting()) {
            this.startSittingAnimationState.startIfNotRunning(this.age);
        }
        if(!this.isSitting() && this.startSittingAnimationState.isRunning()) {
            this.startSittingAnimationState.stop();
            this.stopSittingAnimationState.start(this.age);
        }

        if(this.isInAir() && this.hasControllingPassenger()) {
            this.jumpAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.jumpAnimationState.stop();
        }
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.isOf(Items.STICK);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)) : ((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.5f);
        }

        return super.getSaddledSpeed(controllingPlayer);
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.isIn(ItemTags.GOAT_FOOD);
    }

    /* VARIANTS */
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        BroadhoofGoatVariant variant = Util.getRandom(BroadhoofGoatVariant.values(), this.random);
        this.setVariant(variant);

        BroadhoofGoatHorns horns = Util.getRandom(BroadhoofGoatHorns.values(), this.random);
        this.setHorns(horns);

        if(!this.getWorld().isClient()) {
            this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
        }

        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    private void setBroadhoofVariant(BroadhoofGoatVariant variant, BroadhoofGoatHorns horns) {
        this.setVariant(variant);
        this.setHorns(horns);
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

    public BroadhoofGoatHorns getHorns() {
        return BroadhoofGoatHorns.byId(this.getTypeHorns() & 255);
    }
    private int getTypeHorns() {
        return this.dataTracker.get(HORNS);
    }

    private void setHorns(BroadhoofGoatHorns horns) {
        this.dataTracker.set(HORNS, horns.getId() & 255);
    }

    public boolean hasRightHorn() {
        return this.dataTracker.get(RIGHT_HORN);
    }

    public boolean hasLeftHorn() {
        return this.dataTracker.get(LEFT_HORN);
    }

    public void setBrushedBeard(boolean brushedBeard) {
        this.dataTracker.set(BRUSHED_BEARD, brushedBeard);
    }
    public boolean hasBrushedBeard() {
        return this.dataTracker.get(BRUSHED_BEARD);
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
