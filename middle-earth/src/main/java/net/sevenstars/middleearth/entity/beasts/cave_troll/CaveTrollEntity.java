package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.spider.Pouncer;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.sound.SoundsME;
import net.sevenstars.middleearth.utils.PlayerUtil;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// TODO Add sounds
public class CaveTrollEntity extends AbstractBeastEntity {
    public LootTable scavengeLootTable;
    public LootParams lootWorldContext;
    private float smashingStrength; // Used in server-side only
    private float smashingTime; // Used in server-side only
    private float enragedTime; // Used in server-side only
    public static final EntityDataAccessor<Boolean> SCAVENGING = SynchedEntityData.defineId(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ROARING = SynchedEntityData.defineId(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SMASHING = SynchedEntityData.defineId(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ENRAGED = SynchedEntityData.defineId(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CaveTrollEntity.class, EntityDataSerializers.INT);
    public final AnimationState chaseAnimationState = new AnimationState();
    public final AnimationState scavengingAnimationState = new AnimationState();
    public final AnimationState startSleepingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState stopSleepingAnimationState = new AnimationState();
    public final AnimationState roaringAnimationState = new AnimationState();
    public final AnimationState smashingAnimationState = new AnimationState();

    public CaveTrollEntity(EntityType<? extends AbstractBeastEntity> entityType, Level world) {
        super(entityType, world);
        if(scavengeLootTable == null && !world.isClientSide()) {
            if(world instanceof ServerLevel serverWorld) {

                LootTable lootTable = serverWorld.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "gameplay/cave_troll_scavenging")));

                if(lootTable != null) {
                    scavengeLootTable = lootTable;

                    lootWorldContext = new LootParams.Builder(serverWorld)
                            .withParameter(LootContextParams.THIS_ENTITY, this)
                            .withParameter(LootContextParams.ORIGIN, this.position())
                            .create(LootContextParamSets.CHEST);
                }

            }
        }
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.MAX_HEALTH, 120.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.ATTACK_SPEED, 0.65)
                .add(Attributes.FOLLOW_RANGE, 28.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.STEP_HEIGHT, 1.25)
                .add(Attributes.FOLLOW_RANGE, 15.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SCAVENGING, false);
        builder.define(ROARING, false);
        builder.define(SLEEPING, false);
        builder.define(SMASHING, false);
        builder.define(ENRAGED, false);
        builder.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        this.entityData.set(VARIANT, view.getInt("Variant"));
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        CaveTrollVariant variant = Util.getRandom(CaveTrollVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    public CaveTrollVariant getVariant() {
        return CaveTrollVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(CaveTrollVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel world = (ServerLevel)this.level();
        ProfilerFiller profiler = this.level().getProfiler();
        profiler.push("caveTrollBrain");
        this.getBrain().tick(world, this);
        profiler.popPush("caveTrollActivityUpdate");
        CaveTrollBrain.updateActivities(this);
        profiler.pop();

        if(!this.isClientWorld() && this.isSunBurnTick()) {
            this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100));
        }

        super.customServerAiStep();
    }

