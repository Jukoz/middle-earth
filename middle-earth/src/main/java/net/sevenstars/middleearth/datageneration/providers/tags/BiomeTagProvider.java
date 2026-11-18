package net.sevenstars.middleearth.datageneration.providers.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.tags.BiomeType;
import net.sevenstars.middleearth.datageneration.content.tags.BiomeTypes;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BiomeTagProvider extends FabricTagProvider<Biome> {

    public BiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        List<RegistryKey<Biome>> biomes = lookup.getOrThrow(RegistryKeys.BIOME).streamKeys()
                .filter(key -> key.getValue().getNamespace().equals(MiddleEarth.MOD_ID))
                .sorted(Comparator.comparing(key -> key.getValue().getPath()))
                .toList();

        for (RegistryKey<Biome> biome : BiomeTypes.declaredBiomes()) {
            if (!biomes.contains(biome)) throw new IllegalStateException("Biome types declared for unregistered biome " + biome.getValue());
        }

        for (RegistryKey<Biome> biome : biomes) {
            for (BiomeType type : BiomeTypes.of(biome)) {
                builder(type.tag).add(biome);
            }
        }
    }
}
