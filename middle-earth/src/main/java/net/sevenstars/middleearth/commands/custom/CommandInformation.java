package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandInformation {
    private static final String INFO_BASE_COMMAND = "info";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [INFO]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(INFO_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player()) // With Player Target
                    .executes(CommandInformation::getTargetInfo))
                .executes(CommandInformation::getInfo)));
    }

    private static int getInfo(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 1;

        ServerPlayerEntity source = context.getSource().getPlayer();

        PlayerData data = StateSaverAndLoader.getPlayerState(source);

        Race race =  PlayerDataService.getPlayerRace(source, source.getWorld());
        if(race != null)
            source.sendMessage(Text.literal("Race : ").append(Text.translatable(race.getTranslatableKey())));

        Faction faction =  PlayerDataService.getPlayerFaction(source, source.getWorld());
        if(faction != null)
            source.sendMessage(Text.literal("Faction : ").append(faction.getFullName()));

        SpawnData spawnData =  PlayerDataService.getPlayerSpawnData(source, source.getWorld());
        if(spawnData != null){
            source.sendMessage(Text.literal("Middle-earth Spawn : ").append(spawnData.getFullName()));
        }

        PlayerDataService.OriginAggregate origin =  PlayerDataService.getOriginAggregate(source, source.getWorld());
        if(origin != null){
            source.sendMessage(Text.literal("Origin : ").append(Text.translatable(origin.dimensionId().toTranslationKey())).append(" " + origin.origin().toShortString()));
        }
        return 0;
    }

    private static int getTargetInfo(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targettedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

        PlayerData data = StateSaverAndLoader.getPlayerState(targettedPlayer);

        Race race =  PlayerDataService.getPlayerRace(targettedPlayer, targettedPlayer.getWorld());
        if(race != null)
            context.getSource().sendMessage(Text.literal("Race : ").append(Text.translatable(race.getTranslatableKey())));


        Faction faction =  PlayerDataService.getPlayerFaction(targettedPlayer, targettedPlayer.getWorld());
        if(faction != null)
            context.getSource().sendMessage(Text.literal("Faction : ").append(Text.translatable(faction.getId().toTranslationKey())));
        return 0;
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        try{
            ServerPlayerEntity targettedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

            PlayerData data = StateSaverAndLoader.getPlayerState(targettedPlayer);
            targettedPlayer.sendMessage(Text.of(data.toString()));

            return 1;
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("GetPlayerInformationCommand", e);
            return 0;
        }
    }

    public static void register(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess) {
    }
}
