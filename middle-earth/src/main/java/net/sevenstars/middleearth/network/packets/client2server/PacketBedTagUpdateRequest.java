package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenData;
import net.sevenstars.middleearth.registries.content.npcroles.NpcRoles;

import java.util.Optional;

public class PacketBedTagUpdateRequest extends ClientToServerPacket<PacketBedTagUpdateRequest> {
    public static final Id<PacketBedTagUpdateRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "bed_tag_update_request"));

    public static final PacketCodec<RegistryByteBuf, PacketBedTagUpdateRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.type,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.role,
            PacketCodecs.BOOLEAN, p -> p.resetLinks,
            PacketBedTagUpdateRequest::new
    );

    private final BlockPos pos;
    private final String type;
    private final String role;
    private final boolean resetLinks;

    public PacketBedTagUpdateRequest(BlockPos pos, String type, String role, boolean resetLinks) {
        this.pos = pos;
        this.type = type;
        this.role = role;
        this.resetLinks = resetLinks;
    }

    @Override
    public Id<PacketBedTagUpdateRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketBedTagUpdateRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getServer().execute(() -> {
            if(!player.isCreative() || !player.isCreativeLevelTwoOp() || !player.getBlockPos().isWithinDistance(pos, 8))
                return;
            if(!(player.getWorld().getBlockEntity(pos) instanceof BedTagHolder holder))
                return;
            BedTag tag = null;
            if(!type.isEmpty()) {
                Identifier id = role.isEmpty() ? null : Identifier.tryParse(role);
                if(!BedTag.types().contains(type) || !role.isEmpty() && (id == null || NpcRoles.get(id) == null))
                    return;
                BedTag before = holder.getBedTag();
                tag = new BedTag(type, Optional.ofNullable(id));
                if(before != null && !resetLinks)
                    tag = tag.withLinks(before.links(), before.manual());
            }
            holder.setBedTag(tag);
        });
    }
}
