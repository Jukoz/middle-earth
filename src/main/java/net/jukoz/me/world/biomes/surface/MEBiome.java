package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.BiomeGenerationData;
import net.jukoz.me.world.biomes.BlocksLayeringData;
import net.jukoz.me.world.biomes.SlopeMap;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.awt.*;

public class MEBiome {
    public int height;
    public Color color;
    public RegistryKey<Biome> biome;

    public BiomeGenerationData biomeGenerationData;
    public SlopeMap slopeMap;
    public BlocksLayeringData blocksLayering;

    public CaveType caveType;

    public MEBiome(int height, RegistryKey<Biome> biome, BiomeGenerationData biomeGenerationData, SlopeMap slopeMap, BlocksLayeringData blocksLayering) {
        this(height, biome, biomeGenerationData, slopeMap, blocksLayering, CaveType.DEFAULT);
    }

    public MEBiome(int height, RegistryKey<Biome> biome, BiomeGenerationData biomeGenerationData, SlopeMap slopeMap, BlocksLayeringData blocksLayering, CaveType caveType) {
        this.height = height;
        this.biome = biome;
        this.biomeGenerationData = biomeGenerationData;
        this.slopeMap = slopeMap;
        this.blocksLayering = blocksLayering;
        this.caveType = caveType;
    }
}
