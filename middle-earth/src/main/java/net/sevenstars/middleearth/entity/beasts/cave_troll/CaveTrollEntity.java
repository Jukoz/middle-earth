package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
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
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.utils.PlayerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// TODO Add sounds
public class CaveTrollEntity extends AbstractBeastEntity {
    public LootTable scavengeLootTable;
    public LootWorldContext lootWorldContext;
    private float smashingStrength; // Used in server-side only
    private float smashingTime; // Used in server-side only
    private float enragedTime; // Used in server-side only
    public static final TrackedData<Boolean> SCAVENGING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> ROARING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SMASHING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> ENRAGED = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState chaseAnimationState = new AnimationState();
    public final AnimationState scavengingAnimationState = new AnimationState();
    public final AnimationState startSleepingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState stopSleepingAnimationState = new AnimationState();
    public final AnimationState roaringAnimationState = new AnimationState();
    public final AnimationState smashingAnimationState = new AnimationState();

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
        builder.add(ROARING, false);
        builder.add(SLEEPING, false);
        builder.add(SMASHING, false);
        builder.add(ENRAGED, false);
    }


    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("caveTrollBrain");
        this.getBrain().tick(world, this);
        profiler.swap("caveTrollActivityUpdate");
        CaveTrollBrain.updateActivities(this);
        profiler.pop();

        if(!this.isClientWorld() && this.isAffectedByDaylight()) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100));
        }

        super.mobTick(world);
    }

    @Override
    public void tryBonding(PlayerEntity player) {
        double rand = this.random.nextDouble();

        if(rand < 0.15 || (rand < 0.3 && this.getTameness() <= 0) || player.isInCreativeMode()) { // Tame success, chance is twice as high if the troll is feral
            this.tameBeast(player);
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeTimeout = 0;
        }
        else if(rand > 0.7) { // Tame failure (wake up, become enraged)
            this.enragedTime = this.age;
            this.setEnraged(true);
            this.getBrain().remember(MemoryModuleType.ATTACK_TARGET, player);
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1200));
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(!this.getWorld().isClient()) { // Server side
            for(RaceType race : this.getCompatibleRaces()) { // Check for race
                if(PlayerUtil.isOfRace(player, race) || player.isCreative()) {

                    if(isTrollWeapon(itemStack) && isOwner(player) && this.getMainHandStack().isEmpty()) { // Give the troll a weapon
                        this.equipStack(EquipmentSlot.MAINHAND, itemStack.copyAndEmpty());
                        itemStack.decrementUnlessCreative(1, player);
                        return ActionResult.SUCCESS_SERVER;
                    }
                    else if(player.isSneaking() && itemStack.isEmpty() && isOwner(player) && !this.getMainHandStack().isEmpty()) {  // Take weapon away from troll
                        player.giveOrDropStack(this.getMainHandStack().copyAndEmpty());
                        return ActionResult.SUCCESS_SERVER;
                    }
                    else if(canAddPassenger(player) && isTame() && itemStack.isEmpty()) { // Ride if player is compatible and hand is empty
                        putPlayerOnBack(player);
                        return ActionResult.SUCCESS_SERVER;
                    }
                    else if(!itemStack.isEmpty()) {
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
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public boolean usesTameness() {
        return true;
    }

    public boolean isTrollWeapon(ItemStack itemStack) {
        return itemStack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "troll_weapons")));
    }

    @Override
    protected boolean isTamable() {
        return this.isSleeping();
    }

    @Override
    public boolean isMountable() { // This method only determines whether the entity is mountable via the usual horse method
        return false;
    }

    @Override
    public void tameBeast(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            this.setTame(true);
            this.setTameness(75);
            this.stopSleeping();
            this.getBrain().remember(MemoryModulesME.TAME, true);
            this.getBrain().forget(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN);
            this.getBrain().forget(MemoryModulesME.FOOD_EATEN_COUNT);
            this.getBrain().forget(MemoryModuleType.NEAREST_ATTACKABLE);
            this.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
            this.setOwner(player);
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

            if(this.isSmashing() && this.hasControllingPassenger()) {
                if(this.age - this.smashingTime > 30) {
                    smashAttack(smashingStrength);
                }
            }

            if(this.isEnraged() && this.age - this.enragedTime > 1200) {
                this.setEnraged(false);
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
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 1.25f) : ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 0.15f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor)  {
        List<Entity> passengerList = this.getPassengerList();
        boolean saddled = this.hasSaddleEquipped();
        boolean sprinting = this.isCharging();

        if(this.getControllingPassenger() != null) {
            sprinting = this.getControllingPassenger().isSprinting() || this.isCharging();
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

            if(this.getWorld().isClient() && this.smashingAnimationState.isRunning()) {
                float time = (this.smashingAnimationState.getTimeInMilliseconds(this.age) / 2000.0F) * 2 * MathHelper.PI; // Goes from 0 to 2Pi over the duration of the animation
                if(this.smashingAnimationState.getTimeInMilliseconds(this.age) < 1000) {
                    front -= MathHelper.sin(time) * 0.3;
                }
                else {
                    front -= MathHelper.sin(time) * 2f;
                    y += MathHelper.sin(time) * 0.3f;
                }

            }

            double x = MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * side - MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * front;
            double z = MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * side + MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * front;

            return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(x, y, z);
        }
        else { // Passenger 2 or 3 - Side ==============================================================================
            double y = sprinting ?
                    -MathHelper.cos(frequency * animationProgress) * 0.06 * animationSpeed : // height when sprinting
                    MathHelper.sin(frequency * animationProgress) * 0.02; // height when walking

            double side = sprinting ?
                    MathHelper.sin(frequency * animationProgress - (4f/15f)*MathHelper.PI) * 0.15 : // side-to-side movement when sprinting
                    MathHelper.sin(frequency * animationProgress) * 0.04; // side-to-side movement when walking

            double front = sprinting ?
                    0.35 : // front-back movement when sprinting
                    0; // front-back movement when walking

            if(passengerList.size() >= 3 && passenger.equals(passengerList.get(2))) {   // The left passenger (2) moves inverted to the right one (1)
                y = -y;
            }

            y = sprinting ? y + 0.15 : y; // Add offset if sprinting

            if(this.getWorld().isClient()) {
                float time = (this.smashingAnimationState.getTimeInMilliseconds(this.age) / 2000.0F) * 2 * MathHelper.PI; // Goes from 0 to 2Pi over the duration of the animation
                if(this.smashingAnimationState.getTimeInMilliseconds(this.age) < 1000) {
                    front -= MathHelper.sin(time) * 0.3;
                }
                else {
                    front -= MathHelper.sin(time) * 1.8f;
                    y += MathHelper.sin(time) * 0.3f;
                }

            }

            double x = MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * side - MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * front;
            double z = MathHelper.sin((float)Math.toRadians(this.getBodyYaw())) * side + MathHelper.cos((float)Math.toRadians(this.getBodyYaw())) * front;

            return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(x, y, z);
        }
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

        if(this.isRoaring()) {
            this.roaringAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.roaringAnimationState.stop();
        }

        if(this.isSmashing()) {
            this.smashingAnimationState.startIfNotRunning(this.age);
        }
        else if(smashingAnimationState.getTimeInMilliseconds(this.age) >= 2000) {
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
        return getTargetInBrain();
    }

    @Override
    public void chargeAttack() {
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));
        Vec3d direction = Vec3d.ZERO;
        LivingEntity target = this.getTarget();
        int difficulty = this.hasControllingPassenger() ? this.getWorld().getDifficulty().getId() : 0;

        if(!this.isTame() && !this.getWorld().isClient) { // Charge Attack for wild Troll
            if(target != null) { // Has attack target memory
                direction = this.getPos().relativize(target.getPos()); // Vector from Troll to target entity
            }

            this.setYaw((float) Math.toDegrees(Math.atan2(-direction.x, direction.z))); // Turning the troll into the right direction
            this.setChargeVelocity(direction);
        }
        else if (this.getWorld().isClient && this.hasControllingPassenger()) { // Charge Attack for tamed Troll
            this.setChargeVelocity(this.getRotationVector());
        }

        for(Entity entity : entities) { // Check through all nearby entities
            if(entity != this.getOwner() && !this.getPassengerList().contains(entity)) { // Exclude passengers and owner
                if(getWorld() instanceof ServerWorld serverWorld) {
                    entity.damage(serverWorld, entity.getDamageSources().mobAttack(this), 10.0f + difficulty * 2);
                }

            }
        }
        this.getWorld().addParticleClient(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1, 0.1, 1);
    }

    public void smashAttack(float strength) { // Strength goes from 0 to 100
        setSmashing(false);
        Box box = new Box(this.getPos().subtract(5,0,5), this.getPos().add(5,1,5));

        double weaponDamage = 0;
        AttributeModifiersComponent component = this.getWeaponStack().get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if(component != null) {
            for(AttributeModifiersComponent.Entry modifier : component.modifiers()) {
                if(modifier.matches(EntityAttributes.ATTACK_DAMAGE, Identifier.ofVanilla("base_attack_damage"))) {
                    weaponDamage = modifier.modifier().value();
                }
            }
        }

        List<Entity> entities = this.getWorld().getOtherEntities(this, box);
        World world = this.getWorld();
        int difficulty = this.hasControllingPassenger() ? world.getDifficulty().getId() : 0;

        if(world instanceof ServerWorld serverWorld) {
            for(Entity entity : entities) {
                if(this.isValidTarget(entity)) {
                    entity.damage(serverWorld, this.getDamageSources().mobAttack(this),  (float)weaponDamage + (strength / 12.5f) + (difficulty * 2));
                }
            }

            for(int x = -5; x <= 5; x++) { // Spawn particles on affected blocks
                for(int z = -5; z <= 5; z++) {
                    BlockStateParticleEffect particles = new BlockStateParticleEffect(ParticleTypes.BLOCK, world.getBlockState(new BlockPos(this.getBlockX() + x, this.getBlockY() - 1, this.getBlockZ() + z)));
                    serverWorld.spawnParticles(particles, this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z, 7, 0.5, 0.3, 0.5, 0.2);
                }
            }

            this.playSound(SoundEvents.BLOCK_STONE_BREAK, 1, 0.4f);
            if(weaponDamage > 0) {
                this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1, 0.1f);
            }
        }
    }

    @Override
    public float getWeaponDisableBlockingForSeconds() {
        return this.isCharging() || this.isSmashing() ? 10.0f : 0f;
    }

    @Override
    protected void jump(float strength, Vec3d movementInput) {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            super.jump(strength, movementInput);
        }
        else if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            setChargeTimeout(300);
        }
    }

    @Override
    public void startJumping(int height) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            if(!this.isSitting()) {
                this.playJumpSound();
                this.setSmashing(true);
                this.smashingTime = this.age;
                this.smashingStrength = height;
            }
            else {
                this.setSitting(false);
            }
        }
        else {
            super.startJumping(height);
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

    public boolean isEnraged() {
        return this.dataTracker.get(ENRAGED);
    }

    public void setEnraged(boolean isEnraged) {
        this.dataTracker.set(ENRAGED, isEnraged);
    }

    public boolean isSmashing() {
        return this.dataTracker.get(SMASHING);
    }

    public void setSmashing(boolean isSmashing) {
        this.dataTracker.set(SMASHING, isSmashing);
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
    public boolean isRoaring() {
        return this.dataTracker.get(ROARING);
    }

    public void setRoaring(boolean isRoaring) {
        this.dataTracker.set(ROARING, isRoaring);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
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
        return ImmutableList.of(RaceType.ORC, RaceType.URUK);
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return itemStack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "troll_food")));
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "chains")));
    }

    public static boolean shouldTarget(LivingEntity target) {
        return target instanceof NpcEntity || target instanceof PlayerEntity;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return super.getHurtSound(source);
    }

    @Nullable
    @Override
    protected SoundEvent getEatSound() {
        return super.getEatSound();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    @Nullable
    protected SoundEvent getRoarSound() {
        return null;
    }

    public void playRoarSound() {
        this.playSound(this.getRoarSound());
    }
}
