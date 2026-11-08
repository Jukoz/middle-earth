package net.sevenstars.middleearth.world.features.underground.gems;

import net.sevenstars.middleearth.block.special.gems.GemKind;
import net.sevenstars.middleearth.block.special.gems.GemOreBlock;
import net.sevenstars.middleearth.block.special.gems.GemOreStone;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.FeatureConfig;

/**
 * Config for one gem's ore placement.
 *
 * @param gem     the gem to hide
 * @param targets host ore blocks whose clusters can hold the gem
 * @param minY    lowest y (inclusive)
 * @param maxY    highest y (inclusive)
 * @param oneIn      each host cluster in the band has a 1-in-this chance to get gems
 * @param perCluster how many of that cluster's ore blocks become gems (capped at the cluster's size)
 * @param region     if set, the SURFACE biome above the chunk must be in this tag (underground is cave biomes)
 */
public record GemOreFeatureConfig(GemKind gem, List<Block> targets, int minY, int maxY, int oneIn, int perCluster,
                                  Optional<TagKey<Biome>> region) implements FeatureConfig {
    public static final Codec<GemOreFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        GemKind.CODEC.fieldOf("gem").forGetter(GemOreFeatureConfig::gem),
        Registries.BLOCK.getCodec().listOf().fieldOf("targets").forGetter(GemOreFeatureConfig::targets),
        Codec.INT.fieldOf("min_y").forGetter(GemOreFeatureConfig::minY),
        Codec.INT.fieldOf("max_y").forGetter(GemOreFeatureConfig::maxY),
        Codec.intRange(1, 1000).fieldOf("one_in").forGetter(GemOreFeatureConfig::oneIn),
        Codec.intRange(1, 64).optionalFieldOf("per_cluster", 1).forGetter(GemOreFeatureConfig::perCluster),
        TagKey.codec(RegistryKeys.BIOME).optionalFieldOf("region").forGetter(GemOreFeatureConfig::region)
    ).apply(instance, GemOreFeatureConfig::new));
}
