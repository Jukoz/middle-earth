package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentType;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.InstrumentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.goals.BeastRevengeGoal;
import net.sevenstars.middleearth.entity.goals.ChargeAttackGoal;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.utils.PlayerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

public class BroadhoofGoatEntity extends AbstractBeastEntity {
    private static final float MIN_MOVEMENT_SPEED_BONUS = (float)BroadhoofGoatEntity.getChildMovementSpeedBonus(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float)BroadhoofGoatEntity.getChildMovementSpeedBonus(() -> 1.0);
    private static final float MIN_JUMP_STRENGTH_BONUS = (float)BroadhoofGoatEntity.getChildJumpStrengthBonus(() -> 0.0);
    private static final float MAX_JUMP_STRENGTH_BONUS = (float)BroadhoofGoatEntity.getChildJumpStrengthBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = BroadhoofGoatEntity.getChildHealthBonus(max -> 0);
    private static final float MAX_HEALTH_BONUS = BroadhoofGoatEntity.getChildHealthBonus(max -> max - 1);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> BEADS = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> HAIR = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> LEFT_HORN = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RIGHT_HORN = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> BRUSHED_BEARD = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MOUNTABLE = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState jumpAnimationState = new AnimationState();
    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntitiesME.BROADHOOF_GOAT.getDimensions().scaled(0.5f);


