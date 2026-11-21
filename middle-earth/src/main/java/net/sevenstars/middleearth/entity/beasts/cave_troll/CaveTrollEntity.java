package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.gui.cavetroll.CaveTrollScreenHandler;
import net.minecraft.item.BannerItem;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.WorldAccess;
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
    public LootWorldContext lootWorldContext;
    private float smashingStrength; // Used in server-side only
    private float smashingTime; // Used in server-side only
    private float enragedTime; // Used in server-side only
    private boolean riderJumpHeld; // Used in server-side only
    private static final byte SKIP_TO_SLAM_STATUS = 100;
    // Ticks into the smash animation where the downswing starts
    private static final int DOWNSWING_TICK = 27;
    private static final Identifier RIDER_REACH_ID = MiddleEarth.id("cave_troll_rider_reach");
    private static final double RIDER_REACH_BONUS = 3.0;
    // Flat bonus for a player rider, what Hard adds
    private static final float PLAYER_RIDER_DAMAGE_BONUS = 6.0f;
    private static final int RIDDEN_ABILITY_COOLDOWN = 120;

    private static final double MAX_CHARGE_SPEED = 0.6; // Blocks per tick at the end of a charge
    private static final double SMASH_REACH = 2.5;
    private static final double SMASH_RADIUS = 5.0;
    private static final double SMASH_HEIGHT = 1.5;

    // Ground covered per footfall, in blocks.
    public static final float WALK_STRIDE = 1.95f;
    public static final float RUN_STRIDE = 2.6f;

    private static final float WALK_LOOP_SECONDS = 1.75f;
    private static final float RUN_LOOP_SECONDS = 1.25f;
    public static final float WALK_ANIMATION_SPEED = WALK_LOOP_SECONDS * 1000f / (6f * 50f * 2f * WALK_STRIDE);
    public static final float RUN_ANIMATION_SPEED = RUN_LOOP_SECONDS * 1000f / (6f * 50f * 2f * RUN_STRIDE);
    public static final TrackedData<Boolean> SCAVENGING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> ROARING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SMASHING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> ENRAGED = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.INTEGER);
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
                            .add(LootContextParameters.ORIGIN, this.getEntityPos())
                            .build(LootContextTypes.CHEST);
                }

            }
        }
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.MAX_HEALTH, 160.0)
                .add(EntityAttributes.ARMOR, 10.0)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(EntityAttributes.ATTACK_SPEED, 0.4)
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
        builder.add(VARIANT, 0);
    }

    @Override
    public void writeData(WriteView view) {
        super.writeData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(VARIANT, view.getInt("Variant", 0));
    }


    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        CaveTrollVariant variant = Util.getRandom(CaveTrollVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public CaveTrollVariant getVariant() {
        return CaveTrollVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(CaveTrollVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
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

    private boolean isAffectedByDaylight() {
        if (this.getEntityWorld().getEnvironmentAttributes().getAttributeValue(EnvironmentAttributes.MONSTERS_BURN_GAMEPLAY, this.getEntityPos())) {
            float f = this.getBrightnessAtEyes();
            BlockPos blockPos = BlockPos.ofFloored(this.getX(), this.getEyeY(), this.getZ());
            boolean bl = this.isTouchingWaterOrRain() || this.inPowderSnow || this.wasInPowderSnow;
            return f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !bl && this.getEntityWorld().isSkyVisible(blockPos);
        }
        return false;
    }

    @Override
    public void tryBonding(PlayerEntity player) {
        double rand = this.random.nextDouble();

        if(rand < 0.15 || (rand < 0.3 && this.getTameness() <= 0) || player.isInCreativeMode()) { // Tame success, chance is twice as high if the troll is feral
            this.tameBeast(player);
            this.getEntityWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeTimeout = 0;
        }
        else if(rand > 0.7) { // Tame failure (wake up, become enraged)
            this.enragedTime = this.age;
            this.setEnraged(true);
            this.getBrain().remember(MemoryModuleType.ATTACK_TARGET, player);
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1200));
            this.getEntityWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(!this.getEntityWorld().isClient()) { // Server side
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

    // No horse armor
    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return slot != EquipmentSlot.BODY && super.canUseSlot(slot);
    }

    // The banner lives in the body slot
    public static boolean isBanner(ItemStack stack) {
        return stack.getItem() instanceof BannerItem || stack.isOf(WeaponItemsME.HELD_BANNER);
    }

    public ItemStack getBannerStack() {
        return this.getEquippedStack(EquipmentSlot.BODY);
    }

    // Mob equipment only drops by chance, the banner always does
    @Override
    protected void dropInventory(ServerWorld world) {
        super.dropInventory(world);
        ItemStack banner = this.getBannerStack();
        if(!banner.isEmpty()) {
            this.dropStack(world, banner.copy());
            this.equipStack(EquipmentSlot.BODY, ItemStack.EMPTY);
        }
    }

    public SimpleInventory getChestInventory() {
        return this.items;
    }

    @Override
    public void openInventory(PlayerEntity player) {
        if(!this.getEntityWorld().isClient() && (!this.hasPassengers() || this.hasPassenger(player)) && this.isTame()) {
            player.openHandledScreen(new ExtendedScreenHandlerFactory<Integer>() {
                @Override
                public Integer getScreenOpeningData(ServerPlayerEntity player) {
                    return CaveTrollEntity.this.getId();
                }

                @Override
                public Text getDisplayName() {
                    return CaveTrollEntity.this.getDisplayName();
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new CaveTrollScreenHandler(syncId, playerInventory, CaveTrollEntity.this);
                }
            });
        }
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
    protected boolean isTamable(PlayerEntity player) {
        return this.isSleeping() || player.isCreative();
    }

    @Override
    public boolean isMountable() { // This method only determines whether the entity is mountable via the usual horse method
        return false;
    }

    @Override
    public void tameBeast(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            this.tameBeast((LivingEntity) player);
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }
    }

    @Override
    public void tameBeast(LivingEntity livingEntity) {
        if(!this.getEntityWorld().isClient()) {
            this.setTame(true);
            this.setTameness(75);
            this.stopSleeping();
            this.getBrain().remember(MemoryModulesME.TAME, true);
            this.getBrain().forget(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN);
            this.getBrain().forget(MemoryModulesME.FOOD_EATEN_COUNT);
            this.getBrain().forget(MemoryModuleType.NEAREST_ATTACKABLE);
            this.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
            this.setOwner(livingEntity);
        }

    }

    @Override
    public void tickMovement() {
        this.tickHandSwing();
        if(this.getEntityWorld().isClient()) {
            setupAnimationStates();
        }
        else {
            if(this.getTargetInBrain() != null && !this.isSprinting()) {
                this.setSprinting(true);
            }
            else if(this.getTargetInBrain() == null && this.isSprinting()) {
                this.setSprinting(false);
            }

            // Space during a charge cuts it short with the slam
            boolean jumpHeld = this.getControllingPassenger() instanceof ServerPlayerEntity rider && rider.getPlayerInput().jump();
            if(jumpHeld && !this.riderJumpHeld && this.isCharging() && this.isSmashing() && this.age - this.smashingTime < DOWNSWING_TICK) {
                this.setCharging(false);
                this.smashingTime = this.age - DOWNSWING_TICK;
                this.getEntityWorld().sendEntityStatus(this, SKIP_TO_SLAM_STATUS);
            }
            this.riderJumpHeld = jumpHeld;

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
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if(passenger instanceof PlayerEntity player) {
            EntityAttributeInstance reach = player.getAttributeInstance(EntityAttributes.ENTITY_INTERACTION_RANGE);
            if(reach != null && !reach.hasModifier(RIDER_REACH_ID)) {
                reach.addTemporaryModifier(new EntityAttributeModifier(RIDER_REACH_ID, RIDER_REACH_BONUS, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if(passenger instanceof PlayerEntity player) {
            EntityAttributeInstance reach = player.getAttributeInstance(EntityAttributes.ENTITY_INTERACTION_RANGE);
            if(reach != null) {
                reach.removeModifier(RIDER_REACH_ID);
            }
        }
    }

    // The troll swings, so its attack speed sets the cooldown
    public float getRiderSwingCooldownTicks() {
        return (float)(20.0 / this.getAttributeValue(EntityAttributes.ATTACK_SPEED));
    }

    public void riderSwing(ServerWorld world, PlayerEntity rider, Entity target) {
        if(!this.isValidTarget(target)) {
            return;
        }

        float charge = rider.getAttackCooldownProgress(0.5f);
        rider.resetTicksSinceLastAttack();
        this.swingHand(Hand.MAIN_HAND);

        float damage = (float)this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE) * (0.2f + charge * charge * 0.8f) + PLAYER_RIDER_DAMAGE_BONUS;
        if(target.damage(world, this.getDamageSources().playerAttack(rider), damage) && target instanceof LivingEntity living) {
            knockAway(living, charge * charge);
        }
        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1.5f, 0.6f);
    }

    private double getWeaponModifier(RegistryEntry<EntityAttribute> attribute, String id) {
        AttributeModifiersComponent component = this.getWeaponStack().get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if(component != null) {
            for(AttributeModifiersComponent.Entry modifier : component.modifiers()) {
                if(modifier.matches(attribute, Identifier.ofVanilla(id))) {
                    return modifier.modifier().value();
                }
            }
        }
        return 0;
    }

    private void knockAway(LivingEntity target, double strength) {
        knockAway(target, this.getEntityPos(), strength);
    }

    private static boolean isWithinSmashRadius(Box box, Vec3d impact) {
        double dx = Math.max(Math.max(box.minX - impact.x, impact.x - box.maxX), 0.0);
        double dz = Math.max(Math.max(box.minZ - impact.z, impact.z - box.maxZ), 0.0);
        return dx * dx + dz * dz <= SMASH_RADIUS * SMASH_RADIUS;
    }

    private void knockAway(LivingEntity target, Vec3d origin, double strength) {
        Vec3d away = target.getEntityPos().subtract(origin).getHorizontal().normalize();
        target.takeKnockback(strength, -away.x, -away.z);
        // Player velocity is client authoritative unless flagged
        target.knockedBack = true;
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 1.8f) : ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 0.9f);
        }

        return super.getSaddledSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 1.25f) : ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 0.2f);
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

        float animationSpeed = Math.min(this.limbAnimator.getSpeed(), 1.0f);
        float animationProgress = this.limbAnimator.getAnimationProgress() * (MathHelper.PI / 180) * 18;
        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (RUN_ANIMATION_SPEED / RUN_LOOP_SECONDS) : (WALK_ANIMATION_SPEED / WALK_LOOP_SECONDS);

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

            if(this.getEntityWorld().isClient() && this.smashingAnimationState.isRunning()) {
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

            if(this.getEntityWorld().isClient()) {
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
        return this.hasControllingPassenger() ? RIDDEN_ABILITY_COOLDOWN : 300;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return getTargetInBrain();
    }

    @Override
    public void chargeAttack() {
        List<Entity> entities = this.getEntityWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));
        Vec3d direction = Vec3d.ZERO;
        LivingEntity target = this.getTarget();
        int difficulty = this.hasControllingPassenger() ? this.getEntityWorld().getDifficulty().getId() : 0;
        float riderBonus = riderDamageBonus();

        if(!this.isTame() && !this.getEntityWorld().isClient()) { // Charge Attack for wild Troll
            if(target != null) { // Has attack target memory
                direction = this.getEntityPos().relativize(target.getEntityPos()); // Vector from Troll to target entity
            }

            this.setYaw((float) Math.toDegrees(Math.atan2(-direction.x, direction.z))); // Turning the troll into the right direction
            this.setChargeVelocity(direction);
        }
        else if (this.getEntityWorld().isClient() && this.hasControllingPassenger()) { // Charge Attack for tamed Troll
            this.setChargeVelocity(this.getRotationVector());
        }

        for(Entity entity : entities) { // Check through all nearby entities
            if(entity != this.getOwner() && !this.getPassengerList().contains(entity)) { // Exclude passengers and owner
                if(getEntityWorld() instanceof ServerWorld serverWorld) {
                    if(entity.damage(serverWorld, attackSource(), 10.0f + riderBonus) && entity instanceof LivingEntity living) {
                        knockAway(living, 1.5);
                    }
                }

            }
        }
    }

    // Starts as a stumble and eases into a run, so the speed rises with the square of the charge's progress
    @Override
    public void setChargeVelocity(Vec3d direction) {
        double progress = MathHelper.clamp(1.0 - (double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration(), 0.0, 1.0);
        this.setVelocity(direction
                .getHorizontal().normalize()
                .multiply(MAX_CHARGE_SPEED * progress * progress)
                .add(0, this.getVelocity().y, 0));
    }

    // Fixed for a player rider, scales with difficulty for an NPC rider
    private float riderDamageBonus() {
        if(this.getControllingPassenger() instanceof PlayerEntity) {
            return PLAYER_RIDER_DAMAGE_BONUS;
        }
        return this.hasControllingPassenger() ? this.getEntityWorld().getDifficulty().getId() * 2 : 0.0f;
    }

    // Player-sourced, so world difficulty does not scale it
    private DamageSource attackSource() {
        if(this.getControllingPassenger() instanceof PlayerEntity rider) {
            return this.getDamageSources().playerAttack(rider);
        }
        return this.getDamageSources().mobAttack(this);
    }

    public void smashAttack(float strength) { // Strength goes from 0 to 100
        setSmashing(false);
        // The fists land in front of the troll; everything within the radius and up to SMASH_HEIGHT off the ground is hit
        Vec3d impact = this.getEntityPos().add(Vec3d.fromPolar(0.0f, this.getBodyYaw()).multiply(SMASH_REACH));
        Box box = new Box(impact.subtract(SMASH_RADIUS, 0, SMASH_RADIUS), impact.add(SMASH_RADIUS, SMASH_HEIGHT, SMASH_RADIUS));

        double weaponDamage = getWeaponModifier(EntityAttributes.ATTACK_DAMAGE, "base_attack_damage");

        List<Entity> entities = this.getEntityWorld().getOtherEntities(this, box);
        World world = this.getEntityWorld();
        float riderBonus = riderDamageBonus();

        if(world instanceof ServerWorld serverWorld) {
            double baseDamage = this.getAttributeBaseValue(EntityAttributes.ATTACK_DAMAGE);
            float charge = strength / 100f;
            float damage = (float)((baseDamage + weaponDamage) * (0.4f + 0.6f * charge)) + riderBonus;

            for(Entity entity : entities) {
                if(this.isValidTarget(entity) && isWithinSmashRadius(entity.getBoundingBox(), impact)) {
                    if(entity.damage(serverWorld, attackSource(), damage) && entity instanceof LivingEntity living) {
                        knockAway(living, impact, 0.6 + 0.8 * charge);
                    }
                }
            }

            BlockPos impactBlock = BlockPos.ofFloored(impact);
            int radius = MathHelper.floor(SMASH_RADIUS);
            for(int x = -radius; x <= radius; x++) { // Spawn particles on affected blocks
                for(int z = -radius; z <= radius; z++) {
                    if(x * x + z * z > SMASH_RADIUS * SMASH_RADIUS) {
                        continue;
                    }
                    BlockPos ground = impactBlock.add(x, -1, z);
                    BlockStateParticleEffect particles = new BlockStateParticleEffect(ParticleTypes.BLOCK, world.getBlockState(ground));
                    serverWorld.spawnParticles(particles, ground.getX() + 0.5, impactBlock.getY(), ground.getZ() + 0.5, 7, 0.5, 0.3, 0.5, 0.2);
                }
            }

            this.playSound(SoundsME.TROLL_SLAM, 1.5f, 1.0f);
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
            setChargeTimeout(maxChargeCooldown());
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
            boolean wasSitting = this.isSitting();
            super.startJumping(height);
            if(this.hasControllingPassenger() && !wasSitting) {
                // The smash wind-up lasts as long as the charge, so the slam lands as the charge ends
                this.setSmashing(true);
                this.smashingTime = this.age;
                this.smashingStrength = height;
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        if(status == SKIP_TO_SLAM_STATUS) {
            this.smashingAnimationState.start(this.age - DOWNSWING_TICK);
        }
        else {
            super.handleStatus(status);
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
        if(!this.getEntityWorld().isClient()) {
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

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CaveTrollBrain.create(this, dynamic);
    }

    // The brain is always built by CaveTrollBrain.create, so the cast holds
    @SuppressWarnings("unchecked")
    @Override
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
        return target instanceof NpcEntity || target instanceof PlayerEntity || target instanceof Pouncer;
    }

    @Override
    protected float calculateNextStepSoundDistance() {
        // distanceTraveled grows by 0.6 per block
        return this.distanceTraveled + (this.isRunningGait() ? RUN_STRIDE : WALK_STRIDE) * 0.6f;
    }

    public boolean isRunningGait() {
        return this.isSprinting() || this.isCharging() || (this.getControllingPassenger() != null && this.getControllingPassenger().isSprinting());
    }

    @Override
    protected void updateLimbs(float posDelta) {
        // Uncapped, so the legs keep pace with the ground at any speed
        this.limbAnimator.updateLimbs(posDelta * 6.0f, 0.2f, 1.0f);
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
    protected SoundEvent getEatSound() {
        return super.getEatSound();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsME.CAVE_TROLL_IDLE;
    }

    // The horse base class plays hoof sounds on jumps and landings
    @Override
    public void playSound(SoundEvent sound, float volume, float pitch) {
        if(sound == SoundEvents.ENTITY_HORSE_JUMP || sound == SoundEvents.ENTITY_HORSE_LAND) {
            sound = SoundsME.CAVE_TROLL_STEP;
        }
        else if(sound == SoundEvents.ENTITY_CAMEL_DASH) { // Remove weird charge sound
            return;
        }
        super.playSound(sound, volume, pitch);
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
        this.playSound(this.getRoarSound());
    }

    public static boolean canSpawn(EntityType<CaveTrollEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }
}
