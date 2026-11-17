package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.block.Block;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenData;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlock;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;

public class PacketStructureMarkerEditRequest extends ClientToServerPacket<PacketStructureMarkerEditRequest> {
    public static final Id<PacketStructureMarkerEditRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_marker_edit_request"));

    public static final PacketCodec<RegistryByteBuf, PacketStructureMarkerEditRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketCodecs.BOOLEAN, p -> p.isNew,
            PacketCodecs.BOOLEAN, p -> p.remove,
            MarkerKind.PACKET_CODEC, p -> p.kind,
            PacketCodecs.string(StructureMarkerBlockEntity.MAXIMUM_VALUE_LENGTH), p -> p.value,
            PacketCodecs.VAR_INT, p -> p.count,
            PacketCodecs.BOOLEAN, p -> p.respawns,
            PacketCodecs.indexed(Direction::byIndex, Direction::getIndex), p -> p.facing,
            PacketStructureMarkerEditRequest::new
    );

    private final BlockPos pos;
    private final boolean isNew;
    private final boolean remove;
    private final MarkerKind kind;
    private final String value;
    private final int count;
    private final boolean respawns;
    private final Direction facing;

    public PacketStructureMarkerEditRequest(BlockPos pos, boolean isNew, boolean remove, MarkerKind kind, String value, int count, boolean respawns, Direction facing) {
        this.pos = pos;
        this.isNew = isNew;
        this.remove = remove;
        this.kind = kind;
        this.value = value;
        this.count = count;
        this.respawns = respawns;
        this.facing = facing;
    }

    @Override
    public Id<PacketStructureMarkerEditRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureMarkerEditRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getEntityWorld().getServer().execute(() -> {
            if(!StructureEditing.mayEdit(player, pos))
                return;
            if(!(player.getEntityWorld() instanceof ServerWorld world) || !world.isInBuildLimit(pos)) {
                StructureEditing.log("marker at %s refused: outside the world".formatted(pos.toShortString()));
                return;
            }
            BlockState existing = world.getBlockState(pos);
            boolean marker = existing.isOf(DecorativeBlockRegistryME.STRUCTURE_MARKER);
            if(remove) {
                if(marker)
                    world.setBlockState(pos, existing.getFluidState().getBlockState(), Block.NOTIFY_ALL);
                StructureEditing.log(marker ? "marker at %s removed".formatted(pos.toShortString()) : "no marker at %s to remove".formatted(pos.toShortString()));
                return;
            }
            if(!facing.getAxis().isHorizontal() || !validValue()) {
                StructureEditing.log("marker %s at %s refused: facing %s, value `%s`".formatted(kind.asString(), pos.toShortString(), facing.asString(), value));
                return;
            }
            if(isNew) {
                if(!existing.isReplaceable()) {
                    StructureEditing.log("marker %s at %s refused: %s is in the way".formatted(kind.asString(), pos.toShortString(), existing.getBlock().getName().getString()));
                    return;
                }
                BlockState placed = DecorativeBlockRegistryME.STRUCTURE_MARKER.getDefaultState()
                        .with(StructureMarkerBlock.FACING, facing)
                        .with(StructureMarkerBlock.WATERLOGGED, existing.getFluidState().isEqualAndStill(Fluids.WATER));
                world.setBlockState(pos, placed, Block.NOTIFY_ALL);
            } else if(marker) {
                world.setBlockState(pos, existing.with(StructureMarkerBlock.FACING, facing), Block.NOTIFY_ALL);
            } else {
                StructureEditing.log("marker %s at %s refused: the marker that was to be changed is gone".formatted(kind.asString(), pos.toShortString()));
                return;
            }
            StructureEditing.log("marker %s %s at %s facing %s%s, which is %s".formatted(kind.asString(), isNew ? "placed" : "changed", pos.toShortString(), facing.asString(),
                    value.isEmpty() ? "" : " value `%s` x%d%s".formatted(value, count, respawns ? " respawning" : ""),
                    world.getBlockState(pos.offset(facing)).getBlock().getName().getString()));
            if(world.getBlockEntity(pos) instanceof StructureMarkerBlockEntity entity)
                entity.update(kind, kind == MarkerKind.SPAWN_NPC || kind == MarkerKind.SPAWN_ANIMAL ? value : "", count, kind == MarkerKind.SPAWN_ANIMAL && respawns);
        });
    }

    private boolean validValue() {
        // A nest id, or only a nest kind for markers placed before nests had full names
        if(kind == MarkerKind.SPAWN_NPC) {
            Identifier nest = Identifier.tryParse(value);
            return BedTag.nestKinds().contains(value) || nest != null && BedTag.peopleNests().containsKey(nest);
        }
        if(kind == MarkerKind.SPAWN_ANIMAL) {
            return StructureMarkerScreenData.allAnimals().contains(value);
        }
        return true;
    }
}
