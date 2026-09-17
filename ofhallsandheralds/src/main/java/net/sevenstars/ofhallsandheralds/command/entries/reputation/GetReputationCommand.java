package net.sevenstars.ofhallsandheralds.command.entries.reputation;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.ofhallsandheralds.command.CommandKeys;
import net.sevenstars.ofhallsandheralds.command.providers.FactionSuggestionProvider;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.dtos.Reputation;
import net.sevenstars.ofhallsandheralds.persistentdatas.PlayerPersistentDataManagerHH;
import net.sevenstars.ofhallsandheralds.registries.services.FactionService;

import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GetReputationCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(
                literal(CommandKeys.REPUTATION)
                        .requires(source -> true)
                        .then(literal(CommandKeys.GET)
                                .then(argument(CommandKeys.FACTION, IdentifierArgumentType.identifier())
                                        .suggests(new FactionSuggestionProvider(false, false))
                                            .executes(GetReputationCommand::getReputation))));

        dispatcher.register(
                literal(CommandKeys.REPUTATION)
                        .requires(source -> true)
                        .then(literal(CommandKeys.GET)
                                .then(argument(CommandKeys.FACTION, IdentifierArgumentType.identifier())
                                        .suggests(new FactionSuggestionProvider(false, false))
                                        .then(argument(CommandKeys.PLAYER, EntityArgumentType.player())
                                            .executes(GetReputationCommand::getReputation)))));
    }

    private static int getReputation(CommandContext<ServerCommandSource> context) {
        Identifier factionIdentifier = IdentifierArgumentType.getIdentifier(context, "faction");
        UUID uuid = null;
        if(context.getSource().isExecutedByPlayer())
            uuid = context.getSource().getPlayer().getUuid();
        try{
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, CommandKeys.PLAYER);
            uuid = targetedPlayer.getUuid();
        } catch (CommandSyntaxException e){
            // No player
        }

        if(uuid == null)
            return 0;

        RegistryKey<Faction> factionKey = FactionService.getFactionKey(context.getSource().getWorld(), factionIdentifier);
        Reputation rep = PlayerPersistentDataManagerHH.getReputation().get(uuid).getReputationValue(factionKey);
        if(rep == null)
            return 0;

        context.getSource().sendMessage(Text.literal(rep.getFaction().getValue() + " " + rep.getLevel()));

        return 1;
    }
}
