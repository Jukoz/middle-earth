package net.sevenstars.middleearth.block.special.structureManager.nest;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class StructureNestBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_nest";

    private enum SyncedData {
        MANAGER_ID("%s.ManagerId".formatted(ID)),
        NEST_ID("%s.NestId".formatted(ID)),
        SPAWN_RADIUS("%s.SpawnRadius".formatted(ID)),
        IS_ENABLED("%s.IsEnabled".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }
    @Nullable
    protected Identifier managerId;
    @Nullable
    protected Identifier nestId;
    protected int spawnRadius;
    protected boolean isEnabled;

    protected int fails = 0;
    boolean initialized = false;

    public StructureNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_NEST, pos, state);
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new StructureNestScreenData(this.pos,
                Optional.ofNullable(this.managerId),
                Optional.ofNullable(this.nestId),
                spawnRadius,
                isEnabled
            );
    }

    public Text getDisplayName() {
        return Text.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StructureNestScreenHandler(syncId, playerInventory, new StructureNestScreenData(this.pos,
                Optional.ofNullable(this.managerId),
                Optional.ofNullable(this.nestId),
                this.spawnRadius,
                this.isEnabled));
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Optional<Identifier> managerId = view.read(SyncedData.MANAGER_ID.name, Identifier.CODEC);
        managerId.ifPresent(identifier -> this.managerId = identifier);
        Optional<Identifier> nestId = view.read(SyncedData.NEST_ID.name, Identifier.CODEC);
        nestId.ifPresent(identifier -> this.nestId = identifier);
        spawnRadius = view.getInt(SyncedData.SPAWN_RADIUS.name, 0);
        isEnabled = view.getBoolean(SyncedData.IS_ENABLED.name, false);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if(nestId != null)
            view.put(SyncedData.NEST_ID.name, Identifier.CODEC, this.nestId);
        if(managerId != null)
            view.put(SyncedData.MANAGER_ID.name, Identifier.CODEC, this.managerId);
        view.put(SyncedData.SPAWN_RADIUS.name, Codec.INT, this.spawnRadius);
        view.put(SyncedData.IS_ENABLED.name, Codec.BOOL, this.isEnabled);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void setStructureManagerId(Identifier structureManagerId) {
        this.managerId = structureManagerId;
        updateListeners();
    }

    public void setStructureNestId(Identifier structureNestId) {
        this.nestId = structureNestId;
        updateListeners();
    }

    public void setSpawnRadius(int newRadius) {
        this.spawnRadius = newRadius;
        updateListeners();
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        updateListeners();
    }

    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    public static void tickEvent(World world, BlockPos blockPos, BlockState blockState, StructureNestBlockEntity entity) {
        entity.tickEvent(world, blockState);
    }

    private void tickEvent(World world, BlockState blockState) {
        if(world.isClient)
            return;

        if(!blockState.get(StructureNestBlock.ENABLED)) {
            fails = 0;
            return;
        }

        if(managerId == null || nestId == null || world.getTickOrder() % 20 != 0) // every 1 seconds
            return;

        StructureManagerBlockEntity structureManagerBlockEntity = StructureManagerService.getClosest(world, pos, 20);
        if(structureManagerBlockEntity == null) {
            fails++;
        }
        else {
            if(structureManagerBlockEntity.subscribeNest(this.pos, this.managerId, this.nestId, this.spawnRadius))
            {
                world.breakBlock(pos, false);
                world.removeBlockEntity(pos);
                initialized = true;
                updateListeners();
            } else {
                fails++;
            }
        }
        if(fails >= 12) {
            world.breakBlock(pos, false);
            world.removeBlockEntity(pos);
            updateListeners();
        }
    }
}
