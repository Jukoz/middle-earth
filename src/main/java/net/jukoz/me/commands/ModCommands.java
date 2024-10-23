package net.jukoz.me.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.jukoz.me.commands.custom.*;

public class ModCommands {
    public static String BASE_COMMAND = "middle_earth";
    public static void register() {
        // Faction Commands
        CommandRegistrationCallback.EVENT.register(CommandFaction::register);
        CommandRegistrationCallback.EVENT.register(CommandSpawn::register);
        CommandRegistrationCallback.EVENT.register(CommandRace::register);

        // TODO : Commands to clean
        CommandRegistrationCallback.EVENT.register(CommandDimensionTeleport::register);
        CommandRegistrationCallback.EVENT.register(CommandFetchPlayerInformations::register);

        //Misc commands
        CommandRegistrationCallback.EVENT.register(CommandCustomEquipment::register);
    }
}
