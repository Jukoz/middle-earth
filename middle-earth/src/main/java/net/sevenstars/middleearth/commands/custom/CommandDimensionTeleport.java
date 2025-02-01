package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

public class CommandDimensionTeleport {
    private static final String TP = "tp";
    private static final String PLAYER = "player";
    private static final String LOCATION = "location";
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register((CommandManager.literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)))
                .then((CommandManager.literal(TP))
                .then(CommandManager.argument(PLAYER, EntityArgumentType.player())
                .then(CommandManager.argument(LOCATION, Vec3ArgumentType.vec3())
                .executes(CommandDimensionTeleport::run)))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try{
            PosArgument posArgument = Vec3ArgumentType.getPosArgument(context, LOCATION);
            BlockPos coordinates = posArgument.toAbsoluteBlockPos(context.getSource());

            ServerPlayerEntity targettedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

            ModDimensions.teleportPlayerToMe(targettedPlayer, coordinates.toCenterPos(), true, true);
            return 1;
        } catch (Exception e){
            return 0;
        }
    }
}
