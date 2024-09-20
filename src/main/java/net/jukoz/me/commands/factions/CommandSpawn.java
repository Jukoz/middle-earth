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
import net.jukoz.me.exceptions.NoFactionException;
import net.jukoz.me.resources.StateSaverAndLoader;
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
                    .executes(CommandSpawn::getTargetSpawnCoordinates))))
                .then(literal(GET) // Without Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::getSpawnCoordinates)))));

        // [GET SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player()) // With Player Target
                        .then(literal(GET)
                        .then(literal(MIDDLE_EARTH)
                        .executes(CommandSpawn::getTargetMiddleEarthSpawn))))
                    .then(literal(GET) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .executes(CommandSpawn::getMiddleEarthSpawn)))));

        // [TP - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then(literal(TP) // With Player Target
                        .then(literal(MIDDLE_EARTH)
                        .then(argument(WELCOME, BoolArgumentType.bool())
                        .executes(CommandSpawn::teleportTargetToMiddleEarthSpawn)))))
                    .then(literal(TP) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .then(argument(WELCOME, BoolArgumentType.bool())
                    .executes(CommandSpawn::teleportToMiddleEarthSpawn))))));

        // [TP - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(TP) // With Player Target
                    .then(literal(OVERWORLD)
                    .executes(CommandSpawn::teleportTargetToOverworldSpawn))))
                .then(literal(TP) // Without Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::teleportToOverworldSpawn)))));

        // [SET - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                        .then(argument(PLAYER, EntityArgumentType.player())
                                .then(literal(SET)
                                .then(literal(OVERWORLD) // With Player Target
                                .then(argument(OVERWORLD_COORD, BlockPosArgumentType.blockPos())
                                .executes(CommandSpawn::setOverworldOriginForTarget)))))
                        .then(literal(SET) // Without Target
                        .then(literal(OVERWORLD)
                        .then(argument(OVERWORLD_COORD, BlockPosArgumentType.blockPos())
                        .executes(CommandSpawn::setOverworldOrigin))))));

        // [SET - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then(literal(SET)
                        .then(literal(MIDDLE_EARTH) // With Player Target
                        .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                        .executes(CommandSpawn::setMiddleEarthSpawnForTarget)))))
                    .then(literal(SET) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandSpawn::setMiddleEarthSpawn))))));

        // [RESET - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
            .requires(source -> source.hasPermissionLevel(2)) // Require OP
            .then(literal(SPAWN_BASE_COMMAND)
            .then(argument(PLAYER, EntityArgumentType.player())
                .then(literal(RESET) // With Player Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::resetTargetOverworldSpawn))))
            .then(literal(RESET) // Without Target
            .then(literal(OVERWORLD)
            .executes(CommandSpawn::resetOverworldSpawn)))));

        // [RESET - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(RESET) // With Player Target
                    .then(literal(MIDDLE_EARTH)
                    .executes(CommandSpawn::resetTargetMiddleEarthSpawn))))
                .then(literal(RESET) // Without Target
                .then(literal(MIDDLE_EARTH)
                .executes(CommandSpawn::resetMiddleEarthSpawn)))));

        // [TP TO - SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(TP)
                    .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(TO)
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllSpawnSuggestionProvider())
                    .executes(CommandSpawn::exploringTeleportToSpawnForTarget))))
                .then(literal(TO) // Without Target
                .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                .suggests(new AllSpawnSuggestionProvider())
                .executes(CommandSpawn::exploringTeleportToSpawn)))));
    }

    // region Getters
    private static int getSpawnCoordinates(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        PlayerData data = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());
        if(data.getOverworldSpawnCoordinates() != null){
            BlockPos pos = data.getOverworldSpawnCoordinates();
            MutableText sourceText = Text.translatable("Your overworld spawn is [%s, %s, %s](xyz)".formatted(pos.getX(), pos.getY(), pos.getZ()));
            context.getSource().getPlayer().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            BlockPos pos = context.getSource().getServer().getOverworld().getSpawnPos();
            MutableText sourceText = Text.translatable("No overworld spawn assigned, default value is [%s, %s, %s](xyz)".formatted(pos.getX(), pos.getY(), pos.getZ()));
            context.getSource().getPlayer().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;
    }

    private static int getTargetSpawnCoordinates(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        if(data.getOverworldSpawnCoordinates() != null){
            BlockPos pos = data.getOverworldSpawnCoordinates();
            MutableText sourceText = Text.translatable("Overworld spawn for %s is [%s, %s, %s](xyz)".formatted(targetedPlayer.getName(), pos.getX(), pos.getY(), pos.getZ()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            BlockPos pos = context.getSource().getServer().getOverworld().getSpawnPos();
            MutableText sourceText = Text.translatable("No overworld spawn assigned for %s, default value is [%s, %s, %s](xyz)".formatted(targetedPlayer.getName(), pos.getX(), pos.getY(), pos.getZ()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;
    }

    private static int getMiddleEarthSpawn(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        PlayerData data = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());
        if(data.hasAffilition()){
            Identifier spawnId = data.getAffiliationData().spawnId;
            MutableText sourceText = Text.translatable("command.me.spawn.middle_earth.get.source",
                    Text.translatable("spawn."+spawnId.toTranslationKey()));
            context.getSource().getPlayer().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("No Middle-earth spawn assigned");
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;

    }

    private static int getTargetMiddleEarthSpawn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(!context.getSource().isExecutedByPlayer()) return 0;

        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        if(data.hasAffilition()){
            Identifier spawnId = data.getAffiliationData().spawnId;
            MutableText sourceText = Text.translatable("command.me.spawn.middle_earth.get.target", targetedPlayer.getName(),
                    Text.translatable("spawn."+spawnId.toTranslationKey()));

            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("No Middle-earth spawn assigned for %s".formatted(targetedPlayer.getName()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }
        return 0;
    }
    // endregion

    // region Setters
    private static int setMiddleEarthSpawn(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayerEntity targetedPlayer = context.getSource().getPlayer();

        Identifier foundId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);
        PlayerData playerData = StateSaverAndLoader.getPlayerState(targetedPlayer);

        if(!playerData.hasAffilition()){
            MutableText sourceText = Text.translatable("You cannot select a spawn if you have joined no faction.");
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            return 0;
        }

        try{
            if(playerData.setMiddleEarthSpawnId(foundId)){
                Faction faction = FactionLookup.getFactionById(playerData.getAffiliationData().faction);
                if(faction != null){
                    boolean isDynamic = faction.getSpawnData().getSpawnList().get(foundId);
                    if(isDynamic){
                        Vector2d coords = faction.getSpawnData().findDynamicSpawn(foundId);
                        Vector3i spawnCoordinates =  ModDimensions.getDimensionHeight((int) coords.x, (int) coords.y);
                        BlockPos newSpawn = new BlockPos(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z);
                        targetedPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, newSpawn, 0, true, true);
                    } else{
                        Vec3d coords = faction.getSpawnData().findCustomSpawn(foundId);
                        BlockPos newSpawn = new BlockPos((int) coords.x, (int) coords.y, (int) coords.z);
                        targetedPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, newSpawn, 0, true, true);
                    }
                    MutableText sourceText = Text.translatable("Your Middle-earth spawn have been set to %s".formatted(playerData.getAffiliationData().spawnId));
                    context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                    return 0;
                }
            }
            MutableText sourceText = Text.translatable("Couldn't find the spawn [%s]".formatted(foundId));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        } catch (FactionIdentifierException e){
            MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, playerData.getAffiliationData().faction.toString());
            context.getSource().sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
        }

        return 0;
    }

    private static int setMiddleEarthSpawnForTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);

        Identifier foundId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);
        PlayerData playerData = StateSaverAndLoader.getPlayerState(targetedPlayer);

        if(!playerData.hasAffilition()){
            MutableText sourceText = Text.translatable("You cannot assign a spawn when %s have not joined any faction.".formatted(targetedPlayer.getName()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            return 0;
        }

        try{
            if(playerData.setMiddleEarthSpawnId(foundId)){
                Faction faction = FactionLookup.getFactionById(playerData.getAffiliationData().faction);
                if(faction != null){
                    boolean isDynamic = faction.getSpawnData().getSpawnList().get(foundId);
                    if(isDynamic){
                        Vector2d coords = faction.getSpawnData().findDynamicSpawn(foundId);
                        Vector3i spawnCoordinates =  ModDimensions.getDimensionHeight((int) coords.x, (int) coords.y);
                        BlockPos newSpawn = new BlockPos(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z);
                        targetedPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, newSpawn, 0, true, true);
                    } else{
                        Vec3d coords = faction.getSpawnData().findCustomSpawn(foundId);
                        BlockPos newSpawn = new BlockPos((int) coords.x, (int) coords.y, (int) coords.z);
                        targetedPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, newSpawn, 0, true, true);
                    }

                    MutableText sourceText = Text.translatable("%s Middle-earth spawn have been set to %s".formatted(targetedPlayer.getName() ,playerData.getAffiliationData().spawnId));
                    context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                    MutableText targetText = Text.translatable("Your Middle-earth spawn have been set to %s".formatted(playerData.getAffiliationData().spawnId));
                    targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
                    return 0;
                }
            }
            MutableText sourceText = Text.translatable("Couldn't find the spawn [%s]".formatted(foundId));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        } catch (FactionIdentifierException e){
            MutableText errorMessage = Text.translatable(FactionIdentifierException.KEY, playerData.getAffiliationData().faction.toString());
            context.getSource().sendMessage(errorMessage.withColor(CommandColors.ALERT.color));
        }

        return 0;
    }

    private static int setOverworldOrigin(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        BlockPos pos = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);

        PlayerData data = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());
        data.setOverworldSpawn(pos);

        MutableText sourceText = Text.translatable("Your new overworld spawn is [%s, %s, %s](xyz)".formatted(pos.getX(), pos.getY(), pos.getZ()));
        context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));

        return 0;
    }

    private static int setOverworldOriginForTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        BlockPos pos = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);

        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        data.setOverworldSpawn(pos);

        MutableText sourceText = Text.translatable("%s new overworld spawn is [%s, %s, %s](xyz)".formatted(targetedPlayer.getName(), pos.getX(), pos.getY(), pos.getZ()));
        context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));

        MutableText targetText = Text.translatable("Your new overworld spawn is [%s, %s, %s](xyz)".formatted(pos.getX(), pos.getY(), pos.getZ()));
        targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));

        return 0;
    }

    // endregion

    // region Resets
    private static int resetTargetOverworldSpawn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData data = StateSaverAndLoader.getPlayerState(targetedPlayer);
        BlockPos pos = context.getSource().getServer().getOverworld().getSpawnPos();
        data.setOverworldSpawn(pos);
        MutableText targetText = Text.translatable("%s overworld return spawn have been reset to [%s, %s, %s](xyz)".formatted(pos.getX(), pos.getY(), pos.getZ()));
        context.getSource().sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
        return 0;
    }

    private static int resetOverworldSpawn(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        PlayerData data = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());
        BlockPos pos = context.getSource().getServer().getOverworld().getSpawnPos();
        data.setOverworldSpawn(pos);
        MutableText targetText = Text.translatable("Your overworld return spawn have been reset to [%s, %s, %s](xyz)".formatted(pos.getX(), pos.getY(), pos.getZ()));
        context.getSource().sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
        return 0;
    }

    private static int resetTargetMiddleEarthSpawn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData playerData = StateSaverAndLoader.getPlayerState(targetedPlayer);

        if(!playerData.hasAffilition()){
            MutableText sourceText = Text.translatable("You cannot reset the Middle-earth spawn when %s have not joined any faction.".formatted(targetedPlayer.getName()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            return 0;
        }

        try{
            Faction faction = FactionLookup.findFactionById(playerData.getAffiliationData().faction);
            if(faction != null){
                Identifier foundSpawn = faction.getSpawnData().getDefaultSpawn();
                boolean isDynamic = faction.getSpawnData().getSpawnList().get(foundSpawn);
                if(isDynamic){
                    Vector2d coords = faction.getSpawnData().findDynamicSpawn(foundSpawn);
                    Vector3i spawnCoordinates =  ModDimensions.getDimensionHeight((int) coords.x, (int) coords.y);
                    BlockPos newSpawn = new BlockPos(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z);
                    targetedPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, newSpawn, 0, true, true);
                } else{
                    Vec3d coords = faction.getSpawnData().findCustomSpawn(foundSpawn);
                    BlockPos newSpawn = new BlockPos((int) coords.x, (int) coords.y, (int) coords.z);
                    targetedPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, newSpawn, 0, true, true);
                }
                playerData.setMiddleEarthSpawnId(foundSpawn);

                MutableText sourceText = Text.translatable("%s Middle-earth spawn have been reset to %s".formatted(targetedPlayer.getName() ,playerData.getAffiliationData().spawnId));
                context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                MutableText targetText = Text.translatable("Your Middle-earth spawn have been reset to %s".formatted(playerData.getAffiliationData().spawnId));
                targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
            }
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private static int resetMiddleEarthSpawn(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        PlayerData playerData = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());

        if(!playerData.hasAffilition()){
            MutableText sourceText = Text.translatable("You cannot reset your Middle-earth spawn when you have not joined any faction.");
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
            return 0;
        }

        try{
            Faction faction = FactionLookup.findFactionById(playerData.getAffiliationData().faction);
            if(faction != null){
                Identifier foundSpawn = faction.getSpawnData().getDefaultSpawn();
                BlockPos newSpawn = faction.getSpawnData().getSpawnBlockPos(foundSpawn);
                if(newSpawn != null){
                    context.getSource().getPlayer().setSpawnPoint(ModDimensions.ME_WORLD_KEY, newSpawn, 0, true, true);
                    playerData.setMiddleEarthSpawnId(foundSpawn);

                    MutableText sourceText = Text.translatable("Your Middle-earth spawn have been reset to %s".formatted(playerData.getAffiliationData().spawnId));
                    context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                } else {
                    MutableText sourceText = Text.translatable("There was an error while updating the spawn");
                    context.getSource().sendMessage(sourceText.withColor(CommandColors.ALERT.color));
                }
            }
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    // endregion

    // region Teleports
    private static int teleportToMiddleEarthSpawn(CommandContext<ServerCommandSource> context) {
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
                if(spawnCoordinates != null) {
                    ModDimensions.teleportPlayerToMe(player, new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded);
                    MutableText sourceText = Text.translatable("You got teleported to the Middle-earth spawn");
                    context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                    return 0;
                }
            }
        }
        MutableText sourceText = Text.translatable("You have no Middle-earth spawn assigned");
        context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        return 0;
    }

    private static int teleportTargetToMiddleEarthSpawn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
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
                if(spawnCoordinates != null) {
                    ModDimensions.teleportPlayerToMe(player, new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded);
                    MutableText sourceText = Text.translatable("%s got teleported to the Middle-earth spawn".formatted(player.getName()));
                    context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
                    return 0;
                }
            }
        }
        MutableText sourceText = Text.translatable("%s has no Middle-earth spawn assigned".formatted(player.getName()));
        context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        return 0;
    }


    private static int teleportToOverworldSpawn(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        ServerPlayerEntity player = context.getSource().getPlayer();
        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableText sourceText = Text.translatable("You got teleported to the overworld spawn");
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
            return 0;
        }
        MutableText sourceText = Text.translatable("There was an error while teleporting you to the overworld spawn");
        context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        return 0;
    }

    private static int teleportTargetToOverworldSpawn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, PLAYER);

        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableText sourceText = Text.translatable("%s got teleported to the overworld spawn".formatted(player.getName()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
            return 0;
        }
        MutableText sourceText = Text.translatable("There was an error while teleporting %s to the overworld spawn".formatted(player.getName()));
        context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        return 0;
    }

    private static int exploringTeleportToSpawn(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        Identifier spawnId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(FactionUtil.exploringTeleportToSpawnId(context.getSource().getPlayer(), spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(spawnId);
            MutableText targetText = Text.translatable("You got teleported to %s at [%s, %s, %s](xyz)".formatted(spawnId, pos.getX(), pos.getY(), pos.getZ()));
            context.getSource().sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("You couldn't be teleported to %s".formatted(spawnId));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }

        return 0;
    }

    private static int exploringTeleportToSpawnForTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        Identifier spawnId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(FactionUtil.exploringTeleportToSpawnId(targetedPlayer, spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(spawnId);
            MutableText sourceText = Text.translatable("%s got teleported to %s at [%s, %s, %s](xyz)".formatted(targetedPlayer.getName(),  spawnId,pos.getX(), pos.getY(), pos.getZ()));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.SUCCESS.color));
            MutableText targetText = Text.translatable("You got teleported to %s at [%s, %s, %s](xyz)".formatted(spawnId, pos.getX(), pos.getY(), pos.getZ()));
            targetedPlayer.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("%s couldn't be teleported to %s".formatted(targetedPlayer.getName(),  spawnId));
            context.getSource().sendMessage(sourceText.withColor(CommandColors.WARNING.color));
        }

        return 0;
    }
    // endregion
}
