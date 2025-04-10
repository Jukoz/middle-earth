package net.sevenstars.middleearth.resources;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
/**
 * <a href="https://fabricmc.net/wiki/tutorial:persistent_states">Documentation</a><br/>
 * <a href="https://github.com/TerraformersMC/Biolith/blob/main/common/src/main/java/com/terraformersmc/biolith/impl/config/BiolithState.java">Other Source</a>
 */
public class StateSaverAndLoader extends PersistentState {
    private static final PersistentStateType<StateSaverAndLoader> TYPE;

    private HashMap<UUID, PlayerData> players;

    public StateSaverAndLoader(Context context) {
        this.players = new HashMap<>();
    }

    public static final Codec<StateSaverAndLoader> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            NbtCompound.CODEC.fieldOf("player_datas").forGetter(StateSaverAndLoader::getPlayerDataNbt)
    ).apply(instance, StateSaverAndLoader::new));

    public StateSaverAndLoader() {
        this.players = new HashMap<>();
    }
    public StateSaverAndLoader(NbtCompound nbt){
        this.players = new HashMap<>();
        var listOpt = nbt.getList("list");
        if(listOpt.isPresent()){
            var list = listOpt.get();
            for(int i = 0; i < list.size(); i++){
                var playerDataNbt = list.getCompoundOrEmpty(i);
                var uuidOpt = playerDataNbt.getString("uuid");
                var newPlayerData = new PlayerData(playerDataNbt.getCompoundOrEmpty("data"));
                uuidOpt.ifPresent(s -> this.players.put(UUID.fromString(s), newPlayerData));
            }
        }
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.players = new HashMap<>();
        return state;
    }

    private NbtCompound getPlayerDataNbt() {
        NbtCompound nbt = new NbtCompound();
        NbtList list = new NbtList();
        for(UUID uuid : players.keySet()){
            NbtCompound specificNbt = new NbtCompound();
            specificNbt.putString("uuid", uuid.toString());
            specificNbt.put("data", this.players.get(uuid).createNbt());
            list.add(specificNbt);
        }
        nbt.put("list", list);
        return nbt;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(TYPE);
        state.markDirty();
        return state;
    }
    public static PlayerData getPlayerState(PlayerEntity player) {
        StateSaverAndLoader serverState = getServerState((MinecraftServer)Objects.requireNonNull(player.getServer()));
        UUID playerUUID = player.getUuid();
        if (serverState.players.get(playerUUID) == null) {
            serverState.players.put(playerUUID, new PlayerData());
        }
        return serverState.players.get(playerUUID);
    }


    static {
        TYPE = new PersistentStateType<>("middle_earth_player_datas", StateSaverAndLoader::createNew, CODEC, (DataFixTypes) null);
    }
}






