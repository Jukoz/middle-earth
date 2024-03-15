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
    public Block stoneBlock;
    public CaveType caveType;

    public MEBiome(int height, RegistryKey<Biome> biome, Block surfaceBlock, Block underSurfaceBlock, Block stoneBlock) {
        this.height = height;
        this.biome = biome;
        this.surfaceBlock = surfaceBlock;
        this.underSurfaceBlock = underSurfaceBlock;
        this.stoneBlock = stoneBlock;
        this.caveType = CaveType.DEFAULT;
    }

    public MEBiome(int height, RegistryKey<Biome> biome, Block surfaceBlock, Block underSurfaceBlock, Block stoneBlock, CaveType caveType) {
        this.height = height;
        this.biome = biome;
        this.surfaceBlock = surfaceBlock;
        this.underSurfaceBlock = underSurfaceBlock;
        this.stoneBlock = stoneBlock;
        this.caveType = caveType;
    }
}
