package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.commands.custom.BuildSectionEditing;

import java.util.List;

// What the builder's tool does to a section through its structure block: renames it or removes its box
public class PacketSectionEditRequest extends ClientToServerPacket<PacketSectionEditRequest> {
    public static final Id<PacketSectionEditRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "section_edit_request"));
    public static final int RENAME = 0;
    public static final int REMOVE = 1;

    public static final PacketCodec<RegistryByteBuf, PacketSectionEditRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketCodecs.VAR_INT, p -> p.what,
            PacketCodecs.string(BuildSections.MAXIMUM_NAME_LENGTH), p -> p.name,
            PacketSectionEditRequest::new
    );

    private final BlockPos pos;
    private final int what;
    private final String name;

    public PacketSectionEditRequest(BlockPos pos, int what, String name) {
        this.pos = pos;
        this.what = what;
        this.name = name;
    }

    @Override
    public Id<PacketSectionEditRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketSectionEditRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getServer().execute(() -> {
            if(!StructureEditing.mayEdit(player, pos) || !(player.getWorld() instanceof ServerWorld world))
                return;
            List<BuildSections.Section> sections = BuildSections.near(world, pos);
            BuildSections.Section section = sections.stream().filter(found -> found.block().equals(pos)).findFirst().orElse(null);
            if(section == null)
                return;
            if(what == REMOVE) {
                BuildSectionEditing.remove(world, section);
                player.sendMessage(Text.translatable("ui.%s.build_section.removed".formatted(MiddleEarth.MOD_ID), section.name()), true);
                return;
            }
            Identifier id = BuildSectionEditing.named(name);
            if(id == null) {
                player.sendMessage(Text.translatable("ui.%s.build_section.bad_name".formatted(MiddleEarth.MOD_ID)), true);
                return;
            }
            if(world.getStructureTemplateManager().getTemplate(id).isPresent() || sections.stream().anyMatch(found -> found.name().equals(id.toString()))) {
                player.sendMessage(Text.translatable("ui.%s.build_section.taken".formatted(MiddleEarth.MOD_ID), id.getPath()), true);
                return;
            }
            if(BuildSectionEditing.rename(world, section, id)) {
                StructureEditing.log("%s is now named %s".formatted(section.name(), id));
                player.sendMessage(Text.translatable("ui.%s.build_section.renamed".formatted(MiddleEarth.MOD_ID), section.name(), id.getPath()), true);
            }
        });
    }
}
