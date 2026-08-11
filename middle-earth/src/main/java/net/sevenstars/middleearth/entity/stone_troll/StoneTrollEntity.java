package net.sevenstars.middleearth.entity.stone_troll;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.conversion.EntityConversionContext;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

// TODO ADD BRAIN
// TODO ADD ANIMATIONS
public class StoneTrollEntity extends PathAwareEntity {
    public static final TrackedData<Integer> PETRIFYING = DataTracker.registerData(StoneTrollEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final List<RegistryKey<Biome>> darkBiomes = List.of(

    );
    private final int PETRIFYING_DURATION = 600;

    //region Init
    public StoneTrollEntity(EntityType<? extends StoneTrollEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 100.0f)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.15f)
                .add(EntityAttributes.FOLLOW_RANGE, 13.0f)
                .add(EntityAttributes.STEP_HEIGHT, 1.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 10)
                .add(EntityAttributes.ATTACK_SPEED, 0.65f)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.6);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(PETRIFYING, PETRIFYING_DURATION);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return StoneTrollBrain.create(this, dynamic);
    }

    public Brain<StoneTrollEntity> getBrain() {
        return (Brain<StoneTrollEntity>)super.getBrain();
    }

    //endregion

    //region Tick-based methods
    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("stoneTrollBrain");
        this.getBrain().tick(world, this);
        profiler.swap("caveTrollActivityUpdate");
        StoneTrollBrain.updateActivities(this);
        profiler.pop();

        super.mobTick(world);
    }

    @Override
    public void tickMovement() {
        if (this.isAlive() && !this.getWorld().isClient() && this.getPetrifying() != -1 && this.getWorld().getBiome(this.getBlockPos()).getKey().isPresent()) {
            RegistryKey<Biome> biomeKey = this.getWorld().getBiome(this.getBlockPos()).getKey().get();

            boolean inDaylight = this.isAffectedByDaylight() && !darkBiomes.contains(biomeKey);

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
            this.getWorld().addParticleClient(ParticleTypes.LARGE_SMOKE, this.getX() + ((random.nextFloat() * 2f) - 1f), this.getY() + 1d + random.nextFloat(), this.getZ() + ((random.nextFloat() * 2f) - 1f), random.nextFloat() / 8.0f, 0.2f, random.nextFloat() / 8.0f);
        }

        super.tickMovement();
    }
    //endregion

    //region Getters/Setters
    public void setPetrifying(int petrifying) {
        this.dataTracker.set(PETRIFYING, petrifying);
    }
    public int getPetrifying() {
        return this.dataTracker.get(PETRIFYING);
    }
    public boolean isPetrified() {
        return this.dataTracker.get(PETRIFYING) == -1;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return getTargetInBrain();
    }
    //endregion


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



    public void turnToStone() {
        this.setAiDisabled(true);
        this.convertTo(
                EntitiesME.PETRIFIED_TROLL,
                EntityConversionContext.create(this, true, false),
                troll -> {}
        );
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("Petrifying", this.getPetrifying());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(PETRIFYING, view.getInt("Petrifying", 0));
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        if(!this.isPetrified()) {
            super.onDamaged(damageSource);
        }
    }

    public static boolean canSpawn(EntityType<StoneTrollEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }
}
