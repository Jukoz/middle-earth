/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package net.jesteur.me.entity.barrow_wights;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.BiConsumer;

public class BarrowWightEntity
        extends HostileEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int MAX_HEALTH = 30;
    private static final float MOVEMENT_SPEED = 0.3f;
    private static final float KNOCKBACK_RESISTANCE = 1.0f;
    private static final float ATTACK_KNOCKBACK = 1.5f;
    private static final int ATTACK_DAMAGE = 30;
    private static final TrackedData<Integer> ANGER = DataTracker.registerData(BarrowWightEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public AnimationState attackingAnimationState = new AnimationState();
    public AnimationState screamAnimationState = new AnimationState();

    public BarrowWightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.getNavigation().setCanSwim(true);
        this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0f);
        this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0f);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0f);
    }


    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, this.isInPose(EntityPose.EMERGING) ? 1 : 0);
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return super.canSpawn(world) && world.isSpaceEmpty(this, this.getType().getDimensions().getBoxAt(this.getPos()));
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.0f;
    }


    @Override
    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean disablesShield() {
        return true;
    }

    @Override
    protected float calculateNextStepSoundDistance() {
        return this.distanceTraveled + 0.55f;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, ATTACK_KNOCKBACK).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE);
    }

    @Override
    protected float getSoundVolume() {
        return 4.0f;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WARDEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WARDEN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WARDEN_STEP, 10.0f, 1.0f);
    }

    @Override
    public boolean tryAttack(Entity target) {
        System.out.println("try to attack" + target);
        this.getWorld().sendEntityStatus(this, (byte)4);
        this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 10.0f, this.getSoundPitch());
        HallucinatingWhispersTask.cooldown(this, 40);
        SonicBoomTask.cooldown(this, 40);
        return super.tryAttack(target);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void mobTick() {
        ServerWorld serverWorld = (ServerWorld)this.getWorld();
        serverWorld.getProfiler().push("barrowWightBrain");
        this.getBrain().tick(serverWorld, this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
        BarrowWightBrain.updateActivities(this);
    }



    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        /*if (POSE.equals(data)) {
            switch (this.getPose()) {
                case ROARING: {
                    this.roaringAnimationState.start(this.age);
                    break;
                }
                case SNIFFING: {
                    this.sniffingAnimationState.start(this.age);
                    break;
                }
                case EMERGING: {
                    this.emergingAnimationState.start(this.age);
                    break;
                }
                case DIGGING: {
                    this.diggingAnimationState.start(this.age);
                }
            }
        }*/
        super.onTrackedDataSet(data);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return BarrowWightBrain.create(this, dynamic);
    }

    public Brain<BarrowWightEntity> getBrain() {
        return (Brain<BarrowWightEntity>) super.getBrain();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Override
    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        World world = this.getWorld();
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Contract(value="null->false")
    public boolean isValidTarget(@Nullable Entity entity) {
        if (!(entity instanceof LivingEntity)) return false;
        LivingEntity livingEntity = (LivingEntity)entity;
        if (this.getWorld() != entity.getWorld()) return false;
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity)) return false;
        if (this.isTeammate(entity)) return false;
        if (livingEntity.getType() == EntityType.ARMOR_STAND) return false;
        if (livingEntity.getType() == EntityType.WARDEN) return false;
        if (livingEntity.isInvulnerable()) return false;
        if (livingEntity.isDead()) return false;
        if (!this.getWorld().getWorldBorder().contains(livingEntity.getBoundingBox())) return false;
        return true;
    }



    @Override
    @Nullable
    public LivingEntity getTarget() {
        return this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (spawnReason == SpawnReason.TRIGGERED) {
            this.playSound(SoundEvents.ENTITY_WARDEN_AGITATED, 5.0f, 1.0f);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (!(this.getWorld().isClient || this.isAiDisabled())) {
            Entity entity = source.getAttacker();
            if (this.brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).isEmpty() && entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if (!source.isIndirect() || this.isInRange(livingEntity, 5.0)) {
                    this.updateAttackTarget(livingEntity);
                }
            }
        }
        return bl;
    }

    public void updateAttackTarget(LivingEntity target) {
        this.getBrain().remember(MemoryModuleType.ATTACK_TARGET, target);
        this.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        HallucinatingWhispersTask.cooldown(this, 200);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        EntityDimensions entityDimensions = super.getDimensions(pose);
        return entityDimensions;
    }



    @Override
    protected EntityNavigation createNavigation(World world) {
        return new MobNavigation(this, world){

            @Override
            protected PathNodeNavigator createPathNodeNavigator(int range) {
                this.nodeMaker = new LandPathNodeMaker();
                this.nodeMaker.setCanEnterOpenDoors(true);
                return new PathNodeNavigator(this.nodeMaker, range){

                    @Override
                    protected float getDistance(PathNode a, PathNode b) {
                        return a.getHorizontalDistance(b);
                    }
                };
            }
        };
    }

    public BarrowWightVariant getVariant() {
        return BarrowWightVariant.BASIC;
    }
}