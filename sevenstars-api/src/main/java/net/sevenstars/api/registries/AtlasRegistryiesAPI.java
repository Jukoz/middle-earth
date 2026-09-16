package net.sevenstars.api.registries;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class AtlasRegistryiesAPI {
    private static final Map<Identifier, Identifier> ATLASES = new HashMap<>();

    private AtlasRegistryiesAPI() {
    }

    public static void injectAtlas(Identifier atlasId, Identifier definitionId) {
        ATLASES.put(atlasId, definitionId);
    }

    public static Map<Identifier, Identifier> getAtlases() {
        return Map.copyOf(ATLASES);
    }
}
