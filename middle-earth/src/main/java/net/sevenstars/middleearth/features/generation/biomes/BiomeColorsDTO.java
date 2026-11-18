package net.sevenstars.middleearth.features.generation.biomes;

import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;

import java.util.Objects;

public class BiomeColorsDTO {
    public BiomeAmbianceME ambiance;
    public int waterColor;
    public int waterFogColor;
    public int grassColor;
    public int foliageColor;

    public BiomeColorsDTO(BiomeColorsDTO copy) {
        this.ambiance = copy.ambiance;
        this.waterColor = copy.waterColor;
        this.waterFogColor = copy.waterFogColor;
        this.grassColor = copy.grassColor;
        this.foliageColor = copy.foliageColor;
    }

    public BiomeColorsDTO(BiomeAmbianceME ambiance, int waterColor, int waterFogColor, int grassColor, int foliageColor) {
        this.ambiance = Objects.requireNonNull(ambiance, "A biome needs an ambiance");
        this.waterColor = waterColor;
        this.waterFogColor = waterFogColor;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
    }

    public Biome.Builder createBiomeBuilder() {
        Biome.Builder biome = new Biome.Builder()
                .effects(new BiomeEffects.Builder()
                        .waterColor(waterColor)
                        .grassColor(grassColor)
                        .foliageColor(foliageColor)
                        .build())
                .setEnvironmentAttribute(EnvironmentAttributes.WATER_FOG_COLOR_VISUAL, ColorHelper.fullAlpha(waterFogColor));
        ambiance.applyTo(biome);
        return biome;
    }
}
