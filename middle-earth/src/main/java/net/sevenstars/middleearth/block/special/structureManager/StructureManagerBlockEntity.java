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
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    ModLogger logger = MiddleEarth.LOGGER;
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
        view.read(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC)
                .ifPresent(x -> structureNestList = x);
    }
    // endregion


    @Override
    public void setWorld(World world) {
        super.setWorld(world);
    }

    public void showAllEntities() {
        if(structureNestList == null)
            return;
        for(var nest : structureNestList.getManagers()){
            for(var uuid : nest.getEntityUuids()){
                if(world.getEntity(uuid) instanceof LivingEntity livingEntity){
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10*20));
                }
            }
        }
    }

    public boolean subscribeNest(BlockPos nestPos, Identifier managerId, Identifier nestId, int spawnRadius) {
        if(!enabled || managerId == null || structureManagerIdentifier == null || managerData == null || managerId.compareTo(this.structureManagerIdentifier) != 0)
            return false;

        SpawnNestNodeData data = managerData.getNpcSpawnNest(nestId);
        SpawnNestManager manager = new SpawnNestManager(data, nestPos, spawnRadius);
        this.structureNestList.addNest(manager);
        MiddleEarth.LOGGER.logDebugMsg("Subscribed new nest to [%s] with a nest at [%s] which is <%s>".formatted(this.pos, nestPos, nestId));
        return true;
    }

    public static void triggerDeathSignal(BlockPos pos, LivingEntity entity) {
        if(entity.getWorld().isClient)
            return;
        StructureManagerBlockEntity blockEntity = (StructureManagerBlockEntity) entity.getWorld().getBlockEntity(pos);
        if(blockEntity!=null && !blockEntity.isRemoved()){
            blockEntity.structureNestList.computeDeath(entity);
            blockEntity.world.markDirty(pos);
        }
    }

    private void tickEvent(World world, BlockPos blockPos, BlockState blockState) {
        if(!world.isClient && firstTick){
            tryToInitializeManager(world);
            this.firstTick = false;
        }

        ServerWorld serverWorld = (ServerWorld) world;
        if(structureNestList == null || !enabled)
            return;

        long timeOfDay = serverWorld.getTime() % 24000;
        long gameTick = serverWorld.getTime();
        if((timeOfDay > 0 && timeOfDay < 11000) || (timeOfDay > 12000 && timeOfDay < 23000) && wellnessChecked)
            wellnessChecked = false;

        boolean haveToDoWellnessCheck = (timeOfDay > 11000 && timeOfDay < 12000) || (timeOfDay >= 23000) && !wellnessChecked;
        for(SpawnNestManager data : structureNestList.getManagers()){
            if(managerData == null)
                managerData = StructureManagerService.GetStructureManagerData(serverWorld, structureManagerIdentifier);
            if(haveToDoWellnessCheck){
                data.doWellnessCheck(managerData, serverWorld, blockPos);
            }
            data.tick(managerData, gameTick, serverWorld, blockPos);
        }
        if(haveToDoWellnessCheck && !wellnessChecked)
            wellnessChecked = true;
    }

    private void tryToInitializeManager(World world){
        if(world.isClient)
            return;
        if(!toInitialize || enabled)
            return;
        if(structureManagerIdentifier == null)
            return;

        if(toInitialize && !enabled){
            this.managerData = StructureManagerService.GetStructureManagerData(world, structureManagerIdentifier);
            if(structureNestList == null)
                this.structureNestList = new StructureNestList();
            if(managerData == null) {
                this.logger.logDebugMsg("%s::[%s] Couldn't find managerData under <%s>".formatted(ID, pos, managerData));
                return;
            };

            this.toInitialize = false;
            this.enabled = true;
        }
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
}
