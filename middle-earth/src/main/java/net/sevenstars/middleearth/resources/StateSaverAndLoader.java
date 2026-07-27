package net.sevenstars.middleearth.resources;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;

import java.util.HashMap;
import java.util.UUID;

public class StateSaverAndLoader extends SavedData {
    private static final String DATA_NAME = "middle_earth_player_datas";
    private static final Factory<StateSaverAndLoader> TYPE = new Factory<>(
            StateSaverAndLoader::new,
            StateSaverAndLoader::load,
            null
    );

    private final HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    public CompoundTag save(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        ListTag list = new ListTag();
        players.forEach((uuid, playerData) -> {
            CompoundTag entry = new CompoundTag();
            entry.putString("uuid", uuid.toString());
            entry.put("data", playerData.createNbt());
            list.add(entry);
        });
        nbt.put("list", list);
        return nbt;
    }

    private static StateSaverAndLoader load(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        CompoundTag dataRoot = nbt.contains("player_datas", Tag.TAG_COMPOUND)
                ? nbt.getCompound("player_datas")
                : nbt;
        ListTag list = dataRoot.getList("list", Tag.TAG_COMPOUND);

        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompound(i);
            String uuidString = entry.getString("uuid");
            if (uuidString.isEmpty()) {
                continue;
            }

            try {
                PlayerData playerData = new PlayerData(entry.getCompound("data"));
                playerData.setDirtyMarker(state::setDirty);
                state.players.put(UUID.fromString(uuidString), playerData);
            } catch (IllegalArgumentException ignored) {
                // Ignore malformed legacy entries instead of preventing the world from loading.
            }
        }
        return state;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        DimensionDataStorage storage = server.getLevel(Level.OVERWORLD).getDataStorage();
        return storage.computeIfAbsent(TYPE, DATA_NAME);
    }

    private static PlayerData createPlayerData(StateSaverAndLoader state) {
        PlayerData playerData = new PlayerData();
        playerData.setDirtyMarker(state::setDirty);
        state.setDirty();
        return playerData;
    }

    private static boolean canAccessPlayerState(Player player) {
        return player != null && !player.level().isClientSide && player.getServer() != null;
    }

    public static PlayerData getPlayerStateReadOnly(Player player) {
        if (!canAccessPlayerState(player)) {
            return null;
        }

        StateSaverAndLoader state = getServerState(player.getServer());
        return state.players.get(player.getUUID());
    }

    public static PlayerData getPlayerState(Player player) {
        if (!canAccessPlayerState(player)) {
            return null;
        }

        StateSaverAndLoader state = getServerState(player.getServer());
        PlayerData playerData = state.players.computeIfAbsent(
                player.getUUID(),
                ignored -> createPlayerData(state)
        );
        return playerData;
    }
}
