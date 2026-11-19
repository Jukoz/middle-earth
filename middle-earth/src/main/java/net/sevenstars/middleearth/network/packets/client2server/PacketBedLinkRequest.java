package net.sevenstars.middleearth.network.packets.client2server;

import net.sevenstars.middleearth.registries.content.structurefeatures.BuildFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Until the first manual link, its links are auto-found ones only the drawing client knows
public class PacketBedLinkRequest extends ClientToServerPacket<PacketBedLinkRequest> {
    public static final Id<PacketBedLinkRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "bed_link_request"));

    private static final int REACH = 64;
    private static final int MAXIMUM_LINKS = 32;

    public static final PacketCodec<RegistryByteBuf, PacketBedLinkRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.bed,
            BlockPos.PACKET_CODEC, p -> p.target,
            BlockPos.PACKET_CODEC.collect(PacketCodecs.toList(MAXIMUM_LINKS)), p -> p.automatic,
            PacketBedLinkRequest::new
    );

    private final BlockPos bed;
    private final BlockPos target;
    private final List<BlockPos> automatic;

    public PacketBedLinkRequest(BlockPos bed, BlockPos target, List<BlockPos> automatic) {
        this.bed = bed;
        this.target = target;
        this.automatic = automatic;
    }

    @Override
    public Id<PacketBedLinkRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketBedLinkRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getEntityWorld().getServer().execute(() -> {
            if(!StructureEditing.isBuilder(player) || bed.equals(target)) {
                StructureEditing.log("link of bed %s to %s refused: not a creative operator, or the bed itself".formatted(bed.toShortString(), target.toShortString()));
                return;
            }
            BlockPos at = player.getBlockPos();
            if(!at.isWithinDistance(bed, REACH) || !at.isWithinDistance(target, REACH)) {
                StructureEditing.log("link of bed %s to %s refused: more than %d blocks from the builder".formatted(bed.toShortString(), target.toShortString(), REACH));
                return;
            }
            if(!StructureEditing.isNear(player.getEntityWorld(), at, bed) || !StructureEditing.isNear(player.getEntityWorld(), at, target)) {
                StructureEditing.log("link of bed %s to %s refused: not loaded".formatted(bed.toShortString(), target.toShortString()));
                return;
            }
            BlockState state = player.getEntityWorld().getBlockState(bed);
            if(!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.PART) != BedPart.HEAD) {
                StructureEditing.log("link of bed %s refused: no bed head there".formatted(bed.toShortString()));
                return;
            }
            if(!(player.getEntityWorld().getBlockEntity(bed) instanceof BedTagHolder holder) || holder.getBedTag() == null) {
                StructureEditing.log("link of bed %s refused: the bed is not tagged".formatted(bed.toShortString()));
                return;
            }
            BedTag tag = holder.getBedTag();
            BlockPos offset = target.subtract(bed);
            // A chest or barrel becomes the bed's store, and clicking it again unlinks it and the nearest store is
            // used. A fire is a place of work, not a store
            Block clicked = player.getEntityWorld().getBlockState(target).getBlock();
            if(BuildFeatures.STORE.test(clicked) && BuildFeatures.stationOf(clicked) == null) {
                boolean untied = tag.store().filter(offset::equals).isPresent();
                holder.setBedTag(tag.withStore(untied ? Optional.empty() : Optional.of(offset)));
                player.sendMessage(Text.translatable("ui.%s.bed_link.%s".formatted(MiddleEarth.MOD_ID, untied ? "store_untied" : "store_tied"), clicked.getName()), true);
                StructureEditing.log("bed %s %s the store at %s (%s)".formatted(bed.toShortString(), untied ? "untied from" : "tied to", target.toShortString(), clicked.getName().getString()));
                return;
            }
            List<BlockPos> links = new ArrayList<>();
            if(tag.manual()) {
                links.addAll(tag.links());
            } else {
                for(BlockPos link : automatic) {
                    if(link.isWithinDistance(bed, REACH) && !link.equals(bed))
                        links.add(link.subtract(bed));
                }
            }
            boolean unlinked = links.remove(offset);
            if(!unlinked) {
                if(links.size() >= MAXIMUM_LINKS) {
                    StructureEditing.log("link of bed %s to %s refused: the bed already has %d links".formatted(bed.toShortString(), target.toShortString(), MAXIMUM_LINKS));
                    return;
                }
                links.add(offset);
            }
            holder.setBedTag(tag.withLinks(links, true));
            StructureEditing.log("bed %s %s %s (%s), %d links by hand now".formatted(bed.toShortString(), unlinked ? "unlinked from" : "linked to", target.toShortString(),
                    player.getEntityWorld().getBlockState(target).getBlock().getName().getString(), links.size()));
            Text block = player.getEntityWorld().getBlockState(target).getBlock().getName();
            player.sendMessage(Text.translatable("ui.%s.bed_link.%s".formatted(MiddleEarth.MOD_ID, unlinked ? "unlinked" : "linked"), block), true);
        });
    }
}
