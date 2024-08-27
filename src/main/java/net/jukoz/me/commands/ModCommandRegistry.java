package net.jukoz.me.commands;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.factions.CommandJoinFaction;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class ModCommandRegistry{
    public static String BASE_COMMAND = MiddleEarth.MOD_ID;
    public static String FACTION_BASE_COMMAND = "faction";

    public static final SuggestionProvider<ServerCommandSource> ALL_JOINABLE_FACTIONS;

    public static void register() {
        // Faction Commands
        CommandRegistrationCallback.EVENT.register(CommandJoinFaction::register);

        // TODO : Commands to clean
        CommandRegistrationCallback.EVENT.register(CommandDimensionTeleport::register);
        CommandRegistrationCallback.EVENT.register(CommandFetchPlayerInformations::register);
    }

    static {
        ALL_JOINABLE_FACTIONS = SuggestionProviders.register(Identifier.of(MiddleEarth.MOD_ID, "all_joinable_factions"),
                (context, builder) -> CommandSource.suggestIdentifiers(ModSuggestionProviders::getAllJoinableFactionSuggestion, builder));
    }
}
