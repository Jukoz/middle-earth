package net.sevenstars.middleearth.features.generation.biomes.caves;


import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class CaveBiomesMap {
    ArrayList<CaveBiomeDTO> caves;

    public CaveBiomesMap() {
        this.caves = new ArrayList<>();
    }

    public void addCave(CaveBiomeDTO caveBiomeDTO) {
        caves.add(caveBiomeDTO);
    }

    // falls back to the whole palette when nothing is allowed in the band
    public RegistryKey<Biome> getClosestBiome(Vec2f coordinates, DepthBands.Band band) {
        RegistryKey<Biome> biome = closest(coordinates, band);
        return biome != null ? biome : closest(coordinates, null);
    }

    private RegistryKey<Biome> closest(Vec2f coordinates, DepthBands.Band band) {
        RegistryKey<Biome> biome = null;
        float currentDistance = 10.0f;
        for (CaveBiomeDTO biomeDTO: caves) {
            if(band != null && !biomeDTO.bands.contains(band)) continue;
            float distance = biomeDTO.coordinates.distanceSquared(coordinates);
            if(distance < currentDistance) {
                currentDistance = distance;
                biome = biomeDTO.biome;
            }
        }
        return biome;
    }
}
