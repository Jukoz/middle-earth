package net.jukoz.me.entity.beasts.broadhoof;

import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.entity.beasts.warg.WargVariant;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BroadhoofGoatEntity extends AbstractBeastEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> HORNS = DataTracker.registerData(BroadhoofGoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public BroadhoofGoatEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25d)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0d)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.4d)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0d)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0d)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0d)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.15d)
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 6.0d);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(HORNS, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putInt("Horns", this.getTypeHorns());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        this.dataTracker.set(HORNS, nbt.getInt("Horns"));
    }

    /* VARIANTS */
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        BroadhoofGoatVariant variant = Util.getRandom(BroadhoofGoatVariant.values(), this.random);
        this.setVariant(variant);

        BroadhoofGoatHorns horns = Util.getRandom(BroadhoofGoatHorns.values(), this.random);
        this.setHorns(horns);

        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public BroadhoofGoatVariant getVariant() {
        return BroadhoofGoatVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(BroadhoofGoatVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    public BroadhoofGoatHorns getHorns() {
        return BroadhoofGoatHorns.byId(this.getTypeHorns() & 255);
    }
    private int getTypeHorns() {
        return this.dataTracker.get(HORNS);
    }

    private void setHorns(BroadhoofGoatHorns horns) {
        this.dataTracker.set(HORNS, horns.getId() & 255);
    }

    public boolean hasRightHorn() {
        return true;
    }

    public boolean hasLeftHorn() {
        return true;
    }
}
