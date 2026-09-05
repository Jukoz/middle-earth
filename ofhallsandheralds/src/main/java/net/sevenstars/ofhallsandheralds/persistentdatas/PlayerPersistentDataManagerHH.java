package net.sevenstars.ofhallsandheralds.persistentdatas;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.persistentdatas.origin.OriginPersistenceManager;
import net.sevenstars.ofhallsandheralds.persistentdatas.reputation.ReputationPersistenceManager;

import java.nio.file.Path;
import java.util.UUID;

public class PlayerPersistentDataManagerHH {
    private static ReputationPersistenceManager reputationDataManager;
    private static OriginPersistenceManager originPersistenceManager;

    public static void init(MinecraftServer server) {
        Path base = server.getSavePath(WorldSavePath.ROOT).resolve("data").resolve(OfHallsAndHeralds.getNamespace());
        // Managers
        reputationDataManager = new ReputationPersistenceManager(base.resolve("reputations"));
        originPersistenceManager = new OriginPersistenceManager(base.resolve("origins"));
    }

    public static ReputationPersistenceManager getReputation() {
        if (reputationDataManager == null)
            throw new IllegalStateException("PlayerReputationDataManager has not been initialized");
        return reputationDataManager;
    }

    public static OriginPersistenceManager getOrigin() {
        if (originPersistenceManager == null)
            throw new IllegalStateException("OriginPersistenceManager has not been initialized");
        return originPersistenceManager;
    }

    public static void unloadAll(UUID uuid) {
        getReputation().unload(uuid);
        getOrigin().unload(uuid);
    }

    public static void loadAll(UUID uuid) {
        getReputation().load(uuid);
        getOrigin().load(uuid);
        /*
        ReputationPersistentData reputationPersistentData = PlayerPersistentDataManagerHH.getReputation().load(handler.getPlayer().getUuid());
        server.execute(() -> {
            ServerPlayNetworking.send(handler.getPlayer(), new PlayerPersistentReputationDataPacket(reputationPersistentData));
        });
         */
    }
}