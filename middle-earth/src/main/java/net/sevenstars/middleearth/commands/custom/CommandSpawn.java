package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.sevenstars.middleearth.commands.suggestions.AllSpawnSuggestionProvider;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.factions.FactionUtil;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.utils.ModColors;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
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
        ServerPlayerEntity playerSource = context.getSource().getPlayer();
        PlayerDataService.OriginAggregate originAggregate = PlayerDataService.getOriginAggregateOrDefault(playerSource, playerSource.getWorld());
        BlockPos pos = originAggregate.origin();
        MutableText sourceText = Text.translatable("command.%s.get.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), pos.getX(), pos.getY(), pos.getZ());
        context.getSource().getPlayer().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));

        return 0;
    }

    private static int getPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData data = StateSaverAndLoader.getPlayerState(playerTarget);
        PlayerDataService.OriginAggregate originAggregate = PlayerDataService.getOriginAggregateOrDefault(playerTarget, playerTarget.getWorld());
        BlockPos pos = originAggregate.origin();
        MutableText sourceText = Text.translatable("command.%s.get.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), pos.getX(), pos.getY(), pos.getZ());
        context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));

        return 0;
    }

    private static int getSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        PlayerEntity playerSource = context.getSource().getPlayer();
        SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.getWorld());
        if(spawnData != null){
            BlockPos pos = spawnData.getBlockPos();
            MutableText sourceText = Text.translatable("command.%s.get.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Text.translatable("spawn."+ spawnData.getIdentifier().toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().getPlayer().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("command.%s.get.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        }
        return 0;

    }

    private static int getPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(!context.getSource().isExecutedByPlayer()) return 0;

        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(context, PLAYER);
        SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.getWorld());
        if(spawnData != null){
            BlockPos pos = spawnData.getBlockPos();
            MutableText sourceText = Text.translatable("command.%s.get.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), Text.translatable("spawn."+spawnData.getIdentifier().toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("command.%s.get.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        }
        return 0;
    }
    // endregion

    // region Setters
    private static int setSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayerEntity playerSource = context.getSource().getPlayer();

        Identifier spawnIdInput = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(!PlayerDataService.playerPassedOnboarding(playerSource)){
            MutableText sourceText = Text.translatable("command.%s.set.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.setSpawn(playerSource, playerSource.getWorld(), spawnIdInput)){
            SpawnData spawnData =  PlayerDataService.getPlayerSpawnData(playerSource, playerSource.getWorld());
            BlockPos pos =  spawnData.getBlockPos();
            if(pos != null) {
                if(ModDimensions.isInMiddleEarth(playerSource.getWorld())){
                    ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(ModDimensions.ME_WORLD_KEY, pos, 0, true);
                    playerSource.setSpawnPoint(respawn, true);
                }
                MutableText sourceText = Text.translatable("command.%s.set.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Text.translatable("spawn."+spawnIdInput.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                playerSource.sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                return 0;
            }
        }
        MutableText sourceText = Text.translatable("command.%s.set.spawn.middle_earth.no_spawn_found".formatted(MiddleEarth.MOD_ID), spawnIdInput.toString());
        playerSource.sendMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }

    private static int setPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(context, PLAYER);

        Identifier spawnIdInput = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(!PlayerDataService.playerPassedOnboarding(playerTarget)){
            MutableText sourceText = Text.translatable("command.%s.set.player.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.setSpawn(playerTarget, playerTarget.getWorld(), spawnIdInput)){
            SpawnData spawnData =  PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.getWorld());
            BlockPos pos =  spawnData.getBlockPos();
            if(pos != null) {
                if(ModDimensions.isInMiddleEarth(playerTarget.getWorld())){
                    ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(ModDimensions.ME_WORLD_KEY, pos, 0, true);
                    playerTarget.setSpawnPoint(respawn, true);
                }

                MutableText targetText = Text.translatable("command.%s.set.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Text.translatable("spawn."+spawnIdInput.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                playerTarget.sendMessage(targetText.withColor(ModColors.SUCCESS.color));
                MutableText sourceText = Text.translatable("command.%s.set.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(),Text.translatable("spawn."+spawnIdInput.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                return 0;
            }
        }
        MutableText sourceText = Text.translatable("command.%s.set.spawn.middle_earth.no_spawn_found".formatted(MiddleEarth.MOD_ID), spawnIdInput.toString());
        context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));


        return 0;
    }

    private static int setSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        BlockPos posInput = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);

        ServerPlayerEntity playerSource = context.getSource().getPlayer();

        if(PlayerDataService.setOrigin(playerSource, playerSource.getWorld(), DimensionTypes.OVERWORLD.getRegistry(), posInput)) {
            if(ModDimensions.isInOverworld(playerSource.getWorld())){
                ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(World.OVERWORLD, posInput, 0, true);
                playerSource.setSpawnPoint(respawn, true);
                return 0;
            }
        }
        MutableText sourceText = Text.translatable("command.%s.set.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
        return 0;
    }

    private static int setPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos posInput = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(context, PLAYER);

        PlayerDataService.setOrigin(playerTarget, playerTarget.getWorld(), DimensionTypes.OVERWORLD.getRegistry(), posInput);

        MutableText sourceText = Text.translatable("command.%s.set.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName() ,posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));

        MutableText targetText = Text.translatable("command.%s.set.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        playerTarget.sendMessage(targetText.withColor(ModColors.SUCCESS.color));

        return 0;
    }

    // endregion

    // region Resets
    private static int resetPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(context, PLAYER);
        BlockPos posInput = context.getSource().getServer().getOverworld().getSpawnPos();
        PlayerDataService.setOrigin(playerTarget, playerTarget.getWorld(), DimensionTypes.OVERWORLD.getRegistry(), posInput);

        MutableText sourceText = Text.translatable("command.%s.reset.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
        MutableText targetText = Text.translatable("command.%s.reset.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        playerTarget.sendMessage(targetText.withColor(ModColors.SUCCESS.color));
        return 0;
    }

    private static int resetSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayerEntity playerSource = context.getSource().getPlayer();
        PlayerDataService.resetOrigin(playerSource, playerSource.getWorld());
        PlayerDataService.OriginAggregate newOrigin = PlayerDataService.getOriginAggregateOrDefault(playerSource, playerSource.getWorld());
        MutableText sourceText = Text.translatable("command.%s.reset.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), newOrigin.origin().getX(), newOrigin.origin().getY(), newOrigin.origin().getZ());
        playerSource.sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
        return 0;
    }

    private static int resetPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(context, PLAYER);

        if(!PlayerDataService.playerPassedOnboarding(playerTarget)){
            MutableText sourceText = Text.translatable("command.%s.reset.player.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }
        if(PlayerDataService.resetSpawn(playerTarget, playerTarget.getWorld())){
            SpawnData data = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.getWorld());

            MutableText sourceText = Text.translatable("command.%s.reset.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(),
                    Text.translatable("spawn." + data.getIdentifier().toTranslationKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));

            MutableText targetText = Text.translatable("command.%s.reset.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                    Text.translatable("spawn." + data.getIdentifier().toTranslationKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            playerTarget.sendMessage(targetText.withColor(ModColors.SUCCESS.color));
            return 0;

        }

        return 0;
    }

    private static int resetSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayerEntity playerSource = context.getSource().getPlayer();
        if(!PlayerDataService.playerPassedOnboarding(playerSource)){
            MutableText sourceText = Text.translatable("command.%s.reset.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID));
            playerSource.sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.resetSpawn(playerSource, playerSource.getWorld())){
            SpawnData data = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.getWorld());

            MutableText sourceText = Text.translatable("command.%s.reset.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                    Text.translatable("spawn." + data.getIdentifier().toTranslationKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            playerSource.sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
        }
        return 0;
    }

    // endregion

    // region Teleports
    private static int teleportToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        ServerPlayerEntity playerSource = context.getSource().getPlayer();
        if(!PlayerDataService.playerPassedOnboarding(playerSource)){
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.getWorld());
            if(spawnData == null)
                return 0;

            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(playerSource, playerSource.getWorld());
            if(ModDimensions.isInOverworld(playerSource.getWorld()) && origin != null){
                PlayerDataService.setOrigin(playerSource, playerSource.getWorld(), DimensionTypes.OVERWORLD_ID, playerSource.getBlockPos());
            }
            Vector3i spawnCoordinates = spawnData.getWorldCoordinates();
            if(spawnCoordinates != null) {
                BlockPos pos = new BlockPos(spawnCoordinates.x, spawnCoordinates.y,spawnCoordinates.z);
                if(ModDimensions.isInMiddleEarth(playerSource.getWorld())){
                    ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(ModDimensions.ME_WORLD_KEY, pos, 0, true);
                    playerSource.setSpawnPoint(respawn, true);
                }
                ModDimensions.teleportPlayerToMe(playerSource, new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded);
                MutableText sourceText = Text.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                        Text.translatable("spawn."+ spawnData.getIdentifier().toTranslationKey()));
                context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                return 0;
            }

        }
        MutableText sourceText = Text.translatable("command.%s.teleport.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID));
        context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }

    private static int teleportPlayerToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(context, PLAYER);
        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        if(PlayerDataService.playerPassedOnboarding(playerTarget)){
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.getWorld());
            if(spawnData == null)
                return 0;

            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(playerTarget, playerTarget.getWorld());
            if(ModDimensions.isInOverworld(playerTarget.getWorld()) && origin != null){
                PlayerDataService.setOrigin(playerTarget, playerTarget.getWorld(), DimensionTypes.OVERWORLD_ID, playerTarget.getBlockPos());
            }
            Vector3i spawnCoordinates = spawnData.getWorldCoordinates();
            if(spawnCoordinates != null) {
                BlockPos pos = new BlockPos(spawnCoordinates.x, spawnCoordinates.y,spawnCoordinates.z);
                if(ModDimensions.isInMiddleEarth(playerTarget.getWorld())){
                    ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(ModDimensions.ME_WORLD_KEY, pos, 0, true);
                    playerTarget.setSpawnPoint(respawn, true);
                }
                ModDimensions.teleportPlayerToMe(playerTarget, new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded);
                MutableText sourceText = Text.translatable("command.%s.teleport.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), Text.translatable("spawn."+spawnData.getIdentifier().toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
                MutableText targetText = Text.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Text.translatable("spawn."+spawnData.getIdentifier().toTranslationKey()));
                playerTarget.sendMessage(targetText.withColor(ModColors.SUCCESS.color));
                return 0;
            }

        }
        MutableText sourceText = Text.translatable("command.%s.teleport.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
        context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }


    private static int teleportToSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        ServerPlayerEntity player = context.getSource().getPlayer();
        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableText sourceText = Text.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        }
        MutableText sourceText = Text.translatable("command.%s.teleport.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID));
        context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }

    private static int teleportPlayerToSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, PLAYER);

        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableText sourceText = Text.translatable("command.%s.teleport.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), player.getName(), player.getX(), player.getY(), player.getZ());
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        }
        MutableText sourceText = Text.translatable("command.%s.teleport.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), player.getName());
        context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }

    private static int forceTeleportToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        Identifier spawnId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(FactionUtil.forceTeleportToSpawnMiddleEarthId(context.getSource().getPlayer(), spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld(), spawnId);
            MutableText targetText = Text.translatable("command.%s.teleport.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Text.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(targetText.withColor(ModColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("command.%s.teleport.to.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID), Text.translatable("spawn."+spawnId.toTranslationKey()));
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        }
        return 0;
    }

    private static int forceTeleportPlayerToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        Identifier spawnId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(FactionUtil.forceTeleportToSpawnMiddleEarthId(targetedPlayer, spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld(), spawnId);
            MutableText targetText = Text.translatable("command.%s.teleport.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Text.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            targetedPlayer.sendMessage(targetText.withColor(ModColors.SUCCESS.color));
            MutableText sourceText = Text.translatable("command.%s.teleport.player.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName(),Text.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
        } else {
            MutableText sourceText = Text.translatable("command.%s.teleport.player.to.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID), Text.translatable("spawn."+spawnId.toTranslationKey()));
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        }

        return 0;
    }
    // endregion
}
