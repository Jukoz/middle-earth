package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.goals.BeastRevengeGoal;
import net.sevenstars.middleearth.entity.goals.ChargeAttackGoal;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.utils.PlayerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

public class BroadhoofGoatEntity extends AbstractBeastEntity {
    private static final float MIN_MOVEMENT_SPEED_BONUS = (float)BroadhoofGoatEntity.generateSpeed(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float)BroadhoofGoatEntity.generateSpeed(() -> 1.0);
    private static final float MIN_JUMP_STRENGTH_BONUS = (float)BroadhoofGoatEntity.generateJumpStrength(() -> 0.0);
    private static final float MAX_JUMP_STRENGTH_BONUS = (float)BroadhoofGoatEntity.generateJumpStrength(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = BroadhoofGoatEntity.generateMaxHealth(max -> 0);
    private static final float MAX_HEALTH_BONUS = BroadhoofGoatEntity.generateMaxHealth(max -> max - 1);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(BroadhoofGoatEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BEADS = SynchedEntityData.defineId(BroadhoofGoatEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAIR = SynchedEntityData.defineId(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LEFT_HORN = SynchedEntityData.defineId(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIGHT_HORN = SynchedEntityData.defineId(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> BRUSHED_BEARD = SynchedEntityData.defineId(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MOUNTABLE = SynchedEntityData.defineId(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState jumpAnimationState = new AnimationState();
    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntitiesME.BROADHOOF_GOAT.getDimensions().scale(0.5f);


    public BroadhoofGoatEntity(EntityType<? extends AbstractBeastEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.MAX_HEALTH, 50.0d)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4d)
                .add(Attributes.ATTACK_SPEED, 1.0d)
                .add(Attributes.FOLLOW_RANGE, 38.0d)
                .add(Attributes.ATTACK_DAMAGE, 4.0d)
                .add(Attributes.STEP_HEIGHT, 1.15d)
                .add(Attributes.SAFE_FALL_DISTANCE, 10.0d)
                .add(EntityAttributesME.POWDERED_SNOW_IMMUNITY, 1.0f)
                .add(Attributes.JUMP_STRENGTH, 1);
    }

    @Override
    protected void randomizeAttributes(RandomSource random) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateMaxHealth(random::nextInt));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateSpeed(random::nextDouble));
        this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateJumpStrength(random::nextDouble));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 2.5, false));
        this.goalSelector.addGoal(4, new ChargeAttackGoal(this, null, maxChargeCooldown()));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.5));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0, (stack) -> {return stack.is(ItemTags.COW_FOOD);}, false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new BeastRevengeGoal(this, new Class[0]).setGroupRevenge());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(BEADS, 0);
        builder.define(HAIR, false);
        builder.define(LEFT_HORN, true);
        builder.define(RIGHT_HORN, true);
        builder.define(BRUSHED_BEARD, false);
        builder.define(MOUNTABLE, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        view.putInt("Variant", this.getGoatVariant());
        view.putInt("Beads", this.getGoatBeadsIndex());
        view.putBoolean("Hair", this.hasHair());
        view.putBoolean("HasLeftHorn", this.hasLeftHorn());
        view.putBoolean("HasRightHorn", this.hasRightHorn());
        view.putBoolean("HasBrushedBeard", this.hasBrushedBeard());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        this.entityData.set(VARIANT, view.getInt("Variant"));
        this.entityData.set(BEADS, view.getInt("Beads"));
        this.entityData.set(HAIR, view.getBoolean("Hair"));
        this.entityData.set(LEFT_HORN, !view.contains("HasLeftHorn") || view.getBoolean("HasLeftHorn"));
        this.entityData.set(RIGHT_HORN, !view.contains("HasRightHorn") || view.getBoolean("HasRightHorn"));
        this.entityData.set(BRUSHED_BEARD, view.getBoolean("HasBrushedBeard"));
        this.entityData.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
    }

    protected static float generateMaxHealth(IntUnaryOperator randomIntGetter) {
        return 20.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(8);
    }

    protected static double generateJumpStrength(DoubleSupplier randomDoubleGetter) {
        return (double)0.8f + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2;
    }

    protected static double generateSpeed(DoubleSupplier randomDoubleGetter) {
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
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        ItemStack itemStack = player.getItemInHand(hand);

        if(this.isClientWorld()) { // Client
            if(itemStack.isEmpty()) {
                return super.mobInteract(player, hand);
            }
        }
        else { // Server
            for(RaceType race : this.getCompatibleRaces()) { // Check for race
                if(PlayerUtil.isOfRace(player, race) || player.isCreative()) {
                    if(this.isTamed()) {
                        if (this.isFood(itemStack)) { // Feed
                            if(this.getHealth() < this.getMaxHealth()) { // Food provides health
                                itemStack.consume(1, player);
                                FoodProperties foodComponent = itemStack.get(DataComponents.FOOD);
                                float f = foodComponent != null ? (float)foodComponent.nutrition() : 1.0f;
                                this.heal(2.0f * f);
                                return InteractionResult.SUCCESS;
                            }
                            else if (this.getAge() == 0 && this.canFallInLove()) { // Food provides baby
                                this.usePlayerItem(player, hand, itemStack);
                                this.setInLove(player);
                                return InteractionResult.SUCCESS;
                            }
                        }
                        else if(itemStack.is(Items.BRUSH)) { // Brush beard
                            this.setBrushedBeard(true);
                            return InteractionResult.SUCCESS;
                        }
                        else if(hasBrushedBeard()) {
                            if(BroadhoofGoatBeads.isValidMaterial(itemStack)) {
                                this.setGoatBeads(BroadhoofGoatBeads.getBeads(itemStack));
                                itemStack.consume(1, player);

                                return InteractionResult.SUCCESS;
                            }
                            else if(itemStack.is(Items.SHEARS)) { // Un-Brush beard
                                if(getGoatBeads().equals(BroadhoofGoatBeads.NONE)) {
                                    this.setBrushedBeard(false);
                                }
                                else {
                                    this.setGoatBeads(BroadhoofGoatBeads.NONE);
                                }

                                if(!player.isCreative()) {
                                    itemStack.hurtAndBreak(1, player,
                                            hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                                }

                                return InteractionResult.SUCCESS;
                            }
                        }
                    }

                    return super.mobInteract(player, hand);
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float animationSpeed = this.walkAnimation.speed();
        float animationProgress = this.walkAnimation.position() * (Mth.PI / 180) * 18;

        boolean sprinting = passenger.isSprinting();

        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (1.2f/0.74f) : 4;

        double y = sprinting ?
                Mth.sin(animationProgress * frequency + Mth.PI / 4) * 0.11 * animationSpeed - 0.05 :
                Mth.cos(animationProgress * frequency) * 0.06 * animationSpeed - 0.05;

        if(this.isSitting()) {
            y = -0.5;
        }

        return super.getPassengerAttachmentPoint(passenger, dimensions, scaleFactor).add(0, y,0);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        BroadhoofGoatEntity broadhoofEntity = (BroadhoofGoatEntity)entity;
        BroadhoofGoatEntity broadhoofEntity2 = EntitiesME.BROADHOOF_GOAT.create(world);
        if (broadhoofEntity2 != null) {
            int i = this.random.nextInt(9);
            BroadhoofGoatColor goatColor = i < 4 ? this.getGoatColor() : (i < 8 ? broadhoofEntity.getGoatColor() : Util.getRandom(BroadhoofGoatColor.values(), this.random));
            i = this.random.nextInt(9);
            BroadhoofGoatPattern goatPattern = i < 4 ? this.getPattern() : (i < 8 ? broadhoofEntity.getPattern() : Util.getRandom(BroadhoofGoatPattern.values(), this.random));
            i = this.random.nextInt(5);
            BroadhoofGoatHorns broadhoofHorns = i < 2 ? this.getHorns() : (i < 4 ? broadhoofEntity.getHorns() : Util.getRandom(BroadhoofGoatHorns.values(), this.random));

            broadhoofEntity2.setGoatVariant(goatColor, goatPattern, broadhoofHorns);
            this.setOffspringAttributes(entity, broadhoofEntity2);
        }
        return broadhoofEntity2;
    }

    @Override
    protected void setOffspringAttributes(AgeableMob other, AbstractHorse child) {
        this.setOffspringAttribute(other, child, Attributes.MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setOffspringAttribute(other, child, Attributes.JUMP_STRENGTH, MIN_JUMP_STRENGTH_BONUS, MAX_JUMP_STRENGTH_BONUS);
        this.setOffspringAttribute(other, child, Attributes.MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }


    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    @Override
    public boolean isMountable() {
        return this.entityData.get(MOUNTABLE);
    }

    @Override
    protected boolean isTamable(Player player) {
        return this.isMountable();
    }

    @Override
    public boolean canMate(Animal other) {
        return other instanceof BroadhoofGoatEntity;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.GOAT_FOOD);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result) {
            this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
        }
        return result;
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
            this.setDeltaMovement(targetDir.multiply(1,0,1).normalize().scale(1.0d - ((double)Mth.abs(this.chargeTimeout - (maxChargeCooldown() - chargeDuration()) - (chargeDuration() * 0.2f)) / chargeDuration())).add(0, this.getDeltaMovement().y, 0));
        }
        else if (this.level().isClientSide) {
            this.setDeltaMovement(this.getLookAngle().multiply(1,0,1).normalize().scale(1.0d - ((double)Mth.abs(this.chargeTimeout - (maxChargeCooldown() - chargeDuration()) - (chargeDuration() * 0.2f)) / chargeDuration())).add(0, this.getDeltaMovement().y, 0));
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            Entity owner = this.getOwner();
            List<Entity> entities = serverLevel.getEntities(
                    this,
                    this.getBoundingBox().inflate(0.2, 0, 0.2),
                    entity -> entity != owner && !this.getPassengers().contains(entity)
            );
            for(Entity entity : entities) {
                entity.hurt(entity.damageSources().mobAttack(this), getAttackDamage());

                Vec3 velocity = this.getDeltaMovement();
                velocity = velocity.multiply(1.0, 0.0, 1.0);
                velocity = velocity.normalize();
                Vec3 vec3d = velocity.scale(2);
                if (vec3d.lengthSqr() > 0.0) {
                    entity.push(vec3d.x, 0.15, vec3d.z);
                }

                if(this.random.nextInt(10) == 0 && !this.isTamed() && !this.isBaby()) {
                    this.dropHorn();
                }

                this.setCharging(false);
            }
        }
        this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    protected void executeRidersJump(float strength, Vec3 movementInput) {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            super.executeRidersJump(strength, movementInput);
        }
        else {
            this.setChargeTimeout(30);
            double d = this.getJumpPower(strength);
            Vec3 vec3d = this.getDeltaMovement().scale(4);
            this.setDeltaMovement(vec3d.x, d, vec3d.z);
            this.setOnGround(false);
            this.hasImpulse = true;
            if (movementInput.z > 0.0) {
                float f = Mth.sin(this.getYRot() * ((float)Math.PI / 180));
                float g = Mth.cos(this.getYRot() * ((float)Math.PI / 180));
                this.setDeltaMovement(this.getDeltaMovement().add(-0.4f * f * strength, 0.0, 0.4f * g * strength));
            }
        }
    }

    @Override
    public void handleStartJump(int height) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            if(!this.isSitting()) {
                this.allowStandSliding = true;
                this.playJumpSound();
            }
            else {
                this.setSitting(false);
            }
        }
        else {
            super.handleStartJump(height);
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
    public void aiStep() {
        super.aiStep();

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
        EntityDataAccessor<Boolean> trackedData = !bl ? RIGHT_HORN : (!bl2 ? LEFT_HORN : (this.random.nextBoolean() ? LEFT_HORN : RIGHT_HORN));
        this.entityData.set(trackedData, false);
        Vec3 vec3d = this.position();
        ItemStack itemStack = this.getGoatHornStack();
        double d = Mth.randomBetween(this.random, -0.2f, 0.2f);
        double e = Mth.randomBetween(this.random, 0.3f, 0.7f);
        double f = Mth.randomBetween(this.random, -0.2f, 0.2f);
        ItemEntity itemEntity = new ItemEntity(this.level(), vec3d.x(), vec3d.y(), vec3d.z(), itemStack, d, e, f);
        this.level().addFreshEntity(itemEntity);
        return true;
    }

    public ItemStack getGoatHornStack() {
        RandomSource random = RandomSource.create((long)this.getUUID().hashCode());
        TagKey<Instrument> tagKey = this.random.nextBoolean() ? InstrumentTags.SCREAMING_GOAT_HORNS : InstrumentTags.REGULAR_GOAT_HORNS;
        return this.level().registryAccess().registryOrThrow(Registries.INSTRUMENT).getRandomElementOf(tagKey, random).map((registryEntry) -> {
            return InstrumentItem.create(Items.GOAT_HORN, registryEntry);
        }).orElseGet(() -> {
            return new ItemStack(Items.GOAT_HORN);
        });
    }

    protected void setupAnimationStates() {
        if(this.isSitting()) {
            this.startSittingAnimationState.startIfStopped(this.tickCount);
        }
        if(!this.isSitting() && this.startSittingAnimationState.isStarted()) {
            this.startSittingAnimationState.stop();
            this.stopSittingAnimationState.start(this.tickCount);
        }

        if(!this.onGround() && this.hasControllingPassenger()) {
            this.jumpAnimationState.startIfStopped(this.tickCount);
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
        return stack.is(Items.STICK);
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    protected float getRiddenSpeed(Player controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5f);
        }

        return super.getRiddenSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 2f) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.75f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.is(ItemTags.GOAT_FOOD);
    }

    /* VARIANTS */
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        BroadhoofGoatColor color = Util.getRandom(BroadhoofGoatColor.values(), this.random);
        BroadhoofGoatPattern pattern = BroadhoofGoatPattern.PATTERN_COMBINATIONS.get(color).getRandom().getItem();
        BroadhoofGoatHorns horns = Util.getRandom(BroadhoofGoatHorns.values(), this.random);

        this.setGoatVariant(color, pattern, horns);

        this.setHair(this.random.nextBoolean());

        if(!this.level().isClientSide()) {
            this.entityData.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
        }

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }


    // VARIANTS

    private void setGoatVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    private int getGoatVariant() {
        return this.entityData.get(VARIANT);
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

    public void setGoatBeads(BroadhoofGoatBeads beads) {
        this.entityData.set(BEADS, beads.getIndex() & 0xFF);
    }

    public int getGoatBeadsIndex() {
        return this.entityData.get(BEADS);
    }

    public BroadhoofGoatBeads getGoatBeads() {
        return BroadhoofGoatBeads.byIndex(getGoatBeadsIndex());
    }

    public boolean hasHair() {
        return this.entityData.get(HAIR);
    }

    public void setHair(boolean hair) {
        this.entityData.set(HAIR, hair);
    }

    public boolean hasRightHorn() {
        return this.entityData.get(RIGHT_HORN);
    }

    public boolean hasLeftHorn() {
        return this.entityData.get(LEFT_HORN);
    }

    public void setBrushedBeard(boolean brushedBeard) {
        this.entityData.set(BRUSHED_BEARD, brushedBeard);
    }
    public boolean hasBrushedBeard() {
        return this.entityData.get(BRUSHED_BEARD);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.GOAT_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.GOAT_HURT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.GOAT_AMBIENT;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientStandSound() {
        return SoundEvents.GOAT_SCREAMING_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.GOAT_PREPARE_RAM;
    }

    @Override
    public void makeMad() {
        this.playSound(this.getAngrySound(), 1.0f, 0.7f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.GOAT_STEP, 0.15f, 0.7f);
    }

    @Override
    protected void playGallopSound(SoundType group) {
        this.playSound(SoundEvents.GOAT_STEP, 1.0f, 0.7f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.GOAT_LONG_JUMP, 1.0f, 0.7f);
    }
}
