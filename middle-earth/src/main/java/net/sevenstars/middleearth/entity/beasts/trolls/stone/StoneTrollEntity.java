package net.sevenstars.middleearth.entity.beasts.trolls.stone;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;
import net.sevenstars.middleearth.entity.goals.BeastTargetPlayerGoal;
import net.sevenstars.middleearth.resources.datas.Disposition;

import java.util.List;

public class StoneTrollEntity extends TrollEntity {
    public static final TrackedData<Integer> PETRIFYING = DataTracker.registerData(StoneTrollEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final List<RegistryKey<Biome>> darkBiomes = List.of(

    );
    private final int PETRIFYING_DURATION = 600;

    public StoneTrollEntity(EntityType<? extends StoneTrollEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new AvoidSunlightGoal(this));
        this.goalSelector.add(1, new EscapeSunlightGoal(this, 1.2d));
        this.targetSelector.add(4, new BeastTargetPlayerGoal(this, this.getDisposition()));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(PETRIFYING, PETRIFYING_DURATION);
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

    @Override
    protected Disposition getDisposition() {
        return Disposition.EVIL;
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
    public boolean isBondingItem(ItemStack itemStack) {
        if(!this.isPetrified()) {
            return super.isBondingItem(itemStack);
        }
        return false;
    }

    @Override
    protected boolean isAffectedByDaylight() {
        if (this.getWorld().isDay() && !this.getWorld().isClient()) {
            if(this.getWorld().getBiome(getBlockPos()).isIn(TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_biome_in_darkness")))){
                return false;
            }
            float f = this.getWorld().getBlockState(getBlockPos()).getAmbientOcclusionLightLevel(getWorld(), getBlockPos());
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
        // TODO : Fix this :)
        //this.convertTo(ModEntities.PETRIFIED_TROLL, EntityConversionContext.create(new PetrifiedTrollEntity(ModEntities.PETRIFIED_TROLL, getWorld()), false, false), SpawnReason.CONVERSION, );
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
