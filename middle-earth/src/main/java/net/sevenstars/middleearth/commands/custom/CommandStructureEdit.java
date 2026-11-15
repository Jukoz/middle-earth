package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockBox;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.World;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.registry.Registry;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.enums.Orientation;
import net.minecraft.structure.pool.StructurePools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesRegistry;
import net.sevenstars.middleearth.MiddleEarth;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommandStructureEdit {
    private static final String COMMAND = "structedit";
    private static final String LOAD = "load";
    private static final String SAVE = "save";
    private static final String TEMPLATE = "template";
    private static final String STRUCTURE = "structure";
    private static final String SPACED = "spaced";
    private static final String ASSEMBLED = "assembled";
    private static final String SEED = "seed";
    private static final String VIEW = "view";
    private static final String CLOSE = "close";
    private static final String BEDS = "beds";
    private static final String NO_CLIP = "noclip";
    private static final String POOLS = "pools";
    private static final String NEW_STRUCTURE = "newstructure";
    private static final String NAME = "name";
    private static final String START_POOL_KEY = "start_pool";
    private static final String NEW = "new";
    private static final String PLUG = "plug";
    private static final String SIZE_X = "x";
    private static final String SIZE_Y = "y";
    private static final String SIZE_Z = "z";
    private static final Vec3i DEFAULT_NEW_SIZE = new Vec3i(16, 8, 16);
    private static final Identifier EMPTY_PLUG = Identifier.ofVanilla("empty");
    // What the plug turns into where the piece is generated
    private static final String PLUG_FINAL_STATE = "minecraft:air";
    // The most a structure block's own screen lets a box span
    private static final int MAXIMUM_NEW_SIZE = 48;
    private static final String PROJECTION = "projection";
    private static final String RIGID = "rigid";
    private static final String TERRAIN_MATCHING = "terrain_matching";
    private static final String POOL = "pool";
    private static final String TEST = "test";
    private static final String CLEAR = "clear";
    private static final String NEXT = "next";
    private static final String BACK = "back";
    // Looking down at the bed from where the builder is set beside it
    private static final float BED_VIEW_PITCH = 35;
    private static final String GAP = "gap";
    private static final String EXPAND = "expand";
    private static final String BLOCKS = "blocks";
    private static final int MAXIMUM_EXPANSION = 64;
    // In degrees up or down from level, past which the builder is taken to mean up or down
    private static final float LOOKING_UP = 45;
    // Blocks between the player and the nearest face of the loaded template
    private static final int DISTANCE_IN_FRONT = 3;
    // The structure block saves from one block above itself, as vanilla's default does
    private static final BlockPos SAVE_OFFSET = new BlockPos(0, 1, 0);
    private static final int NEAREST_SEARCH_RADIUS = 32;
    private static final String SOURCE_RESOURCES = "src/main/resources";

    private record Placed(RegistryKey<World> dimension, BlockPos saveBlock) {}

    private static final Map<UUID, Placed> PLACED = new HashMap<>();
    // The bed each builder was last taken to
    private static final Map<UUID, BlockPos> BED_VISITED = new HashMap<>();

    private static final SuggestionProvider<ServerCommandSource> TEMPLATES = (context, builder) ->
            CommandSource.suggestIdentifiers(context.getSource().getServer().getStructureTemplateManager().streamTemplates(), builder);

    private static final SuggestionProvider<ServerCommandSource> STRUCTURES = (context, builder) ->
            CommandSource.suggestIdentifiers(context.getSource().getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE).getIds(), builder);

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal(COMMAND)
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal(LOAD)
                        .then(CommandManager.argument(TEMPLATE, IdentifierArgumentType.identifier())
                                .suggests(TEMPLATES)
                                .executes(CommandStructureEdit::load)))
                .then(CommandManager.literal(STRUCTURE)
                        .then(CommandManager.argument(STRUCTURE, IdentifierArgumentType.identifier())
                                .suggests(STRUCTURES)
                                .executes(context -> loadStructure(context, true, null))
                                .then(CommandManager.literal(SPACED)
                                        .executes(context -> loadStructure(context, true, null))
                                        .then(CommandManager.argument(SEED, LongArgumentType.longArg())
                                                .executes(context -> loadStructure(context, true, LongArgumentType.getLong(context, SEED)))))
                                .then(CommandManager.literal(ASSEMBLED)
                                        .executes(context -> loadStructure(context, false, null))
                                        .then(CommandManager.argument(SEED, LongArgumentType.longArg())
                                                .executes(context -> loadStructure(context, false, LongArgumentType.getLong(context, SEED)))))))
                .then(CommandManager.literal(VIEW)
                        .then(CommandManager.literal(SPACED).executes(context -> view(context, true)))
                        .then(CommandManager.literal(ASSEMBLED).executes(context -> view(context, false))))
                .then(CommandManager.literal(CLOSE).executes(CommandStructureEdit::close))
                .then(CommandManager.literal(NO_CLIP).executes(CommandStructureEdit::noClip))
                .then(CommandManager.literal(POOLS)
                        .then(CommandManager.argument(TEMPLATE, IdentifierArgumentType.identifier())
                                .suggests(TEMPLATES)
                                .executes(CommandStructureEdit::pools)))
                .then(CommandManager.literal(NEW_STRUCTURE)
                        .then(CommandManager.argument(NAME, IdentifierArgumentType.identifier())
                                .then(CommandManager.argument(TEMPLATE, IdentifierArgumentType.identifier())
                                        .suggests(TEMPLATES)
                                        .then(CommandManager.argument(STRUCTURE, IdentifierArgumentType.identifier())
                                                .suggests(STRUCTURES)
                                                .executes(CommandStructureEdit::newStructure)))))
                .then(CommandManager.literal(NEW)
                        .then(CommandManager.argument(TEMPLATE, IdentifierArgumentType.identifier())
                                .executes(context -> create(context, DEFAULT_NEW_SIZE, false))
                                .then(CommandManager.literal(PLUG).executes(context -> create(context, DEFAULT_NEW_SIZE, true)))
                                .then(CommandManager.argument(SIZE_X, IntegerArgumentType.integer(1, MAXIMUM_NEW_SIZE))
                                        .then(CommandManager.argument(SIZE_Y, IntegerArgumentType.integer(1, MAXIMUM_NEW_SIZE))
                                                .then(CommandManager.argument(SIZE_Z, IntegerArgumentType.integer(1, MAXIMUM_NEW_SIZE))
                                                        .executes(context -> create(context, sizeOf(context), false))
                                                        .then(CommandManager.literal(PLUG).executes(context -> create(context, sizeOf(context), true))))))))
                .then(CommandManager.literal(PROJECTION)
                        .then(CommandManager.argument(TEMPLATE, IdentifierArgumentType.identifier())
                                .suggests(TEMPLATES)
                                .then(projection(RIGID, StructurePool.Projection.RIGID))
                                .then(projection(TERRAIN_MATCHING, StructurePool.Projection.TERRAIN_MATCHING))))
                .then(CommandManager.literal(TEST)
                        .then(CommandManager.literal(CLEAR).executes(CommandStructureEdit::clearTest))
                        .then(CommandManager.argument(STRUCTURE, IdentifierArgumentType.identifier())
                                .suggests(STRUCTURES)
                                .executes(context -> test(context, null))
                                .then(CommandManager.argument(SEED, LongArgumentType.longArg())
                                        .executes(context -> test(context, LongArgumentType.getLong(context, SEED))))))
                .then(CommandManager.literal(BEDS)
                        .executes(context -> beds(context, 0))
                        .then(CommandManager.literal(NEXT).executes(context -> beds(context, 1)))
                        .then(CommandManager.literal(BACK).executes(context -> beds(context, -1))))
                .then(CommandManager.literal(GAP)
                        .then(CommandManager.argument(BLOCKS, IntegerArgumentType.integer(0, StructureEditSession.MAXIMUM_GAP))
                                .executes(CommandStructureEdit::gap)))
                .then(CommandManager.literal(EXPAND)
                        .then(CommandManager.argument(BLOCKS, IntegerArgumentType.integer(1, MAXIMUM_EXPANSION))
                                .executes(context -> expand(context, null))
                                .then(CommandManager.literal("up").executes(context -> expand(context, Direction.UP)))
                                .then(CommandManager.literal("down").executes(context -> expand(context, Direction.DOWN)))
                                .then(CommandManager.literal("north").executes(context -> expand(context, Direction.NORTH)))
                                .then(CommandManager.literal("south").executes(context -> expand(context, Direction.SOUTH)))
                                .then(CommandManager.literal("east").executes(context -> expand(context, Direction.EAST)))
                                .then(CommandManager.literal("west").executes(context -> expand(context, Direction.WEST)))))
                .then(CommandManager.literal(SAVE)
                        .executes(context -> save(context, null))
                        .then(CommandManager.argument(TEMPLATE, IdentifierArgumentType.identifier())
                                .suggests(TEMPLATES)
                                .executes(context -> save(context, identifier(context, TEMPLATE))))));
    }

    // The whole structure, put together from its pieces as the game would from that seed. With no seed, one is rolled and
    // told, to come back to the same pieces later
    private static int loadStructure(CommandContext<ServerCommandSource> context, boolean spaced, @Nullable Long seed) throws CommandSyntaxException {
        Identifier id = identifier(context, STRUCTURE);
        return loadStructure(context.getSource(), id, spaced, seed != null ? seed : context.getSource().getWorld().getRandom().nextLong());
    }

    private static int loadStructure(ServerCommandSource source, Identifier id, boolean spaced, long seed) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayerOrThrow();
        ServerWorld world = source.getWorld();
        RegistryEntry<Structure> structure = world.getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE).getEntry(id).orElse(null);
        if(structure == null) {
            source.sendError(Text.literal("No structure " + id));
            return 0;
        }
        StructureEditSession before = StructureEditSession.claim(player.getUuid());
        if(before != null && before.hasChanges(world)) {
            source.sendError(Text.literal("The pieces of %s have changes that are not saved. /%s %s them or /%s %s first".formatted(before.getStructure(), COMMAND, SAVE, COMMAND, CLOSE)));
            return 0;
        }
        Direction facing = player.getHorizontalFacing();
        BlockPos corner = player.getBlockPos().offset(facing, DISTANCE_IN_FRONT);
        StructureEditSession session = StructureEditSession.load(world, player.getUuid(), structure, id, seed, spaced, corner);
        if(session == null) {
            source.sendError(Text.literal("Could not put %s together from the seed %d. Another seed may do, and only structures made of jigsaw pieces can be loaded".formatted(id, seed)));
            return 0;
        }
        source.sendFeedback(() -> Text.literal("Loaded %s from the seed %d: %d pieces, %s. %s".formatted(id, seed, session.getPieces().size(),
                spaced ? "spaced %d blocks apart".formatted(session.getGap()) : "assembled", spaced ? "The world stands still until /%s %s. /%s %s writes the pieces that were changed".formatted(COMMAND, CLOSE, COMMAND, SAVE)
                        : "This view is for looking: /%s %s %s to change and save pieces".formatted(COMMAND, VIEW, SPACED))), false);
        return 1;
    }

    // The same structure of the same seed, the other way
    private static int view(CommandContext<ServerCommandSource> context, boolean spaced) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        StructureEditSession session = StructureEditSession.claim(source.getPlayerOrThrow().getUuid());
        if(session == null) {
            source.sendError(Text.literal("No structure is loaded. /%s %s <structure> loads one".formatted(COMMAND, STRUCTURE)));
            return 0;
        }
        return loadStructure(source, session.getStructure(), spaced, session.getSeed());
    }

    // How far apart spaced pieces stand. A structure that is loaded is laid out again with it
    private static int gap(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        int blocks = IntegerArgumentType.getInteger(context, BLOCKS);
        StructureEditSession.setGap(player.getUuid(), blocks);
        StructureEditSession session = StructureEditSession.claim(player.getUuid());
        if(session == null || !session.isSpaced()) {
            source.sendFeedback(() -> Text.literal("Spaced pieces will stand %d blocks apart".formatted(blocks)), false);
            return 1;
        }
        return loadStructure(source, session.getStructure(), true, session.getSeed());
    }

    // Makes the piece the builder stands in, or the nearest, bigger on the side they look to or name
    private static int expand(CommandContext<ServerCommandSource> context, @Nullable Direction named) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        StructureEditSession session = StructureEditSession.claim(player.getUuid());
        if(session == null || !session.isSpaced() || !session.getDimension().equals(source.getWorld().getRegistryKey())) {
            source.sendError(Text.literal("Only the pieces of a structure loaded spaced apart can be made bigger. /%s %s <structure> loads one".formatted(COMMAND, STRUCTURE)));
            return 0;
        }
        StructureEditSession.Piece piece = session.pieceAt(player.getBlockPos());
        if(piece == null)
            return 0;
        Direction side = named != null ? named : player.getPitch() < -LOOKING_UP ? Direction.UP : player.getPitch() > LOOKING_UP ? Direction.DOWN : player.getHorizontalFacing();
        int blocks = IntegerArgumentType.getInteger(context, BLOCKS);
        StructureEditSession.Piece inTheWay = session.expand(source.getWorld(), piece, side, blocks);
        if(inTheWay != null) {
            source.sendError(Text.literal("%s is in the way of %s to the %s. /%s %s <blocks> sets the pieces further apart".formatted(inTheWay.template(), piece.template(), side.asString(), COMMAND, GAP)));
            return 0;
        }
        source.sendFeedback(() -> Text.literal("Made %s %d blocks bigger to the %s. /%s %s writes it".formatted(piece.template(), blocks, side.asString(), COMMAND, SAVE)), false);
        return 1;
    }

    // A real one, to see what was saved come alive: built from the templates as they are now, but tagged beds and markers
    // only count once datagen has read them
    private static int test(CommandContext<ServerCommandSource> context, @Nullable Long given) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        ServerWorld world = source.getWorld();
        Identifier id = identifier(context, STRUCTURE);
        RegistryEntry<Structure> structure = world.getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE).getEntry(id).orElse(null);
        boolean piece = structure == null && world.getStructureTemplateManager().getTemplate(id).isPresent();
        if(structure == null && !piece) {
            source.sendError(Text.literal("No structure and no template " + id));
            return 0;
        }
        if(world.getServer().getTickManager().isFrozen()) {
            source.sendError(Text.literal("The world stands still, and nobody would be born. /%s %s first".formatted(COMMAND, CLOSE)));
            return 0;
        }
        if(piece) {
            RegistryEntry<Structure> carrier = carrierFor(world, id);
            Direction facing = player.getHorizontalFacing();
            StructureEditSession.Generated set = carrier == null ? null : StructureEditSession.generatePiece(world, player.getUuid(), id, carrier,
                    player.getBlockPos().offset(facing, DISTANCE_IN_FRONT));
            if(set == null) {
                source.sendError(Text.literal("Could not set down the piece " + id));
                return 0;
            }
            source.sendFeedback(() -> Text.literal("Set down the piece %s by itself from %d, %d, %d. Its people are born at its beds. /%s %s %s takes it away again".formatted(id,
                    set.box().getMinX(), set.box().getMinY(), set.box().getMinZ(), COMMAND, TEST, CLEAR)), false);
            return 1;
        }
        long seed = given != null ? given : world.getRandom().nextLong();
        StructureEditSession.Generated generated = StructureEditSession.generate(world, player.getUuid(), structure, seed, player.getBlockPos());
        if(generated == null) {
            source.sendError(Text.literal("The game put no pieces of %s together from the seed %d here".formatted(id, seed)));
            return 0;
        }
        BlockBox box = generated.box();
        source.sendFeedback(() -> Text.literal("Generated %s from the seed %d: %d pieces from %d, %d, %d to %d, %d, %d. Its people are born at its beds. /%s %s %s takes it away again".formatted(id, seed,
                generated.pieces(), box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ(), COMMAND, TEST, CLEAR)), false);
        return 1;
    }

    // A piece set down by itself is kept by the world under the name of a structure it knows: one named after the folder
    // the piece is in where there is one, so that it is of the same people, or else the first there is
    private static @Nullable RegistryEntry<Structure> carrierFor(ServerWorld world, Identifier template) {
        Registry<Structure> structures = world.getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE);
        String folder = template.getPath().split("/")[0];
        RegistryEntry<Structure> first = null;
        for(Identifier id : structures.getIds().stream().sorted().toList()) {
            if(!id.getNamespace().equals(template.getNamespace()))
                continue;
            RegistryEntry<Structure> entry = structures.getEntry(id).orElse(null);
            if(first == null)
                first = entry;
            if(entry != null && id.getPath().startsWith(folder))
                return entry;
        }
        return first;
    }

    // Those the builder generated since the game was started, or else the one stood in
    private static int clearTest(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        int cleared = StructureEditSession.clearTests(source.getWorld(), player.getUuid());
        if(cleared == 0 && StructureEditSession.clearTestAt(source.getWorld(), player.getBlockPos()))
            cleared = 1;
        if(cleared == 0) {
            source.sendError(Text.literal("Nothing to take away: stand inside the generated structure and try again"));
            return 0;
        }
        int count = cleared;
        source.sendFeedback(() -> Text.literal("Took away %d generated structure%s and whoever lived there".formatted(count, count == 1 ? "" : "s")), false);
        return 1;
    }

    private static final SuggestionProvider<ServerCommandSource> POOL_NAMES = (context, builder) ->
            CommandSource.suggestIdentifiers(context.getSource().getRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL).getIds(), builder);

    private static LiteralArgumentBuilder<ServerCommandSource> projection(String name, StructurePool.Projection projection) {
        return CommandManager.literal(name)
                .executes(context -> setProjection(context, projection, null))
                .then(CommandManager.argument(POOL, IdentifierArgumentType.identifier())
                        .suggests(POOL_NAMES)
                        .executes(context -> setProjection(context, projection, identifier(context, POOL))));
    }

    // How a piece meets the ground is said by the pools that offer it. Changed in those the game has loaded, for what is
    // generated from now on, and in the mod's own files where the game runs from them, for every world opened after
    private static int setProjection(CommandContext<ServerCommandSource> context, StructurePool.Projection projection, @Nullable Identifier pool) {
        ServerCommandSource source = context.getSource();
        Identifier template = identifier(context, TEMPLATE);
        Registry<StructurePool> pools = source.getRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL);
        if(!PieceProjections.of(pools).containsKey(template)) {
            source.sendError(Text.literal("No pool offers the template " + template));
            return 0;
        }
        List<Identifier> changed = PieceProjections.set(pools, template, projection, pool);
        String how = projection == StructurePool.Projection.TERRAIN_MATCHING ? "follows the ground" : "is set as it was built";
        if(changed.isEmpty()) {
            source.sendFeedback(() -> Text.literal("%s already %s in %s".formatted(template, how, pool == null ? "every pool that offers it" : pool.toString())), false);
            return 1;
        }
        StructureEditing.log("%s now %s in the pools %s".formatted(template, how, changed));
        source.sendFeedback(() -> Text.literal("%s now %s in %s, for what is generated from now on".formatted(template, how, changed)), false);
        Path resources = FabricLoader.getInstance().getGameDir().resolve("..").resolve(SOURCE_RESOURCES).normalize();
        if(!FabricLoader.getInstance().isDevelopmentEnvironment() || !isOwnSources(resources)) {
            source.sendFeedback(() -> Text.literal("Not written to any file: it lasts until this world is closed"), false);
            return 1;
        }
        try {
            List<Path> written = PieceProjections.write(resources, template, projection, pool);
            StructureEditing.log("  written to %s".formatted(written));
            source.sendFeedback(() -> Text.literal("Written to %d pool file%s of the mod".formatted(written.size(), written.size() == 1 ? "" : "s")), false);
        } catch (IOException e) {
            source.sendError(Text.literal("Could not write the pool files: " + e.getMessage()));
        }
        return 1;
    }

    private static Vec3i sizeOf(CommandContext<ServerCommandSource> context) {
        return new Vec3i(IntegerArgumentType.getInteger(context, SIZE_X), IntegerArgumentType.getInteger(context, SIZE_Y), IntegerArgumentType.getInteger(context, SIZE_Z));
    }

    // A build that does not exist yet: a structure block set to save a box of that size under that name, in front of the
    // builder, with whatever stands there left as it is. With a plug, a jigsaw block of the build's own name is set in
    // the middle of the side the builder stands at, for another piece's plug to take it by
    private static int create(CommandContext<ServerCommandSource> context, Vec3i size, boolean plug) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        ServerWorld world = source.getWorld();
        Identifier id = identifier(context, TEMPLATE);
        if(world.getStructureTemplateManager().getTemplate(id).isPresent()) {
            source.sendError(Text.literal("There is a template %s already. /%s %s %s to change it".formatted(id, COMMAND, LOAD, id)));
            return 0;
        }
        Direction facing = player.getHorizontalFacing();
        BlockPos feet = player.getBlockPos();
        int x = facing.getOffsetX() > 0 ? feet.getX() + DISTANCE_IN_FRONT
                : facing.getOffsetX() < 0 ? feet.getX() - DISTANCE_IN_FRONT - size.getX() + 1 : feet.getX() - size.getX() / 2;
        int z = facing.getOffsetZ() > 0 ? feet.getZ() + DISTANCE_IN_FRONT
                : facing.getOffsetZ() < 0 ? feet.getZ() - DISTANCE_IN_FRONT - size.getZ() + 1 : feet.getZ() - size.getZ() / 2;
        BlockPos origin = new BlockPos(x, feet.getY(), z);
        BlockPos saveBlock = origin.subtract(SAVE_OFFSET);
        if(!world.isInBuildLimit(saveBlock) || !world.isInBuildLimit(origin.add(size).add(-1, -1, -1))) {
            source.sendError(Text.literal("A box of that size does not fit in the world here"));
            return 0;
        }
        world.setBlockState(saveBlock, Blocks.STRUCTURE_BLOCK.getDefaultState(), Block.NOTIFY_ALL);
        if(!(world.getBlockEntity(saveBlock) instanceof StructureBlockBlockEntity structureBlock)) {
            source.sendError(Text.literal("Could not place the structure block at " + saveBlock.toShortString()));
            return 0;
        }
        structureBlock.setMode(StructureBlockMode.SAVE);
        structureBlock.setTemplateName(id);
        structureBlock.setOffset(SAVE_OFFSET);
        structureBlock.setSize(size);
        structureBlock.setIgnoreEntities(true);
        structureBlock.setAuthor(player);
        structureBlock.markDirty();
        world.updateListeners(saveBlock, world.getBlockState(saveBlock), world.getBlockState(saveBlock), Block.NOTIFY_ALL);

        BlockPos plugPos = null;
        if(plug) {
            // On the floor of the box, in the middle of the side that faces the builder, and facing out of it
            Direction out = facing.getOpposite();
            int along = (out.getAxis() == Direction.Axis.X ? size.getZ() : size.getX()) / 2;
            plugPos = out.getAxis() == Direction.Axis.X
                    ? new BlockPos(out == Direction.WEST ? origin.getX() : origin.getX() + size.getX() - 1, origin.getY(), origin.getZ() + along)
                    : new BlockPos(origin.getX() + along, origin.getY(), out == Direction.NORTH ? origin.getZ() : origin.getZ() + size.getZ() - 1);
            world.setBlockState(plugPos, Blocks.JIGSAW.getDefaultState().with(JigsawBlock.ORIENTATION, Orientation.byDirections(out, Direction.UP)), Block.NOTIFY_ALL);
            if(world.getBlockEntity(plugPos) instanceof JigsawBlockEntity jigsaw) {
                jigsaw.setName(id);
                jigsaw.setTarget(EMPTY_PLUG);
                jigsaw.setPool(StructurePools.EMPTY);
                jigsaw.setFinalState(PLUG_FINAL_STATE);
                jigsaw.setJoint(JigsawBlockEntity.Joint.ROLLABLE);
                jigsaw.markDirty();
                world.updateListeners(plugPos, world.getBlockState(plugPos), world.getBlockState(plugPos), Block.NOTIFY_ALL);
            }
        }
        PLACED.put(player.getUuid(), new Placed(world.getRegistryKey(), saveBlock));
        StructureEditSession.beginSingle(world.getServer(), player.getUuid());
        BlockPos placedPlug = plugPos;
        StructureEditing.log("new build %s, %dx%dx%d from %s, structure block at %s%s".formatted(id, size.getX(), size.getY(), size.getZ(), origin.toShortString(),
                saveBlock.toShortString(), placedPlug == null ? "" : ", plug at " + placedPlug.toShortString()));
        source.sendFeedback(() -> Text.literal("New build %s: a box of %dx%dx%d from %s, its structure block below the first corner at %s%s. /%s %s writes it".formatted(id,
                size.getX(), size.getY(), size.getZ(), origin.toShortString(), saveBlock.toShortString(),
                placedPlug == null ? "" : ", and a plug named %s at %s".formatted(id, placedPlug.toShortString()), COMMAND, SAVE)), false);
        return 1;
    }

    // A name typed without a namespace is one of the mod's, not one of the game's
    private static Identifier identifier(CommandContext<ServerCommandSource> context, String argument) {
        Identifier id = IdentifierArgumentType.getIdentifier(context, argument);
        for(ParsedCommandNode<ServerCommandSource> node : context.getNodes()) {
            if(node.getNode().getName().equals(argument) && !node.getRange().get(context.getInput()).contains(":"))
                return Identifier.of(MiddleEarth.MOD_ID, id.getPath());
        }
        return id;
    }

    // Another project that runs the mod from its jar has sources of its own beside the game, which are not the mod's
    private static boolean isOwnSources(Path resources) {
        return Files.isDirectory(resources.resolve("data").resolve(MiddleEarth.MOD_ID).resolve("structure"));
    }

    private static @Nullable Path sources(ServerCommandSource source) {
        Path resources = FabricLoader.getInstance().getGameDir().resolve("..").resolve(SOURCE_RESOURCES).normalize();
        if(FabricLoader.getInstance().isDevelopmentEnvironment() && isOwnSources(resources))
            return resources;
        source.sendError(Text.literal("Pool and structure files are written to the mod's own sources, which this game does not run from"));
        return null;
    }

    // Puts a saved piece into the pools its plugs say it belongs in, and makes the pools its own plugs ask for
    private static int pools(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Identifier template = identifier(context, TEMPLATE);
        if(source.getWorld().getStructureTemplateManager().getTemplate(template).isEmpty()) {
            source.sendError(Text.literal("No template " + template));
            return 0;
        }
        Path resources = sources(source);
        if(resources == null)
            return 0;
        try {
            PiecePools.Result result = PiecePools.publish(source.getWorld(), template, resources);
            StructureEditing.log("pools of %s: put in %s of the open world, wrote %s, new %s".formatted(template, result.live(), result.written(), result.created()));
            if(result.live().isEmpty() && result.written().isEmpty()) {
                source.sendFeedback(() -> Text.literal("%s is already in every pool its plugs fit, or no plug asks for it by name".formatted(template)), false);
                return 1;
            }
            source.sendFeedback(() -> Text.literal("%s: wrote %d pool file%s. In the open world it is now in %s".formatted(template, result.written().size(),
                    result.written().size() == 1 ? "" : "s", result.live().isEmpty() ? "no pool yet" : result.live().toString())), false);
            if(!result.created().isEmpty())
                source.sendFeedback(() -> Text.literal("New pools %s are only seen once the world is opened again".formatted(result.created())), false);
            return 1;
        } catch (IOException e) {
            source.sendError(Text.literal("Could not write the pool files: " + e.getMessage()));
            return 0;
        }
    }

    // A structure of its own for a piece to start from: the settings of one that is like it, with a pool of that piece
    // to start with. Where it generates is as the one it was copied from, until its file says otherwise
    private static int newStructure(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Identifier name = identifier(context, NAME);
        Identifier template = identifier(context, TEMPLATE);
        Identifier like = identifier(context, STRUCTURE);
        Path resources = sources(source);
        if(resources == null)
            return 0;
        Path folder = resources.resolve("data").resolve(name.getNamespace()).resolve("worldgen").resolve("structure");
        Path file = folder.resolve(name.getPath() + ".json");
        Path copied = resources.resolve("data").resolve(like.getNamespace()).resolve("worldgen").resolve("structure").resolve(like.getPath() + ".json");
        if(Files.exists(file) || !Files.isRegularFile(copied) || source.getWorld().getStructureTemplateManager().getTemplate(template).isEmpty()) {
            source.sendError(Text.literal(Files.exists(file) ? "There is a structure %s already".formatted(name) : !Files.isRegularFile(copied)
                    ? "No structure file of %s to copy the settings from".formatted(like) : "No template " + template));
            return 0;
        }
        try {
            JsonObject settings = new Gson().fromJson(Files.readString(copied), JsonObject.class);
            settings.addProperty(START_POOL_KEY, template.toString());
            Files.createDirectories(file.getParent());
            Files.writeString(file, new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(settings));
            PiecePools.writeStart(resources, template, template);
            PiecePools.Result result = PiecePools.publish(source.getWorld(), template, resources);
            StructureEditing.log("new structure %s starting from %s, settings of %s, wrote %s and pools %s".formatted(name, template, like, file, result.written()));
            source.sendFeedback(() -> Text.literal("Wrote the structure %s, starting from %s with the settings of %s, and %d pool file%s. Leave the world and open it again, then /%s %s %s"
                    .formatted(name, template, like, result.written().size() + 1, result.written().isEmpty() ? "" : "s", COMMAND, TEST, name)), false);
            return 1;
        } catch (IOException | JsonParseException e) {
            source.sendError(Text.literal("Could not write the structure: " + e.getMessage()));
            return 0;
        }
    }

    private static int noClip(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        if(!player.isCreative()) {
            source.sendError(Text.literal("Passing through blocks is for creative mode"));
            return 0;
        }
        boolean on = StructureEditing.toggleNoClip(player);
        if(on) {
            player.getAbilities().flying = true;
            player.sendAbilitiesUpdate();
        }
        source.sendFeedback(() -> Text.literal(on ? "You pass through blocks now, flying. /%s %s again to stop".formatted(COMMAND, NO_CLIP) : "Blocks are solid to you again"), false);
        return 1;
    }

    private record FoundBed(BlockPos pos, String build) {}

    // Every bed of what the builder has loaded, piece after piece, in an order that stays the same between calls
    private static List<FoundBed> bedsOf(ServerWorld world, ServerPlayerEntity player) {
        Map<BlockBox, String> builds = new LinkedHashMap<>();
        StructureEditSession session = StructureEditSession.claim(player.getUuid());
        if(session != null && session.getDimension().equals(world.getRegistryKey())) {
            for(StructureEditSession.Piece piece : session.getPieces())
                builds.put(piece.box(), piece.template().getPath());
        } else {
            StructureBlockBlockEntity block = findStructureBlock(world, player);
            if(block != null) {
                BlockPos from = block.getPos().add(block.getOffset());
                builds.put(BlockBox.create(from, from.add(block.getSize()).add(-1, -1, -1)), block.getTemplateName());
            }
        }
        List<FoundBed> beds = new ArrayList<>();
        for(Map.Entry<BlockBox, String> build : builds.entrySet()) {
            BlockBox box = build.getKey();
            List<BlockPos> found = new ArrayList<>();
            for(int x = box.getMinX() >> 4; x <= box.getMaxX() >> 4; x++) {
                for(int z = box.getMinZ() >> 4; z <= box.getMaxZ() >> 4; z++) {
                    if(!world.isChunkLoaded(x, z))
                        continue;
                    for(BlockPos pos : world.getChunk(x, z).getBlockEntityPositions()) {
                        BlockState state = world.getBlockState(pos);
                        if(box.contains(pos) && state.getBlock() instanceof BedBlock && state.get(BedBlock.PART) == BedPart.HEAD)
                            found.add(pos.toImmutable());
                    }
                }
            }
            found.sort(Comparator.naturalOrder());
            found.forEach(pos -> beds.add(new FoundBed(pos, build.getValue())));
        }
        return beds;
    }

    // Takes the builder to the first bed, or from the one last visited to the one after or before it
    private static int beds(CommandContext<ServerCommandSource> context, int step) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        ServerWorld world = source.getWorld();
        List<FoundBed> beds = bedsOf(world, player);
        if(beds.isEmpty()) {
            source.sendError(Text.literal("No beds: nothing is loaded here, or what is loaded has none"));
            return 0;
        }
        int index = 0;
        if(step != 0) {
            BlockPos visited = BED_VISITED.get(player.getUuid());
            int last = -1;
            for(int i = 0; i < beds.size(); i++) {
                if(beds.get(i).pos().equals(visited))
                    last = i;
            }
            index = last < 0 ? 0 : Math.floorMod(last + step, beds.size());
        }
        FoundBed bed = beds.get(index);
        BED_VISITED.put(player.getUuid(), bed.pos());
        BlockPos stand = NestSurvey.standFor(world, bed.pos());
        if(stand == null)
            stand = bed.pos().up();
        Vec3d from = Vec3d.ofBottomCenter(stand);
        Vec3d to = Vec3d.ofCenter(bed.pos());
        float yaw = stand.getX() == bed.pos().getX() && stand.getZ() == bed.pos().getZ() ? player.getYaw()
                : (float) Math.toDegrees(Math.atan2(to.z - from.z, to.x - from.x)) - 90;
        player.teleport(world, from.x, from.y, from.z, Set.of(), yaw, BED_VIEW_PITCH, false);

        long untagged = beds.stream().filter(found -> BedTagHolder.tagAt(world, found.pos()).isEmpty()).count();
        String who = BedTagHolder.tagAt(world, bed.pos()).map(tag -> "%s%s%s".formatted(tag.nestKind(),
                tag.place().map(place -> " of " + place.getPath()).orElse(""), tag.job().map(job -> ", job " + job.getPath()).orElse(""))).orElse("not tagged");
        int number = index + 1;
        source.sendFeedback(() -> Text.literal("Bed %d of %d, in %s: %s. %d beds are not tagged. /%s %s %s and %s to go on".formatted(number, beds.size(), bed.build(), who,
                untagged, COMMAND, BEDS, NEXT, BACK)), false);
        return 1;
    }

    private static int close(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        boolean single = StructureEditSession.endSingle(source.getServer(), source.getPlayerOrThrow().getUuid());
        int tests = StructureEditSession.clearTests(source.getWorld(), source.getPlayerOrThrow().getUuid());
        if(!StructureEditSession.close(source.getWorld(), source.getPlayerOrThrow().getUuid())) {
            if(tests > 0) {
                source.sendFeedback(() -> Text.literal("Took away %d generated structure%s and whoever lived there".formatted(tests, tests == 1 ? "" : "s")), false);
                return 1;
            }
            // A template loaded by itself stays where it is: only the world is set running again
            if(single) {
                source.sendFeedback(() -> Text.literal("The world runs again. The template stays where it was loaded"), false);
                return 1;
            }
            source.sendError(Text.literal("No structure is loaded in this dimension"));
            return 0;
        }
        source.sendFeedback(() -> Text.literal("Took the pieces out of the world. Changes that were not saved are gone"), false);
        return 1;
    }

    private static int saveStructure(ServerCommandSource source, ServerWorld world, StructureEditSession session) {
        if(!session.isSpaced()) {
            source.sendError(Text.literal("An assembled structure is for looking. /%s %s %s to change and save its pieces".formatted(COMMAND, VIEW, SPACED)));
            return 0;
        }
        StructureEditSession.Saved saved = session.save(world);
        for(Identifier conflict : saved.conflicts())
            source.sendError(Text.literal("Several pieces of " + conflict + " were changed in different ways. Make them alike, or change only one, and save again"));
        if(saved.written().isEmpty() && saved.conflicts().isEmpty())
            source.sendFeedback(() -> Text.literal("No piece was changed since it was loaded or saved"), false);
        for(Identifier id : saved.written()) {
            Path file = world.getStructureTemplateManager().getTemplatePath(id, ".nbt");
            source.sendFeedback(() -> Text.literal("Saved " + id + " to " + file), false);
            copyToSources(source, id, file);
        }
        return saved.written().size();
    }

    private static int load(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        ServerWorld world = source.getWorld();
        Identifier id = identifier(context, TEMPLATE);

        StructureTemplateManager manager = world.getStructureTemplateManager();
        // Read it again, in case the file was changed since it was loaded
        manager.unloadTemplate(id);
        StructureTemplate template = manager.getTemplate(id).orElse(null);
        if(template == null) {
            source.sendError(Text.literal("No structure template " + id));
            return 0;
        }

        Vec3i size = template.getSize();
        Direction facing = player.getHorizontalFacing();
        BlockPos feet = player.getBlockPos();
        int x = facing.getOffsetX() > 0 ? feet.getX() + DISTANCE_IN_FRONT
                : facing.getOffsetX() < 0 ? feet.getX() - DISTANCE_IN_FRONT - size.getX() + 1 : feet.getX() - size.getX() / 2;
        int z = facing.getOffsetZ() > 0 ? feet.getZ() + DISTANCE_IN_FRONT
                : facing.getOffsetZ() < 0 ? feet.getZ() - DISTANCE_IN_FRONT - size.getZ() + 1 : feet.getZ() - size.getZ() / 2;
        BlockPos origin = new BlockPos(x, feet.getY(), z);

        StructurePlacementData placement = new StructurePlacementData().setIgnoreEntities(true);
        template.place(world, origin, origin, placement, world.getRandom(), Block.NOTIFY_LISTENERS);

        BlockPos saveBlock = origin.subtract(SAVE_OFFSET);
        world.setBlockState(saveBlock, Blocks.STRUCTURE_BLOCK.getDefaultState(), Block.NOTIFY_ALL);
        if(!(world.getBlockEntity(saveBlock) instanceof StructureBlockBlockEntity structureBlock)) {
            source.sendError(Text.literal("Could not place the structure block at " + saveBlock.toShortString()));
            return 0;
        }
        structureBlock.setMode(StructureBlockMode.SAVE);
        structureBlock.setTemplateName(id);
        structureBlock.setOffset(SAVE_OFFSET);
        structureBlock.setSize(size);
        structureBlock.setIgnoreEntities(true);
        structureBlock.setAuthor(player);
        structureBlock.markDirty();
        world.updateListeners(saveBlock, world.getBlockState(saveBlock), world.getBlockState(saveBlock), Block.NOTIFY_ALL);

        PLACED.put(player.getUuid(), new Placed(world.getRegistryKey(), saveBlock));
        StructureEditSession.beginSingle(world.getServer(), player.getUuid());
        source.sendFeedback(() -> Text.literal("Loaded %s (%dx%dx%d) from %s. Its structure block is at %s, below the first corner."
                .formatted(id, size.getX(), size.getY(), size.getZ(), origin.toShortString(), saveBlock.toShortString())), false);
        return 1;
    }

    private static int save(CommandContext<ServerCommandSource> context, Identifier override) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        ServerWorld world = source.getWorld();

        StructureEditSession session = StructureEditSession.claim(player.getUuid());
        if(override == null && session != null && session.getDimension().equals(world.getRegistryKey()))
            return saveStructure(source, world, session);

        StructureBlockBlockEntity structureBlock = findStructureBlock(world, player);
        if(structureBlock == null) {
            source.sendError(Text.literal("No structure block in save mode was placed by /%s load or is within %d blocks".formatted(COMMAND, NEAREST_SEARCH_RADIUS)));
            return 0;
        }
        if(override != null)
            structureBlock.setTemplateName(override);
        String name = structureBlock.getTemplateName();
        Identifier id = Identifier.tryParse(name);
        if(id == null) {
            source.sendError(Text.literal("The structure block has no valid template name"));
            return 0;
        }

        if(!structureBlock.saveStructure(true)) {
            source.sendError(Text.literal("Saving " + id + " failed, see the log"));
            return 0;
        }
        StructureFeaturesRegistry.edited(id);
        Path saved = world.getStructureTemplateManager().getTemplatePath(id, ".nbt");
        source.sendFeedback(() -> Text.literal("Saved " + id + " to " + saved), false);

        copyToSources(source, id, saved);
        return 1;
    }

    // In a development run the saved template goes over the one of the mod, for datagen to read
    private static void copyToSources(ServerCommandSource source, Identifier id, Path saved) {
        if(!FabricLoader.getInstance().isDevelopmentEnvironment())
            return;
        Path resources = FabricLoader.getInstance().getGameDir().resolve("..").resolve(SOURCE_RESOURCES).normalize();
        if(!isOwnSources(resources))
            return;
        Path target = resources.resolve("data").resolve(id.getNamespace()).resolve("structure").resolve(id.getPath() + ".nbt");
        try {
            Files.createDirectories(target.getParent());
            Files.copy(saved, target, StandardCopyOption.REPLACE_EXISTING);
            source.sendFeedback(() -> Text.literal("Copied it over the mod template " + target + ". Rerun datagen."), false);
        } catch (IOException e) {
            source.sendError(Text.literal("Could not copy it to " + target + ": " + e.getMessage()));
        }
    }

    // A template loaded by itself is saved by its structure block, whose box this sets
    public static @Nullable Identifier resizeLoaded(ServerWorld world, ServerPlayerEntity player, BlockBox box) {
        StructureBlockBlockEntity block = findStructureBlock(world, player);
        if(block == null)
            return null;
        BlockPos at = block.getPos();
        block.setOffset(new BlockPos(box.getMinX() - at.getX(), box.getMinY() - at.getY(), box.getMinZ() - at.getZ()));
        block.setSize(new Vec3i(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ()));
        block.markDirty();
        world.updateListeners(at, world.getBlockState(at), world.getBlockState(at), Block.NOTIFY_ALL);
        StructureEditing.log("%s given the bounds %s to %s by its structure block at %s".formatted(block.getTemplateName(),
                new BlockPos(box.getMinX(), box.getMinY(), box.getMinZ()).toShortString(), new BlockPos(box.getMaxX(), box.getMaxY(), box.getMaxZ()).toShortString(), at.toShortString()));
        return Identifier.tryParse(block.getTemplateName());
    }

    private static StructureBlockBlockEntity findStructureBlock(ServerWorld world, ServerPlayerEntity player) {
        Placed placed = PLACED.get(player.getUuid());
        if(placed != null && placed.dimension().equals(world.getRegistryKey())
                && world.getBlockEntity(placed.saveBlock()) instanceof StructureBlockBlockEntity block && block.getMode() == StructureBlockMode.SAVE)
            return block;

        StructureBlockBlockEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        BlockPos center = player.getBlockPos();
        for(BlockPos pos : BlockPos.iterate(center.add(-NEAREST_SEARCH_RADIUS, -NEAREST_SEARCH_RADIUS, -NEAREST_SEARCH_RADIUS),
                center.add(NEAREST_SEARCH_RADIUS, NEAREST_SEARCH_RADIUS, NEAREST_SEARCH_RADIUS))) {
            if(!world.isChunkLoaded(pos) || !world.getBlockState(pos).isOf(Blocks.STRUCTURE_BLOCK))
                continue;
            if(world.getBlockEntity(pos) instanceof StructureBlockBlockEntity block && block.getMode() == StructureBlockMode.SAVE && block.hasStructureName()) {
                double distance = pos.getSquaredDistance(center);
                if(distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = block;
                }
            }
        }
        return nearest;
    }
}
