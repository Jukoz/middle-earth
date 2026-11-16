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
import net.sevenstars.middleearth.commands.custom.BuildSectionEditing;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import net.minecraft.network.codec.PacketCodecs;
import java.util.List;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;

// Sets what of a build is saved to the box between two corners. The build is the one the anchor stands in: a piece of the
// structure the builder has loaded, or a section with a structure block of its own. With a name, the box is made a new
// section of that name instead, wherever the anchor is
public class PacketStructureBoundsRequest extends ClientToServerPacket<PacketStructureBoundsRequest> {
    public static final Id<PacketStructureBoundsRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_bounds_request"));

    private static final int MAXIMUM_NAME_LENGTH = 128;

    public static final PacketCodec<RegistryByteBuf, PacketStructureBoundsRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.anchor,
            BlockPos.PACKET_CODEC, p -> p.first,
            BlockPos.PACKET_CODEC, p -> p.second,
            PacketCodecs.string(MAXIMUM_NAME_LENGTH), p -> p.name,
            PacketStructureBoundsRequest::new
    );

    private final BlockPos anchor;
    private final BlockPos first;
    private final BlockPos second;
    private final String name;

    public PacketStructureBoundsRequest(BlockPos anchor, BlockPos first, BlockPos second, String name) {
        this.name = name;
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
            int limit = StructureEditing.MAXIMUM_SIZE;
            if(!world.isInBuildLimit(first) || !world.isInBuildLimit(second) || !BuildSectionEditing.fits(box)) {
                StructureEditing.log("bounds from %s to %s refused: outside the world or over %d blocks a side".formatted(first.toShortString(), second.toShortString(), limit));
                player.sendMessage(Text.translatable("ui.%s.structure_bounds.too_big".formatted(MiddleEarth.MOD_ID), limit), true);
                return;
            }
            String size = "%dx%dx%d".formatted(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
            List<BuildSections.Section> sections = BuildSections.near(world, anchor);
            if(!name.isBlank()) {
                create(world, player, sections, box, size);
                return;
            }
            StructureEditSession session = StructureEditSession.claim(player.getUuid());
            StructureEditSession.Piece piece = session == null || !session.getDimension().equals(world.getRegistryKey()) ? null : session.pieceContaining(anchor);
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
            BuildSections.Section section = BuildSections.at(sections, anchor);
            if(section == null || !BuildSectionEditing.resize(world, section, box)) {
                StructureEditing.log("bounds from %s to %s refused: the block at %s is in no build that is loaded".formatted(first.toShortString(), second.toShortString(), anchor.toShortString()));
                player.sendMessage(Text.translatable("ui.%s.structure_bounds.no_build".formatted(MiddleEarth.MOD_ID)), true);
                return;
            }
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.set".formatted(MiddleEarth.MOD_ID), pathOf(section.name()), size), true);
        });
    }

    private void create(ServerWorld world, ServerPlayerEntity player, List<BuildSections.Section> sections, BlockBox box, String size) {
        Identifier id = BuildSectionEditing.named(name);
        if(id == null) {
            player.sendMessage(Text.translatable("ui.%s.build_section.bad_name".formatted(MiddleEarth.MOD_ID)), true);
            return;
        }
        if(world.getStructureTemplateManager().getTemplate(id).isPresent() || sections.stream().anyMatch(section -> section.name().equals(id.toString()))) {
            StructureEditing.log("section %s not made: there is a template or a section of that name".formatted(id));
            player.sendMessage(Text.translatable("ui.%s.build_section.taken".formatted(MiddleEarth.MOD_ID), id.getPath()), true);
            return;
        }
        BlockPos at = BuildSectionEditing.blockFor(world, box, sections);
        if(!world.isInBuildLimit(at) || !BuildSectionEditing.place(world, player, id, box, at)) {
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.no_build".formatted(MiddleEarth.MOD_ID)), true);
            return;
        }
        StructureEditSession.beginSingle(world.getServer(), player.getUuid());
        StructureEditing.log("new section %s, %s from %s to %s, structure block at %s".formatted(id, size, first.toShortString(), second.toShortString(), at.toShortString()));
        player.sendMessage(Text.translatable("ui.%s.build_section.made".formatted(MiddleEarth.MOD_ID), id.getPath(), size), true);
        List<BuildSections.Section> now = BuildSections.near(world, anchor);
        List<BuildSections.Section> cutUp = BuildSectionEditing.cutUp(now);
        for(BuildSections.Section whole : cutUp) {
            BuildSectionEditing.remove(world, whole);
            player.sendMessage(Text.translatable("ui.%s.build_section.cut_up".formatted(MiddleEarth.MOD_ID), pathOf(whole.name())), false);
        }
        // The section it was cut from still saves all of it, this part too, until the rest of it is cut up as well
        sections.stream().filter(section -> section.box().intersects(box) && !cutUp.contains(section)).findFirst().ifPresent(over ->
                player.sendMessage(Text.translatable("ui.%s.build_section.overlaps".formatted(MiddleEarth.MOD_ID), pathOf(over.name()), over.name()), false));
    }

    private static String pathOf(String name) {
        Identifier id = Identifier.tryParse(name);
        return id == null ? name : id.getPath();
    }
}
