package net.sevenstars.middleearth.world.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.TicketType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.registries.RegistryAliasesME;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.factions.FactionUtil;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import net.sevenstars.middleearth.world.map.MiddleEarthMapUtils;
import net.sevenstars.middleearth.world.biomes.surface.ModBiomeSource;
import net.sevenstars.api.registries.RegistrationBridge;
import org.joml.Vector3i;

import java.util.Optional;

public class ModDimensions {
    private static final int NO_SAFE_Y = Integer.MIN_VALUE;
    private static final int SAFE_VERTICAL_SEARCH_UP = 16;
    private static final int SAFE_VERTICAL_SEARCH_DOWN = 32;
    private static final EntityDimensions DEFAULT_PLAYER_DIMENSIONS =
            EntityDimensions.scalable(0.6F, 1.8F);

    public static ResourceLocation ME_DIMENSION_ID = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "middle_earth");
    public static ResourceLocation OW_DIMENSION_ID = ResourceLocation.parse("overworld");

    public static final ResourceKey<LevelStem> ME_DIMENSION_KEY =
            ResourceKey.create(Registries.LEVEL_STEM, ME_DIMENSION_ID);

    public static ResourceKey<Level> ME_WORLD_KEY =
            ResourceKey.create(Registries.DIMENSION, ME_DIMENSION_KEY.location());

    public static final ResourceKey<LevelStem> OW_DIMENSION_KEY =
            ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.parse("overworld"));

    public static ResourceKey<Level> OW_WORLD_KEY =
            ResourceKey.create(Registries.DIMENSION, OW_DIMENSION_KEY.location());

    public static void register() {
        RegistrationBridge.register(
                BuiltInRegistries.BIOME_SOURCE,
                MiddleEarth.of("middle_earth_biome_source"),
                ModBiomeSource.CODEC
        );
        RegistrationBridge.register(
                BuiltInRegistries.CHUNK_GENERATOR,
                ME_DIMENSION_ID,
                MiddleEarthChunkGenerator.CODEC
        );
        ME_WORLD_KEY = ResourceKey.create(Registries.DIMENSION, ME_DIMENSION_ID);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.CHUNK_GENERATOR, ME_DIMENSION_ID.getPath()));

        MiddleEarth.LOGGER.logDebugMsg("Registering ModDimensions for " + MiddleEarth.MOD_ID);
    }

    public static boolean teleportPlayerToMe(Player player, Vec3 coordinates, boolean setSpawnPoint, boolean welcomeNeeded){
        if (player.level().isClientSide() || !(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }

        ServerLevel serverWorld = serverPlayer.getServer().getLevel(ME_WORLD_KEY);
        if (serverWorld == null) {
            MiddleEarth.LOGGER.logError("Middle-earth dimension is not loaded; cannot teleport player.");
            return false;
        }

        Optional<Vec3> safeTarget = findSafeMiddleEarthTeleportTarget(
                serverWorld,
                coordinates,
                serverPlayer
        );
        if (safeTarget.isEmpty()) {
            MiddleEarth.LOGGER.logError("No safe Middle-earth landing position was found; teleport cancelled.");
            return false;
        }
        return completeMiddleEarthTeleport(
                serverPlayer,
                serverWorld,
                safeTarget.get(),
                setSpawnPoint,
                welcomeNeeded
        );
    }

    public static boolean teleportPlayerToMeReturnPoint(
            Player player,
            BlockPos returnPos,
            boolean welcomeNeeded
    ) {
        if (player.level().isClientSide() || !(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }
        ServerLevel serverWorld = serverPlayer.getServer().getLevel(ME_WORLD_KEY);
        if (serverWorld == null) {
            return false;
        }
        Optional<Vec3> safeTarget = findSafeMiddleEarthReturnTarget(
                serverWorld,
                returnPos,
                serverPlayer
        );
        return safeTarget.isPresent()
                && completeMiddleEarthTeleport(
                        serverPlayer,
                        serverWorld,
                        safeTarget.get(),
                        true,
                        welcomeNeeded
                );
    }

    private static boolean completeMiddleEarthTeleport(
            ServerPlayer serverPlayer,
            ServerLevel serverWorld,
            Vec3 target,
            boolean setSpawnPoint,
            boolean welcomeNeeded
    ) {
        BlockPos targetPos = BlockPos.containing(target);
        if (!transitionPlayer(serverPlayer, serverWorld, target)) {
            return false;
        }
        if (setSpawnPoint) {
            serverPlayer.setRespawnPosition(ME_WORLD_KEY, targetPos, serverPlayer.getYRot(), true, true);
        }
        if (welcomeNeeded) {
            FactionUtil.sendOnFactionJoinMessage(serverPlayer);
        }
        Race race = PlayerDataService.getPlayerRace(serverPlayer, serverWorld);
        if (race != null) {
            RaceUtil.updateRace(serverPlayer, race, false);
        }
        return true;
    }

    public static Optional<Vec3> findSafeMiddleEarthReturnTarget(
            ServerLevel level,
            BlockPos returnPos,
            ServerPlayer player
    ) {
        if (level == null
                || returnPos == null
                || !Level.isInSpawnableBounds(returnPos)
                || !MiddleEarthMapUtils.getInstance().isWorldCoordinateInBorder(
                        returnPos.getX(),
                        returnPos.getZ()
                )) {
            return Optional.empty();
        }
        ChunkPos chunkPos = new ChunkPos(returnPos);
        level.getChunk(chunkPos.x, chunkPos.z);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        int safeY = findSafeY(
                level,
                returnPos.getX(),
                returnPos.getY(),
                returnPos.getZ(),
                cursor,
                player
        );
        if (safeY == NO_SAFE_Y) {
            return Optional.empty();
        }
        return Optional.of(new Vec3(
                returnPos.getX() + 0.5D,
                safeY,
                returnPos.getZ() + 0.5D
        ));
    }

    public static Optional<Vec3> findSafeMiddleEarthTeleportTarget(ServerLevel level, Vec3 coordinates) {
        return findSafeMiddleEarthTeleportTarget(level, coordinates, null);
    }

    public static Optional<Vec3> findSafeMiddleEarthTeleportTarget(
            ServerLevel level,
            Vec3 coordinates,
            ServerPlayer player
    ) {
        if (level == null || !isFinite(coordinates)) {
            return Optional.empty();
        }
        int x = (int) Math.floor(coordinates.x);
        int z = (int) Math.floor(coordinates.z);
        if (!MiddleEarthMapUtils.getInstance().isWorldCoordinateInBorder(x, z)) {
            Vector3i fallback = getSpawnCoordinate();
            x = fallback.x;
            z = fallback.z;
        }

        Optional<BlockPos> landing = findSafeLandingInChunk(level, x, z, player);
        Vector3i fallback = getSpawnCoordinate();
        if (landing.isEmpty() && (x != fallback.x || z != fallback.z)) {
            landing = findSafeLandingInChunk(level, fallback.x, fallback.z, player);
        }
        return landing.map(pos -> new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D));
    }

    public static boolean isInMiddleEarth(Level world){
        return world.dimension().location().equals(ME_DIMENSION_ID);
    }

    public static boolean isInOverworld(Level world){
        return world.dimension().location().equals(OW_DIMENSION_ID);
    }

    public static boolean teleportPlayerToOverworld(Player player) {
        if(!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(player, player.level());
            BlockPos coordinate;
            if(origin == null) {
                coordinate = player.getServer().overworld().getSharedSpawnPos();
            } else {
                coordinate = origin.origin();
            }

            ServerLevel serverWorld = serverPlayer.getServer().getLevel(OW_WORLD_KEY);
            if (serverWorld != null) {
                Optional<Vec3> safeTarget = findSafeOverworldReturnTarget(
                        serverWorld,
                        serverPlayer,
                        coordinate
                );
                if (safeTarget.isEmpty()) {
                    MiddleEarth.LOGGER.logError("No safe Overworld return position was found; teleport cancelled.");
                    return false;
                }
                Vec3 target = safeTarget.get();
                coordinate = BlockPos.containing(target);
                if (!transitionPlayer(serverPlayer, serverWorld, target)) {
                    return false;
                }
                serverPlayer.setRespawnPosition(Level.OVERWORLD, coordinate, serverPlayer.getYRot(), true, true);
                if (!ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP) {
                    AttributePool.reverse(player);
                } else {
                    RaceUtil.initializeRace(serverPlayer);
                }
                return true;
            }
        }
        return false;
    }

    private static Optional<Vec3> findSafeOverworldReturnTarget(
            ServerLevel level,
            ServerPlayer player,
            BlockPos preferred
    ) {
        Optional<Vec3> preferredTarget = findSafeTargetAt(
                level,
                player,
                preferred,
                preferred.getY()
        );
        if (preferredTarget.isPresent()) {
            return preferredTarget;
        }

        BlockPos sharedSpawn = level.getSharedSpawnPos();
        int surfaceY = level.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                sharedSpawn.getX(),
                sharedSpawn.getZ()
        );
        return findSafeTargetAt(level, player, sharedSpawn, surfaceY);
    }

    private static Optional<Vec3> findSafeTargetAt(
            ServerLevel level,
            ServerPlayer player,
            BlockPos horizontalTarget,
            int preferredY
    ) {
        if (horizontalTarget == null || !Level.isInSpawnableBounds(horizontalTarget)) {
            return Optional.empty();
        }
        ChunkPos chunkPos = new ChunkPos(horizontalTarget);
        level.getChunk(chunkPos.x, chunkPos.z);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        int safeY = findSafeY(
                level,
                horizontalTarget.getX(),
                preferredY,
                horizontalTarget.getZ(),
                cursor,
                player
        );
        if (safeY == NO_SAFE_Y) {
            return Optional.empty();
        }
        return Optional.of(new Vec3(
                horizontalTarget.getX() + 0.5D,
                safeY,
                horizontalTarget.getZ() + 0.5D
        ));
    }

    public static boolean transitionPlayer(ServerPlayer player, ServerLevel targetLevel, Vec3 target) {
        if (player == null || targetLevel == null || !isFinite(target)) {
            return false;
        }

        BlockPos targetPos = BlockPos.containing(target);
        targetLevel.getChunkSource().addRegionTicket(
                TicketType.POST_TELEPORT,
                new ChunkPos(targetPos),
                1,
                player.getId()
        );
        player.stopRiding();
        player.stopSleeping();
        var transitioned = player.changeDimension(new DimensionTransition(
                targetLevel,
                target,
                Vec3.ZERO,
                player.getYRot(),
                player.getXRot(),
                DimensionTransition.DO_NOTHING
        ));
        return transitioned == player && player.level() == targetLevel;
    }

    private static boolean isFinite(Vec3 position) {
        return Double.isFinite(position.x) && Double.isFinite(position.y) && Double.isFinite(position.z);
    }

    private static Optional<BlockPos> findSafeLandingInChunk(
            ServerLevel level,
            int centerX,
            int centerZ,
            ServerPlayer player
    ) {
        ChunkPos chunkPos = new ChunkPos(Math.floorDiv(centerX, 16), Math.floorDiv(centerZ, 16));
        level.getChunk(chunkPos.x, chunkPos.z);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        BlockPos landing = findSafeLandingInColumn(
                level,
                chunkPos,
                centerX,
                centerZ,
                cursor,
                player
        );
        if (landing != null) {
            return Optional.of(landing);
        }

        // Search the requested chunk from nearest to farthest without generating
        // every neighboring chunk around a one-time teleport.
        for (int radius = 1; radius <= 15; radius++) {
            int minX = centerX - radius;
            int maxX = centerX + radius;
            int minZ = centerZ - radius;
            int maxZ = centerZ + radius;
            for (int x = minX; x <= maxX; x++) {
                landing = findSafeLandingInColumn(level, chunkPos, x, minZ, cursor, player);
                if (landing != null) {
                    return Optional.of(landing);
                }
                landing = findSafeLandingInColumn(level, chunkPos, x, maxZ, cursor, player);
                if (landing != null) {
                    return Optional.of(landing);
                }
            }
            for (int z = minZ + 1; z < maxZ; z++) {
                landing = findSafeLandingInColumn(level, chunkPos, minX, z, cursor, player);
                if (landing != null) {
                    return Optional.of(landing);
                }
                landing = findSafeLandingInColumn(level, chunkPos, maxX, z, cursor, player);
                if (landing != null) {
                    return Optional.of(landing);
                }
            }
        }
        return Optional.empty();
    }

    private static BlockPos findSafeLandingInColumn(
            ServerLevel level,
            ChunkPos loadedChunk,
            int x,
            int z,
            BlockPos.MutableBlockPos cursor,
            ServerPlayer player
    ) {
        if (Math.floorDiv(x, 16) != loadedChunk.x
                || Math.floorDiv(z, 16) != loadedChunk.z
                || !MiddleEarthMapUtils.getInstance().isWorldCoordinateInBorder(x, z)) {
            return null;
        }
        int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        int safeY = findSafeY(level, x, surfaceY, z, cursor, player);
        return safeY == NO_SAFE_Y ? null : new BlockPos(x, safeY, z);
    }

    private static int findSafeY(
            ServerLevel level,
            int x,
            int preferredY,
            int z,
            BlockPos.MutableBlockPos cursor,
            ServerPlayer player
    ) {
        int minY = level.getMinBuildHeight() + 1;
        int maxY = level.getMaxBuildHeight() - 2;
        int startY = Mth.clamp(preferredY, minY, maxY);
        for (int offset = 0; offset <= SAFE_VERTICAL_SEARCH_UP; offset++) {
            int candidateY = startY + offset;
            if (candidateY <= maxY
                    && isSafeLanding(level, x, candidateY, z, cursor, player)) {
                return candidateY;
            }
        }
        for (int offset = 1; offset <= SAFE_VERTICAL_SEARCH_DOWN; offset++) {
            int candidateY = startY - offset;
            if (candidateY >= minY
                    && isSafeLanding(level, x, candidateY, z, cursor, player)) {
                return candidateY;
            }
        }
        return NO_SAFE_Y;
    }

    private static boolean isSafeLanding(
            ServerLevel level,
            int x,
            int y,
            int z,
            BlockPos.MutableBlockPos cursor,
            ServerPlayer player
    ) {
        cursor.set(x, y, z);
        var feetState = level.getBlockState(cursor);
        boolean feetClear = feetState.getCollisionShape(level, cursor).isEmpty();
        boolean feetHazard = isHazard(feetState);
        boolean feetLava = feetState.getFluidState().is(FluidTags.LAVA);

        cursor.set(x, y + 1, z);
        var headState = level.getBlockState(cursor);
        boolean headClear = headState.getCollisionShape(level, cursor).isEmpty();
        boolean headHazard = isHazard(headState);
        boolean headLava = headState.getFluidState().is(FluidTags.LAVA);

        cursor.set(x, y - 1, z);
        var floorState = level.getBlockState(cursor);
        boolean floorHazard = isHazard(floorState);
        boolean floorLava = floorState.getFluidState().is(FluidTags.LAVA);
        boolean supported = !floorState.getCollisionShape(level, cursor).isEmpty()
                || floorState.getFluidState().is(FluidTags.WATER);
        EntityDimensions dimensions = player == null
                ? DEFAULT_PLAYER_DIMENSIONS
                : player.getDimensions(Pose.STANDING);
        Vec3 target = new Vec3(x + 0.5D, y, z + 0.5D);
        var standingBounds = dimensions.makeBoundingBox(target);
        boolean fullBodyClear = level.getWorldBorder().isWithinBounds(standingBounds)
                && level.noBlockCollision(player, standingBounds);

        return feetClear && headClear && supported && fullBodyClear
                && !feetHazard && !headHazard && !floorHazard
                && !feetLava && !headLava && !floorLava;
    }

    private static boolean isHazard(net.minecraft.world.level.block.state.BlockState state) {
        return state.is(BlockTags.FIRE)
                || state.is(Blocks.CACTUS)
                || state.is(Blocks.SWEET_BERRY_BUSH)
                || state.is(Blocks.POWDER_SNOW)
                || state.is(Blocks.WITHER_ROSE)
                || state.is(Blocks.MAGMA_BLOCK)
                || state.is(Blocks.CAMPFIRE)
                || state.is(Blocks.SOUL_CAMPFIRE);
    }

    /**
     * For future usage only, not necessary for now
     * @return world coordinate for current map coordinate selected based on map iteration/pixel weight
     */
    public static Vector3i getSpawnCoordinate(){
        Vector3i spawnCoordinate = new Vector3i(939, 90, 915);;
        double worldIteration = Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION);
        int x = (int)((spawnCoordinate.x * worldIteration));
        int z = (int)((spawnCoordinate.z * worldIteration));

        return new Vector3i(x, spawnCoordinate.y, z);
    }
}
