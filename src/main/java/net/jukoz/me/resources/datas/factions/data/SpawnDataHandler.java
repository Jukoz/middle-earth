package net.jukoz.me.resources.datas.factions.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.util.NbtType;
import net.jukoz.me.utils.IdentifierUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.util.*;

public class SpawnDataHandler {
    Vector2i mapViewCenter;

    /**
     * Spawns without height (Will be based on the map iteration in the future)
     */
    HashMap<Identifier, Vector2d> dynamicSpawns;

    /**
     * Spawns with every values hardcoded, mainly used for servers for custom spawns
     */
    HashMap<Identifier, Vec3d> customSpawns;


    public SpawnDataHandler(Vector2i mapViewCenter, HashMap<Identifier, Vector2d> dynamicSpawns, HashMap<Identifier, Vec3d> customSpawns){
        this.mapViewCenter = mapViewCenter;
        this.dynamicSpawns = dynamicSpawns;
        this.customSpawns = customSpawns;
    }

    public SpawnDataHandler(Optional<NbtCompound> spawnsNbt) {
        if(spawnsNbt.isEmpty()){
            return;
        }
        deserializeNbt(spawnsNbt.get());
    }

    private void deserializeNbt(NbtCompound nbtCompound) {
        // Get Map view center
        NbtCompound mapViewCenterNbt = nbtCompound.getCompound("map_view_center");
        mapViewCenter = new Vector2i(mapViewCenterNbt.getInt("x"), mapViewCenterNbt.getInt("y"));

        // Get all dynamic spawns (XZ)
        NbtList dynamicSpawnsNbtList = nbtCompound.getList("dynamic_spawns", NbtType.COMPOUND);
        dynamicSpawns = new HashMap<>();
        for(int i = 0; i < dynamicSpawnsNbtList.size(); i++){
            NbtCompound nbt = dynamicSpawnsNbtList.getCompound(i);
            Identifier id = IdentifierUtil.getIdentifierFromString(nbt.getString("id"));
            double x = nbt.getDouble("x");
            double z = nbt.getDouble("z");
            dynamicSpawns.put(id, new Vector2d(x,z));
        }

        // Get all custom spawns (XYZ)
        NbtList customSpawnsNbtList = nbtCompound.getList("custom_spawns", NbtType.COMPOUND);
        customSpawns = new HashMap<>();
        for(int i = 0; i < customSpawnsNbtList.size(); i++){
            NbtCompound nbt = customSpawnsNbtList.getCompound(i);
            Identifier id = IdentifierUtil.getIdentifierFromString(nbt.getString("id"));
            double x = nbt.getDouble("x");
            double y = nbt.getDouble("y");
            double z = nbt.getDouble("z");
            customSpawns.put(id, new Vec3d(x,y,z));
        }
    }

    public Optional<NbtCompound> serializeNbt() {
        if(mapViewCenter == null || (dynamicSpawns == null || dynamicSpawns.isEmpty()) && (customSpawns == null || customSpawns.isEmpty()))
            return Optional.empty();

        NbtCompound nbt = new NbtCompound();

        // Write Map view center
        NbtCompound mapViewCenterNbt = new NbtCompound();
        mapViewCenterNbt.putInt("x", mapViewCenter.x);
        mapViewCenterNbt.putInt("y", mapViewCenter.y);
        nbt.put("map_view_center", mapViewCenterNbt);

        // Write all dynamic spawns
        NbtList dynamicSpawnsNbt = new NbtList();
        for(Identifier key : dynamicSpawns.keySet()){
            NbtCompound compound = new NbtCompound();
            compound.putString("id", key.toString());
            compound.putDouble("x",  dynamicSpawns.get(key).x);
            compound.putDouble("z",  dynamicSpawns.get(key).y);
            dynamicSpawnsNbt.add(compound);
        }
        nbt.put("dynamic_spawns", dynamicSpawnsNbt);

        // Write all custom spawns
        NbtList customSpawnSetsNbt = new NbtList();
        for(Identifier key : customSpawns.keySet()){
            NbtCompound compound = new NbtCompound();
            compound.putString("id", key.toString());
            compound.putDouble("x",  customSpawns.get(key).getX());
            compound.putDouble("y",  customSpawns.get(key).getY());
            compound.putDouble("z",  customSpawns.get(key).getZ());
            customSpawnSetsNbt.add(compound);
        }
        nbt.put("custom_spawns", customSpawnSetsNbt);

        return Optional.of(nbt);
    }

    public Vector2d findDynamicSpawn(Identifier spawnId) {
        return dynamicSpawns.get(spawnId);
    }

    public Vec3d findCustomSpawn(Identifier spawnId) {
        return customSpawns.get(spawnId);
    }

    public static String getTranslatableKey(Identifier id){
        if(id == null)
            return null;
        return "spawn.".concat(id.toTranslationKey());
    }

    public HashMap<Identifier, Boolean> getSpawnList(){
        HashMap<Identifier, Boolean> spawnList = new HashMap<>();
        if(dynamicSpawns != null)
            for(Identifier id : dynamicSpawns.keySet()){
                spawnList.put(id, true);
            }
        if(customSpawns != null)
            for(Identifier id : customSpawns.keySet()){
                spawnList.put(id, false);
            }
        return spawnList;
    }

    public Identifier getDefaultSpawn() {
        Optional<Identifier> defaultSpawnId = getSpawnList().keySet().stream().findFirst();
        return defaultSpawnId.orElse(null);
    }
}
