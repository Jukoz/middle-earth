package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
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
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenData;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.util.Optional;

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
    // Empty to keep the bed's current place
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
        player.getServer().execute(() -> {
            if(!StructureEditing.mayEdit(player, pos))
                return;
            BlockState state = player.getWorld().getBlockState(pos);
            if(!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.PART) != BedPart.HEAD
                    || !(player.getWorld().getBlockEntity(pos) instanceof BedTagHolder holder)) {
                StructureEditing.log("bed tag at %s refused: no bed head there".formatted(pos.toShortString()));
                return;
            }
            BedTag tag = null;
            if(!nestKind.isEmpty()) {
                Identifier id = job.isEmpty() ? null : Identifier.tryParse(job);
                if(!BedTag.nestKinds().contains(nestKind) || !job.isEmpty() && (id == null || NpcJobs.get(id) == null)) {
                    StructureEditing.log("bed tag at %s refused: unknown nest kind `%s` or job `%s`".formatted(pos.toShortString(), nestKind, job));
                    return;
                }
                // The screen only offers jobs that fit who lives there, so a forged request has to meet the same rule
                if(id != null && !NpcJobs.fits(id, BedTag.folkOf(player.getWorld().getRegistryManager(), nestKind), nestKind)) {
                    StructureEditing.log("bed tag at %s refused: job `%s` does not fit `%s`, who are %s".formatted(pos.toShortString(), job, nestKind,
                            BedTag.folkOf(player.getWorld().getRegistryManager(), nestKind)));
                    return;
                }
                Identifier classId = npcClass.isEmpty() ? null : Identifier.tryParse(npcClass);
                if(!npcClass.isEmpty() && (classId == null || !player.getWorld().getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS).containsId(classId))) {
                    StructureEditing.log("bed tag at %s refused: unknown class `%s`".formatted(pos.toShortString(), npcClass));
                    return;
                }
                BedTag before = holder.getBedTag();
                Optional<Identifier> placeId = place.isEmpty() ? before == null ? Optional.empty() : before.place() : Optional.ofNullable(Identifier.tryParse(place));
                StructureManagerData placeData = placeId.map(found -> StructureManagerService.getStructureManagerData(player.getWorld(), found)).orElse(null);
                if(!place.isEmpty() && (placeData == null || placeData.findPeopleNest(nestKind) == null)) {
                    StructureEditing.log("bed tag at %s refused: place `%s` has no `%s`".formatted(pos.toShortString(), place, nestKind));
                    return;
                }
                tag = new BedTag(nestKind, Optional.ofNullable(id), Optional.ofNullable(classId), placeId);
                if(before != null && !resetLinks)
                    tag = tag.withLinks(before.links(), before.manual()).withManager(before.manager()).withStore(before.store());
            }
            holder.setBedTag(tag);
            StructureEditing.log("bed at %s tagged %s".formatted(pos.toShortString(), tag == null ? "nobody" : "`%s` of `%s` job `%s` class `%s` links %d%s".formatted(
                    tag.nestKind(), tag.place().map(Identifier::toString).orElse("its manager's place"), tag.job().map(Identifier::toString).orElse("as the nest"), tag.npcClass().map(Identifier::toString).orElse("of the nest"),
                    tag.links().size(), tag.manual() ? " by hand" : "")));
        });
    }
}
