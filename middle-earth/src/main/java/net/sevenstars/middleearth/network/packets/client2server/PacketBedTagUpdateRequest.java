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
import net.sevenstars.middleearth.commands.custom.BedTagEditing;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenData;

public class PacketBedTagUpdateRequest extends ClientToServerPacket<PacketBedTagUpdateRequest> {
    public static final Id<PacketBedTagUpdateRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "bed_tag_update_request"));

    public static final PacketCodec<RegistryByteBuf, PacketBedTagUpdateRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.place,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.nestKind,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.job,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.npcClass,
            PacketCodecs.BOOLEAN, p -> p.resetLinks,
            PacketBedTagUpdateRequest::new
    );

    private final BlockPos pos;
    // Empty keeps the bed's current place
    private final String place;
    private final String nestKind;
    private final String job;
    private final String npcClass;
    private final boolean resetLinks;

    public PacketBedTagUpdateRequest(BlockPos pos, String place, String nestKind, String job, String npcClass, boolean resetLinks) {
        this.place = place;
        this.npcClass = npcClass;
        this.pos = pos;
        this.nestKind = nestKind;
        this.job = job;
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
        player.getEntityWorld().getServer().execute(() -> BedTagEditing.retag(player, pos, place, nestKind, job, npcClass, resetLinks));
    }
}
