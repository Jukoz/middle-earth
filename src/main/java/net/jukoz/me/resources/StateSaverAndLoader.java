package net.jukoz.me.resources;

import com.jcraft.jorbis.Block;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import org.jetbrains.annotations.Debug;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * Documentation : <a href="https://fabricmc.net/wiki/tutorial:persistent_states">link</a>
 */
public class StateSaverAndLoader extends PersistentState {
    public HashMap<UUID, PlayerData> players = new HashMap<>();
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound playersNbt = new NbtCompound();
        players.forEach((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            if(playerData.hasAffilition()){
                AffiliationData affiliationData = playerData.getAffiliationData();
                playerNbt.putInt("alignment", affiliationData.alignment);
                playerNbt.putInt("faction", affiliationData.faction);
                playerNbt.putInt("subfaction", affiliationData.subfaction);
            }

            BlockPos overworldSpawn = playerData.getOverworldSpawnCoordinates();
            if(overworldSpawn != null){
                playerNbt.putIntArray("ow", new int[]{overworldSpawn.getX(), overworldSpawn.getY(), overworldSpawn.getZ()});
                LoggerUtil.logDebugMsg("Overworld="+overworldSpawn+";");
            }

            BlockPos middleEarthSpawn = playerData.getMiddleEarthSpawnCoordinates();
            if(playerData.getMiddleEarthSpawnCoordinates() != null){
                playerNbt.putIntArray("me", new int[]{middleEarthSpawn.getX(), middleEarthSpawn.getY(), middleEarthSpawn.getZ()});
                LoggerUtil.logDebugMsg("Middle_Earth="+middleEarthSpawn+";");
            }

            playersNbt.put(uuid.toString(), playerNbt);
        });
        nbt.put("players", playersNbt);

        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        StateSaverAndLoader state = new StateSaverAndLoader();

        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();
            try{
                int alignment = playersNbt.getCompound(key).getInt("alignment");
                int faction = playersNbt.getCompound(key).getInt("faction");
                int subfaction = playersNbt.getCompound(key).getInt("subfaction");

                AffiliationData affiliationData = new AffiliationData(alignment, faction, subfaction);
                playerData.setAffiliationData(affiliationData);

                int[] overworldPos = playersNbt.getCompound(key).getIntArray("ow");
                if(overworldPos.length == 3){
                    BlockPos overworldSpawn = new BlockPos(
                            overworldPos[0],
                            overworldPos[1],
                            overworldPos[2]
                    );
                    playerData.setOverworldSpawn(overworldSpawn);
                    LoggerUtil.logDebugMsg("Overworld="+overworldSpawn+";");
                }

                int[] middleEarthPos = playersNbt.getCompound(key).getIntArray("me");
                if(middleEarthPos.length == 3){
                    BlockPos middleEarthSpawn = new BlockPos(
                            middleEarthPos[0],
                            middleEarthPos[1],
                            middleEarthPos[2]
                    );
                    playerData.setMiddleEarthSpawn(middleEarthSpawn);
                    LoggerUtil.logDebugMsg("MiddleEarth="+middleEarthSpawn+";");
                }
            } catch(Exception e){

            }

            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });

        return state;
    }

    private static Type<StateSaverAndLoader> type = new Type<>(
            StateSaverAndLoader::new, // If there's no 'StateSaverAndLoader' yet create one
            StateSaverAndLoader::createFromNbt, // If there is a 'StateSaverAndLoader' NBT, parse it with 'createFromNbt'
            null // Supposed to be an 'DataFixTypes' enum, but we can just pass null
    );

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        // (Note: arbitrary choice to use 'World.OVERWORLD' instead of 'World.END' or 'World.NETHER'.  Any work)
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        // The first time the following 'getOrCreate' function is called, it creates a brand new 'StateSaverAndLoader' and
        // stores it inside the 'PersistentStateManager'. The subsequent calls to 'getOrCreate' pass in the saved
        // 'StateSaverAndLoader' NBT on disk to our function 'StateSaverAndLoader::createFromNbt'.
        StateSaverAndLoader state = persistentStateManager.getOrCreate(type, MiddleEarth.MOD_ID);

        // If state is not marked dirty, when Minecraft closes, 'writeNbt' won't be called and therefore nothing will be saved.
        // Technically it's 'cleaner' if you only mark state as dirty when there was actually a change, but the vast majority
        // of mod writers are just going to be confused when their data isn't being saved, and so it's best just to 'markDirty' for them.
        // Besides, it's literally just setting a bool to true, and the only time there's a 'cost' is when the file is written to disk when
        // there were no actual change to any of the mods state (INCREDIBLY RARE).
        state.markDirty();

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        // If it crashes, it means that you ain't using it correctly.
        StateSaverAndLoader serverState = getServerState(player.getWorld().getServer());

        // Either get the player by the uuid, or we don't have data for him yet, make a new player state
        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

        return playerState;
    }
}
