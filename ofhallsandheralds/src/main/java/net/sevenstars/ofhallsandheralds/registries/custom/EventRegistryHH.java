package net.sevenstars.ofhallsandheralds.registries.custom;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.sevenstars.ofhallsandheralds.persistentdatas.PlayerPersistentDataManagerHH;

public class EventRegistryHH {
    public static void register() {
        registerPlayerPersistentDataManager();
    }

    private static void registerPlayerPersistentDataManager() {
        ServerLifecycleEvents.SERVER_STARTED.register(PlayerPersistentDataManagerHH::init);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerPersistentDataManagerHH.loadAll(handler.getPlayer().getUuid());
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            PlayerPersistentDataManagerHH.unloadAll(handler.getPlayer().getUuid());
        });
    }
}
