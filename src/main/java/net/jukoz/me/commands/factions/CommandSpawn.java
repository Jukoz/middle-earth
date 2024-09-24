package net.jukoz.me.commands.factions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.commands.CommandColors;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.jukoz.me.commands.suggestions.AllSpawnSuggestionProvider;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.RaceType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.FactionUtil;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector2d;
import org.joml.Vector3i;

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
    private static final String TO = "to";
    private static final String RESET = "reset";
    private static final String WELCOME = "welcome_needed";
    private static final String SPAWN_ID = "spawn_id";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [GET OVERWORLD SPAWN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player()) // With Player Target
                    .then(literal(GET)
                    .then(literal(OVERWORLD)
                    .executes(CommandSpawn::getPlayerSpawnOverworld))))
                .then(literal(GET) // Without Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::getSpawnOverworld)))));

        // [GET SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player()) // With Player Target
                        .then(literal(GET)
                        .then(literal(MIDDLE_EARTH)
                        .executes(CommandSpawn::getPlayerSpawnMiddleEarth))))
                    .then(literal(GET) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .executes(CommandSpawn::getSpawnMiddleEarth)))));

        // [TP - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then(literal(TP) // With Player Target
                        .then(literal(MIDDLE_EARTH)
                        .then(argument(WELCOME, BoolArgumentType.bool())
                        .executes(CommandSpawn::teleportPlayerToSpawnMiddleEarth)))))
                    .then(literal(TP) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .then(argument(WELCOME, BoolArgumentType.bool())
                    .executes(CommandSpawn::teleportToSpawnMiddleEarth))))));

        // [TP - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(TP) // With Player Target
                    .then(literal(OVERWORLD)
                    .executes(CommandSpawn::teleportPlayerToSpawnOverworld))))
                .then(literal(TP) // Without Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::teleportToSpawnOverworld)))));

        // [SET - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                        .then(argument(PLAYER, EntityArgumentType.player())
                                .then(literal(SET)
                                .then(literal(OVERWORLD) // With Player Target
                                .then(argument(OVERWORLD_COORD, BlockPosArgumentType.blockPos())
                                .executes(CommandSpawn::setPlayerSpawnOverworld)))))
                        .then(literal(SET) // Without Target
                        .then(literal(OVERWORLD)
                        .then(argument(OVERWORLD_COORD, BlockPosArgumentType.blockPos())
                        .executes(CommandSpawn::setSpawnOverworld))))));

        // [SET - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then(literal(SET)
                        .then(literal(MIDDLE_EARTH) // With Player Target
                        .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                        .executes(CommandSpawn::setPlayerSpawnMiddleEarth)))))
                    .then(literal(SET) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandSpawn::setSpawnMiddleEarth))))));

        // [RESET - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
            .requires(source -> source.hasPermissionLevel(2)) // Require OP
            .then(literal(SPAWN_BASE_COMMAND)
            .then(argument(PLAYER, EntityArgumentType.player())
                .then(literal(RESET) // With Player Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::resetPlayerSpawnOverworld))))
            .then(literal(RESET) // Without Target
            .then(literal(OVERWORLD)
            .executes(CommandSpawn::resetSpawnOverworld)))));

        // [RESET - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(RESET) // With Player Target
                    .then(literal(MIDDLE_EARTH)
                    .executes(CommandSpawn::resetPlayerSpawnMiddleEarth))))
                .then(literal(RESET) // Without Target
                .then(literal(MIDDLE_EARTH)
                .executes(CommandSpawn::resetSpawnMiddleEarth)))));

        // [TP TO - SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(TP)
                    .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(TO)
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllSpawnSuggestionProvider())
                    .executes(CommandSpawn::forceTeleportPlayerToSpawnMiddleEarth))))
                .then(literal(TO) // Without Target
                .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                .suggests(new AllSpawnSuggestionProvider())
                .executes(CommandSpawn::forceTeleportToSpawnMiddleEarth)))));
    }

    // region Getters
    private static int getSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        ServerPlayerEntity player = context.getSource().getPlayer();
        PlayerData data = StateSaverAndLoader.getPlayerState(player);

        if(data.getOverworldSpawnCoordinates() != null){
            BlockPos pos = data.getOverworldSpawnCoordinates();
            MutableText sourceText = Text.translatable("command.me.get.spawn.overworld.success", pos.getX(), pos.getY(), pos.getZ());
            context.getSource().getPlayer().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            BlockPos pos = context.getSource().getServer().getOverworld().getSpawnPos();
            MutableText sourceText = Text.translatable("command.me.get.spawn.overworld.no_spawn", pos.getX(), pos.getY(), pos.getZ());
            context.getSource().getPlayer().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;
    }

    private static int getPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        if(data.getOverworldSpawnCoordinates() != null){
            BlockPos pos = data.getOverworldSpawnCoordinates();
            MutableText sourceText = Text.translatable("command.me.get.player.spawn.overworld.success", targetedPlayer.getName(), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            BlockPos pos = context.getSource().getServer().getOverworld().getSpawnPos();
            MutableText sourceText = Text.translatable("command.me.get.player.spawn.overworld.no_spawn", targetedPlayer.getName(), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;
    }

    private static int getSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        PlayerData data = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());
        Identifier spawnId = data.getCurrentSpawnId();
        if(spawnId != null){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld() ,spawnId);
            MutableText sourceText = Text.translatable("command.me.get.spawn.middle_earth.success", Text.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().getPlayer().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("command.me.get.spawn.middle_earth.no_spawn");
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;

    }

    private static int getPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(!context.getSource().isExecutedByPlayer()) return 0;

        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        Identifier spawnId = data.getCurrentSpawnId();
        if(spawnId != null){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld() ,spawnId);
            MutableText sourceText = Text.translatable("command.me.get.player.spawn.middle_earth.success", targetedPlayer.getName(), Text.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("command.me.get.player.spawn.middle_earth.no_spawn", targetedPlayer.getName());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;
    }
    // endregion

    // region Setters
    private static int setSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayerEntity sourcePlayer = context.getSource().getPlayer();

        Identifier foundId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);
        PlayerData playerData = StateSaverAndLoader.getPlayerState(sourcePlayer);

        if(!playerData.hasAffilition()){
            MutableText sourceText = Text.translatable("command.me.set.spawn.middle_earth.no_faction");
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            return 0;
        }

        try{
            if(playerData.setSpawnMiddleEarthId(context.getSource().getWorld(), foundId)){
                Faction faction = playerData.getCurrentFaction(context.getSource().getWorld());
                if(faction.getSpawnData() != null){
                    BlockPos pos =  FactionUtil.getSpawnBlockPos(context.getSource().getWorld(), foundId);
                    if(pos != null) {
                        if(ModDimensions.isInMiddleEarth(sourcePlayer.getWorld()))
                            sourcePlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, true);
                        MutableText sourceText = Text.translatable("command.me.set.spawn.middle_earth.success", Text.translatable("spawn."+foundId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                        sourcePlayer.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                        return 0;
                    }
                }
            }
            MutableText sourceText = Text.translatable("command.me.set.spawn.middle_earth.no_spawn_found", foundId.toString());
            sourcePlayer.sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        } catch (FactionIdentifierException e){
            MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, playerData.getCurrentFactionId().toString());
            sourcePlayer.sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
        }

        return 0;
    }

    private static int setPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

        Identifier foundId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);
        PlayerData playerData = StateSaverAndLoader.getPlayerState(targetedPlayer);

        if(!playerData.hasAffilition()){
            MutableText sourceText = Text.translatable("command.me.set.player.spawn.middle_earth.no_faction", targetedPlayer.getName());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            return 0;
        }

        try{
            if(playerData.setSpawnMiddleEarthId(context.getSource().getWorld(), foundId)){
                Faction faction = playerData.getCurrentFaction(context.getSource().getWorld());
                if(faction != null && faction.getSpawnData() != null){
                    BlockPos pos =  faction.getSpawnData().getSpawnBlockPos(foundId);
                    if(pos != null) {
                        if(ModDimensions.isInMiddleEarth(targetedPlayer.getWorld()))
                            targetedPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, true);

                        MutableText targetText = Text.translatable("command.me.set.spawn.middle_earth.success", Text.translatable("spawn."+foundId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                        targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
                        MutableText sourceText = Text.translatable("command.me.set.player.spawn.middle_earth.success", targetedPlayer.getName(),Text.translatable("spawn."+foundId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                        context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                        return 0;
                    }
                }
            }
            MutableText sourceText = Text.translatable("command.me.set.spawn.middle_earth.no_spawn_found", foundId.toString());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        } catch (FactionIdentifierException e){
            MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, playerData.getCurrentFactionId().toString());
            context.getSource().sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
        }

        return 0;
    }

    private static int setSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        BlockPos pos = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);
        ServerPlayerEntity player = context.getSource().getPlayer();
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        data.setOverworldSpawn(pos);
        if(ModDimensions.isInOverworld(player.getWorld()))
            player.setSpawnPoint(World.OVERWORLD, pos, 0, true, true);

        MutableText sourceText = Text.translatable("command.me.set.spawn.overworld.success", pos.getX(), pos.getY(), pos.getZ());
        context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));

        return 0;
    }

    private static int setPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        BlockPos pos = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);

        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        data.setOverworldSpawn(pos);

        MutableText sourceText = Text.translatable("command.me.set.player.spawn.overworld.success", targetedPlayer.getName() ,pos.getX(), pos.getY(), pos.getZ());
        context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));

        MutableText targetText = Text.translatable("command.me.set.spawn.overworld.success", pos.getX(), pos.getY(), pos.getZ());
        targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));

        return 0;
    }

    // endregion

    // region Resets
    private static int resetPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        BlockPos pos = context.getSource().getServer().getOverworld().getSpawnPos();
        data.setOverworldSpawn(pos);

        MutableText sourceText = Text.translatable("command.me.reset.player.spawn.overworld.success", targetedPlayer.getName() ,pos.getX(), pos.getY(), pos.getZ());
        context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        MutableText targetText = Text.translatable("command.me.reset.spawn.overworld.success", pos.getX(), pos.getY(), pos.getZ());
        targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
        return 0;
    }

    private static int resetSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayerEntity player = context.getSource().getPlayer();
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        BlockPos pos = context.getSource().getServer().getOverworld().getSpawnPos();
        data.setOverworldSpawn(pos);
        MutableText sourceText = Text.translatable("command.me.reset.spawn.overworld.success",pos.getX(), pos.getY(), pos.getZ());
        player.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        return 0;
    }

    private static int resetPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData playerData = StateSaverAndLoader.getPlayerState(targetedPlayer);

        if(!playerData.hasAffilition()){
            MutableText sourceText = Text.translatable("command.me.reset.player.spawn.middle_earth.no_faction", targetedPlayer.getName());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            return 0;
        }

        try{
            Faction faction = playerData.getCurrentFaction(context.getSource().getWorld());
            Identifier spawnId = faction.getSpawnData().getDefaultSpawn();
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld(), spawnId);
            if(pos != null){
                playerData.setSpawnMiddleEarthId(context.getSource().getWorld(), spawnId);
                if(ModDimensions.isInMiddleEarth(targetedPlayer.getWorld()))
                    targetedPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, true);

                MutableText sourceText = Text.translatable("command.me.reset.player.spawn.middle_earth.success", targetedPlayer.getName(), Text.translatable("spawn." + spawnId.toTranslationKey()),pos.getX(), pos.getY(), pos.getZ());
                context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));

                MutableText targetText = Text.translatable("command.me.reset.spawn.middle_earth.success", Text.translatable("spawn." + spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
                return 0;
            }
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private static int resetSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayerEntity player = context.getSource().getPlayer();
        PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
        if(!playerData.hasAffilition()){
            MutableText sourceText = Text.translatable("command.me.reset.spawn.middle_earth.no_faction");
            player.sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            return 0;
        }

        try{
            Faction faction = playerData.getCurrentFaction(context.getSource().getWorld());
            Identifier foundSpawn = faction.getSpawnData().getDefaultSpawn();
            BlockPos newSpawn = faction.getSpawnData().getSpawnBlockPos(foundSpawn);

            if(newSpawn != null){
                if(ModDimensions.isInMiddleEarth(player.getWorld()))
                    player.setSpawnPoint(ModDimensions.ME_WORLD_KEY, newSpawn, 0, true, true);

                playerData.setSpawnMiddleEarthId(context.getSource().getWorld(), foundSpawn);
                MutableText sourceText = Text.translatable("command.me.reset.spawn.middle_earth.success", Text.translatable("spawn." + foundSpawn.toTranslationKey()), newSpawn.getX(), newSpawn.getY(), newSpawn.getZ());
                player.sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                return 0;
            }
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    // endregion

    // region Teleports
    private static int teleportToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        ServerPlayerEntity player = context.getSource().getPlayer();
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data != null){
            if(data.hasAffilition()){
                Vec3d spawnCoordinates = data.getSpawnMiddleEarthCoordinate(context.getSource().getWorld());
                if(ModDimensions.isInOverworld(player.getWorld()) && data.getOverworldSpawnCoordinates() == null){
                    data.setOverworldSpawn(player.getBlockPos());
                }
                if(spawnCoordinates != null) {
                    BlockPos pos = new BlockPos((int) spawnCoordinates.x, (int) spawnCoordinates.y, (int) spawnCoordinates.z);
                    if(ModDimensions.isInMiddleEarth(player.getWorld()))
                        player.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, true);
                    ModDimensions.teleportPlayerToMe(player, new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded);
                    MutableText sourceText = Text.translatable("command.me.teleport.spawn.middle_earth.success", Text.translatable("spawn."+ data.getCurrentSpawnId().toTranslationKey()));
                    context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                    return 0;
                }
            }
        }
        MutableText sourceText = Text.translatable("command.me.teleport.spawn.middle_earth.no_spawn");
        context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        return 0;
    }

    private static int teleportPlayerToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, PLAYER);
        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        PlayerData data = StateSaverAndLoader.getPlayerState(target);
        if(data != null){
            if(data.hasAffilition()){
                Vec3d spawnCoordinates = data.getSpawnMiddleEarthCoordinate(context.getSource().getWorld());
                if(ModDimensions.isInOverworld(target.getWorld()) && data.getOverworldSpawnCoordinates() == null){
                    data.setOverworldSpawn(target.getBlockPos());
                }
                if(spawnCoordinates != null) {
                    BlockPos pos = new BlockPos((int) spawnCoordinates.x, (int) spawnCoordinates.y, (int) spawnCoordinates.z);
                    if(ModDimensions.isInMiddleEarth(target.getWorld()))
                        target.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, true);
                    ModDimensions.teleportPlayerToMe(target, new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded);
                    MutableText sourceText = Text.translatable("command.me.teleport.player.spawn.middle_earth.success", target.getName(), Text.translatable("spawn."+data.getCurrentSpawnId().toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                    context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                    MutableText targetText = Text.translatable("command.me.teleport.spawn.middle_earth.success", Text.translatable("spawn."+data.getCurrentSpawnId().toTranslationKey()));
                    target.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
                    return 0;
                }
            }
        }
        MutableText sourceText = Text.translatable("command.me.teleport.player.spawn.middle_earth.no_spawn", target.getName());
        context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        return 0;
    }


    private static int teleportToSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        ServerPlayerEntity player = context.getSource().getPlayer();
        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableText sourceText = Text.translatable("command.me.teleport.spawn.middle_earth.success");
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
            return 0;
        }
        MutableText sourceText = Text.translatable("command.me.teleport.spawn.middle_earth.error");
        context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        return 0;
    }

    private static int teleportPlayerToSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, PLAYER);

        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableText sourceText = Text.translatable("command.me.teleport.player.spawn.middle_earth.success", player.getName(), player.getX(), player.getY(), player.getZ());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
            return 0;
        }
        MutableText sourceText = Text.translatable("command.me.teleport.player.spawn.middle_earth.no_spawn", player.getName());
        context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        return 0;
    }

    private static int forceTeleportToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        Identifier spawnId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(FactionUtil.forceTeleportToSpawnMiddleEarthId(context.getSource().getPlayer(), spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld(), spawnId);
            MutableText targetText = Text.translatable("command.me.teleport.to.spawn.middle_earth.success", Text.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("command.me.teleport.to.spawn.middle_earth.error", Text.translatable("spawn."+spawnId.toTranslationKey()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;
    }

    private static int forceTeleportPlayerToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        Identifier spawnId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(FactionUtil.forceTeleportToSpawnMiddleEarthId(targetedPlayer, spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld(), spawnId);
            MutableText targetText = Text.translatable("command.me.teleport.to.spawn.middle_earth.success", Text.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
            MutableText sourceText = Text.translatable("command.me.teleport.player.to.spawn.middle_earth.success", targetedPlayer.getName(),Text.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("command.me.teleport.player.to.spawn.middle_earth.error", Text.translatable("spawn."+spawnId.toTranslationKey()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }

        return 0;
    }
    // endregion
}
