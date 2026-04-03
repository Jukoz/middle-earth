package net.sevenstars.middleearth.entity.beasts;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.inventory.StackWithSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.DispositionType;
import net.sevenstars.middleearth.resources.datas.RaceType;

import java.util.List;

public abstract class AbstractBeastEntity extends AbstractHorseEntity {
    public static final TrackedData<Boolean> CHARGING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SITTING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RUNNING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> FIGHTING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    // The tameness value ranges from 0-100, updating every in-game day. If it reaches 0, the beast will break free from its owner.
    private static final TrackedData<Integer> TAMENESS = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.INTEGER);

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
    protected Vec3d targetDir = Vec3d.ZERO;

    // region Initializing
    protected AbstractBeastEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(CHARGING, false);
        builder.add(SITTING, false);
        builder.add(CHEST, false);
        builder.add(RUNNING, false);
        builder.add(FIGHTING, false);
        builder.add(TAMENESS, 75);
    }

    @Override
    protected void initGoals() {
    }

    protected abstract void setupAnimationStates();

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (!this.firstUpdate && CHARGING.equals(data)) {
            this.chargeTimeout = this.chargeTimeout == 0 ? maxChargeCooldown() : this.chargeTimeout;
        }
        super.onTrackedDataSet(data);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putBoolean("Sitting", this.isSitting());
        view.putBoolean("ChestedBeast", this.hasChest());
        view.putInt("Tameness", this.getTameness());
        if (this.hasChest()) {
            WriteView.ListAppender<StackWithSlot> listAppender = view.getListAppender("Items", StackWithSlot.CODEC);

            for (int i = 0; i < this.items.size(); i++) {
                ItemStack itemStack = this.items.getStack(i);
                if (!itemStack.isEmpty()) {
                    listAppender.add(new StackWithSlot(i, itemStack));
                }
            }
        }
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.setSitting(view.getBoolean("Sitting", false));
        this.setHasChest(view.getBoolean("ChestedBeast", false));
        this.setTameness(view.getInt("Tameness", 75));
        this.onChestedStatusChanged();
        if (this.hasChest()) {
            for (StackWithSlot stackWithSlot : view.getTypedListView("Items", StackWithSlot.CODEC)) {
                if (stackWithSlot.isValidSlot(this.items.size())) {
                    this.items.setStack(stackWithSlot.slot(), stackWithSlot.stack());
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

    protected boolean isTamable() {
        return true;
    }

    public boolean canCarryChest() {
        return false;
    }

    public final boolean cannotFollowOwner() {
        return this.isSitting() || this.hasVehicle() || this.mightBeLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
    }

    public boolean shouldAttackWhenMounted() {
        return false;
    }

    public boolean canCharge() {
        return !this.isSitting() && !this.hasPassengers();
    }

    @Override
    public boolean isPersistent() {
        return isTame() || getTameness() <= 0;
    }

    protected boolean isClientWorld() {
        return this.getWorld().isClient();
    }

    public boolean isOwner(LivingEntity entity) {
        return this.getOwner() != null && this.getOwner() == entity;
    }
    // endregion

    // region DataTracker

    public int getTameness() {
        return this.dataTracker.get(TAMENESS);
    }
    public void setTameness(int tameness) {
        this.dataTracker.set(TAMENESS, tameness);
    }

    public boolean hasChest() {
        return this.dataTracker.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.dataTracker.set(CHEST, hasChest);
    }

    public boolean isRunning() {
        return this.dataTracker.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.dataTracker.set(RUNNING, running);
    }

    public boolean isCharging() {
        return this.dataTracker.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.dataTracker.set(CHARGING, charging);
    }

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
    }

    public boolean isFighting() {
        return dataTracker.get(FIGHTING);
    }

    public void setFighting(boolean isFighting) {
        dataTracker.set(FIGHTING, isFighting);
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
        float f = Math.min(0.25F, this.limbAnimator.getSpeed());
        float g = this.limbAnimator.getSpeed(); // TODO : was this.limbAnimator.getPos();
        return (double)this.getHeight() - 0.19 + (double)(0.12F * MathHelper.cos(g * 1.5F) * 2.0F * f);
    }

    public PlayerEntity getOwner() {
        if(super.getOwner() instanceof PlayerEntity playerEntity)
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
        return (float)this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
    }

    // endregion

    // region Equipment

    protected void dropInventory(ServerWorld world) {
        super.dropInventory(world);
        if (this.hasChest()) {
            this.dropItem(world, Blocks.CHEST);
            this.setHasChest(false);
        }
    }

    @Override
    public StackReference getStackReference(int mappedIndex) {
        return mappedIndex == 499 ? new StackReference() {
            @Override
            public ItemStack get() {
                return AbstractBeastEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
            }

            @Override
            public boolean set(ItemStack stack) {
                if (stack.isEmpty()) {
                    if (AbstractBeastEntity.this.hasChest()) {
                        AbstractBeastEntity.this.setHasChest(false);
                        AbstractBeastEntity.this.onChestedStatusChanged();
                    }

                    return true;
                } else if (stack.isOf(Items.CHEST)) {
                    if (!AbstractBeastEntity.this.hasChest()) {
                        AbstractBeastEntity.this.setHasChest(true);
                        AbstractBeastEntity.this.onChestedStatusChanged();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } : super.getStackReference(mappedIndex);
    }

    public int getInventoryColumns() {
        return this.hasChest() ? 5 : 0;
    }

    private void addChest(PlayerEntity player, ItemStack chest) {
        this.setHasChest(true);
        this.playAddChestSound();
        chest.decrementUnlessCreative(1, player);
        this.onChestedStatusChanged();
    }

    protected void playAddChestSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    // endregion

    // region Move Set and Behavior

    public void breakFree() {
        this.setTame(false);
        this.setOwner(null);

        if(this.getBrain() != null) {
            this.getBrain().forget(MemoryModulesME.TAME);
        }
    }

    @Override
    protected void jump(float strength, Vec3d movementInput) {
        if(this.isSitting()) {
            this.setSitting(false);
        }
        else if(this.chargeTimeout <= 0) {
            this.setCharging(true);
            this.chargeTimeout = maxChargeCooldown();
        }
    }

    @Override
    public boolean isAngry() {
        return false;
    }

    @Override
    public void startJumping(int height) {
        if(!this.isSitting()) {
            this.playSound(SoundEvents.ENTITY_CAMEL_DASH, 1.0f, 1.0f);
            this.setCharging(true);
        }
        else {
            this.setSitting(false);
        }
    }

    public void tryBonding(PlayerEntity player) {
        if(random.nextDouble() <= 0.1d || player.isInCreativeMode()) {
            this.tameBeast(player);
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeTimeout = 0;
        }
        else {
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    public void tameBeast(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            this.setOwner(player);
            this.setTame(true);
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }
    }

    public void tameBeast(LivingEntity livingEntity) {
        this.setOwner(livingEntity);
        this.setTame(true);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(isBondingItem(player.getStackInHand(hand)) && !this.isTame() && this.isTamable()) {
            if(!this.getWorld().isClient()) {
                this.tryBonding(player);
                this.eat(player, hand, itemStack);
            }
            return ActionResult.SUCCESS;
        }

        if(this.isTame()) {
            if(isFoodItem(itemStack) && getOwner() == player) {
                int tamenessIncrease;

                FoodComponent component = itemStack.get(DataComponentTypes.FOOD);
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

                this.eat(player, hand, itemStack);
                playSound(SoundEvents.ENTITY_HORSE_EAT);
                return ActionResult.SUCCESS;
            }

            if(isCommandItem(itemStack) && player == getOwner()) {
                this.setSitting(!isSitting());
                return ActionResult.SUCCESS;
            }

            if (!this.hasChest() && itemStack.isOf(Items.CHEST) && canCarryChest()) {
                this.addChest(player, itemStack);
                return ActionResult.SUCCESS;
            }

            if(!(isCommandItem(itemStack) || isBreedingItem(itemStack) || itemStack.isOf(Items.CHEST)) && this.isMountable()) {
                super.interactMob(player, hand);
            }
        }

        return ActionResult.FAIL;
    }

    @Override
    public ActionResult interactHorse(PlayerEntity player, ItemStack stack) {
        return ActionResult.PASS;
    }

    @Override
    protected void putPlayerOnBack(PlayerEntity player) {
        ItemStack item = player.getStackInHand(Hand.MAIN_HAND);
        if(this.canAddPassenger(player) && item.isEmpty()) {
            super.putPlayerOnBack(player);
        }
    }

    public boolean damage(DamageSource source, float amount) {
        if(!source.equals(getDamageSources().drown()) && !source.equals(getDamageSources().lava())
                && !source.equals(getDamageSources().cramming()) && !source.equals(getDamageSources().magic())) {
            amount *= (1 - RESISTANCE);
        }
        return !this.getWorld().isClient() && super.damage((ServerWorld) this.getWorld(), source, amount);
    }

    public void chargeAttack() {
    }

    public void setChargeVelocity(Vec3d direction) {
        this.setVelocity(direction
                .getHorizontal().normalize() // Remove the y-axis and normalize the vector
                .multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())) // Progressively get faster during charge (linear)
                .add(0, this.getVelocity().y, 0)); // Add y-Velocity to make beast fall and climb steps
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new BeastEntityNavigation(this, world);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return this.isSitting() ? 0 : super.getSaddledSpeed(controllingPlayer);
    }

    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        return this.isSitting() ? 0 : (float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
    }

    // endregion

    // region Tick Management
    @Override
    public void tick() {
        super.tick();

        if(this.getTarget() != null) {
            this.getLookControl().lookAt(this.getTarget());
        }

        if(this.isCharging()) {
            chargeAttack();
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }
        }
        if(this.chargeTimeout <= (maxChargeCooldown() - chargeDuration()) || !isCharging()) {
            this.setCharging(false);
            this.targetDir = Vec3d.ZERO;
        }
        if(!this.isCharging()) {
            this.chargeAnimationState.stop();
        }
        if(chargeTimeout > 0) {
            --this.chargeTimeout;
        }

        if(this.hasControllingPassenger() && !this.shouldAttackWhenMounted()) {
            this.setAttacker(null);
            this.setAttacking(false);
            this.setTarget(null);
        }

        if (this.getWorld().isClient) {
            setupAnimationStates();
        }

        if (!this.isClientWorld() && isTame()) {
            if(this.getWorld().getTimeOfDay() == 6500) { // Tameness always decreases shortly after noon
                List<? extends PlayerEntity> players = this.getWorld().getPlayers();
                if(this.getOwner() != null && players.contains(this.getOwner())) { // Check if owner is online
                    // Get amount of other beasts using the Tameness mechanic in a 25 block radius. The tameness decreases exponentially faster for each of them.
                    int affectingBeasts = this.getWorld().getEntitiesByClass(AbstractBeastEntity.class, this.getBoundingBox().expand(25), (entity) -> usesTameness() && getOwner() == this.getOwner()).size();

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
    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }

        if (this.getControllingPassenger() instanceof NpcEntity npc && this.isAlive()) {
            Vec3d movementInput = new Vec3d(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed);
            if (this.canMoveVoluntarily()) {
                this.setMovementSpeed(this.getNpcSaddledSpeed(npc));
                this.travel(movementInput);
            } else {
                this.setVelocity(Vec3d.ZERO);
            }
        }
    }

    // endregion

    protected void setChildAttribute(PassiveEntity other, AbstractHorseEntity child, RegistryEntry<EntityAttribute> attribute, double min, double max) {
        double d = this.calculateAttributeBaseValue(this.getAttributeBaseValue(attribute), other.getAttributeBaseValue(attribute), min, max, this.random);
        child.getAttributeInstance(attribute).setBaseValue(d);
    }

    static double calculateAttributeBaseValue(double parentBase, double otherParentBase, double min, double max, Random random) {
        double g;
        if (max <= min) {
            throw new IllegalArgumentException("Incorrect range for an attribute");
        }
        parentBase = MathHelper.clamp(parentBase, min, max);
        otherParentBase = MathHelper.clamp(otherParentBase, min, max);
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
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
            this.attackTicksLeft = ATTACK_COOLDOWN;
            this.attackAnimationState.start(this.age);
        }
        if (status == EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES) {
            this.spawnPlayerReactionParticles(true);
        } else if (status == EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES) {
            this.spawnPlayerReactionParticles(false);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f, 1.0f);
    }
}
