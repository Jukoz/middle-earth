package net.jukoz.me.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.utils.ModColors;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.jukoz.me.commands.suggestions.AllJoinableFactionSuggestionProvider;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.exceptions.IdenticalFactionException;
import net.jukoz.me.exceptions.NoFactionException;
import net.jukoz.me.exceptions.SpawnIdentifierException;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.FactionUtil;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.*;

public class CommandFaction {
    public static String FACTION_BASE_COMMAND = "faction";
    private static final String JOIN = "join";
    private static final String GET = "get";
    private static final String CLEAR = "clear";
    private static final String FACTION_ID = "faction_id";
    private static final String SPAWN_ID = "spawn_id";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, RegistrationEnvironment registrationEnvironment) {
        // [GET]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                .then(literal(GET) // With Player Target
                .executes(CommandFaction::getFaction))));


        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                .then(literal(GET) // With Player Target
                .executes(CommandFaction::getTargetFaction)))));

        // [CLEAR]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(CLEAR) // With Player Target
                    .executes(CommandFaction::clearTargetFaction))))
                .then(literal(FACTION_BASE_COMMAND)
                .then(literal(CLEAR) // Without Target
                .executes(CommandFaction::clearFaction))));

        // [JOIN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(JOIN) // With Player Target
                    .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllJoinableFactionSuggestionProvider())
                    .executes(CommandFaction::forceTargetToJoinFaction))))
                .then(literal(JOIN) // Without Target
                .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                .suggests(new AllJoinableFactionSuggestionProvider())
                .executes(CommandFaction::joinFaction)))));

        // [JOIN + SET SPAWN]
        dispatcher.register((literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Require OP
                .then((literal(FACTION_BASE_COMMAND))
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then((literal(JOIN) // With Player Target
                        .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllJoinableFactionSuggestionProvider())
                        .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                        .executes(CommandFaction::forceTargetToJoinFaction))))))

                    .then((literal(JOIN)) // No Player Target
                    .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllJoinableFactionSuggestionProvider())
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandFaction::joinFaction))))));
    }

    private static int getFaction(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                PlayerData playerData = StateSaverAndLoader.getPlayerState(source);
                if(playerData != null && playerData.hasAffilition()){
                    try{
                        Faction currentFaction = playerData.getCurrentFaction(context.getSource().getWorld());
                        MutableText sourceText = Text.translatable("command.me.get.faction.success", currentFaction.getFullName());
                        source.sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                    } catch (FactionIdentifierException e) {
                        MutableText sourceText = Text.translatable("command.me.get.faction.no_faction");
                        source.sendMessage(sourceText.withColor(ModColors.WARNING.color));
                    }
                }
            }
        }
        return 0;
    }

    private static int getTargetFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        if(targetedPlayer != null && context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                PlayerData playerData = StateSaverAndLoader.getPlayerState(targetedPlayer);
                if(playerData != null && playerData.hasAffilition()){
                    try{
                        Faction foundFaction = playerData.getCurrentFaction(context.getSource().getWorld());
                        MutableText sourceText = Text.translatable("command.me.get.player.faction.success", targetedPlayer.getName(), foundFaction.getFullName());
                        source.sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                    } catch (FactionIdentifierException e) {
                        MutableText sourceText = Text.translatable("command.me.get.player.faction.no_faction", targetedPlayer.getName());
                        source.sendMessage(sourceText.withColor(ModColors.WARNING.color));
                    }
                }
            }
        }
        return 0;
    }

    private static int clearFaction(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null) {
                try {
                    if (FactionUtil.clearFaction(source)) {
                        MutableText sourceText = Text.translatable("command.me.clear.faction.success");
                        source.sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                    }
                    return 1;
                } catch (NoFactionException e) {
                    MutableText sourceText = Text.translatable(NoFactionException.KEY_SOURCE);
                    source.sendMessage(sourceText.withColor(ModColors.ALERT.color));
                } catch (FactionIdentifierException e) {
                    MutableText sourceText = Text.translatable(FactionIdentifierException.KEY);
                    source.sendMessage(sourceText.withColor(ModColors.ALERT.color));
                }
            }
        }
        return 0;
    }

    private static int clearTargetFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        boolean isPlayerSource = context.getSource().isExecutedByPlayer();

        if(targetedPlayer != null){
            ServerPlayerEntity playerSource = null;
            if(isPlayerSource)
                playerSource = context.getSource().getPlayer();
            if(context.getSource() != null) {
                try {
                    if (FactionUtil.clearFaction(playerSource)) {
                        MutableText sourceText = Text.translatable("command.me.clear.player.faction.success", targetedPlayer.getName());
                        context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                    }
                    return 1;
                } catch (NoFactionException e) {
                    MutableText sourceText = Text.translatable(NoFactionException.KEY_TARGET, targetedPlayer.getName());
                    context.getSource().sendMessage(sourceText.withColor(ModColors.ALERT.color));
                } catch (FactionIdentifierException e) {
                    MutableText sourceText = Text.translatable(FactionIdentifierException.KEY);
                    context.getSource().sendMessage(sourceText.withColor(ModColors.ALERT.color));
                }
            }
        }

        return 0;
    }
    private static int joinFaction(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity targetedPlayer = context.getSource().getPlayer();
        Identifier factionIdentifier = IdentifierArgumentType.getIdentifier(context, FACTION_ID);;
        if(targetedPlayer != null){
            Identifier spawnIdentifier = null;
            try{
                spawnIdentifier = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);
            } catch (Exception ignored){
            }
            boolean success =  updateFactionFromCommand(targetedPlayer, context.getSource(), factionIdentifier, spawnIdentifier);
            if(success){
                return 1;
            }
        }

        return 0;
    }

    private static int forceTargetToJoinFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        Identifier factionIdentifier = IdentifierArgumentType.getIdentifier(context, FACTION_ID);
        Identifier spawnIdentifier = null;
        try{
            spawnIdentifier = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);
        } catch (Exception ignored){
        }

        boolean success = updateFactionFromCommand(targetedPlayer, context.getSource(), factionIdentifier, spawnIdentifier);
        if(success){
            try{
                Faction faction = FactionLookup.getFactionById(context.getSource().getWorld(), factionIdentifier);
                if(context.getSource().isExecutedByPlayer()){
                    ServerPlayerEntity source = context.getSource().getPlayer();
                    MutableText sourceText = Text.translatable("command.me.join.faction.join.success", targetedPlayer.getName(), faction.getFullName());
                    source.sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                }
            } catch (FactionIdentifierException e) {
                LoggerUtil.logDebugMsg("Faction Id does not exist");
            }

            return 1;
        }
        return 0;
    }

    public static boolean updateFactionFromCommand(ServerPlayerEntity target, ServerCommandSource source, Identifier factionIdentifier, @Nullable Identifier spawnId) {
        Faction foundFaction = null;
        boolean commandOnSelf = source.isExecutedByPlayer() && source.getPlayer() == target;
        try {
            foundFaction = FactionLookup.getFactionById(source.getWorld(), factionIdentifier);
            FactionUtil.updateFaction(target, foundFaction, spawnId);
            return true;
        } catch (FactionIdentifierException e){
            MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, factionIdentifier.toString());
            source.sendMessage(errorMessage.withColor(ModColors.ALERT.color));
            return false;
        } catch (IdenticalFactionException e){
            if(commandOnSelf){ // Player on himself
                MutableText errorMessage = Text.translatable(IdenticalFactionException.KEY_SOURCE, (foundFaction == null) ? factionIdentifier.toString() : foundFaction.getFullName());
                source.sendMessage(errorMessage.withColor(ModColors.ALERT.color));
                return false;
            }  else { // Player on another target or Command block
                MutableText errorMessage = Text.translatable(IdenticalFactionException.KEY_TARGET, target.getName(), (foundFaction == null) ? factionIdentifier.toString() : foundFaction.getFullName());
                source.sendMessage(errorMessage.withColor(ModColors.ALERT.color));
                return false;
            }
        } catch (SpawnIdentifierException e){
                MutableText errorMessage;
                if(spawnId != null){
                    errorMessage = Text.translatable(SpawnIdentifierException.KEY, spawnId.toString());
                } else {
                    errorMessage = Text.translatable("command.me.fail");
                }
                source.sendMessage(errorMessage.withColor(ModColors.ALERT.color));
                return false;
        } catch (NoFactionException e){
            if(commandOnSelf){ // Player on himself
                MutableText errorMessage = Text.translatable(NoFactionException.KEY_SOURCE);
                source.sendMessage(errorMessage.withColor(ModColors.ALERT.color));
                return false;
            }  else { // Player on another target or Command block
                MutableText errorMessage = Text.translatable(NoFactionException.KEY_TARGET, target);
                source.sendMessage(errorMessage.withColor(ModColors.ALERT.color));
                return false;
            }
        }
    }
}
