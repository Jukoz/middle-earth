package net.sevenstars.middleearth.world.biomes;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.sevenstars.middleearth.world.biomes.surface.MapBiomeData;
import org.jetbrains.annotations.Nullable;

// The mood of a biome. Each biome picks one of the shared moods below where it is defined,
// and can change a single part of it for itself: MIRKWOOD.withFog(0.05f, 0.45f)
public final class BiomeAmbianceME {
    public static final BiomeAmbianceME CLEAR = sky(0x78A7FF, 0xC0D8FF);
    public static final BiomeAmbianceME HIGHLAND = sky(0x7DA2FF, 0xC0D8FF);
    public static final BiomeAmbianceME WATER = sky(0x7BA4FF, 0xC0D8FF);
    public static final BiomeAmbianceME SOUTHERN = sky(0x6EB1FF, 0xC4DFFF);
    // The Shire & the downs around it
    public static final BiomeAmbianceME GENTLE = sky(0x84ACF5, 0xC0D4F2);
    // Lindon, the Blue Mountains, Eregion & Rhun
    public static final BiomeAmbianceME LUMINOUS = sky(0x86B0FE, 0xC2DFFF);
    public static final BiomeAmbianceME DALE = sky(0x7D97FF, 0xB9D2FF);
    public static final BiomeAmbianceME FROZEN = sky(0x83A7FF, 0xA1BDE6);
    public static final BiomeAmbianceME DUNLAND = sky(0x7290E9, 0xC5D1C6);
    // Empty lands of Enedwaith & Rhudaur
    public static final BiomeAmbianceME SUBDUED = sky(0x7290E9, 0xA5C1EE);
    // Shaded vales & the eastern plains
    public static final BiomeAmbianceME SOFT = sky(0x7590E4, 0xBCCDEC);
    // Emyn Muil & old forests
    public static final BiomeAmbianceME DUSKY = sky(0x6A82C8, 0x9EB8E0);
    // Angmar, Nurn & the northern wilds
    public static final BiomeAmbianceME COLD_HAZE = sky(0x88A0DD, 0xB3C4DF);
    // Lands spoiled by Mordor
    public static final BiomeAmbianceME BLIGHTED = sky(0x86A0CA, 0xA8B7C2);
    // Marshes, troll country & the pools of the wastes under cloud
    public static final BiomeAmbianceME OVERCAST = sky(0x9CA8AB, 0x8D999B);
    public static final BiomeAmbianceME SNOWSTORM = sky(0xB4C0CC, 0xC6D0D8).withFog(0.0f, 0.35f);
    public static final BiomeAmbianceME GREY = sky(0xB9B9B9, 0x817770);
    public static final BiomeAmbianceME GUNDABAD = sky(0xACA9B1, 0xA5A5B1);
    public static final BiomeAmbianceME DEAD_MARSHES = sky(0xB5B5B3, 0x6C767A).withFog(0.0f, 0.45f);
    public static final BiomeAmbianceME LORIEN_GOLD = sky(0x78A7FF, 0xD1CA75);
    public static final BiomeAmbianceME LORIEN_EDGE = sky(0x78A7FF, 0xAECA81);
    public static final BiomeAmbianceME WOODLAND_REALM = sky(0x86AEFE, 0xBACEC5);
    public static final BiomeAmbianceME WOODLAND_AUTUMN = sky(0x86AEFE, 0x7E8B80);
    public static final BiomeAmbianceME MIRKWOOD_EDGE = sky(0x73A4B0, 0x78827C).withFog(0.15f, 0.8f);
    public static final BiomeAmbianceME MIRKWOOD = sky(0x6883A6, 0x677468);
    public static final BiomeAmbianceME DARK_MIRKWOOD = sky(0x50698C, 0x49584B);
    public static final BiomeAmbianceME MIRKWOOD_MARSH = sky(0x6A87A0, 0x869CA2);
    public static final BiomeAmbianceME DOL_GULDUR = sky(0x383A70, 0x4D4955);
    public static final BiomeAmbianceME DAGORLAD = sky(0x6E76A0, 0x747581);
    // Brown Lands & the wastes before Mordor
    public static final BiomeAmbianceME WASTELAND = sky(0x535050, 0x4C4848);
    public static final BiomeAmbianceME MORGUL = sky(0x445A52, 0x38513D).withFog(0.0f, 0.5f);
    // Plains of Mordor
    public static final BiomeAmbianceME ASH = sky(0x292023, 0x1D151B);
    // Mountains of Mordor & Udun
    public static final BiomeAmbianceME ASH_MOUNTAINS = sky(0x3F3636, 0x2F2727);

    private final int skyColor;
    private final int fogColor;
    @Nullable
    private final Fog fog;

    private BiomeAmbianceME(int skyColor, int fogColor, @Nullable Fog fog) {
        this.skyColor = requireColor("sky", skyColor);
        this.fogColor = requireColor("fog", fogColor);
        this.fog = fog;
    }

    public static BiomeAmbianceME sky(int skyColor, int fogColor) {
        return new BiomeAmbianceME(skyColor, fogColor, null);
    }

    public BiomeAmbianceME withSky(int skyColor, int fogColor) {
        return new BiomeAmbianceME(skyColor, fogColor, fog);
    }

    // Both values are fractions of the view distance. A negative start thickens the fog at the camera
    public BiomeAmbianceME withFog(float start, float end) {
        return new BiomeAmbianceME(skyColor, fogColor, new Fog(start, end));
    }

    public BiomeAmbianceME withoutFog() {
        return new BiomeAmbianceME(skyColor, fogColor, null);
    }

    public int getSkyColor() {
        return skyColor;
    }

    public int getFogColor() {
        return fogColor;
    }

    @Nullable
    public Fog getFog() {
        return fog;
    }

    // The fog distance is not part of the biome file, the fog mixin reads it through of()
    public void applyTo(BiomeEffects.Builder effects) {
        effects.skyColor(skyColor).fogColor(fogColor);
    }

    // Null for caves & biomes of other dimensions
    @Nullable
    public static BiomeAmbianceME of(RegistryKey<Biome> biome) {
        return MapBiomeData.findAmbiance(biome);
    }

    private static int requireColor(String name, int color) {
        if(color < 0 || color > 0xFFFFFF)
            throw new IllegalArgumentException("The %s colour 0x%X is not a RGB colour".formatted(name, color));
        return color;
    }

    public record Fog(float start, float end) {
        public Fog {
            if(!(end > 0 && end <= 1))
                throw new IllegalArgumentException("The fog end %s must be above 0 and at most 1".formatted(end));
            if(!(start < end && start >= -1))
                throw new IllegalArgumentException("The fog start %s must be at least -1 and below the end %s".formatted(start, end));
        }
    }
}
