package net.jukoz.me.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModEvents {
    public static void register(){
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
            RaceUtil.reset(player);
            boolean isInMiddleEarth = ModDimensions.isInMiddleEarth(player.getWorld());
            if(isInMiddleEarth){
                RaceUtil.initializeRace(player);
            } else if(ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                RaceUtil.initializeRace(player);
            }
        });
    }
}
