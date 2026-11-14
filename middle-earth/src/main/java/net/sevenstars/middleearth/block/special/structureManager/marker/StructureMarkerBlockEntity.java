package net.sevenstars.middleearth.block.special.structureManager.marker;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.block.special.structureManager.GeneratedStructures;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenHandler;
import org.jetbrains.annotations.Nullable;

public class StructureMarkerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<StructureMarkerScreenData> {
    private static final String ID = "structure_marker";
    private static final String MARKER_ID = "%s.MarkerId".formatted(ID);
    private static final String VALUE = "%s.Value".formatted(ID);
    private static final String COUNT = "%s.Count".formatted(ID);
    private static final String RESPAWNS = "%s.Respawns".formatted(ID);
    public static final int MAXIMUM_VALUE_LENGTH = 128;
    public static final int MAXIMUM_COUNT = 64;

    private MarkerKind kind = MarkerKind.GUARD_POST;
    // A nest or an entity type for the markers that spawn something
    private String value = "";
    private int count = 1;
    private boolean respawns = false;
    private boolean checked;

    public StructureMarkerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.STRUCTURE_MARKER, pos, state);
    }

    public MarkerKind getKind() {
        return kind;
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public boolean getRespawns() {
        return respawns;
    }

    public void update(MarkerKind kind, String value, int count, boolean respawns) {
        this.kind = kind;
        this.value = value.length() > MAXIMUM_VALUE_LENGTH ? value.substring(0, MAXIMUM_VALUE_LENGTH) : value;
        this.count = Math.clamp(count, 1, MAXIMUM_COUNT);
        this.respawns = respawns;
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public StructureMarkerScreenData getScreenOpeningData(ServerPlayerEntity player) {
        return screenData();
    }

    public StructureMarkerScreenData screenData() {
        return StructureMarkerScreenData.create(pos, false, kind, value, count, respawns, getCachedState().get(StructureMarkerBlock.FACING));
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StructureMarkerScreenHandler(syncId, playerInventory, screenData());
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        kind = view.read(MARKER_ID, MarkerKind.CODEC).orElse(MarkerKind.GUARD_POST);
        value = view.getString(VALUE, "");
        count = view.getInt(COUNT, 1);
        respawns = view.getBoolean(RESPAWNS, false);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put(MARKER_ID, MarkerKind.CODEC, kind);
        view.putString(VALUE, value);
        view.putInt(COUNT, count);
        view.putBoolean(RESPAWNS, respawns);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    // The builder's tool draws the marker by its kind, which the client has to know
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createComponentlessNbt(registries);
    }

    public static void tick(World world, BlockPos pos, BlockState state, StructureMarkerBlockEntity marker) {
        if(marker.checked || !(world instanceof ServerWorld serverWorld))
            return;
        marker.checked = true;
        // A marker in a structure that was generated has done its work, which is in the data of its template
        if(GeneratedStructures.contains(serverWorld, pos))
            serverWorld.setBlockState(pos, state.getFluidState().getBlockState(), Block.NOTIFY_ALL);
    }
}
