package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.sevenstars.middleearth.commands.CommandUtils;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.network.packets.S2C.PacketForceOnboardingScreen;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.utils.ModColors;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandOnboarding {
    public static String ONBOARDING_BASE_COMMAND = "onboarding";
    private static final String OPEN = "open";
    private static final String TRY = "try";
    private static final String PLAYER = "player";
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [TRY OPEN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(ONBOARDING_BASE_COMMAND)
                        .then(literal(TRY)
                        .then(literal(OPEN)
                        .then(argument(PLAYER, EntityArgumentType.player())
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

    private static int open(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                ServerPlayNetworking.send(source, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION));
            }
        }
        return 0;
    }

    private static int openForTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        if(targetPlayer != null){
            ServerPlayNetworking.send(targetPlayer, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION));
        }

        return 0;
    }

    private static int tryOpen(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                PlayerData data = StateSaverAndLoader.getPlayerState(source);
                if(data == null || !data.hasAffilition()){
                    ServerPlayNetworking.send(source, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION));
                } else {
                    MutableText sourceText = Text.translatable("command.me.open.onboarding.error");
                    source.sendMessage(sourceText.withColor(ModColors.WARNING.color));
                }
            }
        }
        return 0;
    }

    private static int tryOpenForTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        if(targetPlayer != null){
            PlayerData data = StateSaverAndLoader.getPlayerState(targetPlayer);
            if(data == null || !data.hasAffilition()){
                ServerPlayNetworking.send(targetPlayer, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION));
                MutableText sourceText = Text.translatable("command.me.open_target.onboarding.success", targetPlayer.getName());
                context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
            } else {
                MutableText sourceText = Text.translatable("command.me.open_target.onboarding.error",targetPlayer.getName());
                context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            }
        }

        return 0;
    }
}
