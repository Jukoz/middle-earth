package net.sevenstars.middleearth.world.biomes;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class BiomeFogDatasME {
    public static Map<RegistryKey<Biome>, BiomeFogDatasME> DATA;
    static {
        DATA = new HashMap<>();
        DATA.put(BiomeKeyRegistryME.BARROW_DOWNS, new BiomeFogDatasME(0.0f, 0.3f));
        DATA.put(BiomeKeyRegistryME.DARK_MIRKWOOD, new BiomeFogDatasME(0.1f, 0.45f));
        DATA.put(BiomeKeyRegistryME.DARK_MIRKWOOD_EDGE, new BiomeFogDatasME(0.1f, 0.5f));
        DATA.put(BiomeKeyRegistryME.DOL_GULDUR, new BiomeFogDatasME(0f, 0.4f));
        DATA.put(BiomeKeyRegistryME.DUNLAND_FOOTHILLS, new BiomeFogDatasME(0.0f, 0.6f));
        DATA.put(BiomeKeyRegistryME.FANGORN, new BiomeFogDatasME(0.1f, 0.6f));
        DATA.put(BiomeKeyRegistryME.FORODWAITH, new BiomeFogDatasME(0f, 0.5f));
        DATA.put(BiomeKeyRegistryME.MIRKWOOD, new BiomeFogDatasME(0.1f, 0.7f));
        DATA.put(BiomeKeyRegistryME.MIRKWOOD_EDGE, new BiomeFogDatasME(0.15f, 0.8f));
        DATA.put(BiomeKeyRegistryME.MIRKWOOD_FOOTHILLS, new BiomeFogDatasME(0.2f, 0.8f));
        DATA.put(BiomeKeyRegistryME.MIRKWOOD_SWAMP, new BiomeFogDatasME(0.15f, 0.7f));
        DATA.put(BiomeKeyRegistryME.MISTY_MOUNTAINS, new BiomeFogDatasME(-0.1f, 0.2f));
        DATA.put(BiomeKeyRegistryME.MISTY_MOUNTAINS_PEAKS, new BiomeFogDatasME(-0.1f, 0.2f));
        DATA.put(BiomeKeyRegistryME.DUNLAND, new BiomeFogDatasME(0.2f, 0.95f));
        DATA.put(BiomeKeyRegistryME.THE_OLD_FOREST, new BiomeFogDatasME(0.1f, 0.6f));
    };

    public float fogStart;
    public float fogEnd;

    public BiomeFogDatasME(float fogStart, float fogEnd) {
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
    }
}
