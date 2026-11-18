package net.sevenstars.middleearth.block.special.structureManager.marker;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
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
import net.sevenstars.middleearth.block.special.structureManager.GeneratedStructures;
import net.sevenstars.middleearth.block.special.structureManager.features.MarkerAnimals;
import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.block.special.structureManager.features.SpawnNestManager;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenHandler;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class StructureMarkerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<StructureMarkerScreenData>, NestHost {
    private static final String ID = "structure_marker";
    private static final String MARKER_ID = "%s.MarkerId".formatted(ID);
    private static final String VALUE = "%s.Value".formatted(ID);
    private static final String COUNT = "%s.Count".formatted(ID);
    private static final String RESPAWNS = "%s.Respawns".formatted(ID);
    public static final int MAXIMUM_VALUE_LENGTH = 128;
    private static final String ANIMALS = "Animals";
    // In ticks, between checks of what a marker has spawned
    private static final int SPAWN_CHECK_INTERVAL = 100;
    public static final int MAXIMUM_COUNT = 64;

    private MarkerKind kind = MarkerKind.GUARD_POST;
    private String value = "";
    private int count = 1;
    private boolean respawns = false;
    private boolean checked;
    private boolean inStructure;
    // Spawned people (spawn_npc) or animals (spawn_animal). This world only
    private @Nullable SpawnNestManager nest;
    private @Nullable MarkerAnimals animals;

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
        nest = NestHost.load(view);
        animals = NestHost.isOfThisWorld(view) ? view.read(ANIMALS, MarkerAnimals.CODEC).orElse(null) : null;
    }

    @Override
    public @Nullable SpawnNestManager getNest() {
        return nest;
    }

    @Override
    public void setNest(@Nullable SpawnNestManager nest) {
        this.nest = nest;
    }

    @Override
    public void markRemoved() {
        StructureManagerService.unregisterHost(world, pos);
        super.markRemoved();
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put(MARKER_ID, MarkerKind.CODEC, kind);
        view.putString(VALUE, value);
        view.putInt(COUNT, count);
        view.putBoolean(RESPAWNS, respawns);
        NestHost.save(view, nest);
        view.putNullable(ANIMALS, MarkerAnimals.CODEC, animals);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createComponentlessNbt(registries);
    }

    public static void tick(World world, BlockPos pos, BlockState state, StructureMarkerBlockEntity marker) {
        if(!(world instanceof ServerWorld serverWorld))
            return;
        boolean spawns = marker.kind == MarkerKind.SPAWN_NPC || marker.kind == MarkerKind.SPAWN_ANIMAL;
        if(!marker.checked) {
            marker.checked = true;
            marker.inStructure = GeneratedStructures.contains(serverWorld, pos);
            if(marker.inStructure && !spawns) {
                // In a generated structure the marker's work is already in the template's data
                serverWorld.setBlockState(pos, state.getFluidState().getBlockState(), Block.NOTIFY_ALL);
                return;
            }
            if(marker.inStructure && !state.get(StructureMarkerBlock.HIDDEN))
                serverWorld.setBlockState(pos, state.with(StructureMarkerBlock.HIDDEN, true), Block.NOTIFY_ALL);
        }
        if(marker.inStructure && spawns && Math.floorMod(world.getTime() + pos.hashCode(), SPAWN_CHECK_INTERVAL) == 0)
            marker.spawn(serverWorld);
    }

    // People form a nest spawned on the marker. Animals are tracked apart and respawn only if set
    private void spawn(ServerWorld serverWorld) {
        if(kind == MarkerKind.SPAWN_NPC) {
            SpawnNestNodeData data = StructureManagerService.nestForMarker(serverWorld, value, Optional.empty());
            if(data == null)
                return;
            if(nest == null || !nest.getId().equals(data.getId())) {
                nest = new SpawnNestManager(data, pos, 0, count);
                markDirty();
            }
            StructureManagerService.registerHost(serverWorld, pos);
            nest.tick(serverWorld.getTime(), serverWorld, pos);
            return;
        }
        Identifier typeId = Identifier.tryParse(value);
        EntityType<?> type = typeId == null ? null : Registries.ENTITY_TYPE.getOptionalValue(typeId).orElse(null);
        if(type == null)
            return;
        if(animals == null) {
            // Try again once the surrounding chunks are loaded
            if(!serverWorld.shouldTickEntityAt(pos))
                return;
            animals = new MarkerAnimals(pos, List.of(), -1);
            animals.spawn(serverWorld, type, count);
            markDirty();
        } else if(respawns && animals.topUp(serverWorld, type, count)) {
            markDirty();
        }
    }
}
