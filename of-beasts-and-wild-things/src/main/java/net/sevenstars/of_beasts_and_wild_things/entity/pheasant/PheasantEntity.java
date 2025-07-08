package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class PheasantEntity extends AnimalEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(PheasantEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState diggingAnimationState = new AnimationState();

    public PheasantEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createPheasantAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MAX_HEALTH, 5.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.2);
    }

    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("pheasantBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("pheasantActivityUpdate");
        PheasantBrain.updateActivities(this);
        profiler.pop();
        super.mobTick(world);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return PheasantBrain.create(this, dynamic);
    }

    public Brain<PheasantEntity> getBrain() {
        return (Brain<PheasantEntity>)super.getBrain();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.PHEASANT.create(world, SpawnReason.BREEDING);
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        PheasantEntityVariant variant = Util.getRandom(PheasantEntityVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
    }

    //TODO to test
    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(VARIANT, view.getInt("Variant", 0));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }

        wingFlap();
    }

    private void wingFlap() {
        Vec3d velocity = this.getVelocity();
        if (!this.isOnGround() && velocity.y < 0.0) {
            this.setVelocity(velocity.multiply(1.0, 0.6, 1.0));
        }
    }

    private void setupAnimationStates() {
        if(this.isInPose(EntityPose.DIGGING)) {
          diggingAnimationState.startIfNotRunning(this.age);
        }
        else {
            diggingAnimationState.stop();
        }

        if(this.isInPose(EntityPose.STANDING)) {
            idleAnimationState.startIfNotRunning(this.age);
        }
        else {
            idleAnimationState.stop();
        }
    }

    public PheasantEntityVariant getVariant() {
        return PheasantEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(PheasantEntityVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }
}