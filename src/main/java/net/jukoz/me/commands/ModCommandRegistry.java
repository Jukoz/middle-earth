package net.jukoz.me.commands;

import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.arguments.ArgumentSpawnId;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

public class ModCommandRegistry {
    public static String BASE_COMMAND = MiddleEarth.MOD_ID;

    public static void register() {
        CommandRegistrationCallback.EVENT.register(CommandDimensionTeleport::register);
        CommandRegistrationCallback.EVENT.register(CommandFetchPlayerInformations::register);
        CommandRegistrationCallback.EVENT.register(CommandSetPlayerInformation::register);

        ArgumentTypeRegistry.registerArgumentType(
                Identifier.of(MiddleEarth.MOD_ID, "spawn_id"),
                ArgumentSpawnId.class, ConstantArgumentSerializer.of(ArgumentSpawnId::spawnId));
    }
}
