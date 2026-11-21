package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.commands.CommandRegistryME;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.network.packets.server2client.PacketConfigAction;

import static net.minecraft.server.command.CommandManager.literal;

public class CommandConfig {
    private static final String CONFIG_BASE_COMMAND = "config";
    private static final String RELOAD = "reload";
    private static final String RESET = "reset";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [CONFIG]
        dispatcher.register(
                literal(CommandRegistryME.BASE_COMMAND)
                        .then(literal(CONFIG_BASE_COMMAND)
                                .then(literal(RELOAD)
                                        .executes(CommandConfig::reloadConfig)
                                )
                        )
        );

        dispatcher.register(
                literal(CommandRegistryME.BASE_COMMAND)
                        .then(literal(CONFIG_BASE_COMMAND)
                                .then(literal(RESET)
                                        .executes(CommandConfig::resetConfig)
                                )
                        )
        );
    }

    private static int reloadConfig(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        boolean isOp = CommandManager.GAMEMASTERS_CHECK.allows(source.getPermissions());

        if (player != null) {
            ServerPlayNetworking.send(player, new PacketConfigAction(false));
        }

        if (isOp) {
            ServerConfigME.reload();
            if (player != null) {
                source.sendMessage(Text.literal("Reloaded Middle-earth CLIENT and SERVER configs."));
            } else {
                source.sendMessage(Text.literal("Reloaded Middle-earth SERVER config."));
            }
        } else if (player != null) {
            source.sendMessage(Text.literal("Reloaded Middle-earth CLIENT config (server config requires OP)."));
        } else {
            source.sendMessage(Text.literal("Reloading Middle-earth SERVER config requires OP."));
            return 0;
        }
        return 1;
    }

    private static int resetConfig(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        boolean isOp = CommandManager.GAMEMASTERS_CHECK.allows(source.getPermissions());

        if (player != null) {
            ServerPlayNetworking.send(player, new PacketConfigAction(true));
        }

        if (isOp) {
            ServerConfigME.reset();
            if (player != null) {
                source.sendMessage(Text.literal("Reset Middle-earth CLIENT and SERVER configs."));
            } else {
                source.sendMessage(Text.literal("Reset Middle-earth SERVER config."));
            }
        } else if (player != null) {
            source.sendMessage(Text.literal("Reset Middle-earth CLIENT config (server config requires OP)."));
        } else {
            source.sendMessage(Text.literal("Resetting Middle-earth SERVER config requires OP."));
            return 0;
        }
        return 1;
    }
}