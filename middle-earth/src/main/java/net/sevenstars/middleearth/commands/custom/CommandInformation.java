package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class CommandInformation {
    private static final String INFO_BASE_COMMAND = "info";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess, Commands.CommandSelection registrationEnvironment) {
        // [INFO]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(INFO_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgument.player()) // With Player Target
                    .executes(CommandInformation::getTargetInfo))
                .executes(CommandInformation::getInfo)));
    }

    private static int getInfo(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 1;

        ServerPlayer source = context.getSource().getPlayer();

        Race race =  PlayerDataService.getPlayerRace(source, source.level());
        if(race != null)
            source.sendSystemMessage(Component.literal("Race : ").append(Component.translatable(race.getTranslatableKey())));

        Faction faction =  PlayerDataService.getPlayerFaction(source, source.level());
        if(faction != null)
            source.sendSystemMessage(Component.literal("Faction : ").append(faction.getFullName()));

        SpawnData spawnData =  PlayerDataService.getPlayerSpawnData(source, source.level());
        if(spawnData != null){
            source.sendSystemMessage(Component.literal("Middle-earth Spawn : ").append(spawnData.getFullName()));
        }

        PlayerDataService.OriginAggregate origin =  PlayerDataService.getOriginAggregate(source, source.level());
        if(origin != null){
            source.sendSystemMessage(Component.literal("Origin : ").append(Component.translatable(origin.dimensionId().toLanguageKey())).append(" " + origin.origin().toShortString()));
        }
        return 0;
    }

    private static int getTargetInfo(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer targettedPlayer = EntityArgument.getPlayer(context, PLAYER);

        Race race =  PlayerDataService.getPlayerRace(targettedPlayer, targettedPlayer.level());
        if(race != null)
            context.getSource().sendSystemMessage(Component.literal("Race : ").append(Component.translatable(race.getTranslatableKey())));


        Faction faction =  PlayerDataService.getPlayerFaction(targettedPlayer, targettedPlayer.level());
        if(faction != null)
            context.getSource().sendSystemMessage(Component.literal("Faction : ").append(Component.translatable(faction.getId().toLanguageKey())));
        return 0;
    }

    private static int run(CommandContext<CommandSourceStack> context) {
        try{
            ServerPlayer targettedPlayer = EntityArgument.getPlayer(context, PLAYER);

            PlayerData data = StateSaverAndLoader.getPlayerStateReadOnly(targettedPlayer);
            targettedPlayer.sendSystemMessage(Component.nullToEmpty(data == null ? "No Data" : data.toString()));

            return 1;
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("GetPlayerInformationCommand", e);
            return 0;
        }
    }

}
