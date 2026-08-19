package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.sound.SoundsWT;
import org.jetbrains.annotations.Nullable;

public class DeerEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.INT);

    public DeerEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.getNavigation().setCanFloat(true);
    }

    public static AttributeSupplier.Builder createDeerAttributes() {
        return net.minecraft.world.entity.Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    protected void customServerAiStep() {
        ServerLevel world = (ServerLevel) this.level();
        ProfilerFiller profiler = this.level().getProfiler();
        profiler.push("deerBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("deerActivityUpdate");
        DeerBrain.updateActivities(this);
        profiler.pop();
        super.customServerAiStep();
    }

    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return DeerBrain.create(this, dynamic);
    }

    public Brain<DeerEntity> getBrain() {
        return (Brain<DeerEntity>)super.getBrain();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.SWEET_BERRY_BUSH);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return EntitiesWT.DEER.create(world);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                 @Nullable SpawnGroupData entityData) {
        DeerEntityVariant variant = Util.getRandom(DeerEntityVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundsWT.DEER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundsWT.DEER_GRUNT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 1.0f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsWT.DEER_IDLE;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 1.0f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 1.0f, 1.0f);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
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

    public DeerEntityVariant getVariant() {
        return DeerEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(DeerEntityVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }
}
