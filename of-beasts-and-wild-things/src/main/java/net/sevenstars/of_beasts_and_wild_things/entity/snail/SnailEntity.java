package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

import javax.xml.crypto.Data;

public class SnailEntity extends AnimalEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CLIMBING = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int CLIMBING_TIME_TRANSITION = 12;
    private int climbingTicks = 0;

    public SnailEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSnailAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MAX_HEALTH, 2)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.05f)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 1)
                .add(EntityAttributes.ARMOR, 0.5f);
    }

    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("snailBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("snailActivityUpdate");
        SnailBrain.updateActivities(this);
        profiler.pop();
        super.mobTick(world);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return SnailBrain.create(this, dynamic);
    }

    public Brain<SnailEntity> getBrain() {
        return (Brain<SnailEntity>)super.getBrain();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(isClimbingWall()) {
            this.setVelocity(this.getVelocity().getX(),this.getMovementSpeed() / 5,this.getVelocity().getZ());
            this.climbingTicks = Math.min(CLIMBING_TIME_TRANSITION, this.climbingTicks + 1);
        }
        else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }
    }

    public void setClimbingWall(boolean climbing) {
        this.dataTracker.set(CLIMBING, climbing);
    }

    public boolean isClimbingWall() {
        return dataTracker.get(CLIMBING);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    @Override
    public void jump() {
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SNAIL.create(world, SpawnReason.BREEDING);
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        SnailEntityVariant variant = Util.getRandom(SnailEntityVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(CLIMBING, false);
    }

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

    public SnailEntityVariant getVariant() {
        return SnailEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(SnailEntityVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }
}
