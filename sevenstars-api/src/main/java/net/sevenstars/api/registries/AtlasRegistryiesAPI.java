package net.sevenstars.api.registries;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class AtlasRegistryiesAPI {
    private static Map<Identifier, Identifier> atlases;

    public static void injectAtlas(Identifier key, Identifier value){
        if(atlases == null)
            atlases = new HashMap<>();
        atlases.put(key, value);
    }

    public static Map<Identifier, Identifier> getAtlases() {
        if(atlases == null){
            return new HashMap<>();
        }
        return atlases;
    }
}
