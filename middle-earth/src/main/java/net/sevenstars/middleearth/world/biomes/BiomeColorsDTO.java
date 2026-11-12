package net.sevenstars.middleearth.world.biomes;

public class BiomeColorsDTO {
    public int skyColor;
    public int fogColor;
    public int waterColor;
    public int waterFogColor;
    public int grassColor;
    public int foliageColor;

    public BiomeColorsDTO(BiomeColorsDTO copy) {
        this.skyColor = copy.skyColor;
        this.fogColor = copy.fogColor;
        this.waterColor = copy.waterColor;
        this.waterFogColor = copy.waterFogColor;
        this.grassColor = copy.grassColor;
        this.foliageColor = copy.foliageColor;
    }

    public BiomeColorsDTO(int grassColor, int foliageColor) {
        // Default vanilla values
        this.skyColor = BiomeSkyPresetME.CLEAR.skyColor;
        this.fogColor = BiomeSkyPresetME.CLEAR.fogColor;
        this.waterColor = 4159204;
        this.waterFogColor = 329011;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
    }

    public BiomeColorsDTO(BiomeSkyPresetME sky, int waterColor, int waterFogColor, int grassColor, int foliageColor) {
        this.skyColor = sky.skyColor;
        this.fogColor = sky.fogColor;
        this.waterColor = waterColor;
        this.waterFogColor = waterFogColor;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
    }
}
