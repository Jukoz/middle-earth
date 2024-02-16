package net.jukoz.me.entity.beasts.trolls.hill;

import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HillTrollEntity extends TrollEntity {
    public static final TrackedData<Integer> PETRIFYING = DataTracker.registerData(HillTrollEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public HillTrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(2, new EscapeSunlightGoal(this, 1.2d));
        this.goalSelector.add(4, new ChargeAttackGoal(this, 400));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(6, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((BeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((BeastEntity)this));
        this.targetSelector.add(3, new TargetPlayerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PETRIFYING, 100);
    }

    public void setPetrifying(int petrifying) {
        this.dataTracker.set(PETRIFYING, petrifying);
    }
    public int getPetrifying() {
        return this.dataTracker.get(PETRIFYING);
    }
    public boolean isPetrified() {
        return this.dataTracker.get(PETRIFYING) == -1;
    }

    @Override
    public boolean canThrow() {
        return !this.isPetrified();
    }

    @Override
    public boolean canCharge() {
        return !this.isPetrified();
    }

    @Override
    public Item getBondingItem() {
        if(!this.isPetrified()) {
            return super.getBondingItem();
        }
        return null;
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
        if (this.isAlive() && !this.getWorld().isClient() && this.getPetrifying() != -1) {
            boolean inDaylight = this.isAffectedByDaylight() && this.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
            if (inDaylight) {
                this.setPetrifying(this.getPetrifying() - 1);
                if(this.getPetrifying() <= 0) {
                    this.turnToStone();
                }
            }
            else {
                this.setPetrifying(100);
            }
        }
        if(getPetrifying() != -1 && getPetrifying() < 100 && this.getWorld().isClient() && this.age % 3 == 0) {
            this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + ((random.nextFloat() * 2f) - 1f), this.getY() + 1d + random.nextFloat(), this.getZ() + ((random.nextFloat() * 2f) - 1f), random.nextFloat() / 8.0f, 0.2f, random.nextFloat() / 8.0f);
        }
        super.tickMovement();
    }

    public void turnToStone() {
        this.setPetrifying(-1);
        this.setAiDisabled(true);
        this.setTame(false);
        this.setOwner(null);
        if(!this.getPassengerList().isEmpty()) {
            for(Entity entity : this.getPassengerList()) {
                entity.dismountVehicle();
            }
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Petrifying", this.getPetrifying());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(PETRIFYING, nbt.getInt("Petrifying"));
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        if(!this.isPetrified()) {
            super.onDamaged(damageSource);
        }
    }
}
