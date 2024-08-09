package net.jukoz.me.utils.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.jukoz.me.MiddleEarth;

public class ModCommandRegistry {
    public static String BASE_COMMAND = MiddleEarth.MOD_ID;

    public static void register() {
        CommandRegistrationCallback.EVENT.register(DimensionTeleportCommand::register);
        CommandRegistrationCallback.EVENT.register(GetPlayerInformationCommand::register);
    }
}
