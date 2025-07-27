package net.sevenstars.middleearth.block.special.structureManager;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.resources.StructureManagerDatasME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    ModLogger logger = MiddleEarth.LOGGER;
    private static final String ID = "structure_manager";

    private enum SyncedData {
        ENABLED("%s.Enabled".formatted(ID)),
        SPAWN_NEST_LIST("%s.Nests".formatted(ID)),
        SELECTED_ID("%s.SelectedId".formatted(ID)),
        RUNTIME_ID("%s.RuntimeId".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }

    @Nullable
    protected Identifier selectedStructureManagerData;
    @Nullable
    protected Identifier runtimeStructureManagerData;

    private boolean enabled;
    private StructureManagerData managerData;
    private StructureNestList structureNestList;

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
        this.enabled = true;
        this.runtimeStructureManagerData = null;
        this.selectedStructureManagerData = null;
        this.structureNestList = null;
    }

    // region [Basic Overrides]

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new StructureManagerScreenData(this.pos, this.enabled,
                Optional.ofNullable(this.selectedStructureManagerData),
                Optional.ofNullable(this.runtimeStructureManagerData)
        );
    }
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StructureManagerScreenHandler(syncId, playerInventory,
                new StructureManagerScreenData(this.pos, this.enabled,
                    Optional.ofNullable(this.selectedStructureManagerData),
                    Optional.ofNullable(this.runtimeStructureManagerData))
        );
    }

    public static void tickEvent(World world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        entity.tickEvent(world, blockPos, blockState);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putBoolean(SyncedData.ENABLED.name, this.enabled);
        if(runtimeStructureManagerData != null)
            view.put(SyncedData.RUNTIME_ID.name, Identifier.CODEC, this.runtimeStructureManagerData);
        else if(selectedStructureManagerData != null)
            view.put(SyncedData.SELECTED_ID.name, Identifier.CODEC, this.selectedStructureManagerData);
        if(structureNestList != null)
            view.put(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC, this.structureNestList);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.enabled = view.getBoolean(SyncedData.ENABLED.name, false);
        Optional<Identifier> runtime = view.read(SyncedData.RUNTIME_ID.name, Identifier.CODEC);
        runtime.ifPresent(x -> runtimeStructureManagerData = x);
        if(runtimeStructureManagerData == null){
            Optional<Identifier> selected = view.read(SyncedData.SELECTED_ID.name, Identifier.CODEC);
            selected.ifPresent(x -> selectedStructureManagerData = x);
        }

        Optional<StructureNestList> nests = view.read(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC);
        nests.ifPresent(x -> structureNestList = x);

        if(enabled && selectedStructureManagerData != null){
            runtimeStructureManagerData = selectedStructureManagerData;
            selectedStructureManagerData = null;
        }
    }
    // endregion

    public boolean subscribeNest(BlockPos nestPos, Identifier managerId, Identifier nestId, int spawnRadius) {
        if(!isRuntimeEnabled() || managerId.compareTo(this.runtimeStructureManagerData) != 0)
            return false;

        if(managerData == null){
            if(this.runtimeStructureManagerData == null)
                return false;
            this.managerData = StructureManagerService.GetStructureManagerData(world, this.runtimeStructureManagerData);
            if(this.managerData == null)
                return false;
            this.structureNestList = new StructureNestList();
        }
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
        tryToInitializeManager(world);
        if(structureNestList == null || !enabled)
            return;
        long tick = world.getTickOrder();
        for(SpawnNestManager data : structureNestList.getManagers()){
            if(managerData == null)
                managerData = StructureManagerService.GetStructureManagerData(world, StructureManagerDatasME.NPC_TESTING_AREA_GONDOR.getId());
            data.tick(managerData, tick, world, blockPos);
        }
    }

    private void tryToInitializeManager(World world){
        if(runtimeStructureManagerData != null && managerData == null && enabled) {
            this.managerData = StructureManagerService.GetStructureManagerData(world, runtimeStructureManagerData);
            this.structureNestList = new StructureNestList();
            if(managerData == null) {
                this.logger.logDebugMsg("%s::[%s] Couldn't find managerData under <%s>".formatted(ID, pos, managerData));
                return;
            };
        }
    }
    public void toggle(boolean activate) {
        this.enabled = activate;
        updateListeners();
    }
    public void setDataId(Identifier identifier) {
        this.selectedStructureManagerData = identifier;
        this.runtimeStructureManagerData = null;
        this.enabled = false;
        updateListeners();
    }

    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    public boolean isRuntimeEnabled() {
        return enabled && runtimeStructureManagerData != null;
    }
}
