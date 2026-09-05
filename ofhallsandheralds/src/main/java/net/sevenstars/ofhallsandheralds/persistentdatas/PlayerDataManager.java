package net.sevenstars.ofhallsandheralds.persistentdatas;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;

import java.nio.file.Path;

public class PlayerDataManager {
    private static PlayerPersistentDataManager manager;

    public static void init(MinecraftServer server) {
        Path path = server.getSavePath(WorldSavePath.ROOT)
                .resolve("data")
                .resolve(OfHallsAndHeralds.getNamespace())
                .resolve("reputation");

        manager = new PlayerPersistentDataManager(path);
    }

    public static PlayerPersistentDataManager get() {
        if (manager == null)
            throw new IllegalStateException("PlayerDataManager has not been initialized");

        return manager;
    }
}