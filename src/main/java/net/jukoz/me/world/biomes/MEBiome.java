package net.jukoz.me.world.biomes;

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
    public Block deepStoneBlock;

    public MEBiome(int height, RegistryKey<Biome> biome, Block surfaceBlock, Block underSurfaceBlock, Block stoneBlock, Block deepStoneBlock) {
        this.height = height;
        this.biome = biome;
        this.surfaceBlock = surfaceBlock;
        this.underSurfaceBlock = underSurfaceBlock;
        this.stoneBlock = stoneBlock;
        this.deepStoneBlock = deepStoneBlock;
    }
}
