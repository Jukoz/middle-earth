package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.CommandUtils;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.network.packets.S2C.PacketForceOnboardingScreen;
import net.sevenstars.middleearth.network.connections.ConnectionToClient;
import net.sevenstars.middleearth.network.handlers.OnboardingServerHandler;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.utils.ModColors;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class CommandOnboarding {
    public static String ONBOARDING_BASE_COMMAND = "onboarding";
    private static final String OPEN = "open";
    private static final String TRY = "try";
    private static final String PLAYER = "player";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess, Commands.CommandSelection registrationEnvironment) {
        // [TRY OPEN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(ONBOARDING_BASE_COMMAND)
                        .then(literal(TRY)
                        .then(literal(OPEN)
                        .then(argument(PLAYER, EntityArgument.player())
                        .executes(CommandOnboarding::tryOpenForTarget)))))
                .then(literal(ONBOARDING_BASE_COMMAND)
                        .then(literal(TRY)
                        .then(literal(OPEN)
                        .executes(CommandOnboarding::tryOpen)))));

        // [OPEN]
        CommandUtils.simpleCommand(dispatcher, ONBOARDING_BASE_COMMAND,
                literal(OPEN).executes(CommandOnboarding::open),
                PLAYER, literal(OPEN).executes(CommandOnboarding::openForTarget));
    }

    private static int open(CommandContext<CommandSourceStack> context) {
        if(context.getSource().isPlayer()) {
            ServerPlayer source = context.getSource().getPlayer();
            if(source != null && OnboardingServerHandler.beginForced(source)){
                ConnectionToClient.send(source, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION, source));
                return 1;
            }
        }
        return 0;
    }

    private static int openForTarget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer targetPlayer = EntityArgument.getPlayer(context, PLAYER);
        if(targetPlayer != null && OnboardingServerHandler.beginForced(targetPlayer)){
            ConnectionToClient.send(targetPlayer, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION, targetPlayer));
            return 1;
        }

        return 0;
    }

    private static int tryOpen(CommandContext<CommandSourceStack> context) {
        if(context.getSource().isPlayer()) {
            ServerPlayer playerSource = context.getSource().getPlayer();
            if(playerSource != null){
                boolean playerPassedOnboarding = PlayerDataService.playerPassedOnboarding(playerSource);
                if(playerPassedOnboarding && OnboardingServerHandler.beginForced(playerSource)){
                    ConnectionToClient.send(playerSource, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION, playerSource));
                    return 1;
                } else {
                    MutableComponent sourceText = Component.translatable("command.%s.open.onboarding.error".formatted(MiddleEarth.MOD_ID));
                    playerSource.sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
                }
            }
        }
        return 0;
    }

    private static int tryOpenForTarget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);
        if(playerTarget != null){
            boolean playerPassedOnboarding = PlayerDataService.playerPassedOnboarding(playerTarget);
            if(playerPassedOnboarding && OnboardingServerHandler.beginForced(playerTarget)){
                ConnectionToClient.send(playerTarget, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION, playerTarget));
                MutableComponent sourceText = Component.translatable("command.%s.open_target.onboarding.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
                context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
                return 1;
            } else {
                MutableComponent sourceText = Component.translatable("command.%s.open_target.onboarding.error".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
                context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            }
        }

        return 0;
    }
}
