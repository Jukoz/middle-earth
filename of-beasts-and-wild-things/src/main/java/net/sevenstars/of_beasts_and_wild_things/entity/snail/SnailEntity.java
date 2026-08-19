package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import org.jetbrains.annotations.Nullable;

public class SnailEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SnailEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(SnailEntity.class, EntityDataSerializers.BOOLEAN);
    public static final int CLIMBING_TIME_TRANSITION = 12;
    private int climbingTicks = 0;

    public SnailEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createSnailAttributes() {
        return net.minecraft.world.entity.Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.05f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.ARMOR, 0.5f);
    }

    protected void customServerAiStep() {
        ServerLevel world = (ServerLevel) this.level();
        ProfilerFiller profiler = this.level().getProfiler();
        profiler.push("snailBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("snailActivityUpdate");
        SnailBrain.updateActivities(this);
        profiler.pop();
        super.customServerAiStep();
    }

    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return SnailBrain.create(this, dynamic);
    }

    public Brain<SnailEntity> getBrain() {
        return (Brain<SnailEntity>)super.getBrain();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbingWall(this.horizontalCollision);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(isClimbingWall()) {
            this.setDeltaMovement(this.getDeltaMovement().x(),this.getSpeed() / 5,this.getDeltaMovement().z());
            this.climbingTicks = Math.min(CLIMBING_TIME_TRANSITION, this.climbingTicks + 1);
        }
        else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }
    }

    public void setClimbingWall(boolean climbing) {
        this.entityData.set(CLIMBING, climbing);
    }

    public boolean isClimbingWall() {
        return entityData.get(CLIMBING);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    @Override
    public void jumpFromGround() {
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return EntitiesWT.SNAIL.create(world);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                 @Nullable SpawnGroupData entityData) {
        SnailEntityVariant variant = Util.getRandom(SnailEntityVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(CLIMBING, false);
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

    public SnailEntityVariant getVariant() {
        return SnailEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(SnailEntityVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }
}
