package net.sevenstars.middleearth.entity.beasts;

import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

// Beasts are mostly aggressive Entities which work much like wolves, while also allowing the player to mount them.
public class AbstractBeastEntity extends AbstractHorseEntity {
    public static final TrackedData<Boolean> CHARGING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SITTING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RUNNING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState startSittingAnimationState = new AnimationState();
    public final AnimationState stopSittingAnimationState = new AnimationState();

    protected int idleAnimationTimeout = 1000;
    protected int attackTicksLeft = 0;
    protected boolean hasCharged = false;
    protected boolean startedSitting = false;

    protected int chargeTimeout; // ticking cooldown of the charge attack

    public static final int ATTACK_COOLDOWN = 10;
    public static final float RESISTANCE = 0.15f;
    protected Vec3d targetDir = Vec3d.ZERO;

    // Initializing ====================================================================================================
    protected AbstractBeastEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(CHARGING, false);
        builder.add(SITTING, false);
        builder.add(CHEST, false);
        builder.add(RUNNING, false);
    }

    @Override
    protected void initAttributes(Random random) {

    }

    protected void setupAnimationStates() {

    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (!this.firstUpdate && CHARGING.equals(data)) {
            this.chargeTimeout = this.chargeTimeout == 0 ? maxChargeCooldown() : this.chargeTimeout;
        }
        super.onTrackedDataSet(data);
    }

    private StackReference createInventoryStackReference(final int slot, final Predicate<ItemStack> predicate) {
        return new StackReference(){

            @Override
            public ItemStack get() {
                return AbstractBeastEntity.this.items.getStack(slot);
            }

            @Override
            public boolean set(ItemStack stack) {
                if (!predicate.test(stack)) {
                    return false;
                }
                AbstractBeastEntity.this.items.setStack(slot, stack);
                AbstractBeastEntity.this.updateSaddledFlag();
                return true;
            }
        };
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sitting", this.isSitting());
        nbt.putBoolean("ChestedBeast", this.hasChest());
        if (this.hasChest()) {
            NbtList nbtList = new NbtList();
            for(int i = 2; i < this.items.size(); ++i) {
                ItemStack itemStack = this.items.getStack(i);
                if (!itemStack.isEmpty()) {
                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.putByte("Slot", (byte)i);
                    nbtList.add(itemStack.encode(this.getRegistryManager(), nbtCompound));
                }
            }
            nbt.put("Items", nbtList);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSitting(nbt.getBoolean("Sitting"));
        this.setHasChest(nbt.getBoolean("ChestedBeast"));
        this.onChestedStatusChanged();
        if (this.hasChest()) {
            NbtList nbtList = nbt.getList("Items", 10);

            for(int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                int j = nbtCompound.getByte("Slot") & 255;
                if (j >= 2 && j < this.items.size()) {
                    this.items.setStack(j, (ItemStack)ItemStack.fromNbt(this.getRegistryManager(), nbtCompound).orElse(ItemStack.EMPTY));
                }
            }
        }
        this.updateSaddledFlag();
    }

    // Getters and Setters =============================================================================================
    protected boolean isMountable() {
        return true;
    }
    protected boolean isTamable() {
        return true;
    }

    protected Disposition getDisposition(){
        return null;
    }

    protected List<RaceType> getRaceType() {
        return null;
    }

    public boolean hasChest() {
        return this.dataTracker.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.dataTracker.set(CHEST, hasChest);
    }

    public boolean canCarryChest() {
        return true;
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

    public boolean isRunning() {
        return this.dataTracker.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.dataTracker.set(RUNNING, running);
    }

    @Override
    public int getJumpCooldown() {
        return this.chargeTimeout;
    }

    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.limbAnimator.getSpeed());
        float g = this.limbAnimator.getPos();
        return (double)this.getHeight() - 0.19 + (double)(0.12F * MathHelper.cos(g * 1.5F) * 2.0F * f);
    }

    public PlayerEntity getOwner() {
        if(this.getOwnerUuid() != null) {
            return getPlayerByUuid(this.getOwnerUuid());
        }
        return null;
    }

    public boolean hasCharged() {
        return hasCharged;
    }

    public void setHasCharged(boolean hasCharged) {
        this.hasCharged = hasCharged;
    }

    @Override
    public boolean isPersistent() {
        return isTame();
    }

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
    }

    public boolean isCommandItem(ItemStack stack) {
        return false;
    }

    public void setCharging(boolean charging) {
        this.dataTracker.set(CHARGING, charging);
    }

    public boolean isCharging() {
        return this.dataTracker.get(CHARGING);
    }

    public int getChargeTimeout() {
        return this.chargeTimeout;
    }
    public void setChargeTimeout(int chargeTimeout) {
        this.chargeTimeout = chargeTimeout;
    }

    public int maxChargeCooldown() {
        return 400;
    }
    public int chargeDuration() {
        return 20;
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    // Equipment =======================================================================================================

    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            if (!this.getWorld().isClient) {
                this.dropItem(Blocks.CHEST);
            }

            this.setHasChest(false);
        }
    }

    @Override
    public StackReference getStackReference(int mappedIndex) {
        int j;
        int i = mappedIndex - 400;
        if (i >= 0 && i < 2 && i < this.items.size()) {
            if (i == 0) {
                return this.createInventoryStackReference(i, stack -> stack.isEmpty() || stack.isOf(Items.SADDLE));
            }
            if (i == 1) {
                return StackReference.EMPTY;
            }
        }
        if ((j = mappedIndex - 500 + 2) >= 2 && j < this.items.size()) {
            return StackReference.of(this.items, j);
        }
        return super.getStackReference(mappedIndex);
    }

    public int getInventoryColumns() {
        return this.hasChest() ? 5 : 0;
    }

    private void addChest(PlayerEntity player, ItemStack chest) {
        if(canCarryChest()) {
            this.setHasChest(true);
            this.playAddChestSound();
            if (!player.getAbilities().creativeMode) {
                chest.decrement(1);
            }
            this.onChestedStatusChanged();
        }
    }

    protected void playAddChestSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public SoundEvent getSaddleSound() {
        return super.getSaddleSound();
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return this.isSitting() ? 0 : super.getSaddledSpeed(controllingPlayer);
    }

    // Move Set and Behavior ===========================================================================================
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

    protected void tameBeast(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            this.setOwnerUuid(player.getUuid());
            this.setTame(true);
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = !this.isBaby() && this.isTame() && player.shouldCancelInteraction();

        ItemStack itemStack = player.getStackInHand(hand);

        if(isBondingItem(player.getStackInHand(hand)) && !this.isTame() && this.isTamable()) {
            if(!this.getWorld().isClient()) {
                this.tryBonding(player);
                this.eat(player, hand, itemStack);
            }
            return ActionResult.success(this.getWorld().isClient());
        }

        if(this.isTame() && this.isTamable()) {
            if(isCommandItem(itemStack) && player == getOwner()) {
                this.setSitting(!isSitting());
                return ActionResult.SUCCESS;
            }

            if (itemStack.isOf(Items.CHEST) && !this.hasChest()) {
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
        return super.interactHorse(player, stack);
    }

    public boolean isBondingItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!source.equals(getDamageSources().drown()) && !source.equals(getDamageSources().lava())
                && !source.equals(getDamageSources().cramming()) && !source.equals(getDamageSources().magic())) {
            amount *= (1 - RESISTANCE);
        }
        return super.damage(source, amount);
    }

    public void chargeAttack() {
    }

    // Tick Management =================================================================================================
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
            this.setAttacking(null);
            this.setTarget(null);
        }

        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }
    }

    // =================================================================================================================

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

    public PlayerEntity getPlayerByUuid(UUID uuid) {
        for (int i = 0; i < this.getWorld().getPlayers().size(); ++i) {
            PlayerEntity playerEntity = this.getWorld().getPlayers().get(i);
            if (!uuid.equals(playerEntity.getUuid())) continue;
            return playerEntity;
        }
        return null;
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WARDEN_STEP, 0.15F, 2.0F);
    }

    @Override
    public boolean cannotBeSilenced() {
        return super.cannotBeSilenced();
    }
}
