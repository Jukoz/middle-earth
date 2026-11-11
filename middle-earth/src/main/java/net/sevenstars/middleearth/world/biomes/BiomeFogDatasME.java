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
        DATA.put(BiomeKeyRegistryME.CARADHRAS, new BiomeFogDatasME(0.0f, 0.35f));
        DATA.put(BiomeKeyRegistryME.CARADHRAS_PEAKS, new BiomeFogDatasME(0.0f, 0.35f));
        DATA.put(BiomeKeyRegistryME.DEAD_MARSHES, new BiomeFogDatasME(0.0f, 0.45f));
        DATA.put(BiomeKeyRegistryME.DEAD_MARSHES_WATER, new BiomeFogDatasME(0.0f, 0.45f));
        DATA.put(BiomeKeyRegistryME.NINDALF, new BiomeFogDatasME(0.05f, 0.6f));
        DATA.put(BiomeKeyRegistryME.LONG_MARSHES, new BiomeFogDatasME(0.1f, 0.7f));
        DATA.put(BiomeKeyRegistryME.MIRKWOOD_MARSHES, new BiomeFogDatasME(0.1f, 0.6f));
        DATA.put(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_MARSHES, new BiomeFogDatasME(0.1f, 0.7f));
        DATA.put(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_SWAMP, new BiomeFogDatasME(0.1f, 0.7f));
        DATA.put(BiomeKeyRegistryME.WEBBED_WOODS, new BiomeFogDatasME(0.1f, 0.6f));
        DATA.put(BiomeKeyRegistryME.WEBBED_DARK_WOODS, new BiomeFogDatasME(0.05f, 0.45f));
        DATA.put(BiomeKeyRegistryME.MORGUL_VALE, new BiomeFogDatasME(0.0f, 0.5f));
        DATA.put(BiomeKeyRegistryME.MORGUL_RIVER, new BiomeFogDatasME(0.0f, 0.5f));
        DATA.put(BiomeKeyRegistryME.MORGUL_FOREST, new BiomeFogDatasME(0.05f, 0.55f));
        DATA.put(BiomeKeyRegistryME.TROLLSHAWS, new BiomeFogDatasME(0.15f, 0.75f));
        DATA.put(BiomeKeyRegistryME.MORDOR, new BiomeFogDatasME(0.2f, 0.85f));
        DATA.put(BiomeKeyRegistryME.MORDOR_HILL, new BiomeFogDatasME(0.2f, 0.85f));
        DATA.put(BiomeKeyRegistryME.MORDOR_WASTES, new BiomeFogDatasME(0.2f, 0.85f));
        DATA.put(BiomeKeyRegistryME.MORDOR_ASHEN_FOREST, new BiomeFogDatasME(0.2f, 0.8f));
        DATA.put(BiomeKeyRegistryME.UDUN, new BiomeFogDatasME(0.15f, 0.75f));
        DATA.put(BiomeKeyRegistryME.GORGOROTH, new BiomeFogDatasME(0.15f, 0.75f));
        DATA.put(BiomeKeyRegistryME.GORGOROTH_DELTA, new BiomeFogDatasME(0.15f, 0.75f));
        DATA.put(BiomeKeyRegistryME.GORGOROTH_ASHEN_WOODS, new BiomeFogDatasME(0.15f, 0.75f));
        DATA.put(BiomeKeyRegistryME.MOUNT_DOOM, new BiomeFogDatasME(0.1f, 0.6f));
        DATA.put(BiomeKeyRegistryME.MOUNT_DOOM_PIT, new BiomeFogDatasME(0.1f, 0.6f));
    };

    public float fogStart;
    public float fogEnd;

    public BiomeFogDatasME(float fogStart, float fogEnd) {
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
    }
}
