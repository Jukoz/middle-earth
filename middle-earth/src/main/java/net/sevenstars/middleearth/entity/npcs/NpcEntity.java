package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.features.beards.BeardTypes;
import org.jetbrains.annotations.Nullable;


public class NpcEntity extends PassiveEntity {
    private static final TrackedData<Byte> BEARD_TYPE; // 0 = none

    public NpcEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.dataTracker.set(BEARD_TYPE, (byte)0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 15.0f));
    }

    // region NBT & DataTrackers
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(BEARD_TYPE,  (byte)0);
    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("BeardType", this.getBeardType());
    }
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(BEARD_TYPE, nbt.getByte("BeardType"));
    }
    public Byte getBeardType() {
        return this.dataTracker.get(BEARD_TYPE);
    }

    public BeardTypes getBeardTypeValue() {
        return BeardTypes.values()[this.dataTracker.get(BEARD_TYPE)];
    }

    static {
        BEARD_TYPE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
    // endregion

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createLivingAttributes()
                .add(EntityAttributes.MAX_HEALTH, 8.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.FOLLOW_RANGE, 35.0)
                ;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
