package net.jukoz.me.entity.beasts.trolls.hill;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
}
