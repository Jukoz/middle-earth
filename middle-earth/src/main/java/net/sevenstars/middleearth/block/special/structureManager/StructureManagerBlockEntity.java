package net.sevenstars.middleearth.block.special.structureManager;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.block.special.structureManager.features.MarkerAnimals;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.block.special.structureManager.features.SpawnNestManager;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureNestList;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import org.jetbrains.annotations.Nullable;

import net.sevenstars.middleearth.block.special.beds.BedTag;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_manager";
    // In ticks, a second
    private static final int MARKER_CHECK_INTERVAL = 20;
    private static final int GLOW_TICKS = 10 * 20;
    private static final int DAY_TICKS = 24000;
    // The nests are looked over once at dusk and once before dawn
    private static final int DUSK_CHECK_START = 11000;
    private static final int DUSK_CHECK_END = 12000;
    private static final int DAWN_CHECK_START = 23000;
    // Nobody is placed at random around a marker, but on it
    private static final int MARKER_SPAWN_RADIUS = 0;

    private enum SyncedData {
        ENABLED("%s.Enabled".formatted(ID)),
        TO_INITIALIZE("%s.ToInitialize".formatted(ID)),
        SPAWN_NEST_LIST("%s.Nests".formatted(ID)),
        MARKER_ANIMALS("%s.MarkerAnimals".formatted(ID)),
        IDENTIFIER("%s.Identifier".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }

    // Saved
    private boolean enabled = false;
    private boolean toInitialize = false;
    // The place a manager was given before beds and markers kept theirs. Only those tagged before then still go by it
    @Nullable
    private Identifier structureManagerIdentifier = null;
    private StructureNestList structureNestList = null;
    private List<MarkerAnimals> markerAnimals = new ArrayList<>();

    // Runtime
    private boolean wellnessChecked = false;
    private boolean worldWasSet = false;
    private boolean staleChecked = false;
    private boolean registered = false;
    // Unknown until the first tick
    @Nullable
    private Boolean inGeneratedStructure;

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.STRUCTURE_MANAGER, pos, state);
    }

    @Override
    public void markRemoved() {
        StructureManagerService.unregister(this);
        super.markRemoved();
    }

    public void updateData(Identifier structureManagerId, boolean isActive, boolean toInitialize) {
        this.structureManagerIdentifier = structureManagerId;
        this.enabled = isActive;
        this.toInitialize = toInitialize;
        updateListeners();
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new StructureManagerScreenData(this.pos, this.enabled, this.toInitialize, Optional.ofNullable(this.structureManagerIdentifier));
    }
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    // The builder's tool draws which manager each bed is born by, which for a bed that names no place hangs on the manager's
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        if(structureManagerIdentifier != null)
            nbt.putString(SyncedData.IDENTIFIER.name, structureManagerIdentifier.toString());
        return nbt;
    }

    public @Nullable Identifier getStructureManagerId() {
        return structureManagerIdentifier;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StructureManagerScreenHandler(syncId, playerInventory,
                new StructureManagerScreenData(this.pos, this.enabled, this.toInitialize, Optional.ofNullable(this.structureManagerIdentifier))
        );
    }

    public static void tickEvent(World world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        entity.tickEvent(world, blockPos, blockState);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putBoolean(SyncedData.ENABLED.name, this.enabled);
        view.putBoolean(SyncedData.TO_INITIALIZE.name, this.toInitialize);
        if(structureManagerIdentifier != null)
            view.put(SyncedData.IDENTIFIER.name, Identifier.CODEC, this.structureManagerIdentifier);
        if(structureNestList != null)
            view.put(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC, this.structureNestList);
        view.put(SyncedData.MARKER_ANIMALS.name, Codec.list(MarkerAnimals.CODEC), this.markerAnimals);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.enabled = view.getBoolean(SyncedData.ENABLED.name, false);
        this.toInitialize = view.getBoolean(SyncedData.TO_INITIALIZE.name, false);
        view.read(SyncedData.IDENTIFIER.name, Identifier.CODEC)
                .ifPresent(x -> structureManagerIdentifier = x);
        // Saved in a chunk, the block entity has its position in its data. Without one it comes from a structure template, a copy
        // or a command, and what it remembers of the nests it spawned is of the world it was built in: its positions, its npcs.
        // It starts over from its own data, which the nest blocks of the template are subscribed to again
        boolean ofThisWorld = view.getOptionalInt("x").isPresent() || (world != null && world.isClient);
        if(ofThisWorld) {
            view.read(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC)
                    .ifPresent(x -> structureNestList = x);
            markerAnimals = new ArrayList<>(view.read(SyncedData.MARKER_ANIMALS.name, Codec.list(MarkerAnimals.CODEC)).orElse(List.of()));
        } else {
            structureNestList = null;
            markerAnimals = new ArrayList<>();
            enabled = false;
            toInitialize = true;
        }
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        worldWasSet = true;
    }

    public void showAllEntities() {
        if(structureNestList == null)
            return;
        for(SpawnNestManager nest : structureNestList.getManagers()){
            for(UUID uuid : nest.getEntityUuids()){
                if(world.getEntity(uuid) instanceof LivingEntity livingEntity){
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, GLOW_TICKS));
                }
            }
        }
    }

    // A tagged bed this manager has a spawn slot for
    public boolean ownsBed(BlockPos pos) {
        if(structureNestList == null)
            return false;
        for(SpawnNestManager nest : structureNestList.getManagers()){
            if(pos.equals(nest.getBed()))
                return true;
        }
        return false;
    }

    // A lord lives here, or will once the bed of one has spawned it
    public boolean hasLordNest() {
        if(structureNestList == null)
            return false;
        for(SpawnNestManager nest : structureNestList.getManagers()) {
            SpawnNestNodeData data = world == null ? null : StructureManagerService.findNest(world, nest.getId());
            Optional<Identifier> job = nest.getBed() != null ? nest.getBedJob() : data != null ? data.getJob() : NpcJobs.forNest(nest.getId());
            if(NpcJobs.isLord(job.orElse(null)))
                return true;
        }
        return false;
    }

    // Everyone living in the place, whom its nests spawned
    public Set<UUID> residents() {
        Set<UUID> residents = new HashSet<>();
        if(structureNestList == null)
            return residents;
        for(SpawnNestManager nest : structureNestList.getManagers())
            residents.addAll(nest.getEntityUuids());
        return residents;
    }

    public boolean canBear(BedTag tag) {
        return world != null && StructureManagerService.nestFor(world, tag, Optional.ofNullable(structureManagerIdentifier)) != null;
    }

    public @Nullable BlockPos findNestOrigin(UUID entity) {
        if(structureNestList == null)
            return null;
        for(SpawnNestManager nest : structureNestList.getManagers()){
            if(nest.getEntityUuids().contains(entity))
                return nest.getOriginPos();
        }
        return null;
    }

    public void respawnAllEntities() {
        if(structureNestList == null)
            return;
        if(world == null || world.isClient())
            return;
        for(var nest : structureNestList.getManagers()){
            nest.forceRespawn((ServerWorld) world, pos);
        }
    }

    public static void triggerDeathSignal(BlockPos pos, LivingEntity entity) {
        if(entity.getEntityWorld().isClient())
            return;
        if(entity.getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity && !blockEntity.isRemoved() && blockEntity.structureNestList != null){
            blockEntity.structureNestList.computeDeath(entity);
            blockEntity.world.markDirty(pos);
        }
    }

    private void tickEvent(World world, BlockPos blockPos, BlockState blockState) {
        if(!(world instanceof ServerWorld checkedWorld))
            return;
        if(inGeneratedStructure == null)
            inGeneratedStructure = GeneratedStructures.contains(checkedWorld, blockPos);
        if(!inGeneratedStructure)
            return;

        if(worldWasSet){
            tryToInitializeManager(world);
            this.worldWasSet = false;
        }

        if (!this.registered) {
            StructureManagerService.register(this);
            this.registered = true;
        }

        if(!enabled || structureNestList == null)
            return;

        long gameTick = checkedWorld.getTime();
        long timeOfDay = gameTick % DAY_TICKS;
        boolean inCheckWindow = (timeOfDay > DUSK_CHECK_START && timeOfDay < DUSK_CHECK_END) || timeOfDay >= DAWN_CHECK_START;
        boolean haveToDoWellnessCheck = inCheckWindow && !wellnessChecked;
        if(!inCheckWindow)
            wellnessChecked = false;

        if(!staleChecked) {
            staleChecked = true;
            if(structureNestList.removeStale(checkedWorld) > 0)
                markDirty();
        }
        for(SpawnNestManager nest : structureNestList.getManagers()){
            if(haveToDoWellnessCheck)
                nest.doWellnessCheck(checkedWorld, blockPos);
            nest.tick(gameTick, checkedWorld, blockPos);
        }
        if(haveToDoWellnessCheck)
            wellnessChecked = true;
        if(gameTick % MARKER_CHECK_INTERVAL == 0)
            tickMarkers(checkedWorld);
    }

    // What the builders marked to be spawned: people of the nest a marker names, animals with their own count. Each tagged bed
    // of the structure that is this manager's is a nest of its own, of one person
    private void tickMarkers(ServerWorld serverWorld) {
        RoutineSite site = RoutineSites.forManager(serverWorld, pos);
        if(site == null)
            return;
        Optional<Identifier> place = Optional.ofNullable(structureManagerIdentifier);
        for(StructureFeatureData.Marker marker : site.getMarkers(MarkerKind.SPAWN_NPC)) {
            SpawnNestNodeData data = StructureManagerService.nestForMarker(serverWorld, marker.value(), place);
            if(data == null || structureNestList.getManagers().stream().anyMatch(nest -> nest.isMarked() && nest.getOriginPos().equals(marker.pos())))
                continue;
            structureNestList.addNest(new SpawnNestManager(data, marker.pos(), MARKER_SPAWN_RADIUS, marker.count()));
            markDirty();
        }
        // A bed this manager took before it was told whose each bed is goes back to the one it belongs to
        if(!site.getManagers().isEmpty()) {
            Set<BlockPos> owned = new HashSet<>();
            for(StructureFeatureData.Bed bed : site.getBeds()) {
                if(pos.equals(StructureManagerService.ownerOf(serverWorld, bed, site.getManagers())))
                    owned.add(bed.pos());
            }
            if(structureNestList.removeBedsExcept(owned) > 0)
                markDirty();
        }
        for(StructureFeatureData.Bed bed : site.getBeds()) {
            if(bed.tag().isEmpty() || ownsBed(bed.pos()))
                continue;
            SpawnNestNodeData data = StructureManagerService.nestFor(serverWorld, bed.tag().get(), place);
            if(data == null)
                continue;
            boolean owned = site.getManagers().isEmpty() ? StructureManagerService.isNearestBearer(serverWorld, bed.pos(), bed.tag().get(), site, this)
                    : pos.equals(StructureManagerService.ownerOf(serverWorld, bed, site.getManagers()));
            if(!owned)
                continue;
            structureNestList.addNest(new SpawnNestManager(data, bed));
            markDirty();
        }
        for(StructureFeatureData.Marker marker : site.getMarkers(MarkerKind.SPAWN_ANIMAL)) {
            Identifier typeId = Identifier.tryParse(marker.value());
            EntityType<?> type = typeId == null ? null : Registries.ENTITY_TYPE.getOptionalValue(typeId).orElse(null);
            if(type == null)
                continue;
            MarkerAnimals animals = markerAnimals.stream().filter(found -> found.getPos().equals(marker.pos())).findFirst().orElse(null);
            if(animals == null) {
                // Tried again once the marker's ground is loaded
                if(!serverWorld.shouldTickEntityAt(marker.pos()))
                    continue;
                animals = new MarkerAnimals(marker.pos(), List.of(), -1);
                markerAnimals.add(animals);
                animals.spawn(serverWorld, type, marker.count());
                markDirty();
            } else if(marker.respawns() && animals.topUp(serverWorld, type, marker.count())) {
                markDirty();
            }
        }
    }

    private void tryToInitializeManager(World world){
        if(world.isClient())
            return;
        if(!toInitialize || enabled)
            return;

        this.structureNestList = new StructureNestList();
        this.toInitialize = false;
        this.enabled = true;
    }

    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}
