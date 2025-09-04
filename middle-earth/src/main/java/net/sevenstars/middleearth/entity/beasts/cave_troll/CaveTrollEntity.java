package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.utils.PlayerUtil;

import java.util.List;

// TODO Add weakness on sun exposure
// TODO Implement charge attack
// TODO Implement sweep attack
// TODO Add Fighting Activities
// TODO Implement Tameness mechanic
public class CaveTrollEntity extends AbstractBeastEntity {
    public LootTable scavengeLootTable;
    public LootWorldContext lootWorldContext;
    public static final TrackedData<Boolean> SCAVENGING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> EATING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState chaseAnimationState = new AnimationState();
    public final AnimationState scavengingAnimationState = new AnimationState();
    public final AnimationState startSleepingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState stopSleepingAnimationState = new AnimationState();

    public CaveTrollEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);

        if(scavengeLootTable == null && !world.isClient()) {
            if(world instanceof ServerWorld serverWorld) {

                LootTable lootTable = serverWorld.getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MiddleEarth.MOD_ID, "gameplay/cave_troll_scavenging")));

                if(lootTable != null) {
                    scavengeLootTable = lootTable;

                    lootWorldContext = new LootWorldContext.Builder(serverWorld)
                            .add(LootContextParameters.THIS_ENTITY, this)
                            .add(LootContextParameters.ORIGIN, this.getPos())
                            .build(LootContextTypes.CHEST);
                }

            }
        }
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.MAX_HEALTH, 120.0)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(EntityAttributes.ATTACK_SPEED, 0.65)
                .add(EntityAttributes.FOLLOW_RANGE, 28.0)
                .add(EntityAttributes.ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.STEP_HEIGHT, 1.25)
                .add(EntityAttributes.FOLLOW_RANGE, 15.0);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SCAVENGING, false);
        builder.add(EATING, false);
        builder.add(SLEEPING, false);
    }


    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("caveTrollBrain");
        this.getBrain().tick(world, this);
        profiler.swap("caveTrollActivityUpdate");
        CaveTrollBrain.updateActivities(this);
        profiler.pop();

        super.mobTick(world);
    }

    @Override
    public void tryBonding(PlayerEntity player) {
        double rand = this.random.nextDouble();

        if(rand < 0.15 || player.isInCreativeMode()) { // Tame success
            this.tameBeast(player);
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeTimeout = 0;
        }
        else if(rand > 0.7) { // Tame failure (wake up, become enraged)
            this.getBrain().remember(MemoryModuleType.ATTACK_TARGET, player);
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1200));
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    @Override
    protected void putPlayerOnBack(PlayerEntity player) {
        ItemStack item = player.getStackInHand(Hand.MAIN_HAND);
        if(this.canAddPassenger(player) && item.isEmpty()) {
            super.putPlayerOnBack(player);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(!this.isTame()) {
            tryBonding(player); // TODO: ONLY TEMPORARY - DON'T FORGET TO REMOVE
        }

        if(!this.getWorld().isClient()) { // Server side
            for(RaceType race : this.getCompatibleRaces()) { // Check for race
                if(PlayerUtil.isOfRace(player, race)) {
                    if(canAddPassenger(player) && itemStack.isEmpty()) { // Ride if player is compatible and hand is empty
                        putPlayerOnBack(player);
                        return ActionResult.SUCCESS;
                    }

                    if(!itemStack.isEmpty()) {
                        return super.interactMob(player, hand);
                    }
                }
            }
        }
        else {  // Client side
            if(!itemStack.isEmpty()) {
                return super.interactMob(player, hand);
            }
        }

        return ActionResult.PASS; // Player is of incompatible race - don't interact
    }

    @Override
    protected boolean isTamable() {
        return this.isSleeping();
    }

    @Override
    protected void tameBeast(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            this.getBrain().remember(MemoryModulesME.TAME, true);
            this.getBrain().forget(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN);
            this.getBrain().forget(MemoryModulesME.FOOD_EATEN_COUNT);
            this.setOwner(player);
            this.setTame(true);
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }
    }

    @Override
    public void tickMovement() {
        if(this.getWorld().isClient) {
            setupAnimationStates();
        }
        else {
            if(this.getTargetInBrain() != null && !this.isSprinting()) {
                this.setSprinting(true);
            }
            else if(this.getTargetInBrain() == null && this.isSprinting()) {
                this.setSprinting(false);
            }
        }

        super.tickMovement();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {   // Allow 3 people to ride the troll
        if(this.isSitting()) {
            return false;
        }
        return this.hasSaddleEquipped() ? getPassengerList().size() < 3 : getPassengerList().isEmpty();
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 1.25f) : ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 0.15f);
        }

        return super.getSaddledSpeed(controllingPlayer);
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        List<Entity> passengerList = this.getPassengerList();
        boolean saddled = this.hasSaddleEquipped();
        boolean sprinting = false;
        if(this.getControllingPassenger() != null) {
            sprinting = this.getControllingPassenger().isSprinting();
        }

        float animationSpeed = this.limbAnimator.getSpeed();
        float animationProgress = this.limbAnimator.getAnimationProgress() * (MathHelper.PI / 180) * 18;
        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (2f/1.25f) : (10f/1.75f);

        if(passenger.equals(this.getControllingPassenger()) || !saddled) { // Passenger 1 - Controlling ============================================================================
            double y = sprinting ?
                    -MathHelper.cos(2 * frequency * animationProgress) * 0.06 * animationSpeed + 0.1 : // height when sprinting
                    MathHelper.sin(2 * frequency * animationProgress) * 0.02; // height when walking

            double side = sprinting ?
                    MathHelper.sin(frequency * animationProgress - (4f/15f)*MathHelper.PI) * 0.225 : // side-to-side movement when sprinting
                    MathHelper.sin(frequency * animationProgress) * 0.04; // side-to-side movement when walking

            double front = sprinting ?
                    0.35 : // front-back movement when sprinting
                    0; // front-back movement when walking

            if(!saddled) {
                y -= 0.32;
                front += 0.5;
            }

            double x = MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * side - MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * front;
            double z = MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * side + MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * front;

            return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(x, y, z);
        }
        else if(passengerList.size() >= 2 && passenger.equals(passengerList.get(1))) { // Passenger 2 - Right side ========================================================================
            double y = sprinting ?
                    -MathHelper.cos(frequency * animationProgress) * 0.07 * animationSpeed + 0.15 : // height when sprinting
                    MathHelper.sin(frequency * animationProgress) * 0.02; // height when walking

            double side = sprinting ?
                    MathHelper.sin(frequency * animationProgress - (4f/15f)*MathHelper.PI) * 0.15 : // side-to-side movement when sprinting
                    MathHelper.sin(frequency * animationProgress) * 0.04; // side-to-side movement when walking

            double front = sprinting ?
                    0.35 : // front-back movement when sprinting
                    0; // front-back movement when walking

            double x = MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * side - MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * front;
            double z = MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * side + MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * front;

            return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(x, y, z);
        }
        else if(passengerList.size() >= 3 && passenger.equals(passengerList.get(2))) { // Passenger 3 - Left side =========================================================================
            double y = sprinting ?
                    MathHelper.cos(frequency * animationProgress) * 0.07 * animationSpeed + 0.15 : // height when sprinting
                    -MathHelper.sin(frequency * animationProgress) * 0.02; // height when walking

            double side = sprinting ?
                    MathHelper.sin(frequency * animationProgress - (4f/15f)*MathHelper.PI) * 0.15 : // side-to-side movement when sprinting
                    MathHelper.sin(frequency * animationProgress) * 0.04; // side-to-side movement when walking

            double front = sprinting ?
                    0.35 : // front-back movement when sprinting
                    0; // front-back movement when walking

            double x = MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * side - MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * front;
            double z = MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * side + MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * front;

            return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(x, y, z);
        }
        return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor);
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    @Override
    protected void setupAnimationStates() {
        if(this.isScavenging()) { // Looking for food
            this.scavengingAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.scavengingAnimationState.stop();
        }

        if(this.isSitting()) { // Sitting
            this.startSittingAnimationState.startIfNotRunning(this.age);
        }
        else if(this.startSittingAnimationState.isRunning()) {
            this.startSittingAnimationState.stop();
            this.stopSittingAnimationState.startIfNotRunning(this.age);
        }
        if(this.stopSittingAnimationState.getTimeInMilliseconds(this.age) > 3000) {
            this.stopSittingAnimationState.stop();
        }

        if(this.isSleeping()) { // Sleeping
            if(!this.startSleepingAnimationState.isRunning() && !this.sleepingAnimationState.isRunning()) {
                this.startSleepingAnimationState.startIfNotRunning(this.age);
            }
            if (this.startSleepingAnimationState.getTimeInMilliseconds(this.age) > 5000) {
                this.sleepingAnimationState.startIfNotRunning(this.age);
                this.startSleepingAnimationState.stop();
            }
        }
        else if(this.startSleepingAnimationState.isRunning() || this.sleepingAnimationState.isRunning()) {
            this.startSleepingAnimationState.stop();
            this.sleepingAnimationState.stop();
            this.stopSleepingAnimationState.startIfNotRunning(this.age);
        }
        if(this.stopSleepingAnimationState.getTimeInMilliseconds(this.age) > 5000) {
            this.stopSleepingAnimationState.stop();
        }
    }

    public void startSleeping() {
        if (this.hasVehicle()) {
            this.stopRiding();
        }

        this.setSleeping(true);
        this.setVelocity(Vec3d.ZERO);
        this.velocityDirty = true;

        this.brain.forget(MemoryModuleType.WALK_TARGET);
        this.brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void stopSleeping() {
        this.setSleeping(false);
    }

    public boolean isSleeping() {
        return this.dataTracker.get(SLEEPING);
    }

    public void setSleeping(boolean isSleeping) {
        this.dataTracker.set(SLEEPING, isSleeping);
    }

    @Override
    public void setSitting(boolean sitting) {
        if(!this.getWorld().isClient()) {
            if(sitting) {
                this.getBrain().remember(MemoryModulesME.SITTING, true);
                this.getBrain().forget(MemoryModuleType.WALK_TARGET);
            }
            else {
                this.getBrain().forget(MemoryModulesME.SITTING);
            }

            this.removeAllPassengers();
        }

        super.setSitting(sitting);
    }

    public boolean isScavenging() {
        return this.dataTracker.get(SCAVENGING);
    }

    public void setScavenging(boolean isDigging) {
        this.dataTracker.set(SCAVENGING, isDigging);
    }
    public boolean isEating() {
        return this.dataTracker.get(EATING);
    }

    public void setEating(boolean isEating) {
        this.dataTracker.set(EATING, isEating);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CaveTrollBrain.create(this, dynamic);
    }

    public Brain<CaveTrollEntity> getBrain() {
        return (Brain<CaveTrollEntity>)super.getBrain();
    }

    @Override
    public Disposition getDisposition() {
        return Disposition.EVIL;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return ImmutableList.of(RaceType.ORC, RaceType.URUK);
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "chains")));
    }

    public static boolean shouldTarget(LivingEntity target) {
        return target instanceof NpcEntity || target instanceof PlayerEntity;
    }
}
