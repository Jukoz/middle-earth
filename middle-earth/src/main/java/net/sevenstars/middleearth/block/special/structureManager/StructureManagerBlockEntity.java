package net.sevenstars.middleearth.block.special.structureManager;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
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
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.block.special.structureManager.features.SpawnNestManager;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureNestList;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_manager";

    private enum SyncedData {
        ENABLED("%s.Enabled".formatted(ID)),
        TO_INITIALIZE("%s.ToInitialize".formatted(ID)),
        SPAWN_NEST_LIST("%s.Nests".formatted(ID)),
        IDENTIFIER("%s.Identifier".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }

    // Synced Data
    private boolean enabled;
    private boolean toInitialize;
    @Nullable
    protected Identifier structureManagerIdentifier;
    private StructureNestList structureNestList;
    private boolean wellnessChecked;

    boolean firstTick = true;
    // Runtime
    private StructureManagerData managerData;
    private boolean worldWasSet = false;
    private boolean registered = false;

    @Override
    public void markRemoved() {
        StructureManagerService.unregister(this);
        super.markRemoved();
    }

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.STRUCTURE_MANAGER, pos, state);
        // Default values
        this.enabled = false;
        this.toInitialize = false;
        this.structureManagerIdentifier = null;
        this.structureNestList = null;
        this.firstTick = true;
        this.wellnessChecked = false;
    }

    // region [Basic Overrides]
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
        } else if(structureManagerIdentifier != null) {
            structureNestList = null;
            enabled = false;
            toInitialize = true;
        }
    }
    // endregion


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
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10*20));
                }
            }
        }
    }

    public @Nullable SpawnNestManager findNest(UUID entity) {
        if(structureNestList == null)
            return null;
        for(SpawnNestManager nest : structureNestList.getManagers()){
            if(nest.getEntityUuids().contains(entity))
                return nest;
        }
        return null;
    }

    public boolean ownsBed(BlockPos pos) {
        return ownsBed(pos, null);
    }

    public boolean isBedTakenElsewhere(BlockPos pos, SpawnNestManager nest) {
        return ownsBed(pos, nest) || StructureManagerService.isBedTaken(world, pos, this);
    }

    private boolean ownsBed(BlockPos pos, @Nullable SpawnNestManager except) {
        if(structureNestList == null)
            return false;
        for(SpawnNestManager nest : structureNestList.getManagers()){
            if(nest == except)
                continue;
            for(NestSurvey.Bed taken : nest.getBeds()){
                if(taken.owner().isPresent() && taken.pos().equals(pos))
                    return true;
            }
        }
        return false;
    }

    // Gives the npc a bed of its own in the building of its nest, and tells it where things are put away
    public void assignBed(NpcEntity npc) {
        SpawnNestManager nest = findNest(npc.getUuid());
        if(nest == null || !(world instanceof ServerWorld serverWorld) || !nest.survey(serverWorld))
            return;
        NestSurvey.Bed bed = nest.claimBed(npc.getUuid(), pos -> isBedTakenElsewhere(pos, nest));
        npc.setHousehold(bed == null ? null : bed.pos(), bed == null ? null : bed.stand().orElse(null), nest.getStore());
        markDirty();
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
            nest.forceRespawn(managerData, (ServerWorld) world, pos);
        }
    }

    public boolean subscribeNest(BlockPos nestPos, Identifier managerId, Identifier nestId, int spawnRadius) {
        if(!enabled || managerId == null || structureManagerIdentifier == null || managerData == null || managerId.compareTo(this.structureManagerIdentifier) != 0)
            return false;

        SpawnNestNodeData data = managerData.findSpawnNest(nestId);
        if(data == null || structureNestList == null)
            return false;
        SpawnNestManager manager = new SpawnNestManager(data, nestPos, spawnRadius);
        if(world instanceof ServerWorld serverWorld)
            manager.survey(serverWorld);
        this.structureNestList.addNest(manager);
        markDirty();
        return true;
    }

    public static void triggerDeathSignal(BlockPos pos, LivingEntity entity) {
        if(entity.getEntityWorld().isClient())
            return;
        StructureManagerBlockEntity blockEntity = (StructureManagerBlockEntity) entity.getEntityWorld().getBlockEntity(pos);
        if(blockEntity != null && !blockEntity.isRemoved()){
            blockEntity.structureNestList.computeDeath(entity);
            blockEntity.world.markDirty(pos);
        }
    }

    private void tickEvent(World world, BlockPos blockPos, BlockState blockState) {
        if(!world.isClient() && worldWasSet){
            tryToInitializeManager(world);
            this.worldWasSet = false;
        }

        if (!world.isClient() && !this.registered) {
            StructureManagerService.register(this);
            this.registered = true;
        }

        if(!enabled)
            return;

        ServerWorld serverWorld = (ServerWorld) world;
        if(structureNestList == null)
            return;

        long timeOfDay = serverWorld.getTime() % 24000;
        long gameTick = serverWorld.getTime();
        if((timeOfDay > 0 && timeOfDay < 11000) || (timeOfDay > 12000 && timeOfDay < 23000) && wellnessChecked)
            wellnessChecked = false;

        boolean haveToDoWellnessCheck = (timeOfDay > 11000 && timeOfDay < 12000) || (timeOfDay >= 23000) && !wellnessChecked;
        if(managerData == null) {
            managerData = StructureManagerService.getStructureManagerData(serverWorld, structureManagerIdentifier);
            if(managerData != null && structureNestList.removeUnknown(managerData) > 0)
                markDirty();
        }
        for(SpawnNestManager data : structureNestList.getManagers()){
            if(managerData == null)
                managerData = StructureManagerService.getStructureManagerData(serverWorld, structureManagerIdentifier);
            if(haveToDoWellnessCheck){
                data.doWellnessCheck(managerData, serverWorld, blockPos);
            }
            data.tick(managerData, gameTick, serverWorld, blockPos);
        }
        if(haveToDoWellnessCheck && !wellnessChecked)
            wellnessChecked = true;
    }

    private void tryToInitializeManager(World world){
        if(world.isClient())
            return;
        if(!toInitialize || enabled)
            return;
        if(structureManagerIdentifier == null)
            return;

        this.managerData = StructureManagerService.getStructureManagerData(world, structureManagerIdentifier);
        this.structureNestList = new StructureNestList();
        if(managerData == null) {
            return;
        };

        this.toInitialize = false;
        this.enabled = true;
    }

    public void setInitializationState(boolean toInitialize) {
        this.toInitialize = toInitialize;
        updateListeners();
    }

    public void setActiveState(boolean activate) {
        this.enabled = activate;
        updateListeners();
    }

    public void setStructureManagerId(Identifier identifier) {
        this.structureManagerIdentifier = identifier;
        updateListeners();
    }

    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    public void fetchBeds(){
        // TODO : Fetch all beds surrounding the nodes, making sure there's no duplicate
        StructureManagerData managerData = getWorld().getRegistryManager().getOptional(DynamicRegistriesME.STRUCTURE_MANAGER_DATA).get().get(structureManagerIdentifier);
        for(SpawnNestManager data : structureNestList.getManagers()) {
            SpawnNestNodeData nodeData = managerData.getNpcSpawnNest(data.getId());
            if(nodeData == null)
                continue;

            int bedRadius = nodeData.getBedRadius();
            BlockPos origin = data.getOriginPos();
        }
    }

    public void redistributeBeds(){
        // TODO : Redistribute beds to the nest nodes
        // TODO : Makes sure the beds are still distributed to the correct npcs
    }
}
