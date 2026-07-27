package net.sevenstars.middleearth.entity.beasts.trolls;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.goals.*;
import net.sevenstars.middleearth.entity.projectile.boulder.BoulderEntity;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

import java.util.List;

public class TrollEntity extends AbstractBeastEntity {
    private int throwCooldown = 100;
    public final AnimationState throwingAnimationState = new AnimationState();

    private int throwingAnimationTimeout = 0;
    private int bondingTries = 0;
    private int bondingTimeout = 0;

    public static final EntityDataAccessor<Boolean> THROWING = SynchedEntityData.defineId(TrollEntity.class, EntityDataSerializers.BOOLEAN);

    public TrollEntity(EntityType<? extends TrollEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35f)
                .add(Attributes.MAX_HEALTH, 120.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.ATTACK_SPEED, 0.9)
                .add(Attributes.FOLLOW_RANGE, 28.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.STEP_HEIGHT, 1.25)
                .add(Attributes.JUMP_STRENGTH, 0.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BeastSitGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.addGoal(4, new ChargeAttackGoal(this, this.getDisposition(), maxChargeCooldown()));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new BeastTrackOwnerAttackerGoal((AbstractBeastEntity) this));
        this.targetSelector.addGoal(2, new BeastAttackWithOwnerGoal((AbstractBeastEntity)this));
        this.targetSelector.addGoal(3, new BeastRevengeGoal(this, new Class[0]));
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(THROWING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        if(!this.firstTick && THROWING.equals(data)) {
            this.throwCooldown = this.throwCooldown == 0 ? 200 : this.throwCooldown;
        }
        super.onSyncedDataUpdated(data);
    }

    @Override
    protected void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        if(this.isSitting()) {
            this.sittingAnimationState.startIfStopped(this.tickCount);
        }
        else {
            this.sittingAnimationState.stop();
        }

        if(this.isThrowing() && this.throwingAnimationTimeout <= 0) {
            this.throwingAnimationTimeout = 100;
            this.throwingAnimationState.start(this.tickCount);
        }else {
            --this.throwingAnimationTimeout;
        }
        if(isThrowing()) {
            this.setSpeed(0);
        }

