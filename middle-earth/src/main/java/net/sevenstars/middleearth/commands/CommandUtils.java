package net.sevenstars.middleearth.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class CommandUtils {
    public static final String COMMAND_PLAYER_REPLACEMENT = "<p>";

    public static void sendAllCommands(ServerPlayer player, List<String> commands){
        Commands commandManager =  player.level().getServer().getCommands();
        CommandSourceStack commandSource = player.level().getServer().createCommandSourceStack();

        for(String com : commands) {
            com = com.replace(COMMAND_PLAYER_REPLACEMENT, player.getName().getString());
            commandManager.performPrefixedCommand(commandSource, com);
        }
    }

    public static void simpleCommand(CommandDispatcher<CommandSourceStack> dispatcher, String baseCommand, LiteralArgumentBuilder<CommandSourceStack> executes, String player, LiteralArgumentBuilder<CommandSourceStack> executes2) {
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(baseCommand)
                        .then(executes)));

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(baseCommand)
                    .then(argument(player, EntityArgument.player())
                            .then(executes2))));
    }
}
