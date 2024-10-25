package net.jukoz.me.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class CommandFetchPlayerInformations {
    private static final String INFO = "info";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register((CommandManager.literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)))
                .then((CommandManager.literal(INFO))
                .then(CommandManager.argument(PLAYER, EntityArgumentType.player())
                .executes(CommandFetchPlayerInformations::run))));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        try{
            ServerPlayerEntity targettedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

            PlayerData data = StateSaverAndLoader.getPlayerState(targettedPlayer);

            targettedPlayer.sendMessage(Text.of(data.toString()));

            return 1;
        } catch (Exception e){
            LoggerUtil.logError("GetPlayerInformationCommand", e);
            return 0;
        }
    }

    public static void register(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess) {
    }
}
