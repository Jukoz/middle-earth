package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantBrain;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntity;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityVariant;
import org.jetbrains.annotations.Nullable;

public class DeerEntity extends AnimalEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(DeerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public DeerEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createDeerAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MAX_HEALTH, 10.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3);
    }

    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("deerBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("deerActivityUpdate");
        DeerBrain.updateActivities(this);
        profiler.pop();
        super.mobTick(world);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return DeerBrain.create(this, dynamic);
    }

    public Brain<DeerEntity> getBrain() {
        return (Brain<DeerEntity>)super.getBrain();
    }

    @Override
    public boolean isInvulnerableTo(ServerWorld world, DamageSource source) {
        return super.isInvulnerableTo(world, source) || source.isOf(DamageTypes.SWEET_BERRY_BUSH);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.DEER.create(world, SpawnReason.BREEDING);
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        DeerEntityVariant variant = Util.getRandom(DeerEntityVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
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

    public DeerEntityVariant getVariant() {
        return DeerEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(DeerEntityVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }
}
