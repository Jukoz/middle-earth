package net.sevenstars.middleearth.world.biomes;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

/**
 * Shared sky & fog colours for groups of biomes. A biome listed here uses the preset instead of its own sky & fog colours.
 */
public enum BiomeSkyPresetME {
    OVERCAST(0x9CA8AB, 0x8D999B,
            BiomeKeyRegistryME.NINDALF,
            BiomeKeyRegistryME.LONG_MARSHES,
            BiomeKeyRegistryME.TROLLSHAWS),
    SNOWSTORM(0xB4C0CC, 0xC6D0D8,
            BiomeKeyRegistryME.CARADHRAS,
            BiomeKeyRegistryME.CARADHRAS_PEAKS);

    private static final Map<RegistryKey<Biome>, BiomeSkyPresetME> PRESET_BY_BIOME = new HashMap<>();

    static {
        for(BiomeSkyPresetME preset : values()) {
            for(RegistryKey<Biome> biome : preset.biomes) {
                if(PRESET_BY_BIOME.put(biome, preset) != null)
                    throw new IllegalStateException("%s has several sky presets".formatted(biome.getValue()));
            }
        }
    }

    private final int skyColor;
    private final int fogColor;
    private final RegistryKey<Biome>[] biomes;

    @SafeVarargs
    BiomeSkyPresetME(int skyColor, int fogColor, RegistryKey<Biome>... biomes) {
        this.skyColor = skyColor;
        this.fogColor = fogColor;
        this.biomes = biomes;
    }

    public static void apply(RegistryKey<Biome> biome, BiomeColorsDTO colors) {
        BiomeSkyPresetME preset = PRESET_BY_BIOME.get(biome);
        if(preset == null) return;
        colors.skyColor = preset.skyColor;
        colors.fogColor = preset.fogColor;
    }
}
