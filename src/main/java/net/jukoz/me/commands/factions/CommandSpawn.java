package net.jukoz.me.commands.factions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class CommandSpawn {
    private static final String BASE_SPAWN_COMMAND = "spawn";
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String SPAWN_ID = "spawn_id";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // SET
        dispatcher.register((CommandManager.literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Require OP
                .then((CommandManager.literal(BASE_SPAWN_COMMAND))
                        .then(
                                CommandManager.argument(PLAYER, EntityArgumentType.player()) // Player Target
                                // Set with target
                                .then(CommandManager.literal(SET)
                                    .then(CommandManager.argument(SPAWN_ID, IdentifierArgumentType.identifier())
                                        .suggests(new AllAvailableSpawnSuggestionProvider())
                                    .executes(CommandSpawn::runSetWithTarget)))
                                // Get with target
                                .then(CommandManager.literal(GET)
                                                .executes(CommandSpawn::runGetWithTarget)))
                // Set without target
                .then(CommandManager.literal(SET)
                .then(CommandManager.argument(SPAWN_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                                            .executes(CommandSpawn::runGetWithTarget)))
                // Get without target
                .then(CommandManager.literal(GET)
                    .executes(CommandSpawn::runGetWithTarget))));

    }


    private static int runGet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = context.getSource().getPlayer();

        return 0;
    }

    private static int runGetWithTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

        return 0;
    }

    private static int runSet(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity targetedPlayer = context.getSource().getPlayer();

        return 0;
    }
    private static int runSetWithTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

        return 0;
    }
}
