package net.jukoz.me.utils.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommandRegistry {
    public static String BASE_COMMAND = "middle-earth";

    public static void register() {
        CommandRegistrationCallback.EVENT.register(DimensionTeleportCommand::register);
    }
}
