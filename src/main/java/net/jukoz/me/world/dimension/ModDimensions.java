package net.jukoz.me.world.dimension;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.FactionSelectionScreen;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.network.packets.TeleportRequestPacket;
import net.jukoz.me.network.packets.TeleportToMeSpawnRequestPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.Vector;


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

    public static void teleportPlayerToME(PlayerEntity player) {
        Vector3i spawnCoordinates = getSpawnCoordinate();
        Vector2i coordinates = MiddleEarthMapUtils.getInstance().getWorldCoordinateFromInitialMap(spawnCoordinates.x, spawnCoordinates.z);
        int height = (int) (1 + MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(coordinates.x, coordinates.y));
        Vector3i targetCoords = new Vector3i(coordinates.x, height, coordinates.y);
        teleportPlayerToMe(player, targetCoords);
    }

    public static void teleportPlayerToMe(PlayerEntity player, Vector3i coordinates){
        if(!player.getWorld().isClient()) {
            RegistryKey<World> registryKey = ME_WORLD_KEY;
            ServerWorld serverWorld = (ServerWorld) player.getWorld();
            if (serverWorld != null) {
                serverWorld = serverWorld.getServer().getWorld(registryKey);

                player.wakeUp();

                ((ServerPlayerEntity) player).teleport(serverWorld, coordinates.x , coordinates.y, coordinates.z, 0, 0);
                player.refreshPositionAfterTeleport(coordinates.x, coordinates.y, coordinates.z);
            }
        }
    }

    public static void teleportPlayerToMe(TeleportRequestPacket packet, ServerPlayNetworking.Context context){
        context.player().stopRiding();
        Vector3i coordinates = packet.getCoordinates();

        ServerWorld serverWorld = (ServerWorld) context.player().getWorld();
        if (serverWorld != null) {
            RegistryKey<World> registryKey = ME_WORLD_KEY;
            serverWorld = serverWorld.getServer().getWorld(registryKey);

            context.player().wakeUp();

            context.player().teleport(serverWorld, coordinates.x, coordinates.y, coordinates.z, context.player().getYaw(), context.player().getPitch());
            context.player().refreshPositionAfterTeleport(coordinates.x, coordinates.y, coordinates.z);
        }
    }

    public static void openOnboardingScreen(PlayerEntity player){
        MinecraftClient mc = MinecraftClient.getInstance();
        if(player.getWorld().isClient) {
            if (mc.currentScreen == null) {
                mc.setScreen(new FactionSelectionScreen());
            }
        }
        if(!player.isCreative() && player.getStackInHand(Hand.MAIN_HAND).isOf(ModResourceItems.STARLIGHT_PHIAL)){
            player.getStackInHand(Hand.MAIN_HAND).decrement(1);
        }
    }

    public static int getHighestYAtXZ(int x, int z) {
        return (int) MiddleEarthHeightMap.getHeight(x, z);
    }

    public static Vector3i getSpawnCoordinate(){
        Vector3i spawnCoordinate = new Vector3i(939, 90, 915);;
        double worldIteration = Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION);
        int x = (int)((spawnCoordinate.x * worldIteration));
        int z = (int)((spawnCoordinate.z * worldIteration));

        return new Vector3i(x, spawnCoordinate.y, z);
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

                ((ServerPlayerEntity) player).teleport(serverWorld, coordinate.getX() , coordinate.getY(), coordinate.getZ(), 0, 0);
                player.refreshPositionAfterTeleport(coordinate.getX() , coordinate.getY(), coordinate.getZ());
            }
        }
    }

}
