package net.jukoz.me.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.commands.arguments.ArgumentSpawnId;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.*;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class CommandSetPlayerInformation {
    private static final String SET = "set";
    private static final String CATEGORY_SPAWN = "spawn";
    private static final String CATEGORY_CLEAR = "clear";
    private static final String PLAYER = "player";
    private static final String SPAWN_ID = "spawn_id";
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register((CommandManager.literal(ModCommandRegistry.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)))
                .then((CommandManager.literal(SET))
                .then((CommandManager.literal(CATEGORY_SPAWN))
                .then(CommandManager.argument(PLAYER, EntityArgumentType.player())
                .then(CommandManager.argument(SPAWN_ID, IdentifierArgumentType.identifier())
                .executes(CommandSetPlayerInformation::run))))));

        dispatcher.register((CommandManager.literal(ModCommandRegistry.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)))
                .then((CommandManager.literal(SET))
                .then((CommandManager.literal(CATEGORY_CLEAR))
                .then(CommandManager.argument(PLAYER, EntityArgumentType.player())
                .executes(CommandSetPlayerInformation::runClear)))));
    }

    private static int runClear(CommandContext<ServerCommandSource> context) {
        try{
            ServerPlayerEntity targettedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
            PlayerData playerData = StateSaverAndLoader.getPlayerState(targettedPlayer);
            if(playerData.hasAffilition()){
                playerData.clearData();

                ServerPlayerEntity sourcePlayer = context.getSource().getPlayer();
                if(sourcePlayer != null){
                    MutableText text = Text.translatable("command.me.clear_player_data.success");
                    sourcePlayer.sendMessage(Text.of(String.format(text.getString(), targettedPlayer.getName())), true);
                }
            }
            return 1;
        } catch (Exception e){
            return 0;
        }
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try{
            Identifier id = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

            ServerPlayerEntity targettedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
            PlayerData playerData = StateSaverAndLoader.getPlayerState(targettedPlayer);
            if(playerData.hasAffilition()){
                AffiliationData affiliationData = playerData.getAffiliationData();
                // TODO : Make sure the id exists + suggestions
                affiliationData.spawnId = id;
                targettedPlayer.sendMessage(Text.of(playerData.getAffiliationData().spawnId));
                ServerPlayerEntity sourcePlayer = context.getSource().getPlayer();
                if(sourcePlayer != null){
                    MutableText text = Text.translatable("command.me.set_player_spawn.success");
                    sourcePlayer.sendMessage(Text.of(String.format(text.getString(), affiliationData.spawnId, targettedPlayer.getName())), true);
                }
            }
            return 1;
        } catch (Exception e){
            return 0;
        }
    }

}
