package net.jukoz.me.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.commands.CommandUtils;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllRaceSuggestionProvider;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.packets.S2C.PacketForceOnboardingScreen;
import net.jukoz.me.network.packets.S2C.PacketOnboardingResult;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.ModColors;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandOnboarding {
    public static String ONBOARDING_BASE_COMMAND = "onboarding";
    private static final String OPEN = "open";
    private static final String PLAYER = "player";
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
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
}
