package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
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
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.VariantHolderUtils;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.goals.BowAtEntityGoal;
import net.sevenstars.middleearth.entity.goals.ChargeAttackGoal;
import net.sevenstars.middleearth.entity.goals.SmartFleeEntityGoal;
import net.sevenstars.middleearth.entity.goals.interfaces.Evader;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.greathornvariants.GreatHornVariantRegistry;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.sound.SoundsME;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

public class GreatHornEntity extends AbstractBeastEntity implements Evader {
    private static final TagKey<net.minecraft.world.level.biome.Biome> WARM_VARIANT_BIOMES =
            TagKey.create(Registries.BIOME, MiddleEarth.of("spawns_warm_variant_great_horn"));
    private static final TagKey<net.minecraft.world.level.biome.Biome> COLD_VARIANT_BIOMES =
            TagKey.create(Registries.BIOME, MiddleEarth.of("spawns_cold_variant_great_horn"));
    private static final int HORNS_ATTACK_COOLDOWN = 50;
    private static final float MIN_MOVEMENT_SPEED_BONUS = (float) GreatHornEntity.generateSpeed(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float) GreatHornEntity.generateSpeed(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = GreatHornEntity.generateMaxHealth(max -> 0);
    private static final float MAX_HEALTH_BONUS = GreatHornEntity.generateMaxHealth(max -> max - 1);
    private static final EntityDataAccessor<Holder<GreatHornVariant>> VARIANT = SynchedEntityData.defineId(GreatHornEntity.class, TrackedDataHandlerRegistryME.GREAT_HORN_VARIANT);;
    private static final EntityDataAccessor<Integer> BOW = SynchedEntityData.defineId(GreatHornEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK = SynchedEntityData.defineId(GreatHornEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> BLUE_SADDLE = SynchedEntityData.defineId(GreatHornEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MOUNTABLE = SynchedEntityData.defineId(GreatHornEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EVADING = SynchedEntityData.defineId(GreatHornEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState earWigglingAnimationState = new AnimationState();
    public final AnimationState gallopAnimationState = new AnimationState();
    public final AnimationState bowAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntitiesME.GREAT_HORN.getDimensions().scale(0.5f);
    protected int attackAnimationCooldown = 0;
    protected int bowAnimationTimeout = 0;

    public GreatHornEntity(EntityType<? extends AbstractBeastEntity> entityType, Level world) {
        super(entityType, world);
        idleAnimationTimeout = 200;
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.MAX_HEALTH, 50.0d)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3d)
                .add(Attributes.ATTACK_SPEED, 1.0d)
                .add(Attributes.FOLLOW_RANGE, 38.0d)
                .add(Attributes.ATTACK_DAMAGE, 4.0d)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5f)
                .add(Attributes.STEP_HEIGHT, 1.15d)
                .add(Attributes.SAFE_FALL_DISTANCE, 7.0d)
                .add(Attributes.JUMP_STRENGTH, 0.75d);
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
        this.goalSelector.addGoal(2, new BowAtEntityGoal(this, Player.class, 16, (livingEntity -> {
            return this.shouldBow((Player) livingEntity);
        }) ));
        this.goalSelector.addGoal(3, new SmartFleeEntityGoal<>(this, (Evader) this,
                Player.class, 20.0F, 1.6, 1.9, (entity) -> {
            return !this.canTrust((Player)entity);
        }));
        this.goalSelector.addGoal(4, new SmartFleeEntityGoal<>(this, (Evader) this,
                WargEntity.class, 20.0F, 1.7, 2.0, (entity) -> true));
        this.goalSelector.addGoal(4, new ChargeAttackGoal(this, null, maxChargeCooldown()));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.5));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0, (stack) -> stack.is(ItemTagsME.ELK_FOOD), false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        Holder<GreatHornVariant> greatHornVariantRegistryEntry =
                VariantHolderUtils.getDefaultOrAny(this.registryAccess(), GreatHornVariantRegistry.DEFAULT);
        builder.define(BOW, 0);
        builder.define(BLUE_SADDLE, false);
        builder.define(MOUNTABLE, true);
        builder.define(EVADING, false);
        builder.define(ATTACK, 0);
        builder.define(VARIANT, greatHornVariantRegistryEntry);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        VariantHolderUtils.writeVariant(view, this.getRegistryVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        VariantHolderUtils.readVariant(view, this.registryAccess(), DynamicRegistriesME.GREAT_HORN_VARIANTS)
                .ifPresent(this::setVariant);
        this.entityData.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
    }

    protected static float generateMaxHealth(IntUnaryOperator randomIntGetter) {
        return 20.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(8);
    }

    protected static double generateJumpStrength(DoubleSupplier randomDoubleGetter) {
        return 0;
    }

    protected static double generateSpeed(DoubleSupplier randomDoubleGetter) {
        return ((double)0.4f + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.2) * 0.27;
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.GOOD;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return List.of(RaceType.ELF);
    }

    @Override
    public boolean usesTameness() {
        return false;
    }

    public boolean hasBlueSaddle() {
        return this.entityData.get(BLUE_SADDLE);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(!this.level().isClientSide() && !player.isCreative()) {
            RaceType playerRace = RaceUtil.getRaceType(player);

            if(playerRace == null || playerRace == RaceType.NONE || (this.getCompatibleRaces() != null && !this.getCompatibleRaces().contains(playerRace))) {
                return InteractionResult.FAIL;
            }
        }

        if(this.isTamed() && this.isTamable(player)) {
            if (this.isFood(itemStack)) {
                if(this.getHealth() < this.getMaxHealth()) {
                    itemStack.consume(1, player);
                    FoodProperties foodComponent = itemStack.get(DataComponents.FOOD);
                    float f = foodComponent != null ? (float)foodComponent.nutrition() : 1.0f;
                    this.heal(2.0f * f);
                    return InteractionResult.SUCCESS;
                }
                else if (!this.level().isClientSide && this.getAge() == 0 && this.canFallInLove()) {
                    this.usePlayerItem(player, hand, itemStack);
                    this.setInLove(player);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if(itemStack.getItem().equals(Items.BLUE_DYE) && !this.entityData.get(BLUE_SADDLE)) {
            this.entityData.set(BLUE_SADDLE, true);
        } else if(itemStack.getItem().equals(Items.RED_DYE) && this.entityData.get(BLUE_SADDLE)) {
            this.entityData.set(BLUE_SADDLE, false);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canCarryChest() {
        return false;
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float f = this.walkAnimation.position() / 20;
        float g = this.walkAnimation.position() * (Mth.PI / 180) * 18; // TODO : Fix,was using limbAnimator.getPos()

        double y = 0.45;
        if(gallopAnimationState.isStarted()) {
            y += -0.025 + Mth.cos((f/0.75f) * (Mth.PI*2)) * 0.15;
        } else {
            y += Mth.cos(g - Mth.PI) * 0.02 - 0.2;
        }

        return super.getPassengerAttachmentPoint(passenger, dimensions, scaleFactor).add(0, y,0);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        GreatHornEntity greatHornEntity2 = EntitiesME.GREAT_HORN.create(world);
        if (greatHornEntity2 != null) {
            this.setOffspringAttributes(entity, greatHornEntity2);
            if (this.random.nextBoolean()) {
                greatHornEntity2.setVariant(this.getRegistryVariant());
            } else {
                greatHornEntity2.setVariant(((GreatHornEntity)entity).getRegistryVariant());
            }
        }
        return greatHornEntity2;
    }

    @Override
    protected void setOffspringAttributes(AgeableMob other, AbstractHorse child) {
        this.setOffspringAttribute(other, child, Attributes.MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setOffspringAttribute(other, child, Attributes.MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    @Override
    protected boolean isTamable(Player player) {
        return this.isMountable();
    }

    @Override
    public boolean canMate(Animal other) {
        return other instanceof GreatHornEntity;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTagsME.ELK_FOOD);
    }

    @Override
    protected void executeRidersJump(float strength, Vec3 movementInput) {
        if(this.chargeTimeout <= 0 && this.hasControllingPassenger()
                && this.getControllingPassenger().isSprinting()) {
            this.setCharging(true);
            this.chargeTimeout = maxChargeCooldown();
            float entitySpeed = (float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
            if(!this.level().isClientSide) {
                Vec2 vec2f = this.getRiddenRotation(this.getControllingPassenger());
                this.setDeltaMovement(new Vec3(vec2f.x,0,vec2f.y).normalize().add(0,0.4,0).scale(strength * (1.4f + entitySpeed)));
            }
            else if (this.level().isClientSide) {
                this.setDeltaMovement(this.getLookAngle().multiply(1,0,1).normalize().add(0,0.4,0).scale(strength * (1.4f + entitySpeed)));
            }
            this.chargeAnimationState.startIfStopped(this.tickCount);
        }
    }

    @Override
    public void handleStartJump(int height) {
        if(this.hasControllingPassenger()) {
            this.allowStandSliding = true;
            this.playJumpSound();
            float jumpPercentage = (float)height/100;
            if(!this.getControllingPassenger().isSprinting()) {
                this.setChargeTimeout(HORNS_ATTACK_COOLDOWN);
                entityData.set(ATTACK, HORNS_ATTACK_COOLDOWN);
                attackAnimationCooldown = HORNS_ATTACK_COOLDOWN;
                if (this.level() instanceof ServerLevel serverLevel) {
                    List<Entity> entities = serverLevel.getEntities(
                            this,
                            this.getBoundingBox().inflate(2.5, 2, 2.5),
                            entity -> !this.getPassengers().contains(entity)
                    );
                    for(Entity entity : entities) {
                            entity.hurt(entity.damageSources().mobAttack(this), jumpPercentage * getAttackDamage());
                        double dx = entity.getX() - this.getX();
                        double dz = entity.getZ() - this.getZ();

                        Vec3 velocity = new Vec3(dx, 1.25f + getRandom().nextFloat() * 0.5f, dz).normalize();
                        velocity = velocity.scale(jumpPercentage);
                        entity.push(velocity);

                        this.setCharging(false);
                    }
                }
            } else {
                this.playSound(SoundsME.GREAT_HORN_CALL, 1.0f, 1.0f);
            }
        }
        else {
            super.handleStartJump(height);
        }
    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    @Override
    public int getJumpCooldown() {
        return Math.max(super.getJumpCooldown(), this.entityData.get(ATTACK));
    }

    @Override
    public int maxChargeCooldown() {
        return 80;
    }

    @Override
    public int chargeDuration() {
        return 16;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isCharging()) {
            if(this.chargeTimeout <= maxChargeCooldown() - 10 && this.onGround()) {
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }

        if(bowAnimationTimeout > 0) {
            bowAnimationTimeout = Math.max(bowAnimationTimeout - 1, 0);
            if(bowAnimationTimeout == 0) {
                entityData.set(BOW, -1);
            }
        }
        if(attackAnimationCooldown > 0) {
            attackAnimationCooldown = Math.max(attackAnimationCooldown - 1, 0);
            entityData.set(ATTACK, attackAnimationCooldown);
        }
        if (this.level().isClientSide && bowAnimationState.isStarted()) {
            if(random.nextInt(2) == 0) {
                Vector3f randPos = new Vector3f(this.random.nextFloat()*6 - 3f, this.random.nextFloat()*1.25f, this.random.nextFloat()*6 - 3f);
                this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX() + randPos.x, this.getY() + randPos.y, this.getZ() + randPos.z,
                        0.0, 0.75f + this.random.nextFloat(), 0.0);
            }
        }
    }

    public void makeStuckInBlock(BlockState state, Vec3 multiplier) {
        float pow = 0.1f;
        Vec3 lessPenalty = new Vec3(Math.pow(multiplier.x, pow), Math.pow(multiplier.y, pow), Math.pow(multiplier.z, pow));
        super.makeStuckInBlock(state, lessPenalty);
    }

    protected void setupAnimationStates() {
        this.idleAnimationState.startIfStopped(this.tickCount);

        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.earWigglingAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        int bowState = entityData.get(BOW);
        if(bowState == 1) {
            this.bowAnimationState.startIfStopped(this.tickCount);
            entityData.set(BOW, 0);
        } else if(bowState == -1) {
            this.bowAnimationState.stop();
            entityData.set(BOW, 0);
        }

        int attack = entityData.get(ATTACK);
        if(attack == HORNS_ATTACK_COOLDOWN) {
            this.attackAnimationState.start(this.tickCount);
        } else if(attack == 0) {
            this.attackAnimationState.stop();
        }

        if(hasControllingPassenger()) {
            if((getControllingPassenger().isSprinting())) {
                this.gallopAnimationState.startIfStopped(this.tickCount);
            } else {
                this.gallopAnimationState.stop();
            }
        } else if(this.entityData.get(EVADING)) {
            this.gallopAnimationState.startIfStopped(this.tickCount);
        } else {
            this.gallopAnimationState.stop();
        }
    }

    @Override
    public void startFlee() {
        this.entityData.set(EVADING, true);
    }

    @Override
    public void stopFlee() {
        this.entityData.set(EVADING, false);
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.is(Items.STICK);
    }

    @Override
    protected float getRiddenSpeed(Player controllingPlayer) {
        float speed = ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
        if(this.level().getBiome(this.blockPosition()).is(BiomeTags.IS_FOREST)) {
            speed *= 1.1f;
        }
        if (controllingPlayer.isSprinting()) {
            return speed;
        } else {
            return speed * 0.5f;
        }
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    public boolean canTrust(Player playerEntity) {
        RaceType playerRace = RaceUtil.getRaceType(playerEntity);
        return (playerRace != null && playerRace != RaceType.NONE) && (this.getCompatibleRaces() != null && this.getCompatibleRaces().contains(playerRace));
    }

    public boolean shouldBow(Player playerEntity) {
        return isOwner(playerEntity) && bowAnimationTimeout > 0;
    }

    public boolean isOwner(Player playerEntity) {
        Player owner = this.getOwner();
        return (owner != null && owner.getUUID().equals(playerEntity.getUUID()));
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.is(ItemTagsME.ELK_FOOD);
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public void setOwner(@Nullable LivingEntity entity) {
        super.setOwner(entity);
        this.entityData.set(BOW, 1);
        bowAnimationTimeout = 80;
    }

    /* VARIANTS */
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                 @Nullable SpawnGroupData entityData) {
        if (entityData instanceof GreatHornData greatHornData) {
            this.setVariant(greatHornData.variant);
        } else {
            var biome = world.getBiome(this.blockPosition());
            var variantKey = biome.is(COLD_VARIANT_BIOMES)
                    ? GreatHornVariantRegistry.COLD
                    : biome.is(WARM_VARIANT_BIOMES)
                    ? GreatHornVariantRegistry.WARM
                    : this.random.nextBoolean()
                    ? GreatHornVariantRegistry.BROWN
                    : GreatHornVariantRegistry.TEMPERATE;
            Holder<GreatHornVariant> variant = world.registryAccess()
                    .registryOrThrow(DynamicRegistriesME.GREAT_HORN_VARIANTS)
                    .getHolderOrThrow(variantKey);
            this.setVariant(variant);
            entityData = new GreatHornData(variant);
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    private void setVariant(Holder<GreatHornVariant> variant) {
        this.entityData.set(VARIANT, variant);
    }

    public GreatHornVariant getVariant() {
        return getRegistryVariant().value();
    }

    private Holder<GreatHornVariant> getRegistryVariant() {
        return this.entityData.get(VARIANT);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundsME.GREAT_HORN_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundsME.GREAT_HORN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsME.GREAT_HORN_IDLE;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
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

    public static class GreatHornData extends AgeableMob.AgeableMobGroupData {
        public final Holder<GreatHornVariant> variant;

        public GreatHornData(Holder<GreatHornVariant> variant) {
            super(0.075f);
            this.variant = variant;
        }
    }

    public static boolean canSpawn(EntityType<GreatHornEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor world, MobSpawnType spawnReason) {
        return true;
    }
}
