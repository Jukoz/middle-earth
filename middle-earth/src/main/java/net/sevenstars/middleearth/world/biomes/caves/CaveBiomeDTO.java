package net.sevenstars.middleearth.world.biomes.caves;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;

public class CaveBiomeDTO {
    RegistryKey<Biome> biome;
    Vec2f coordinates;

    public CaveBiomeDTO(RegistryKey<Biome> biome, Vec2f coordinates) {
        this.biome = biome;
        this.coordinates = coordinates;
    }
}
