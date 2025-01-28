package net.sevenstars.middleearth.resources;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.persistent_datas.AffiliationData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;


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
                playerNbt.putString("disposition", playerData.getCurrentDisposition().toString().toLowerCase());
                playerNbt.putString("faction_id", playerData.getCurrentFactionId().toString().toLowerCase());
                playerNbt.putString("spawn_id", playerData.getCurrentSpawnId().toString().toLowerCase());
            }

            BlockPos overworldSpawn = playerData.getOverworldSpawnCoordinates();
            if(overworldSpawn != null){
                playerNbt.putIntArray("overworld_spawn", new int[]{overworldSpawn.getX(), overworldSpawn.getY(), overworldSpawn.getZ()});
            }
            if(playerData.getRace() != null){
                playerNbt.putString("race", playerData.getRace().toString());
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
                String dispositionValue = playersNbt.getCompound(key).getString("disposition");
                boolean hasDisposition = dispositionValue != null && !dispositionValue.isEmpty();
                String factionIdValue = playersNbt.getCompound(key).getString("faction_id");
                boolean hasFaction = factionIdValue != null && !factionIdValue.isEmpty();
                String spawnIdValue = playersNbt.getCompound(key).getString("spawn_id");
                boolean hasSpawn = spawnIdValue != null && !spawnIdValue.isEmpty();
                int[] overworldPos = playersNbt.getCompound(key).getIntArray("overworld_spawn");
                boolean hasOverworldPos = overworldPos != null && overworldPos.length == 3;

                if(hasDisposition && hasFaction && hasSpawn){
                    Disposition disposition = Disposition.valueOf(dispositionValue.toUpperCase());
                    Identifier factionId = IdentifierUtil.getIdentifierFromString(factionIdValue);
                    Identifier spawnId = IdentifierUtil.getIdentifierFromString(spawnIdValue);

                    AffiliationData affiliationData = new AffiliationData(disposition.name(), factionId, spawnId);
                    playerData.setAffiliationData(affiliationData);
                }
                String raceValue = playersNbt.getCompound(key).getString("race");
                if(raceValue != null && !raceValue.isEmpty()){
                    playerData.setRace(IdentifierUtil.getIdentifierFromString(raceValue));
                }

                if(hasOverworldPos){
                    BlockPos overworldSpawn = new BlockPos(
                            overworldPos[0],
                            overworldPos[1],
                            overworldPos[2]
                    );
                    playerData.setOverworldSpawn(overworldSpawn);
                }
            } catch(Exception e){
                MiddleEarth.LOGGER.logError("StateSaverAndLoader",e);
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

    public static PlayerData getPlayerState(PlayerEntity player) {
        try {
            if(player == null){
                throw new Exception("Cannot have null as parameter");
            }
            if(player.getWorld().isClient){
                throw new Exception("Cannot be used client side");
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("StateSaverAndLoader::getPlayerState", e);
            return null;
        }


        // If it crashes, it means that you ain't using it correctly.
        StateSaverAndLoader serverState = getServerState(player.getWorld().getServer());

        // Either get the player by the uuid, or we don't have data for him yet, make a new player state
        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

        return playerState;
    }
}
