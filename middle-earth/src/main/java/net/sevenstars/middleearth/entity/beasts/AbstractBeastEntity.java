package net.sevenstars.middleearth.entity.beasts;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractBeastEntity extends AbstractHorse {
    public static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHEST = SynchedEntityData.defineId(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FIGHTING = SynchedEntityData.defineId(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);

    // The tameness value ranges from 0-100, updating every in-game day. If it reaches 0, the beast will break free from its owner.
    private static final EntityDataAccessor<Integer> TAMENESS = SynchedEntityData.defineId(AbstractBeastEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState startSittingAnimationState = new AnimationState();
    public final AnimationState stopSittingAnimationState = new AnimationState();

    protected int idleAnimationTimeout = 1000;
    protected int attackTicksLeft = 0;
    protected boolean hasCharged = false;

    protected int chargeTimeout; // ticking cooldown of the charge attack

    public static final int ATTACK_COOLDOWN = 10;
    public static final float RESISTANCE = 0.15f;
    protected Vec3 targetDir = Vec3.ZERO;

    // region Initializing
    protected AbstractBeastEntity(EntityType<? extends AbstractBeastEntity> entityType, Level world) {
        super(entityType, world);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHARGING, false);
        builder.define(SITTING, false);
        builder.define(CHEST, false);
        builder.define(RUNNING, false);
        builder.define(FIGHTING, false);
        builder.define(TAMENESS, 75);
    }

    @Override
    protected void registerGoals() {
    }

    protected abstract void setupAnimationStates();

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        if (!this.firstTick && CHARGING.equals(data)) {
            this.chargeTimeout = this.chargeTimeout == 0 ? maxChargeCooldown() : this.chargeTimeout;
        }
        super.onSyncedDataUpdated(data);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        view.putBoolean("Sitting", this.isSitting());
        view.putBoolean("ChestedBeast", this.hasChest());
        view.putInt("Tameness", this.getTameness());
        if (this.hasChest()) {
            ListTag items = new ListTag();
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
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
        this.setSitting(view.getBoolean("Sitting"));
        this.setHasChest(view.getBoolean("ChestedBeast"));
        this.setTameness(view.contains("Tameness", Tag.TAG_INT) ? view.getInt("Tameness") : 75);
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
    }

    // endregion

    // region Conditions
    public abstract DispositionType getDisposition();

    public abstract List<RaceType> getCompatibleRaces();

    public abstract boolean usesTameness();

    public abstract boolean isCommandItem(ItemStack stack);
    public abstract boolean isBondingItem(ItemStack itemStack);
    public abstract boolean isFoodItem(ItemStack itemStack);

    public boolean isMountable() {
        return true;
    }

    protected boolean isTamable(Player player) {
        return true;
    }

    public boolean canCarryChest() {
        return false;
    }

    public final boolean cannotFollowOwner() {
        return this.isSitting() || this.isPassenger() || this.mayBeLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
    }

    public boolean shouldAttackWhenMounted() {
        return false;
    }

    public boolean canCharge() {
        return !this.isSitting() && !this.isVehicle();
    }

    @Override
    public boolean isPersistenceRequired() {
        return isTamed() || getTameness() <= 0;
    }

    public void resetTameness(){
        this.setTameness(75);
    }

    protected boolean isClientWorld() {
        return this.level().isClientSide();
    }

    public boolean isOwner(LivingEntity entity) {
        return this.getOwner() != null && this.getOwner() == entity;
    }

    /**
     * Checks an entity for beast-specific targeting criteria (LivingEntity, not a passenger, not its owner, not a creative player)
     * @param entity
     * @return isValidTarget
     */
    public boolean isValidTarget(Entity entity) {
        return entity instanceof LivingEntity livingEntity &&                           // Entity is LivingEntity
                !this.getPassengers().contains(livingEntity) &&                      // Is not a passenger
                !(this.getOwner() != null && this.getOwner().equals(livingEntity)) &&   // Is not its owner
                !(livingEntity instanceof Player player && player.isCreative());  // Is not a creative player
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypes.IN_WALL) && this.isVehicle() && getControllingPassenger() instanceof NpcEntity) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }
    // endregion

    // region DataTracker

    public int getTameness() {
        return this.entityData.get(TAMENESS);
    }
    public void setTameness(int tameness) {
        this.entityData.set(TAMENESS, tameness);
    }

    public boolean hasChest() {
        return this.entityData.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.entityData.set(CHEST, hasChest);
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }

    public boolean isFighting() {
        return entityData.get(FIGHTING);
    }

    public void setFighting(boolean isFighting) {
        entityData.set(FIGHTING, isFighting);
    }

    // endregion

    // region Non-tracked Getters and Setters
    public boolean hasCharged() {
        return hasCharged;
    }

    public void setHasCharged(boolean hasCharged) {
        this.hasCharged = hasCharged;
    }

    public int getChargeTimeout() {
        return this.chargeTimeout;
    }

    public void setChargeTimeout(int chargeTimeout) {
        this.chargeTimeout = chargeTimeout;
    }

    @Override
    public int getJumpCooldown() {
        return this.chargeTimeout;
    }

    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.walkAnimation.speed());
        float g = this.walkAnimation.speed(); // TODO : was this.limbAnimator.getPos();
        return (double)this.getBbHeight() - 0.19 + (double)(0.12F * Mth.cos(g * 1.5F) * 2.0F * f);
    }

    public Player getOwner() {
        if(super.getOwner() instanceof Player playerEntity)
            return playerEntity;
        return null;
    }

    public int maxChargeCooldown() {
        return 400;
    }
    public int chargeDuration() {
        return 20;
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    // endregion

    // region Equipment

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.hasChest()) {
            this.spawnAtLocation(Blocks.CHEST);
            this.setHasChest(false);
        }
    }

    @Override
    public SlotAccess getSlot(int mappedIndex) {
        return mappedIndex == 499 ? new SlotAccess() {
            @Override
            public ItemStack get() {
                return AbstractBeastEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
            }

            @Override
            public boolean set(ItemStack stack) {
                if (stack.isEmpty()) {
                    if (AbstractBeastEntity.this.hasChest()) {
                        AbstractBeastEntity.this.setHasChest(false);
                        AbstractBeastEntity.this.createInventory();
                    }

                    return true;
                } else if (stack.is(Items.CHEST)) {
                    if (!AbstractBeastEntity.this.hasChest()) {
                        AbstractBeastEntity.this.setHasChest(true);
                        AbstractBeastEntity.this.createInventory();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } : super.getSlot(mappedIndex);
    }

    public int getInventoryColumns() {
        return this.hasChest() ? 5 : 0;
    }

    private void addChest(Player player, ItemStack chest) {
        this.setHasChest(true);
        this.playAddChestSound();
        chest.consume(1, player);
        this.createInventory();
    }

    protected void playAddChestSound() {
        this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    // endregion

    // region Move Set and Behavior

    public void breakFree() {
        this.setTamed(false);
        this.setOwner(null);
        this.setSitting(false);

        if(this.getBrain() != null) {
            this.getBrain().eraseMemory(MemoryModulesME.TAME);
        }
    }

    @Override
    protected void executeRidersJump(float strength, Vec3 movementInput) {
        if(this.isSitting()) {
            this.setSitting(false);
        }
        else if(this.chargeTimeout <= 0) {
            this.setCharging(true);
            this.chargeTimeout = maxChargeCooldown();
        }
    }

    @Override
    public boolean isStanding() {
        return false;
    }

    @Override
    public void handleStartJump(int height) {
        if(!this.isSitting()) {
            this.playSound(SoundEvents.CAMEL_DASH, 1.0f, 1.0f);
            this.setCharging(true);
        }
        else {
            this.setSitting(false);
        }
    }

    public void tryBonding(Player player) {
        if(random.nextDouble() <= 0.1d || player.hasInfiniteMaterials()) {
            this.tameBeast(player);
            this.level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);

            this.chargeTimeout = 0;
        }
        else {
            this.level().broadcastEntityEvent(this, EntityEvent.TAMING_FAILED);
        }
    }

    public void tameBeast(Player player) {
        if (player instanceof ServerPlayer) {
            this.setOwner(player);
            this.setTamed(true);
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
        }
    }

    public void tameBeast(LivingEntity livingEntity) {
        this.setOwner(livingEntity);
        this.setTamed(true);
    }

    public void setOwner(@Nullable LivingEntity entity) {
        this.setOwnerUUID(entity == null ? null : entity.getUUID());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(isBondingItem(player.getItemInHand(hand)) && !this.isTamed() && this.isTamable(player)) {
            if(!this.level().isClientSide()) {
                this.tryBonding(player);
                this.usePlayerItem(player, hand, itemStack);
            }
            return InteractionResult.SUCCESS;
        }

        if(this.isTamed()) {
            if(isFoodItem(itemStack) && getOwner() == player) {
                int tamenessIncrease;

                FoodProperties component = itemStack.get(DataComponents.FOOD);
                if(component != null) {
                    tamenessIncrease = component.nutrition();
                }
                else {
                    tamenessIncrease = 4;
                }

                this.setTameness(this.getTameness() + tamenessIncrease);
                if(this.getTameness() > 100) {
                    this.setTameness(100);
                }

                this.usePlayerItem(player, hand, itemStack);
                makeSound(SoundEvents.HORSE_EAT);
                return InteractionResult.SUCCESS;
            }

            if(isCommandItem(itemStack) && player == getOwner()) {
                this.setSitting(!isSitting());
                return InteractionResult.SUCCESS;
            }

            if (!this.hasChest() && itemStack.is(Items.CHEST) && canCarryChest()) {
                this.addChest(player, itemStack);
                return InteractionResult.SUCCESS;
            }

            if(!(isCommandItem(itemStack) || isFood(itemStack) || itemStack.is(Items.CHEST)) && this.isMountable()) {
                super.mobInteract(player, hand);
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResult fedFood(Player player, ItemStack stack) {
        return InteractionResult.PASS;
    }

    @Override
    protected void doPlayerRide(Player player) {
        ItemStack item = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(this.canAddPassenger(player) && item.isEmpty()) {
            super.doPlayerRide(player);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(!source.equals(damageSources().drown()) && !source.equals(damageSources().lava())
                && !source.equals(damageSources().cramming()) && !source.equals(damageSources().magic())) {
            amount *= (1 - RESISTANCE);
        }
        return super.hurt(source, amount);
    }

    public void chargeAttack() {
    }

    public void setChargeVelocity(Vec3 direction) {
        this.setDeltaMovement(new Vec3(direction.x, 0.0, direction.z)
                .normalize()
                .scale(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())) // Progressively get faster during charge (linear)
                .add(0, this.getDeltaMovement().y, 0)); // Add y-Velocity to make beast fall and climb steps
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new BeastEntityNavigation(this, world);
    }

    @Override
    protected float getRiddenSpeed(Player controllingPlayer) {
        return this.isSitting() ? 0 : super.getRiddenSpeed(controllingPlayer);
    }

    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        return this.isSitting() ? 0 : (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    // endregion

    // region Tick Management
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
        if(this.chargeTimeout <= (maxChargeCooldown() - chargeDuration()) || !isCharging()) {
            this.setCharging(false);
            this.targetDir = Vec3.ZERO;
        }
        if(!this.isCharging()) {
            this.chargeAnimationState.stop();
        }
        if(chargeTimeout > 0) {
            --this.chargeTimeout;
        }

        if(this.hasControllingPassenger() && !this.shouldAttackWhenMounted()) {
            this.setLastHurtByMob(null);
            this.setAggressive(false);
            this.setTarget(null);
        }

        if (this.level().isClientSide) {
            setupAnimationStates();
        }

        if (!this.isClientWorld() && isTamed() && this.getOwner() != null) {
            if(this.level().getDayTime() == 6500) { // Tameness always decreases shortly after noon
                List<? extends Player> players = this.level().players();
                if(this.getOwner() != null && players.contains(this.getOwner())) { // Check if owner is online
                    // Get amount of other beasts using the Tameness mechanic in a 25 block radius. The tameness decreases exponentially faster for each of them.
                    int affectingBeasts = this.level().getEntitiesOfClass(AbstractBeastEntity.class, this.getBoundingBox().inflate(25), (entity) -> usesTameness() && getOwner() == this.getOwner()).size();

                    // The number of entities includes itself, therefore the smallest value to decrease is at 10
                    this.setTameness(this.getTameness() - (int)(5 * Math.pow(2, affectingBeasts)));

                    if(this.getTameness() <= 0) { // Tameness is 0, break free
                        this.breakFree();
                    }
                }
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }

        if (this.getControllingPassenger() instanceof NpcEntity npc && this.isAlive()) {
            Vec3 movementInput = new Vec3(this.xxa, this.yya, this.zza);
            if (this.isControlledByLocalInstance()) {
                this.setSpeed(this.getNpcSaddledSpeed(npc));
                this.travel(movementInput);
            } else {
                this.setDeltaMovement(Vec3.ZERO);
            }
        }
    }

    // endregion

    protected void setOffspringAttribute(AgeableMob other, AbstractHorse child, Holder<Attribute> attribute, double min, double max) {
        double d = this.createOffspringAttribute(this.getAttributeBaseValue(attribute), other.getAttributeBaseValue(attribute), min, max, this.random);
        child.getAttribute(attribute).setBaseValue(d);
    }

    static double createOffspringAttribute(double parentBase, double otherParentBase, double min, double max, RandomSource random) {
        double g;
        if (max <= min) {
            throw new IllegalArgumentException("Incorrect range for an attribute");
        }
        parentBase = Mth.clamp(parentBase, min, max);
        otherParentBase = Mth.clamp(otherParentBase, min, max);
        double d = 0.15 * (max - min);
        double f = (parentBase + otherParentBase) / 2.0;
        double e = Math.abs(parentBase - otherParentBase) + d * 2.0;
        double h = f + e * (g = (random.nextDouble() + random.nextDouble() + random.nextDouble()) / 3.0 - 0.5);
        if (h > max) {
            double i = h - max;
            return max - i;
        }
        if (h < min) {
            double i = min - h;
            return min + i;
        }
        return h;
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == EntityEvent.START_ATTACKING) {
            this.attackTicksLeft = ATTACK_COOLDOWN;
            this.attackAnimationState.start(this.tickCount);
        }
        if (status == EntityEvent.TAMING_SUCCEEDED) {
            this.spawnTamingParticles(true);
        } else if (status == EntityEvent.TAMING_FAILED) {
            this.spawnTamingParticles(false);
        } else {
            super.handleEntityEvent(status);
        }
    }

    @Override
    protected void updateWalkAnimation(float posDelta) {
        float f = this.getPose() == Pose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.walkAnimation.update(f, 0.2f);
    }
}