    @Override
    public void tryBonding(Player player) {
        double rand = this.random.nextDouble();

        if(rand < 0.15 || (rand < 0.3 && this.getTameness() <= 0) || player.hasInfiniteMaterials()) { // Tame success, chance is twice as high if the troll is feral
            this.tameBeast(player);
            this.level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);

            this.chargeTimeout = 0;
        }
        else if(rand > 0.7) { // Tame failure (wake up, become enraged)
            this.enragedTime = this.tickCount;
            this.setEnraged(true);
            this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, player);
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200));
            this.level().broadcastEntityEvent(this, EntityEvent.TAMING_FAILED);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(!this.level().isClientSide()) { // Server side
            for(RaceType race : this.getCompatibleRaces()) { // Check for race
                if(PlayerUtil.isOfRace(player, race) || player.isCreative()) {

                    if(isTrollWeapon(itemStack) && isOwner(player) && this.getMainHandItem().isEmpty()) { // Give the troll a weapon
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.copyAndClear());
                        itemStack.consume(1, player);
                        return InteractionResult.SUCCESS;
                    }
                    else if(player.isShiftKeyDown() && itemStack.isEmpty() && isOwner(player) && !this.getMainHandItem().isEmpty()) {  // Take weapon away from troll
                        ItemStack returnedWeapon = this.getMainHandItem().copyAndClear();
                        if (!player.addItem(returnedWeapon)) {
                            player.drop(returnedWeapon, false);
                        }
                        return InteractionResult.SUCCESS;
                    }
                    else if(canAddPassenger(player) && isTamed() && itemStack.isEmpty()) { // Ride if player is compatible and hand is empty
                        doPlayerRide(player);
                        return InteractionResult.SUCCESS;
                    }
                    else if(!itemStack.isEmpty()) {
                        return super.mobInteract(player, hand);
                    }
                }
            }
        }
        else {  // Client side
            if(!itemStack.isEmpty()) {
                return super.mobInteract(player, hand);
            }
        }

        return InteractionResult.PASS; // Player is of incompatible race - don't interact
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public boolean usesTameness() {
        return true;
    }

    public boolean isTrollWeapon(ItemStack itemStack) {
        return itemStack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "troll_weapons")));
    }

    @Override
    protected boolean isTamable(Player player) {
        return this.isSleeping() || player.isCreative();
    }

    @Override
    public boolean isMountable() { // This method only determines whether the entity is mountable via the usual horse method
        return false;
    }

    @Override
    public void tameBeast(Player player) {
        if (player instanceof ServerPlayer) {
            this.tameBeast((LivingEntity) player);
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
        }
    }

    @Override
    public void tameBeast(LivingEntity livingEntity) {
        if(!this.level().isClientSide()) {
            this.setTamed(true);
            this.setTameness(75);
            this.stopSleeping();
            this.getBrain().setMemory(MemoryModulesME.TAME, true);
            this.getBrain().eraseMemory(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN);
            this.getBrain().eraseMemory(MemoryModulesME.FOOD_EATEN_COUNT);
            this.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE);
            this.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
            this.setOwner(livingEntity);
        }

    }

    @Override
    public void aiStep() {
        if(this.level().isClientSide) {
            setupAnimationStates();
        }
        else {
            if(this.getTargetFromBrain() != null && !this.isSprinting()) {
                this.setSprinting(true);
            }
            else if(this.getTargetFromBrain() == null && this.isSprinting()) {
                this.setSprinting(false);
            }

            if(this.isSmashing() && this.hasControllingPassenger()) {
                if(this.tickCount - this.smashingTime > 30) {
                    smashAttack(smashingStrength);
                }
            }

            if(this.isEnraged() && this.tickCount - this.enragedTime > 1200) {
                this.setEnraged(false);
            }
        }

        super.aiStep();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {   // Allow 3 people to ride the troll
        if(this.isSitting()) {
            return false;
        }
        return this.isSaddled() ? getPassengers().size() < 3 : getPassengers().isEmpty();
    }

    @Override
    protected float getRiddenSpeed(Player controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.25f) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.2f);
        }

        return super.getRiddenSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.25f) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.2f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor)  {
        List<Entity> passengerList = this.getPassengers();
        boolean saddled = this.isSaddled();
        boolean sprinting = this.isCharging();

        if(this.getControllingPassenger() != null) {
            sprinting = this.getControllingPassenger().isSprinting() || this.isCharging();
        }

        float animationSpeed = this.walkAnimation.speed();
        float animationProgress = this.walkAnimation.position() * (Mth.PI / 180) * 18;
        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (2f/1.25f) : (10f/1.75f);

        if(passenger.equals(this.getControllingPassenger()) || !saddled) { // Passenger 1 - Controlling ============================================================================
            double y = sprinting ?
                    -Mth.cos(2 * frequency * animationProgress) * 0.06 * animationSpeed + 0.1 : // height when sprinting
                    Mth.sin(2 * frequency * animationProgress) * 0.02; // height when walking

            double side = sprinting ?
                    Mth.sin(frequency * animationProgress - (4f/15f)*Mth.PI) * 0.225 : // side-to-side movement when sprinting
                    Mth.sin(frequency * animationProgress) * 0.04; // side-to-side movement when walking

            double front = sprinting ?
                    0.35 : // front-back movement when sprinting
                    0; // front-back movement when walking

            if(!saddled) {
                y -= 0.32;
                front += 0.5;
            }

            if(this.level().isClientSide() && this.smashingAnimationState.isStarted()) {
                float time = (this.smashingAnimationState.getAccumulatedTime() / 2000.0F) * 2 * Mth.PI; // Goes from 0 to 2Pi over the duration of the animation
                if(this.smashingAnimationState.getAccumulatedTime() < 1000) {
                    front -= Mth.sin(time) * 0.3;
                }
                else {
                    front -= Mth.sin(time) * 2f;
                    y += Mth.sin(time) * 0.3f;
                }

            }

            double x = Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side - Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;
            double z = Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side + Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;

            return super.getPassengerAttachmentPoint(passenger, dimensions, scaleFactor).add(x, y, z);
        }
        else { // Passenger 2 or 3 - Side ==============================================================================
            double y = sprinting ?
                    -Mth.cos(frequency * animationProgress) * 0.06 * animationSpeed : // height when sprinting
                    Mth.sin(frequency * animationProgress) * 0.02; // height when walking

            double side = sprinting ?
                    Mth.sin(frequency * animationProgress - (4f/15f)*Mth.PI) * 0.15 : // side-to-side movement when sprinting
                    Mth.sin(frequency * animationProgress) * 0.04; // side-to-side movement when walking

            double front = sprinting ?
                    0.35 : // front-back movement when sprinting
                    0; // front-back movement when walking

            if(passengerList.size() >= 3 && passenger.equals(passengerList.get(2))) {   // The left passenger (2) moves inverted to the right one (1)
                y = -y;
            }

            y = sprinting ? y + 0.15 : y; // Add offset if sprinting

            if(this.level().isClientSide()) {
                float time = (this.smashingAnimationState.getAccumulatedTime() / 2000.0F) * 2 * Mth.PI; // Goes from 0 to 2Pi over the duration of the animation
                if(this.smashingAnimationState.getAccumulatedTime() < 1000) {
                    front -= Mth.sin(time) * 0.3;
                }
                else {
                    front -= Mth.sin(time) * 1.8f;
                    y += Mth.sin(time) * 0.3f;
                }

            }

            double x = Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side - Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;
            double z = Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side + Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;

            return super.getPassengerAttachmentPoint(passenger, dimensions, scaleFactor).add(x, y, z);
        }
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    protected void setupAnimationStates() {
        if(this.isScavenging()) { // Looking for food
            this.scavengingAnimationState.startIfStopped(this.tickCount);
        }
        else {
            this.scavengingAnimationState.stop();
        }

        if(this.isSitting()) { // Sitting
            this.startSittingAnimationState.startIfStopped(this.tickCount);
        }
        else if(this.startSittingAnimationState.isStarted()) {
            this.startSittingAnimationState.stop();
            this.stopSittingAnimationState.startIfStopped(this.tickCount);
        }
        if(this.stopSittingAnimationState.getAccumulatedTime() > 3000) {
            this.stopSittingAnimationState.stop();
        }

        if(this.isSleeping()) { // Sleeping
            if(!this.startSleepingAnimationState.isStarted() && !this.sleepingAnimationState.isStarted()) {
                this.startSleepingAnimationState.startIfStopped(this.tickCount);
            }
            if (this.startSleepingAnimationState.getAccumulatedTime() > 5000) {
                this.sleepingAnimationState.startIfStopped(this.tickCount);
                this.startSleepingAnimationState.stop();
            }
        }
        else if(this.startSleepingAnimationState.isStarted() || this.sleepingAnimationState.isStarted()) {
            this.startSleepingAnimationState.stop();
            this.sleepingAnimationState.stop();
            this.stopSleepingAnimationState.startIfStopped(this.tickCount);
        }
        if(this.stopSleepingAnimationState.getAccumulatedTime() > 5000) {
            this.stopSleepingAnimationState.stop();
        }

        if(this.isRoaring()) {
            this.roaringAnimationState.startIfStopped(this.tickCount);
        }
        else {
            this.roaringAnimationState.stop();
        }

        if(this.isSmashing()) {
            this.smashingAnimationState.startIfStopped(this.tickCount);
        }
        else if(smashingAnimationState.getAccumulatedTime() >= 2000) {
            smashingAnimationState.stop();
        }
    }

    @Override
    public int chargeDuration() {
        return 30;
    }

    @Override
    public int maxChargeCooldown() {
        return 300;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return getTargetFromBrain();
    }

    @Override
    public void chargeAttack() {
        Vec3 direction = Vec3.ZERO;
        LivingEntity target = this.getTarget();
        int difficulty = this.hasControllingPassenger() ? this.level().getDifficulty().getId() : 0;

        if(!this.isTamed() && !this.level().isClientSide) { // Charge Attack for wild Troll
            if(target != null) { // Has attack target memory
                direction = this.position().vectorTo(target.position()); // Vector from Troll to target entity
            }

            this.setYRot((float) Math.toDegrees(Math.atan2(-direction.x, direction.z))); // Turning the troll into the right direction
            this.setChargeVelocity(direction);
        }
        else if (this.level().isClientSide && this.hasControllingPassenger()) { // Charge Attack for tamed Troll
            this.setChargeVelocity(this.getLookAngle());
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            Entity owner = this.getOwner();
            List<Entity> entities = serverLevel.getEntities(
                    this,
                    this.getBoundingBox().inflate(0.2f, 0.0, 0.2f),
                    entity -> entity != owner && !this.getPassengers().contains(entity)
            );
            for(Entity entity : entities) {
                entity.hurt(entity.damageSources().mobAttack(this), 10.0f + difficulty * 2);
            }
        }
        this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1, 0.1, 1);
    }

    public void smashAttack(float strength) { // Strength goes from 0 to 100
        setSmashing(false);
        AABB box = new AABB(this.position().subtract(5,0,5), this.position().add(5,1,5));

        double weaponDamage = 0;
        ItemAttributeModifiers component = this.getWeaponItem().get(DataComponents.ATTRIBUTE_MODIFIERS);
        if(component != null) {
            for(ItemAttributeModifiers.Entry modifier : component.modifiers()) {
                if(modifier.matches(Attributes.ATTACK_DAMAGE, ResourceLocation.withDefaultNamespace("base_attack_damage"))) {
                    weaponDamage = modifier.modifier().amount();
                }
            }
        }

        Level world = this.level();
        int difficulty = this.hasControllingPassenger() ? world.getDifficulty().getId() : 0;

        if(world instanceof ServerLevel serverWorld) {
            List<Entity> entities = serverWorld.getEntities(this, box, this::isValidTarget);
            for(Entity entity : entities) {
                entity.hurt(this.damageSources().mobAttack(this),  (float)weaponDamage + (strength / 12.5f) + (difficulty * 2));
            }

            for(int x = -5; x <= 5; x++) { // Spawn particles on affected blocks
                for(int z = -5; z <= 5; z++) {
                    BlockParticleOption particles = new BlockParticleOption(ParticleTypes.BLOCK, world.getBlockState(new BlockPos(this.getBlockX() + x, this.getBlockY() - 1, this.getBlockZ() + z)));
                    serverWorld.sendParticles(particles, this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z, 7, 0.5, 0.3, 0.5, 0.2);
                }
            }

            this.playSound(SoundEvents.STONE_BREAK, 1, 0.4f);
            if(weaponDamage > 0) {
                this.playSound(SoundEvents.ANVIL_LAND, 1, 0.1f);
            }
        }
    }

    public float getSecondsToDisableBlocking() {
        return this.isCharging() || this.isSmashing() ? 10.0f : 0f;
    }

    @Override
    protected void executeRidersJump(float strength, Vec3 movementInput) {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            super.executeRidersJump(strength, movementInput);
        }
        else if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            setChargeTimeout(300);
        }
    }

    @Override
    public void handleStartJump(int height) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            if(!this.isSitting()) {
                this.playJumpSound();
                this.setSmashing(true);
                this.smashingTime = this.tickCount;
                this.smashingStrength = height;
            }
            else {
                this.setSitting(false);
            }
        }
        else {
            super.handleStartJump(height);
        }
    }

    public void startSleeping() {
        if (this.isPassenger()) {
            this.stopRiding();
        }

        this.setSleeping(true);
        this.setDeltaMovement(Vec3.ZERO);
        this.hasImpulse = true;

        this.brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void stopSleeping() {
        this.setSleeping(false);
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleeping(boolean isSleeping) {
        this.entityData.set(SLEEPING, isSleeping);
    }

    public boolean isEnraged() {
        return this.entityData.get(ENRAGED);
    }

    public void setEnraged(boolean isEnraged) {
        this.entityData.set(ENRAGED, isEnraged);
    }

    public boolean isSmashing() {
        return this.entityData.get(SMASHING);
    }

    public void setSmashing(boolean isSmashing) {
        this.entityData.set(SMASHING, isSmashing);
    }

    @Override
    public void setSitting(boolean sitting) {
        if(!this.level().isClientSide()) {
            if(sitting) {
                this.getBrain().setMemory(MemoryModulesME.SITTING, true);
                this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            }
            else {
                this.getBrain().eraseMemory(MemoryModulesME.SITTING);
            }

            this.ejectPassengers();
        }

        super.setSitting(sitting);
    }

    public boolean isScavenging() {
        return this.entityData.get(SCAVENGING);
    }

    public void setScavenging(boolean isDigging) {
        this.entityData.set(SCAVENGING, isDigging);
    }
    public boolean isRoaring() {
        return this.entityData.get(ROARING);
    }

    public void setRoaring(boolean isRoaring) {
        this.entityData.set(ROARING, isRoaring);
    }

    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return CaveTrollBrain.create(this, dynamic);
    }

    public Brain<CaveTrollEntity> getBrain() {
        return (Brain<CaveTrollEntity>)super.getBrain();
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.EVIL;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return ImmutableList.of(RaceType.SNAGA, RaceType.GOBLIN, RaceType.ORC, RaceType.URUK);
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return itemStack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "troll_food")));
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "chains")));
    }

    public static boolean shouldTarget(LivingEntity target) {
        return target instanceof NpcEntity || target instanceof Player || target instanceof Pouncer;
    }

    @Override
    protected float nextStep() {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            return this.moveDist + 1.0f;
        }
        return this.moveDist + 0.25f;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundsME.CAVE_TROLL_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundsME.CAVE_TROLL_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getEatingSound() {
        return super.getEatingSound();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsME.CAVE_TROLL_IDLE;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            this.playSound(SoundsME.CAVE_TROLL_STEP, 1.5f, 1.0f);
        }
        else {
            this.playSound(SoundsME.CAVE_TROLL_STEP, 1.0f, 1.0f);
        }
    }

    @Nullable
    protected SoundEvent getRoarSound() {
        return SoundsME.CAVE_TROLL_ROAR;
    }

    public void playRoarSound() {
        this.makeSound(this.getRoarSound());
    }

    public static boolean canSpawn(EntityType<CaveTrollEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor world, MobSpawnType spawnReason) {
        return true;
    }
}
