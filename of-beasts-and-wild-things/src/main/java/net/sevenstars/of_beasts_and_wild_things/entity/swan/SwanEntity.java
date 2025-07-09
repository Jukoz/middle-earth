package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
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
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class SwanEntity extends AnimalEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(SwanEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public SwanEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSwanAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MAX_HEALTH, 10.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.ATTACK_DAMAGE, 1.5)
                .add(EntityAttributes.ATTACK_SPEED, 1.0);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("swanBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("swanActivityUpdate");
        SwanBrain.updateActivities(this);
        profiler.pop();

        this.updateFloating();
        super.mobTick(world);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return SwanBrain.create(this, dynamic);
    }

    public Brain<SwanEntity> getBrain() {
        return (Brain<SwanEntity>)super.getBrain();
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

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        SwanEntity child = ModEntities.SWAN.create(world, SpawnReason.BREEDING);
        int i = this.random.nextInt(2);

        SwanEntityVariant variant = i == 0 ? this.getVariant() : ((SwanEntity)entity).getVariant();
        if(child != null) {
            child.setVariant(variant);
            return child;
        }

        return null;
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return state.isIn(FluidTags.WATER);
    }

    private void updateFloating() {
        if (this.isTouchingWater()) {
            ShapeContext shapeContext = ShapeContext.of(this);
            if (!shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, this.getBlockPos(), true) || this.getWorld().getFluidState(this.getBlockPos().up()).isIn(FluidTags.WATER)) {
                this.setVelocity(this.getVelocity().multiply(0.5).add(0.0, 0.05, 0.0));
            } else {
                this.setOnGround(true);
            }
        }
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        SwanEntityVariant variant = Util.getRandom(SwanEntityVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public SwanEntityVariant getVariant() {
        return SwanEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(SwanEntityVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 2.0F);
    }
}
