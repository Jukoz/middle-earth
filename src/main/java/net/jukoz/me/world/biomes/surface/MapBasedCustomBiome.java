package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.BiomeGenerationData;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class MapBasedCustomBiome {
    public static final int DEFAULT_WATER_HEIGHT = 64;
    private final RegistryKey<Biome> biomeRegistryKey;
    private final int height;
    private final int waterHeight;
    private final BiomeGenerationData biomeGenerationData;

    public MapBasedCustomBiome(RegistryKey<Biome> key, int height, BiomeGenerationData data){
        this.biomeRegistryKey = key;
        this.height = Math.min(255, Math.max(-255, height));
        this.waterHeight = DEFAULT_WATER_HEIGHT;
        this.biomeGenerationData = data;
    }
    public MapBasedCustomBiome(RegistryKey<Biome> key, int height, int waterHeight, BiomeGenerationData data){
        this.biomeRegistryKey = key;
        this.height = Math.min(255, Math.max(-255, height));
        this.waterHeight = Math.min(255, Math.max(-255, waterHeight));
        this.biomeGenerationData = data;
    }

    public MapBasedCustomBiome addHeightBasedSubBiome(RegistryKey<Biome> key, int heightThreshold){
        // TODO : Test with misties
        return this;
    }


    public RegistryKey<Biome> getBiomeKey(){
        return biomeRegistryKey;
    }
    public CustomBiome getBiome(){
        return CustomBiomesData.getBiome(biomeRegistryKey);
    }

    public int getHeight(){
        return height;
    }
    public int getWaterHeight(){
        return waterHeight;
    }

    public BiomeGenerationData getBiomeData() {
        return biomeGenerationData;
    }
}
