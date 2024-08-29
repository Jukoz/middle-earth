package net.jukoz.me.commands.factions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.commands.CommandColors;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.jukoz.me.commands.suggestions.AllJoinableFactionSuggestionProvider;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.resources.datas.faction.FactionUtil;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class CommandFaction {
    private static final String BASE = "join";
    private static final String GET = "get";
    private static final String FACTION_ID = "faction_id";
    private static final String SPAWN_ID = "spawn_id";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [GET]
        dispatcher.register((CommandManager.literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Require OP
                .then((CommandManager.literal(ModCommands.FACTION_BASE_COMMAND))
                        .then(CommandManager.argument(PLAYER, EntityArgumentType.player())
                            .then(CommandManager.literal(GET) // With Player Target
                            .executes(CommandFaction::getFactionFromTarget)))

                            .then(CommandManager.literal(GET) // No Player Target
                            .executes(CommandFaction::getFactionFromSource))));

        // [JOIN]
        dispatcher.register((CommandManager.literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Require OP
                .then((CommandManager.literal(ModCommands.FACTION_BASE_COMMAND))
                .then(CommandManager.argument(PLAYER, EntityArgumentType.player())
                        .then((CommandManager.literal(BASE) // With Player Target
                        .then(CommandManager.argument(FACTION_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllJoinableFactionSuggestionProvider())
                        .executes(CommandFaction::makeTargetJoinFaction)))))

                        .then((CommandManager.literal(BASE)) // No Player Target
                        .then(CommandManager.argument(FACTION_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllJoinableFactionSuggestionProvider())
                        .executes(CommandFaction::makeSourceJoinFaction)))));

        // [JOIN + SET SPAWN] // TODO : spawn need to be set
        dispatcher.register((CommandManager.literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Require OP
                .then((CommandManager.literal(ModCommands.FACTION_BASE_COMMAND))
                .then(CommandManager.argument(PLAYER, EntityArgumentType.player())
                    .then((CommandManager.literal(BASE) // With Player Target
                    .then(CommandManager.argument(FACTION_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllJoinableFactionSuggestionProvider())
                    .then(CommandManager.argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandFaction::makeTargetJoinFaction))))))

                    .then((CommandManager.literal(BASE)) // No Player Target
                    .then(CommandManager.argument(FACTION_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllJoinableFactionSuggestionProvider())
                    .then(CommandManager.argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandFaction::makeSourceJoinFaction))))));
    }

    private static int getFactionFromSource(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                PlayerData playerData = StateSaverAndLoader.getPlayerState(source);
                if(playerData != null && playerData.hasAffilition()){
                    Faction foundFaction = FactionUtil.getFactionById(playerData.getAffiliationData().faction);
                    if(foundFaction != null){
                        MutableText sourceText = Text.translatable("command.me.faction.get.source", foundFaction.getFullName());
                        source.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color), true);
                        return 1;
                    }
                }
                MutableText sourceText = Text.translatable("command.me.faction.get.source.none");
                source.sendMessage(sourceText.withColor(CommandColors.WARNING.color), true);
            }
        }
        return 0;
    }

    private static int getFactionFromTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        if(targetedPlayer != null && context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                PlayerData playerData = StateSaverAndLoader.getPlayerState(targetedPlayer);
                if(playerData != null && playerData.hasAffilition()){
                    Faction foundFaction = FactionUtil.getFactionById(playerData.getAffiliationData().faction);
                    if(foundFaction != null) {
                        // Send success message to source
                        MutableText sourceText = Text.translatable("command.me.faction.get.target", targetedPlayer.getName(), foundFaction.getFullName());
                        source.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color), true);
                        return 1;
                    }
                }
                // Send empty message to source
                MutableText sourceText = Text.translatable("command.me.faction.get.target.none", targetedPlayer.getName());
                source.sendMessage(sourceText.withColor(CommandColors.WARNING.color), true);
            }
        }
        return 0;
    }

    private static int makeTargetJoinFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        Identifier factionIdentifier = IdentifierArgumentType.getIdentifier(context, FACTION_ID);
        boolean success = updateFactionFromCommand(targetedPlayer, context.getSource(), factionIdentifier, null);
        if(success){
            Faction faction = FactionUtil.getFactionById(factionIdentifier);
            if(context.getSource().isExecutedByPlayer()){
                ServerPlayerEntity source = context.getSource().getPlayer();
                // Send success message to source
                MutableText sourceText = Text.translatable("command.me.faction.join.source", targetedPlayer.getName(), faction.getFullName());
                source.sendMessage(sourceText.withColor(CommandColors.ALERT.color), true);
            }
            return 1;
        } else {
            // Send error message to source
            // TODO : Translatable field
            MutableText errorMessage = Text.translatable("command.me.fail");
            targetedPlayer.sendMessage(errorMessage.withColor(CommandColors.ALERT.color), true);
        }
        return 0;
    }

    private static int makeSourceJoinFaction(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity targetedPlayer = context.getSource().getPlayer();
        Identifier factionIdentifier = IdentifierArgumentType.getIdentifier(context, FACTION_ID);;
        if(targetedPlayer != null){
            boolean success =  updateFactionFromCommand(targetedPlayer, context.getSource(), factionIdentifier, null);
            if(success){
                return 1;
            }
        }

        return 0;
    }

    public static boolean updateFactionFromCommand(ServerPlayerEntity target, ServerCommandSource source, Identifier factionIdentifier, @Nullable Identifier spawnId) {
        try {
            Faction foundFaction = ModFactionRegistry.findFactionById(factionIdentifier);
            return FactionUtil.updateFaction(target, foundFaction, spawnId);
        } catch (FactionIdentifierException e){
            MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, factionIdentifier.toString());
            if(source != null) {
                if(source.isExecutedByPlayer()){
                    source.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
                } else {
                    target.getCommandSource().sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
                }
            }
        }
        return false;
    }
}
