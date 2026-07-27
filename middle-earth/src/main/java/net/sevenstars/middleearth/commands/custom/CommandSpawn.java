package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.phys.Vec3;
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

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

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

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess, Commands.CommandSelection registrationEnvironment) {
        // [GET OVERWORLD SPAWN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgument.player()) // With Player Target
                    .then(literal(GET)
                    .then(literal(OVERWORLD)
                    .executes(CommandSpawn::getPlayerSpawnOverworld))))
                .then(literal(GET) // Without Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::getSpawnOverworld)))));

        // [GET SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgument.player()) // With Player Target
                        .then(literal(GET)
                        .then(literal(MIDDLE_EARTH)
                        .executes(CommandSpawn::getPlayerSpawnMiddleEarth))))
                    .then(literal(GET) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .executes(CommandSpawn::getSpawnMiddleEarth)))));

        // [TP - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgument.player())
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
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgument.player())
                    .then(literal(TP) // With Player Target
                    .then(literal(OVERWORLD)
                    .executes(CommandSpawn::teleportPlayerToSpawnOverworld))))
                .then(literal(TP) // Without Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::teleportToSpawnOverworld)))));

        // [SET - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                        .then(argument(PLAYER, EntityArgument.player())
                                .then(literal(SET)
                                .then(literal(OVERWORLD) // With Player Target
                                .then(argument(OVERWORLD_COORD, BlockPosArgument.blockPos())
                                .executes(CommandSpawn::setPlayerSpawnOverworld)))))
                        .then(literal(SET) // Without Target
                        .then(literal(OVERWORLD)
                        .then(argument(OVERWORLD_COORD, BlockPosArgument.blockPos())
                        .executes(CommandSpawn::setSpawnOverworld))))));

        // [SET - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgument.player())
                        .then(literal(SET)
                        .then(literal(MIDDLE_EARTH) // With Player Target
                        .then(argument(SPAWN_ID, ResourceLocationArgument.id())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                        .executes(CommandSpawn::setPlayerSpawnMiddleEarth)))))
                    .then(literal(SET) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .then(argument(SPAWN_ID, ResourceLocationArgument.id())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandSpawn::setSpawnMiddleEarth))))));

        // [RESET - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
            .requires(source -> source.hasPermission(2)) // Require OP
            .then(literal(SPAWN_BASE_COMMAND)
            .then(argument(PLAYER, EntityArgument.player())
                .then(literal(RESET) // With Player Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::resetPlayerSpawnOverworld))))
            .then(literal(RESET) // Without Target
            .then(literal(OVERWORLD)
            .executes(CommandSpawn::resetSpawnOverworld)))));

        // [RESET - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgument.player())
                    .then(literal(RESET) // With Player Target
                    .then(literal(MIDDLE_EARTH)
                    .executes(CommandSpawn::resetPlayerSpawnMiddleEarth))))
                .then(literal(RESET) // Without Target
                .then(literal(MIDDLE_EARTH)
                .executes(CommandSpawn::resetSpawnMiddleEarth)))));

        // [TP TO - SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2)) // Require OP
                .then(literal(TP)
                    .then(argument(PLAYER, EntityArgument.player())
                    .then(literal(TO)
                    .then(argument(SPAWN_ID, ResourceLocationArgument.id())
                    .suggests(new AllSpawnSuggestionProvider())
                    .executes(CommandSpawn::forceTeleportPlayerToSpawnMiddleEarth))))
                .then(literal(TO) // Without Target
                .then(argument(SPAWN_ID, ResourceLocationArgument.id())
                .suggests(new AllSpawnSuggestionProvider())
                .executes(CommandSpawn::forceTeleportToSpawnMiddleEarth)))));
    }

    // region Getters
    private static int getSpawnOverworld(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayer playerSource = context.getSource().getPlayer();
        PlayerDataService.OriginAggregate originAggregate = PlayerDataService.getOriginAggregateOrDefault(playerSource, playerSource.level());
        BlockPos pos = originAggregate.origin();
        MutableComponent sourceText = Component.translatable("command.%s.get.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), pos.getX(), pos.getY(), pos.getZ());
        context.getSource().getPlayer().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));

        return 1;
    }

    private static int getPlayerSpawnOverworld(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);
        PlayerDataService.OriginAggregate originAggregate = PlayerDataService.getOriginAggregateOrDefault(playerTarget, playerTarget.level());
        BlockPos pos = originAggregate.origin();
        MutableComponent sourceText = Component.translatable("command.%s.get.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), pos.getX(), pos.getY(), pos.getZ());
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));

        return 1;
    }

    private static int getSpawnMiddleEarth(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;
        Player playerSource = context.getSource().getPlayer();
        SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.level());
        if(spawnData != null){
            BlockPos pos = spawnData.getBlockPos();
            MutableComponent sourceText = Component.translatable("command.%s.get.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+ spawnData.getIdentifier().toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().getPlayer().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 1;
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.get.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        }
        return 0;

    }

    private static int getPlayerSpawnMiddleEarth(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if(!context.getSource().isPlayer()) return 0;

        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);
        SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.level());
        if(spawnData != null){
            BlockPos pos = spawnData.getBlockPos();
            MutableComponent sourceText = Component.translatable("command.%s.get.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), Component.translatable("spawn."+spawnData.getIdentifier().toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 1;
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.get.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        }
        return 0;
    }
    // endregion

    // region Setters
    private static int setSpawnMiddleEarth(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayer playerSource = context.getSource().getPlayer();

        ResourceLocation spawnIdInput = ResourceLocationArgument.getId(context, SPAWN_ID);

        if(!PlayerDataService.playerPassedOnboarding(playerSource)){
            MutableComponent sourceText = Component.translatable("command.%s.set.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.setSpawn(playerSource, playerSource.level(), spawnIdInput)){
            SpawnData spawnData =  PlayerDataService.getPlayerSpawnData(playerSource, playerSource.level());
            BlockPos pos =  spawnData.getBlockPos();
            if(pos != null) {
                if(ModDimensions.isInMiddleEarth(playerSource.level())){
                    playerSource.setRespawnPosition(ModDimensions.ME_WORLD_KEY, pos, 0.0F, true, true);
                }
                MutableComponent sourceText = Component.translatable("command.%s.set.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnIdInput.toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
                playerSource.sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
                return 1;
            }
        }
        MutableComponent sourceText = Component.translatable("command.%s.set.spawn.middle_earth.no_spawn_found".formatted(MiddleEarth.MOD_ID), spawnIdInput.toString());
        playerSource.sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }

    private static int setPlayerSpawnMiddleEarth(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);

        ResourceLocation spawnIdInput = ResourceLocationArgument.getId(context, SPAWN_ID);

        if(!PlayerDataService.playerPassedOnboarding(playerTarget)){
            MutableComponent sourceText = Component.translatable("command.%s.set.player.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.setSpawn(playerTarget, playerTarget.level(), spawnIdInput)){
            SpawnData spawnData =  PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.level());
            BlockPos pos =  spawnData.getBlockPos();
            if(pos != null) {
                if(ModDimensions.isInMiddleEarth(playerTarget.level())){
                    playerTarget.setRespawnPosition(ModDimensions.ME_WORLD_KEY, pos, 0.0F, true, true);
                }

                MutableComponent targetText = Component.translatable("command.%s.set.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnIdInput.toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
                playerTarget.sendSystemMessage(targetText.withColor(ModColors.SUCCESS.color));
                MutableComponent sourceText = Component.translatable("command.%s.set.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(),Component.translatable("spawn."+spawnIdInput.toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
                context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
                return 1;
            }
        }
        MutableComponent sourceText = Component.translatable("command.%s.set.spawn.middle_earth.no_spawn_found".formatted(MiddleEarth.MOD_ID), spawnIdInput.toString());
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));


        return 0;
    }

    private static int setSpawnOverworld(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;
        BlockPos posInput = BlockPosArgument.getBlockPos(context, OVERWORLD_COORD);

        ServerPlayer playerSource = context.getSource().getPlayer();

        if(!PlayerDataService.setOrigin(playerSource, playerSource.level(), BuiltinDimensionTypes.OVERWORLD.location(), posInput)) {
            return 0;
        }
        if(ModDimensions.isInOverworld(playerSource.level())){
            playerSource.setRespawnPosition(Level.OVERWORLD, posInput, 0.0F, true, true);
        }
        MutableComponent sourceText = Component.translatable("command.%s.set.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
        return 1;
    }

    private static int setPlayerSpawnOverworld(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        BlockPos posInput = BlockPosArgument.getBlockPos(context, OVERWORLD_COORD);
        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);

        if(!PlayerDataService.setOrigin(playerTarget, playerTarget.level(), BuiltinDimensionTypes.OVERWORLD.location(), posInput)) {
            return 0;
        }
        if(ModDimensions.isInOverworld(playerTarget.level())) {
            playerTarget.setRespawnPosition(Level.OVERWORLD, posInput, 0.0F, true, true);
        }

        MutableComponent sourceText = Component.translatable("command.%s.set.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName() ,posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));

        MutableComponent targetText = Component.translatable("command.%s.set.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        playerTarget.sendSystemMessage(targetText.withColor(ModColors.SUCCESS.color));

        return 1;
    }

    // endregion

    // region Resets
    private static int resetPlayerSpawnOverworld(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);
        BlockPos posInput = context.getSource().getServer().overworld().getSharedSpawnPos();
        if(!PlayerDataService.setOrigin(playerTarget, playerTarget.level(), BuiltinDimensionTypes.OVERWORLD.location(), posInput)) {
            return 0;
        }
        if(ModDimensions.isInOverworld(playerTarget.level())) {
            playerTarget.setRespawnPosition(Level.OVERWORLD, posInput, 0.0F, true, true);
        }

        MutableComponent sourceText = Component.translatable("command.%s.reset.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
        MutableComponent targetText = Component.translatable("command.%s.reset.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        playerTarget.sendSystemMessage(targetText.withColor(ModColors.SUCCESS.color));
        return 1;
    }

    private static int resetSpawnOverworld(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayer playerSource = context.getSource().getPlayer();
        if(!PlayerDataService.resetOrigin(playerSource, playerSource.level())) {
            return 0;
        }
        PlayerDataService.OriginAggregate newOrigin = PlayerDataService.getOriginAggregateOrDefault(playerSource, playerSource.level());
        if(ModDimensions.isInOverworld(playerSource.level())) {
            playerSource.setRespawnPosition(Level.OVERWORLD, newOrigin.origin(), 0.0F, true, true);
        }
        MutableComponent sourceText = Component.translatable("command.%s.reset.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), newOrigin.origin().getX(), newOrigin.origin().getY(), newOrigin.origin().getZ());
        playerSource.sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
        return 1;
    }

    private static int resetPlayerSpawnMiddleEarth(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);

        if(!PlayerDataService.playerPassedOnboarding(playerTarget)){
            MutableComponent sourceText = Component.translatable("command.%s.reset.player.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }
        if(PlayerDataService.resetSpawn(playerTarget, playerTarget.level())){
            SpawnData data = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.level());

            MutableComponent sourceText = Component.translatable("command.%s.reset.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(),
                    Component.translatable("spawn." + data.getIdentifier().toLanguageKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));

            MutableComponent targetText = Component.translatable("command.%s.reset.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                    Component.translatable("spawn." + data.getIdentifier().toLanguageKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            playerTarget.sendSystemMessage(targetText.withColor(ModColors.SUCCESS.color));
            return 1;

        }

        return 0;
    }

    private static int resetSpawnMiddleEarth(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayer playerSource = context.getSource().getPlayer();
        if(!PlayerDataService.playerPassedOnboarding(playerSource)){
            MutableComponent sourceText = Component.translatable("command.%s.reset.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID));
            playerSource.sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.resetSpawn(playerSource, playerSource.level())){
            SpawnData data = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.level());

            MutableComponent sourceText = Component.translatable("command.%s.reset.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                    Component.translatable("spawn." + data.getIdentifier().toLanguageKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            playerSource.sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 1;
        }
        return 0;
    }

    // endregion

    // region Teleports
    private static int teleportToSpawnMiddleEarth(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;

        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        ServerPlayer playerSource = context.getSource().getPlayer();
        if(PlayerDataService.playerPassedOnboarding(playerSource)){
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.level());
            if(spawnData == null)
                return 0;

            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(playerSource, playerSource.level());
            if(ModDimensions.isInOverworld(playerSource.level()) && origin != null){
                PlayerDataService.setOrigin(playerSource, playerSource.level(), BuiltinDimensionTypes.OVERWORLD.location(), playerSource.blockPosition());
            }
            Vector3i spawnCoordinates = spawnData.getWorldCoordinates();
            if(spawnCoordinates != null) {
                BlockPos pos = new BlockPos(spawnCoordinates.x, spawnCoordinates.y,spawnCoordinates.z);
                if(ModDimensions.isInMiddleEarth(playerSource.level())){
                    playerSource.setRespawnPosition(ModDimensions.ME_WORLD_KEY, pos, 0.0F, true, true);
                }
                if (!ModDimensions.teleportPlayerToMe(playerSource, new Vec3(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded)) {
                    return 0;
                }
                MutableComponent sourceText = Component.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                        Component.translatable("spawn."+ spawnData.getIdentifier().toLanguageKey()));
                context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
                return 1;
            }

        }
        MutableComponent sourceText = Component.translatable("command.%s.teleport.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID));
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }

    private static int teleportPlayerToSpawnMiddleEarth(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);
        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        if(PlayerDataService.playerPassedOnboarding(playerTarget)){
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.level());
            if(spawnData == null)
                return 0;

            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(playerTarget, playerTarget.level());
            if(ModDimensions.isInOverworld(playerTarget.level()) && origin != null){
                PlayerDataService.setOrigin(playerTarget, playerTarget.level(), BuiltinDimensionTypes.OVERWORLD.location(), playerTarget.blockPosition());
            }
            Vector3i spawnCoordinates = spawnData.getWorldCoordinates();
            if(spawnCoordinates != null) {
                BlockPos pos = new BlockPos(spawnCoordinates.x, spawnCoordinates.y,spawnCoordinates.z);
                if(ModDimensions.isInMiddleEarth(playerTarget.level())){
                    playerTarget.setRespawnPosition(ModDimensions.ME_WORLD_KEY, pos, 0.0F, true, true);
                }
                if (!ModDimensions.teleportPlayerToMe(playerTarget, new Vec3(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded)) {
                    return 0;
                }
                MutableComponent sourceText = Component.translatable("command.%s.teleport.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), Component.translatable("spawn."+spawnData.getIdentifier().toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
                context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
                MutableComponent targetText = Component.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnData.getIdentifier().toLanguageKey()));
                playerTarget.sendSystemMessage(targetText.withColor(ModColors.SUCCESS.color));
                return 1;
            }

        }
        MutableComponent sourceText = Component.translatable("command.%s.teleport.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }


    private static int teleportToSpawnOverworld(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;

        ServerPlayer player = context.getSource().getPlayer();
        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableComponent sourceText = Component.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 1;
        }
        MutableComponent sourceText = Component.translatable("command.%s.teleport.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID));
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }

    private static int teleportPlayerToSpawnOverworld(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, PLAYER);

        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableComponent sourceText = Component.translatable("command.%s.teleport.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), player.getName(), player.getX(), player.getY(), player.getZ());
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 1;
        }
        MutableComponent sourceText = Component.translatable("command.%s.teleport.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), player.getName());
        context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }

    private static int forceTeleportToSpawnMiddleEarth(CommandContext<CommandSourceStack> context) {
        if(!context.getSource().isPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ResourceLocation spawnId = ResourceLocationArgument.getId(context, SPAWN_ID);

        if(FactionUtil.forceTeleportToSpawnMiddleEarthId(context.getSource().getPlayer(), spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getLevel(), spawnId);
            MutableComponent targetText = Component.translatable("command.%s.teleport.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnId.toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendSystemMessage(targetText.withColor(ModColors.SUCCESS.color));
            return 1;
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.teleport.to.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnId.toLanguageKey()));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        }
        return 0;
    }

    private static int forceTeleportPlayerToSpawnMiddleEarth(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer targetedPlayer = EntityArgument.getPlayer(context, PLAYER);
        ResourceLocation spawnId = ResourceLocationArgument.getId(context, SPAWN_ID);

        if(FactionUtil.forceTeleportToSpawnMiddleEarthId(targetedPlayer, spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getLevel(), spawnId);
            MutableComponent targetText = Component.translatable("command.%s.teleport.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnId.toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
            targetedPlayer.sendSystemMessage(targetText.withColor(ModColors.SUCCESS.color));
            MutableComponent sourceText = Component.translatable("command.%s.teleport.player.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName(),Component.translatable("spawn."+spawnId.toLanguageKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 1;
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.teleport.player.to.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnId.toLanguageKey()));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
        }

        return 0;
    }
    // endregion
}
