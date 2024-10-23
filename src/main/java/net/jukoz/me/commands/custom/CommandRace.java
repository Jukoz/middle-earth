package net.jukoz.me.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.ModColors;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.literal;

public class CommandRace {
    public static String RACE_BASE_COMMAND = "race";
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String RESET = "reset";
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [GET]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(RACE_BASE_COMMAND)
                .then(literal(GET)
                .executes(CommandRace::getRace))));

    }

    private static int getRace(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                PlayerData playerData = StateSaverAndLoader.getPlayerState(source);
                if(playerData != null){
                    Identifier id = playerData.getRace();
                    if(id != null){
                        MutableText sourceText = Text.translatable("testing this shit out %s", id);
                        source.sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                        return 0;
                    }
                }
                MutableText sourceText = Text.translatable("You don't have a race");
                source.sendMessage(sourceText.withColor(ModColors.WARNING.color));
            }
        }
        return 0;
    }
}
