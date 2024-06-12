package net.jukoz.me.world.biomes.surface;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.world.chunkgen.ProceduralStructures;
import net.jukoz.me.world.features.tree.trunks.CanopyTrunkPlacer;
import net.jukoz.me.world.map.MiddleEarthMapRuntime;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.utils.noises.SimplexNoise;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.jukoz.me.world.biomes.caves.ModCaveBiomes;
import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
import net.minecraft.block.AbstractBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.CheckerboardBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ModBiomeSource extends BiomeSource {

    public static final MapCodec<ModBiomeSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(Biome.REGISTRY_CODEC).fieldOf("biomes").forGetter((biomeSource) -> biomeSource.biomes)).apply(instance, ModBiomeSource::new));

    private final List<RegistryEntry<Biome>> biomes;
    private final int CAVE_NOISE = 96;
    private final int CAVE_OFFSET = 7220;
    private MiddleEarthMapRuntime middleEarthMapRuntime;
    public ModBiomeSource(List<RegistryEntry<Biome>> biomes) {
        this.biomes = biomes;
        middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
    }

    @Override
    protected MapCodec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return biomes.stream();
    }

    private RegistryKey<Biome> getCaveBiome(int x, int z, MEBiome surfaceBiome) {
        float temperature = (float) SimplexNoise.noise((double) x / CAVE_NOISE,  (double) z / CAVE_NOISE);
        float humidity = (float) SimplexNoise.noise((double) (x + CAVE_OFFSET) / CAVE_NOISE, (double)(z + CAVE_OFFSET) / CAVE_NOISE);
        return ModCaveBiomes.getBiome(new Vec2f(temperature, humidity), surfaceBiome);
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        int i = BiomeCoords.toBlock(x);
        int j = BiomeCoords.toBlock(y);
        int k = BiomeCoords.toBlock(z);

        MEBiome meBiome = middleEarthMapRuntime.getBiome(i, k);
        
        if (meBiome == null) {
            return biomes.get(0);
        }

        RegistryKey<Biome> biome = meBiome.biome;
        RegistryKey<Biome> processedBiome;

        if(!MEBiomesData.waterBiomes.contains(biome)) {
            float height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(i, k);
            if(j <= CavesPlacedFeatures.MAX_MITHRIL_HEIGHT && meBiome.caveType == CaveType.MISTIES) {
                processedBiome = MEBiomeKeys.MITHRIL_CAVE;
            }
            else if(j < (height - 16)) {
                processedBiome = getCaveBiome(i, k, meBiome);
            }
            else if(height <= meBiome.waterHeight + 1.25f) {
                if(MEBiomesData.wastePondBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.wastePond.biome;
                } else if(MEBiomesData.mirkwoodSwampBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.mirkwoodSwamp.biome;
                } else if(MEBiomesData.oasisBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.oasis.biome;
                } else if(MEBiomesData.frozenBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.frozenPond.biome;
                } else if(MEBiomesData.anduinWaterBiomes.contains(biome)){
                    processedBiome = MEBiomesData.greatRiver.biome;
                }
                else {
                    processedBiome = MEBiomesData.pond.biome;
                }
            } else if(biome.isOf(MEBiomeKeys.NAN_CURUNIR.getRegistryRef()) && ProceduralStructures.isInsideIsengard(i, k)) {
                processedBiome = MEBiomeKeys.ISENGARD;
            } else processedBiome = biome;
        } else processedBiome = biome;

        return biomes.stream().filter(
                        b -> b.getKey().get().toString().equalsIgnoreCase(processedBiome.toString()))
                .findFirst().get();
    }
}
