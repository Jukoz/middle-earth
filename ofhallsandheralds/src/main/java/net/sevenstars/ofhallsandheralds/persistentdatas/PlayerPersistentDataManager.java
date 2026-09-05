package net.sevenstars.ofhallsandheralds.persistentdatas;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtSizeTracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerPersistentDataManager {

    private final Path directory;
    private final Map<UUID, PlayerPersistentData> data = new HashMap<>();

    public PlayerPersistentDataManager(Path directory) {
        this.directory = directory;
    }

    public PlayerPersistentData get(UUID uuid) {
        return data.computeIfAbsent(uuid, this::load);
    }

    private Path getPlayerPath(UUID uuid) {
        return directory.resolve(uuid + ".nbt");
    }

    public void save(UUID uuid) {
        PlayerPersistentData playerData = data.get(uuid);

        if (playerData != null)
            save(uuid, playerData);
    }

    public void save(UUID uuid, PlayerPersistentData data) {
        Path path = getPlayerPath(uuid);

        try {
            Files.createDirectories(directory);

            NbtCompound nbt = PlayerPersistentData.CODEC
                    .encodeStart(NbtOps.INSTANCE, data)
                    .getOrThrow()
                    .asCompound()
                    .orElseThrow();

            NbtIo.writeCompressed(nbt, path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save player data for " + uuid, e);
        }
    }

    public void unload(UUID uuid) {
        save(uuid);
        data.remove(uuid);
    }

    public PlayerPersistentData load(UUID uuid) {
        Path path = getPlayerPath(uuid);

        if (!Files.exists(path))
            return new PlayerPersistentData();

        try {
            NbtCompound nbt = NbtIo.readCompressed(path, NbtSizeTracker.ofUnlimitedBytes());

            return PlayerPersistentData.CODEC
                    .parse(NbtOps.INSTANCE, nbt)
                    .getOrThrow();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load player data for " + uuid, e);
        }
    }
}