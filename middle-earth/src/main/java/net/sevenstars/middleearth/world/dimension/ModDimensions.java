package net.sevenstars.middleearth.world.dimension;

import net.minecraft.entity.Entity;
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
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.registries.RegistryAliases;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.factions.FactionUtil;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
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
        RegistryAliases.aliases.add(new RegistryAliases.Alias(Registries.CHUNK_GENERATOR, ME_DIMENSION_ID.getPath()));

        MiddleEarth.LOGGER.logDebugMsg("Registering ModDimensions for " + MiddleEarth.MOD_ID);
    }

    public static Vector3i getDimensionHeight(int x, int z) {
        MiddleEarthHeightMap.getHeight(x, z);
        int height = (int) (1 + MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z));
        return new Vector3i(x, height, z);
    }

    public static void teleportPlayerToMe(PlayerEntity player, Vec3d coordinates, boolean setSpawnPoint, boolean welcomeNeeded){
        if(!player.getWorld().isClient()) {
            RegistryKey<World> registryKey = ME_WORLD_KEY;
            ServerWorld serverWorld = (ServerWorld) player.getWorld();
            if (serverWorld != null) {

                player.teleportTo(new TeleportTarget(player.getServer().getWorld(ME_WORLD_KEY), coordinates, Vec3d.ZERO, 0, 0, new TeleportTarget.PostDimensionTransition() {
                    @Override
                    public void onTransition(Entity entity) {
                        // idk
                    }
                }));
                if(setSpawnPoint){
                    ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(registryKey, new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z), player.getYaw(), true);
                    ((ServerPlayerEntity) player).setSpawnPoint(respawn, true);
                }
                if(welcomeNeeded)
                    FactionUtil.sendOnFactionJoinMessage(player);
                Race race =  PlayerDataService.getPlayerRace(player, player.getWorld());
                if(race != null){
                    RaceUtil.updateRace(player, race, false);
                }

            }
        }
    }

    public static boolean isInMiddleEarth(World world){
        return world.getRegistryKey().getValue().equals(ME_DIMENSION_ID);
    }

    public static boolean isInOverworld(World world){
        return world.getRegistryKey().getValue().equals(OW_DIMENSION_ID);
    }

    public static boolean teleportPlayerToOverworld(PlayerEntity player) {
        if(!player.getWorld().isClient()) {
            RegistryKey<World> registryKey = OW_WORLD_KEY;
            ServerWorld serverWorld = (ServerWorld) player.getWorld();
            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(player, player.getWorld());
            BlockPos coordinate;
            if(origin == null) {
                coordinate = player.getServer().getOverworld().getSpawnPos();
            } else {
                coordinate = origin.origin();
            }

            if (serverWorld != null) {
                Vec3d coordinates = new Vec3d(coordinate.getX(), coordinate.getY(), coordinate.getZ());
                player.teleportTo(new TeleportTarget(player.getServer().getOverworld(), coordinates, Vec3d.ZERO, 0, 0, entity -> {
                    // idk
                }));
                
                if(!ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                    AttributePool.reverse(player);
                }
                return true;
            }
        }
        return false;
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
