package net.sevenstars.middleearth.block.special.structureManager.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BedBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.entity.Entity;
import net.sevenstars.middleearth.entity.EntitiesME;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.registries.content.npcroles.NpcRoles;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class SpawnNestManager {
    public static final Codec<SpawnNestManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(SpawnNestManager::getId),
            Codec.list(Uuids.CODEC).fieldOf("entity_uuid").forGetter(SpawnNestManager::getEntityUuids),
            Codec.LONG.fieldOf("respawn_event_trigger_tick").forGetter(SpawnNestManager::getRespawnEventTriggerTick),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestManager::getRespawnTickDelay),
            BlockPos.CODEC.fieldOf("origin_pos").forGetter(SpawnNestManager::getOriginPos),
            Codec.INT.fieldOf("spawn_radius").forGetter(SpawnNestManager::getSpawnRadius),
            Codec.BOOL.optionalFieldOf("surveyed", false).forGetter(manager -> manager.surveyed),
            Codec.list(NestSurvey.Bed.CODEC).optionalFieldOf("beds", List.of()).forGetter(manager -> manager.beds),
            BlockPos.CODEC.optionalFieldOf("store").forGetter(manager -> Optional.ofNullable(manager.store)),
            Codec.BOOL.optionalFieldOf("paired", false).forGetter(manager -> manager.paired)
    ).apply(instance, SpawnNestManager::new));

    private static final String ID = "spawn_nest_data";

    private Identifier id;
    private ArrayList<UUID> entities;
    private long respawnEventTriggerTick;
    private int respawnTickDelay;
    private BlockPos originPos;
    private int spawnRadius;

    // Found once when the nest registers, and kept with it
    private boolean surveyed = false;
    private List<NestSurvey.Bed> beds = new ArrayList<>();
    private @Nullable BlockPos store = null;
    // True when the beds are this nest's alone, as the generated data has them. Beds found by looking around are shared with the other nests of the building
    private boolean paired = false;

    public SpawnNestManager(Identifier dataId, List<UUID> dataEntities, long dataRespawnEventTriggerTick, int dataRespawnTickDelay, BlockPos position, int spawnRadius, boolean surveyed, List<NestSurvey.Bed> beds, Optional<BlockPos> store, boolean paired) {
        this.paired = paired;
        this.surveyed = surveyed;
        this.beds = new ArrayList<>(beds);
        this.store = store.orElse(null);
        this.id = dataId;
        this.entities =  Lists.newArrayList();
        this.entities.addAll(dataEntities);
        this.respawnEventTriggerTick = dataRespawnEventTriggerTick;
        this.respawnTickDelay = dataRespawnTickDelay;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, BlockPos position, int spawnRadius) {
        this.entities = new ArrayList<UUID>();
        this.id = spawnNestNodeData.getId();
        this.respawnTickDelay = spawnNestNodeData.getRespawnTickDelay();
        // The delay is for coming back after a defeat, a new settlement is lived in from the start
        this.respawnEventTriggerTick = -spawnNestNodeData.getRespawnTickDelay();
        this.originPos = position;
        this.spawnRadius = spawnRadius;
    }

    public Identifier getId() {
        return this.id;
    }

    public ArrayList<UUID> getEntityUuids() {
        return entities;
    }

    private long getRespawnEventTriggerTick() {
        return this.respawnEventTriggerTick;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }
    public BlockPos getOriginPos() {
        return this.originPos;
    }
    public int getSpawnRadius() {
        return this.spawnRadius;
    }

    // True once the building has been looked through, which needs it loaded
    public boolean survey(ServerWorld world) {
        if(surveyed)
            return true;
        NestSurvey.Result result = NestSurvey.fromData(world, originPos);
        paired = result != null;
        if(result == null)
            result = NestSurvey.survey(world, originPos);
        if(result == null)
            return false;
        beds = new ArrayList<>(result.beds());
        store = result.store().orElse(null);
        surveyed = true;
        return true;
    }

    public List<NestSurvey.Bed> getBeds() {
        return beds;
    }

    public @Nullable BlockPos getStore() {
        return store;
    }

    public @Nullable NestSurvey.Bed bedOf(UUID owner) {
        for(NestSurvey.Bed bed : beds) {
            if(bed.owner().isPresent() && bed.owner().get().equals(owner))
                return bed;
        }
        return null;
    }

    // A bed nobody of this nest has, and that no other nest of the building gave away
    public @Nullable NestSurvey.Bed claimBed(UUID owner, Predicate<BlockPos> takenElsewhere) {
        NestSurvey.Bed own = bedOf(owner);
        if(own != null)
            return own;
        for(int i = 0; i < beds.size(); i++) {
            NestSurvey.Bed bed = beds.get(i);
            if(bed.owner().isEmpty() && !takenElsewhere.test(bed.pos())) {
                beds.set(i, bed.withOwner(owner));
                return beds.get(i);
            }
        }
        return null;
    }

    // The floor beside the bed the next npc of this nest will be given
    // Beds nobody has, still standing. One broken since the place was built houses nobody
    private int freeBeds(ServerWorld world, Predicate<BlockPos> takenElsewhere) {
        int free = 0;
        for(NestSurvey.Bed bed : beds) {
            if(bed.owner().isPresent() || takenElsewhere.test(bed.pos()))
                continue;
            if(!world.isChunkLoaded(bed.pos().getX() >> 4, bed.pos().getZ() >> 4) || world.getBlockState(bed.pos()).getBlock() instanceof BedBlock)
                free++;
        }
        return free;
    }

    private @Nullable BlockPos freeBedside(ServerWorld world, Predicate<BlockPos> takenElsewhere) {
        for(int i = 0; i < beds.size(); i++) {
            NestSurvey.Bed bed = beds.get(i);
            if(bed.owner().isPresent() || takenElsewhere.test(bed.pos()))
                continue;
            if(bed.stand().isEmpty())
                beds.set(i, new NestSurvey.Bed(bed.pos(), Optional.ofNullable(NestSurvey.standFor(world, bed.pos())), Optional.empty()));
            return beds.get(i).stand().orElse(null);
        }
        return null;
    }

    private void releaseBed(UUID owner) {
        for(int i = 0; i < beds.size(); i++) {
            NestSurvey.Bed bed = beds.get(i);
            if(bed.owner().isPresent() && bed.owner().get().equals(owner))
                beds.set(i, bed.withOwner(null));
        }
    }

    public void addEntity(LivingEntity entity){
        if(entity.getEntityWorld().isClient())
            return;

        UUID uuid = entity.getUuid();
        if(this.entities == null)
            this.entities = new ArrayList<UUID>();

        this.entities.add(uuid);
    }

    public boolean removeEntity(LivingEntity entity){
        if(entity.getEntityWorld().isClient() || !this.entities.contains(entity.getUuid()))
            return false;
        this.entities.remove(entity.getUuid());
        releaseBed(entity.getUuid());
        if(this.entities.isEmpty()){
            beginRespawnSequence(entity.getEntityWorld());
        }
        return true;
    }
    public void removeEntity(World world, UUID uuid){
        if(world.isClient() || !this.entities.contains(uuid))
            return;
        this.entities.remove(uuid);
        releaseBed(uuid);
        if(this.entities.isEmpty()){
            beginRespawnSequence(world);
        }
    }

    private void beginRespawnSequence(World world) {
        this.respawnEventTriggerTick = world.getTime();
    }

    public boolean canRespawn(long time){
        return (entities.isEmpty() && time > respawnEventTriggerTick + respawnTickDelay);
    }

    public void tick(StructureManagerData structureManagerData, long currentTick, ServerWorld world, BlockPos sourcePos) {
        if(canRespawn(currentTick)){
            respawnAll(structureManagerData, world, sourcePos);
        }
    }

    public void doWellnessCheck(StructureManagerData structureManagerData, World world, BlockPos sourcePos) {
        if(entities != null && !entities.isEmpty()){
            List<UUID> toRemove = new ArrayList<>();
            for (UUID uuid : entities){ // Wellness check
                var entity = world.getEntity(uuid);
                if(entity == null || !entity.isAlive())
                    toRemove.add(uuid);
            }
            for (UUID uuid : toRemove)
                removeEntity(world, uuid);
        }
    }


    private void respawnAll(StructureManagerData structureManagerData, ServerWorld world, BlockPos structureManagerPos) {
        if(structureManagerData == null)
            return;
        SpawnNestNodeData data = structureManagerData.findSpawnNest(id);

        if(data != null){
            StructureSpawnNestPool pool = data.getRandomPool();
            boolean people = pool.getEntityType() == EntitiesME.NPC && survey(world);
            Predicate<BlockPos> takenElsewhere = world.getBlockEntity(structureManagerPos) instanceof StructureManagerBlockEntity manager
                    ? bed -> manager.isBedTakenElsewhere(bed, this) : bed -> false;
            // People are spawned from beds: a nest houses whom it has a free bed for, and nobody without one. Beasts need none
            int entityAmountToSpawn = pool.getEntityAmount();
            if(pool.getEntityType() == EntitiesME.NPC) {
                // The building is not loaded far enough to know its beds, which is asked again on the next tick
                if(!people)
                    return;
                entityAmountToSpawn = Math.min(entityAmountToSpawn, freeBeds(world, takenElsewhere));
            }
            if(entityAmountToSpawn == 0) {
                // Looked at again after the usual wait, by when a bed may have been put in
                this.respawnEventTriggerTick = world.getTime();
                return;
            }
            for(int i = 0; i < entityAmountToSpawn; i ++){
                BlockPos bedside = people ? freeBedside(world, takenElsewhere) : null;
                LivingEntity entityToAdd = bedside != null ? StructureManagerService.spawnEntityAt(world, pool, bedside)
                        : StructureManagerService.spawnEntity(world, pool, originPos, spawnRadius);
                if(entityToAdd instanceof NpcEntity npcEntity){
                    Optional<Identifier> role = data.getRole().isPresent() ? data.getRole() : householdRole(world, structureManagerPos, npcEntity);
                    role.ifPresent(npcEntity::takeRole);
                    npcEntity.assignStructureManager((StructureManagerBlockEntity) world.getBlockEntity(structureManagerPos));
                }
                if(entityToAdd != null)
                    addEntity(entityToAdd);
                if(entityToAdd instanceof NpcEntity npcEntity && world.getBlockEntity(structureManagerPos) instanceof StructureManagerBlockEntity manager)
                    manager.assignBed(npcEntity);
            }
            world.markDirty(structureManagerPos);
        }
        this.respawnEventTriggerTick = -1;
    }

    // A civilian whose nest gives it no role takes up what the household has the means for, unless somebody living here has already
    private Optional<Identifier> householdRole(ServerWorld world, BlockPos structureManagerPos, NpcEntity npc) {
        NpcType type = npc.getNpcType();
        if(type == null || !type.isCivilian())
            return Optional.empty();
        RoutineSite site = RoutineSites.forManager(world, structureManagerPos);
        if(site == null)
            return Optional.empty();
        return NpcRoles.forHousehold(site, originPos, type, role -> entities.stream()
                .anyMatch(uuid -> world.getEntity(uuid) instanceof NpcEntity resident && resident.isAlive() && role.equals(resident.getRoleId())));
    }

    public boolean computeDeath(LivingEntity entity) {
        if (removeEntity(entity)) {
            return true;
        }
        return false;
    }

    public void forceRespawn(StructureManagerData structureManagerData, ServerWorld world, BlockPos structureManagerPos) {
        for(var uuid : getEntityUuids()){
            if(world.getEntity(uuid) instanceof LivingEntity livingEntity){
                livingEntity.setRemoved(Entity.RemovalReason.DISCARDED);
                MiddleEarth.LOGGER.logDebugMsg("Removed %s".formatted(uuid));
            }
        }
        entities = new ArrayList<UUID>();
        for(int i = 0; i < beds.size(); i++)
            beds.set(i, beds.get(i).withOwner(null));

        refreshBeds(structureManagerData, world);
        respawnAll(structureManagerData, world, structureManagerPos);
    }

    public void refreshBeds(StructureManagerData structureManagerData, World world){
        // TODO : Connect with the @StructureManagerBlockEntity.fetchBeds() / Redistribute

        BlockPos origin = getOriginPos();
        int bedRadius = 10;
        List<BlockPos> bedBlockPositions = new ArrayList<>();
        BlockPos.findClosest(origin, bedRadius, 5, new Predicate<BlockPos>() {
            @Override
            public boolean test(BlockPos blockPos) {
                var blockState = world.getBlockState(blockPos);
                if(blockState.getBlock() instanceof BedBlock bedBlock){
                    if(BedBlock.getBedPart(blockState) == DoubleBlockProperties.Type.FIRST){
                        bedBlockPositions.add(blockPos);
                    }
                }
                return false;
            }
        });
    }
}

