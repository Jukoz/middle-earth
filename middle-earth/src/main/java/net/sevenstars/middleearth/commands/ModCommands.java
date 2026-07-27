package net.sevenstars.middleearth.commands;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.sevenstars.middleearth.commands.custom.*;

public class ModCommands {
    public static String BASE_COMMAND = "middle_earth";

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ModCommands::registerCommands);
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        var buildContext = event.getBuildContext();
        var commandSelection = event.getCommandSelection();

        // PlayerFactionPayload Commands
        CommandFaction.register(dispatcher, buildContext, commandSelection);
        CommandSpawn.register(dispatcher, buildContext, commandSelection);
        CommandRace.register(dispatcher, buildContext, commandSelection);

        // Onboarding Commands
        CommandOnboarding.register(dispatcher, buildContext, commandSelection);

        //Misc commands
        CommandCustomEquipment.register(dispatcher, buildContext, commandSelection);
        CommandInformation.register(dispatcher, buildContext, commandSelection);
        CommandDimensionTeleport.register(dispatcher, buildContext, commandSelection);
    }
}
