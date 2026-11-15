package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.block.Block;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
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
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlock;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;

public class PacketStructureMarkerEditRequest extends ClientToServerPacket<PacketStructureMarkerEditRequest> {
    public static final Id<PacketStructureMarkerEditRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_marker_edit_request"));

    public static final PacketCodec<RegistryByteBuf, PacketStructureMarkerEditRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketCodecs.BOOLEAN, p -> p.isNew,
            PacketCodecs.BOOLEAN, p -> p.remove,
            PacketCodecs.indexed(index -> MarkerKind.values()[index], MarkerKind::ordinal), p -> p.kind,
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
        player.getServer().execute(() -> {
            if(!StructureEditing.mayEdit(player, pos))
                return;
            if(!(player.getWorld() instanceof ServerWorld world) || !world.isInBuildLimit(pos))
                return;
            BlockState existing = world.getBlockState(pos);
            boolean marker = existing.isOf(DecorativeBlockRegistryME.STRUCTURE_MARKER);
            if(remove) {
                if(marker)
                    world.setBlockState(pos, existing.getFluidState().getBlockState(), Block.NOTIFY_ALL);
                return;
            }
            if(!facing.getAxis().isHorizontal() || !validValue())
                return;
            if(isNew) {
                if(!existing.isReplaceable())
                    return;
                BlockState placed = DecorativeBlockRegistryME.STRUCTURE_MARKER.getDefaultState()
                        .with(StructureMarkerBlock.FACING, facing)
                        .with(StructureMarkerBlock.WATERLOGGED, existing.getFluidState().isEqualAndStill(Fluids.WATER));
                world.setBlockState(pos, placed, Block.NOTIFY_ALL);
            } else if(marker) {
                world.setBlockState(pos, existing.with(StructureMarkerBlock.FACING, facing), Block.NOTIFY_ALL);
            } else {
                return;
            }
            if(world.getBlockEntity(pos) instanceof StructureMarkerBlockEntity entity)
                entity.update(kind, kind == MarkerKind.SPAWN_NPC || kind == MarkerKind.SPAWN_ANIMAL ? value : "", count, kind == MarkerKind.SPAWN_ANIMAL && respawns);
        });
    }

    private boolean validValue() {
        if(kind == MarkerKind.SPAWN_NPC)
            return BedTag.nestKinds().contains(value);
        if(kind == MarkerKind.SPAWN_ANIMAL) {
            Identifier id = Identifier.tryParse(value);
            return id != null && Registries.ENTITY_TYPE.containsId(id);
        }
        return true;
    }
}
