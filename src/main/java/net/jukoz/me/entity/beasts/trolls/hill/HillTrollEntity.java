package net.jukoz.me.entity.beasts.trolls.hill;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HillTrollEntity extends TrollEntity {
    public static final TrackedData<Boolean> STONED = DataTracker.registerData(TrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int turnToStoneTime = 100;

    public HillTrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STONED, false);
    }

    public void setStoned(boolean stoned) {
        this.dataTracker.set(STONED, stoned);
    }
    public boolean isStoned() {
        return  this.dataTracker.get(STONED);
    }

    @Override
    public boolean canThrow() {
        return !this.isStoned();
    }

    @Override
    public boolean canCharge() {
        return !this.isStoned();
    }

    @Override
    protected boolean isAffectedByDaylight() {
        if (this.getWorld().isDay() && !this.getWorld().isClient()) {
            float f = this.getBrightnessAtEyes();
            BlockPos blockPos = BlockPos.ofFloored(this.getX(), this.getEyeY(), this.getZ());
            return f > 0.5f && this.getWorld().isSkyVisible(blockPos);
        }
        return false;
    }

    @Override
    public void tickMovement() {
        if (this.isAlive() && !this.getWorld().isClient()) {
            boolean inDaylight = this.isAffectedByDaylight() && this.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
            if (inDaylight) {
                --this.turnToStoneTime;
                if(this.turnToStoneTime <= 0) {
                    this.turnToStone();
                }
            }
            if(!inDaylight && this.turnToStoneTime != 100) {
                this.turnToStoneTime = 100;
            }
        }
        super.tickMovement();
    }

    public void turnToStone() {
        this.setAiDisabled(true);
        this.setStoned(true);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Petrified", this.isStoned());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(STONED, nbt.getBoolean("Petrified"));
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        if(!this.isStoned()) {
            super.onDamaged(damageSource);
        }
    }
}
