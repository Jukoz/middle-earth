package net.sevenstars.middleearth.block.special.structureManager.marker;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.structure.StructureStart;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenHandler;
import org.jetbrains.annotations.Nullable;

public class StructureMarkerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<StructureMarkerScreenData> {
    private static final String ID = "structure_marker";
    private static final String MARKER_ID = "%s.MarkerId".formatted(ID);

    private MarkerKind kind = MarkerKind.GUARD_POST;
    private boolean checked;

    public StructureMarkerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.STRUCTURE_MARKER, pos, state);
    }

    public MarkerKind getKind() {
        return kind;
    }

    public void setKind(MarkerKind kind) {
        this.kind = kind;
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public StructureMarkerScreenData getScreenOpeningData(ServerPlayerEntity player) {
        return new StructureMarkerScreenData(pos, kind);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StructureMarkerScreenHandler(syncId, playerInventory, new StructureMarkerScreenData(pos, kind));
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        kind = view.read(MARKER_ID, MarkerKind.CODEC).orElse(MarkerKind.GUARD_POST);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put(MARKER_ID, MarkerKind.CODEC, kind);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public static void tick(World world, BlockPos pos, BlockState state, StructureMarkerBlockEntity marker) {
        if(marker.checked || !(world instanceof ServerWorld serverWorld))
            return;
        marker.checked = true;
        // A marker in a structure that was generated has done its work, which is in the data of its template
        StructureStart start = serverWorld.getStructureAccessor().getStructureContaining(pos, structure -> true);
        if(start != null && start.hasChildren())
            serverWorld.setBlockState(pos, state.getFluidState().getBlockState(), Block.NOTIFY_ALL);
    }
}
