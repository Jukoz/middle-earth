package net.jesteur.me.world.biomes;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.joml.Vector4f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MEBiomeFogData {
    public static Map<RegistryKey<Biome>, MEBiomeFogData> DATA;
    static {
        DATA = new HashMap<>();
        DATA.put(MEBiomeKeys.ERIADOR, new MEBiomeFogData(0.9f, 0.95f));
        DATA.put(MEBiomeKeys.BARROW_DOWNS, new MEBiomeFogData(0.1f, 0.3f));
        DATA.put(MEBiomeKeys.DARK_MIRKWOOD, new MEBiomeFogData(0.3f, 0.6f));
        DATA.put(MEBiomeKeys.DARK_MIRKWOOD_EDGE, new MEBiomeFogData(0.45f, 0.7f));
        DATA.put(MEBiomeKeys.DOL_GULDUR, new MEBiomeFogData(0.5f, 0.6f));
        DATA.put(MEBiomeKeys.DUNLAND_FOOTHILLS, new MEBiomeFogData(0.5f, 0.6f));
        DATA.put(MEBiomeKeys.FANGORN, new MEBiomeFogData(0.4f, 0.6f));
        DATA.put(MEBiomeKeys.FORODWAITH, new MEBiomeFogData(0.3f, 0.5f));
        DATA.put(MEBiomeKeys.MIRKWOOD, new MEBiomeFogData(0.5f, 0.7f));
        DATA.put(MEBiomeKeys.MIRKWOOD_EDGE, new MEBiomeFogData(0.6f, 0.8f));
        DATA.put(MEBiomeKeys.MIRKWOOD_FOOTHILLS, new MEBiomeFogData(0.8f, 0.9f));
        DATA.put(MEBiomeKeys.MISTY_MOUNTAINS, new MEBiomeFogData(0.0f, 0.2f));
        DATA.put(MEBiomeKeys.NORTHERN_DUNLAND, new MEBiomeFogData(0.6f, 0.8f));
        DATA.put(MEBiomeKeys.THE_OLD_FOREST, new MEBiomeFogData(0.4f, 0.6f));
    };

    public float fogStart;
    public float fogEnd;

    public MEBiomeFogData(float fogStart, float fogEnd) {
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
    }
}
