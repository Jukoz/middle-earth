package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.awt.*;

public class MEBiome {
    public int height;
    public Color color;
    public RegistryKey<Biome> biome;
    public Block surfaceBlock;
    public Block underSurfaceBlock;
    public Block upperStoneBlock;
    public Block stoneBlock;
    public CaveType caveType;

    public MEBiome(int height, RegistryKey<Biome> biome, Block surfaceBlock, Block underSurfaceBlock, Block stoneBlock) {
        this(height, biome, surfaceBlock, underSurfaceBlock, stoneBlock, stoneBlock, CaveType.DEFAULT);
    }

    public MEBiome(int height, RegistryKey<Biome> biome, Block surfaceBlock, Block underSurfaceBlock, Block stoneBlock, CaveType caveType) {
        this(height, biome, surfaceBlock, underSurfaceBlock, stoneBlock, stoneBlock, caveType);
    }

    public MEBiome(int height, RegistryKey<Biome> biome, Block surfaceBlock, Block underSurfaceBlock, Block upperStoneBlock, Block stoneBlock) {
        this(height, biome, surfaceBlock, underSurfaceBlock, upperStoneBlock, stoneBlock, CaveType.DEFAULT);
    }

    public MEBiome(int height, RegistryKey<Biome> biome, Block surfaceBlock, Block underSurfaceBlock, Block upperStoneBlock, Block stoneBlock, CaveType caveType) {
        this.height = height;
        this.biome = biome;
        this.surfaceBlock = surfaceBlock;
        this.underSurfaceBlock = underSurfaceBlock;
        this.upperStoneBlock = upperStoneBlock;
        this.stoneBlock = stoneBlock;
        this.caveType = caveType;
    }
}