    public BroadhoofGoatEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.MAX_HEALTH, 50.0d)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.4d)
                .add(EntityAttributes.ATTACK_SPEED, 1.0d)
                .add(EntityAttributes.FOLLOW_RANGE, 38.0d)
                .add(EntityAttributes.ATTACK_DAMAGE, 4.0d)
                .add(EntityAttributes.STEP_HEIGHT, 1.15d)
                .add(EntityAttributes.SAFE_FALL_DISTANCE, 10.0d)
                .add(EntityAttributes.JUMP_STRENGTH, 1);
    }

    @Override
    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(this.getChildHealthBonus(random::nextInt));
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus(random::nextDouble));
        this.getAttributeInstance(EntityAttributes.JUMP_STRENGTH).setBaseValue(this.getChildJumpStrengthBonus(random::nextDouble));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 2.5, false));
        this.goalSelector.add(4, new ChargeAttackGoal(this, null, maxChargeCooldown()));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.5));
        this.goalSelector.add(6, new TemptGoal(this, 1.0, (stack) -> {return stack.isIn(ItemTags.COW_FOOD);}, false));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastRevengeGoal(this, new Class[0]).setGroupRevenge());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(BEADS, 0);
        builder.add(HAIR, false);
        builder.add(LEFT_HORN, true);
        builder.add(RIGHT_HORN, true);
        builder.add(BRUSHED_BEARD, false);
        builder.add(MOUNTABLE, true);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("Variant", this.getGoatVariant());
        view.putInt("Beads", this.getGoatBeadsIndex());
        view.putBoolean("Hair", this.hasHair());
        view.putBoolean("HasLeftHorn", this.hasLeftHorn());
        view.putBoolean("HasRightHorn", this.hasRightHorn());
        view.putBoolean("HasBrushedBeard", this.hasBrushedBeard());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(VARIANT, view.getInt("Variant", 0));
        this.dataTracker.set(BEADS, view.getInt("Beads", 0));
        this.dataTracker.set(HAIR, view.getBoolean("Hair", false));
        this.dataTracker.set(LEFT_HORN, view.getBoolean("HasLeftHorn", true));
        this.dataTracker.set(RIGHT_HORN, view.getBoolean("HasRightHorn", true));
        this.dataTracker.set(BRUSHED_BEARD, view.getBoolean("HasBrushedBeard", false));
        this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 20.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(8);
    }

    protected static double getChildJumpStrengthBonus(DoubleSupplier randomDoubleGetter) {
        return (double)0.8f + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2;
    }

    protected static double getChildMovementSpeedBonus(DoubleSupplier randomDoubleGetter) {
        return ((double)0.4f + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2) * 0.25;
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.GOOD;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return List.of(RaceType.DWARF);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        ItemStack itemStack = player.getStackInHand(hand);

        if(this.isClientWorld()) { // Client
            if(itemStack.isEmpty()) {
                return super.interactMob(player, hand);
            }
        }
        else { // Server
            for(RaceType race : this.getCompatibleRaces()) { // Check for race
                if(PlayerUtil.isOfRace(player, race) || player.isCreative()) {
                    if(this.isTame()) {
                        if (this.isBreedingItem(itemStack)) { // Feed
                            if(this.getHealth() < this.getMaxHealth()) { // Food provides health
                                itemStack.decrementUnlessCreative(1, player);
                                FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
                                float f = foodComponent != null ? (float)foodComponent.nutrition() : 1.0f;
                                this.heal(2.0f * f);
                                return ActionResult.SUCCESS_SERVER;
                            }
                            else if (this.getBreedingAge() == 0 && this.canEat()) { // Food provides baby
                                this.eat(player, hand, itemStack);
                                this.lovePlayer(player);
                                return ActionResult.SUCCESS_SERVER;
                            }
                        }
                        else if(itemStack.isOf(Items.BRUSH)) { // Brush beard
                            this.setBrushedBeard(true);
                            return ActionResult.SUCCESS_SERVER;
                        }
                        else if(hasBrushedBeard()) {
                            if(BroadhoofGoatBeads.isValidMaterial(itemStack)) {
                                this.setGoatBeads(BroadhoofGoatBeads.getBeads(itemStack));
                                itemStack.decrementUnlessCreative(1, player);

                                return ActionResult.SUCCESS_SERVER;
                            }
                            else if(itemStack.isOf(Items.SHEARS)) { // Un-Brush beard
                                if(getGoatBeads().equals(BroadhoofGoatBeads.NONE)) {
                                    this.setBrushedBeard(false);
                                }
                                else {
                                    this.setGoatBeads(BroadhoofGoatBeads.NONE);
                                }

                                if(!player.isCreative()) {
                                    itemStack.damage(1, player);
                                }

                                return ActionResult.SUCCESS_SERVER;
                            }
                        }
                    }

                    return super.interactMob(player, hand);
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float animationSpeed = this.limbAnimator.getSpeed();
        float animationProgress = this.limbAnimator.getAnimationProgress() * (MathHelper.PI / 180) * 18;

        boolean sprinting = passenger.isSprinting();

        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (1.2f/0.74f) : 4;

        double y = sprinting ?
                MathHelper.sin(animationProgress * frequency + MathHelper.PI / 4) * 0.11 * animationSpeed - 0.05 :
                MathHelper.cos(animationProgress * frequency) * 0.06 * animationSpeed - 0.05;

        if(this.isSitting()) {
            y = -0.5;
        }

        return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(0, y,0);
    }

    @Override
    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        BroadhoofGoatEntity broadhoofEntity = (BroadhoofGoatEntity)entity;
        BroadhoofGoatEntity broadhoofEntity2 = EntitiesME.BROADHOOF_GOAT.create(world, SpawnReason.BREEDING);
        if (broadhoofEntity2 != null) {
            int i = this.random.nextInt(9);
            BroadhoofGoatColor goatColor = i < 4 ? this.getGoatColor() : (i < 8 ? broadhoofEntity.getGoatColor() : Util.getRandom(BroadhoofGoatColor.values(), this.random));
            i = this.random.nextInt(9);
            BroadhoofGoatPattern goatPattern = i < 4 ? this.getPattern() : (i < 8 ? broadhoofEntity.getPattern() : Util.getRandom(BroadhoofGoatPattern.values(), this.random));
            i = this.random.nextInt(5);
            BroadhoofGoatHorns broadhoofHorns = i < 2 ? this.getHorns() : (i < 4 ? broadhoofEntity.getHorns() : Util.getRandom(BroadhoofGoatHorns.values(), this.random));

            broadhoofEntity2.setGoatVariant(goatColor, goatPattern, broadhoofHorns);
            this.setChildAttributes(entity, broadhoofEntity2);
        }
        return broadhoofEntity2;
    }

    @Override
    protected void setChildAttributes(PassiveEntity other, AbstractHorseEntity child) {
        this.setChildAttribute(other, child, EntityAttributes.MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setChildAttribute(other, child, EntityAttributes.JUMP_STRENGTH, MIN_JUMP_STRENGTH_BONUS, MAX_JUMP_STRENGTH_BONUS);
        this.setChildAttribute(other, child, EntityAttributes.MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }


    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }

    @Override
    public boolean isMountable() {
        return this.dataTracker.get(MOUNTABLE);
    }

    @Override
    protected boolean isTamable() {
        return this.isMountable();
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return other instanceof BroadhoofGoatEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.GOAT_FOOD);
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        if(!world.isClient && super.tryAttack(world, target)) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
        }

        return super.tryAttack(world, target);
    }

    @Override
    public void chargeAttack() {
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2,0,0.2));

        if(!this.isTame() && !this.getWorld().isClient) {
            if(targetDir == Vec3d.ZERO && this.getTarget() != null) {
                targetDir = new Vec3d( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                        this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                        this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
            }
            this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
            this.setVelocity(targetDir.multiply(1,0,1).normalize().multiply(1.0d - ((double)MathHelper.abs(this.chargeTimeout - (maxChargeCooldown() - chargeDuration()) - (chargeDuration() * 0.2f)) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }
        else if (this.getWorld().isClient) {
            this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().multiply(1.0d - ((double)MathHelper.abs(this.chargeTimeout - (maxChargeCooldown() - chargeDuration()) - (chargeDuration() * 0.2f)) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }

        for(Entity entity : entities) {
            if(entity != this.getOwner() && entity != this && !this.getPassengerList().contains(entity) && !this.getWorld().isClient()) {
                entity.damage((ServerWorld) this.getWorld(), entity.getDamageSources().mobAttack(this), getAttackDamage());

                Vec3d velocity = this.getVelocity();
                velocity = velocity.multiply(1.0, 0.0, 1.0);
                velocity = velocity.normalize();
                Vec3d vec3d = velocity.multiply(2);
                if (vec3d.lengthSquared() > 0.0) {
                    entity.addVelocity(vec3d.x, 0.15, vec3d.z);
                }

                if(this.random.nextInt(10) == 0 && !this.isTame() && !this.isBaby()) {
                    this.dropHorn();
                }

                this.setCharging(false);
            }
        }
        this.getWorld().addParticleClient(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfNotRunning(this.age);
    }

    @Override
    protected void jump(float strength, Vec3d movementInput) {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            super.jump(strength, movementInput);
        }
        else {
            this.setChargeTimeout(30);
            double d = this.getJumpVelocity(strength);
            Vec3d vec3d = this.getVelocity().multiply(4);
            this.setVelocity(vec3d.x, d, vec3d.z);
            this.setOnGround(false);
            this.velocityDirty = true;
            if (movementInput.z > 0.0) {
                float f = MathHelper.sin(this.getYaw() * ((float)Math.PI / 180));
                float g = MathHelper.cos(this.getYaw() * ((float)Math.PI / 180));
                this.setVelocity(this.getVelocity().add(-0.4f * f * strength, 0.0, 0.4f * g * strength));
            }
        }
    }

    @Override
    public void startJumping(int height) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            if(!this.isSitting()) {
                this.jumping = true;
                this.playJumpSound();
            }
            else {
                this.setSitting(false);
            }
        }
        else {
            super.startJumping(height);
        }
    }

    @Override
    public int maxChargeCooldown() {
        return 120;
    }

    @Override
    public int chargeDuration() {
        return 16;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(this.isSitting()) {
            this.getNavigation().stop();
        }
    }

    public boolean dropHorn() {
        boolean bl = this.hasLeftHorn();
        boolean bl2 = this.hasRightHorn();
        if (!bl && !bl2) {
            return false;
        }
        TrackedData<Boolean> trackedData = !bl ? RIGHT_HORN : (!bl2 ? LEFT_HORN : (this.random.nextBoolean() ? LEFT_HORN : RIGHT_HORN));
        this.dataTracker.set(trackedData, false);
        Vec3d vec3d = this.getPos();
        ItemStack itemStack = this.getGoatHornStack();
        double d = MathHelper.nextBetween(this.random, -0.2f, 0.2f);
        double e = MathHelper.nextBetween(this.random, 0.3f, 0.7f);
        double f = MathHelper.nextBetween(this.random, -0.2f, 0.2f);
        ItemEntity itemEntity = new ItemEntity(this.getWorld(), vec3d.getX(), vec3d.getY(), vec3d.getZ(), itemStack, d, e, f);
        this.getWorld().spawnEntity(itemEntity);
        return true;
    }

    public ItemStack getGoatHornStack() {
        Random random = Random.create((long)this.getUuid().hashCode());
        TagKey<Instrument> tagKey = this.random.nextBoolean() ? InstrumentTags.SCREAMING_GOAT_HORNS : InstrumentTags.REGULAR_GOAT_HORNS;
        return (ItemStack)this.getWorld().getRegistryManager().getOrThrow(RegistryKeys.INSTRUMENT).getRandomEntry(tagKey, random).map((registryEntry) -> {
            return GoatHornItem.getStackForInstrument(Items.GOAT_HORN, registryEntry);
        }).orElseGet(() -> {
            return new ItemStack(Items.GOAT_HORN);
        });
    }

    protected void setupAnimationStates() {
        if(this.isSitting()) {
            this.startSittingAnimationState.startIfNotRunning(this.age);
        }
        if(!this.isSitting() && this.startSittingAnimationState.isRunning()) {
            this.startSittingAnimationState.stop();
            this.stopSittingAnimationState.start(this.age);
        }

        if(!this.isOnGround() && this.hasControllingPassenger()) {
            this.jumpAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.jumpAnimationState.stop();
        }
    }

    @Override
    public boolean usesTameness() {
        return false;
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.isOf(Items.STICK);
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED)) : ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 0.5f);
        }

        return super.getSaddledSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 2f) : ((float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 0.75f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        BroadhoofGoatColor color = Util.getRandom(BroadhoofGoatColor.values(), this.random);
        BroadhoofGoatPattern pattern = BroadhoofGoatPattern.PATTERN_COMBINATIONS.get(color).getRandom().getItem();
        BroadhoofGoatHorns horns = Util.getRandom(BroadhoofGoatHorns.values(), this.random);

        this.setGoatVariant(color, pattern, horns);

        this.setHair(this.random.nextBoolean());

        if(!this.getWorld().isClient()) {
            this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
        }

        return super.initialize(world, difficulty, spawnReason, entityData);
    }


    // VARIANTS

    private void setGoatVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    private int getGoatVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setGoatVariant(BroadhoofGoatColor color, BroadhoofGoatPattern pattern, BroadhoofGoatHorns horns) {
        this.setGoatVariant(color.getIndex() & 0xFF | pattern.getIndex() << 8 & 0xFF00 | horns.getId() << 16 & 0xFF0000);
    }

    public BroadhoofGoatColor getGoatColor() {
        return BroadhoofGoatColor.byIndex(this.getGoatVariant() & 0xFF);
    }

    private void setGoatColor(BroadhoofGoatColor color) {
        this.setGoatVariant(color.getIndex() & 0xFF00);
    }

    public BroadhoofGoatPattern getPattern() {
        return BroadhoofGoatPattern.byIndex((this.getGoatVariant() & 0xFF00) >> 8);
    }

    public BroadhoofGoatHorns getHorns() {
        return BroadhoofGoatHorns.byId(this.getGoatVariant() & 0xFF0000);
    }

    @Nullable
    @Override
    public <T> T get(ComponentType<? extends T> type) {
        return type == DataComponentTypesME.GOAT_VARIANT ? castComponentValue((ComponentType<T>)type, this.getGoatColor()) : super.get(type);
    }

    @Override
    protected void copyComponentsFrom(ComponentsAccess from) {
        this.copyComponentFrom(from, DataComponentTypesME.GOAT_VARIANT);
        super.copyComponentsFrom(from);
    }

    @Override
    protected <T> boolean setApplicableComponent(ComponentType<T> type, T value) {
        if (type == DataComponentTypesME.GOAT_VARIANT) {
            this.setGoatColor(castComponentValue(DataComponentTypesME.GOAT_VARIANT, value));
            return true;
        } else {
            return super.setApplicableComponent(type, value);
        }
    }

    public void setGoatBeads(BroadhoofGoatBeads beads) {
        this.dataTracker.set(BEADS, beads.getIndex() & 0xFF);
    }

    public int getGoatBeadsIndex() {
        return this.dataTracker.get(BEADS);
    }

    public BroadhoofGoatBeads getGoatBeads() {
        return BroadhoofGoatBeads.byIndex(getGoatBeadsIndex());
    }

    public boolean hasHair() {
        return this.dataTracker.get(HAIR);
    }

    public void setHair(boolean hair) {
        this.dataTracker.set(HAIR, hair);
    }

    public boolean hasRightHorn() {
        return this.dataTracker.get(RIGHT_HORN);
    }

    public boolean hasLeftHorn() {
        return this.dataTracker.get(LEFT_HORN);
    }

    public void setBrushedBeard(boolean brushedBeard) {
        this.dataTracker.set(BRUSHED_BEARD, brushedBeard);
    }
    public boolean hasBrushedBeard() {
        return this.dataTracker.get(BRUSHED_BEARD);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GOAT_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GOAT_HURT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GOAT_AMBIENT;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientStandSound() {
        return SoundEvents.ENTITY_GOAT_SCREAMING_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_GOAT_PREPARE_RAM;
    }

    @Override
    public void playAngrySound() {
        this.playSound(this.getAngrySound(), 1.0f, 0.7f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 0.15f, 0.7f);
    }

    @Override
    protected void playWalkSound(BlockSoundGroup group) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 1.0f, 0.7f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_GOAT_LONG_JUMP, 1.0f, 0.7f);
    }
}
