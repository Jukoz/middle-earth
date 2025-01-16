package net.jukoz.me.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ModEvents {
    public static void register(){
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
            MiddleEarthHeightMap.setSeed(player.getServerWorld().getSeed());

            PlayerData data = StateSaverAndLoader.getPlayerState(player);

            if(ModServerConfigs.ENABLE_INITIAL_MIDDLE_EARTH_SPAWN_OVERRIDE && (data == null || data.getIsFirstTime())){
                Vec3d spawnCoordinates = new Vec3d(
                        ModServerConfigs.MIDDLE_EARTH_SPAWN_OVERRIDE_X_COORDINATE,
                        ModServerConfigs.MIDDLE_EARTH_SPAWN_OVERRIDE_Y_COORDINATE,
                        ModServerConfigs.MIDDLE_EARTH_SPAWN_OVERRIDE_Z_COORDINATE
                );
                ModDimensions.teleportPlayerToMe(player, spawnCoordinates, ModServerConfigs.ENABLE_FORCED_MIDDLE_EARTH_RESPAWN, false);
                if(ModServerConfigs.ENABLE_PHIAL_ON_FIRST_SPAWN)
                    player.giveItemStack(new ItemStack(ModResourceItems.STARLIGHT_PHIAL));
                if(data != null)
                    data.setSpawn();
            }
            if(data != null && data.getRace() != null){
                RaceUtil.reset(player);
                boolean isInMiddleEarth = ModDimensions.isInMiddleEarth(player.getWorld());
                if(isInMiddleEarth){
                    RaceUtil.initializeRace(player);
                } else if(ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                    RaceUtil.initializeRace(player);
                }
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((serverPlayNetworkHandler, packetSender) -> {
            ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();

            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            if(data != null)
                data.setSpawn();
        });
    }
}
