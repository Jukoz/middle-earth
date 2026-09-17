package net.sevenstars.api.command;

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
    public static void simpleCommand(CommandDispatcher<ServerCommandSource> dispatcher, String baseCommand, LiteralArgumentBuilder<ServerCommandSource> executes, String player, LiteralArgumentBuilder<ServerCommandSource> executes2) {

    }
}
