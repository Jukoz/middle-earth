package net.jukoz.me.world.biomes;

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
        this.skyColor = 7907327;
        this.fogColor = 12638463;
        this.waterColor = 4159204;
        this.waterFogColor = 329011;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
    }

    public BiomeColorsDTO(int skyColor, int fogColor, int waterColor, int waterFogColor, int grassColor, int foliageColor) {
        this.skyColor = skyColor;
        this.fogColor = fogColor;
        this.waterColor = waterColor;
        this.waterFogColor = waterFogColor;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
    }
}
