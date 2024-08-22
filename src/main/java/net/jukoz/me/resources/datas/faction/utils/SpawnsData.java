package net.jukoz.me.resources.datas.faction.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

import java.util.*;

public class SpawnsData {
    Vector2i mapViewCenter;
    HashMap<Identifier, Vector2i> dynamicSpawns;
    HashMap<Identifier, Vec3d> customSpawns;


    public SpawnsData(Vector2i mapViewCenter, HashMap<Identifier, Vector2i> dynamicSpawns, HashMap<Identifier, Vec3d> customSpawns){
        this.mapViewCenter = mapViewCenter;
        this.dynamicSpawns = dynamicSpawns;
        this.customSpawns = customSpawns;
    }

    public SpawnsData(Optional<NbtCompound> spawnsNbt) {
        if(spawnsNbt.isEmpty()){
            return;
        }
        NbtCompound compound = spawnsNbt.get();

        NbtCompound mapViewCenterNbt = compound.getCompound("map_view_center");
        mapViewCenter = new Vector2i(mapViewCenterNbt.getInt("x"), mapViewCenterNbt.getInt("y"));

        JsonParser jsonParser = new JsonParser();
        NbtList dynamicSpawnsNbtList = compound.getList("dynamic_spawns", NbtElement.COMPOUND_TYPE);
        dynamicSpawns = new HashMap<>();
        String intRegex = "[^0-9]";
        for(NbtElement element: dynamicSpawnsNbtList){
            JsonObject json = (JsonObject) jsonParser.parse(element.asString());
            Identifier id = Identifier.of(json.get("id").getAsString());
            int x = Integer.parseInt(json.get("x").getAsString().replaceAll(intRegex, ""));
            int z = Integer.parseInt(json.get("z").getAsString().replaceAll(intRegex, ""));
            Vector2i coordinate = new Vector2i(x,z);
            dynamicSpawns.put(id, coordinate);
        }
        NbtList customSpawnsNbtList = compound.getList("custom_spawns", NbtElement.COMPOUND_TYPE);
        customSpawns = new HashMap<>();
        String doubleRegex = "[^0-9.]";
        for(NbtElement element: customSpawnsNbtList){
            JsonObject json = (JsonObject) jsonParser.parse(element.asString());
            Identifier id = Identifier.of(json.get("id").getAsString());
            double x = Double.parseDouble(json.get("x").getAsString().replaceAll(doubleRegex, ""));
            double y = Double.parseDouble(json.get("y").getAsString().replaceAll(doubleRegex, ""));
            double z = Double.parseDouble(json.get("z").getAsString().replaceAll(doubleRegex, ""));
            Vec3d coordinate = new Vec3d(x,y,z);
            customSpawns.put(id, coordinate);
        }
    }

    public Optional<NbtCompound> getNbt() {
        if(mapViewCenter == null || (dynamicSpawns == null || dynamicSpawns.isEmpty()) && (customSpawns == null || customSpawns.isEmpty()))
            return Optional.empty();

        NbtCompound nbt = new NbtCompound();

        NbtCompound mapViewCenterNbt = new NbtCompound();
        mapViewCenterNbt.putInt("x", mapViewCenter.x);
        mapViewCenterNbt.putInt("y", mapViewCenter.y);
        nbt.put("map_view_center", mapViewCenterNbt);

        NbtList dynamicSpawnsNbt = new NbtList();
        for(Identifier key : dynamicSpawns.keySet()){
            NbtCompound compound = new NbtCompound();
            compound.putString("id", key.toString());
            compound.putInt("x",  dynamicSpawns.get(key).x);
            compound.putInt("z",  dynamicSpawns.get(key).y);
            dynamicSpawnsNbt.add(compound);
        }
        nbt.put("dynamic_spawns", dynamicSpawnsNbt);

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

    public HashMap<Identifier, Vector2i> getDynamicSpawns(){
        return dynamicSpawns;
    }

    public HashMap<Identifier, Vec3d> getCustomSpawns(){
        return customSpawns;
    }

    public Vector2i findDynamicSpawn(Identifier spawnId) {
        return dynamicSpawns.get(spawnId);
    }

    public Vec3d findCustomSpawn(Identifier spawnId) {
        return customSpawns.get(spawnId);
    }

    public static String getTranslatableKey(Identifier id){
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
}
