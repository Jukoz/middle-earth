package net.sevenstars.middleearth.world.biomes.surface;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.world.biomes.BiomeColorsDTO;
import net.sevenstars.middleearth.world.biomes.BlocksLayeringData;
import net.sevenstars.middleearth.world.biomes.SlopeMap;
import net.sevenstars.middleearth.world.biomes.caves.CaveType;


/**
 * Custom biome data used during runtime to determine what to generate
 */
public class BiomeData {
    private final ResourceKey<Biome> biomeRegistryKey;
    private final SlopeMap slopeMap;
    private final BlocksLayeringData blocksLayering;
    private final BiomeColorsDTO biomeColors;
    private CaveType caveType;

    public BiomeData(ResourceKey<Biome> biomeRegistryKey, SlopeMap slopeMap, BlocksLayeringData blocksLayering, BiomeColorsDTO biomeColorsDTO){
        this.biomeColors = biomeColorsDTO;
        this.biomeRegistryKey = biomeRegistryKey;
        this.slopeMap = slopeMap;
        this.blocksLayering = blocksLayering;
    }

    public BiomeData(ResourceKey<Biome> biomeRegistryKey, SlopeMap slopeMap, BlocksLayeringData blocksLayering, BiomeColorsDTO biomeColorsDTO, CaveType caveType){
        this(biomeRegistryKey, slopeMap, blocksLayering, biomeColorsDTO);
        this.caveType = caveType;
    }

    public ResourceKey<Biome> getBiomeRegistryKey(){
        return this.biomeRegistryKey;
    }

    public SlopeMap getSlopeMap(){
        return this.slopeMap;
    }

    public BlocksLayeringData getBlocksLayering(){
        return this.blocksLayering;
    }

    public CaveType getCaveType(){
        return this.caveType;
    }

    public BiomeColorsDTO getBiomeColors(){
        return biomeColors;
    }
}
