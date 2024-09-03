package net.jukoz.me.commands.factions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.commands.CommandColors;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.jukoz.me.commands.suggestions.AllJoinableFactionSuggestionProvider;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.exceptions.IdenticalFactionException;
import net.jukoz.me.exceptions.SpawnIdentifierException;
import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.resources.datas.faction.FactionUtil;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.logging.Logger;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.*;

public class CommandFaction {
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
                .then(literal(ModCommands.FACTION_BASE_COMMAND)
                .then(literal(GET) // With Player Target
                .executes(CommandFaction::getFaction))));


        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(ModCommands.FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                .then(literal(GET) // With Player Target
                .executes(CommandFaction::getTargetFaction)))));

        // [CLEAR]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(ModCommands.FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(CLEAR) // With Player Target
                    .executes(CommandFaction::clearTargetFaction))))
                .then(literal(ModCommands.FACTION_BASE_COMMAND)
                .then(literal(CLEAR) // Without Target
                .executes(CommandFaction::clearFaction))));

        // [JOIN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(ModCommands.FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(JOIN) // With Player Target
                    .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllJoinableFactionSuggestionProvider())
                    .executes(CommandFaction::forceTargetToJoinFaction))))
                .then(literal(JOIN) // Without Target
                .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                .suggests(new AllJoinableFactionSuggestionProvider())
                .executes(CommandFaction::joinFaction)))));

        // [JOIN + SET SPAWN] // TODO : spawn need to be set
        dispatcher.register((literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Require OP
                .then((literal(ModCommands.FACTION_BASE_COMMAND))
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

    private static int getFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                source.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(50);
                PlayerData playerData = StateSaverAndLoader.getPlayerState(source);
                if(playerData != null && playerData.hasAffilition()){
                    Faction foundFaction = FactionUtil.getFactionById(playerData.getAffiliationData().faction);
                    if(foundFaction != null){
                        MutableText sourceText = Text.translatable("command.me.faction.get.source", foundFaction.getFullName());
                        source.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                        return 1;
                    }
                }
                MutableText sourceText = Text.translatable("command.me.faction.get.source.none");
                source.sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            }
        }
        return 0;
    }

    private static int getTargetFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        if(targetedPlayer != null && context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){

                /* TODO : race stuff with custom attributes
                final DynamicRegistryManager registryManager = source.getWorld().getRegistryManager();
                EntityAttribute attribute = registryManager.get(RegistryKeys.ATTRIBUTE).get(Identifier.ofVanilla("generic.gravity"));
                LoggerUtil.logDebugMsg("default value : " + attribute.getDefaultValue());

                Optional<RegistryEntry.Reference<EntityAttribute>> attributeEntry =  Registries.ATTRIBUTE.getEntry(Identifier.ofVanilla("generic.gravity"));
                if(attributeEntry != null && attributeEntry.isPresent()){
                    EntityAttributeInstance instance = source.getAttributes().getCustomInstance(attributeEntry.get());
                    if(instance != null){
                        instance.setBaseValue(0.3);
                    }
                }
                */

                //source.getAttributeInstance(entry).setBaseValue(0.10000000149011612);

                PlayerData playerData = StateSaverAndLoader.getPlayerState(targetedPlayer);
                if(playerData != null && playerData.hasAffilition()){
                    Faction foundFaction = FactionUtil.getFactionById(playerData.getAffiliationData().faction);
                    if(foundFaction != null) {
                        // Send success message to source
                        MutableText sourceText = Text.translatable("command.me.faction.get.target", targetedPlayer.getName(), foundFaction.getFullName());
                        source.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                        return 1;
                    }
                }
                // Send empty message to source
                MutableText sourceText = Text.translatable("command.me.faction.get.target.none", targetedPlayer.getName());
                source.sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            }
        }
        return 0;
    }

    private static int clearFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                PlayerData playerData = StateSaverAndLoader.getPlayerState(source);
                if(playerData != null && playerData.hasAffilition()){
                    Faction foundFaction = FactionUtil.getFactionById(playerData.getAffiliationData().faction);
                    if(foundFaction != null){
                        MutableText sourceText = Text.translatable("in progress", foundFaction.getFullName());
                        source.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                        return 1;
                    }
                }
                MutableText sourceText = Text.translatable("in progress");
                source.sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            }
        }
        return 0;
    }

    private static int clearTargetFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
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
            Faction faction = FactionUtil.getFactionById(factionIdentifier);
            if(context.getSource().isExecutedByPlayer()){
                ServerPlayerEntity source = context.getSource().getPlayer();
                // Send success message to source
                MutableText sourceText = Text.translatable("command.me.faction.join.source", targetedPlayer.getName(), faction.getFullName());
                source.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color), true);
            }
            return 1;
        }
        return 0;
    }

    public static boolean updateFactionFromCommand(ServerPlayerEntity target, ServerCommandSource source, Identifier factionIdentifier, @Nullable Identifier spawnId) {
        Faction foundFaction = null;
        try {
            foundFaction = ModFactionRegistry.findFactionById(factionIdentifier);
            FactionUtil.updateFaction(target, foundFaction, spawnId);
            return true;
        } catch (FactionIdentifierException e){
            if(source != null) {
                MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, factionIdentifier.toString());
                source.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
                return false;
            }
        } catch (IdenticalFactionException e){
            if(source != null){
                if(source.isExecutedByPlayer() && source.getPlayer() == target){ // Player on himself
                    MutableText errorMessage = Text.translatable(IdenticalFactionException.KEY_SOURCE, (foundFaction == null) ? factionIdentifier.toString() : foundFaction.getFullName());
                    source.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
                    return false;
                }  else { // Player on another target or Command block
                    MutableText errorMessage = Text.translatable(IdenticalFactionException.KEY_TARGET, target.getName(), (foundFaction == null) ? factionIdentifier.toString() : foundFaction.getFullName());
                    source.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
                    return false;
                }
            }
        } catch (SpawnIdentifierException e){
            if(source != null) {
                MutableText errorMessage;
                if(spawnId != null){
                    errorMessage = Text.translatable(SpawnIdentifierException.KEY, spawnId.toString());
                } else {
                    errorMessage = Text.translatable("command.me.fail");
                }
                source.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
                return false;
            }
        }
        MutableText errorMessage = Text.translatable("command.me.fail");
        source.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
        return false;
    }
}
