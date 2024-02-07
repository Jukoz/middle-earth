package net.jukoz.me.entity.pheasant;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class PheasantEntity extends AnimalEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(PheasantEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    public float flapSpeed = 1.0f;
    private float field_28639 = 1.0f;

    public PheasantEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
        this.goalSelector.add(2, new FleeWhenStartledGoal());
        this.goalSelector.add(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    public static DefaultAttributeContainer.Builder createPheasantAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85f : dimensions.height * 0.92f;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (this.isOnGround() ? -1.0f : 4.0f) * 0.3f;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0f, 1.0f);
        if (!this.isOnGround() && this.flapSpeed < 1.0f) {
            this.flapSpeed = 1.0f;
        }
        this.flapSpeed *= 0.9f;
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
        this.flapProgress += this.flapSpeed * 2.0f;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void addFlapEffects() {
        this.field_28639 = this.speed + this.maxWingDeviation / 2.0f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15f, 1.0f);
    }

    /* VARIANTS */
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        PheasantVariant variant = Util.getRandom(PheasantVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public PheasantVariant getVariant() {
        return PheasantVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(PheasantVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    /* GOALS */
    class FleeWhenStartledGoal extends Goal {
        PheasantEntity pheasant = PheasantEntity.this;
        PlayerEntity player;
        Vec3d vec3d = Vec3d.ZERO;
        Vec3d fleeDir;
        @Nullable
        Path fleePath;
        final EntityNavigation fleeingEntityNavigation;
        boolean flying;

        public FleeWhenStartledGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
            this.fleeingEntityNavigation = pheasant.getNavigation();
        }

        @Override
        public boolean canStart() {
            player = pheasant.getWorld().getClosestPlayer(pheasant, 7.0d);
            if(player == null) {
                return false;
            }

            vec3d = NoPenaltyTargeting.findFrom(this.pheasant, 4, 3, this.player.getPos());
            if (vec3d == null) {
                return false;
            }
            if (this.player.squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < this.player.squaredDistanceTo(this.pheasant)) {
                return false;
            }

            this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
            if(this.fleePath == null) {
                return false;
            }

            boolean wearingCloak =  player.getEquippedStack(EquipmentSlot.CHEST).isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "cloaks")))
                    && player.getEquippedStack(EquipmentSlot.HEAD).isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "cloaks")));

            return !(player == null || wearingCloak || player.isSneaking() || !pheasant.isOnGround());
        }

        @Override
        public void start() {
            fleeDir = vec3d.subtract(pheasant.getPos()).multiply(1,0,1).normalize();
            this.flying = false;
            this.fleeingEntityNavigation.startMovingAlong(this.fleePath, 1.6d);
        }

        @Override
        public void tick() {
            if(this.fleeingEntityNavigation.isIdle() && !this.flying) {
                this.pheasant.setVelocity(fleeDir.multiply(1.7, 0, 1.7).add(0, 0.5, 0));
                this.pheasant.setYaw((float) Math.toDegrees(Math.atan2(-fleeDir.x, fleeDir.z)));
                this.flying = true;
            }
        }

        @Override
        public boolean shouldContinue() {
            return !this.flying;
        }

        @Override
        public void stop() {
            this.player = null;
            this.flying = false;
        }
    }

}
