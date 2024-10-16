package net.jukoz.me.commands;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

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
}
