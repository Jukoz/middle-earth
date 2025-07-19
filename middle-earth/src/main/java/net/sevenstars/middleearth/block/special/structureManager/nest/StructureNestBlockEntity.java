package net.sevenstars.middleearth.block.special.structureManager.nest;

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
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StructureNestBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_nest";

    private enum SyncedData {
        MANAGER_ID("%s.ManagerId".formatted(ID)),
        NEST_ID("%s.NestId".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }
    @Nullable
    protected Identifier managerId;
    @Nullable
    protected Identifier nestId;

    public StructureNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_NEST, pos, state);
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new StructureNestScreenData(this.pos,
                Optional.ofNullable(this.managerId),
                Optional.ofNullable(this.nestId)
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
                Optional.ofNullable(this.nestId)));
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Optional<Identifier> managerId = view.read(SyncedData.MANAGER_ID.name, Identifier.CODEC);
        managerId.ifPresent(identifier -> this.managerId = identifier);
        Optional<Identifier> nestId = view.read(SyncedData.NEST_ID.name, Identifier.CODEC);
        nestId.ifPresent(identifier -> this.nestId = identifier);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if(nestId != null)
            view.put(SyncedData.NEST_ID.name, Identifier.CODEC, this.nestId);
        if(managerId != null)
            view.put(SyncedData.MANAGER_ID.name, Identifier.CODEC, this.managerId);
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

    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}
