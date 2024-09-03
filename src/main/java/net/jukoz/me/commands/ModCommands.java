package net.jukoz.me.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.factions.CommandFaction;
import net.jukoz.me.commands.factions.CommandSpawn;

public class ModCommands {
    public static String BASE_COMMAND = "middle_earth";
    public static String FACTION_BASE_COMMAND = "faction";
    public static void register() {
        // Faction Commands
        CommandRegistrationCallback.EVENT.register(CommandFaction::register);
        CommandRegistrationCallback.EVENT.register(CommandSpawn::register);

        // TODO : Commands to clean
        CommandRegistrationCallback.EVENT.register(CommandDimensionTeleport::register);
        CommandRegistrationCallback.EVENT.register(CommandFetchPlayerInformations::register);
    }
}
