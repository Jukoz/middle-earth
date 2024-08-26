package net.jukoz.me.entity.beasts.warg;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.deer.DeerEntity;
import net.jukoz.me.entity.duck.DuckEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.goose.GooseEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.pheasant.PheasantEntity;
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
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
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
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WargEntity extends AbstractBeastEntity {

    private static final double WALKING_SPEED = 0.25;
    private static final double HUNTING_SPEED = 2;
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(WargEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public int idleAnimationTimeout = this.random.nextInt(600) + 1700;

    public WargEntity(EntityType<? extends WargEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKING_SPEED)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0d)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2d)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0d)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0d)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0d)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.15d)
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 6.0d);
    }

    @Override
    public boolean isInAttackRange(LivingEntity entity) {
        return (super.isInAttackRange(entity) && this.isTame()) || (this.getAttackBox().expand(1.5f).intersects(entity.getBoundingBox()) && !this.isTame());
    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, HUNTING_SPEED, false));
        this.goalSelector.add(4, new ChargeAttackGoal(this, maxChargeCooldown()));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((AbstractBeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((AbstractBeastEntity)this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(4, new TargetPlayerGoal(this));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(8, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(9, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
        this.targetSelector.add(10, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(11, new ActiveTargetGoal<>(this, SheepEntity.class, true));
        this.targetSelector.add(12, new ActiveTargetGoal<>(this, GoatEntity.class, true));
        this.targetSelector.add(13, new ActiveTargetGoal<>(this, DeerEntity.class, true));
        this.targetSelector.add(14, new ActiveTargetGoal<>(this, PheasantEntity.class, true));
        this.targetSelector.add(15, new ActiveTargetGoal<>(this, GooseEntity.class, true));
        this.targetSelector.add(16, new ActiveTargetGoal<>(this, DuckEntity.class, true));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isCharging()) {
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }

            if(this.chargeTimeout <= maxChargeCooldown() - 10 && !this.isInAir()) {
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }
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
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_food")));
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
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float f = this.limbAnimator.getSpeed();
        float g = this.limbAnimator.getPos() * (MathHelper.PI / 180) * 18;
        float h = passenger.isSprinting() ? 1 : 0;

        double y = (MathHelper.cos(g * 2 * 1.2f - (MathHelper.PI * (h - 1))) * f * (0.06 + (0.035 * h))) - 0.1;

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
        else if (--idleAnimationTimeout <= 0 && !this.isTame()){
            this.setSitting(!this.isSitting());
            this.idleAnimationTimeout = this.random.nextInt(600) + 1700;
        }

        if(this.isSitting()) {
            this.getNavigation().stop();
        }
    }

    @Override
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

    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")));
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
            if(entity.getUuid() != this.getOwnerUuid() && entity != this && !this.getPassengerList().contains(entity) && !((entity instanceof WargEntity) && !this.isTame())) {
                entity.damage(entity.getDamageSources().mobAttack(this), 12.0f);
                if(entity.hasVehicle()) {
                    entity.dismountVehicle();
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
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        int i;
        if (fallDistance > 1.0f) {
            this.playSound(SoundEvents.ENTITY_WOLF_STEP, 2.5f, 0.7f);
        }
        if ((i = this.computeFallDamage(fallDistance, damageMultiplier)) <= 0) {
            return false;
        }
        this.damage(damageSource, i);
        if (this.hasPassengers()) {
            for (Entity entity : this.getPassengersDeep()) {
                entity.damage(damageSource, i);
            }
        }
        this.playBlockFallSound();
        return true;
    }

    @Override
    public boolean isHorseArmor(ItemStack stack) {
        return (stack.isOf(ModEquipmentItems.WARG_LEATHER_ARMOR) || stack.isOf(ModEquipmentItems.WARG_PLATE_ARMOR));
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
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WOLF_AMBIENT;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientStandSound() {
        return SoundEvents.ENTITY_WOLF_HOWL;
    }

    @Nullable
    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_WOLF_GROWL;
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
