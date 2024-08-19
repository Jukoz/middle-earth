package net.jukoz.me.entity.beasts.broadhoof;

import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.item.ModEquipmentItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
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


    public BroadhoofGoatEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKING_SPEED)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0d)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.4d)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0d)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0d)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0d)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.15d)
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 6.0d);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, HUNTING_SPEED, false));
        this.goalSelector.add(6, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((AbstractBeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((AbstractBeastEntity) this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(HORNS, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putInt("Horns", this.getTypeHorns());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        this.dataTracker.set(HORNS, nbt.getInt("Horns"));
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

        if (this.getWorld().isClient && this.isTame()) {
            this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }

        for(Entity entity : entities) {
            if(entity.getUuid() != this.getOwnerUuid() && entity != this && !this.getPassengerList().contains(entity)) {
                entity.damage(entity.getDamageSources().mobAttack(this), 8.0f);
                entity.pushAwayFrom(this);
                this.setCharging(false);
            }
        }
        this.getWorld().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfNotRunning(this.age);
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
        return 10;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(this.isSitting()) {
            this.getNavigation().stop();
        }
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
        return true;
    }

    public boolean hasLeftHorn() {
        return true;
    }
}
