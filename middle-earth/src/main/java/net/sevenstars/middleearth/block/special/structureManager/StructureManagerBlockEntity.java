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
import net.minecraft.server.world.ServerWorld;
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
import java.util.Objects;
import java.util.Optional;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    ModLogger logger = MiddleEarth.LOGGER;
    private static final String ID = "structure_manager";

    @Nullable
    protected Identifier structureManagerDataRegistryIdentifier;

    private boolean isActive;
    private StructureManagerData managerData;
    private SpawnNestList spawnNestList;

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
        this.isActive = true;
        this.structureManagerDataRegistryIdentifier = null;
        this.spawnNestList = null;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new StructureManagerScreenData(this.pos, this.isActive, Optional.ofNullable(this.structureManagerDataRegistryIdentifier));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StructureManagerScreenHandler(syncId, playerInventory, new StructureManagerScreenData(this.pos, this.isActive, Optional.ofNullable(this.structureManagerDataRegistryIdentifier)));
    }

    public static void tickEvent(World world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        entity.tickEvent(world, blockPos, blockState);
    }

    void initializeData(World world, boolean isPlayer) {
        if(world.isClient)
            return;

        if(structureManagerDataRegistryIdentifier != null){
            managerData = StructureManagerService.GetStructureManagerData(world, this.structureManagerDataRegistryIdentifier);

            if(managerData == null) {
                this.logger.logDebugMsg("%s::[%s] Couldn't find managerData under <%s>".formatted(ID, pos, structureManagerDataRegistryIdentifier));
                return;
            };

            List<SpawnNestManager> spawnNestManagerList = new ArrayList<>();
            for(SpawnNestNodeData nestNode : managerData.getNpcSpawnNest()){
                spawnNestManagerList.add(new SpawnNestManager(nestNode));
            }
            this.spawnNestList = new SpawnNestList(spawnNestManagerList);
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putBoolean("%s.IsActive".formatted(ID), this.isActive);
        if(managerData != null)
            view.putString("%s.Id".formatted(ID), Objects.requireNonNullElse(managerData, StructureManagerDatasME.NPC_TESTING_AREA_A).getId().toString());
        if(spawnNestList != null)
            view.put("%s.SpawnNestList".formatted(ID), SpawnNestList.CODEC, this.spawnNestList);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.logger.logDebugMsg("%s::[%s] Reading Data :: Begin".formatted(ID, pos));

        isActive = view.getBoolean("%s.IsActive".formatted(ID), false);

        structureManagerDataRegistryIdentifier = Identifier.of(view.getString("%s.Id".formatted(ID), StructureManagerDatasME.NPC_TESTING_AREA_A.getId().toString()));
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            managerData = StructureManagerService.GetStructureManagerData(serverWorld, structureManagerDataRegistryIdentifier);
        }

        spawnNestList = view.read("%s.SpawnNestList".formatted(ID), SpawnNestList.CODEC).orElseGet(SpawnNestList::new);

        this.logger.logDebugMsg("%s::[%s] Reading Data :: Completed\nIsActive = %s\nManagerId = %s\nManagerData = %s\nSpawnNestList = %s"
                .formatted(ID, pos, isActive, structureManagerDataRegistryIdentifier, managerData, spawnNestList.managers.size()));
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
        if(spawnNestList == null || !isActive)
            return;
        long tick = world.getTickOrder();
        for(SpawnNestManager data : spawnNestList.managers){
            if(managerData == null)
                managerData = StructureManagerService.GetStructureManagerData(world, StructureManagerDatasME.NPC_TESTING_AREA_A.getId());
            data.tick(managerData, tick, world, blockPos);
        }
    }

    public void setDataId(Identifier identifier) {
        this.structureManagerDataRegistryIdentifier = identifier;
        updateListeners();
        this.logger.logDebugMsg("%s::[%s] Setting Data Id :: New is - %s".formatted(ID, pos, isActive, structureManagerDataRegistryIdentifier.toString()));
    }

    public Identifier getDataId() {
        MiddleEarth.LOGGER.logDebugMsg("%s::[%s] Grabbing Data Id :: Current is - %s".formatted(ID, pos, isActive, structureManagerDataRegistryIdentifier.toString()));
        return structureManagerDataRegistryIdentifier;
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
        return isActive;
    }

    private static final class SpawnNestList {
        ModLogger logger = MiddleEarth.LOGGER;
        public static final Codec<SpawnNestList> CODEC;
            public static final PacketCodec<ByteBuf, SpawnNestList> PACKET_CODEC;
        private static final String ID = "spawn_nest_list";

        private final List<SpawnNestManager> managers;

        private SpawnNestList(List<SpawnNestManager> spawnNestManagers) {
            this.managers = spawnNestManagers;
            this.logger.logDebugMsg("%s:: Constructor :: From list with %s nest sources".formatted(ID, spawnNestManagers.size()));
            for (SpawnNestManager data : managers)
                this.logger.logDebugMsg("%s:: Constructor :: Added %s entities for <%s>".formatted(ID, data.getEntityUuids().size(), data.getId()));
        }

        public SpawnNestList() {
            this.managers = List.of();
            this.logger.logDebugMsg("%s:: Constructor :: Added blank list".formatted(ID));
        }

        public void computeDeath(LivingEntity entity) {
            for (SpawnNestManager nest : managers) {
                if(nest.computeDeath(entity))
                    return;
            }
            this.logger.logDebugMsg("SMBE :: No nest contain the entity");
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
