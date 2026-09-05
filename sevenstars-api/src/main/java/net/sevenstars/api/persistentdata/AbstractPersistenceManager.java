package net.sevenstars.api.persistentdata;

import com.mojang.serialization.Codec;
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

public abstract class AbstractPersistenceManager<T> {
    public Path directory;
    public Map<UUID, T> data = new HashMap<>();

    public AbstractPersistenceManager(Path directory) {
        this.directory = directory;
    }

    public T get(UUID uuid) {
        return data.computeIfAbsent(uuid, this::load);
    }

    private Path getPlayerPath(UUID uuid) {
        return directory.resolve(uuid + ".nbt");
    }

    public void save(UUID uuid) {
        T foundData = data.get(uuid);

        if (foundData != null)
            save(uuid, foundData);
    }

    public void save(UUID uuid, T dataToSave) {
        Path path = getPlayerPath(uuid);

        try {
            Files.createDirectories(directory);

            NbtCompound nbt = ObtenirCodec()
                    .encodeStart(NbtOps.INSTANCE, dataToSave)
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

    public T load(UUID uuid) {
        Path path = getPlayerPath(uuid);

        if (!Files.exists(path))
            return createDefault();

        try {
            NbtCompound nbt = NbtIo.readCompressed(path, NbtSizeTracker.ofUnlimitedBytes());

            return ObtenirCodec()
                   .parse(NbtOps.INSTANCE, nbt)
                   .getOrThrow();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load player data for " + uuid, e);
        }
    }

    protected abstract Codec<T> ObtenirCodec();
    protected abstract T createDefault();
}
