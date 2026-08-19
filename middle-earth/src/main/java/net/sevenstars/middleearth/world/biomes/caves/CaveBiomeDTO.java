package net.sevenstars.middleearth.world.biomes.caves;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec2;

public class CaveBiomeDTO {
    ResourceKey<Biome> biome;
    Vec2 coordinates;

    public CaveBiomeDTO(ResourceKey<Biome> biome, Vec2 coordinates) {
        this.biome = biome;
        this.coordinates = coordinates;
    }
}
