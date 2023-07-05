package net.jesteur.me.events;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.jesteur.me.item.ModRessourceItems;
import net.jesteur.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jesteur.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jesteur.me.world.dimension.ModDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.joml.Vector3i;

/**
 * Event called when player starts sleeping (on right click)
 * If the player has a starlight phial in his inventory, then we proceed in getting his current and target world
 * Once we find the right coordinates, we wake up the player to free the bed and teleport him.
 */
public class OnPlayerSleepEvent implements EntitySleepEvents.StartSleeping {
    public static final Vector3i ME_SPAWN_LOCATION = new Vector3i(3640, 90, 2950);

    @Override
    public void onStartSleeping(LivingEntity entity, BlockPos sleepingPos) {
        if(entity.getType() == EntityType.PLAYER) {
            PlayerEntity player = (PlayerEntity) entity;
            if(player.getInventory().contains(ModRessourceItems.STARLIGHT_PHIAL.getDefaultStack())) {
                if(!player.getWorld().isClient()) {
                    RegistryKey<World> registryKey = player.getWorld().getRegistryKey() == ModDimensions.WORLD_KEY ? World.OVERWORLD : ModDimensions.WORLD_KEY;
                    ServerWorld serverWorld = (ServerWorld) player.getWorld();
                    if (serverWorld != null) {
                        serverWorld = serverWorld.getServer().getWorld(registryKey);

                        Vector3i targetPos = new Vector3i(ME_SPAWN_LOCATION.x, ME_SPAWN_LOCATION.y, ME_SPAWN_LOCATION.z);
                        if(registryKey != ModDimensions.WORLD_KEY) targetPos = new Vector3i(serverWorld.getSpawnPos().getX(), 80, serverWorld.getSpawnPos().getZ());

                        player.wakeUp();
                        ((ServerPlayerEntity) player).teleport(serverWorld, targetPos.x, targetPos.y + 10, targetPos.z, 0, 0);
                        World targetWorld =  player.getWorld();
                        int highY = 1 + targetWorld.getChunk(new BlockPos(targetPos.x, 0, targetPos.z)).sampleHeightmap(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, targetPos.x, targetPos.z);

                        player.refreshPositionAfterTeleport(targetPos.x, highY, targetPos.z);
                    }
                }
            }
        }
    }
}
