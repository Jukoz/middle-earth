package net.sevenstars.ofhallsandheralds.registries.custom;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.command.entries.reputation.*;

public class CommandRegistryHH  {
    public static void register() {
        OfHallsAndHeralds.logRegistryMessage("Commands");

        // Reputation
        CommandRegistrationCallback.EVENT.register(SetReputationCommand::register);
        CommandRegistrationCallback.EVENT.register(GetReputationCommand::register);
    }
}
