package net.sevenstars.middleearth.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModEvents {
    public static void register(){
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
            MiddleEarthHeightMap.setSeed(player.getServerWorld().getSeed());

            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            if(data == null)
                return;
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
    }
}
