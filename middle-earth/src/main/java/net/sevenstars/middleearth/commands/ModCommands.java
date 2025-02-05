package net.sevenstars.middleearth.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.sevenstars.middleearth.commands.custom.*;

public class ModCommands {
    public static String BASE_COMMAND = "middle_earth";
    public static void register() {
        // Faction Commands
        CommandRegistrationCallback.EVENT.register(CommandFaction::register);
        CommandRegistrationCallback.EVENT.register(CommandSpawn::register);
        CommandRegistrationCallback.EVENT.register(CommandRace::register);

        // Onboarding Commands
        CommandRegistrationCallback.EVENT.register(CommandOnboarding::register);

        //Misc commands
        CommandRegistrationCallback.EVENT.register(CommandCustomEquipment::register);
        CommandRegistrationCallback.EVENT.register(CommandInformation::register);
        CommandRegistrationCallback.EVENT.register(CommandDimensionTeleport::register);
    }
}
