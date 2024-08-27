package net.jukoz.me.commands.factions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.commands.ModCommandRegistry;
import net.jukoz.me.resources.datas.faction.FactionUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CommandJoinFaction {
    private static final String BASE = "join";
    private static final String FACTION_ID = "join";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register((CommandManager.literal(ModCommandRegistry.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Need to be OP
                .then((CommandManager.literal(ModCommandRegistry.FACTION_BASE_COMMAND))
                .then(CommandManager.argument(PLAYER, EntityArgumentType.player()) // Optional
                    .then((CommandManager.literal(BASE))
                    .then(CommandManager.argument(FACTION_ID, IdentifierArgumentType.identifier())
                            .suggests(ModCommandRegistry.ALL_JOINABLE_FACTIONS)
                    .executes(CommandJoinFaction::runWithTarget))))
                .then((CommandManager.literal(BASE))
                .then(CommandManager.argument(FACTION_ID, IdentifierArgumentType.identifier())
                        .suggests(ModCommandRegistry.ALL_JOINABLE_FACTIONS)
                .executes(CommandJoinFaction::run)))));
    }

    private static int runWithTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        Identifier factionIdentifier = IdentifierArgumentType.getIdentifier(context, FACTION_ID);;
        boolean success =  FactionUtil.updateFaction(targetedPlayer, factionIdentifier, null);
        if(success){
            LoggerUtil.logDebugMsg("Join faction is successful");
            MutableText targetText = Text.translatable("You have joined X faction");
            targetedPlayer.sendMessage(targetText, true);

            MutableText sourceText = Text.translatable("command.me.set_player_spawn.success", "test 1", "test 2");
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null)
                source.sendMessage(sourceText, true);
            return 1;
        } else {
            MutableText errorMessage = Text.translatable("The faction id or player name isn't valid");
            targetedPlayer.sendMessage(errorMessage, true);
        }
        return 0;
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity targetedPlayer = context.getSource().getPlayer();
        Identifier factionIdentifier = IdentifierArgumentType.getIdentifier(context, FACTION_ID);;
        if(targetedPlayer != null){
            boolean success =  FactionUtil.updateFaction(targetedPlayer, factionIdentifier, null);
            LoggerUtil.logDebugMsg("Command result : " +success);
            if(success){
                LoggerUtil.logDebugMsg("Join faction is successful");
                MutableText targetText = Text.translatable("You have joined X faction");
                targetedPlayer.sendMessage(targetText, true);
                return 1;
            } else {
                MutableText errorMessage = Text.translatable("The faction id isn't valid");
                targetedPlayer.sendMessage(errorMessage, true);
            }
        }

        return 0;
    }
}
