package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.BiomeColorsDTO;
import net.jukoz.me.world.biomes.BlocksLayeringData;
import net.jukoz.me.world.biomes.SlopeMap;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;


/**
 * Custom biome data used during runtime to determine what to generate
 */
public class CustomBiome {
    private final RegistryKey<Biome> biomeRegistryKey;
    private final SlopeMap slopeMap;
    private final BlocksLayeringData blocksLayering;
    private final BiomeColorsDTO biomeColors;
    private CaveType caveType;

    public CustomBiome(RegistryKey<Biome> biomeRegistryKey, SlopeMap slopeMap, BlocksLayeringData blocksLayering, BiomeColorsDTO biomeColorsDTO){
        this.biomeColors = biomeColorsDTO;
        this.biomeRegistryKey = biomeRegistryKey;
        this.slopeMap = slopeMap;
        this.blocksLayering = blocksLayering;
    }

    public CustomBiome(RegistryKey<Biome> biomeRegistryKey, SlopeMap slopeMap, BlocksLayeringData blocksLayering, BiomeColorsDTO biomeColorsDTO, CaveType caveType){
        this(biomeRegistryKey, slopeMap, blocksLayering, biomeColorsDTO);
        this.caveType = caveType;
    }

    public RegistryKey<Biome> getBiomeRegistryKey(){
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
