package net.jukoz.me.commands.factions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.commands.CommandColors;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.jukoz.me.commands.suggestions.AllJoinableFactionSuggestionProvider;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.exceptions.IdenticalFactionException;
import net.jukoz.me.exceptions.NoFactionException;
import net.jukoz.me.network.packets.C2S.PacketTeleportToCurrentSpawn;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.FactionUtil;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandSpawn {
    private static final String SPAWN_BASE_COMMAND = "spawn";
    private static final String SET = "set";
    private static final String OVERWORLD = "overworld";
    private static final String MIDDLE_EARTH = "middle_earth";
    private static final String OVERWORLD_COORD = "overworld_blockpos";
    private static final String GET = "get";
    private static final String TP = "tp";
    private static final String WELCOME = "welcome_needed";
    private static final String SPAWN_ID = "spawn_id";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [GET SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player()) // With Player Target
                        .then(literal(MIDDLE_EARTH)
                        .then(literal(GET)
                        .executes(CommandSpawn::getTargetSpawnId))))
                    .then(literal(MIDDLE_EARTH) // Without Target
                    .then(literal(GET)
                    .executes(CommandSpawn::getSpawnId)))));

        // [SET SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then(literal(MIDDLE_EARTH) // Without Target
                        .then(literal(SET) // With Player Target
                        .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                        .executes(CommandSpawn::setSpawnForTarget)))))
                    .then(literal(MIDDLE_EARTH) // Without Target
                    .then(literal(SET) // Without Target
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandSpawn::setSpawnForSource))))));

        // [SET OVERWORLD SPAWN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                        .then(argument(PLAYER, EntityArgumentType.player())
                                .then(literal(OVERWORLD) // With Player Target
                                .then(literal(SET)
                                .then(argument(OVERWORLD_COORD, BlockPosArgumentType.blockPos())
                                .executes(CommandSpawn::setOverworldOriginForSource)))))
                        .then(literal(OVERWORLD) // Without Target
                        .then(literal(SET)
                        .then(argument(OVERWORLD_COORD, BlockPosArgumentType.blockPos())
                        .executes(CommandSpawn::setOverworldOriginForTarget))))));

        // [TP]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                        .then(argument(PLAYER, EntityArgumentType.player())
                            .then(literal(TP) // With Player Target
                            .then(argument(WELCOME, BoolArgumentType.bool())
                            .executes(CommandSpawn::teleportTargetToSpawn))))
                        .then(literal(TP) // Without Target
                        .then(argument(WELCOME, BoolArgumentType.bool())
                        .executes(CommandSpawn::teleportToSpawn)))));
    }



    private static int getSpawnId(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        PlayerData data = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());
        if(data.hasAffilition()){
            Identifier spawnId = data.getAffiliationData().spawnId;
            MutableText sourceText = Text.translatable("command.me.spawn.middle_earth.get.source",
                    Text.translatable("spawn."+spawnId.toTranslationKey()));
            context.getSource().getPlayer().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        }
        return 0;

    }

    private static int getTargetSpawnId(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(!context.getSource().isExecutedByPlayer()) return 0;

        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, PLAYER);

        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data.hasAffilition()){
            Identifier spawnId = data.getAffiliationData().spawnId;
            MutableText sourceText = Text.translatable("command.me.spawn.middle_earth.get.target", player.getName(),
                    Text.translatable("spawn."+spawnId.toTranslationKey()));

            context.getSource().getPlayer().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        }
        return 0;
    }

    private static int setOverworldOriginForSource(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        BlockPos pos = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);

        PlayerData data = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());
        data.setOverworldSpawn(pos);

        return 0;
    }

    private static int setOverworldOriginForTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        BlockPos pos = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);

        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        data.setOverworldSpawn(pos);

        return 0;
    }

    private static int setSpawnForSource(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        ServerPlayerEntity source = context.getSource().getPlayer();
        PlayerData data = StateSaverAndLoader.getPlayerState(source);
        if(data.hasAffilition()){
            AffiliationData affiliationData = data.getAffiliationData();
            Identifier foundId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);
            try{
                if(FactionLookup.findFactionById(affiliationData.faction).getSpawnData().getSpawnList().containsKey(foundId)){
                    affiliationData.spawnId = foundId;
                }
            } catch (FactionIdentifierException e){
                MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, affiliationData.faction.toString());
                source.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
                return 0;
            }
            return 1;
        } else {
            MutableText errorMessage = Text.translatable(NoFactionException.KEY_SOURCE);
            source.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
        }

        return 0;
    }

    private static int setSpawnForTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);

        if(data.hasAffilition()){
            AffiliationData affiliationData = data.getAffiliationData();
            Identifier foundId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);
            try{
                if(FactionLookup.findFactionById(affiliationData.faction).getSpawnData().getSpawnList().containsKey(foundId)){
                    affiliationData.spawnId = foundId;
                }
            } catch (FactionIdentifierException e){
                MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, affiliationData.faction.toString());
                context.getSource().sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
                return 0;
            }
            return 1;
        } else {
            MutableText errorMessage = Text.translatable(NoFactionException.KEY_TARGET, targetedPlayer.getName());
            context.getSource().sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
        }
        return 0;
    }

    private static int teleportToSpawn(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        ServerPlayerEntity player = context.getSource().getPlayer();
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data != null){
            if(data.hasAffilition()){
                AffiliationData affiliationData = data.getAffiliationData();
                Vec3d spawnCoordinates = affiliationData.getMiddleEarthSpawnCoordinate();
                if(ModDimensions.isInOverworld(player.getWorld()) && data.getOverworldSpawnCoordinates() == null){
                    data.setOverworldSpawn(player.getBlockPos());
                }
                if(spawnCoordinates != null)
                    ModDimensions.teleportPlayerToMe(player, new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), welcomeNeeded);
            }
        }
        return 0;
    }

    private static int teleportTargetToSpawn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, PLAYER);
        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data != null){
            if(data.hasAffilition()){
                AffiliationData affiliationData = data.getAffiliationData();
                Vec3d spawnCoordinates = affiliationData.getMiddleEarthSpawnCoordinate();
                if(ModDimensions.isInOverworld(player.getWorld()) && data.getOverworldSpawnCoordinates() == null){
                    data.setOverworldSpawn(player.getBlockPos());
                }
                if(spawnCoordinates != null)
                    ModDimensions.teleportPlayerToMe(player, new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), welcomeNeeded);
            }
        }
        return 0;
    }
}
