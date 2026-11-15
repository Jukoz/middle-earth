package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
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
                                .executes(context -> save(context, IdentifierArgumentType.getIdentifier(context, TEMPLATE))))));
    }

    // The whole structure, put together from its pieces as the game would from that seed. With no seed, one is rolled and
    // told, to come back to the same pieces later
    private static int loadStructure(CommandContext<ServerCommandSource> context, boolean spaced, @Nullable Long seed) throws CommandSyntaxException {
        Identifier id = IdentifierArgumentType.getIdentifier(context, STRUCTURE);
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
        StructureEditSession before = StructureEditSession.of(player.getUuid());
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
                spaced ? "spaced %d blocks apart".formatted(session.getGap()) : "assembled", spaced ? "/%s %s writes the pieces that were changed".formatted(COMMAND, SAVE)
                        : "This view is for looking: /%s %s %s to change and save pieces".formatted(COMMAND, VIEW, SPACED))), false);
        return 1;
    }

    // The same structure of the same seed, the other way
    private static int view(CommandContext<ServerCommandSource> context, boolean spaced) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        StructureEditSession session = StructureEditSession.of(source.getPlayerOrThrow().getUuid());
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
        StructureEditSession session = StructureEditSession.of(player.getUuid());
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
        StructureEditSession session = StructureEditSession.of(player.getUuid());
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

    private static int close(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        if(!StructureEditSession.close(source.getWorld(), source.getPlayerOrThrow().getUuid())) {
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
        Identifier id = IdentifierArgumentType.getIdentifier(context, TEMPLATE);

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
        source.sendFeedback(() -> Text.literal("Loaded %s (%dx%dx%d) from %s. Its structure block is at %s, below the first corner."
                .formatted(id, size.getX(), size.getY(), size.getZ(), origin.toShortString(), saveBlock.toShortString())), false);
        return 1;
    }

    private static int save(CommandContext<ServerCommandSource> context, Identifier override) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        ServerWorld world = source.getWorld();

        StructureEditSession session = StructureEditSession.of(player.getUuid());
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
        if(!Files.isDirectory(resources))
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
