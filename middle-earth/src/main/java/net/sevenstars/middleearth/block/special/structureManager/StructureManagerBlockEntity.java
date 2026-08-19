package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.structureManager.features.SpawnNestManager;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureNestList;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Objects;
import java.util.UUID;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedMenuProviderME {
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
    protected ResourceLocation structureManagerIdentifier;
    private StructureNestList structureNestList;
    private boolean wellnessChecked;

    boolean firstTick = true;
    // Runtime
    private StructureManagerData managerData;
    private boolean worldWasSet = false;
    private boolean registered = false;

    @Override
    public void setRemoved() {
        StructureManagerService.unregister(this);
        super.setRemoved();
    }

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
        // Default values
        this.enabled = false;
        this.toInitialize = false;
        this.structureManagerIdentifier = null;
        this.structureNestList = null;
        this.firstTick = true;
        this.wellnessChecked = false;
    }

    // region [Basic Overrides]
    public void updateData(ResourceLocation structureManagerId, boolean isActive, boolean toInitialize) {
        if (!canChangeStructureManagerId(structureManagerId)) {
            return;
        }
        if (registered) {
            StructureManagerService.unregister(this);
            registered = false;
        }
        this.structureManagerIdentifier = structureManagerId;
        this.enabled = isActive;
        this.toInitialize = toInitialize;
        this.managerData = null;
        if (this.level != null && !this.level.isClientSide && this.enabled) {
            resolveManagerData(this.level);
        }
        syncRegistration();
        updateListeners();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Override
    public void writeOpeningData(RegistryFriendlyByteBuf buffer) {
        StructureManagerScreenData.PACKET_CODEC.encode(buffer,
                new StructureManagerScreenData(this.worldPosition, this.enabled, this.toInitialize,
                        Optional.ofNullable(this.structureManagerIdentifier)));
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new StructureManagerScreenHandler(syncId, playerInventory,
                new StructureManagerScreenData(this.worldPosition, this.enabled, this.toInitialize, Optional.ofNullable(this.structureManagerIdentifier))
        );
    }

    public static void tickEvent(Level world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        entity.tickEvent(world, blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean(SyncedData.ENABLED.name, this.enabled);
        tag.putBoolean(SyncedData.TO_INITIALIZE.name, this.toInitialize);
        if (structureManagerIdentifier != null) {
            tag.putString(SyncedData.IDENTIFIER.name, structureManagerIdentifier.toString());
        }
        if (structureNestList != null) {
            StructureNestList.CODEC.encodeStart(NbtOps.INSTANCE, structureNestList).result()
                    .ifPresent(encoded -> tag.put(SyncedData.SPAWN_NEST_LIST.name, encoded));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.enabled = tag.getBoolean(SyncedData.ENABLED.name);
        this.toInitialize = tag.getBoolean(SyncedData.TO_INITIALIZE.name);
        if (tag.contains(SyncedData.IDENTIFIER.name)) {
            structureManagerIdentifier = ResourceLocation.tryParse(tag.getString(SyncedData.IDENTIFIER.name));
        }
        if (tag.contains(SyncedData.SPAWN_NEST_LIST.name)) {
            StructureNestList.CODEC.parse(NbtOps.INSTANCE, tag.get(SyncedData.SPAWN_NEST_LIST.name)).result()
                    .ifPresent(value -> structureNestList = value);
        }
    }
    // endregion


    @Override
    public void setLevel(Level world) {
        super.setLevel(world);
        worldWasSet = true;
    }

    public void showAllEntities() {
        if(structureNestList == null || !(level instanceof ServerLevel serverLevel))
            return;
        for(SpawnNestManager nest : structureNestList.getManagers()){
            for(UUID uuid : nest.getEntityUuids()){
                if(serverLevel.getEntity(uuid) instanceof LivingEntity livingEntity){
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 10*20));
                }
            }
        }
    }

    public void respawnAllEntities() {
        if(structureNestList == null)
            return;
        if(level == null || level.isClientSide)
            return;
        resolveManagerData(level);
        if (managerData == null) {
            return;
        }
        for(var nest : structureNestList.getManagers()){
            nest.forceRespawn(managerData, (ServerLevel) level, worldPosition);
        }
    }

    public boolean subscribeNest(BlockPos nestPos, ResourceLocation managerId, ResourceLocation nestId, int spawnRadius) {
        if(!acceptsNest(managerId) || nestId == null)
            return false;

        SpawnNestNodeData data = managerData.getNpcSpawnNest(nestId);
        if (data == null) {
            return false;
        }
        if (this.structureNestList == null) {
            this.structureNestList = new StructureNestList();
        }
        SpawnNestManager manager = new SpawnNestManager(data, nestPos, spawnRadius);
        this.structureNestList.addNest(manager);
        this.setChanged();
        return true;
    }

    public boolean isOperational() {
        return enabled
                && structureManagerIdentifier != null
                && managerData != null
                && !isRemoved();
    }

    public boolean acceptsNest(@Nullable ResourceLocation managerId) {
        return managerId != null
                && isOperational()
                && managerId.equals(this.structureManagerIdentifier);
    }

    public void removeManagedEntity(UUID uuid) {
        if (level == null || level.isClientSide || structureNestList == null) {
            return;
        }
        if (structureNestList.removeEntity(level, uuid)) {
            setChanged();
            level.blockEntityChanged(worldPosition);
        }
    }

    private void tickEvent(Level world, BlockPos blockPos, BlockState blockState) {
        long tickOffset = Math.floorMod(worldPosition.asLong(), 20L);
        boolean shouldRetryInitialization = toInitialize
                && !enabled
                && Math.floorMod(world.getGameTime() + tickOffset, 20L) == 0;
        if(!world.isClientSide && (worldWasSet || shouldRetryInitialization)){
            tryToInitializeManager(world);
            this.worldWasSet = false;
        }

        if (!world.isClientSide && !this.registered) {
            resolveManagerData(world);
        }
        if (!world.isClientSide) {
            syncRegistration();
        }

        if(!enabled)
            return;

        ServerLevel serverWorld = (ServerLevel) world;
        if(structureNestList == null || managerData == null)
            return;

        long timeOfDay = serverWorld.getGameTime() % 24000;
        long gameTick = serverWorld.getGameTime();
        boolean outsideWellnessWindow = (timeOfDay > 0 && timeOfDay < 11000)
                || (timeOfDay > 12000 && timeOfDay < 23000);
        if(outsideWellnessWindow && wellnessChecked)
            wellnessChecked = false;

        boolean insideWellnessWindow = (timeOfDay > 11000 && timeOfDay < 12000)
                || timeOfDay >= 23000;
        boolean haveToDoWellnessCheck = insideWellnessWindow && !wellnessChecked;
        boolean wellnessStateChanged = false;
        for(SpawnNestManager data : structureNestList.getManagers()){
            if(haveToDoWellnessCheck){
                wellnessStateChanged |= data.doWellnessCheck(managerData, serverWorld, blockPos);
            }
            data.tick(managerData, gameTick, serverWorld, blockPos);
        }
        if (wellnessStateChanged) {
            setChanged();
            world.blockEntityChanged(worldPosition);
        }
        if(haveToDoWellnessCheck && !wellnessChecked)
            wellnessChecked = true;
    }

    private void tryToInitializeManager(Level world){
        if(world.isClientSide)
            return;
        if(!toInitialize || enabled)
            return;
        if(structureManagerIdentifier == null)
            return;

        this.managerData = StructureManagerService.getStructureManagerData(world, structureManagerIdentifier);
        if(managerData == null) {
            return;
        }

        this.structureNestList = new StructureNestList();
        this.toInitialize = false;
        this.enabled = true;
        updateListeners();
    }

    private void resolveManagerData(Level world) {
        if (!enabled || managerData != null || structureManagerIdentifier == null) {
            return;
        }
        managerData = StructureManagerService.getStructureManagerData(
                world, structureManagerIdentifier);
        if (managerData != null && structureNestList == null) {
            structureNestList = new StructureNestList();
        }
    }

    private void syncRegistration() {
        if (level == null || level.isClientSide) {
            return;
        }
        if (isOperational()) {
            if (!registered) {
                StructureManagerService.register(this);
                registered = true;
            }
        } else if (registered) {
            StructureManagerService.unregister(this);
            registered = false;
        }
    }

    public void setInitializationState(boolean toInitialize) {
        if (this.toInitialize == toInitialize) {
            return;
        }
        this.toInitialize = toInitialize;
        updateListeners();
    }

    public void setActiveState(boolean activate) {
        if (this.enabled == activate) {
            return;
        }
        this.enabled = activate;
        if (!activate) {
            this.managerData = null;
        }
        syncRegistration();
        updateListeners();
    }

    public void setStructureManagerId(ResourceLocation identifier) {
        if (Objects.equals(this.structureManagerIdentifier, identifier)) {
            return;
        }
        if (!canChangeStructureManagerId(identifier)) {
            return;
        }
        this.structureManagerIdentifier = identifier;
        this.managerData = null;
        syncRegistration();
        updateListeners();
    }

    private boolean canChangeStructureManagerId(ResourceLocation identifier) {
        return Objects.equals(this.structureManagerIdentifier, identifier)
                || structureNestList == null
                || structureNestList.getManagers().isEmpty();
    }

    private void updateListeners() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(
                    this.getBlockPos(),
                    this.getBlockState(),
                    this.getBlockState(),
                    Block.UPDATE_CLIENTS
            );
        }
    }

    public void fetchBeds(){
        // TODO : Fetch all beds surrounding the nodes, making sure there's no duplicate
        StructureManagerData managerData = StructureManagerService.getStructureManagerData(getLevel(), structureManagerIdentifier);
        if (managerData == null) {
            return;
        }
        if (structureNestList == null) {
            return;
        }
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
