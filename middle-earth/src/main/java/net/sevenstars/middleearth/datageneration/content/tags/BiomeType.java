package net.sevenstars.middleearth.datageneration.content.tags;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public enum BiomeType {
    PLAINS(ConventionalBiomeTags.IS_PLAINS),
    SNOWY_PLAINS(ConventionalBiomeTags.IS_SNOWY_PLAINS),
    FOREST(ConventionalBiomeTags.IS_FOREST),
    TAIGA(ConventionalBiomeTags.IS_TAIGA),
    HILL(ConventionalBiomeTags.IS_HILL),
    MOUNTAIN(ConventionalBiomeTags.IS_MOUNTAIN),
    MOUNTAIN_SLOPE(ConventionalBiomeTags.IS_MOUNTAIN_SLOPE),
    MOUNTAIN_PEAK(ConventionalBiomeTags.IS_MOUNTAIN_PEAK),
    SWAMP(ConventionalBiomeTags.IS_SWAMP),
    RIVER(ConventionalBiomeTags.IS_RIVER),
    OCEAN(ConventionalBiomeTags.IS_OCEAN),
    BEACH(ConventionalBiomeTags.IS_BEACH),
    STONY_SHORES(ConventionalBiomeTags.IS_STONY_SHORES),
    DESERT(ConventionalBiomeTags.IS_DESERT),
    SAVANNA(ConventionalBiomeTags.IS_SAVANNA),
    WASTELAND(ConventionalBiomeTags.IS_WASTELAND),
    SNOWY(ConventionalBiomeTags.IS_SNOWY),
    AQUATIC_ICY(ConventionalBiomeTags.IS_AQUATIC_ICY),
    CAVE(ConventionalBiomeTags.IS_CAVE);

    public final TagKey<Biome> tag;

    BiomeType(TagKey<Biome> tag) {
        this.tag = tag;
    }
}
