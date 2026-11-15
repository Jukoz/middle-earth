package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.commands.custom.CommandStructureEdit;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;

// Sets what of a build is saved to the box between two corners. The build is the one the anchor stands in: a piece of the
// structure the builder has loaded, or a single template that was loaded with its structure block
public class PacketStructureBoundsRequest extends ClientToServerPacket<PacketStructureBoundsRequest> {
    public static final Id<PacketStructureBoundsRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_bounds_request"));

    // In blocks along one side
    private static final int MAXIMUM_SIZE = 128;

    public static final PacketCodec<RegistryByteBuf, PacketStructureBoundsRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.anchor,
            BlockPos.PACKET_CODEC, p -> p.first,
            BlockPos.PACKET_CODEC, p -> p.second,
            PacketStructureBoundsRequest::new
    );

    private final BlockPos anchor;
    private final BlockPos first;
    private final BlockPos second;

    public PacketStructureBoundsRequest(BlockPos anchor, BlockPos first, BlockPos second) {
        this.anchor = anchor;
        this.first = first;
        this.second = second;
    }

    @Override
    public Id<PacketStructureBoundsRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureBoundsRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getServer().execute(() -> {
            // The corner clicked last is the one the builder stands at
            if(!StructureEditing.mayEdit(player, second) || !(player.getWorld() instanceof ServerWorld world))
                return;
            BlockBox box = BlockBox.create(first, second);
            if(!world.isInBuildLimit(first) || !world.isInBuildLimit(second) || box.getBlockCountX() > MAXIMUM_SIZE || box.getBlockCountY() > MAXIMUM_SIZE || box.getBlockCountZ() > MAXIMUM_SIZE) {
                StructureEditing.log("bounds from %s to %s refused: outside the world or over %d blocks a side".formatted(first.toShortString(), second.toShortString(), MAXIMUM_SIZE));
                player.sendMessage(Text.translatable("ui.%s.structure_bounds.too_big".formatted(MiddleEarth.MOD_ID), MAXIMUM_SIZE), true);
                return;
            }
            String size = "%dx%dx%d".formatted(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
            StructureEditSession session = StructureEditSession.claim(player.getUuid());
            StructureEditSession.Piece piece = session == null || !session.getDimension().equals(world.getRegistryKey()) ? null : session.pieceAt(anchor);
            if(piece != null) {
                if(!session.isSpaced()) {
                    player.sendMessage(Text.translatable("ui.%s.structure_bounds.view_only".formatted(MiddleEarth.MOD_ID)), true);
                    return;
                }
                StructureEditSession.Piece inTheWay = session.resize(world, piece, box);
                if(inTheWay != null)
                    player.sendMessage(Text.translatable("ui.%s.structure_bounds.in_the_way".formatted(MiddleEarth.MOD_ID), inTheWay.template().getPath()), true);
                else
                    player.sendMessage(Text.translatable("ui.%s.structure_bounds.set".formatted(MiddleEarth.MOD_ID), piece.template().getPath(), size), true);
                return;
            }
            Identifier template = CommandStructureEdit.resizeLoaded(world, player, box);
            if(template == null) {
                StructureEditing.log("bounds from %s to %s refused: the block at %s is in no build that is loaded".formatted(first.toShortString(), second.toShortString(), anchor.toShortString()));
                player.sendMessage(Text.translatable("ui.%s.structure_bounds.no_build".formatted(MiddleEarth.MOD_ID)), true);
                return;
            }
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.set".formatted(MiddleEarth.MOD_ID), template.getPath(), size), true);
        });
    }
}
