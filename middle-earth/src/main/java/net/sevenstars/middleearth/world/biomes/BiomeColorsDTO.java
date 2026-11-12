package net.sevenstars.middleearth.world.biomes;

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

    public BiomeEffects.Builder createEffects() {
        BiomeEffects.Builder effects = new BiomeEffects.Builder()
                .waterColor(waterColor)
                .waterFogColor(waterFogColor)
                .grassColor(grassColor)
                .foliageColor(foliageColor);
        ambiance.applyTo(effects);
        return effects;
    }
}
