package net.jukoz.me.utils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.*;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class DimensionTeleportCommand {
    private static final String TP = "tp";
    private static final String PLAYER = "player";
    private static final String LOCATION = "location";
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register((CommandManager.literal(ModCommandRegistry.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)))
                .then((CommandManager.literal(TP))
                .then(CommandManager.argument(PLAYER, EntityArgumentType.player())
                .then(CommandManager.argument(LOCATION, Vec3ArgumentType.vec3())
                .executes(DimensionTeleportCommand::run)))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try{
            PosArgument posArgument = Vec3ArgumentType.getPosArgument(context, LOCATION);
            Vec3d coordinates = posArgument.toAbsolutePos(context.getSource());

            ServerPlayerEntity targettedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

            ModDimensions.teleportPlayerToMe(targettedPlayer, coordinates);
            return 1;
        } catch (Exception e){
            return 0;
        }
    }
}
