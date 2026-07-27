package net.sevenstars.middleearth.world.biomes.surface;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.world.biomes.BiomeGenerationData;

public class MapBasedCustomBiome {
    public static final int DEFAULT_WATER_HEIGHT = 64;
    private final ResourceKey<Biome> biomeRegistryKey;
    private final byte  height;
    private final byte waterHeight;
    private final BiomeGenerationData biomeGenerationData;
    private final BiomeData biomeData;

    public MapBasedCustomBiome(ResourceKey<Biome> key, int height, BiomeGenerationData data){
        this.biomeRegistryKey = key;
        this.height = (byte) height;
        this.waterHeight = DEFAULT_WATER_HEIGHT;
        this.biomeGenerationData = data;
        this.biomeData = MapBiomeData.getBiome(key);
    }
    public MapBasedCustomBiome(ResourceKey<Biome> key, int height, int waterHeight, BiomeGenerationData data){
        this.biomeRegistryKey = key;
        this.height = (byte) height;
        this.waterHeight = (byte) waterHeight;
        this.biomeGenerationData = data;
        this.biomeData = MapBiomeData.getBiome(key);
    }

    public MapBasedCustomBiome addHeightBasedSubBiome(ResourceKey<Biome> key, int heightThreshold){
        // TODO : Test with misties
        return this;
    }


    public ResourceKey<Biome> getBiomeKey(){
        return biomeRegistryKey;
    }
    public BiomeData getBiome(){
        return biomeData;
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
