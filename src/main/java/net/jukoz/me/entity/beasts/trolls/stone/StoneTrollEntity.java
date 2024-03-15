package net.jukoz.me.entity.beasts.trolls.stone;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StoneTrollEntity extends TrollEntity {
    public static final TrackedData<Integer> PETRIFYING = DataTracker.registerData(StoneTrollEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final List<RegistryKey<Biome>> darkBiomes = List.of(
            MEBiomeKeys.MORDOR,
            MEBiomeKeys.MORDOR_MOUNTAINS,
            MEBiomeKeys.MORDOR_WASTES
    );
    private final int PETRIFYING_DURATION = 600;

    public StoneTrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(3, new AvoidSunlightGoal(this));
        this.goalSelector.add(4, new EscapeSunlightGoal(this, 1.2d));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(6, new ChargeAttackGoal(this, 400));
        this.goalSelector.add(7, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
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
        this.dataTracker.startTracking(PETRIFYING, PETRIFYING_DURATION);
    }

    @Override
    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.limbAnimator.getSpeed());
        float g = this.limbAnimator.getPos();
        float h = this.isSitting() ? -0.75f : 0;
        return (double)this.getHeight() - 1.0d + (double)(0.12F * MathHelper.cos(g * 1.5F) * 2.0F * f) + h;
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        int i = this.getPassengerList().indexOf(passenger);
        if (i < 0) {
            return;
        }

        float f = -0.5f; // Z-Offset

        Vec3d vec3d = new Vec3d(0.0, 0.0, f).rotateY(-this.bodyYaw * ((float)Math.PI / 180));
        positionUpdater.accept(passenger, this.getX() + vec3d.x, this.getY() + this.getMountedHeightOffset(), this.getZ() + vec3d.z);
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
        return !this.isPetrified() && !this.isSitting();
    }

    @Override
    public boolean canCharge() {
        return !this.isPetrified() && !this.isSitting();
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
        if (this.isAlive() && !this.getWorld().isClient() && this.getPetrifying() != -1 && this.getWorld().getBiome(this.getBlockPos()).getKey().isPresent()) {
            RegistryKey<Biome> biomeKey = this.getWorld().getBiome(this.getBlockPos()).getKey().get();

            boolean inDaylight = this.isAffectedByDaylight() && this.getEquippedStack(EquipmentSlot.CHEST).isEmpty() && !darkBiomes.contains(biomeKey);

            if (inDaylight) {
                this.setPetrifying(this.getPetrifying() - 1);
                if(this.getPetrifying() <= 0) {
                    this.turnToStone();
                }
                if(this.age % 10 == 0) {
                    this.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0f, 1.0f);
                }
            }
            else {
                this.setPetrifying(PETRIFYING_DURATION);
            }
        }
        if(getPetrifying() != -1 && getPetrifying() < PETRIFYING_DURATION && this.getWorld().isClient() && this.age % 3 == 0) {
            this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + ((random.nextFloat() * 2f) - 1f), this.getY() + 1d + random.nextFloat(), this.getZ() + ((random.nextFloat() * 2f) - 1f), random.nextFloat() / 8.0f, 0.2f, random.nextFloat() / 8.0f);
        }

        super.tickMovement();
    }

    public void turnToStone() {
        this.setAiDisabled(true);
        this.convertTo(ModEntities.PETRIFIED_TROLL, true);
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
