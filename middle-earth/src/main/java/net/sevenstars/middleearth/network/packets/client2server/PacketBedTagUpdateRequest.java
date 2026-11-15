package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenData;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;

import java.util.Optional;

public class PacketBedTagUpdateRequest extends ClientToServerPacket<PacketBedTagUpdateRequest> {
    public static final Id<PacketBedTagUpdateRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "bed_tag_update_request"));

    public static final PacketCodec<RegistryByteBuf, PacketBedTagUpdateRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.nestKind,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.job,
            PacketCodecs.string(BedTagScreenData.MAXIMUM_LENGTH), p -> p.npcClass,
            PacketCodecs.BOOLEAN, p -> p.resetLinks,
            PacketBedTagUpdateRequest::new
    );

    private final BlockPos pos;
    private final String nestKind;
    private final String job;
    private final String npcClass;
    private final boolean resetLinks;

    public PacketBedTagUpdateRequest(BlockPos pos, String nestKind, String job, String npcClass, boolean resetLinks) {
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
        player.getServer().execute(() -> {
            if(!StructureEditing.mayEdit(player, pos))
                return;
            if(!(player.getWorld().getBlockEntity(pos) instanceof BedTagHolder holder))
                return;
            BedTag tag = null;
            if(!nestKind.isEmpty()) {
                Identifier id = job.isEmpty() ? null : Identifier.tryParse(job);
                if(!BedTag.nestKinds().contains(nestKind) || !job.isEmpty() && (id == null || NpcJobs.get(id) == null))
                    return;
                Identifier classId = npcClass.isEmpty() ? null : Identifier.tryParse(npcClass);
                if(!npcClass.isEmpty() && (classId == null || !player.getWorld().getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS).containsId(classId)))
                    return;
                BedTag before = holder.getBedTag();
                tag = new BedTag(nestKind, Optional.ofNullable(id), Optional.ofNullable(classId));
                if(before != null && !resetLinks)
                    tag = tag.withLinks(before.links(), before.manual()).withManager(before.manager());
            }
            holder.setBedTag(tag);
        });
    }
}
