package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
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

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal(COMMAND)
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal(LOAD)
                        .then(CommandManager.argument(TEMPLATE, IdentifierArgumentType.identifier())
                                .suggests(TEMPLATES)
                                .executes(CommandStructureEdit::load)))
                .then(CommandManager.literal(SAVE)
                        .executes(context -> save(context, null))
                        .then(CommandManager.argument(TEMPLATE, IdentifierArgumentType.identifier())
                                .suggests(TEMPLATES)
                                .executes(context -> save(context, IdentifierArgumentType.getIdentifier(context, TEMPLATE))))));
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

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Path resources = FabricLoader.getInstance().getGameDir().resolve("..").resolve(SOURCE_RESOURCES).normalize();
            if(Files.isDirectory(resources)) {
                Path target = resources.resolve("data").resolve(id.getNamespace()).resolve("structure").resolve(id.getPath() + ".nbt");
                try {
                    Files.createDirectories(target.getParent());
                    Files.copy(saved, target, StandardCopyOption.REPLACE_EXISTING);
                    source.sendFeedback(() -> Text.literal("Copied it over the mod template " + target + ". Rerun datagen."), false);
                } catch (IOException e) {
                    source.sendError(Text.literal("Could not copy it to " + target + ": " + e.getMessage()));
                }
            }
        }
        return 1;
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
