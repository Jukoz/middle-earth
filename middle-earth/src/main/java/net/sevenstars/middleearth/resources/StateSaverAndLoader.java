package net.sevenstars.middleearth.resources;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.apache.commons.lang3.SerializationUtils;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
/**
 * <a href="https://fabricmc.net/wiki/tutorial:persistent_states">Documentation</a><br/>
 * <a href="https://github.com/TerraformersMC/Biolith/blob/main/common/src/main/java/com/terraformersmc/biolith/impl/config/BiolithState.java">Other Source</a>
 */
public class StateSaverAndLoader extends PersistentState {
    private static final String PLAYERS = "Players";

    private HashMap<UUID, PlayerData> players;

    public StateSaverAndLoader(ByteBuffer players) {
        HashMap<UUID, PlayerData> playerDataHashMap = SerializationUtils.deserialize(players.array());
        this.players = playerDataHashMap;
    }

    public StateSaverAndLoader(Context context) {
        this.players = new HashMap<>();
    }

    private static Codec<StateSaverAndLoader> getCodec(PersistentState.Context context) {
        return RecordCodecBuilder.create((instance) -> instance.group(
                Codec.BYTE_BUFFER.fieldOf(PLAYERS).forGetter(StateSaverAndLoader::getPlayerDataByteBuffer)
        ).apply(instance, StateSaverAndLoader::new));
    }

    public static PersistentStateType<StateSaverAndLoader> getPersistentStateType() {
        return new PersistentStateType<>(
                MiddleEarth.MOD_ID + "_" + "player_datas",
                StateSaverAndLoader::new,
                StateSaverAndLoader::getCodec,
                null
        );
    }

    public ByteBuffer getPlayerDataByteBuffer() {
        return ByteBuffer.wrap(SerializationUtils.serialize(players));
    }

    private static final PersistentStateType<StateSaverAndLoader> TYPE;

    public static final Codec<StateSaverAndLoader> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            NbtCompound.CODEC.fieldOf("player_datas").forGetter(StateSaverAndLoader::getPlayerDataNbt)
    ).apply(instance, StateSaverAndLoader::new));


    public StateSaverAndLoader() {
        this.players = new HashMap();
    }
    public StateSaverAndLoader(NbtCompound nbt){
        this.players = new HashMap();
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.players = new HashMap();
        return state;
    }

    private NbtCompound getPlayerDataNbt() {
        return new NbtCompound();
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(TYPE);
        state.markDirty();
        return state;
    }
    public static PlayerData getPlayerState(PlayerEntity player) {
        StateSaverAndLoader serverState = getServerState(Objects.requireNonNull(player.getWorld().getServer()));
        if (serverState.players.get(player.getUuid()) == null) {
            // Create player from scratch
        }
        return serverState.players.get(player.getUuid());
    }


    static {
        TYPE = new PersistentStateType<>("middle_earth_player_datas", StateSaverAndLoader::createNew, CODEC, (DataFixTypes) null);
    }

    /*

    private NbtCompound getPlayerDataNbt() {
        return new NbtCompound();
    }


    private static PersistentStateType<StateSaverAndLoader> type = new PersistentStateType<>(
            MiddleEarth.MOD_ID,
            StateSaverAndLoader::new,
            StateSaverAndLoader::getCodec,
            null
    );


    public static void registerPayloads(){
        PayloadTypeRegistry.playS2C().register(PlayerFactionPayload.ID, PlayerFactionPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(PlayerFactionSpawnPayload.ID, PlayerFactionSpawnPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(PlayerOriginSpawnPayload.ID, PlayerOriginSpawnPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(PlayerRacePayload.ID, PlayerRacePayload.CODEC);
    }

    public static void registerGlobalReceivers(){
        ClientPlayNetworking.registerGlobalReceiver(PlayerFactionPayload.ID, PlayerFactionPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(PlayerFactionSpawnPayload.ID, PlayerFactionSpawnPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(PlayerOriginSpawnPayload.ID, PlayerOriginSpawnPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(PlayerRacePayload.ID, PlayerRacePayload::handle);
    }


    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
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
        //...
        return state;
    }

    /*
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
     */
}






