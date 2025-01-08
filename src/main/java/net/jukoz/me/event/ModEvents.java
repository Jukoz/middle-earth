package net.jukoz.me.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.jukoz.me.resources.datas.races.RaceUtil;

public class ModEvents {
    public static void register(){
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            RaceUtil.initializeRace(serverPlayNetworkHandler.player);

        });
    }
}
