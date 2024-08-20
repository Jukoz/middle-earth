package net.jukoz.me.resources.datas.faction.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpawnsData {
    Vector2i mapViewCenter;
    HashMap<Identifier, Vector2i> dynamicSpawns;
    HashMap<Identifier, Vec3d> customSpawns;


    public SpawnsData(NbtCompound spawnsNbt) {
        NbtCompound mapViewCenterNbt = spawnsNbt.getCompound("map_view_center");
        mapViewCenter = new Vector2i(mapViewCenterNbt.getInt("x"), mapViewCenterNbt.getInt("y"));

        JsonParser jsonParser = new JsonParser();
        NbtList dynamicSpawnsNbtList = spawnsNbt.getList("dynamic_spawns", NbtElement.COMPOUND_TYPE);
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
        NbtList customSpawnsNbtList = spawnsNbt.getList("custom_spawns", NbtElement.COMPOUND_TYPE);
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

    public NbtCompound getNbt() {
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

        return nbt;
    }

    public List<Vector3i> getTemporaryCoordinate() {
        List<Vector3i> temporaryList = new ArrayList<>();
        Vector2i dynamicCoord = dynamicSpawns.values().stream().toList().get(0);
        temporaryList.add(new Vector3i(dynamicCoord.x, 0, dynamicCoord.y));
        return temporaryList;
    }
}
