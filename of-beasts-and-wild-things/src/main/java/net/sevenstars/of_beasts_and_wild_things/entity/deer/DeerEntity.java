package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.sound.SoundsWT;
import org.jetbrains.annotations.Nullable;

public class DeerEntity extends AnimalEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(DeerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public DeerEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.getNavigation().setCanSwim(true);
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
        return EntitiesWT.DEER.create(world, SpawnReason.BREEDING);
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        DeerEntityVariant variant = Util.getRandom(DeerEntityVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
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
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 1.0f, 1.0f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
    }

    @Override
    public void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(VARIANT, view.getInt("Variant", 0));
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
