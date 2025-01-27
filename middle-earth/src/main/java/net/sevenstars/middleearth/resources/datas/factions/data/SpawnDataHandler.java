package net.sevenstars.middleearth.resources.datas.factions.data;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector2i;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SpawnDataHandler {
    Vector2i mapViewCenter;

    HashMap<Identifier, SpawnData> spawns;

    public SpawnDataHandler(List<SpawnData> spawnDatas){
        spawns = new HashMap<>();
        for(SpawnData spawn : spawnDatas){
            spawns.put(spawn.getIdentifier(), spawn);
        }
    }

    public SpawnDataHandler(Optional<NbtCompound> spawnsNbt) {
        if(spawnsNbt.isEmpty()){
            return;
        }
        deserializeNbt(spawnsNbt.get());
    }

    private void deserializeNbt(NbtCompound nbtCompound) {
        NbtList compoundList = nbtCompound.getList("data", NbtType.COMPOUND);
        spawns = new HashMap<>();
        for(int i = 0; i < compoundList.size(); i++){
            SpawnData spawnData = SpawnData.deserialize(compoundList.getCompound(i));
            spawns.put(spawnData.getIdentifier(), spawnData);
        }
    }

    public Optional<NbtCompound> serializeNbt() {
        if((spawns == null || spawns.isEmpty()))
            return Optional.empty();

        NbtCompound nbt = new NbtCompound();
        NbtList spawnDataList = new NbtList();
        for(SpawnData spawnData : spawns.values()){
            spawnDataList.add(SpawnData.serialize(spawnData));
        }
        nbt.put("data", spawnDataList);
        return Optional.of(nbt);
    }

    public SpawnData findSpawn(Identifier spawnId) {
        return spawns.get(spawnId);
    }

    public static String getTranslatableKey(Identifier id){
        if(id == null)
            return null;
        return "spawn.".concat(id.toTranslationKey());
    }

    public List<SpawnData> getSpawnList(){
        if(spawns == null || spawns.isEmpty())
            return null;
        return spawns.values().stream().toList();
    }

    public List<Identifier> getAllSpawnIdentifiers(){
        if(spawns == null || spawns.isEmpty())
            return null;
        return spawns.keySet().stream().toList();
    }

    public Identifier getDefaultSpawn() {
        if(spawns == null || spawns.isEmpty())
            return null;
        return spawns.keySet().stream().toList().getFirst();
    }

    public BlockPos getSpawnBlockPos(Identifier spawnId) {
        if(spawns == null || spawns.isEmpty())
            return null;
        SpawnData data = spawns.get(spawnId);
        if(data == null) return null;
        return data.getBlockPos();
    }
}
