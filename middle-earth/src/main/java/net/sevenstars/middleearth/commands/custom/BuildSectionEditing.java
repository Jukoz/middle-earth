package net.sevenstars.middleearth.commands.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.Orientation;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.command.argument.BlockArgumentParser;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.chunk.WorldChunk;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuildSectionEditing {
    static final String AIR = "minecraft:air";

    // A template or another section already has the name
    public static boolean isTaken(ServerWorld world, List<BuildSections.Section> sections, Identifier id) {
        return world.getStructureTemplateManager().getTemplate(id).isPresent() || sections.stream().anyMatch(section -> section.name().equals(id.toString()));
    }

    public static boolean fits(BlockBox box) {
        return box.getBlockCountX() <= StructureEditing.MAXIMUM_SIZE && box.getBlockCountY() <= StructureEditing.MAXIMUM_SIZE && box.getBlockCountZ() <= StructureEditing.MAXIMUM_SIZE;
    }

    // Empty block by the box nearest the first corner, in no section. Buried boxes use the first corner
    public static BlockPos blockFor(ServerWorld world, BlockBox box, List<BuildSections.Section> sections) {
        BlockPos corner = new BlockPos(box.getMinX(), box.getMinY(), box.getMinZ());
        BlockPos nearest = null;
        for(BlockPos pos : BlockPos.iterate(box.getMinX() - 1, box.getMinY() - 1, box.getMinZ() - 1, box.getMaxX() + 1, box.getMaxY() + 1, box.getMaxZ() + 1)) {
            int outside = (pos.getX() < box.getMinX() || pos.getX() > box.getMaxX() ? 1 : 0) + (pos.getY() < box.getMinY() || pos.getY() > box.getMaxY() ? 1 : 0)
                    + (pos.getZ() < box.getMinZ() || pos.getZ() > box.getMaxZ() ? 1 : 0);
            if(outside != 1 || nearest != null && nearest.getSquaredDistance(corner) <= pos.getSquaredDistance(corner))
                continue;
            if(!world.isInBuildLimit(pos) || !world.getBlockState(pos).isAir() || sections.stream().anyMatch(section -> section.box().contains(pos)))
                continue;
            nearest = pos.toImmutable();
        }
        return nearest != null ? nearest : corner.down();
    }

    public static boolean place(ServerWorld world, ServerPlayerEntity player, Identifier id, BlockBox box, BlockPos at) {
        world.setBlockState(at, Blocks.STRUCTURE_BLOCK.getDefaultState(), Block.NOTIFY_ALL);
        if(!(world.getBlockEntity(at) instanceof StructureBlockBlockEntity block))
            return false;
        block.setMode(StructureBlockMode.SAVE);
        block.setTemplateName(id);
        block.setIgnoreEntities(true);
        block.setAuthor(player);
        setBox(world, block, box);
        return true;
    }

    private static void setBox(ServerWorld world, StructureBlockBlockEntity block, BlockBox box) {
        BlockPos at = block.getPos();
        block.setOffset(new BlockPos(box.getMinX() - at.getX(), box.getMinY() - at.getY(), box.getMinZ() - at.getZ()));
        block.setSize(new Vec3i(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ()));
        block.markDirty();
        world.updateListeners(at, world.getBlockState(at), world.getBlockState(at), Block.NOTIFY_ALL);
    }

    public static boolean resize(ServerWorld world, BuildSections.Section section, BlockBox box) {
        if(!(world.getBlockEntity(section.block()) instanceof StructureBlockBlockEntity block))
            return false;
        setBox(world, block, box);
        StructureEditing.log("%s given the bounds %s where it had %s, by its structure block at %s".formatted(section.name(), PieceLayout.describe(box), PieceLayout.describe(section.box()), section.block().toShortString()));
        return true;
    }

    public static boolean rename(ServerWorld world, BuildSections.Section section, Identifier name) {
        if(!(world.getBlockEntity(section.block()) instanceof StructureBlockBlockEntity block))
            return false;
        block.setTemplateName(name);
        block.markDirty();
        world.updateListeners(section.block(), world.getBlockState(section.block()), world.getBlockState(section.block()), Block.NOTIFY_ALL);
        return true;
    }

    public static void remove(ServerWorld world, BuildSections.Section section) {
        world.removeBlockEntity(section.block());
        world.setBlockState(section.block(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
        StructureEditing.log("%s is no section any more, its structure block at %s taken away".formatted(section.name(), section.block().toShortString()));
    }

    // Skips other sections' blocks inside the box, like a build's open blocks
    public static @Nullable Identifier save(ServerWorld world, ServerPlayerEntity player, BuildSections.Section section) {
        Identifier id = Identifier.tryParse(section.name());
        BlockBox box = section.box();
        if(id == null || !StructureBlockBlockEntity.saveStructure(world, id, new BlockPos(box.getMinX(), box.getMinY(), box.getMinZ()),
                new Vec3i(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ()), false, player.getName().getString(), true, List.of(Blocks.STRUCTURE_BLOCK)))
            return null;
        StructureEditing.log("saved the section %s, %s".formatted(id, PieceLayout.describe(box)));
        return id;
    }

    private static @Nullable Identifier templateAt(ServerWorld world, ServerPlayerEntity player, List<BuildSections.Section> sections, BlockPos pos) {
        StructureEditSession.Piece piece = StructureEditSession.pieceAt(player.getUuid(), world.getRegistryKey(), pos);
        if(piece != null)
            return piece.template();
        BuildSections.Section section = BuildSections.at(sections, pos);
        return section == null ? null : Identifier.tryParse(section.name());
    }

    private static @Nullable BlockBox boxAt(ServerWorld world, ServerPlayerEntity player, List<BuildSections.Section> sections, BlockPos pos) {
        StructureEditSession.Piece piece = StructureEditSession.pieceAt(player.getUuid(), world.getRegistryKey(), pos);
        if(piece != null)
            return piece.box();
        BuildSections.Section section = BuildSections.at(sections, pos);
        return section == null ? null : section.box();
    }

    // Box between two corners becomes the piece's or section's bounds. With a name, a new section instead
    public static void setBounds(ServerWorld world, ServerPlayerEntity player, BlockPos anchor, BlockPos first, BlockPos second, String name) {
        if(!StructureEditing.isNear(world, second, first) || !StructureEditing.isNear(world, second, anchor)) {
            StructureEditing.log("bounds from %s to %s refused: %s or %s is far away or not loaded".formatted(first.toShortString(), second.toShortString(), first.toShortString(), anchor.toShortString()));
            return;
        }
        BlockBox box = BlockBox.create(first, second);
        int limit = StructureEditing.MAXIMUM_SIZE;
        if(!world.isInBuildLimit(first) || !world.isInBuildLimit(second) || !fits(box)) {
            StructureEditing.log("bounds from %s to %s refused: outside the world or over %d blocks a side".formatted(first.toShortString(), second.toShortString(), limit));
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.too_big".formatted(MiddleEarth.MOD_ID), limit), true);
            return;
        }
        if(!name.isBlank()) {
            create(world, player, name, anchor, first, second, box);
            return;
        }
        String size = sizeOf(box);
        StructureEditSession.Piece piece = StructureEditSession.pieceAt(player.getUuid(), world.getRegistryKey(), anchor);
        if(piece != null) {
            resizePiece(world, player, piece, box, size);
            return;
        }
        BuildSections.Section section = BuildSections.at(BuildSections.near(world, anchor), anchor);
        if(section == null || !resize(world, section, box)) {
            StructureEditing.log("bounds from %s to %s refused: the block at %s is in no build that is loaded".formatted(first.toShortString(), second.toShortString(), anchor.toShortString()));
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.no_build".formatted(MiddleEarth.MOD_ID)), true);
            return;
        }
        player.sendMessage(Text.translatable("ui.%s.structure_bounds.set".formatted(MiddleEarth.MOD_ID), BuildSections.pathOf(section.name()), size), true);
    }

    private static String sizeOf(BlockBox box) {
        return "%dx%dx%d".formatted(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
    }

    private static void resizePiece(ServerWorld world, ServerPlayerEntity player, StructureEditSession.Piece piece, BlockBox box, String size) {
        StructureEditSession session = StructureEditSession.ofIn(player.getUuid(), world.getRegistryKey());
        if(!session.isSpaced()) {
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.view_only".formatted(MiddleEarth.MOD_ID)), true);
            return;
        }
        StructureEditSession.Piece inTheWay = session.resize(world, piece, box);
        if(inTheWay != null)
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.in_the_way".formatted(MiddleEarth.MOD_ID), inTheWay.template().getPath()), true);
        else
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.set".formatted(MiddleEarth.MOD_ID), piece.template().getPath(), size), true);
    }

    // A new section for the box, which cuts up or overlaps the sections it lies in
    public static void create(ServerWorld world, ServerPlayerEntity player, String typed, BlockPos anchor, BlockPos first, BlockPos second, BlockBox box) {
        Identifier id = BuildSections.named(typed);
        if(id == null) {
            player.sendMessage(Text.translatable("ui.%s.build_section.bad_name".formatted(MiddleEarth.MOD_ID)), true);
            return;
        }
        List<BuildSections.Section> sections = BuildSections.near(world, anchor);
        if(isTaken(world, sections, id)) {
            StructureEditing.log("section %s not made: there is a template or a section of that name".formatted(id));
            player.sendMessage(Text.translatable("ui.%s.build_section.taken".formatted(MiddleEarth.MOD_ID), id.getPath()), true);
            return;
        }
        BlockPos at = blockFor(world, box, sections);
        if(!world.isInBuildLimit(at) || !place(world, player, id, box, at)) {
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.no_build".formatted(MiddleEarth.MOD_ID)), true);
            return;
        }
        WorldFreeze.beginSingle(world.getServer(), player.getUuid());
        String size = sizeOf(box);
        StructureEditing.log("new section %s, %s from %s to %s, structure block at %s".formatted(id, size, first.toShortString(), second.toShortString(), at.toShortString()));
        player.sendMessage(Text.translatable("ui.%s.build_section.made".formatted(MiddleEarth.MOD_ID), id.getPath(), size), true);
        List<BuildSections.Section> cutUp = cutUp(BuildSections.near(world, anchor));
        for(BuildSections.Section whole : cutUp) {
            remove(world, whole);
            player.sendMessage(Text.translatable("ui.%s.build_section.cut_up".formatted(MiddleEarth.MOD_ID), BuildSections.pathOf(whole.name())), false);
        }
        // The section this was cut from still saves this part until the rest of it is cut up too
        sections.stream().filter(section -> section.box().intersects(box) && !cutUp.contains(section)).findFirst().ifPresent(over ->
                player.sendMessage(Text.translatable("ui.%s.build_section.overlaps".formatted(MiddleEarth.MOD_ID), BuildSections.pathOf(over.name()), over.name()), false));
    }

    // The section whose structure block is at the position, taken away or given the typed name
    public static void edit(ServerWorld world, ServerPlayerEntity player, BlockPos block, boolean remove, String typed) {
        List<BuildSections.Section> sections = BuildSections.near(world, block);
        BuildSections.Section section = sections.stream().filter(found -> found.block().equals(block)).findFirst().orElse(null);
        if(section == null)
            return;
        if(remove) {
            remove(world, section);
            player.sendMessage(Text.translatable("ui.%s.build_section.removed".formatted(MiddleEarth.MOD_ID), section.name()), true);
            return;
        }
        Identifier id = BuildSections.named(typed);
        if(id == null) {
            player.sendMessage(Text.translatable("ui.%s.build_section.bad_name".formatted(MiddleEarth.MOD_ID)), true);
            return;
        }
        if(isTaken(world, sections, id)) {
            player.sendMessage(Text.translatable("ui.%s.build_section.taken".formatted(MiddleEarth.MOD_ID), id.getPath()), true);
            return;
        }
        if(!rename(world, section, id))
            return;
        StructureEditing.log("%s is now named %s".formatted(section.name(), id));
        player.sendMessage(Text.translatable("ui.%s.build_section.renamed".formatted(MiddleEarth.MOD_ID), section.name(), id.getPath()), true);
    }

    // The asker's piece asks for the answerer's. Two plugs tie as they are,
    // two plain blocks become plugs that revert on generation
    public static boolean join(ServerWorld world, ServerPlayerEntity player, BlockPos asker, BlockPos answerer) {
        List<BuildSections.Section> sections = BuildSections.near(world, asker);
        Identifier asking = templateAt(world, player, sections, asker);
        Identifier answering = templateAt(world, player, sections, answerer);
        BlockBox answeringBox = boxAt(world, player, sections, answerer);
        if(asking == null || answering == null || answeringBox == null)
            return refuse(player, "no_section", "the block at %s or %s is in no section or piece".formatted(asker.toShortString(), answerer.toShortString()));
        boolean plugs = world.getBlockState(asker).isOf(Blocks.JIGSAW) && world.getBlockState(answerer).isOf(Blocks.JIGSAW);
        if(!plugs) {
            Direction side = Direction.fromVector(answerer.getX() - asker.getX(), answerer.getY() - asker.getY(), answerer.getZ() - asker.getZ(), null);
            if(side == null || asking.equals(answering))
                return refuse(player, "apart", "%s and %s are not side by side in two sections".formatted(asker.toShortString(), answerer.toShortString()));
            if(holdsData(world, asker) || holdsData(world, answerer))
                return refuse(player, "holds_data", "a block at %s or %s holds data a plug cannot turn back into".formatted(asker.toShortString(), answerer.toShortString()));
            plug(world, asker, side);
            plug(world, answerer, side.getOpposite());
        }
        if(!(world.getBlockEntity(asker) instanceof JigsawBlockEntity from) || !(world.getBlockEntity(answerer) instanceof JigsawBlockEntity to))
            return refuse(player, "no_section", "no plug could be set at %s or %s".formatted(asker.toShortString(), answerer.toShortString()));

        // Both plugs: another piece the asker may get there, so it takes the asker's target name
        boolean another = plugs && !from.getPool().equals(StructurePools.EMPTY) && !from.getTarget().equals(PiecePools.EMPTY) && to.getName().equals(PiecePools.EMPTY);
        if(another)
            to.setName(from.getTarget());
        if(to.getName().equals(PiecePools.EMPTY))
            to.setName(freeName(world, answering, answeringBox, answerer));
        if(!to.getName().equals(from.getTarget()) || from.getPool().equals(StructurePools.EMPTY)) {
            StructurePool offered = world.getRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL).get(from.getPool());
            boolean inPool = offered != null && offered.getElementWeights().stream().anyMatch(element -> StructureAssembly.templatesOf(element.getFirst()).contains(answering));
            if(!inPool)
                from.setPool(RegistryKey.of(RegistryKeys.TEMPLATE_POOL, answering));
            from.setTarget(to.getName());
        }
        for(JigsawBlockEntity changed : List.of(from, to)) {
            changed.markDirty();
            world.updateListeners(changed.getPos(), changed.getCachedState(), changed.getCachedState(), Block.NOTIFY_ALL);
        }
        StructureEditing.log("%s at %s now asks for %s at %s: pool %s, target %s%s".formatted(asking, asker.toShortString(), answering, answerer.toShortString(),
                from.getPool().getValue(), to.getName(), plugs ? "" : ", both plugs new"));
        player.sendMessage(Text.translatable("ui.%s.section_join.%s".formatted(MiddleEarth.MOD_ID, another ? "another" : "made"), asking.getPath(), answering.getPath()), true);
        return true;
    }

    public static boolean unplug(ServerWorld world, ServerPlayerEntity player, BlockPos pos) {
        if(!(world.getBlockEntity(pos) instanceof JigsawBlockEntity plug))
            return false;
        Direction facing = world.getBlockState(pos).get(JigsawBlock.ORIENTATION).getFacing();
        BlockPos beside = pos.offset(facing);
        boolean pair = world.getBlockEntity(beside) instanceof JigsawBlockEntity other && world.getBlockState(beside).get(JigsawBlock.ORIENTATION).getFacing() == facing.getOpposite()
                && (asksFor(plug, other) || asksFor(other, plug));
        restore(world, pos);
        if(pair)
            restore(world, beside);
        StructureEditing.log("the plug at %s turned back into its block%s".formatted(pos.toShortString(), pair ? ", and the one it met at " + beside.toShortString() : ""));
        player.sendMessage(Text.translatable("ui.%s.section_join.undone".formatted(MiddleEarth.MOD_ID)), true);
        return true;
    }

    private static boolean asksFor(JigsawBlockEntity asker, JigsawBlockEntity answerer) {
        return !asker.getTarget().equals(PiecePools.EMPTY) && asker.getTarget().equals(answerer.getName());
    }

    private static void restore(ServerWorld world, BlockPos pos) {
        if(!(world.getBlockEntity(pos) instanceof JigsawBlockEntity plug))
            return;
        BlockState state = Blocks.AIR.getDefaultState();
        try {
            state = BlockArgumentParser.block(world.getRegistryManager().getOrThrow(RegistryKeys.BLOCK), plug.getFinalState(), true).blockState();
        } catch (CommandSyntaxException e) {
            StructureEditing.log("the plug at %s stands for %s, which is no block: air is set".formatted(pos.toShortString(), plug.getFinalState()));
        }
        world.removeBlockEntity(pos);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
    }

    // A section fully covered by smaller ones saves nothing extra
    public static List<BuildSections.Section> cutUp(List<BuildSections.Section> sections) {
        List<BuildSections.Section> covered = new ArrayList<>();
        for(BuildSections.Section section : sections) {
            List<BlockBox> others = sections.stream().filter(other -> other.volume() < section.volume() && other.box().intersects(section.box())).map(BuildSections.Section::box).toList();
            if(others.isEmpty())
                continue;
            BlockBox box = section.box();
            boolean all = true;
            for(BlockPos pos : BlockPos.iterate(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())) {
                if(others.stream().noneMatch(other -> other.contains(pos))) {
                    all = false;
                    break;
                }
            }
            if(all)
                covered.add(section);
        }
        return covered;
    }

    private static boolean refuse(ServerPlayerEntity player, String why, String logged) {
        StructureEditing.log("not joined: " + logged);
        player.sendMessage(Text.translatable("ui.%s.section_join.%s".formatted(MiddleEarth.MOD_ID, why)), true);
        return false;
    }

    private static boolean holdsData(ServerWorld world, BlockPos pos) {
        return !world.getBlockState(pos).isOf(Blocks.JIGSAW) && world.getBlockEntity(pos) != null;
    }

    private static void plug(ServerWorld world, BlockPos pos, Direction facing) {
        BlockState before = world.getBlockState(pos);
        if(before.isOf(Blocks.JIGSAW))
            return;
        // Plugs meeting up and down share a top direction, so the pieces join as built
        Direction top = facing.getAxis().isVertical() ? Direction.NORTH : Direction.UP;
        world.setBlockState(pos, Blocks.JIGSAW.getDefaultState().with(JigsawBlock.ORIENTATION, Orientation.byDirections(facing, top)), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        if(!(world.getBlockEntity(pos) instanceof JigsawBlockEntity jigsaw))
            return;
        jigsaw.setName(PiecePools.EMPTY);
        jigsaw.setTarget(PiecePools.EMPTY);
        jigsaw.setPool(StructurePools.EMPTY);
        jigsaw.setFinalState(before.isAir() || before.isOf(Blocks.STRUCTURE_VOID) ? AIR : BlockArgumentParser.stringifyBlockState(before));
        jigsaw.setJoint(facing.getAxis().isVertical() ? JigsawBlockEntity.Joint.ALIGNED : JigsawBlockEntity.Joint.ROLLABLE);
    }

    private static Identifier freeName(ServerWorld world, Identifier template, BlockBox box, BlockPos but) {
        Set<Identifier> taken = new HashSet<>();
        for(int x = box.getMinX() >> 4; x <= box.getMaxX() >> 4; x++) {
            for(int z = box.getMinZ() >> 4; z <= box.getMaxZ() >> 4; z++) {
                WorldChunk chunk = world.getChunkManager().getWorldChunk(x, z);
                if(chunk == null)
                    continue;
                for(BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                    if(blockEntity instanceof JigsawBlockEntity jigsaw && box.contains(jigsaw.getPos()) && !jigsaw.getPos().equals(but))
                        taken.add(jigsaw.getName());
                }
            }
        }
        Identifier name = template;
        for(int number = 2; taken.contains(name); number++)
            name = Identifier.of(template.getNamespace(), template.getPath() + "_" + number);
        return name;
    }
}
