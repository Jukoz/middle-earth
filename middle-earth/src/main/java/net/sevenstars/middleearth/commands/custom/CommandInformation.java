package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandInformation {
    private static final String INFO_BASE_COMMAND = "info";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        /*
        // [INFO]
        dispatcher.register(literal(CommandRegistryME.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(INFO_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player()) // With Player Target
                    .executes(CommandInformation::getTargetInfo))
                .executes(CommandInformation::getInfo)));

         */
    }
    /*

    private static int getInfo(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 1;

        ServerPlayerEntity source = context.getSource().getPlayer();

        PlayerData data = StateSaverAndLoader.getPlayerState(source);

        Race race =  PlayerDataService.getPlayerRace(source, source.getWorld());
        if(race != null)
            source.sendMessage(Text.literal("Race : ").append(Text.translatable(race.getTranslatableKey())));

        Optional<RegistryEntry<Faction>> faction = FactionService.getPlayerFaction(source);
        if(faction.isPresent())
            source.sendMessage(Text.literal("Faction : ").append(Text.translatable(MiddleEarth.rawTranslationKey(LangCategory.FACTION, faction.get().getIdAsString()))));

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


        Optional<RegistryEntry<Faction>> faction =  FactionService.getPlayerFaction(targettedPlayer);
        if(faction.isPresent())
            context.getSource().sendMessage(Text.literal("Faction : ").append(Text.translatable(MiddleEarth.rawTranslationKey(LangCategory.FACTION, faction.get().getIdAsString()))));
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

     */
    public static void register(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess) {
    }

}
