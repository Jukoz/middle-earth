package net.sevenstars.middleearth.block.special.structureManager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
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

import java.util.ArrayList;
import java.util.List;
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
    private SpawnNestList spawnNestList;

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
        this.enabled = true;
        this.runtimeStructureManagerData = null;
        this.selectedStructureManagerData = null;
        this.spawnNestList = null;
    }

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
        if(spawnNestList != null)
            view.put(SyncedData.SPAWN_NEST_LIST.name, SpawnNestList.CODEC, this.spawnNestList);
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

        Optional<SpawnNestList> nests = view.read(SyncedData.SPAWN_NEST_LIST.name, SpawnNestList.CODEC);
        nests.ifPresent(x -> spawnNestList = x);

        if(enabled && selectedStructureManagerData != null){
            runtimeStructureManagerData = selectedStructureManagerData;
            selectedStructureManagerData = null;
        }
    }

    public static void triggerDeathSignal(BlockPos pos, LivingEntity entity) {
        if(entity.getWorld().isClient)
            return;
        StructureManagerBlockEntity blockEntity = (StructureManagerBlockEntity) entity.getWorld().getBlockEntity(pos);
        if(blockEntity!=null && !blockEntity.isRemoved()){
            blockEntity.spawnNestList.computeDeath(entity);
            blockEntity.world.markDirty(pos);
        }
    }

    private void tickEvent(World world, BlockPos blockPos, BlockState blockState) {
        if(runtimeStructureManagerData != null && managerData == null) {
            initializeData(world);
        }
        if(spawnNestList == null || !enabled)
            return;
        long tick = world.getTickOrder();
        for(SpawnNestManager data : spawnNestList.managers){
            if(managerData == null)
                managerData = StructureManagerService.GetStructureManagerData(world, StructureManagerDatasME.NPC_TESTING_AREA_GONDOR.getId());
            data.tick(managerData, tick, world, blockPos);
        }
    }

    private void initializeData(World world){
        if(runtimeStructureManagerData != null && managerData == null){
            this.managerData = StructureManagerService.GetStructureManagerData(world, runtimeStructureManagerData);
            if(managerData == null) {
                this.logger.logDebugMsg("%s::[%s] Couldn't find managerData under <%s>".formatted(ID, pos, managerData));
                return;
            };
            if(spawnNestList == null){
                List<SpawnNestManager> spawnNestManagerList = new ArrayList<>();
                for(SpawnNestNodeData nestNode : managerData.getNpcSpawnNest()){
                    spawnNestManagerList.add(new SpawnNestManager(nestNode));
                }
                this.spawnNestList = new SpawnNestList(spawnNestManagerList);
                updateListeners();
            }
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

    public Identifier getDataId() {
        return selectedStructureManagerData;
    }
    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public boolean getIsActive() {
        return enabled;
    }

    private static final class SpawnNestList {
        ModLogger logger = MiddleEarth.LOGGER;
        public static final Codec<SpawnNestList> CODEC;
            public static final PacketCodec<ByteBuf, SpawnNestList> PACKET_CODEC;
        private static final String ID = "spawn_nest_list";

        private final List<SpawnNestManager> managers;

        private SpawnNestList(List<SpawnNestManager> spawnNestManagers) {
            this.managers = spawnNestManagers;
        }

        public SpawnNestList() {
            this.managers = List.of();
        }

        public void computeDeath(LivingEntity entity) {
            for (SpawnNestManager nest : managers) {
                if(nest.computeDeath(entity))
                    return;
            }
        }

        public List<SpawnNestManager> content() {
            return managers;
        }

        static {
            CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.list(SpawnNestManager.CODEC).fieldOf("Managers").forGetter(SpawnNestList::content)
            ).apply(instance, SpawnNestList::new));
            PACKET_CODEC = PacketCodecs.codec(CODEC);
        }
    }
}
