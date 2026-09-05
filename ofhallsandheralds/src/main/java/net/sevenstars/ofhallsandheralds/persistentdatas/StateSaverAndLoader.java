package net.sevenstars.ofhallsandheralds.persistentdatas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;


import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {
    public HashMap<UUID, PlayerPersistentData> players = new HashMap<>();

    public static final Codec<StateSaverAndLoader> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.unboundedMap(Uuids.CODEC, PlayerPersistentData.CODEC).fieldOf("players").forGetter(state -> state.players)
    ).apply(instance, StateSaverAndLoader::new));

    public StateSaverAndLoader(Map<UUID, PlayerPersistentData> players) {
        this.players = new HashMap<>(players);
    }
    public StateSaverAndLoader() {
        this.players = new HashMap<>();
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.players = new HashMap<>();
        return state;
    }
    public static StateSaverAndLoader createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        NbtCompound playersNbt = tag.getCompound("players").orElse(new NbtCompound());

        playersNbt.getKeys().forEach(key -> {
            UUID uuid = UUID.fromString(key);

            PlayerPersistentData playerData = PlayerPersistentData.CODEC
                    .parse(NbtOps.INSTANCE, playersNbt.get(key))
                    .getOrThrow();

            state.players.put(uuid, playerData);
        });

        return state;
    }

    private static final PersistentStateType<StateSaverAndLoader> TYPE =
            new PersistentStateType<>(
                    OfHallsAndHeralds.getNamespace(),
                    StateSaverAndLoader::createNew,
                    CODEC,
                    null
            );

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(TYPE);
        state.markDirty();
        return state;
    }

    public static PlayerPersistentData getPlayerPersistentData(LivingEntity player) {
        if(player == null)
            return new PlayerPersistentData();
        StateSaverAndLoader serverState = getServerState(player.getServer());
        if(serverState == null)
            return new PlayerPersistentData();
        PlayerPersistentData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerPersistentData());
        return playerState;
    }
}
