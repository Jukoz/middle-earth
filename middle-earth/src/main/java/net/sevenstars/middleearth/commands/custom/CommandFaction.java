package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.CommandUtils;
import net.sevenstars.middleearth.commands.CommandRegistryME;
import net.sevenstars.middleearth.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.sevenstars.middleearth.commands.suggestions.FactionSuggestionProvider;
import net.sevenstars.middleearth.utils.ColorsME;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.registries.services.FactionService;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
        CommandUtils.simpleCommand(dispatcher, FACTION_BASE_COMMAND, literal(GET) // With Player Target
        .executes(CommandFaction::getFaction), PLAYER, literal(GET) // With Player Target
        .executes(CommandFaction::getTargetFaction));

        // [CLEAR]
        dispatcher.register(literal(CommandRegistryME.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(CLEAR) // With Player Target
                    .executes(CommandFaction::clearTargetFaction))))
                .then(literal(FACTION_BASE_COMMAND)
                .then(literal(CLEAR) // Without Target
                .executes(CommandFaction::clearFaction))));

        // [JOIN]
        dispatcher.register(literal(CommandRegistryME.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(JOIN) // With Player Target
                    .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                    .suggests(new FactionSuggestionProvider())
                    .executes(CommandFaction::forceTargetToJoinFaction))))
                .then(literal(JOIN) // Without Target
                .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                .suggests(new FactionSuggestionProvider())
                .executes(CommandFaction::joinFaction)))));

        // [JOIN + SET SPAWN]
        dispatcher.register((literal(CommandRegistryME.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Require OP
                .then((literal(FACTION_BASE_COMMAND))
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then((literal(JOIN) // With Player Target
                        .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                        .suggests(new FactionSuggestionProvider())
                        .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                        .executes(CommandFaction::forceTargetToJoinFaction))))))

                    .then((literal(JOIN)) // No Player Target
                    .then(argument(FACTION_ID, IdentifierArgumentType.identifier())
                    .suggests(new FactionSuggestionProvider())
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandFaction::joinFaction))))));
    }

    private static int getFaction(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                Optional<RegistryEntry<Faction>> currentFaction = FactionService.getPlayerFaction(source);
                if(currentFaction.isEmpty()){
                    MutableText sourceText = Text.translatable("command.%s.get.faction.no_faction".formatted(MiddleEarth.MOD_ID));
                    source.sendMessage(sourceText.withColor(ColorsME.WARNING.color));
                    return 0;
                }
                MutableText sourceText = Text.translatable("command.%s.get.faction.success".formatted(MiddleEarth.MOD_ID), MiddleEarth.rawTranslationKey(LangCategory.FACTION, currentFaction.get().getIdAsString()));
                source.sendMessage(sourceText.withColor(ColorsME.SUCCESS.color));
            }
        }
        return 0;
    }

    private static int getTargetFaction(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        if(targetedPlayer != null && context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                Optional<RegistryEntry<Faction>> currentFaction = FactionService.getPlayerFaction(source);
                if(currentFaction.isEmpty()){
                    MutableText sourceText = Text.translatable("command.%s.get.player.faction.no_faction".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName());
                    source.sendMessage(sourceText.withColor(ColorsME.WARNING.color));
                    return 0;
                }
                MutableText sourceText = Text.translatable("command.%s.get.player.faction.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName(), MiddleEarth.rawTranslationKey(LangCategory.FACTION, currentFaction.get().getIdAsString()));
                source.sendMessage(sourceText.withColor(ColorsME.SUCCESS.color));
            }
        }
        return 0;
    }

    private static int clearFaction(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null) {
                FactionService.setFactionToPlayer(source,null);
                MutableText sourceText = Text.translatable("command.%s.clear.faction.success".formatted(MiddleEarth.MOD_ID));
                source.sendMessage(sourceText.withColor(ColorsME.SUCCESS.color));
                return 1;
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
                FactionService.setFactionToPlayer(playerSource,null);
                MutableText sourceText = Text.translatable("command.%s.clear.player.faction.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName());
                context.getSource().sendMessage(sourceText.withColor(ColorsME.SUCCESS.color));
                return 1;
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
            Faction faction = FactionService.fetchFaction(context.getSource().getWorld(), factionIdentifier);
            if(faction != null && context.getSource().isExecutedByPlayer()){
                ServerPlayerEntity source = context.getSource().getPlayer();
                MutableText sourceText = Text.translatable("command.%s.join.faction.join.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName(),  MiddleEarth.rawTranslationKey(LangCategory.FACTION, factionIdentifier));
                source.sendMessage(sourceText.withColor(ColorsME.SUCCESS.color));
            }
            return 1;
        }
        return 0;
    }

    public static boolean updateFactionFromCommand(ServerPlayerEntity target, ServerCommandSource source, Identifier factionIdentifier, @Nullable Identifier spawnId) {
        Faction foundFaction = null;
        foundFaction = FactionService.fetchFaction(source.getWorld(), factionIdentifier);
        FactionService.setFactionToPlayer(target, FactionService.fetchFactionEntry(source.getWorld(), foundFaction));
        // set default spawnId
        return true;
    }
}
