package net.jukoz.me.entity.beasts.broadhoof;

import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.item.ModEquipmentItems;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.InstrumentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BroadhoofGoatEntity extends AbstractBeastEntity {
    private static final double WALKING_SPEED = 0.15;
    private static final double HUNTING_SPEED = 2;
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> HORNS = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> LEFT_HORN = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RIGHT_HORN = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState jumpAnimationState = new AnimationState();


    public BroadhoofGoatEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKING_SPEED)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0d)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.4d)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0d)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0d)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0d)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.15d)
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 6.0d)
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 0.95);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, HUNTING_SPEED, false));
        this.goalSelector.add(3, new ChargeAttackGoal(this, maxChargeCooldown()));
        this.goalSelector.add(4, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((AbstractBeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((AbstractBeastEntity) this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(HORNS, 0);
        builder.add(LEFT_HORN, true);
        builder.add(RIGHT_HORN, true);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putInt("Horns", this.getTypeHorns());
        nbt.putBoolean("HasLeftHorn", this.hasLeftHorn());
        nbt.putBoolean("HasRightHorn", this.hasRightHorn());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        this.dataTracker.set(HORNS, nbt.getInt("Horns"));
        this.dataTracker.set(LEFT_HORN, nbt.getBoolean("HasLeftHorn"));
        this.dataTracker.set(RIGHT_HORN, nbt.getBoolean("HasRightHorn"));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(this.isTame()) {
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                itemStack.decrementUnlessCreative(1, player);
                FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
                float f = foodComponent != null ? (float)foodComponent.nutrition() : 1.0f;
                this.heal(2.0f * f);
                return ActionResult.success(this.getWorld().isClient());
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float f = this.limbAnimator.getSpeed();
        float g = this.limbAnimator.getPos() * (MathHelper.PI / 180) * 18;
        // h is the frequency, which is calculated by dividing the speed of the animation by the duration of the animation.
        float h = passenger.isSprinting() ? (1.2f/0.74f) : 3;
        float j = passenger.isSprinting() ? 1 : 0;

        double y = MathHelper.cos(g * h + (MathHelper.PI * (j - 1))) * f * (0.06 + (0.1 * j));

        return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(0, y,0);
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
            this.setVelocity(targetDir.multiply(1,0,1).normalize().multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }
        else if (this.getWorld().isClient) {
            this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }

        for(Entity entity : entities) {
            if(entity.getUuid() != this.getOwnerUuid() && entity != this && !this.getPassengerList().contains(entity)) {
                entity.damage(entity.getDamageSources().mobAttack(this), 8.0f);
                entity.pushAwayFrom(this);

                if(this.random.nextInt(10) == 0 && !this.isTame()) {
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
        if(this.isSitting()) {
            this.setSitting(false);
        }
        else if(this.hasControllingPassenger()) {
            if(this.chargeTimeout <= 0 && this.getControllingPassenger().isSprinting()) {
                this.setCharging(true);
                this.chargeTimeout = maxChargeCooldown();
            }
            else if(!this.getControllingPassenger().isSprinting()) {
                double d = this.getJumpVelocity(strength);
                Vec3d vec3d = this.getVelocity().multiply(1.5);
                this.setVelocity(vec3d.x, d, vec3d.z);
                this.setInAir(true);
                this.velocityDirty = true;
                if (movementInput.z > 0.0) {
                    float f = MathHelper.sin(this.getYaw() * ((float)Math.PI / 180));
                    float g = MathHelper.cos(this.getYaw() * ((float)Math.PI / 180));
                    this.setVelocity(this.getVelocity().add(-0.4f * f * strength, 0.0, 0.4f * g * strength));
                }
            }
        }
    }

    @Override
    public void startJumping(int height) {
        if(!this.isSitting() && this.hasControllingPassenger()){
            if(this.isSprinting()) {
                this.playSound(SoundEvents.ENTITY_CAMEL_DASH, 1.0f, 1.0f);
                this.setCharging(true);
            }
            else {
                this.jumping = true;
                this.updateAnger();
                this.playJumpSound();
            }
        }
        else {
            this.setSitting(false);
        }
    }

    @Override
    public boolean isHorseArmor(ItemStack stack) {
        return stack.isOf(ModEquipmentItems.BROADHOOF_GOAT_ARMOR);
    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    @Override
    public int maxChargeCooldown() {
        return 200;
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
            if(!this.sittingAnimationState.isRunning()) {
                this.stopSittingAnimationState.stop();
                this.startSittingAnimationState.startIfNotRunning(this.age);
                this.startedSitting = true;
            }
            if(!this.startSittingAnimationState.isRunning() && startedSitting) {
                this.sittingAnimationState.startIfNotRunning(this.age);
            }
        }
        else if (startedSitting){
            this.stopSittingAnimationState.startIfNotRunning(this.age);
            this.startSittingAnimationState.stop();
            this.sittingAnimationState.stop();
            this.startedSitting = false;
        }
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.isOf(Items.STICK);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 1.5f) : ((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.5f);
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

        return super.initialize(world, difficulty, spawnReason, entityData);
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
