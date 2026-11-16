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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// What is done to the sections of a build: made, given other bounds, taken away, saved, and joined to one another by plugs
public class BuildSectionEditing {
    private static final Identifier EMPTY = Identifier.ofVanilla("empty");
    private static final String AIR = "minecraft:air";

    // A name typed without a namespace is one of the mod's. Null for one that is no name at all
    public static @Nullable Identifier named(String typed) {
        String name = typed.trim();
        if(name.isEmpty())
            return null;
        return Identifier.tryParse(name.contains(":") ? name : MiddleEarth.MOD_ID + ":" + name);
    }

    public static boolean fits(BlockBox box) {
        return box.getBlockCountX() <= StructureEditing.MAXIMUM_SIZE && box.getBlockCountY() <= StructureEditing.MAXIMUM_SIZE && box.getBlockCountZ() <= StructureEditing.MAXIMUM_SIZE;
    }

    // Where the structure block of a section cut out of a build goes: the open block beside its box that is nearest its
    // first corner and in no section, for it to take the place of nothing that was built. Buried in a build, it goes under
    // the first corner as that of a new build does
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
        StructureEditing.log("%s given the bounds %s where it had %s, by its structure block at %s".formatted(section.name(), describe(box), describe(section.box()), section.block().toShortString()));
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

    // Only the box goes. What was built in it stands as it was, and a file it was saved to is kept
    public static void remove(ServerWorld world, BuildSections.Section section) {
        world.removeBlockEntity(section.block());
        world.setBlockState(section.block(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
        StructureEditing.log("%s is no section any more, its structure block at %s taken away".formatted(section.name(), section.block().toShortString()));
    }

    // As its structure block would save it, but for the structure blocks of other sections that stand in its box: they
    // are left out, as the open blocks of a build are
    public static @Nullable Identifier save(ServerWorld world, ServerPlayerEntity player, BuildSections.Section section) {
        Identifier id = Identifier.tryParse(section.name());
        BlockBox box = section.box();
        if(id == null || !StructureBlockBlockEntity.saveStructure(world, id, new BlockPos(box.getMinX(), box.getMinY(), box.getMinZ()),
                new Vec3i(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ()), false, player.getName().getString(), true, List.of(Blocks.STRUCTURE_BLOCK)))
            return null;
        StructureEditing.log("saved the section %s, %s".formatted(id, describe(box)));
        return id;
    }

    // The template a block would be saved to: that of the piece it is in, of a structure that is loaded, or of its section
    private static @Nullable Identifier templateAt(ServerWorld world, ServerPlayerEntity player, List<BuildSections.Section> sections, BlockPos pos) {
        StructureEditSession session = StructureEditSession.claim(player.getUuid());
        StructureEditSession.Piece piece = session == null || !session.getDimension().equals(world.getRegistryKey()) ? null : session.pieceContaining(pos);
        if(piece != null)
            return piece.template();
        BuildSections.Section section = BuildSections.at(sections, pos);
        return section == null ? null : Identifier.tryParse(section.name());
    }

    private static @Nullable BlockBox boxAt(ServerWorld world, ServerPlayerEntity player, List<BuildSections.Section> sections, BlockPos pos) {
        StructureEditSession session = StructureEditSession.of(player.getUuid());
        StructureEditSession.Piece piece = session == null || !session.getDimension().equals(world.getRegistryKey()) ? null : session.pieceContaining(pos);
        if(piece != null)
            return piece.box();
        BuildSections.Section section = BuildSections.at(sections, pos);
        return section == null ? null : section.box();
    }

    // Makes the piece of the first block ask for the piece of the second, there. Two plugs that are there already are
    // tied as they stand. Two blocks side by side that are no plugs yet are made into a pair of them, each to turn back
    // into the block it took the place of when the game generates the pieces. The answering plug is found by its name,
    // which no other plug of its piece may have, in a pool that is named after its piece
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

        if(to.getName().equals(EMPTY))
            to.setName(freeName(world, answering, answeringBox, answerer));
        StructurePool offered = world.getRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL).get(from.getPool());
        boolean inPool = offered != null && offered.getElementWeights().stream().anyMatch(element -> StructureEditSession.templatesOf(element.getFirst()).contains(answering));
        if(!inPool)
            from.setPool(RegistryKey.of(RegistryKeys.TEMPLATE_POOL, answering));
        from.setTarget(to.getName());
        for(JigsawBlockEntity changed : List.of(from, to)) {
            changed.markDirty();
            world.updateListeners(changed.getPos(), changed.getCachedState(), changed.getCachedState(), Block.NOTIFY_ALL);
        }
        StructureEditing.log("%s at %s now asks for %s at %s: pool %s, target %s%s".formatted(asking, asker.toShortString(), answering, answerer.toShortString(),
                from.getPool().getValue(), to.getName(), plugs ? "" : ", both plugs new"));
        player.sendMessage(Text.translatable("ui.%s.section_join.made".formatted(MiddleEarth.MOD_ID), asking.getPath(), answering.getPath()), true);
        return true;
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
        // Plugs that meet up and down have to point the same way round to be joined as they were built
        Direction top = facing.getAxis().isVertical() ? Direction.NORTH : Direction.UP;
        world.setBlockState(pos, Blocks.JIGSAW.getDefaultState().with(JigsawBlock.ORIENTATION, Orientation.byDirections(facing, top)), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        if(!(world.getBlockEntity(pos) instanceof JigsawBlockEntity jigsaw))
            return;
        jigsaw.setName(EMPTY);
        jigsaw.setTarget(EMPTY);
        jigsaw.setPool(StructurePools.EMPTY);
        jigsaw.setFinalState(before.isAir() || before.isOf(Blocks.STRUCTURE_VOID) ? AIR : BlockArgumentParser.stringifyBlockState(before));
        jigsaw.setJoint(facing.getAxis().isVertical() ? JigsawBlockEntity.Joint.ALIGNED : JigsawBlockEntity.Joint.ROLLABLE);
    }

    // Named after its piece, with a number where the piece has a plug of that name already
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

    private static String describe(BlockBox box) {
        return "%d,%d,%d to %d,%d,%d".formatted(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ());
    }
}
