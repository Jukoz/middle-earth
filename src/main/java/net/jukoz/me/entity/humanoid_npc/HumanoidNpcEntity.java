package net.jukoz.me.entity.humanoid_npc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Dynamic;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.barrow_wights.BarrowWightEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.jetbrains.annotations.Nullable;

import javax.print.attribute.standard.Destination;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

public class HumanoidNpcEntity extends PathAwareEntity {

    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;
    private static final ImmutableList<SensorType<? extends Sensor<? super HumanoidNpcEntity>>> SENSORS;
    private static final TrackedData<Integer> EMOTION_STATE;
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<HumanoidNpcEntity, RegistryEntry<PointOfInterestType>>> POINTS_OF_INTEREST;

    public HumanoidNpcEntity(EntityType<? extends HumanoidNpcEntity> entityType, World world) {
        super(entityType, world);
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
        this.getNavigation().setCanSwim(true);
        this.setCanPickUpLoot(true);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(EMOTION_STATE,HumanoidNpcEmotionStates.Idle.getOrder());
    }

    public Brain<HumanoidNpcEntity> getBrain() {
        return (Brain<HumanoidNpcEntity>) super.getBrain();
    }

    protected Brain.Profile<HumanoidNpcEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        Brain<HumanoidNpcEntity> brain = this.createBrainProfile().deserialize(dynamic);
        this.initBrain(brain);
        return brain;
    }

    public void reinitializeBrain(ServerWorld world) {
        Brain<HumanoidNpcEntity> brain = this.getBrain();
        brain.stopAllTasks(world, this);
        this.brain = brain.copy();
        this.initBrain(this.getBrain());
    }

    private void initBrain(Brain<HumanoidNpcEntity> brain) {
        brain.setTaskList(Activity.CORE, HumanoidNpcTaskListProvider.createCoreTasks(0.8F));
        brain.setTaskList(Activity.IDLE, HumanoidNpcTaskListProvider.createIdleTask(0.8F, ModEntities.HOBBIT));
        brain.setTaskList(Activity.REST, HumanoidNpcTaskListProvider.createRestTasks(0.8F));
    /*
            Schedule schedule = new Schedule();
        ScheduleBuilder scheduleBuilder = new ScheduleBuilder(schedule)
                .withActivity(10, Activity.IDLE)
                .withActivity(2000, Activity.WORK)
                .withActivity(9000, Activity.MEET)
                .withActivity(11000, Activity.IDLE)
                .withActivity(12000, Activity.REST);
                        brain.setSchedule(scheduleBuilder.build());

     */

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.doExclusively(Activity.IDLE);
        brain.refreshActivities(this.getWorld().getTimeOfDay(), this.getWorld().getTime());
    }

    private void releaseAllTickets() {
        this.releaseTicketFor(MemoryModuleType.HOME);
        this.releaseTicketFor(MemoryModuleType.JOB_SITE);
        this.releaseTicketFor(MemoryModuleType.POTENTIAL_JOB_SITE);
        this.releaseTicketFor(MemoryModuleType.MEETING_POINT);
    }

    public void releaseTicketFor(MemoryModuleType<GlobalPos> pos) {
        if (this.getWorld() instanceof ServerWorld) {
            MinecraftServer minecraftServer = ((ServerWorld)this.getWorld()).getServer();
            this.brain.getOptionalRegisteredMemory(pos).ifPresent((posx) -> {
                ServerWorld serverWorld = minecraftServer.getWorld(posx.getDimension());
                if (serverWorld != null) {
                    PointOfInterestStorage pointOfInterestStorage = serverWorld.getPointOfInterestStorage();
                    Optional<RegistryEntry<PointOfInterestType>> optional = pointOfInterestStorage.getType(posx.getPos());
                    BiPredicate<HumanoidNpcEntity, RegistryEntry<PointOfInterestType>> biPredicate = POINTS_OF_INTEREST.get(pos);
                    if (optional.isPresent() && biPredicate.test(this, optional.get())) {
                        pointOfInterestStorage.releaseTicket(posx.getPos());
                        DebugInfoSender.sendPointOfInterest(serverWorld, posx.getPos());
                    }

                }
            });
        }
    }

    protected void mobTick() {
        this.getWorld().getProfiler().push("humanoidNpcBrain");
        this.getBrain().tick((ServerWorld)this.getWorld(), this);
        this.getWorld().getProfiler().pop();

        super.mobTick();
    }

    protected void pushWorldProfiler(){
        this.getWorld().getProfiler().push("humanoidNpcBrain");
    }

    public void tick() {
        BlockPos currentBlockPos = null;
        long dayTime = this.getWorld().getTimeOfDay();

        if(dayTime < 1000 || dayTime > 12000) {
            if(!isSleeping()) {
                this.dataTracker.set(EMOTION_STATE, HumanoidNpcEmotionStates.Sleeping.getOrder());
                currentBlockPos = getBlockPos();
                this.setSleepingPosition(currentBlockPos);
            }
        }
        if (isClient()) {
            float healthPercent = 100 / this.getMaxHealth() * this.getHealth(); // 100 / max * amount = percent
            if(dayTime > 1000 || dayTime < 12000){
                if(this.getSleepingPosition().isPresent()){
                    //this.clearSleepingPosition();
                } else {
                    if(healthPercent < 80) {
                        this.dataTracker.set(EMOTION_STATE, HumanoidNpcEmotionStates.Hurt.getOrder());
                    }
                    else {
                        this.dataTracker.set(EMOTION_STATE, HumanoidNpcEmotionStates.Idle.getOrder());
                    }
                }
            }
        }
        super.tick();
        if(currentBlockPos != null){
            this.setPos(currentBlockPos.getX(), currentBlockPos.getY(), currentBlockPos.getZ());
        }
    }

    public boolean isSleeping(){
        return HumanoidNpcEmotionStates.values()[this.dataTracker.get(EMOTION_STATE)] == HumanoidNpcEmotionStates.Sleeping;
    }

    public boolean isClient() {
        return this.getWorld().isClient;
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isSleeping()) {
            return null;
        } else {
            return SoundEvents.ENTITY_VILLAGER_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }


    public void onDeath(DamageSource damageSource) {
        MiddleEarth.LOGGER.info("Humanoid {} died, message: '{}'", this, damageSource.getDeathMessage(this).getString());
        Entity entity = damageSource.getAttacker();
        if (entity != null) {
            this.notifyDeath(entity);
        }
        this.releaseAllTickets();

        super.onDeath(damageSource);
    }

    private void notifyDeath(Entity killer) {
        World var3 = this.getWorld();
        if (var3 instanceof ServerWorld serverWorld) {
            Optional optional = this.brain.getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_MOBS);
            if (!optional.isEmpty()) {
                LivingTargetCache livingTargetCache = (LivingTargetCache)optional.get();
                Objects.requireNonNull(InteractionObserver.class);
                livingTargetCache.iterate(InteractionObserver.class::isInstance).forEach((observer) -> {
                    serverWorld.handleInteraction(EntityInteraction.VILLAGER_KILLED, killer, (InteractionObserver)observer);
                });
            }
        }
    }

    public void sleep(BlockPos pos) {
        super.sleep(pos);

        this.brain.remember(MemoryModuleType.LAST_SLEPT, this.getWorld().getTime());
        this.brain.forget(MemoryModuleType.WALK_TARGET);
        this.brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void wakeUp() {
        super.wakeUp();
        this.brain.remember(MemoryModuleType.LAST_WOKEN, this.getWorld().getTime());
    }

    public HumanoidNpcEmotionStates getEmotionState(){
        return HumanoidNpcEmotionStates.values()[this.dataTracker.get(EMOTION_STATE)];
    }

    static {
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.HOME,
                MemoryModuleType.MEETING_POINT,
                MemoryModuleType.MOBS,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.NEAREST_PLAYERS,
                MemoryModuleType.NEAREST_VISIBLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
                MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.INTERACTION_TARGET,
                MemoryModuleType.PATH,
                MemoryModuleType.DOORS_TO_CLOSE,
                MemoryModuleType.NEAREST_BED,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.NEAREST_HOSTILE,
                MemoryModuleType.SECONDARY_JOB_SITE,
                MemoryModuleType.HIDING_PLACE,
                MemoryModuleType.HEARD_BELL_TIME,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.LAST_SLEPT,
                MemoryModuleType.LAST_WOKEN,
                MemoryModuleType.LAST_WORKED_AT_POI,
                MemoryModuleType.GOLEM_DETECTED_RECENTLY
        );

        SENSORS = ImmutableList.of(
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_ITEMS,
                SensorType.NEAREST_BED,
                SensorType.HURT_BY
        );

        EMOTION_STATE = DataTracker.registerData(HumanoidNpcEntity.class, TrackedDataHandlerRegistry.INTEGER);

        POINTS_OF_INTEREST = ImmutableMap.of(MemoryModuleType.HOME, (villager, registryEntry) -> {
            return registryEntry.matchesKey(PointOfInterestTypes.HOME);
        });
    }
}
