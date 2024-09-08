package net.jukoz.me.world.dimension;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.factions.FactionUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import org.joml.Vector3i;

public class ModDimensions {
    public static Identifier ME_DIMENSION_ID = Identifier.of(MiddleEarth.MOD_ID, "middle_earth");
    public static Identifier OW_DIMENSION_ID = Identifier.of("overworld");

    public static final RegistryKey<DimensionOptions> ME_DIMENSION_KEY =
            RegistryKey.of(RegistryKeys.DIMENSION, ME_DIMENSION_ID);

    public static RegistryKey<World> ME_WORLD_KEY =
            RegistryKey.of(RegistryKeys.WORLD, ME_DIMENSION_KEY.getValue());

    public static final RegistryKey<DimensionOptions> OW_DIMENSION_KEY =
            RegistryKey.of(RegistryKeys.DIMENSION, Identifier.of("overworld"));

    public static RegistryKey<World> OW_WORLD_KEY =
            RegistryKey.of(RegistryKeys.WORLD, OW_DIMENSION_KEY.getValue());

    public static void register() {
        Registry.register(Registries.CHUNK_GENERATOR, ME_DIMENSION_ID, MiddleEarthChunkGenerator.CODEC);
        ME_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, ME_DIMENSION_ID);

        LoggerUtil.logDebugMsg("Registering ModDimensions for " + MiddleEarth.MOD_ID);
    }

    public static Vector3i getDimensionHeight(int x, int z) {
        MiddleEarthHeightMap.getHeight(x, z);
        int height = (int) (1 + MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z));
        return new Vector3i(x, height, z);
    }

    public static void teleportPlayerToMe(PlayerEntity player, Vec3d coordinates, boolean welcomeNeeded){
        if(!player.getWorld().isClient()) {
            RegistryKey<World> registryKey = ME_WORLD_KEY;
            ServerWorld serverWorld = (ServerWorld) player.getWorld();
            if (serverWorld != null) {
                serverWorld = serverWorld.getServer().getWorld(registryKey);

                player.wakeUp();

                ((ServerPlayerEntity) player).teleport(serverWorld, coordinates.x , coordinates.y, coordinates.z, 0, 0);
                player.refreshPositionAfterTeleport(coordinates.x, coordinates.y, coordinates.z);
                ((ServerPlayerEntity) player).setSpawnPoint(registryKey, new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z), player.getYaw(), true, true);
                if(welcomeNeeded)
                    FactionUtil.sendOnFactionJoinMessage(player);
            }
        }
    }

    public static boolean isInMiddleEarth(World world){
        return world.getRegistryKey().getValue().equals(ME_DIMENSION_ID);
    }

    public static boolean isInOverworld(World world){
        return world.getRegistryKey().getValue().equals(OW_DIMENSION_ID);
    }

    public static void teleportPlayerToOverworld(PlayerEntity player) {
        if(!player.getWorld().isClient()) {
            RegistryKey<World> registryKey = OW_WORLD_KEY;
            ServerWorld serverWorld = (ServerWorld) player.getWorld();
            BlockPos coordinate = StateSaverAndLoader.getPlayerState(player).getOverworldSpawnCoordinates();
            if(coordinate == null) {
                LoggerUtil.logError("ModDimensions::Player doesn't have an overworld spawn coordinate ready to be used.");
                return;
            }
            if (serverWorld != null) {
                serverWorld = serverWorld.getServer().getWorld(registryKey);

                player.wakeUp();

                ((ServerPlayerEntity) player).setSpawnPoint(World.OVERWORLD, player.getServer().getOverworld().getSpawnPos(), player.getServer().getOverworld().getSpawnAngle(), true, true);
                ((ServerPlayerEntity) player).teleport(serverWorld, coordinate.getX() , coordinate.getY(), coordinate.getZ(), 0, 0);
                player.refreshPositionAfterTeleport(coordinate.getX() , coordinate.getY(), coordinate.getZ());
            }
        }
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