        if(!this.isThrowing()) {
            this.throwingAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget());
        }

        if(this.isCharging()) {
            chargeAttack();
            if(!chargeAnimationState.isStarted()) {
                this.chargeAnimationState.start(this.tickCount);
            }
        }

        if(throwCooldown == 0 && this.getTarget() != null && !isCharging()) {
            if(this.distanceToSqr(this.getTarget()) >= 25 && !this.isVehicle() && canThrow()) {
                this.setThrowing(true);
                throwCooldown = 200;
            }
        }
        if(this.isThrowing() && canThrow()) {
            this.setDeltaMovement(Vec3.ZERO);
            if(throwCooldown <= 180) {
                throwAttack();
            }
        }
        if(throwCooldown > 0) {
            --this.throwCooldown;
        }

        if (!this.level().isClientSide) {
            if(this.bondingTimeout > 0) {
                this.bondingTimeout--;
            }
        }
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.EVIL;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return null;
    }

    @Override
    public boolean usesTameness() {
        return false;
    }

    @Override
    protected float getRiddenSpeed(Player controllingPlayer) {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5f;
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        view.putBoolean("ChestedTroll", this.hasChest());
        if (this.hasChest()) {
            ListTag items = new ListTag();
            for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemStack = this.inventory.getItem(i);
                if (!itemStack.isEmpty()) {
                    CompoundTag itemTag = new CompoundTag();
                    itemTag.putByte("Slot", (byte)i);
                    itemStack.save(this.registryAccess(), itemTag);
                    items.add(itemTag);
                }
            }
            view.put("Items", items);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        this.setHasChest(view.getBoolean("ChestedTroll"));
        this.createInventory();
        if (this.hasChest()) {
            ListTag items = view.getList("Items", Tag.TAG_COMPOUND);
            for (int i = 0; i < items.size(); i++) {
                CompoundTag itemTag = items.getCompound(i);
                int slot = itemTag.getByte("Slot") & 255;
                if (slot < this.inventory.getContainerSize()) {
                    ItemStack.parse(this.registryAccess(), itemTag)
                            .ifPresent(itemStack -> this.inventory.setItem(slot, itemStack));
                }
            }
        }
        //this.updateSaddledFlag(); // TODO
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        ServerLevel world = (ServerLevel)this.level();
        this.attackTicksLeft = ATTACK_COOLDOWN;
        this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
        float f = this.getAttackDamage();
        float g = (int)f > 0 ? f / 2.0f + (float)this.random.nextInt((int)f) : f;
        boolean bl = target.hurt(this.damageSources().mobAttack(this), g);
        if (bl) {
            double d;
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)target;
                d = livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                d = 0.0;
            }
            double e = Math.max(0.0, 1.0 - d);
            target.setDeltaMovement(target.getDeltaMovement().scale(1f + (0.8f * e))); //.add(0.0, (double)0.1f * e, 0.0));
        }
        this.playSound(SoundEvents.HOGLIN_ATTACK, 1.5f, 0.8f);
        return bl;
    }

    @Override
    public boolean shouldAttackWhenMounted() {
        return true;
    }

    public boolean canThrow() {
        return !this.isSitting();
    }

    public void setThrowing(boolean throwing) {
        this.entityData.set(THROWING, throwing);
    }

    public boolean isThrowing() {
        return this.entityData.get(THROWING);
    }

    @Override
    public int chargeDuration() {
        return 25;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return false;
    }

    public int getBondingTimeout() {
        return bondingTimeout;
    }
    public void setBondingTimeout(int bondingTimeout) {
        this.bondingTimeout = bondingTimeout;
    }

    @Override
    public void tryBonding(Player player) {

        if(player.isCreative()) {
            tameBeast(player);
            this.level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);
            this.setChargeTimeout(0);
        }
        else if(this.getBondingTimeout() <= 0) {
            if(random.nextFloat() <= 0.4f) {
                this.bondingTries++;
                if(bondingTries == 3) {
                    tameBeast(player);
                    this.level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);
                    this.setChargeTimeout(0);
                }
            }
            player.getItemInHand(player.getUsedItemHand()).shrink(1);
            this.setBondingTimeout(40);

        }
    }

    public void throwAttack() {
        Entity target = this.getTarget();
        if(target instanceof Player player) {
            if(PlayerDataService.getPlayerDisposition(player, level()) == this.getDisposition()){
                return;
            }
        }

        if(target != null && !this.level().isClientSide) {
            this.setThrowing(false);

            Vec3 rotationVec = this.getViewVector(1.0f);
            BoulderEntity boulder = new BoulderEntity(EntitiesME.BOULDER, this.level());
            double x = target.getX() - this.getX();
            double y = target.getY(0.3333333333333333) - boulder.getY();
            double z = target.getZ() - this.getZ();
            double c = Math.sqrt(x * x + z * z);

            boulder.setPos(this.getX() + rotationVec.x * 2.0f, this.getY(0.75f), boulder.getZ() + rotationVec.z * 2.0f);
            boulder.shoot(x * 0.8d, y + c * 0.3d , z * 0.8d, 1.0f, 8 - this.level().getDifficulty().getId() * 4);
            if(boulder != null) {
                this.level().addFreshEntity(boulder);
            }
        }
    }

    @Override
    public void chargeAttack() {
        if(!this.isTamed() && !this.level().isClientSide) {
            if(targetDir == Vec3.ZERO && this.getTarget() != null) {
                targetDir = new Vec3( this.getTarget().blockPosition().getX() - this.blockPosition().getX(),
                        this.getTarget().blockPosition().getY() - this.blockPosition().getY(),
                        this.getTarget().blockPosition().getZ() - this.blockPosition().getZ());
            }
            this.setYRot((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
            this.setDeltaMovement(targetDir.multiply(1,0,1).normalize().scale(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getDeltaMovement().y, 0));
        }
        else if (this.level().isClientSide) {
            this.setDeltaMovement(this.getLookAngle().multiply(1,0,1).normalize().scale(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getDeltaMovement().y, 0));
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            Entity owner = this.getOwner();
            List<Entity> entities = serverLevel.getEntities(
                    this,
                    this.getBoundingBox().inflate(0.2f, 0.0, 0.2f),
                    entity -> entity != owner && !this.getPassengers().contains(entity)
            );
            for(Entity entity : entities) {
                entity.hurt(entity.damageSources().mobAttack(this), 16.0f);
            }
        }
        this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfStopped(this.tickCount);
    }
}
