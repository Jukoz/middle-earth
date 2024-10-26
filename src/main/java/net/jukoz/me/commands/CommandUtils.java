package net.jukoz.me.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandUtils {
    public static final String COMMAND_PLAYER_REPLACEMENT = "<p>";

    public static void sendAllCommands(ServerPlayerEntity player, List<String> commands){
        CommandManager commandManager =  player.server.getCommandManager();
        ServerCommandSource commandSource = player.server.getCommandSource();

        for(String com : commands) {
            com = com.replace(COMMAND_PLAYER_REPLACEMENT, player.getName().getString());
            commandManager.executeWithPrefix(commandSource, com);
        }
    }

    public static void simpleCommand(CommandDispatcher<ServerCommandSource> dispatcher, String baseCommand, LiteralArgumentBuilder<ServerCommandSource> executes, String player, LiteralArgumentBuilder<ServerCommandSource> executes2) {
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(baseCommand)
                        .then(executes)));

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(baseCommand)
                        .then(argument(player, EntityArgumentType.player())
                                .then(executes2))));
    }
}
