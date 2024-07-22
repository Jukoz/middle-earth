package net.jukoz.me.entity.beasts.warg;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.deer.DeerEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.pheasant.PheasantEntity;
import net.jukoz.me.entity.pheasant.PheasantVariant;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WargEntity extends BeastEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(WargEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public final AnimationState startSittingAnimationState = new AnimationState();
    public final AnimationState stopSittingAnimationState = new AnimationState();
    public int idleAnimationTimeout = this.random.nextInt(600) + 1700;
    private boolean hasCharged = false;
    private boolean startedSitting = false;
    public WargEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0f)
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 1.0f);
    }

    @Override
    public boolean isInAttackRange(LivingEntity entity) {
        return (super.isInAttackRange(entity) && this.isTame()) || (this.getAttackBox().expand(1.5f).intersects(entity.getBoundingBox()) && !this.isTame());
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.1f, false));
        this.goalSelector.add(5, new ChargeAttackGoal(this, maxChargeCooldown()));
        this.goalSelector.add(6, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((BeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((BeastEntity)this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(4, new TargetPlayerGoal(this));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(8, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(9, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
        this.targetSelector.add(10, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
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

            List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));

            for(Entity entity : entities) {
                if(entity.getUuid() != this.getOwnerUuid() && entity != this && !this.getPassengerList().contains(entity)) {
                    entity.damage(entity.getDamageSources().mobAttack(this), 10.0f);
                    if(entity.hasVehicle()) {
                        entity.dismountVehicle();
                    }

                    this.setCharging(false);
                    this.setHasCharged(false);
                }
            }

            if(this.chargeTimeout <= maxChargeCooldown() - 10 && !this.isInAir()) {
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }

        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }


    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return ((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.75f);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(this.isAttacking()) {
            this.setSitting(false);
            this.idleAnimationTimeout = this.random.nextInt(600) + 1700;

        }
        else if (--idleAnimationTimeout <= 0){
            this.setSitting(!this.isSitting());
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
                this.startedSitting = false;
            }
        }

        else {
            this.stopSittingAnimationState.startIfNotRunning(this.age);
            this.startSittingAnimationState.stop();
            this.sittingAnimationState.stop();
        }
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "bones")));
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
                this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
                this.setVelocity(targetDir.multiply(1,0,1).normalize().add(0,0.6,0).multiply(0.7f));
            }
            else if (this.getWorld().isClient) {
                this.setHasCharged(true);
                this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().add(0,0.35,0).multiply(1.3f));
            }
        }
    }

    @Override
    public boolean hasArmorSlot() {
        return true;
    }

    @Override
    public boolean isHorseArmor(ItemStack stack) {
        return stack.isOf(ModEquipmentItems.WARG_ARMOR);
    }

    public boolean hasCharged() {
        return hasCharged;
    }

    public void setHasCharged(boolean hasCharged) {
        this.hasCharged = hasCharged;
    }

    @Override
    public Item getBondingItem() {
        return Items.MUTTON;
    }

    @Override
    public int chargeDuration() {
        return 50;
    }

    @Override
    public int maxChargeCooldown() {
        return 50;
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
}
