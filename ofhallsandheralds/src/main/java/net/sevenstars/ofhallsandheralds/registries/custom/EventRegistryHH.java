package net.sevenstars.ofhallsandheralds.registries.custom;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.sevenstars.ofhallsandheralds.network.payload.PlayerPersistentDataPacket;
import net.sevenstars.ofhallsandheralds.persistentdatas.PlayerDataManager;
import net.sevenstars.ofhallsandheralds.persistentdatas.PlayerPersistentData;
import net.sevenstars.ofhallsandheralds.persistentdatas.StateSaverAndLoader;

public class EventRegistryHH {
    public static void  register(){
        ServerLifecycleEvents.SERVER_STARTED.register(PlayerDataManager::init);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerPersistentData playerPersistentData = StateSaverAndLoader.getPlayerPersistentData(handler.getPlayer());
            server.execute(() -> {
                ServerPlayNetworking.send(handler.getPlayer(), new PlayerPersistentDataPacket(playerPersistentData));
            });
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            PlayerDataManager.get().unload(handler.getPlayer().getUuid());
        });
    }
}
