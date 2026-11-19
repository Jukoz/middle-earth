package net.sevenstars.middleearth.features.generation.biomes.caves;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;

import java.util.EnumSet;
import java.util.Set;

public class CaveBiomeDTO {
    RegistryKey<Biome> biome;
    Vec2f coordinates;
    Set<DepthBands.Band> bands;

    // no bands means every band
    public CaveBiomeDTO(RegistryKey<Biome> biome, Vec2f coordinates, DepthBands.Band... bands) {
        this.biome = biome;
        this.coordinates = coordinates;
        this.bands = bands.length == 0 ? EnumSet.allOf(DepthBands.Band.class) : EnumSet.of(bands[0], bands);
    }
}
